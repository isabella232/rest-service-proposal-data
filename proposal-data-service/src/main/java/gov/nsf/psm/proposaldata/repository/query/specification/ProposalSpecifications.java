package gov.nsf.psm.proposaldata.repository.query.specification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import gov.nsf.psm.foundation.model.Personnel;
import gov.nsf.psm.foundation.model.proposal.ProposalQueryEnum;
import gov.nsf.psm.foundation.model.proposal.ProposalQueryParams;
import gov.nsf.psm.foundation.model.proposal.ProposalRevisionType;
import gov.nsf.psm.foundation.model.proposal.ProposalStatus;
import gov.nsf.psm.proposaldata.entity.BudgetInstitutionEntity;
import gov.nsf.psm.proposaldata.entity.ProposalPersonsEntity;
import gov.nsf.psm.proposaldata.entity.ProposalPrep;
import gov.nsf.psm.proposaldata.entity.ProposalPrepRevision;

public class ProposalSpecifications {

    private ProposalSpecifications() {
        // Private constructor
    }

    public static Specification<ProposalPrep> fromParams(final ProposalQueryParams params, Metamodel metamodel) {

        return new Specification<ProposalPrep>() {

            @Override
            public Predicate toPredicate(Root<ProposalPrep> fromClause, CriteriaQuery<?> query,
                    CriteriaBuilder builder) {

                query.distinct(true); // Make sure results are distinct

                EntityType<ProposalPrep> proposalPrep_ = metamodel.entity(ProposalPrep.class);
                EntityType<ProposalPrepRevision> proposalPrepRevision_ = metamodel.entity(ProposalPrepRevision.class);
                EntityType<ProposalPersonsEntity> proposalPersonsEntity_ = metamodel
                        .entity(ProposalPersonsEntity.class);
                EntityType<BudgetInstitutionEntity> budgetInstitutionEntity_ = metamodel
                        .entity(BudgetInstitutionEntity.class);
                Join<ProposalPrep, ProposalPrepRevision> revisions = fromClause.join("revisions", JoinType.INNER);

                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> nsfIdPredicates = new ArrayList<>();
                List<Predicate> roleInstPredicates = new ArrayList<>();

                if (!params.getIsSubmitted()) {
                    predicates.add(
                            builder.isNull(fromClause.get(proposalPrep_.getAttribute(ProposalQueryEnum.NSF_PROP_SUBM_DT.getFieldName()).getName())));
                } else if (params.getNsfPropSubmDate() != null) {
                    predicates
                            .add(builder.equal(fromClause.get(proposalPrep_.getAttribute(ProposalQueryEnum.NSF_PROP_SUBM_DT.getFieldName()).getName()),
                                    params.getNsfPropSubmDate()));
                }

                if (!params.getIsSPOAOR()) { // Non-SPOAOR Conditions

                    Join<ProposalPrepRevision, ProposalPersonsEntity> persons = revisions.join("persons");
                    if (params.getRolesToExclude() != null && !params.getRolesToExclude().isEmpty()) { // Exclude user roles if needed
                        predicates.add(builder.not(
                                 persons.get(proposalPersonsEntity_.getAttribute(ProposalQueryEnum.PROP_PERS_ROLE_CODE.getFieldName()).getName()).in(params.getRolesToExclude().stream().map(psmRole -> psmRole.getCode()).toArray())));
                    }

                    // Add personnel params
                    for (Personnel pers : params.getPersonnel()) {
                        Predicate nsfIdPredicate = builder.equal(
                                persons.get(proposalPersonsEntity_.getAttribute(ProposalQueryEnum.PROP_PERS_NSF_ID.getFieldName()).getName()),
                                pers.getNsfId());
                        long idCount = nsfIdPredicates.stream()
                                .filter(Personnel -> pers.getNsfId().equals(pers.getNsfId())).count();
                        if (idCount < 1) { // Unique nsfIds only
                            nsfIdPredicates.add(nsfIdPredicate);
                        }
                        if (pers.getPSMRole() != null && !StringUtils.isEmpty(pers.getPSMRole().getCode())) {
                            List<Predicate> rolePredicates = new ArrayList<>();
                            Predicate rolePredicate = builder.equal(
                                    persons.get(proposalPersonsEntity_.getAttribute(ProposalQueryEnum.PROP_PERS_ROLE_CODE.getFieldName()).getName()),
                                    pers.getPSMRole().getCode());
                            rolePredicates.add(rolePredicate);
                            if (pers.getInstitution() != null && !StringUtils.isEmpty(pers.getInstitution().getId())) {
                                Predicate instPredicate = builder.equal(
                                        persons.get(
                                                proposalPersonsEntity_.getAttribute(ProposalQueryEnum.PROP_PERS_INST_ID.getFieldName()).getName()),
                                        pers.getInstitution().getId());
                                rolePredicates.add(instPredicate);
                            }
                            roleInstPredicates.add(builder.and(rolePredicates.toArray(new Predicate[] {})));
                        }
                    }

                } else { // SPOAOR Conditions
                    
                    if (params.getRolesToExclude() != null && !params.getRolesToExclude().isEmpty()) { // Exclude user roles if needed
                        Subquery<Long> sq = query.subquery(Long.class);
                        Root<ProposalPersonsEntity> sqRoot = sq.from(ProposalPersonsEntity.class);
                        List<Predicate> sqWhereClause = new ArrayList<>();
                        sq.select(sqRoot.get(proposalPersonsEntity_.getAttribute(ProposalQueryEnum.NSF_PROP_PERS_REV_ID.getFieldName()).getName()));
                        sqWhereClause.add(sqRoot.get(proposalPersonsEntity_.getAttribute(ProposalQueryEnum.PROP_PERS_ROLE_CODE.getFieldName()).getName()).in(params.getRolesToExclude().stream().map(psmRole -> psmRole.getCode()).toArray()));
                        for (Personnel pers : params.getPersonnel()) {
                            Predicate nsfIdPredicate = builder.equal(
                                    sqRoot.get(proposalPersonsEntity_.getAttribute(ProposalQueryEnum.PROP_PERS_NSF_ID.getFieldName()).getName()),
                                    pers.getNsfId());
                            long idCount = nsfIdPredicates.stream()
                                    .filter(Personnel -> pers.getNsfId().equals(pers.getNsfId())).count();
                            if (idCount < 1) { // Unique nsfIds only
                                sqWhereClause.add(nsfIdPredicate);
                            }
                        }
                        sq.where(sqWhereClause.toArray(new Predicate[] {}));
                        predicates.add(builder.not(builder.in(revisions.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.NSF_PROP_PREP_REV_ID.getFieldName()).getName())).value(sq)));
                    }
                    
                    if(params.getCheckPermissions()) { // Check proposal permissions
                        List<Predicate> spoAORPredicates = new ArrayList<>();
                        Subquery<Long> sq = query.subquery(Long.class);
                        Root<ProposalPersonsEntity> sqRoot = sq.from(ProposalPersonsEntity.class);
                        List<Predicate> sqWhereClause = new ArrayList<>();
                        sq.select(sqRoot.get(proposalPersonsEntity_.getAttribute(ProposalQueryEnum.NSF_PROP_PERS_REV_ID.getFieldName()).getName()));
                        for (Personnel pers : params.getPersonnel()) {
                            Predicate nsfIdPredicate = builder.equal(
                                    sqRoot.get(proposalPersonsEntity_.getAttribute(ProposalQueryEnum.PROP_PERS_NSF_ID.getFieldName()).getName()),
                                    pers.getNsfId());
                            long idCount = nsfIdPredicates.stream()
                                    .filter(Personnel -> pers.getNsfId().equals(pers.getNsfId())).count();
                            if (idCount < 1) { // Unique nsfIds only
                                sqWhereClause.add(nsfIdPredicate);
                            }
                        }
                        sq.where(sqWhereClause.toArray(new Predicate[] {}));
                        spoAORPredicates.add(builder.in(revisions.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.NSF_PROP_PREP_REV_ID.getFieldName()).getName())).value(sq));
                        Set<Predicate> readOnlyStatusPredicates = new HashSet<>();
                        if(params.getStatusesToExcludeAORSPO() != null && !params.getStatusesToExcludeAORSPO().isEmpty()) {
                            for(ProposalStatus status : params.getStatusesToExcludeAORSPO()) {
                                Predicate readOnlyStatuses = builder.notEqual(revisions.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.PROP_PREP_STS_CODE.getFieldName()).getName()),
                                    status.getStatusCode());
                                readOnlyStatusPredicates.add(readOnlyStatuses);
                            }
                            Predicate readOnlyStatusCond = builder.and(readOnlyStatusPredicates.toArray(new Predicate[] {}));
                            spoAORPredicates.add(builder.or(readOnlyStatusCond));
                        }
                        predicates.add(builder.or(spoAORPredicates.toArray(new Predicate[] {})));
                    }
                    
                    Join<ProposalPrepRevision, BudgetInstitutionEntity> institutions = revisions.join("institutions");

                    for (Personnel pers : params.getPersonnel()) {
                        Set<Predicate> rolePredicates = new HashSet<>();
                        if (pers.getInstitution() != null && !StringUtils.isEmpty(pers.getInstitution().getId())) {
                            Predicate instPredicate = builder.equal(
                                    institutions.get(budgetInstitutionEntity_.getAttribute(ProposalQueryEnum.INST_ID.getFieldName()).getName()),
                                    pers.getInstitution().getId());
                            rolePredicates.add(instPredicate);
                        }
                        Predicate instIdCond = builder.and(rolePredicates.toArray(new Predicate[] {}));
                        roleInstPredicates.add(instIdCond);
                    }

                }

                // Get latest revision for each submission type and any PFUs
                List<Predicate> rvnTypePredicates = new ArrayList<>();
                for (ProposalRevisionType type : params.getPropRevnTypes()) {
                    List<Predicate> sqPredicates = new ArrayList<>();
                    Subquery<Long> sq = query.subquery(Long.class);
                    Root<ProposalPrepRevision> sqRoot = sq.from(ProposalPrepRevision.class);
                    List<Predicate> sqWhereClause = new ArrayList<>();
                    sq.select(builder.max(sqRoot.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.PROP_PREP_REVN_NUM.getFieldName()).getName())));
                    sqWhereClause.add(builder.equal(sqRoot.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.NSF_PROP_PREP_ID.getFieldName()).getName()),fromClause.get(proposalPrep_.getAttribute(ProposalQueryEnum.NSF_PROP_PREP_ID.getFieldName()).getName())));
                    sqWhereClause.add(builder.equal(sqRoot.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.PROP_PREP_REVN_TYPE_CODE.getFieldName()).getName()),
                            type.getType()));
                    if(!params.getIsSubmitted()) {
                        List<Predicate> statusPredicates = new ArrayList<>();
                        for (ProposalStatus status : params.getPropStatus()) {
                            statusPredicates.add(builder.equal(
                                    revisions.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.PROP_PREP_STS_CODE.getFieldName()).getName()),
                                    status.getStatusCode()));
                        }
                        if (!statusPredicates.isEmpty()) {
                            sqWhereClause.add(builder.or(statusPredicates.toArray(new Predicate[] {})));
                        }
                    } else {
                        for (ProposalStatus status : params.getPropStatus()) {
                            sqWhereClause.add(builder.equal(sqRoot.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.PROP_PREP_STS_CODE.getFieldName()).getName()),
                                status.getStatusCode()));
                        }
                    }
                    sq.where(sqWhereClause.toArray(new Predicate[] {}));
                    sqPredicates.add(builder.equal(revisions.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.PROP_PREP_REVN_NUM.getFieldName()).getName()), sq));
                    rvnTypePredicates.add(builder.or(sqPredicates.toArray(new Predicate[] {})));
                    if(params.getIsSubmitted()) {
                        List<Predicate> sqPredicates2 = new ArrayList<>();
                        Subquery<Long> sq2 = query.subquery(Long.class);
                        Root<ProposalPrepRevision> sqRoot2 = sq2.from(ProposalPrepRevision.class);
                        List<Predicate> sqWhereClause2 = new ArrayList<>();
                        sq2.select(builder.max(sqRoot2.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.PROP_PREP_REVN_NUM.getFieldName()).getName())));
                        sqWhereClause2.add(builder.equal(sqRoot2.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.NSF_PROP_PREP_ID.getFieldName()).getName()),fromClause.get(proposalPrep_.getAttribute(ProposalQueryEnum.NSF_PROP_PREP_ID.getFieldName()).getName())));
                        sqWhereClause2.add(builder.equal(sqRoot2.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.PROP_PREP_REVN_TYPE_CODE.getFieldName()).getName()),
                                type.getType()));
                        for (ProposalStatus status : params.getPropStatus()) {
                            sqWhereClause2.add(builder.notEqual(sqRoot2.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.PROP_PREP_STS_CODE.getFieldName()).getName()),
                                status.getStatusCode()));
                        }
                        sq2.where(sqWhereClause2.toArray(new Predicate[] {}));
                        sqPredicates2.add(builder.equal(revisions.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.PROP_PREP_REVN_NUM.getFieldName()).getName()), sq2));
                        rvnTypePredicates.add(builder.or(sqPredicates2.toArray(new Predicate[] {})));
                    }
                }
                
                // Add OR condition
                predicates.add(builder.or(rvnTypePredicates.toArray(new Predicate[] {})));
                
                // Add not null check on submission date if submitted
                if(params.getIsSubmitted()) { // Check that submission date is not null if submitted
                    predicates.add(builder.isNotNull(fromClause.get(proposalPrep_.getAttribute(ProposalQueryEnum.NSF_PROP_SUBM_DT.getFieldName()).getName())));
                }
                
                // Add OR condition
                if (!nsfIdPredicates.isEmpty()) {
                    predicates.add(builder.or(nsfIdPredicates.toArray(new Predicate[] {})));
                }

                // Role code is optional
                if (!roleInstPredicates.isEmpty()) {
                    predicates.add(builder.or(roleInstPredicates.toArray(new Predicate[] {})));
                }
                
                // Excluded statuses are optional
                Set<Predicate> excludedStatusPredicates = new HashSet<>();
                if(params.getStatusesToExclude() != null && !params.getStatusesToExclude().isEmpty()) {
                    for(ProposalStatus status : params.getStatusesToExclude()) {
                        Predicate excludedStatus = builder.notEqual(revisions.get(proposalPrepRevision_.getAttribute(ProposalQueryEnum.PROP_PREP_STS_CODE.getFieldName()).getName()),
                            status.getStatusCode());
                        excludedStatusPredicates.add(excludedStatus);
                    }
                    Predicate excludedStatusCond = builder.and(excludedStatusPredicates.toArray(new Predicate[] {}));
                    predicates.add(excludedStatusCond);
                }

                return builder.and(predicates.toArray(new Predicate[] {}));
            }
        };
    }
}
