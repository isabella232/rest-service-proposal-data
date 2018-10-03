package gov.nsf.psm.proposaldata.utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.Advisee;
import gov.nsf.psm.foundation.model.Affiliation;
import gov.nsf.psm.foundation.model.BiographicalSketch;
import gov.nsf.psm.foundation.model.BudgetImpact;
import gov.nsf.psm.foundation.model.BudgetJustification;
import gov.nsf.psm.foundation.model.COA;
import gov.nsf.psm.foundation.model.CoEditor;
import gov.nsf.psm.foundation.model.Collaborator;
import gov.nsf.psm.foundation.model.CurrentAndPendingSupport;
import gov.nsf.psm.foundation.model.DataManagementPlan;
import gov.nsf.psm.foundation.model.Directorate;
import gov.nsf.psm.foundation.model.Division;
import gov.nsf.psm.foundation.model.FacilitiesEquipment;
import gov.nsf.psm.foundation.model.FundingOpportunity;
import gov.nsf.psm.foundation.model.Institution;
import gov.nsf.psm.foundation.model.OtherSuppDocs;
import gov.nsf.psm.foundation.model.OthrPersBioInfo;
import gov.nsf.psm.foundation.model.PSMMessage;
import gov.nsf.psm.foundation.model.PSMRole;
import gov.nsf.psm.foundation.model.Personnel;
import gov.nsf.psm.foundation.model.PostDocMentPlan;
import gov.nsf.psm.foundation.model.ProgramElement;
import gov.nsf.psm.foundation.model.ProjectDescription;
import gov.nsf.psm.foundation.model.ProjectSummary;
import gov.nsf.psm.foundation.model.ProposalElectronicSign;
import gov.nsf.psm.foundation.model.ProposalUpdateJustification;
import gov.nsf.psm.foundation.model.ReferencesCited;
import gov.nsf.psm.foundation.model.Relationship;
import gov.nsf.psm.foundation.model.ReviewersNotInclude;
import gov.nsf.psm.foundation.model.SuggestedReviewer;
import gov.nsf.psm.foundation.model.UploadableProposalSection;
import gov.nsf.psm.foundation.model.budget.BudgetRecord;
import gov.nsf.psm.foundation.model.budget.EquipmentCost;
import gov.nsf.psm.foundation.model.budget.FringeBenefitCost;
import gov.nsf.psm.foundation.model.budget.IndirectCost;
import gov.nsf.psm.foundation.model.budget.InstitutionBudget;
import gov.nsf.psm.foundation.model.budget.OtherDirectCost;
import gov.nsf.psm.foundation.model.budget.OtherPersonnelCost;
import gov.nsf.psm.foundation.model.budget.ParticipantSupportCost;
import gov.nsf.psm.foundation.model.budget.SeniorPersonnelCost;
import gov.nsf.psm.foundation.model.budget.TravelCost;
import gov.nsf.psm.foundation.model.coversheet.PiCoPiInformation;
import gov.nsf.psm.foundation.model.lookup.AdviseeTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CoEditorTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CollaborativeTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CollaboratorTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.Deadline;
import gov.nsf.psm.foundation.model.lookup.DeadlineTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.InstitutionRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.LoginUserRoleType;
import gov.nsf.psm.foundation.model.lookup.OtherPersonnelRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.ProposalStatusTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.ProposalTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.RelationshipTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.SeniorPersonRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.SubmissionTypeLookUp;
import gov.nsf.psm.foundation.model.proposal.Proposal;
import gov.nsf.psm.foundation.model.proposal.ProposalHeader;
import gov.nsf.psm.foundation.model.proposal.ProposalPackage;
import gov.nsf.psm.foundation.model.proposal.ProposalPermission;
import gov.nsf.psm.foundation.model.proposal.ProposalRevision;
import gov.nsf.psm.foundation.model.proposal.ProposalRevisionType;
import gov.nsf.psm.foundation.model.proposal.ProposalStatus;
import gov.nsf.psm.proposaldata.entity.AdviseeEntity;
import gov.nsf.psm.proposaldata.entity.AdviseeType;
import gov.nsf.psm.proposaldata.entity.AffiliationEntity;
import gov.nsf.psm.proposaldata.entity.BaseEntity;
import gov.nsf.psm.proposaldata.entity.BiographicalSketchEntity;
import gov.nsf.psm.proposaldata.entity.BudgetImpactEntity;
import gov.nsf.psm.proposaldata.entity.BudgetInstitutionEntity;
import gov.nsf.psm.proposaldata.entity.BudgetJustificationEntity;
import gov.nsf.psm.proposaldata.entity.CoEditorEntity;
import gov.nsf.psm.proposaldata.entity.CoEditorType;
import gov.nsf.psm.proposaldata.entity.CollaborativeType;
import gov.nsf.psm.proposaldata.entity.CollaboratorEntity;
import gov.nsf.psm.proposaldata.entity.CollaboratorType;
import gov.nsf.psm.proposaldata.entity.CurrentAndPendingSupportEntity;
import gov.nsf.psm.proposaldata.entity.DataManagementPlanEntity;
import gov.nsf.psm.proposaldata.entity.DeadlineTypeEntity;
import gov.nsf.psm.proposaldata.entity.EquipmentBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.FacilityEquipmentEntity;
import gov.nsf.psm.proposaldata.entity.FringeBenefitBudgetEntity;
import gov.nsf.psm.proposaldata.entity.FundingOppExlclusionEntity;
import gov.nsf.psm.proposaldata.entity.IndirectBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.InstitutionType;
import gov.nsf.psm.proposaldata.entity.LoginUserRolePermissionEntity;
import gov.nsf.psm.proposaldata.entity.LoginUserRoleTypeEntity;
import gov.nsf.psm.proposaldata.entity.OtherDirectCostEntity;
import gov.nsf.psm.proposaldata.entity.OtherPersonnelType;
import gov.nsf.psm.proposaldata.entity.OtherPersonsBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.OtherSuppDocsEntity;
import gov.nsf.psm.proposaldata.entity.OthrPersBioInfoEntity;
import gov.nsf.psm.proposaldata.entity.ParticipantsSupportCostEntity;
import gov.nsf.psm.proposaldata.entity.PostDocMentPlanEntity;
import gov.nsf.psm.proposaldata.entity.ProjectDescriptionEntity;
import gov.nsf.psm.proposaldata.entity.ProjectSummaryEntity;
import gov.nsf.psm.proposaldata.entity.ProposalCOAEntity;
import gov.nsf.psm.proposaldata.entity.ProposalElectronicSignEntity;
import gov.nsf.psm.proposaldata.entity.ProposalMessageEntity;
import gov.nsf.psm.proposaldata.entity.ProposalPersonnelDemographicInfoEntity;
import gov.nsf.psm.proposaldata.entity.ProposalPersonsEntity;
import gov.nsf.psm.proposaldata.entity.ProposalPrep;
import gov.nsf.psm.proposaldata.entity.ProposalPrepRevision;
import gov.nsf.psm.proposaldata.entity.ProposalStatusType;
import gov.nsf.psm.proposaldata.entity.ProposalType;
import gov.nsf.psm.proposaldata.entity.ProposalUpdateJustificationEntity;
import gov.nsf.psm.proposaldata.entity.ReferencesCitedEntity;
import gov.nsf.psm.proposaldata.entity.RelationshipEntity;
import gov.nsf.psm.proposaldata.entity.RelationshipType;
import gov.nsf.psm.proposaldata.entity.ReviewersNotIncludeEntity;
import gov.nsf.psm.proposaldata.entity.SeniorPersonnelType;
import gov.nsf.psm.proposaldata.entity.SeniorPersonsBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.SubmissionType;
import gov.nsf.psm.proposaldata.entity.SuggestedReviewerEntity;
import gov.nsf.psm.proposaldata.entity.TravelBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.UnitOfConsideration;
import gov.nsf.psm.proposaldata.repository.ProposalPrepRepository;
import gov.nsf.psm.proposaldata.service.parameter.InstitutionBudgetParameter;

public class ConverterUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterUtility.class);

    private ConverterUtility() {
    }

    public static List<OtherPersonnelRoleTypeLookUp> convertOtherPersonnelTypeEntitiesToDtos(
            List<OtherPersonnelType> entities) {
        LOGGER.debug("ConverterUtility.convertOtherPersonnelTypeEntitiesToDtos()");
        List<OtherPersonnelRoleTypeLookUp> dtos = new ArrayList<OtherPersonnelRoleTypeLookUp>();
        for (OtherPersonnelType entity : entities) {
            OtherPersonnelRoleTypeLookUp dto = new OtherPersonnelRoleTypeLookUp();
            dto.setCode(entity.getOthPersTypeCode().trim());
            dto.setDescription(entity.getOthPersTypeTxt());
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<ProposalStatusTypeLookUp> convertProposalStatusTypeEntitiesToDtos(
            List<ProposalStatusType> entities) {
        LOGGER.debug("ConverterUtility.convertProposalStatusTypeEntitiesToDtos()");
        List<ProposalStatusTypeLookUp> dtos = new ArrayList<ProposalStatusTypeLookUp>();
        for (ProposalStatusType entity : entities) {
            ProposalStatusTypeLookUp dto = new ProposalStatusTypeLookUp();
            dto.setCode(entity.getPropPrepSttsCode().trim());
            dto.setDescription(entity.getPropPrepSttsTxt());
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<SeniorPersonRoleTypeLookUp> convertSeniorPersonnelTypeEntitiesToDtos(
            List<SeniorPersonnelType> entities) {
        LOGGER.debug("ConverterUtility.convertSeniorPersonnelTypeEntitiesToDtos()");
        List<SeniorPersonRoleTypeLookUp> dtos = new ArrayList<SeniorPersonRoleTypeLookUp>();
        for (SeniorPersonnelType entity : entities) {
            SeniorPersonRoleTypeLookUp dto = new SeniorPersonRoleTypeLookUp();
            dto.setCode(entity.getPropPersRoleCode().trim());
            dto.setDescription(entity.getPropPersRoleTxt());
            dto.setAbbreviation(entity.getPropPersRoleAbbr());
            dto.setUserDataServiceRole(entity.getUserDataServiceRole());
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<InstitutionRoleTypeLookUp> convertInstitutionTypeEntitiesToDtos(List<InstitutionType> entities) {
        LOGGER.debug("ConverterUtility.convertInstitutionTypeEntitiesToDtos()");
        List<InstitutionRoleTypeLookUp> dtos = new ArrayList<InstitutionRoleTypeLookUp>();
        for (InstitutionType entity : entities) {
            InstitutionRoleTypeLookUp dto = new InstitutionRoleTypeLookUp();
            dto.setCode(entity.getInstPropRoleTypeCode().trim());
            dto.setDescription(entity.getInstPropRoleTypeTxt());
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<SubmissionTypeLookUp> convertSubmissionTypeEntitiesToDtos(List<SubmissionType> entities) {
        LOGGER.debug("ConverterUtility.convertSubmissionTypeEntitiesToDtos()");
        List<SubmissionTypeLookUp> dtos = new ArrayList<SubmissionTypeLookUp>();
        for (SubmissionType entity : entities) {
            SubmissionTypeLookUp dto = new SubmissionTypeLookUp();
            dto.setCode(entity.getPropSubmTypeCode().trim());
            dto.setDescription(entity.getPropSubmTypeTxt());
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<CollaborativeTypeLookUp> convertCollabTypeEntitiesToDtos(List<CollaborativeType> entities) {
        LOGGER.debug("ConverterUtility.convertCollabTypeEntitiesToDtos()");
        List<CollaborativeTypeLookUp> dtos = new ArrayList<CollaborativeTypeLookUp>();
        for (CollaborativeType entity : entities) {
            CollaborativeTypeLookUp dto = new CollaborativeTypeLookUp();
            dto.setCode(entity.getPropColbTypeCode().trim());
            dto.setDescription(entity.getPropColbTypeTxt());
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<CollaboratorTypeLookUp> convertCollaboratorTypeEntitiesToDtos(List<CollaboratorType> entities) {
        LOGGER.debug("ConverterUtility.convertCollaboratorTypeEntitiesToDtos()");
        List<CollaboratorTypeLookUp> dtos = new ArrayList<CollaboratorTypeLookUp>();
        for (CollaboratorType entity : entities) {
            CollaboratorTypeLookUp dto = new CollaboratorTypeLookUp();
            dto.setCode(entity.getPropClbrTypeCode().trim());
            dto.setDescription(entity.getPropClbrTypeTxt());
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<LoginUserRoleType> convertLoginUserRoleTypeEntitiesToDtos(
            List<LoginUserRoleTypeEntity> entities) {
        LOGGER.debug("ConverterUtility.convertLoginUserRoleTypeEntitiesToDtos()");
        List<LoginUserRoleType> dtos = new ArrayList<LoginUserRoleType>();
        for (LoginUserRoleTypeEntity entity : entities) {
            LoginUserRoleType dto = new LoginUserRoleType();
            dto.setCode(entity.getPropLognUserRoleTypeCode().trim());
            dto.setDescription(entity.getLognUserRoleTypeText().trim());
            dto.setAbbreviation(entity.getLognUserRoleTypeAbbr().trim());
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<ProposalPermission> convertLoginUserRolePermissionEntitiesToDtos(
            List<LoginUserRolePermissionEntity> entities) {
        LOGGER.debug("ConverterUtility.convertLoginUserRolePermissionEntitiesToDtos()");
        List<ProposalPermission> dtos = new ArrayList<ProposalPermission>();
        for (LoginUserRolePermissionEntity entity : entities) {
            ProposalPermission dto = new ProposalPermission();
            dto.setPermissionCode(entity.getPermApplParam());
            dto.setDescription(entity.getPermDescription());
            dtos.add(dto);
        }
        LOGGER.debug("********* Returning : " + dtos);
        return dtos;
    }

    public static List<AdviseeTypeLookUp> convertAdviseeTypeEntitiesToDtos(List<AdviseeType> entities) {
        LOGGER.debug("ConverterUtility.convertAdviseeTypeEntitiesToDtos()");
        List<AdviseeTypeLookUp> dtos = new ArrayList<AdviseeTypeLookUp>();
        for (AdviseeType entity : entities) {
            AdviseeTypeLookUp dto = new AdviseeTypeLookUp();
            dto.setCode(entity.getPropAdvrTypeCode().trim());
            dto.setDescription(entity.getPropAdvrTypeTxt());
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<CoEditorTypeLookUp> convertCoEditorTypeEntitiesToDtos(List<CoEditorType> entities) {
        LOGGER.debug("ConverterUtility.convertCoEditorTypeEntitiesToDtos()");
        List<CoEditorTypeLookUp> dtos = new ArrayList<CoEditorTypeLookUp>();
        for (CoEditorType entity : entities) {
            CoEditorTypeLookUp dto = new CoEditorTypeLookUp();
            dto.setCode(entity.getPropCoedTypeCode().trim());
            dto.setDescription(entity.getPropCoedTypeTxt());
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<RelationshipTypeLookUp> convertRelationshipTypeEntitiesToDtos(List<RelationshipType> entities) {
        LOGGER.debug("ConverterUtility.convertRelationshipTypeEntitiesToDtos()");
        List<RelationshipTypeLookUp> dtos = new ArrayList<RelationshipTypeLookUp>();
        for (RelationshipType entity : entities) {
            RelationshipTypeLookUp dto = new RelationshipTypeLookUp();
            dto.setCode(entity.getPropRelaTypeCode().trim());
            dto.setDescription(entity.getPropRelaTypeTxt());
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<ProposalTypeLookUp> convertProposalTypeEntitiesToDtos(List<ProposalType> entities) {
        LOGGER.debug("ConverterUtility.convertProposalTypeEntitiesToDtos()");
        List<ProposalTypeLookUp> dtos = new ArrayList<ProposalTypeLookUp>();
        for (ProposalType entity : entities) {
            ProposalTypeLookUp dto = new ProposalTypeLookUp();
            dto.setCode(entity.getPropTypeCode().trim());
            dto.setDescription(entity.getPropTypeTxt());
            dto.setAbbreviation(entity.getPropTypeAbbr());
            dtos.add(dto);
        }
        return dtos;
    }

    public static ProposalPrep convertProposalDtoToEntity(ProposalPackage propDto) {
        LOGGER.debug("ConverterUtility.convertProposalDtoToEntity()");
        ProposalPrep propEntity = new ProposalPrep();
        propEntity.setPropSubmTypeCode(propDto.getSubmissionType());
        propEntity.setPropTypeCode(propDto.getProposalType());
        propEntity.setFundOppId(propDto.getFundingOp().getFundingOpportunityId());

        propEntity.setPropInttDate(new Date());
        propEntity.setPropInttNsfId(propDto.getPi().getNsfId());

        setAuditFields(propEntity);
        propEntity.setLastUpdtUser(propDto.getLastUpdatedUser());
        propEntity.setPropColbTypeCode(propDto.getCollabType());

        gov.nsf.psm.foundation.model.UnitOfConsideration[] uocDtos = propDto.getUocs();

        // Populate uocs
        Set<UnitOfConsideration> uocs = convertUOCToUOCEntity(uocDtos, propDto.getFundingOp().getFundingOpportunityId(),
                propDto.getLastUpdatedUser());

        // Populate revision

        Set<ProposalPrepRevision> revisions = new HashSet<ProposalPrepRevision>();
        ProposalPrepRevision revision = new ProposalPrepRevision();
        Date revInsertDate = ProposalDataUtility.getCurrDate();
        revision.setRevnNum(ProposalRevision.ORIG_REV_NO);
        revision.setPropTitl(propDto.getProposalTitle());
        revision.setPropPrepRevnTypeCode(ProposalRevisionType.ORIGINAL_PROPOSAL);
        revision.setPropPrepSttsCode(ProposalStatus.NOT_FORWARDED_TO_SPO);
        revision.setPropPrepSttsDate(revInsertDate);
        revision.setCreatedDate(revInsertDate);
        revision.setCreatedUser(propDto.getPi().getNsfId());
        revision.setTotalRqstDolAmt(Constants.DEFAULT_BIG_DECIMAL_VALUE);
        revision.setCurrPappgVers(Constants.GPG_VERSION);
        if (propDto.getDeadline() != null) {
            revision.setPropDueDate(propDto.getDeadline().getDeadlineDate());
            revision.setPropDueDateTypeCode(propDto.getDeadline().getDeadlineTypeCode());
        }
        revision.setPropDueDateUpdateDate(ProposalDataUtility.getCurrDate());
        revision.setPropTitleUpdateDate(ProposalDataUtility.getCurrDate());
        setAuditFields(revision);
        revision.setLastUpdtUser(propDto.getLastUpdatedUser());
        revisions.add(revision);

        // add child objects to proposal prep entity
        propEntity.setUocs(uocs);
        propEntity.setRevisions(revisions);

        return propEntity;
    }

    public static ProposalPackage convertProposalEntityToDto(ProposalPrep propEntity, String revId) {
        LOGGER.debug("ConverterUtility.convertProposalEntityToDto()");
        ProposalPackage propDto = new ProposalPackage();
        propDto.setProposalType(propEntity.getPropTypeCode().trim());
        propDto.setSubmissionType(propEntity.getPropSubmTypeCode().trim());
        propDto.setProposalTypeCode(propEntity.getPropTypeCode().trim());
        propDto.setSubmissionTypeCode(propEntity.getPropSubmTypeCode().trim());
        propDto.setPropPrepId(String.valueOf(propEntity.getPropPrepId()));
        propDto.setCollabType(propEntity.getPropColbTypeCode().trim());
        propDto.setLastUpdatedPgm(propEntity.getLastUpdtPgm());
        propDto.setLastUpdatedTmsp(propEntity.getLastUpdtTmsp());
        propDto.setLastUpdatedUser(propEntity.getLastUpdtUser());
        propDto.setSubmissionDate(propEntity.getNsfPropSubmDate());
        propDto.setProposalInitiateDate(propEntity.getPropInttDate());
        propDto.setNsfPropId(propEntity.getNsfPropId());
        FundingOpportunity foDto = new FundingOpportunity();
        foDto.setFundingOpportunityId(propEntity.getFundOppId());
        propDto.setFundingOp(foDto);

        // Retrieve UOC data
        List<gov.nsf.psm.foundation.model.UnitOfConsideration> uocDtos = new ArrayList<gov.nsf.psm.foundation.model.UnitOfConsideration>();

        for (UnitOfConsideration uocEntity : propEntity.getUocs()) {
            Directorate dirDto = new Directorate();
            dirDto.setDirectorateCode(extractDirectorateCodeFromDivisionCode(uocEntity.getOrgCode().trim()));

            Division divDto = new Division();
            divDto.setDivisionCode(uocEntity.getOrgCode().trim());

            ProgramElement pgmEleDto = new ProgramElement();
            pgmEleDto.setProgramElementCode(uocEntity.getPgmEleCode().trim());

            gov.nsf.psm.foundation.model.UnitOfConsideration uocDto = new gov.nsf.psm.foundation.model.UnitOfConsideration();
            uocDto.setDirectorate(dirDto);
            uocDto.setDivision(divDto);
            uocDto.setProgramElement(pgmEleDto);
            uocDto.setUocOrdrNum(uocEntity.getUocOrdrNum());
            uocDto.setLastUpdatedTmsp(uocEntity.getLastUpdtTmsp());
            uocDtos.add(uocDto);
        }
        propDto.setUocList(uocDtos);
        propDto.setUocs(uocDtos.toArray(new gov.nsf.psm.foundation.model.UnitOfConsideration[uocDtos.size()]));

        // Extract the collab code and proposal title from revision record
        // code/proposal title from multiple revision records
        for (ProposalPrepRevision revisionEntity : propEntity.getRevisions()) {
            LOGGER.debug("******* Revision : " + revisionEntity.toString());
            if (revId != null && revId.equalsIgnoreCase(String.valueOf(revisionEntity.getPropPrepRevnId()))) {
                LOGGER.debug("******* Revision Id : " + revisionEntity.getPropPrepRevnId());
                propDto.setProposalTitle(revisionEntity.getPropTitl());
                propDto.setProposalStatus(revisionEntity.getPropPrepSttsCode().trim());
                propDto.setPropPrepRevnTypeCode(revisionEntity.getPropPrepRevnTypeCode().trim());
                propDto.setTotalRqstDolAmt(revisionEntity.getTotalRqstDolAmt());
                
                /* Set Deadline Dates */
                Deadline deadline = new Deadline();
                //deadline date
                deadline.setDeadlineDate(revisionEntity.getPropDueDate());
                //deadline type
                if (revisionEntity.getPropDueDateTypeCode() != null) {
                    deadline.setDeadlineTypeCode(revisionEntity.getPropDueDateTypeCode().trim());
                }
                // set to dto
                propDto.setDeadline(deadline); 
                
            }

        }
        return propDto;
    }

    /**
     * @param uploadableProposalSection
     * @param propRevId
     * @return ProjectSummaryEntity
     */
    public static ProjectSummaryEntity convertProjectSummaryDtoToEntity(UploadableProposalSection projectSummary,
            Long propRevId) {
        LOGGER.debug("ConverterUtility.convertProjectSummaryDtoToEntity()");
        ProjectSummaryEntity psEntity = new ProjectSummaryEntity();
        if (projectSummary instanceof ProjectSummary) {
            ProjectSummary summary = (ProjectSummary) projectSummary;
            psEntity.setPropPrepRevisionId(propRevId);
            psEntity.setProjectSummaryDocFileLocation(projectSummary.getFilePath());
            psEntity.setProjectSummaryDocPageCount(projectSummary.getPageCount());
            psEntity.setProjectSummaryOverViewText(StringEscapeUtils.escapeHtml(summary.getOverview()));
            psEntity.setProjectSummaryBroderImpactText(StringEscapeUtils.escapeHtml(summary.getBrodimpact()));
            psEntity.setProjectSummaryIntellectualMeritText(StringEscapeUtils.escapeHtml(summary.getIntellmerit()));
            psEntity.setProjectSummaryOrigFileName(projectSummary.getOrigFileName());
            setAuditFields(psEntity);
            psEntity.setLastUpdtUser(projectSummary.getLastUpdatedUser());
        }
        return psEntity;
    }

    public static ProposalElectronicSignEntity convertProposalElectronicSignDTOtoEntity(
            ProposalElectronicSign proposalElectronicSign) {
        LOGGER.debug("ConverterUtility.convertProposalElectronicSignDTOtoEntity()");
        ProposalElectronicSignEntity pesEntity = new ProposalElectronicSignEntity();
        pesEntity.setPropPrepRevnId(Integer.valueOf(proposalElectronicSign.getPropRevId()));
        pesEntity.setPropPrepRevnTypeCode(proposalElectronicSign.getPropPrepRevnTypeCode());
        pesEntity.setPropEleCertTypeId(1);
        pesEntity.setUserNsfId(proposalElectronicSign.getUserNsfId());
        pesEntity.setUserLastname(proposalElectronicSign.getUserLastname());
        pesEntity.setUserFirstName(proposalElectronicSign.getUserFirstName());
        pesEntity.setUserMiddleInit(proposalElectronicSign.getUserMiddleInit());
        pesEntity.setUserFaxNumber(proposalElectronicSign.getUserFaxNumber());
        pesEntity.setUserPhoneNumber(proposalElectronicSign.getUserPhoneNumber());
        pesEntity.setUserEmailAddress(proposalElectronicSign.getUserEmailAddress());
        pesEntity.setIpAddress(proposalElectronicSign.getIpAddress());
        pesEntity.setInstitutionId(proposalElectronicSign.getInstitutionId());
        pesEntity.setElecSignDate(ProposalDataUtility.getCurrDate());
        pesEntity.setElecSignCertAgreement(proposalElectronicSign.getElecSignCertAgreement());
        pesEntity.setDebarFlag(proposalElectronicSign.getDebarFlag());
        pesEntity.setDebarText(proposalElectronicSign.getDebarText());
        pesEntity.setCreatedDate(ProposalDataUtility.getCurrDate());
        pesEntity.setCreatedUser(proposalElectronicSign.getUserNsfId());
        setAuditFields(pesEntity);
        pesEntity.setLastUpdtUser(proposalElectronicSign.getLastUpdatedUser());
        return pesEntity;
    }

    public static ProposalElectronicSign convertProposalElectronicSignEntityToDto(
            ProposalElectronicSignEntity proposalElectronicSignEntity) {
        LOGGER.debug("ConverterUtility.convertProposalElectronicSignEntityToDto()");
        ProposalElectronicSign proposalElectronicSign = new ProposalElectronicSign();
        proposalElectronicSign.setPropEelecSignId(String.valueOf(proposalElectronicSignEntity.getPropEelecSignId()));
        proposalElectronicSign.setPropRevId(String.valueOf(proposalElectronicSignEntity.getPropPrepRevnId()));
        proposalElectronicSign.setPropPrepRevnTypeCode(proposalElectronicSignEntity.getPropPrepRevnTypeCode());
        proposalElectronicSign
                .setPropEleCertTypeId(String.valueOf(proposalElectronicSignEntity.getPropEleCertTypeId()));
        proposalElectronicSign.setUserNsfId(String.valueOf(proposalElectronicSignEntity.getUserNsfId()));
        proposalElectronicSign.setUserLastname(proposalElectronicSignEntity.getUserLastname());
        proposalElectronicSign.setUserFirstName(proposalElectronicSignEntity.getUserFirstName());
        proposalElectronicSign.setUserMiddleInit(proposalElectronicSignEntity.getUserMiddleInit());
        proposalElectronicSign.setUserFaxNumber(proposalElectronicSignEntity.getUserFaxNumber());
        proposalElectronicSign.setUserPhoneNumber(proposalElectronicSignEntity.getUserPhoneNumber());
        proposalElectronicSign.setUserEmailAddress(proposalElectronicSignEntity.getUserEmailAddress());
        proposalElectronicSign.setIpAddress(proposalElectronicSignEntity.getIpAddress());
        proposalElectronicSign.setInstitutionId(proposalElectronicSignEntity.getInstitutionId());
        proposalElectronicSign.setElecSignDate(proposalElectronicSignEntity.getElecSignDate());
        proposalElectronicSign.setElecSignCertAgreement(proposalElectronicSignEntity.getElecSignCertAgreement());
        proposalElectronicSign.setDebarFlag(proposalElectronicSignEntity.getDebarFlag());
        proposalElectronicSign.setDebarText(proposalElectronicSignEntity.getDebarText());
        proposalElectronicSign.setCreatedDate(proposalElectronicSignEntity.getCreatedDate());
        proposalElectronicSign.setCreatedUser(proposalElectronicSignEntity.getCreatedUser());
        proposalElectronicSign.setLastUpdatedUser(proposalElectronicSignEntity.getLastUpdtUser());
        proposalElectronicSign.setLastUpdatedTmsp(proposalElectronicSignEntity.getLastUpdtTmsp());
        proposalElectronicSign.setLastUpdatedPgm(proposalElectronicSignEntity.getLastUpdtPgm());

        return proposalElectronicSign;
    }

    /**
     * This method will return new BiographicalSketchEntity
     *
     * @param propRevId
     * @param propPersId
     * @return
     */
    public static BiographicalSketchEntity getNewBiographicalSketchEntity(String propRevId, String propPersId,
            String nsfId) {
        LOGGER.debug("ConverterUtility.getNewBiographicalSketchEntity()");

        BiographicalSketchEntity bioSketchEntity = new BiographicalSketchEntity();
        bioSketchEntity.setPropPersId(Long.valueOf(propPersId));
        bioSketchEntity.setPropPrepRevnId(Long.valueOf(propRevId));
        bioSketchEntity.setBioSketchDocfileLoc("");
        bioSketchEntity.setBioSketchDocpageCount(0);
        bioSketchEntity.setBioSketchOrigFileName("");
        setAuditFields(bioSketchEntity);
        bioSketchEntity.setLastUpdtUser(nsfId);

        return bioSketchEntity;
    }

    /**
     * This method converts SuggestedReviewer DTO to Entity.
     *
     * @param uploadableProposalSection
     * @param propRevId
     * @return ProjectDescriptionEntity
     */
    public static SuggestedReviewerEntity convertToSuggestedReviewerEntity(
            UploadableProposalSection uploadableProposalSection, Long propRevId) {
        LOGGER.debug("ConverterUtility.convertToSuggestedReviewerEntity()");

        SuggestedReviewerEntity srEntity = new SuggestedReviewerEntity();
        srEntity.setPropPrepRevnId(propRevId);
        srEntity.setSuggRevrFileLocation(uploadableProposalSection.getFilePath());
        srEntity.setSuggRevrDocPageCount(uploadableProposalSection.getPageCount());
        srEntity.setSuggRevrDocText("");
        srEntity.setSuggRevrDocOrigFileName(uploadableProposalSection.getOrigFileName());
        setAuditFields(srEntity);
        srEntity.setLastUpdtUser(uploadableProposalSection.getLastUpdatedUser());

        return srEntity;
    }

    /**
     * This method converts SuggestedReviewerEntity to DTO
     *
     * @param srEntity
     * @return
     */

    public static SuggestedReviewer getSuggestedReviewersDto(SuggestedReviewerEntity srEntity) {
        LOGGER.debug("ConverterUtility.getSuggestedReviewersDto()");

        SuggestedReviewer suggRevr = new SuggestedReviewer();
        suggRevr.setSuggRevrText(ProposalDataUtility.returnNullIfBlank(srEntity.getSuggRevrDocText()));
        suggRevr.setFilePath(ProposalDataUtility.returnNullIfBlank(srEntity.getSuggRevrFileLocation()));
        suggRevr.setOrigFileName(ProposalDataUtility.returnNullIfBlank(srEntity.getSuggRevrDocOrigFileName()));
        suggRevr.setPageCount(srEntity.getSuggRevrDocPageCount());
        suggRevr.setLastUpdatedTmsp(srEntity.getLastUpdtTmsp());
        return suggRevr;
    }

    /**
     * This method will return new BiographicalSketchEntity
     *
     * @param propRevId
     * @param propPersId
     * @return
     */
    public static CurrentAndPendingSupportEntity getNewCurrentAndPendingSupportEntity(String propRevId,
            String propPersId, String nsfId) {
        LOGGER.debug("ConverterUtility.getNewCurrentAndPendingSupportEntity()");
        CurrentAndPendingSupportEntity cpsEntity = new CurrentAndPendingSupportEntity();
        cpsEntity.setPropPersId(Long.valueOf(propPersId));
        cpsEntity.setPropPrepRevnId(Long.valueOf(propRevId));
        cpsEntity.setCurrPendSuppDocfileLoc("");
        cpsEntity.setCurrPendSuppDocPageCount(0);
        cpsEntity.setCurrPendSuppOrigFileName("");
        setAuditFields(cpsEntity);
        cpsEntity.setLastUpdtUser(nsfId);

        return cpsEntity;
    }

    /**
     * @param uploadableProposalSection
     * @param propRevId
     * @return ReferencesCitedEntity
     */
    public static ReferencesCitedEntity convertReferenceCitedDtoToEntity(
            UploadableProposalSection uploadableProposalSection, Long propRevId) {
        LOGGER.debug("ConverterUtility.convertReferenceCitedDtoToEntity()");

        ReferencesCitedEntity rcEntity = new ReferencesCitedEntity();
        rcEntity.setPropPrepRevnId(propRevId);
        rcEntity.setRefCitnDocfileLoc(uploadableProposalSection.getFilePath());
        rcEntity.setRefCitnDocpageCount(uploadableProposalSection.getPageCount());
        rcEntity.setRefCitnText("");
        rcEntity.setRefCitnOrigFileName(uploadableProposalSection.getOrigFileName());
        setAuditFields(rcEntity);
        rcEntity.setLastUpdtUser(uploadableProposalSection.getLastUpdatedUser());

        return rcEntity;
    }

    /**
     * @param facEquipEntity
     * @param propRevId
     * @return FacilityEquipmentEntity
     */
    public static FacilityEquipmentEntity convertFacilitiesEquipmentDtoToEntity(FacilityEquipmentEntity facEquipEntity,
            Long propRevId) {
        LOGGER.debug("ConverterUtility.convertFacilitiesEquipmentDtoToEntity()");

        facEquipEntity.setPropPrepRevnId(propRevId);
        facEquipEntity.setFacltEquipDocFileLocation(facEquipEntity.getFacltEquipDocFileLocation());
        facEquipEntity.setFacltEquipDocPageCount(facEquipEntity.getFacltEquipDocPageCount());
        facEquipEntity.setFacltEquipText("");
        facEquipEntity.setFacltEquipOrgFileName(facEquipEntity.getFacltEquipOrgFileName());
        setAuditFields(facEquipEntity);

        return facEquipEntity;
    }

    /**
     * This method Converts ProjectDescriptionEntity to DTO.
     *
     * @param pdEntity
     * @param propRevId
     * @return ProjectDescriptionEntity
     */
    public static ProjectDescriptionEntity convertProjectDescriptionDtoToEntity(ProjectDescriptionEntity pdEntity,
            Long propRevId) {
        LOGGER.debug("ConverterUtility.convertProjectDescriptionDtoToEntity()");
        pdEntity.setPropPrepRevnId(propRevId);
        pdEntity.setProjDescFileLocation(pdEntity.getProjDescFileLocation());
        pdEntity.setProjDescDocPageCount(pdEntity.getProjDescDocPageCount());
        pdEntity.setProjDescDocText("");
        pdEntity.setProjDescDocOrigFileName(pdEntity.getProjDescDocOrigFileName());
        setAuditFields(pdEntity);

        return pdEntity;
    }

    /**
     * This method converts FacilitiesEquipment DTO to Entity.
     *
     * @param uploadableProposalSection
     * @param propRevId
     * @return FacilityEquipmentEntity
     */
    public static FacilityEquipmentEntity convertFacilitiesEquipmentDtoToEntity(
            UploadableProposalSection uploadableProposalSection, Long propRevId) {
        LOGGER.debug("ConverterUtility.convertFacilitiesEquipmentDtoToEntity()");
        FacilityEquipmentEntity facEquipEntity = new FacilityEquipmentEntity();
        facEquipEntity.setPropPrepRevnId(propRevId);
        facEquipEntity.setFacltEquipDocFileLocation(uploadableProposalSection.getFilePath());
        facEquipEntity.setFacltEquipDocPageCount(uploadableProposalSection.getPageCount());
        facEquipEntity.setFacltEquipText("");
        facEquipEntity.setFacltEquipOrgFileName(uploadableProposalSection.getOrigFileName());
        setAuditFields(facEquipEntity);
        facEquipEntity.setLastUpdtUser(uploadableProposalSection.getLastUpdatedUser());

        return facEquipEntity;
    }

    /**
     * This method converts ProjectDescription DTO to Entity.
     *
     * @param uploadableProposalSection
     * @param propRevId
     * @return ProjectDescriptionEntity
     */
    public static ProjectDescriptionEntity convertProjectDescriptionDtoToEntity(
            UploadableProposalSection uploadableProposalSection, Long propRevId) {
        LOGGER.debug("ConverterUtility.convertProjectDescriptionDtoToEntity()");

        ProjectDescriptionEntity projDescEntity = new ProjectDescriptionEntity();
        projDescEntity.setPropPrepRevnId(propRevId);
        projDescEntity.setProjDescFileLocation(uploadableProposalSection.getFilePath());
        projDescEntity.setProjDescDocPageCount(uploadableProposalSection.getPageCount());
        projDescEntity.setProjDescDocText("");
        projDescEntity.setProjDescDocOrigFileName(uploadableProposalSection.getOrigFileName());
        setAuditFields(projDescEntity);
        projDescEntity.setLastUpdtUser(uploadableProposalSection.getLastUpdatedUser());
        return projDescEntity;
    }

    /**
     * This method converts BudgetJustification DTO to Entity.
     *
     * @param uploadableProposalSection
     * @param propRevId
     * @return ProjectDescriptionEntity
     */
    public static BudgetJustificationEntity convertBudgetJustificationDtoToEntity(
            UploadableProposalSection uploadableProposalSection, Long propRevId) {
        LOGGER.debug("ConverterUtility.convertBudgetJustificationDtoToEntity()");

        BudgetJustificationEntity budgJustEntity = new BudgetJustificationEntity();
        if (uploadableProposalSection instanceof BudgetJustification) {
            BudgetJustification justification = (BudgetJustification) uploadableProposalSection;
            budgJustEntity.setPropPrepRevnId(propRevId);
            budgJustEntity.setBudgJustFileLocation(justification.getFilePath());
            budgJustEntity.setBudgJustDocPageCount(justification.getPageCount());
            budgJustEntity.setBudgJustText(justification.getProjDescText());
            budgJustEntity.setBudgJustOrigFileName(justification.getOrigFileName());
            setAuditFields(budgJustEntity);
            budgJustEntity.setLastUpdtUser(justification.getLastUpdatedUser());
        }
        return budgJustEntity;
    }

    /**
     * This method converts BiographicalSketchEntity DTO to Entity.
     *
     * @param uploadableProposalSection
     * @param propRevId
     * @return ProjectDescriptionEntity
     */
    public static BiographicalSketchEntity convertBiographicalSketchDtoToEntity(
            UploadableProposalSection uploadableProposalSection, Long propPersId, Long propRevId) {
        LOGGER.debug("ConverterUtility.convertBiographicalSketchDtoToEntity()");

        BiographicalSketchEntity bioSketchEntity = new BiographicalSketchEntity();
        bioSketchEntity.setPropPersId(propPersId);
        bioSketchEntity.setPropPrepRevnId(propRevId);
        bioSketchEntity.setBioSketchDocfileLoc(uploadableProposalSection.getFilePath());
        bioSketchEntity.setBioSketchDocpageCount(uploadableProposalSection.getPageCount());
        bioSketchEntity.setBioSketchText("");
        bioSketchEntity.setBioSketchOrigFileName(uploadableProposalSection.getOrigFileName());
        setAuditFields(bioSketchEntity);
        bioSketchEntity.setLastUpdtUser(uploadableProposalSection.getLastUpdatedUser());

        return bioSketchEntity;
    }

    public static CurrentAndPendingSupportEntity convertCurrentAndPendingSupportDtoToEntity(
            UploadableProposalSection uploadableProposalSection, Long propPersId, Long propRevId) {
        LOGGER.debug("ConverterUtility.convertCurrentAndPendingSupportDtoToEntity()");

        CurrentAndPendingSupportEntity cpsEntity = new CurrentAndPendingSupportEntity();
        cpsEntity.setPropPersId(propPersId);
        cpsEntity.setPropPrepRevnId(propRevId);
        cpsEntity.setCurrPendSuppDocfileLoc(uploadableProposalSection.getFilePath());
        cpsEntity.setCurrPendSuppDocPageCount(uploadableProposalSection.getPageCount());
        cpsEntity.setCurrPendSuppText("");
        cpsEntity.setCurrPendSuppOrigFileName(uploadableProposalSection.getOrigFileName());
        setAuditFields(cpsEntity);
        cpsEntity.setLastUpdtUser(uploadableProposalSection.getLastUpdatedUser());

        return cpsEntity;
    }

    public static BiographicalSketchEntity getUpdatedBiographicalSketchEntity(Long propPersId, Long propRevId) {
        LOGGER.debug("ConverterUtility.getUpdatedBiographicalSketchEntity()");

        BiographicalSketchEntity bioSketchEntity = new BiographicalSketchEntity();
        bioSketchEntity.setPropPersId(propPersId);
        bioSketchEntity.setPropPrepRevnId(propRevId);
        bioSketchEntity.setBioSketchDocfileLoc("");
        bioSketchEntity.setBioSketchDocpageCount(0);
        bioSketchEntity.setBioSketchText("");
        bioSketchEntity.setBioSketchOrigFileName("");
        setAuditFields(bioSketchEntity);

        return bioSketchEntity;
    }

    /**
     * @param senorPersonnel
     * @return ProposalPersonsEntity
     * @throws CommonUtilException
     */
    public static ProposalPersonsEntity convertSeniorPersonnelDtoToEntity(Personnel seniorPersonnel)
            throws CommonUtilException {
        LOGGER.debug("ConverterUtility.convertSeniorPersonnelDtoToEntity()");

        ProposalPersonsEntity proposalPersonsEntity = new ProposalPersonsEntity();

        try {

            proposalPersonsEntity.setOthrSeniorPersonFirstName(seniorPersonnel.getFirstName());
            proposalPersonsEntity.setOthrSeniorPersonLasttName(seniorPersonnel.getLastName());

            if (seniorPersonnel.getMiddleName() != null) {
                proposalPersonsEntity.setOthrSeniorPersonMiddleInitial(seniorPersonnel.getMiddleName());
            }

            if (seniorPersonnel.getInstitution() != null) {
                proposalPersonsEntity.setPropPersInstitutionId(seniorPersonnel.getInstitution().getId());
            }

            if (seniorPersonnel.getNsfId() != null) {
                proposalPersonsEntity.setPropPersonNsfId(seniorPersonnel.getNsfId());
            }

            proposalPersonsEntity.setPropPersonRoleCode(seniorPersonnel.getPSMRole().getCode());
            proposalPersonsEntity.setPropPrepRevisionId(Long.valueOf(seniorPersonnel.getPropRevnId()));
            setAuditFields(proposalPersonsEntity);
            proposalPersonsEntity.setLastUpdtUser(seniorPersonnel.getLastUpdatedUser());
        } catch (Exception e) {

            throw new CommonUtilException(e);
        }

        return proposalPersonsEntity;
    }

    /**
     * @param projectSummaryEntity
     * @return ProjectSummary
     */
    public static ProjectSummary convertProjectSummaryEntityToDto(ProjectSummaryEntity projectSummaryEntity) {
        LOGGER.debug("ConverterUtility.convertProjectSummaryEntityToDto()");

        ProjectSummary ps = new ProjectSummary();
        ps.setOverview(ProposalDataUtility.returnNullIfBlank(
                StringEscapeUtils.unescapeHtml(projectSummaryEntity.getProjectSummaryOverViewText())));
        ps.setBrodimpact(ProposalDataUtility.returnNullIfBlank(
                StringEscapeUtils.unescapeHtml(projectSummaryEntity.getProjectSummaryBroderImpactText())));
        ps.setIntellmerit(ProposalDataUtility.returnNullIfBlank(
                StringEscapeUtils.unescapeHtml(projectSummaryEntity.getProjectSummaryIntellectualMeritText())));
        ps.setFilePath(ProposalDataUtility.returnNullIfBlank(projectSummaryEntity.getProjectSummaryDocFileLocation()));
        ps.setOrigFileName(ProposalDataUtility.returnNullIfBlank(projectSummaryEntity.getProjectSummaryOrigFileName()));
        ps.setPageCount(projectSummaryEntity.getProjectSummaryDocPageCount());
        ps.setLastUpdatedTmsp(projectSummaryEntity.getLastUpdtTmsp());

        return ps;
    }

    /**
     * @param referenceCitedEntity
     * @return ReferencesCitedEntity
     */
    public static ReferencesCited convertReferenceCitedEntityToDto(ReferencesCitedEntity referenceCitedEntity) {
        LOGGER.debug("ConverterUtility.convertReferenceCitedEntityToDto()");
        ReferencesCited rc = new ReferencesCited();
        rc.setRefCitedText(ProposalDataUtility.returnNullIfBlank(referenceCitedEntity.getRefCitnText()));
        rc.setFilePath(ProposalDataUtility.returnNullIfBlank(referenceCitedEntity.getRefCitnDocfileLoc()));
        rc.setOrigFileName(ProposalDataUtility.returnNullIfBlank(referenceCitedEntity.getRefCitnOrigFileName()));
        rc.setPageCount(referenceCitedEntity.getRefCitnDocpageCount());
        rc.setLastUpdatedTmsp(referenceCitedEntity.getLastUpdtTmsp());

        return rc;
    }

    public static BudgetImpact convertToBudgetImpact(BudgetImpactEntity bImpactEntity) {
        LOGGER.debug("ConverterUtility.convertBudgetImpact()");
        BudgetImpact bImpact = new BudgetImpact();
        bImpact.setFilePath(ProposalDataUtility.returnNullIfBlank(bImpactEntity.getBudgImpactFileLocation()));
        bImpact.setOrigFileName(ProposalDataUtility.returnNullIfBlank(bImpactEntity.getBudgImpactOrigFileName()));
        bImpact.setPageCount(bImpactEntity.getBudgImpactDocPageCount());
        bImpact.setLastUpdatedTmsp(bImpactEntity.getLastUpdtTmsp());
        bImpact.setCreateDate(bImpactEntity.getCreatedDate());
        bImpact.setCreateUser(bImpactEntity.getCreatedUser());
        return bImpact;
    }

    /**
     * This method converts FacilityEquipmentEntity to DTO
     *
     * @param facEqupEntity
     * @return FacilitiesEquipment
     */
    public static FacilitiesEquipment convertFacilitiesEquipmentEntityToDto(FacilityEquipmentEntity facEqupEntity) {
        LOGGER.debug("ConverterUtility.convertFacilitiesEquipmentEntityToDto()");

        FacilitiesEquipment facEquip = new FacilitiesEquipment();
        facEquip.setFacEquipText(ProposalDataUtility.returnNullIfBlank(facEqupEntity.getFacltEquipText()));
        facEquip.setFilePath(ProposalDataUtility.returnNullIfBlank(facEqupEntity.getFacltEquipDocFileLocation()));
        facEquip.setOrigFileName(ProposalDataUtility.returnNullIfBlank(facEqupEntity.getFacltEquipOrgFileName()));
        facEquip.setPageCount(facEqupEntity.getFacltEquipDocPageCount());
        facEquip.setLastUpdatedTmsp(facEqupEntity.getLastUpdtTmsp());
        return facEquip;
    }

    /**
     *
     * @param proposalPersonsEntity
     * @return
     */
    public static Personnel convertSeniorPersonnelEntityToDto(ProposalPersonsEntity proposalPersonsEntity) {
        LOGGER.debug("ConverterUtility.convertSeniorPersonnelEntityToDto()");
        Personnel seniorPersonnel = new Personnel();
        Institution institution = new Institution();
        PSMRole role = new PSMRole();
        seniorPersonnel.setFirstName(proposalPersonsEntity.getOthrSeniorPersonFirstName());
        seniorPersonnel.setLastName(proposalPersonsEntity.getOthrSeniorPersonLasttName());
        seniorPersonnel.setMiddleName(proposalPersonsEntity.getOthrSeniorPersonMiddleInitial());
        role.setCode(proposalPersonsEntity.getPropPersonRoleCode());
        institution.setId(proposalPersonsEntity.getPropPersInstitutionId());
        seniorPersonnel.setInstitution(institution);
        seniorPersonnel.setPSMRole(role);
        if (proposalPersonsEntity.getPropPersonNsfId() != null) {
            seniorPersonnel.setNsfId(proposalPersonsEntity.getPropPersonNsfId());
        }
        seniorPersonnel.setPropRevnId(String.valueOf(proposalPersonsEntity.getPropPrepRevisionId()));
        seniorPersonnel.setPropPersId(String.valueOf(proposalPersonsEntity.getProposalPersonId()));
        seniorPersonnel.setLastUpdatedTmsp(proposalPersonsEntity.getLastUpdtTmsp());
        /* PI-CoPI Demographic Information */
        seniorPersonnel.setEmail(proposalPersonsEntity.getEmailAddr());
        seniorPersonnel.setPhoneNumber(proposalPersonsEntity.getPhoneNumber());
        seniorPersonnel.setFaxNumber(proposalPersonsEntity.getFaxNumber());
        seniorPersonnel.setAcadDegree(proposalPersonsEntity.getAcadDegree());
        seniorPersonnel.setAcadYear(proposalPersonsEntity.getAcadYear());
        seniorPersonnel.setInstNameAtSubmission(proposalPersonsEntity.getInstNameAtSubmission());
        seniorPersonnel.setDeptName(proposalPersonsEntity.getDeptName());
        seniorPersonnel.setAddrLine1(proposalPersonsEntity.getAddrLine1());
        seniorPersonnel.setAddrLine2(proposalPersonsEntity.getAddrLine2());
        seniorPersonnel.setCityName(proposalPersonsEntity.getCityName());
        seniorPersonnel.setStateCode(proposalPersonsEntity.getStateCode());
        seniorPersonnel.setCountryCode(proposalPersonsEntity.getCountryCode());
        seniorPersonnel.setPostalCode(proposalPersonsEntity.getPostalCode());

        return seniorPersonnel;
    }

    /**
     * This method converts ProjectDescriptionEntity to DTO
     *
     * @param facEqupEntity
     * @return ProjectDescription
     */
    public static ProjectDescription convertProjectDescriptionEntityToDto(ProjectDescriptionEntity projDescEntity) {
        LOGGER.debug("ConverterUtility.convertProjectDescriptionEntityToDto()");

        ProjectDescription projDesc = new ProjectDescription();
        projDesc.setProjDescText(ProposalDataUtility.returnNullIfBlank(projDescEntity.getProjDescDocText()));
        projDesc.setFilePath(ProposalDataUtility.returnNullIfBlank(projDescEntity.getProjDescFileLocation()));
        projDesc.setOrigFileName(ProposalDataUtility.returnNullIfBlank(projDescEntity.getProjDescDocOrigFileName()));
        projDesc.setPageCount(projDescEntity.getProjDescDocPageCount());
        projDesc.setLastUpdatedTmsp(projDescEntity.getLastUpdtTmsp());

        return projDesc;
    }

    /**
     * This method converts ProjectDescriptionEntity to DTO
     *
     * @param facEqupEntity
     * @return ProjectDescription
     */
    public static BudgetJustification convertBudgetJustificationEntityToDto(BudgetJustificationEntity budgJustEntity) {
        LOGGER.debug("ConverterUtility.convertBudgetJustificationEntityToDto()");

        BudgetJustification budgJust = new BudgetJustification();
        budgJust.setProjDescText(ProposalDataUtility.returnNullIfBlank(budgJustEntity.getBudgJustText()));
        budgJust.setFilePath(ProposalDataUtility.returnNullIfBlank(budgJustEntity.getBudgJustFileLocation()));
        budgJust.setOrigFileName(ProposalDataUtility.returnNullIfBlank(budgJustEntity.getBudgJustOrigFileName()));
        budgJust.setPageCount(budgJustEntity.getBudgJustDocPageCount());
        budgJust.setLastUpdatedTmsp(budgJustEntity.getLastUpdtTmsp());

        return budgJust;
    }

    /**
     * This method converts BiographicalSketchEntity to DTO
     *
     * @param facEqupEntity
     * @return ProjectDescription
     */
    public static BiographicalSketch convertBiographicalSketchEntityToDto(
            BiographicalSketchEntity biographicalSketchEntity) {
        LOGGER.debug("ConverterUtility.convertBiographicalSketchEntityToDto()");

        BiographicalSketch biographicalSketch = new BiographicalSketch();
        biographicalSketch
                .setBioSketchText(ProposalDataUtility.returnNullIfBlank(biographicalSketchEntity.getBioSketchText()));
        biographicalSketch
                .setFilePath(ProposalDataUtility.returnNullIfBlank(biographicalSketchEntity.getBioSketchDocfileLoc()));
        biographicalSketch.setOrigFileName(
                ProposalDataUtility.returnNullIfBlank(biographicalSketchEntity.getBioSketchOrigFileName()));
        biographicalSketch.setPageCount(biographicalSketchEntity.getBioSketchDocpageCount());
        biographicalSketch.setLastUpdatedTmsp(biographicalSketchEntity.getLastUpdtTmsp());
        if (biographicalSketchEntity.getPerson() != null) {
            biographicalSketch.setPerson(convertSeniorPersonnelEntityToDto(biographicalSketchEntity.getPerson()));
        }

        return biographicalSketch;
    }

    /**
     * This method converts CurrentAndPendingSupport to DTO
     *
     * @param cpsEntity
     * @return
     */

    public static CurrentAndPendingSupport convertCurrentAndPendingSupportEntityToDto(
            CurrentAndPendingSupportEntity cpsEntity) {
        LOGGER.debug("ConverterUtility.convertCurrentAndPendingSupportEntityToDto()");

        CurrentAndPendingSupport cps = new CurrentAndPendingSupport();
        cps.setCurrPendSuppText(ProposalDataUtility.returnNullIfBlank(cpsEntity.getCurrPendSuppText()));
        cps.setFilePath(ProposalDataUtility.returnNullIfBlank(cpsEntity.getCurrPendSuppDocfileLoc()));
        cps.setOrigFileName(ProposalDataUtility.returnNullIfBlank(cpsEntity.getCurrPendSuppOrigFileName()));
        cps.setPageCount(cpsEntity.getCurrPendSuppDocPageCount());
        cps.setLastUpdatedTmsp(cpsEntity.getLastUpdtTmsp());
        if (cpsEntity.getPerson() != null) {
            cps.setPerson(convertSeniorPersonnelEntityToDto(cpsEntity.getPerson()));
        }

        return cps;
    }

    /**
     * @param instBudget
     * @return BudgetInstitutionEntity
     */
    public static BudgetInstitutionEntity convertInstitutionBudgetDtoToEntity(InstitutionBudget instBudget) {
        LOGGER.debug("ConverterUtility.convertInstitutionBudgetDtoToEntity()");
        BudgetInstitutionEntity budgetInstitutionEntity = new BudgetInstitutionEntity();
        budgetInstitutionEntity.setInstId(instBudget.getInstId());
        budgetInstitutionEntity.setPropRevId(Long.valueOf(instBudget.getPropRevId()));
        budgetInstitutionEntity.setInstPropRoleTypeCode(instBudget.getInstPropRoleTypeCode());
        setAuditFields(budgetInstitutionEntity);
        budgetInstitutionEntity.setLastUpdtUser(instBudget.getLastUpdatedUser());
        return budgetInstitutionEntity;
    }

    /**
     * This Method converts the FringeBenefitCost DTO to
     * FringeBenefitBudgetEntity.
     *
     * @param instRecId
     * @param budgetRec
     * @return FringeBenefitBudgetEntity
     */
    public static FringeBenefitBudgetEntity convertFringeBenefitCostDtoToEntity(Long instRecId,
            BudgetRecord budgetRec) {
        LOGGER.debug("ConverterUtility.convertFringeBenefitCostDtoToEntity()");

        FringeBenefitBudgetEntity fringeBudgetEntity = new FringeBenefitBudgetEntity();
        FringeBenefitCost fbCost = budgetRec.getFringeBenefitCost();

        fringeBudgetEntity.setPropInstRecId(instRecId);
        fringeBudgetEntity.setBudgetYear(budgetRec.getBudgetYear());
        if (fbCost != null) {
            fringeBudgetEntity.setFringeBenefitDollarAmount(
                    BigDecimalUtil.ifNullReturnZero(fbCost.getFringeBenefitDollarAmount()));
            fringeBudgetEntity.setFringeBenefitBudgJustification(fbCost.getFringeBenefitBudgJustification());
        } else {
            fringeBudgetEntity.setFringeBenefitDollarAmount(BigDecimal.valueOf(0));
        }
        setAuditFields(fringeBudgetEntity);
        fringeBudgetEntity.setLastUpdtUser(budgetRec.getLastUpdatedUser());

        return fringeBudgetEntity;
    }

    /**
     * This Method converts the OtherPersonnelCost DTO to
     * OtherPersonsBudgetCostEntity.
     *
     * @param instRecId
     * @param budgetYear
     * @param op
     * @return OtherPersonsBudgetCostEntity
     */
    public static OtherPersonsBudgetCostEntity convertOtherPersonBudgetDtoToEntity(Long instRecId, int budgetYear,
            OtherPersonnelCost op) {
        LOGGER.debug("ConverterUtility.convertOtherPersonBudgetDtoToEntity()");

        OtherPersonsBudgetCostEntity otherPersonsBudgetCostEntity = new OtherPersonsBudgetCostEntity();
        otherPersonsBudgetCostEntity.setPropInstRecId(instRecId);
        otherPersonsBudgetCostEntity.setOtherPersonTypeCode(op.getOtherPersonTypeCode());
        otherPersonsBudgetCostEntity.setBudgetYear(budgetYear);
        otherPersonsBudgetCostEntity.setOtherPersonCount(op.getOtherPersonCount());
        otherPersonsBudgetCostEntity.setOtherPersonMonthCount(op.getOtherPersonMonthCount());
        otherPersonsBudgetCostEntity
                .setOtherPersonDollarAmount(BigDecimalUtil.ifNullReturnZero(op.getOtherPersonDollarAmount()));
        setAuditFields(otherPersonsBudgetCostEntity);
        otherPersonsBudgetCostEntity.setLastUpdtUser(op.getLastUpdatedUser());
        return otherPersonsBudgetCostEntity;
    }

    /**
     * This Method converts the SeniorPersonnelCost DTO to
     * ProposalPersonsEntity.
     *
     * @param prModel
     * @param propPrepRevisionId
     * @return ProposalPersonsEntity
     */
    public static ProposalPersonsEntity convertProposalPersonsEntityDtotoEntity(SeniorPersonnelCost seniorPersonnel,
            Long propPrepRevisionId) {
        LOGGER.debug("ConverterUtility.convertProposalPersonsEntityDtotoEntity()");
        ProposalPersonsEntity proposalPersonsEntity = new ProposalPersonsEntity();
        proposalPersonsEntity.setPropPrepRevisionId(propPrepRevisionId);
        proposalPersonsEntity.setPropPersonNsfId(seniorPersonnel.getSeniorPersonNsfId());
        proposalPersonsEntity.setPropPersInstitutionId(seniorPersonnel.getSeniorPersonInstId());
        proposalPersonsEntity.setPropPersonRoleCode(seniorPersonnel.getSeniorPersonRoleCode());
        proposalPersonsEntity.setOthrSeniorPersonFirstName(seniorPersonnel.getSeniorPersonFirstName());
        proposalPersonsEntity.setOthrSeniorPersonLasttName(seniorPersonnel.getSeniorPersonLastName());
        proposalPersonsEntity.setOthrSeniorPersonMiddleInitial(seniorPersonnel.getSeniorPersonMiddleInitial());
        setAuditFields(proposalPersonsEntity);
        return proposalPersonsEntity;
    }

    /**
     * This Method converts the SeniorPersonnelCost DTO to
     * SeniorPersonsBudgetCostEntity.
     *
     * @param seniorPersonnel
     * @param instRecId
     *            * @param propPersonId
     * @return SeniorPersonsBudgetCostEntity
     */
    public static SeniorPersonsBudgetCostEntity convertSeniorPersonsBudgetCostEntityDtoToEntity(
            SeniorPersonnelCost seniorPersonnel, Long instRecId, int budgetYear) {
        LOGGER.debug("ConverterUtility.convertSeniorPersonsBudgetCostEntityDtoToEntity()");

        SeniorPersonsBudgetCostEntity srPrBudget = new SeniorPersonsBudgetCostEntity();
        srPrBudget.setPropInstRecId(instRecId);
        srPrBudget.setPropPersonId(Long.valueOf(seniorPersonnel.getPropPersId()));
        srPrBudget.setSeniorPersonDollarAmount(
                BigDecimalUtil.ifNullReturnZero(seniorPersonnel.getSeniorPersonDollarAmount()));
        srPrBudget.setSeniorPersonMonthCount(seniorPersonnel.getSeniorPersonMonthCount());
        srPrBudget.setSeniorPersonJustificationText(seniorPersonnel.getSeniorPersonJustificationText());
        srPrBudget.setBudgetYear(budgetYear);
        setAuditFields(srPrBudget);
        srPrBudget.setLastUpdtUser(seniorPersonnel.getLastUpdatedUser());
        srPrBudget.setSeniorPersonnelFlag(seniorPersonnel.isHidden());
        return srPrBudget;
    }

    /**
     * This method convert EquipmentBudgetCost DTO to EquipmentBudgetCostEntity
     *
     * @param equipmentCost
     * @param propInstRectId
     * @param budgetYear
     * @return EquipmentBudgetCostEntity
     */
    public static EquipmentBudgetCostEntity convertEquipmentBCDtotoEntity(EquipmentCost equipmentCost,
            Long propInstRectId, int budgetYear) {
        LOGGER.debug("ConverterUtility.convertEquipmentBCDtotoEntity()");

        EquipmentBudgetCostEntity equipBCEntity = new EquipmentBudgetCostEntity();
        equipBCEntity.setBudgetYear(budgetYear);
        equipBCEntity.setPropInstRecId(propInstRectId);
        equipBCEntity.setEquipmentName(equipmentCost.getEquipmentName().trim());
        equipBCEntity
                .setEquipmentDollarAmount(BigDecimalUtil.ifNullReturnZero(equipmentCost.getEquipmentDollarAmount()));
        equipBCEntity.setEquipmentCostBudgetJsutificationText(equipmentCost.getEquipmentCostBudgetJsutificationText());
        setAuditFields(equipBCEntity);
        equipBCEntity.setLastUpdtUser(equipmentCost.getLastUpdatedUser());
        return equipBCEntity;
    }

    /**
     * This method convert TravelBudgetCost DTO to TravelBudgetCostEntity
     *
     * @param tbCost
     * @param propInstRectId
     * @param budgetYear
     * @return TravelBudgetCostEntity
     */
    public static TravelBudgetCostEntity convertTBCostDtoToTBCostEntity(TravelCost tbCost, Long propInstRectId,
            int budgetYear) {
        LOGGER.debug("ConverterUtility.convertTBCostDtoToTBCostEntity()");
        TravelBudgetCostEntity tBCEntity = new TravelBudgetCostEntity();
        tBCEntity.setPropInstRecId(propInstRectId);
        tBCEntity.setBudgetYear(budgetYear);
        tBCEntity
                .setDomesticTravelDollarAmount(BigDecimalUtil.ifNullReturnZero(tbCost.getDomesticTravelDollarAmount()));
        tBCEntity.setForeignTravelDollarAmount(BigDecimalUtil.ifNullReturnZero(tbCost.getForeignTravelDollarAmount()));
        tBCEntity.setTravelCostBudgetJustificationText(tbCost.getTravelCostBudgetJustificationText());
        setAuditFields(tBCEntity);
        tBCEntity.setLastUpdtUser(tbCost.getLastUpdatedUser());
        return tBCEntity;
    }

    /**
     * This method convert ParticipantSupportCost DTO to
     * ParticipantsSupportCostEntity
     *
     * @param psCost
     * @param propInstRectId
     * @param budgetYear
     * @return ParticipantsSupportCostEntity
     */
    public static ParticipantsSupportCostEntity convertPartSCDtoToPartSuppCostEntity(ParticipantSupportCost psCost,
            Long propInstRectId, int budgetYear) {
        LOGGER.debug("ConverterUtility.convertPartSCDtoToPartSuppCostEntity()");
        ParticipantsSupportCostEntity psEntity = new ParticipantsSupportCostEntity();
        psEntity.setPropInstRecId(propInstRectId);
        psEntity.setBudgetYear(budgetYear);
        psEntity.setParticipantNumberCount(psCost.getPartNumberCount());
        psEntity.setStipendDollarAmout(BigDecimalUtil.ifNullReturnZero(psCost.getStipendDollarAmount()));
        psEntity.setSubsistenceDollarAmount(BigDecimalUtil.ifNullReturnZero(psCost.getSubsistenceDollarAmount()));
        psEntity.setTravelDollarAmount(BigDecimalUtil.ifNullReturnZero(psCost.getTravelDollarAmount()));
        psEntity.setOtherDollarAmount(BigDecimalUtil.ifNullReturnZero(psCost.getOtherDollarAmount()));
        psEntity.setParticipantSupportJustificationText(psCost.getParticipantSupportJustificationText());
        setAuditFields(psEntity);
        psEntity.setLastUpdtUser(psCost.getLastUpdatedUser());
        return psEntity;
    }

    /**
     * This method convert OtherDirectCost DTO to OtherDirectCostEntity
     *
     * @param othDC
     * @param propInstRectId
     * @param budgetYear
     * @return OtherDirectCostEntity
     */
    public static OtherDirectCostEntity convertOthDCDtoToOthBCEntity(OtherDirectCost othDC, Long propInstRectId,
            int budgetYear) {

        LOGGER.debug("ConverterUtility.convertOthDCDtoToOthBCEntity()");
        OtherDirectCostEntity othDCEntity = new OtherDirectCostEntity();
        othDCEntity.setPropInstRecId(propInstRectId);
        othDCEntity.setBudgetYear(budgetYear);
        othDCEntity.setMaterialsDollarAmount(BigDecimalUtil.ifNullReturnZero(othDC.getMaterialsDollarAmount()));
        othDCEntity.setPublicationDollarAmount(BigDecimalUtil.ifNullReturnZero(othDC.getPublicationDollarAmount()));
        othDCEntity.setConsultantServicesDollarAmount(
                BigDecimalUtil.ifNullReturnZero(othDC.getConsultantServicesDollarAmount()));
        othDCEntity.setComputerServicesDollarAmount(
                BigDecimalUtil.ifNullReturnZero(othDC.getComputerServicesDollarAmount()));
        othDCEntity.setSubContractDollarAmount(BigDecimalUtil.ifNullReturnZero(othDC.getSubContractDollarAmount()));
        othDCEntity.setOtherDirectCostDollarAmount(
                BigDecimalUtil.ifNullReturnZero(othDC.getOtherDirectCostDollarAmount()));
        othDCEntity.setOtherDirectCostBudgetJustificationText(othDC.getOtherDirectCostBudgetJustificationText());
        setAuditFields(othDCEntity);
        othDCEntity.setLastUpdtUser(othDC.getLastUpdatedUser());
        return othDCEntity;
    }

    /**
     * This method convert IndirectCost DTO to IndirectBudgetCostEntity
     *
     * @param idrCost
     * @param propInstRectId
     * @param budgetYear
     * @return IndirectBudgetCostEntity
     */
    public static IndirectBudgetCostEntity convertIdrBCDtoToIndirBCEntity(IndirectCost idrCost, Long propInstRectId,
            int budgetYear) {
        LOGGER.debug("ConverterUtility.convertIdrBCDtoToIndirBCEntity()");

        IndirectBudgetCostEntity idrBCEntity = new IndirectBudgetCostEntity();
        idrBCEntity.setPropInstRecId(propInstRectId);
        idrBCEntity.setBudgetYear(budgetYear);
        idrBCEntity.setIndirectCostItemName(idrCost.getIndirectCostItemName().trim());
        idrBCEntity.setIndirectCostRate(idrCost.getIndirectCostRate());
        idrBCEntity.setIndirectCostBaseDollarAmount(idrCost.getIndirectCostBaseDollarAmount());
        idrBCEntity.setIndirectCostBudgetJustificationText(idrCost.getIndirectCostBudgetJustificationText());
        setAuditFields(idrBCEntity);
        idrBCEntity.setLastUpdtUser(idrCost.getLastUpdatedUser());
        return idrBCEntity;
    }

    /**
     * Extracts the directorate code from a division code (i.e., "08001000" =>
     * "08000000")
     *
     * @param dirCode
     * @return String
     */
    public static String extractDirectorateCodeFromDivisionCode(String dirCode) {
        String dirCodePrefix = dirCode.substring(0, 2);
        return String.format("%1$-8s", dirCodePrefix).replace(' ', '0');
    }

    public static InstitutionBudget convertBudgetEntitiestoDto(String propPrepId, InstitutionBudgetParameter param) {
        LOGGER.debug("ConverterUtility.convertBudgetEntitiestoDto()");

        BudgetInstitutionEntity bie = param.getBie();
        List<SeniorPersonsBudgetCostEntity> spbceList = param.getSpbceList();
        List<OtherPersonsBudgetCostEntity> opbceList = param.getOpbceList();
        List<FringeBenefitBudgetEntity> fbbeList = param.getFbbeList();
        List<EquipmentBudgetCostEntity> ebceList = param.getEbceList();
        List<TravelBudgetCostEntity> tbceList = param.getTbceList();
        List<ParticipantsSupportCostEntity> psceList = param.getPsceList();
        List<OtherDirectCostEntity> odceList = param.getOdceList();
        List<IndirectBudgetCostEntity> ibceList = param.getIbceList();
        List<ProposalPersonsEntity> propPersList = param.getPropPersList();
        InstitutionBudget ibDto = new InstitutionBudget();
        BudgetRecord br = null;
        FringeBenefitCost fbCostDto = null;
        TravelCost travelCostDto = null;
        ParticipantSupportCost participantSCDto = null;
        OtherDirectCost otherDCDto = null;

        List<BudgetRecord> budgetRecordList = new ArrayList<BudgetRecord>();
        List<OtherPersonnelCost> opCostDtoList = new ArrayList<OtherPersonnelCost>();
        List<SeniorPersonnelCost> spCostDtoList = new ArrayList<SeniorPersonnelCost>();
        List<EquipmentCost> eCostDtoList = new ArrayList<EquipmentCost>();
        List<IndirectCost> indirectCostDtoList = new ArrayList<IndirectCost>();

        ibDto.setInstId(bie.getInstId());
        ibDto.setPropInstRecId(bie.getPropInstRecId());
        ibDto.setInstPropRoleTypeCode(bie.getInstPropRoleTypeCode().trim());
        ibDto.setPropRevId(String.valueOf(bie.getPropRevId()));
        ibDto.setPropPrepId(propPrepId);
        ibDto.setLastUpdtTmsp(bie.getLastUpdtTmsp());
        ibDto.setPcvCheckIndicator(bie.isPcvCheckIndicator());

        for (FringeBenefitBudgetEntity fbCostEntity : fbbeList) {

            int year = fbCostEntity.getBudgetYear();
            br = new BudgetRecord();
            br.setBudgetYear(year);

            // Section A
            spCostDtoList = getSeniorPersonnelCost(year, spbceList, propPersList);
            br.setSrPersonnelList(spCostDtoList);
            // Section B
            opCostDtoList = getOtherPersonnelCost(year, opbceList);
            br.setOtherPersonnelList(opCostDtoList);
            // Section C
            fbCostDto = getFringeBenefitCost(fbCostEntity);
            br.setFringeBenefitCost(fbCostDto);
            // Section D
            eCostDtoList = getEquipmentCost(year, ebceList);
            br.setEquipmentList(eCostDtoList);
            // Section E
            travelCostDto = getTravelCost(year, tbceList);
            br.setTravelCost(travelCostDto);
            // Section F
            participantSCDto = getParticipantSupportCost(year, psceList);
            br.setParticipantsSupportCost(participantSCDto);
            // Section G
            otherDCDto = getOtherDirectCost(year, odceList);
            br.setOtherDirectCost(otherDCDto);
            // Section I
            indirectCostDtoList = getIndirectCost(year, ibceList);
            br.setIndirectCostsList(indirectCostDtoList);

            budgetRecordList.add(br);
        }

        if (spbceList.isEmpty()) {

            br = new BudgetRecord();
            br.setBudgetYear(1);

            List<SeniorPersonnelCost> spCostDtolst = createDefaultSeniorPersonnelCost(propPersList);
            br.setSrPersonnelList(spCostDtolst);
            budgetRecordList.add(br);
        }

        ibDto.setBudgetRecordList(budgetRecordList);

        return ibDto;
    }

    public static List<IndirectCost> getIndirectCost(int year, List<IndirectBudgetCostEntity> ibceList) {

        LOGGER.debug("ConverterUtility.getIndirectCost()");

        List<IndirectCost> ibcList = new ArrayList<IndirectCost>();
        IndirectCost indirectCostDto = null;

        for (IndirectBudgetCostEntity ibce : ibceList) {
            if (ibce.getBudgetYear() == year) {
                indirectCostDto = new IndirectCost();
                indirectCostDto.setIndirectCostBaseDollarAmount(ibce.getIndirectCostBaseDollarAmount());
                indirectCostDto.setIndirectCostBudgetJustificationText(ibce.getIndirectCostBudgetJustificationText());
                indirectCostDto.setIndirectCostItemName(ibce.getIndirectCostItemName());
                indirectCostDto.setIndirectCostRate(ibce.getIndirectCostRate());
                ibcList.add(indirectCostDto);
            }
        }

        return ibcList;

    }

    public static FringeBenefitCost getFringeBenefitCost(FringeBenefitBudgetEntity fbCostEntity) {
        LOGGER.debug("ConverterUtility.getFringeBenefitCost()");
        FringeBenefitCost fbCostDtoLocal = null;
        fbCostDtoLocal = new FringeBenefitCost();
        fbCostDtoLocal.setFringeBenefitDollarAmount(fbCostEntity.getFringeBenefitDollarAmount());
        fbCostDtoLocal.setFringeBenefitBudgJustification(fbCostEntity.getFringeBenefitBudgJustification());
        return fbCostDtoLocal;

    }

    public static TravelCost getTravelCost(int year, List<TravelBudgetCostEntity> tbceList) {

        LOGGER.debug("ConverterUtility.getTravelCost()");

        TravelCost travelCostDto = null;

        for (TravelBudgetCostEntity tbce : tbceList) {
            if (tbce.getBudgetYear() == year) {
                travelCostDto = new TravelCost();
                travelCostDto.setDomesticTravelDollarAmount(tbce.getDomesticTravelDollarAmount());
                travelCostDto.setForeignTravelDollarAmount(tbce.getForeignTravelDollarAmount());
                travelCostDto.setTravelCostBudgetJustificationText(tbce.getTravelCostBudgetJustificationText());
            }
        }

        return travelCostDto;

    }

    public static OtherDirectCost getOtherDirectCost(int year, List<OtherDirectCostEntity> odceList) {
        LOGGER.debug("ConverterUtility.getOtherDirectCost()");

        OtherDirectCost otherDCDto = null;

        for (OtherDirectCostEntity odce : odceList) {
            if (odce.getBudgetYear() == year) {
                otherDCDto = new OtherDirectCost();
                otherDCDto.setComputerServicesDollarAmount(odce.getComputerServicesDollarAmount());
                otherDCDto.setConsultantServicesDollarAmount(odce.getConsultantServicesDollarAmount());
                otherDCDto.setMaterialsDollarAmount(odce.getMaterialsDollarAmount());
                otherDCDto.setOtherDirectCostBudgetJustificationText(odce.getOtherDirectCostBudgetJustificationText());
                otherDCDto.setOtherDirectCostDollarAmount(odce.getOtherDirectCostDollarAmount());
                otherDCDto.setPublicationDollarAmount(odce.getPublicationDollarAmount());
                otherDCDto.setSubContractDollarAmount(odce.getSubContractDollarAmount());

            }
        }

        return otherDCDto;

    }

    public static ParticipantSupportCost getParticipantSupportCost(int year,
            List<ParticipantsSupportCostEntity> psceList) {
        LOGGER.debug("ConverterUtility.getParticipantSupportCost()");

        ParticipantSupportCost participantSCDto = null;

        for (ParticipantsSupportCostEntity psce : psceList) {
            if (psce.getBudgetYear() == year) {
                participantSCDto = new ParticipantSupportCost();
                participantSCDto.setOtherDollarAmount(psce.getOtherDollarAmount());
                participantSCDto.setParticipantSupportJustificationText(psce.getParticipantSupportJustificationText());
                participantSCDto.setPartNumberCount(psce.getParticipantNumberCount());
                participantSCDto.setStipendDollarAmount(psce.getStipendDollarAmout());
                participantSCDto.setSubsistenceDollarAmount(psce.getSubsistenceDollarAmount());
                participantSCDto.setTravelDollarAmount(psce.getTravelDollarAmount());

            }
        }

        return participantSCDto;

    }

    public static List<EquipmentCost> getEquipmentCost(int year, List<EquipmentBudgetCostEntity> ebceList) {
        LOGGER.debug("ConverterUtility.getEquipmentCost()");

        List<EquipmentCost> ecList = new ArrayList<EquipmentCost>();
        EquipmentCost ecDto = null;

        for (EquipmentBudgetCostEntity ebce : ebceList) {
            if (ebce.getBudgetYear() == year) {
                ecDto = new EquipmentCost();
                ecDto.setEquipmentName(ebce.getEquipmentName());
                ecDto.setEquipmentDollarAmount(ebce.getEquipmentDollarAmount());
                ecDto.setEquipmentCostBudgetJsutificationText(ebce.getEquipmentCostBudgetJsutificationText());
                ecList.add(ecDto);
            }
        }

        return ecList;

    }

    public static List<SeniorPersonnelCost> getSeniorPersonnelCost(int year,
            List<SeniorPersonsBudgetCostEntity> spbceList, List<ProposalPersonsEntity> propPersList) {
        LOGGER.debug("ConverterUtility.getSeniorPersonnelCost()");

        List<SeniorPersonnelCost> spCostDtoList = new ArrayList<SeniorPersonnelCost>();
        SeniorPersonnelCost spCostDto = null;

        for (SeniorPersonsBudgetCostEntity spCostEntity : spbceList) {
            if (spCostEntity.getBudgetYear() == year) {
                spCostDto = new SeniorPersonnelCost();
                spCostDto.setSeniorPersonDollarAmount(spCostEntity.getSeniorPersonDollarAmount());
                spCostDto.setSeniorPersonMonthCount(spCostEntity.getSeniorPersonMonthCount());
                SeniorPersonnelCost sc = getSeniorPersonnelInfo(spCostEntity.getPropPersonId(), propPersList);
                spCostDto.setSeniorPersonFirstName(sc.getSeniorPersonFirstName());
                spCostDto.setSeniorPersonLastName(sc.getSeniorPersonLastName());
                spCostDto.setSeniorPersonMiddleInitial(sc.getSeniorPersonMiddleInitial());
                spCostDto.setSeniorPersonNsfId(sc.getSeniorPersonNsfId());
                spCostDto.setSeniorPersonInstId(sc.getSeniorPersonInstId());
                spCostDto.setSeniorPersonRoleCode(sc.getSeniorPersonRoleCode());
                spCostDto.setHidden(spCostEntity.isSeniorPersonnelFlag());
                spCostDto.setPropPersId(String.valueOf(spCostEntity.getPropPersonId()));
                spCostDto.setSeniorPersonJustificationText(spCostEntity.getSeniorPersonJustificationText());
                spCostDtoList.add(spCostDto);
            }
        }
        if (!spCostDtoList.isEmpty()) {
            try {
                spCostDtoList.sort(Comparator.comparing(SeniorPersonnelCost::getSeniorPersonRoleCode)
                        .thenComparing(SeniorPersonnelCost::getSeniorPersonLastName, String.CASE_INSENSITIVE_ORDER));
            } catch (NullPointerException e) {
                LOGGER.debug("ConverterUtility.getSeniorPersonnelCost(): List cannot be sorted"+e);
            }
        }
        return spCostDtoList;

    }

    public static List<SeniorPersonnelCost> createDefaultSeniorPersonnelCost(List<ProposalPersonsEntity> propPersList) {

        LOGGER.debug("ConverterUtility.createDefaultSeniorPersonnelCost()");

        List<SeniorPersonnelCost> spCostDtoList = new ArrayList<SeniorPersonnelCost>();

        SeniorPersonnelCost spCostDto = null;

        for (ProposalPersonsEntity ppe : propPersList) {

            if (ppe.getPropPersonRoleCode().equals(PSMRole.ROLE_PI)
                    || ppe.getPropPersonRoleCode().equals(PSMRole.ROLE_CO_PI)
                    || ppe.getPropPersonRoleCode().equals(PSMRole.ROLE_OSP)) {

                spCostDto = new SeniorPersonnelCost();
                spCostDto.setSeniorPersonDollarAmount(BigDecimal.valueOf(0));
                spCostDto.setSeniorPersonMonthCount(0);
                spCostDto.setSeniorPersonFirstName(ppe.getOthrSeniorPersonFirstName());
                spCostDto.setSeniorPersonLastName(ppe.getOthrSeniorPersonLasttName());
                spCostDto.setSeniorPersonMiddleInitial(ppe.getOthrSeniorPersonMiddleInitial());
                spCostDto.setSeniorPersonNsfId(ppe.getPropPersonNsfId());
                spCostDto.setSeniorPersonInstId(ppe.getPropPersInstitutionId());
                spCostDto.setSeniorPersonRoleCode(ppe.getPropPersonRoleCode());
                spCostDto.setPropPersId(String.valueOf(ppe.getProposalPersonId()));
                spCostDto.setHidden(false);

                spCostDtoList.add(spCostDto);
            }
        }

        spCostDtoList.sort(Comparator.comparing(SeniorPersonnelCost::getSeniorPersonRoleCode)
                .thenComparing(SeniorPersonnelCost::getSeniorPersonLastName, String.CASE_INSENSITIVE_ORDER));
        return spCostDtoList;

    }

    public static SeniorPersonnelCost getSeniorPersonnelInfo(Long propPersId,
            List<ProposalPersonsEntity> propPersList) {
        LOGGER.debug("ConverterUtility.getSeniorPersonnelInfo()");
        SeniorPersonnelCost sc = new SeniorPersonnelCost();

        for (ProposalPersonsEntity proposalPersonsEntity : propPersList) {
            if (proposalPersonsEntity.getProposalPersonId().equals(propPersId)) {
                sc.setSeniorPersonFirstName(proposalPersonsEntity.getOthrSeniorPersonFirstName());
                sc.setSeniorPersonLastName(proposalPersonsEntity.getOthrSeniorPersonLasttName());
                sc.setSeniorPersonMiddleInitial(proposalPersonsEntity.getOthrSeniorPersonMiddleInitial());
                sc.setSeniorPersonNsfId(proposalPersonsEntity.getPropPersonNsfId());
                sc.setSeniorPersonInstId(proposalPersonsEntity.getPropPersInstitutionId());
                sc.setSeniorPersonRoleCode(proposalPersonsEntity.getPropPersonRoleCode().trim());
                sc.setPropPersId(String.valueOf(proposalPersonsEntity.getProposalPersonId()));
            }
        }

        return sc;

    }

    public static List<OtherPersonnelCost> getOtherPersonnelCost(int year,
            List<OtherPersonsBudgetCostEntity> opCostEntityList) {
        LOGGER.debug("ConverterUtility.getOtherPersonnelCost()");

        List<OtherPersonnelCost> opCostDtoList = new ArrayList<OtherPersonnelCost>();
        OtherPersonnelCost opCostDto = null;

        for (OtherPersonsBudgetCostEntity opCostEntity : opCostEntityList) {
            if (opCostEntity.getBudgetYear() == year) {
                opCostDto = new OtherPersonnelCost();
                opCostDto.setOtherPersonCount(opCostEntity.getOtherPersonCount());
                opCostDto.setOtherPersonDollarAmount(opCostEntity.getOtherPersonDollarAmount());
                opCostDto.setOtherPersonMonthCount(opCostEntity.getOtherPersonMonthCount());
                opCostDto.setOtherPersonTypeCode(opCostEntity.getOtherPersonTypeCode().trim());
                opCostDtoList.add(opCostDto);
            }

        }

        return opCostDtoList;
    }

    /**
     * This method converts ProposalMessage to ProposalMessageEntity.
     *
     * @param pMsg
     * @return
     */
    public static ProposalMessageEntity getProposalMessageEntity(int propPrepId, int propRevnId, String propPersId,
            String sectionType, PSMMessage pMsg) {
        LOGGER.debug("ConverterUtility.getProposalMessageEntity()");
        ProposalMessageEntity pe = new ProposalMessageEntity();
        pe.setPropPrepId(propPrepId);
        pe.setPropRevnId(propRevnId);
        if (!"persId".equalsIgnoreCase(propPersId)) {
            pe.setPropPersId(Integer.parseInt(propPersId));
        }
        pe.setPropSectionType(sectionType);
        pe.setPsmMsgId(pMsg.getId());
        pe.setPsmMsgLevel(pMsg.getType().getCode());
        pe.setPsmMsgText(pMsg.getDescription());
        setAuditFields(pe);
        pe.setLastUpdtUser(pMsg.getLastUpdatedUser());
        return pe;
    }

    /**
     * This Method returns Model Object for a given entity.
     *
     * @param entity
     * @return Model Object
     */
    public static OthrPersBioInfo getOthrPersBioInfoDTO(OthrPersBioInfoEntity entity) {
        LOGGER.debug("ConverterUtility.getOthrPersBioInfoDTO()");
        OthrPersBioInfo othBioInfo = new OthrPersBioInfo();
        othBioInfo.setFilePath(ProposalDataUtility.returnNullIfBlank(entity.getOthrPersBioInfoFileLocation()));
        othBioInfo.setOrigFileName(ProposalDataUtility.returnNullIfBlank(entity.getOthrPersBioInfoDocOrigFileName()));
        othBioInfo.setOthrPersBioInfoDocText(ProposalDataUtility.returnNullIfBlank(entity.getOthrPersBioInfoDocText()));
        othBioInfo.setPageCount(entity.getOthrPersBioInfoDocPageCount());
        othBioInfo.setLastUpdatedTmsp(entity.getLastUpdtTmsp());
        return othBioInfo;
    }

    /**
     * This Method returns Model Object for a given entity.
     *
     * @param entity
     * @return Model Object
     */
    public static DataManagementPlan getDataManagementPlanDTO(DataManagementPlanEntity entity) {
        LOGGER.debug("ConverterUtility.getDataManagementPlanDTO()");
        DataManagementPlan dmp = new DataManagementPlan();
        dmp.setFilePath(ProposalDataUtility.returnNullIfBlank(entity.getDataManagementPlanFileLocation()));
        dmp.setOrigFileName(ProposalDataUtility.returnNullIfBlank(entity.getDataManagementPlanDocOrigFileName()));
        dmp.setDataManagementPlanDocText(ProposalDataUtility.returnNullIfBlank(entity.getDataManagementPlanDocText()));
        dmp.setPageCount(entity.getDataManagementPlanDocPageCount());
        dmp.setLastUpdatedTmsp(entity.getLastUpdtTmsp());
        return dmp;
    }

    /**
     * This Method returns Model Object for a given entity.
     *
     * @param entity
     * @return Model Object
     */
    public static PostDocMentPlan getPostDocMentoringPlanDTO(PostDocMentPlanEntity entity) {
        LOGGER.debug("ConverterUtility.getPostDocMentoringPlanDTO()");
        PostDocMentPlan mp = new PostDocMentPlan();
        mp.setFilePath(ProposalDataUtility.returnNullIfBlank(entity.getPostDocMentPlanFileLocation()));
        mp.setOrigFileName(ProposalDataUtility.returnNullIfBlank(entity.getPostDocMentPlanDocOrigFileName()));
        mp.setPostDocMentPlanDocText(ProposalDataUtility.returnNullIfBlank(entity.getPostDocMentPlanDocText()));
        mp.setPageCount(entity.getPostDocMentPlanDocPageCount());
        mp.setLastUpdatedTmsp(entity.getLastUpdtTmsp());
        return mp;
    }

    /**
     * Returns Other Supplementary Documents.
     * 
     * @param entity
     * @return
     */
    public static OtherSuppDocs getOtherSuppDocsDTO(OtherSuppDocsEntity entity) {
        LOGGER.debug("ConverterUtility.getPostDocMentoringPlanDTO()");
        OtherSuppDocs mp = new OtherSuppDocs();
        mp.setFilePath(ProposalDataUtility.returnNullIfBlank(entity.getOtherSuppDocFileLocation()));
        mp.setOrigFileName(ProposalDataUtility.returnNullIfBlank(entity.getOtherSuppDocOrigFileName()));
        mp.setOtherSuppDocTxt(ProposalDataUtility.returnNullIfBlank(entity.getOtherSuppDocText()));
        mp.setPageCount(entity.getOtherSuppDocPageCount());
        mp.setOtherSuppDocNum(entity.getOtherSuppDocNumber());
        mp.setLastUpdatedTmsp(entity.getLastUpdtTmsp());
        return mp;
    }

    /**
     * This method converts OthrPersBioInfo Model to Entity.
     *
     * @param uploadableProposalSection
     * @param propRevId
     * @return OthrPersBioInfoEntity.
     */
    public static OthrPersBioInfoEntity getOthrPersBioInfoEntity(UploadableProposalSection uploadableProposalSection,
            Long propRevId) {
        LOGGER.debug("ConverterUtility.getOthrPersBioInfoEntity()");

        OthrPersBioInfoEntity srEntity = new OthrPersBioInfoEntity();
        srEntity.setPropPrepRevnId(propRevId);
        srEntity.setOthrPersBioInfoFileLocation(uploadableProposalSection.getFilePath());
        srEntity.setOthrPersBioInfoDocPageCount(uploadableProposalSection.getPageCount());
        srEntity.setOthrPersBioInfoDocText("");
        srEntity.setOthrPersBioInfoDocOrigFileName(uploadableProposalSection.getOrigFileName());
        setAuditFields(srEntity);
        srEntity.setLastUpdtUser(uploadableProposalSection.getLastUpdatedUser());
        return srEntity;
    }

    /**
     * This method converts ReviewersNotIncludeEntity to DTO
     *
     * @param srEntity
     * @return
     */

    public static ReviewersNotInclude getReviewersNotIncludeDto(ReviewersNotIncludeEntity srEntity) {
        LOGGER.debug("ConverterUtility.getReviewersNotIncludeDto()");

        ReviewersNotInclude suggRevr = new ReviewersNotInclude();
        suggRevr.setRevrsNotIncludeDocText(ProposalDataUtility.returnNullIfBlank(srEntity.getRevrsNotIncludeDocText()));
        suggRevr.setFilePath(ProposalDataUtility.returnNullIfBlank(srEntity.getRevrsNotIncludeFileLocation()));
        suggRevr.setOrigFileName(ProposalDataUtility.returnNullIfBlank(srEntity.getRevrsNotIncludeDocOrigFileName()));
        suggRevr.setPageCount(srEntity.getRevrsNotIncludeDocPageCount());
        suggRevr.setLastUpdatedTmsp(srEntity.getLastUpdtTmsp());
        return suggRevr;
    }

    /**
     * This method converts SuggestedReviewer DTO to Entity.
     *
     * @param uploadableProposalSection
     * @param propRevId
     * @return ProjectDescriptionEntity
     */
    public static ReviewersNotIncludeEntity convertToReviewersNotIncludeEntity(
            UploadableProposalSection uploadableProposalSection, Long propRevId) {
        LOGGER.debug("ConverterUtility.convertToReviewersNotIncludeEntity()");

        ReviewersNotIncludeEntity srEntity = new ReviewersNotIncludeEntity();
        srEntity.setPropPrepRevnId(propRevId);
        srEntity.setRevrsNotIncludeFileLocation(uploadableProposalSection.getFilePath());
        srEntity.setRevrsNotIncludeDocPageCount(uploadableProposalSection.getPageCount());
        srEntity.setRevrsNotIncludeDocText("");
        srEntity.setRevrsNotIncludeDocOrigFileName(uploadableProposalSection.getOrigFileName());
        setAuditFields(srEntity);
        srEntity.setLastUpdtUser(uploadableProposalSection.getLastUpdatedUser());
        return srEntity;
    }

    /**
     * This method converts DataManagement Plan DTO to Entity.
     *
     * @param uploadableProposalSection
     * @param propRevId
     * @return ProjectDescriptionEntity
     */
    public static DataManagementPlanEntity convertToDataManagementPlanEntity(
            UploadableProposalSection uploadableProposalSection, Long propRevId) {
        LOGGER.debug("ConverterUtility.convertToDataManagementPlanEntity()");

        DataManagementPlanEntity srEntity = new DataManagementPlanEntity();
        srEntity.setPropPrepRevnId(propRevId);
        srEntity.setDataManagementPlanFileLocation(uploadableProposalSection.getFilePath());
        srEntity.setDataManagementPlanDocPageCount(uploadableProposalSection.getPageCount());
        srEntity.setDataManagementPlanDocText("");
        srEntity.setDataManagementPlanDocOrigFileName(uploadableProposalSection.getOrigFileName());
        setAuditFields(srEntity);
        srEntity.setLastUpdtUser(uploadableProposalSection.getLastUpdatedUser());
        return srEntity;
    }

    /**
     * This method converts Post Doc Mentoring Plan DTO to Entity.
     *
     * @param uploadableProposalSection
     * @param propRevId
     * @return ProjectDescriptionEntity
     */
    public static PostDocMentPlanEntity convertToPostDocMentoringPlanEntity(
            UploadableProposalSection uploadableProposalSection, Long propRevId) {
        LOGGER.debug("ConverterUtility.convertToPostDocMentoringPlanEntity()");

        PostDocMentPlanEntity entity = new PostDocMentPlanEntity();
        entity.setPropPrepRevnId(propRevId);
        entity.setPostDocMentPlanFileLocation(uploadableProposalSection.getFilePath());
        entity.setPostDocMentPlanDocPageCount(uploadableProposalSection.getPageCount());
        entity.setPostDocMentPlanDocText("");
        entity.setPostDocMentPlanDocOrigFileName(uploadableProposalSection.getOrigFileName());
        setAuditFields(entity);
        entity.setLastUpdtUser(uploadableProposalSection.getLastUpdatedUser());
        return entity;
    }

    /**
     * This method creates new entity with given data.
     * 
     * @param ups
     * @param propRevId
     * @return
     */
    public static OtherSuppDocsEntity convertToOtherSuppDocsEntity(UploadableProposalSection ups, Long propRevId) {
        OtherSuppDocsEntity entity = new OtherSuppDocsEntity();
        entity.setPropPrepRevnId(propRevId);
        entity.setOtherSuppDocNumber(1);
        entity.setOtherSuppDocFileLocation(ups.getFilePath());
        entity.setOtherSuppDocPageCount(ups.getPageCount());
        entity.setOtherSuppDocText("");
        entity.setOtherSuppDocOrigFileName(ups.getOrigFileName());
        setAuditFields(entity);
        entity.setLastUpdtUser(ups.getLastUpdatedUser());
        return entity;
    }

    /**
     * This method creates new Budget Impact entity with given data.
     * 
     * @param ups
     * @param propRevId
     * @return
     */
    public static BudgetImpactEntity convertToBudgetImpactEntity(UploadableProposalSection ups, Long propRevId) {
        BudgetImpactEntity entity = new BudgetImpactEntity();
        entity.setPropPrepRevnId(propRevId);
        entity.setBudgImpactFileLocation(ups.getFilePath());
        entity.setBudgImpactDocPageCount(ups.getPageCount());
        entity.setBudgImpactOrigFileName(ups.getOrigFileName());
        setAuditFields(entity);
        entity.setLastUpdtUser(ups.getLastUpdatedUser());
        entity.setCreatedUser(ups.getLastUpdatedUser());
        entity.setCreatedDate(ProposalDataUtility.getCurrDate());
        return entity;
    }

    /**
     * This method will set Audit Fields.
     *
     * @param entity
     */
    public static void setAuditFields(BaseEntity entity) {
        entity.setLastUpdtUser(Constants.PDSVC_USER);
        entity.setLastUpdtPgm(Constants.PDSVC_PGM);
        entity.setLastUpdtTmsp(ProposalDataUtility.getCurrDate());
    }

    public static ProposalHeader getProposalHeader(String propPrepId, String propRevId, ProposalPrepRepository propRep)
            throws CommonUtilException {
        LOGGER.debug("ConverterUtility.getProposalHeader()");

        ProposalHeader proposalHeader = new ProposalHeader();

        try {
            ProposalPrep propEntity = propRep.findByPropPrepId(Long.valueOf(propPrepId));
            proposalHeader = setProposalHeaderData(propEntity, propPrepId, propRevId);
        } catch (Exception e) {
            throw new CommonUtilException("Problem while getting Proposal header : ", e);
        }
        return proposalHeader;
    }

    private static ProposalHeader setProposalHeaderData(ProposalPrep propEntity, String propPrepId, String propRevId) {
        LOGGER.debug(
                "ConverterUtility.setProposalHeaderData() propPrepId :: " + propPrepId + "  propRevId :: " + propRevId);
        ProposalHeader ph = new ProposalHeader();
        ph.setPropPrepId(propPrepId);
        ph.setPropRevId(propRevId);
        ph.setSubmissionType(propEntity.getPropSubmTypeCode());
        ph.setProposalInitiatedNSFId(propEntity.getPropInttNsfId());
        ph.setProposalType(propEntity.getPropTypeCode());
        ph.setCollabType(propEntity.getPropColbTypeCode());

        for (ProposalPrepRevision rev : propEntity.getRevisions()) {
            LOGGER.debug("Pulling Reviosn for RevId : " + propRevId);
            if (String.valueOf(rev.getPropPrepRevnId()).equalsIgnoreCase(propRevId)) {
                LOGGER.debug(
                        " ******** created Date : " + rev.getCreatedDate() + " Created User : " + rev.getCreatedUser());
                ph.setRevnCreateDate(rev.getCreatedDate());
                ph.setRevnCreateUser(rev.getCreatedUser());
                ph.setRevnType(rev.getPropPrepRevnTypeCode());
                ph.setCurrPPAPPGVer(rev.getCurrPappgVers());
                Deadline deadline = new Deadline();
                deadline.setDeadlineDate(rev.getPropDueDate());
                deadline.setDeadlineTypeCode(rev.getPropDueDateTypeCode());
                ph.setDeadline(deadline);
            }
        }
        return ph;
    }

    public static ProposalCOAEntity convertCOAToProposalCOAEntity(COA coa) {
        LOGGER.debug("ConverterUtility.convertCOAToProposalCOAEntity()");
        ProposalCOAEntity entity = new ProposalCOAEntity();
        return populateCoaEntityFieldsFromCoa(coa, entity);
    }
    
    public static ProposalCOAEntity convertCOAToExistingProposalCOAEntity(COA coa, ProposalCOAEntity entity) {
        LOGGER.debug("ConverterUtility.convertCOAToProposalCOAEntity()");
        return populateCoaEntityFieldsFromCoa(coa, entity);
    }

    private static ProposalCOAEntity populateCoaEntityFieldsFromCoa(COA coa, ProposalCOAEntity entity){
        entity.setPropPrepRevnId(coa.getPropPrepRevId());
        entity.setPropPersId(Long.valueOf(coa.getPropPersId()));
        entity.setCoaExclFileLoc(coa.getCoaExcelFilePath());
        entity.setCoaExclOrigFileName(coa.getCoaExcelFileName());
        entity.setCoaPdfFileLoc(coa.getCoaPdfFilePath());
        entity.setCoaPdfFileName(coa.getCoaPdfFileName());
        entity.setCoaPdfPageCount(coa.getCoaPdfPageCount());
        setAuditFields(entity);
        entity.setLastUpdtUser(coa.getLastUpdatedUser());
        return entity;
    }
    
    public static COA convertProposalCOAEntityToCOA(ProposalCOAEntity entity) {
        LOGGER.debug("ConverterUtility.convertProposalCOAEntityToCOA()");
        COA coa = new COA();
        coa.setPropPrepRevId(entity.getPropPrepRevnId());
        coa.setPropPersId(String.valueOf(entity.getPropPersId()));
        coa.setCoaExcelFilePath(entity.getCoaExclFileLoc());
        coa.setCoaExcelFileName(entity.getCoaExclOrigFileName());
        coa.setCoaPdfFilePath(entity.getCoaPdfFileLoc());
        coa.setCoaPdfFileName(entity.getCoaPdfFileName());
        coa.setCoaPdfPageCount(entity.getCoaPdfPageCount());
        coa.setLastUpdatedTmsp(entity.getLastUpdtTmsp());
        if (entity.getPerson() != null) {
            coa.setPerson(convertSeniorPersonnelEntityToDto(entity.getPerson()));
        }
        coa.setAffiliations(ConverterUtility.convertAffiliationEntityToAffiliation(entity.getAffiliations()));
        coa.setAdvisees(ConverterUtility.convertAdviseeEntityToAdvisee(entity.getAdvisees()));
        coa.setCollaborators(
                ConverterUtility.convertCollaboratorEntityToCollaborator(entity.getCollaborators()));
        coa.setCoEditors(ConverterUtility.convertCoEditorEntityToCoEditor(entity.getCoeditors()));
        coa.setRelationships(
                ConverterUtility.convertRelationshipEntityToRelationship(entity.getRelationships()));
        return coa;
    }

    public static List<AffiliationEntity> convertAffiliationToAffiliationEntity(ProposalCOAEntity entity, COA coa) {
        LOGGER.debug("ConverterUtility.convertAffiliationToAffiliationEntity()");
        List<AffiliationEntity> affiliations = new ArrayList<>();
        for (Affiliation affiliation : coa.getAffiliations()) {
            AffiliationEntity affl = new AffiliationEntity();
            affl.setPropCOA(entity);
            affl.setSrPersName(affiliation.getSrPersName());
            affl.setOrgAfflName(affiliation.getOrgAfflName());
            affl.setLastActvDate(affiliation.getLastActvDate());
            setAuditFields(affl);
            affl.setLastUpdtUser(coa.getLastUpdatedUser());
            affiliations.add(affl);
        }
        return affiliations;
    }

    public static List<Affiliation> convertAffiliationEntityToAffiliation(List<AffiliationEntity> affls) {
        LOGGER.debug("ConverterUtility.convertAffiliationEntityToAffiliation()");
        List<Affiliation> affiliations = new ArrayList<>();
        for (AffiliationEntity affiliation : affls) {
            Affiliation affl = new Affiliation();
            affl.setCoaId(affiliation.getPropCOA().getPropCOAId());
            affl.setSrPersName(affiliation.getSrPersName());
            affl.setOrgAfflName(affiliation.getOrgAfflName());
            affl.setLastActvDate(affiliation.getLastActvDate());
            affiliations.add(affl);
        }
        return affiliations;
    }

    public static List<AdviseeEntity> convertAdviseeToAdviseeEntity(ProposalCOAEntity entity, COA coa) {
        LOGGER.debug("ConverterUtility.convertAdviseeToAdviseeEntity()");
        List<AdviseeEntity> advisees = new ArrayList<>();
        for (Advisee advisee : coa.getAdvisees()) {
            AdviseeEntity advs = new AdviseeEntity();
            advs.setPropCOA(entity);
            advs.setPropPhdAdvrAdseInfoName(advisee.getAdviseeName());
            advs.setEmailDeptName(advisee.getEmailDeptName());
            advs.setOrgAfflName(advisee.getOrgAfflName());
            advs.setPropPhdAdvrAdseInfoTypeCode(advisee.getAdviseeTypeCode());
            setAuditFields(advs);
            advs.setLastUpdtUser(coa.getLastUpdatedUser());
            advisees.add(advs);
        }
        return advisees;
    }

    public static List<Advisee> convertAdviseeEntityToAdvisee(List<AdviseeEntity> advs) {
        LOGGER.debug("ConverterUtility.convertAdviseeEntityToAdvisee()");
        List<Advisee> advisees = new ArrayList<>();
        for (AdviseeEntity advisee : advs) {
            Advisee adv = new Advisee();
            adv.setCoaId(advisee.getPropCOA().getPropCOAId());
            adv.setAdviseeName(advisee.getPropPhdAdvrAdseInfoName());
            adv.setEmailDeptName(advisee.getEmailDeptName());
            adv.setOrgAfflName(advisee.getOrgAfflName());
            adv.setAdviseeTypeCode(advisee.getPropPhdAdvrAdseInfoTypeCode());
            advisees.add(adv);
        }
        return advisees;
    }

    public static List<CollaboratorEntity> convertCollaboratorToCollaboratorEntity(ProposalCOAEntity entity, COA coa) {
        LOGGER.debug("ConverterUtility.convertCollaboratorToCollaboratorEntity()");
        List<CollaboratorEntity> collaborators = new ArrayList<>();
        for (Collaborator collaborator : coa.getCollaborators()) {
            CollaboratorEntity clbr = new CollaboratorEntity();
            clbr.setPropCOA(entity);
            clbr.setEmailDeptName(collaborator.getEmailDeptName());
            clbr.setOrgAfflName(collaborator.getOrgAfflName());
            clbr.setPropClbrName(collaborator.getClbrName());
            clbr.setPropClbrTypeCode(collaborator.getClbrTypeCode());
            clbr.setLastActvDate(collaborator.getLastActvDate());
            setAuditFields(clbr);
            clbr.setLastUpdtUser(coa.getLastUpdatedUser());
            collaborators.add(clbr);
        }
        return collaborators;
    }

    public static List<Collaborator> convertCollaboratorEntityToCollaborator(List<CollaboratorEntity> clbrs) {
        LOGGER.debug("ConverterUtility.convertCollaboratorEntityToCollaborator()");
        List<Collaborator> collaborators = new ArrayList<>();
        for (CollaboratorEntity collaborator : clbrs) {
            Collaborator clbr = new Collaborator();
            clbr.setCoaId(collaborator.getPropCOA().getPropCOAId());
            clbr.setEmailDeptName(collaborator.getEmailDeptName());
            clbr.setOrgAfflName(collaborator.getOrgAfflName());
            clbr.setClbrName(collaborator.getPropClbrName());
            clbr.setClbrTypeCode(collaborator.getPropClbrTypeCode());
            clbr.setLastActvDate(collaborator.getLastActvDate());
            collaborators.add(clbr);
        }
        return collaborators;
    }

    public static List<CoEditorEntity> convertCoEditorToCoEditorEntity(ProposalCOAEntity entity, COA coa) {
        LOGGER.debug("ConverterUtility.convertCoEditorToCoEditorEntity()");
        List<CoEditorEntity> coeditors = new ArrayList<>();
        for (CoEditor coeditor : coa.getCoEditors()) {
            CoEditorEntity edit = new CoEditorEntity();
            edit.setPropCOA(entity);
            edit.setJrnlAfflName(coeditor.getJournalCollection());
            edit.setOrgAfflName(coeditor.getOrgAfflName());
            edit.setPropCoEditName(coeditor.getCoEditorName());
            edit.setPropCoEditTypeCode(coeditor.getCoEditorTypeCode());
            edit.setLastActvDate(coeditor.getLastActvDate());
            setAuditFields(edit);
            edit.setLastUpdtUser(coa.getLastUpdatedUser());
            coeditors.add(edit);
        }
        return coeditors;
    }

    public static List<CoEditor> convertCoEditorEntityToCoEditor(List<CoEditorEntity> edits) {
        LOGGER.debug("ConverterUtility.convertCoEditorEntityToCoEditor()");
        List<CoEditor> coeditors = new ArrayList<>();
        for (CoEditorEntity coeditor : edits) {
            CoEditor edit = new CoEditor();
            edit.setCoaId(coeditor.getPropCOA().getPropCOAId());
            edit.setJournalCollection(coeditor.getJrnlAfflName());
            edit.setOrgAfflName(coeditor.getOrgAfflName());
            edit.setCoEditorName(coeditor.getPropCoEditName());
            edit.setCoEditorTypeCode(coeditor.getPropCoEditTypeCode());
            edit.setLastActvDate(coeditor.getLastActvDate());
            coeditors.add(edit);
        }
        return coeditors;
    }

    public static List<RelationshipEntity> convertRelationshipToRelationshipEntity(ProposalCOAEntity entity, COA coa) {
        LOGGER.debug("ConverterUtility.convertRelationshipToRelationshipEntity()");
        List<RelationshipEntity> relationships = new ArrayList<>();
        for (Relationship relationship : coa.getRelationships()) {
            RelationshipEntity rela = new RelationshipEntity();
            rela.setPropCOA(entity);
            rela.setEmailDeptName(relationship.getEmailDeptName());
            rela.setOrgAfflName(relationship.getOrgAfflName());
            rela.setPropRelaName(relationship.getRelationshipName());
            rela.setPropRelaTypeCode(relationship.getRelationshipTypeCode());
            rela.setLastActvDate(relationship.getLastActvDate());
            setAuditFields(rela);
            rela.setLastUpdtUser(coa.getLastUpdatedUser());
            relationships.add(rela);
        }
        return relationships;
    }

    public static List<Relationship> convertRelationshipEntityToRelationship(List<RelationshipEntity> relas) {
        LOGGER.debug("ConverterUtility.convertRelationshipEntityToRelationship()");
        List<Relationship> relationships = new ArrayList<>();
        for (RelationshipEntity relationship : relas) {
            Relationship rela = new Relationship();
            rela.setCoaId(relationship.getPropCOA().getPropCOAId());
            rela.setEmailDeptName(relationship.getEmailDeptName());
            rela.setOrgAfflName(relationship.getOrgAfflName());
            rela.setRelationshipName(relationship.getPropRelaName());
            rela.setRelationshipTypeCode(relationship.getPropRelaTypeCode());
            rela.setLastActvDate(relationship.getLastActvDate());
            relationships.add(rela);
        }
        return relationships;
    }

    public static List<DeadlineTypeLookUp> convertDueDateTypeEntitiesToDtos(List<DeadlineTypeEntity> entities) {
        LOGGER.debug("ConverterUtility.convertDueDateTypeEntitiesToDtos()");
        List<DeadlineTypeLookUp> dtos = new ArrayList<DeadlineTypeLookUp>();
        for (DeadlineTypeEntity entity : entities) {
            DeadlineTypeLookUp dto = new DeadlineTypeLookUp();
            dto.setCode(entity.getDeadlineTypeCode().trim());
            dto.setDescription(entity.getDeadlineTypeText().trim());
            dtos.add(dto);
        }
        return dtos;
    }

    public static Proposal convertProposalPrepToProposal(ProposalPrep propEntity, ProposalPrepRevision rev,
            List<ProposalPrepRevision> minRevs, List<ProposalStatusTypeLookUp> statuses, boolean readOnly) {
        Proposal proposal = new Proposal();
        proposal.setDeadlineDate(rev.getPropDueDate());
        proposal.setIsReadOnly(readOnly);
        if(rev.getPropDueDateTypeCode() != null) {
            proposal.setDeadlineTypeCode(rev.getPropDueDateTypeCode().trim());
        }
        if (rev.getDeadlineType() != null) {
            proposal.setDeadlineTypeText(rev.getDeadlineType().getDeadlineTypeText());
        }
        proposal.setLastUpdatedDate(propEntity.getLastUpdtTmsp());
        proposal.setPropPrepId(Long.toString(propEntity.getPropPrepId()));
        proposal.setFundingOpId(propEntity.getFundOppId());
        proposal.setNsfPropId(propEntity.getNsfPropId());
        proposal.setSubmDate(propEntity.getNsfPropSubmDate());
        proposal = convertProposalPrepRevisionToProposal(proposal, rev, minRevs, statuses); // Include
        // revision
        return proposal;
    }

    public static Proposal convertProposalPrepRevisionToProposal(Proposal proposal, ProposalPrepRevision rev,
            List<ProposalPrepRevision> minRevs, List<ProposalStatusTypeLookUp> statuses) {
        boolean isSubmitted = false;
        String statusCode = null;
        if (minRevs != null && !minRevs.isEmpty()) {
            ProposalPrepRevision propRev = minRevs.get(minRevs.size() - 1);
            if(propRev != null) {
                proposal.setPropRevId(Long.toString(propRev.getPropPrepRevnId()));
                statusCode = propRev.getPropPrepSttsCode();
                proposal.setTitle(propRev.getPropTitl());
                proposal.setSubmDate(propRev.getPropPrepSttsDate());
                if(!propRev.getPersons().isEmpty()) {
                    proposal = ProposalQueryUtility.addPersonnel(proposal, propRev);
                }
                isSubmitted = true;
            }
        } else {
            proposal.setPropRevId(Long.toString(rev.getPropPrepRevnId()));
            statusCode = rev.getPropPrepSttsCode();
            proposal.setTitle(rev.getPropTitl());
            if(!rev.getPersons().isEmpty()) {
                proposal = ProposalQueryUtility.addPersonnel(proposal, rev);
            }
        }
        proposal.setRevNo(rev.getRevnNum().intValue());
        if(!StringUtils.isEmpty(statusCode)) {
            ProposalStatus propStatus = new ProposalStatus();
            propStatus.setStatusCode(statusCode);
            propStatus.setStatusDesc(ProposalQueryUtility.getStatusLookup(statuses, statusCode).get().getDescription());
            proposal.setProposalStatus(propStatus);
        }
        if (isSubmitted) {
            List<ProposalRevision> pfus = new ArrayList<>();
            ProposalRevision propRev = new ProposalRevision();
            if (!rev.getPersons().isEmpty()) {
                propRev = ProposalQueryUtility.addRevisionPersonnel(propRev, rev);
            }
            propRev.setLastUpdatedTmsp(rev.getLastUpdtTmsp());
            ProposalStatus status = new ProposalStatus();
            status.setStatusCode(rev.getPropPrepSttsCode());
            status.setStatusDesc(ProposalQueryUtility.getStatusLookup(statuses, rev.getPropPrepSttsCode()).get()
                    .getDescription());
            propRev.setProposalStatus(status);
            List<String> institutionIds = new ArrayList<>();
            if(rev.getInstitutions() != null && !rev.getInstitutions().isEmpty()) {
                for(BudgetInstitutionEntity entity : rev.getInstitutions()) {
                    institutionIds.add(entity.getInstId());
                }
            }
            propRev.setInstitutionIds(institutionIds);
            propRev.setPropRevId(String.valueOf(rev.getPropPrepRevnId()));
            pfus.add(propRev);
            proposal.setPfus(pfus);
        }
        return proposal;
    }

    public static Set<UnitOfConsideration> convertUOCToUOCEntity(
            gov.nsf.psm.foundation.model.UnitOfConsideration[] uocArray, String fundingOpportunityId,
            String lastUpdatedUser) {
        LOGGER.debug("ConverterUtility.convertUOCToUOCEntity()");

        // Populate uocs
        Set<UnitOfConsideration> uocs = new HashSet<>();

        for (int i = 0; i < uocArray.length; i++) {
            UnitOfConsideration uocEntity = new UnitOfConsideration();
            uocEntity.setFundOppId(fundingOpportunityId);
            uocEntity.setOrgCode(uocArray[i].getDivision().getDivisionCode());
            uocEntity.setPgmEleCode(uocArray[i].getProgramElement().getProgramElementCode());
            uocEntity.setUocOrdrNum(i);
            setAuditFields(uocEntity);
            uocEntity.setLastUpdtUser(lastUpdatedUser);
            uocs.add(uocEntity);
        }
        return uocs;
    }

    public static PiCoPiInformation convertProposalPersonnelDemographicInfoEntity(
            ProposalPersonnelDemographicInfoEntity entity) {
        PiCoPiInformation info = new PiCoPiInformation();

        if (entity != null) {
            info.setPropPersId(entity.getPropPersId());
            info.setTelephoneNum(entity.getUserPhoneNumber());
            info.setFaxNumber(entity.getUserFaxNumber());
            info.setEmailAddress(entity.getUserEmailAddress());
            info.setDegree(StringUtils.trim(entity.getUserAcademicDegree()));
            info.setDegreeYear(String.valueOf(entity.getUserAcademicYear()));
            info.setDepartmentName(StringUtils.trim(entity.getUserDeptName()));
        }

        return info;
    }

    public static ProposalUpdateJustificationEntity convertProposalUpdateJustificationDtoToEntity(
            ProposalUpdateJustification proposalUpdateJustifacation) {
        LOGGER.debug("ConverterUtility.convertProposalUpdateJustificationDtoToEntity()");
        ProposalUpdateJustificationEntity pujEntity = new ProposalUpdateJustificationEntity();
        pujEntity.setProposalUpdateJustificationText(proposalUpdateJustifacation.getJustificationText());
        pujEntity.setCreatedUser(proposalUpdateJustifacation.getLastUpdatedUser());
        pujEntity.setPropRevId(Long.valueOf(proposalUpdateJustifacation.getPropRevId()));
        pujEntity.setCreatedDate(ProposalDataUtility.getCurrDate());
        setAuditFields(pujEntity);
        pujEntity.setLastUpdtUser(proposalUpdateJustifacation.getLastUpdatedUser());

        return pujEntity;
    }

    public static ProposalUpdateJustification convertProposalUpdateJustificationEntityToDto(
            ProposalUpdateJustificationEntity proposalUpdateJustifacationEntity) {
        LOGGER.debug("ConverterUtility.convertProposalUpdateJustificationEntityToDto()");
        ProposalUpdateJustification puj = new ProposalUpdateJustification();
        puj.setJustificationText(proposalUpdateJustifacationEntity.getProposalUpdateJustificationText());
        puj.setLastUpdatedTmsp(proposalUpdateJustifacationEntity.getLastUpdtTmsp());
        return puj;
    }

    public static ProposalRevision convertProposalRevisioinEntityToDto(ProposalPrepRevision rev) {
        LOGGER.debug("ConverterUtility.convertProposalRevisioinEntityToDto()");
        ProposalRevision revision = new ProposalRevision();
        ProposalRevisionType revisionType = new ProposalRevisionType();
        revisionType.setType(rev.getPropPrepRevnTypeCode());
        revision.setRevisionType(revisionType);
        revision.setStaticPdfPath(rev.getStaticPdfPath());
        revision.setRevNo((Integer)rev.getRevnNum().intValue());
        ProposalStatus status = new ProposalStatus();
        status.setStatusCode(rev.getPropPrepSttsCode());
        revision.setProposalStatus(status);
        revision.setPropRevId(rev.getPropPrepRevnId().toString());
        return revision;
    }
    
	public static List<FundingOpportunity> convertFundingOppExclusionEntitiesToDtos(
	        List<FundingOppExlclusionEntity> entities) {
		LOGGER.debug("ConverterUtility.convertFundingOppExclusionEntitiesToDtos()");
		List<FundingOpportunity> dtos = new ArrayList<FundingOpportunity>();
		for (FundingOppExlclusionEntity entity : entities) {
			FundingOpportunity dto = new FundingOpportunity();
			dto.setFundingOpportunityId(entity.getFundOppId().trim());
			dtos.add(dto);
		}
		return dtos;
	}
}
