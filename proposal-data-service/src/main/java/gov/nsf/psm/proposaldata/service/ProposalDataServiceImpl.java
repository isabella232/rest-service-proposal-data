package gov.nsf.psm.proposaldata.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.BiographicalSketch;
import gov.nsf.psm.foundation.model.BudgetImpact;
import gov.nsf.psm.foundation.model.BudgetJustification;
import gov.nsf.psm.foundation.model.BudgetRevision;
import gov.nsf.psm.foundation.model.COA;
import gov.nsf.psm.foundation.model.CurrentAndPendingSupport;
import gov.nsf.psm.foundation.model.DataManagementPlan;
import gov.nsf.psm.foundation.model.FacilitiesEquipment;
import gov.nsf.psm.foundation.model.FundingOpportunity;
import gov.nsf.psm.foundation.model.Institution;
import gov.nsf.psm.foundation.model.OtherSuppDocs;
import gov.nsf.psm.foundation.model.OthrPersBioInfo;
import gov.nsf.psm.foundation.model.PSMMessage;
import gov.nsf.psm.foundation.model.PSMMessageType;
import gov.nsf.psm.foundation.model.PSMRole;
import gov.nsf.psm.foundation.model.Personnel;
import gov.nsf.psm.foundation.model.PersonnelParam;
import gov.nsf.psm.foundation.model.PersonnelSectionData;
import gov.nsf.psm.foundation.model.PostDocMentPlan;
import gov.nsf.psm.foundation.model.ProjectDescription;
import gov.nsf.psm.foundation.model.ProjectSummary;
import gov.nsf.psm.foundation.model.ProposalElectronicSign;
import gov.nsf.psm.foundation.model.ProposalUpdateJustification;
import gov.nsf.psm.foundation.model.ReferencesCited;
import gov.nsf.psm.foundation.model.ReviewersNotInclude;
import gov.nsf.psm.foundation.model.Section;
import gov.nsf.psm.foundation.model.SectionStatus;
import gov.nsf.psm.foundation.model.SuggestedReviewer;
import gov.nsf.psm.foundation.model.UploadableProposalSection;
import gov.nsf.psm.foundation.model.WarnMsgs;
import gov.nsf.psm.foundation.model.budget.BudgetRecord;
import gov.nsf.psm.foundation.model.budget.BudgetTotals;
import gov.nsf.psm.foundation.model.budget.EquipmentCost;
import gov.nsf.psm.foundation.model.budget.IndirectCost;
import gov.nsf.psm.foundation.model.budget.InstitutionBudget;
import gov.nsf.psm.foundation.model.budget.OtherDirectCost;
import gov.nsf.psm.foundation.model.budget.OtherPersonnelCost;
import gov.nsf.psm.foundation.model.budget.ParticipantSupportCost;
import gov.nsf.psm.foundation.model.budget.SeniorPersonnelCost;
import gov.nsf.psm.foundation.model.budget.TravelCost;
import gov.nsf.psm.foundation.model.coversheet.CoverSheet;
import gov.nsf.psm.foundation.model.coversheet.PiCoPiInformation;
import gov.nsf.psm.foundation.model.coversheet.PrimaryPlaceOfPerformance;
import gov.nsf.psm.foundation.model.lookup.AdviseeTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CoEditorTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CollaborativeTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CollaboratorTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.Deadline;
import gov.nsf.psm.foundation.model.lookup.DeadlineTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.ElectronicCertificationText;
import gov.nsf.psm.foundation.model.lookup.InstitutionRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.LoginUserRoleType;
import gov.nsf.psm.foundation.model.lookup.OtherPersonnelRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.ProposalStatusTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.ProposalTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.RelationshipTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.SeniorPersonRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.SubmissionTypeLookUp;
import gov.nsf.psm.foundation.model.proposal.Proposal;
import gov.nsf.psm.foundation.model.proposal.ProposalCompleteTransfer;
import gov.nsf.psm.foundation.model.proposal.ProposalCopy;
import gov.nsf.psm.foundation.model.proposal.ProposalHeader;
import gov.nsf.psm.foundation.model.proposal.ProposalPackage;
import gov.nsf.psm.foundation.model.proposal.ProposalPermission;
import gov.nsf.psm.foundation.model.proposal.ProposalQueryParams;
import gov.nsf.psm.foundation.model.proposal.ProposalRevision;
import gov.nsf.psm.foundation.model.proposal.ProposalRevisionPersonnel;
import gov.nsf.psm.foundation.model.proposal.ProposalRevisionType;
import gov.nsf.psm.foundation.model.proposal.ProposalStatus;
import gov.nsf.psm.foundation.model.proposal.SubmittedProposal;
import gov.nsf.psm.proposaldata.entity.AdviseeEntity;
import gov.nsf.psm.proposaldata.entity.AffiliationEntity;
import gov.nsf.psm.proposaldata.entity.BiographicalSketchEntity;
import gov.nsf.psm.proposaldata.entity.BudgetImpactEntity;
import gov.nsf.psm.proposaldata.entity.BudgetInstitutionEntity;
import gov.nsf.psm.proposaldata.entity.BudgetJustificationEntity;
import gov.nsf.psm.proposaldata.entity.CoEditorEntity;
import gov.nsf.psm.proposaldata.entity.CollaborativeType;
import gov.nsf.psm.proposaldata.entity.CollaboratorEntity;
import gov.nsf.psm.proposaldata.entity.CoverSheetEntity;
import gov.nsf.psm.proposaldata.entity.CurrentAndPendingSupportEntity;
import gov.nsf.psm.proposaldata.entity.EquipmentBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.FacilityEquipmentEntity;
import gov.nsf.psm.proposaldata.entity.FringeBenefitBudgetEntity;
import gov.nsf.psm.proposaldata.entity.IndirectBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.InstitutionAddressEntity;
import gov.nsf.psm.proposaldata.entity.OtherDirectCostEntity;
import gov.nsf.psm.proposaldata.entity.OtherPersonsBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.PPOPEntity;
import gov.nsf.psm.proposaldata.entity.ParticipantsSupportCostEntity;
import gov.nsf.psm.proposaldata.entity.ProjectDescriptionEntity;
import gov.nsf.psm.proposaldata.entity.ProjectSummaryEntity;
import gov.nsf.psm.proposaldata.entity.ProposalCOAEntity;
import gov.nsf.psm.proposaldata.entity.ProposalElectronicCertificationTextTypeEntity;
import gov.nsf.psm.proposaldata.entity.ProposalElectronicSignEntity;
import gov.nsf.psm.proposaldata.entity.ProposalMessageEntity;
import gov.nsf.psm.proposaldata.entity.ProposalPersonsEntity;
import gov.nsf.psm.proposaldata.entity.ProposalPrep;
import gov.nsf.psm.proposaldata.entity.ProposalPrepRevision;
import gov.nsf.psm.proposaldata.entity.ProposalPreparationStatusHistoryEntity;
import gov.nsf.psm.proposaldata.entity.ProposalStatusType;
import gov.nsf.psm.proposaldata.entity.ProposalType;
import gov.nsf.psm.proposaldata.entity.ProposalUpdateJustificationEntity;
import gov.nsf.psm.proposaldata.entity.ReferencesCitedEntity;
import gov.nsf.psm.proposaldata.entity.RelationshipEntity;
import gov.nsf.psm.proposaldata.entity.SeniorPersonsBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.SubmissionType;
import gov.nsf.psm.proposaldata.entity.SuggestedReviewerEntity;
import gov.nsf.psm.proposaldata.entity.TravelBudgetCostEntity;
import gov.nsf.psm.proposaldata.repository.AdviseeTypeRepository;
import gov.nsf.psm.proposaldata.repository.BiographicalSketchRepository;
import gov.nsf.psm.proposaldata.repository.BudgetImpactRepository;
import gov.nsf.psm.proposaldata.repository.BudgetInstitutionRepository;
import gov.nsf.psm.proposaldata.repository.BudgetJustificationRepository;
import gov.nsf.psm.proposaldata.repository.CoEditorTypeRepository;
import gov.nsf.psm.proposaldata.repository.CollaborationTypeRepository;
import gov.nsf.psm.proposaldata.repository.CollaboratorTypeRepository;
import gov.nsf.psm.proposaldata.repository.CoverSheetRepository;
import gov.nsf.psm.proposaldata.repository.CurrentAndPendingSupportRepository;
import gov.nsf.psm.proposaldata.repository.DataManagementPlanRepository;
import gov.nsf.psm.proposaldata.repository.DeadlineTypeRepository;
import gov.nsf.psm.proposaldata.repository.EquipmentBudgetCostEntityRepository;
import gov.nsf.psm.proposaldata.repository.FacilityEquipmentRepository;
import gov.nsf.psm.proposaldata.repository.FringeBenefitBudgetRepository;
import gov.nsf.psm.proposaldata.repository.FundingOppExlclusionRepository;
import gov.nsf.psm.proposaldata.repository.IndirectBudgetCostEntityRepository;
import gov.nsf.psm.proposaldata.repository.InstitutionAddressRepository;
import gov.nsf.psm.proposaldata.repository.InstitutionTypeRepository;
import gov.nsf.psm.proposaldata.repository.InternationalActivitiesRepository;
import gov.nsf.psm.proposaldata.repository.OtherDirectCostEntityRepository;
import gov.nsf.psm.proposaldata.repository.OtherFederalAgenciesRepository;
import gov.nsf.psm.proposaldata.repository.OtherPersonnelTypeRepository;
import gov.nsf.psm.proposaldata.repository.OtherPersonsBudgetCostRepository;
import gov.nsf.psm.proposaldata.repository.OtherSuppDocsRepository;
import gov.nsf.psm.proposaldata.repository.OthrPersBioInfoRepository;
import gov.nsf.psm.proposaldata.repository.PPOPRepository;
import gov.nsf.psm.proposaldata.repository.ParticipantsSupportCostEntityRepository;
import gov.nsf.psm.proposaldata.repository.PostDocMentPlanRepository;
import gov.nsf.psm.proposaldata.repository.ProjectDescriptionRepository;
import gov.nsf.psm.proposaldata.repository.ProjectSummaryRepository;
import gov.nsf.psm.proposaldata.repository.ProposalCOARepository;
import gov.nsf.psm.proposaldata.repository.ProposalElectronicCertificationTextRepository;
import gov.nsf.psm.proposaldata.repository.ProposalElectronicSignRepository;
import gov.nsf.psm.proposaldata.repository.ProposalMessageRepository;
import gov.nsf.psm.proposaldata.repository.ProposalPersonalRepository;
import gov.nsf.psm.proposaldata.repository.ProposalPersonnelDemographicInfoRepository;
import gov.nsf.psm.proposaldata.repository.ProposalPrepRepository;
import gov.nsf.psm.proposaldata.repository.ProposalPrepRevisionRepository;
import gov.nsf.psm.proposaldata.repository.ProposalPreparationStatusHistoryRepository;
import gov.nsf.psm.proposaldata.repository.ProposalStatusTypeRepository;
import gov.nsf.psm.proposaldata.repository.ProposalTypeRepository;
import gov.nsf.psm.proposaldata.repository.ProposalUpdateJustificationRepository;
import gov.nsf.psm.proposaldata.repository.ReferenceCitedRepository;
import gov.nsf.psm.proposaldata.repository.RelationshipTypeRepository;
import gov.nsf.psm.proposaldata.repository.ReviewersNotIncludeRepository;
import gov.nsf.psm.proposaldata.repository.SeniorPersonnelTypeRepository;
import gov.nsf.psm.proposaldata.repository.SeniorPersonsBudgetCostRepository;
import gov.nsf.psm.proposaldata.repository.SubmissionTypeRepository;
import gov.nsf.psm.proposaldata.repository.SuggestedReviewerRepository;
import gov.nsf.psm.proposaldata.repository.TravelBudgetCostEntityRepository;
import gov.nsf.psm.proposaldata.repository.query.ProposalSearchRepository;
import gov.nsf.psm.proposaldata.repository.query.specification.ProposalSpecifications;
import gov.nsf.psm.proposaldata.repository.userpermissions.LoginUserRolePermissionRepository;
import gov.nsf.psm.proposaldata.repository.userpermissions.LoginUserRoleTypeRepository;
import gov.nsf.psm.proposaldata.service.parameter.InstitutionBudgetParameter;
import gov.nsf.psm.proposaldata.service.parameter.SectionStatusParameter;
import gov.nsf.psm.proposaldata.utility.BudgetDataUtility;
import gov.nsf.psm.proposaldata.utility.Constants;
import gov.nsf.psm.proposaldata.utility.ConverterUtility;
import gov.nsf.psm.proposaldata.utility.CoverSheetUtility;
import gov.nsf.psm.proposaldata.utility.ProposalCopyUtility;
import gov.nsf.psm.proposaldata.utility.ProposalDataUtility;
import gov.nsf.psm.proposaldata.utility.SectionStatusUtility;
import gov.nsf.psm.proposaldata.utility.UploadUtility;
import gov.nsf.psm.proposaldata.utility.WarnMsgUtility;

@Component("proposalDataService")
public class ProposalDataServiceImpl implements ProposalDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalDataServiceImpl.class);

    @Autowired
    ProposalStatusTypeRepository propSttsRep;

    @Autowired
    SubmissionTypeRepository submTypeRep;

    @Autowired
    ProposalTypeRepository propTypeRep;

    @Autowired
    CollaborationTypeRepository colbTypeRep;

    @Autowired
    CollaboratorTypeRepository clbrTypeRep;

    @Autowired
    AdviseeTypeRepository advrTypeRep;

    @Autowired
    CoEditorTypeRepository coedTypeRep;

    @Autowired
    RelationshipTypeRepository relaTypeRep;

    @Autowired
    ProposalPrepRepository propPrepRep;

    @Autowired
    ProposalPrepRevisionRepository propPrepRevRep;

    @Autowired
    ProjectSummaryRepository projectSummaryRep;

    @Autowired
    BudgetInstitutionRepository budgetInstitutionRep;

    @Autowired
    FringeBenefitBudgetRepository fringeBenefitBudgetRep;

    @Autowired
    OtherPersonsBudgetCostRepository otherPersonsBudgetCostRep;

    @Autowired
    SeniorPersonsBudgetCostRepository seniorPersonsBCRep;

    @Autowired
    ProposalPersonalRepository proposalPersonalRep;

    @Autowired
    EquipmentBudgetCostEntityRepository equipBCRep;

    @Autowired
    TravelBudgetCostEntityRepository travelBCRep;

    @Autowired
    ParticipantsSupportCostEntityRepository partSCEntityRep;

    @Autowired
    OtherDirectCostEntityRepository otherDCEntityRep;

    @Autowired
    IndirectBudgetCostEntityRepository indirectBCRep;

    @Autowired
    ReferenceCitedRepository referenceCitedRepository;

    @Autowired
    OtherPersonnelTypeRepository othPersTypeRep;

    @Autowired
    SeniorPersonnelTypeRepository snrPersTypeRep;

    @Autowired
    InstitutionTypeRepository instTypeRep;

    @Autowired
    FacilityEquipmentRepository facEquipRep;

    @Autowired
    ProjectDescriptionRepository projDescRep;

    @Autowired
    BudgetJustificationRepository budgJustRep;

    @Autowired
    BiographicalSketchRepository bioSketchRep;

    @Autowired
    CurrentAndPendingSupportRepository cpsRep;

    @Autowired
    ProposalMessageRepository propMsgRep;

    @Autowired
    SuggestedReviewerRepository suggRevrRep;

    @Autowired
    OthrPersBioInfoRepository othPersRep;

    @Autowired
    ReviewersNotIncludeRepository rvrsNotInclRep;

    @Autowired
    DataManagementPlanRepository dmpRep;

    @Autowired
    PostDocMentPlanRepository pdocMPRep;

    @Autowired
    CoverSheetRepository cvRep;

    @Autowired
    PPOPRepository ppopRep;

    @Autowired
    ProposalCOARepository propCOARep;

    @Autowired
    OtherFederalAgenciesRepository othFedAgencyRep;

    @Autowired
    InternationalActivitiesRepository intActRep;

    @Autowired
    DeadlineTypeRepository deadlineTypeRep;

    @Autowired
    LoginUserRoleTypeRepository loginUserRoleRep;

    @Autowired
    LoginUserRolePermissionRepository loginUserRolePermRep;

    @Autowired
    ProposalSearchRepository propSearchRep;

    @Autowired
    ProposalElectronicSignRepository proposalElectronicSignRepository;

    @Autowired
    OtherSuppDocsRepository otherSuppDocRep;

    @Autowired
    InstitutionAddressRepository institutionAddressRep;

    @Autowired
    ProposalPersonnelDemographicInfoRepository proposalPersonnelDemographicInfoRep;

    @Autowired
    ProposalElectronicCertificationTextRepository proposalElectronicCertificationTextRep;

    @Autowired
    BudgetImpactRepository bImpactRep;

    @Autowired
    ProposalUpdateJustificationRepository proposalUpdateJustificationRep;

    @Autowired
    ProposalPreparationStatusHistoryRepository proposalPreparationStatusHistoryRep;

    @Autowired
    FundingOppExlclusionRepository fundingOppExclusionRep;

    @PersistenceContext
    private EntityManager entityManager;

    private Map<String, String> submissionTypes;
    private Map<String, String> proposalTypes;
    private Map<String, String> collabTypes;
    private Map<String, String> statusTypes;

    /**
     * Load Cached Lookup Data
     */
    @PostConstruct
    private void preloadLookupData() {

        this.submissionTypes = new HashMap<String, String>();
        this.proposalTypes = new HashMap<String, String>();
        this.collabTypes = new HashMap<String, String>();
        this.statusTypes = new HashMap<String, String>();

        List<SubmissionType> subTypes = submTypeRep.findAll();
        for (SubmissionType subType : subTypes) {
            submissionTypes.put(subType.getPropSubmTypeCode().trim(), subType.getPropSubmTypeTxt());
        }

        List<ProposalType> propTypes = propTypeRep.findAll();
        for (ProposalType propType : propTypes) {
            proposalTypes.put(propType.getPropTypeCode().trim(), propType.getPropTypeTxt());
        }

        List<CollaborativeType> colbTypes = colbTypeRep.findAll();
        for (CollaborativeType collabType : colbTypes) {
            collabTypes.put(collabType.getPropColbTypeCode().trim(), collabType.getPropColbTypeTxt());
        }

        List<ProposalStatusType> statTypes = propSttsRep.findAll();
        for (ProposalStatusType statusType : statTypes) {
            statusTypes.put(statusType.getPropPrepSttsCode().trim(), statusType.getPropPrepSttsTxt());
        }
    }

    @Override
    public List<SubmissionTypeLookUp> getAllSubmissionTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getAllSubmissionTypes()");
        try {
            return ConverterUtility.convertSubmissionTypeEntitiesToDtos(submTypeRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_SUBMISSION_TYPE_ERROR, e);
        }
    }

    @Override
    public List<ProposalTypeLookUp> getAllProposalTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getAllProposalTypes()");
        try {
            return ConverterUtility.convertProposalTypeEntitiesToDtos(propTypeRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_PROPOSAL_TYPE_ERROR, e);
        }
    }

    @Override
    public List<CollaborativeTypeLookUp> getAllCollaborativeTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getAllCollaborativeTypes()");
        try {
            return ConverterUtility.convertCollabTypeEntitiesToDtos(colbTypeRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_COLLABORATIVE_TYPE_ERROR, e);
        }
    }

    @Override
    public List<CollaboratorTypeLookUp> getAllCollaboratorTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getAllCollaboratorTypes()");
        try {
            return ConverterUtility.convertCollaboratorTypeEntitiesToDtos(clbrTypeRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_COLLABORATOR_TYPE_ERROR, e);
        }
    }

    @Override
    public List<AdviseeTypeLookUp> getAllAdviseeTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getAllAdviseeTypes()");
        try {
            return ConverterUtility.convertAdviseeTypeEntitiesToDtos(advrTypeRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_ADVISEE_TYPE_ERROR, e);
        }
    }

    @Override
    public List<CoEditorTypeLookUp> getAllCoEditorTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getAllCoEditorTypes()");
        try {
            return ConverterUtility.convertCoEditorTypeEntitiesToDtos(coedTypeRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_COEDITOR_TYPE_ERROR, e);
        }
    }

    @Override
    public List<RelationshipTypeLookUp> getAllRelationshipTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getAllRelationshipTypes()");
        try {
            return ConverterUtility.convertRelationshipTypeEntitiesToDtos(relaTypeRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_RELATIONSHIP_TYPE_ERROR, e);
        }
    }

    @Override
    public List<OtherPersonnelRoleTypeLookUp> getOtherPersonnelRoleTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getOtherPersonnelRoleTypes()");
        try {
            return ConverterUtility.convertOtherPersonnelTypeEntitiesToDtos(othPersTypeRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_OTHER_PERSONNEL_ROLE_TYPE_ERROR, e);
        }
    }

    @Override
    public List<SeniorPersonRoleTypeLookUp> getSeniorPersonnelRoleTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getSeniorPersonnelRoleTypes()");
        try {
            return ConverterUtility.convertSeniorPersonnelTypeEntitiesToDtos(snrPersTypeRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_SENIOR_PERSONNEL_ROLE_TYPE_ERROR, e);
        }
    }

    @Override
    public List<ProposalStatusTypeLookUp> getAllProposalStatusTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getAllProposalStatusTypes()");
        try {
            return ConverterUtility.convertProposalStatusTypeEntitiesToDtos(propSttsRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_STATUS_TYPE_ERROR, e);
        }
    }

    @Override
    public List<InstitutionRoleTypeLookUp> getInstitutionRoleTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getInstitutionRoleTypes()");
        try {
            return ConverterUtility.convertInstitutionTypeEntitiesToDtos(instTypeRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_RELATIONSHIP_TYPE_ERROR, e);
        }
    }

    @Override
    public List<LoginUserRoleType> getAllLoginUserRoleTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getAllLoginUserRoleTypes()");
        try {
            return ConverterUtility.convertLoginUserRoleTypeEntitiesToDtos(loginUserRoleRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_RELATIONSHIP_TYPE_ERROR, e);
        }
    }

    @Override
    public List<ProposalPermission> getLoginUserRolePermissions(String propStatusCode, String[] userRoleCodes)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getLoginUserRolePermissions()");
        try {
            List<String> userRoleCodesList = Arrays.asList(userRoleCodes);
            return ConverterUtility.convertLoginUserRolePermissionEntitiesToDtos(
                    loginUserRolePermRep.findByCode(propStatusCode, userRoleCodesList));
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_RELATIONSHIP_TYPE_ERROR, e);
        }
    }

    @Override
    public ProposalPackage saveProposalPrep(ProposalPackage propDto) throws CommonUtilException {
        try {
            LOGGER.debug("ProposalDataServiceImpl.saveProposalPrep()");

            ProposalPrep propEntity = propPrepRep.save(ConverterUtility.convertProposalDtoToEntity(propDto));
            String propPrepId = String.valueOf(propEntity.getPropPrepId());
            String prepRevId = String.valueOf(getProposalRevisionId(propEntity.getPropPrepId().toString()));

            // Convert lookup codes to presentation values
            propDto.setProposalType(proposalTypes.get(propEntity.getPropTypeCode()));
            propDto.setSubmissionType(submissionTypes.get(propEntity.getPropSubmTypeCode()));
            propDto.setPropPrepId(String.valueOf(propEntity.getPropPrepId()));
            propDto.setCollabType(collabTypes.get(propDto.getCollabType()));
            propDto.setPropRevId(prepRevId);

            // Save Proposal Budget
            InstitutionBudget instBudget = new InstitutionBudget();
            instBudget.setInstId(propDto.getPi().getInstitution().getId());
            instBudget.setInstPropRoleTypeCode(Constants.PRIMARY_INSTITUTION_TYPE_CODE);
            instBudget.setPropRevId(prepRevId);
            instBudget.setLastUpdatedUser(propDto.getLastUpdatedUser());

            budgetInstitutionRep.save(ConverterUtility.convertInstitutionBudgetDtoToEntity(instBudget));

            // Save PI
            Personnel pi = propDto.getPi();
            pi.setPropPrepId(propPrepId);
            pi.setPropRevnId(prepRevId);
            PSMRole role = new PSMRole();
            role.setCode(PSMRole.ROLE_PI);
            pi.setPSMRole(role);
            pi.setLastUpdatedUser(propDto.getLastUpdatedUser());

            proposalPersonalRep.save(ConverterUtility.convertSeniorPersonnelDtoToEntity(pi));

            // Save Coversheet
            CoverSheet coverSheet = setInititalCoverSheetData(propDto, prepRevId);

            saveCoverSheet(coverSheet);

            updateProposalStatusHistory(prepRevId, ProposalStatus.NOT_FORWARDED_TO_SPO, propDto.getLastUpdatedUser());

            // remove unnecessary fields to send back to presentation layer
            pi.setPropPrepId(null);
            pi.setPropRevnId(null);
            pi.setPSMRole(null);

            return propDto;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PROPOSAL_ERROR, e);
        }
    }

    @Override
    public ProposalPackage createProposalRevision(ProposalCopy copy) throws CommonUtilException {
        ProposalPackage pkg = null;

        LOGGER.debug("Proposal revision: " + copy.toString());

        try {
            ProposalPrepRevision revision = new ProposalPrepRevision();
            Date revInsertDate = ProposalDataUtility.getCurrDate();
            ProposalPrep prop = propPrepRep.findByNsfPropId(copy.getNsfPropId());
            if (prop == null) {// Return null if proposal not found
                LOGGER.debug("Original proposal cannot be found: NSF Proposal ID = " + copy.getNsfPropId());
                return null;
            }
            ProposalPrepRevision prevRevEntity = propPrepRevRep.getMaxSubmittedRevision(prop.getPropPrepId()); // Get
            // previous
            // submitted revision
            ProposalPrepRevision prevRevNumEntity = propPrepRevRep.getMaxRevision(prop.getPropPrepId()); // Get
            // previous
            // revision
            pkg = ProposalCopyUtility.extractPropPackage(prop, prevRevEntity);
            if (prevRevEntity.getPropPrepSttsCode().trim().equalsIgnoreCase(ProposalStatus.PFU_NOT_FORWARDED_TO_SPO)) {
                LOGGER.debug("An unsubmitted revision already exists for NSF Proposal ID = " + copy.getNsfPropId());
                pkg.setIsAvailableForRevision(false);
                return pkg;
            }

            Integer newRevNum = prevRevNumEntity.getRevnNum() + 1;
            revision.setRevnNum(newRevNum.byteValue());
            revision.setPropTitl(prevRevEntity.getPropTitl());
            revision.setPropPrepSttsCode(ProposalStatus.NOT_FORWARDED_TO_SPO); // Should
                                                                               // be
                                                                               // '07'
                                                                               // for
                                                                               // PFUs
            revision.setPropPrepSttsDate(revInsertDate);
            revision.setCreatedDate(revInsertDate);
            revision.setCreatedUser(pkg.getLastUpdatedUser());
            revision.setPropPrepId(prop.getPropPrepId());
            ConverterUtility.setAuditFields(revision);
            revision.setLastUpdtUser(prop.getLastUpdtUser());
            revision.setCurrPappgVers(prevRevEntity.getCurrPappgVers());
            if (prevRevEntity.getPropDueDate() != null) {
                revision.setPropDueDate(prevRevEntity.getPropDueDate());
            }
            revision.setPropDueDateTypeCode(prevRevEntity.getPropDueDateTypeCode());
            revision.setPropDueDateUpdateDate(prevRevEntity.getPropDueDateUpdateDate());
            revision.setPropTitleUpdateDate(prevRevEntity.getPropTitleUpdateDate());

            if (prop.getNsfPropSubmDate() != null) {
                pkg.setIsAvailableForRevision(true);
                switch (copy.getRevisionType().trim()) {
                case ProposalRevisionType.PROPOSAL_FILE_UPDATE:
                    revision.setPropPrepRevnTypeCode(ProposalRevisionType.PROPOSAL_FILE_UPDATE);
                    revision.setPropPrepSttsCode(ProposalStatus.PFU_NOT_FORWARDED_TO_SPO);
                    LOGGER.debug("Type of revision: PFU");
                    break;
                case ProposalRevisionType.BUDGET_REVISION: // Budget Revision
                    revision.setPropPrepRevnTypeCode(ProposalRevisionType.BUDGET_REVISION);
                    revision.setPropPrepSttsCode(ProposalStatus.BR_NOT_SHARED_WITH_SPO_AOR);
                    LOGGER.debug("Type of revision: Budget");
                    break;
                default:
                    //
                }
            } else {
                LOGGER.debug("Proposal revision cannot be created: NSF Proposal ID = " + copy.getNsfPropId());
                pkg.setIsAvailableForRevision(false);
                return pkg;
            }

            // Get existing cover sheet
            CoverSheet cs = null;
            cs = getCoverSheet(String.valueOf(prevRevEntity.getPropPrepId()),
                    String.valueOf(prevRevEntity.getPropPrepRevnId()));
            if (cs != null) {
                cs.setLastUpdatedUser(copy.getNsfId());
                cs.setInitialCreation(true);
                if (cs.getAwdOrganization() != null) {
                    PrimaryPlaceOfPerformance ppop = ProposalCopyUtility.populatePpop(cs.getAwdOrganization());
                    if (ppop != null) {
                        cs.setPpop(ppop);
                    }
                }
            }

            // Get existing personnel
            List<Personnel> personnel = null;
            personnel = getPersonnels(String.valueOf(prevRevEntity.getPropPrepId()),
                    String.valueOf(prevRevEntity.getPropPrepRevnId()));

            // Create revision
            prop.getRevisions().add(revision);
            prop = propPrepRep.save(prop);
            ProposalPrepRevision revEntity = propPrepRevRep.getMaxRevision(prop.getPropPrepId());
            pkg.setCreateDate(revEntity.getLastUpdtTmsp());
            pkg.setPropPrepId(String.valueOf(revEntity.getPropPrepId()));
            pkg.setPropRevId(String.valueOf(revEntity.getPropPrepRevnId()));

            // Get budget
            BudgetInstitutionEntity inst = budgetInstitutionRep.findByPropRevId(prevRevEntity.getPropPrepRevnId());

            // Create institution
            InstitutionBudget instBudget = null;
            if (inst != null) {
                instBudget = new InstitutionBudget();
                instBudget.setInstId(inst.getInstId());
                instBudget.setInstPropRoleTypeCode(inst.getInstPropRoleTypeCode());
                instBudget.setPropRevId(String.valueOf(revEntity.getPropPrepRevnId()));
                instBudget.setLastUpdatedUser(pkg.getLastUpdatedUser());
                BudgetInstitutionEntity budgetInstitutionEntity = ConverterUtility
                        .convertInstitutionBudgetDtoToEntity(instBudget);
                budgetInstitutionEntity.setLastUpdtTmsp(pkg.getCreateDate());
                budgetInstitutionRep.save(budgetInstitutionEntity);
            }

            // Create cover sheet
            Institution institution = new Institution();
            if (instBudget != null && instBudget.getInstId() != null) {
                institution.setId(instBudget.getInstId());
                institution.setTaxId(cs.getEmployerTIN());
                institution.setDunsNumber(cs.getDunsNumber());
                institution.setTimeZone(cs.getTimeZone());
                institution.setLastUpdatedTmsp(pkg.getCreateDate());
                cs.setAwdOrganization(institution);
            }
            cs.setLastUpdatedTmsp(pkg.getCreateDate());
            cs.setPropRevId(String.valueOf(revEntity.getPropPrepRevnId()));
            saveCoverSheet(cs);

            // Create personnel
            List<ProposalRevisionPersonnel> personnelList = new ArrayList<>();
            if (personnel != null && !personnel.isEmpty()) {
                for (Personnel pers : personnel) {
                    pers.setLastUpdatedUser(pkg.getLastUpdatedUser());
                    pers.setPropRevnId(String.valueOf(revEntity.getPropPrepRevnId()));
                    Personnel newPers = savePersonnel(pers);
                    ProposalRevisionPersonnel propRevPers = new ProposalRevisionPersonnel();
                    propRevPers.setPrevPersonnel(pers);
                    propRevPers.setRevPersonnel(newPers);
                    propRevPers.setLastUpdatedTmsp(pkg.getCreateDate());
                    personnelList.add(propRevPers);
                }
            }

            pkg.setPersonnelList(personnelList);

            // Create budget
            BudgetInstitutionEntity revInst = budgetInstitutionRep.findByPropRevId(revEntity.getPropPrepRevnId());
            if (revInst != null) {
                List<InstitutionBudget> budgets = getBudget(String.valueOf(prevRevEntity.getPropPrepId()),
                        String.valueOf(prevRevEntity.getPropPrepRevnId()));
                if (budgets != null && !budgets.isEmpty()) {
                    for (InstitutionBudget origBudget : budgets) {
                        InstitutionBudget newBudget = ProposalCopyUtility.cloneInstitutionBudget(origBudget,
                                personnelList, pkg.getLastUpdatedPgm(), pkg.getLastUpdatedUser(), pkg.getCreateDate());
                        newBudget.setPropMessages(origBudget.getPropMessages());
                        newBudget.setInstId(revInst.getInstId());
                        newBudget.setInstPropRoleTypeCode(revInst.getInstPropRoleTypeCode());
                        newBudget.setPropRevId(String.valueOf(revEntity.getPropPrepRevnId()));
                        newBudget.setLastUpdatedUser(pkg.getLastUpdatedUser());
                        newBudget.setLastUpdatedTmsp(pkg.getCreateDate());
                        newBudget.setPropInstRecId(revInst.getPropInstRecId());
                        saveInstitutionBudget(newBudget, false);
                    }
                }
            }

            // Set latest revision ID
            pkg.setPropRevId(Long.toString(revEntity.getPropPrepRevnId()));

            try {
                updateProposalStatusHistory(String.valueOf(revEntity.getPropPrepRevnId()),
                        revision.getPropPrepSttsCode(), pkg.getLastUpdatedUser());
            } catch (Exception e) {
                throw new CommonUtilException(Constants.SAVE_PROPOSAL_STATUS_HISTORY_LOG_ERROR, e);
            }

            return pkg;
        } catch (Exception e) {
            throw new CommonUtilException(e);
        }

    }

    private CoverSheet setInititalCoverSheetData(ProposalPackage pkg, String prepRevId) {

        CoverSheet cv = new CoverSheet();
        cv.setLastUpdatedUser(pkg.getLastUpdatedUser());
        cv.setPropRevId(prepRevId);

        Institution inst = new Institution();
        inst.setId(pkg.getInstitution().getId());
        inst.setTimeZone(pkg.getInstitution().getTimeZone());
        inst.setTaxId(pkg.getInstitution().getTaxId());
        inst.setDunsNumber(pkg.getInstitution().getDunsNumber());
        cv.setAwdOrganization(inst);
        if (!ProposalDataUtility.isPPOPhasSpecialCharacters(pkg.getInstitution())) {

            PrimaryPlaceOfPerformance ppop = new PrimaryPlaceOfPerformance();
            ppop.setOrganizationName(pkg.getInstitution().getOrganizationName());
            ppop.setCountryCode(pkg.getInstitution().getAddress().getCountryCode());
            ppop.setCityName(pkg.getInstitution().getAddress().getCityName());
            ppop.setStateCode(pkg.getInstitution().getAddress().getStateCode());
            ppop.setPostalCode(pkg.getInstitution().getAddress().getPostalCode());
            ppop.setStreetAddress(pkg.getInstitution().getAddress().getStreetAddress());
            ppop.setStreetAddress2(pkg.getInstitution().getAddress().getStreetAddress2());
            cv.setPpop(ppop);
        }
        cv.setInitialCreation(true);
        return cv;
    }

    public static boolean containSpecialChars(String inputStr, String specialCharacterString) {

        for (int i = 0; i < inputStr.length(); i++) {
            if (specialCharacterString.indexOf(inputStr.charAt(i)) != -1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ProposalPackage getProposalPrepById(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProposalPrepById() --> propPrepId [" + propPrepId + "] propRevId ["
                + propRevId + "]");

        try {
            ProposalPackage propDto = null;
            String revId = null;

            ProposalPrep propEntity = propPrepRep.findOne(Long.parseLong(propPrepId));
            Integer revNum = null;
            Date createdDate = null;
            Date updateDate = null;
            Date revStatusDate = null;
            if (!StringUtils.isEmpty(propEntity.getNsfPropId())) {
                ProposalPrepRevision submittedRev = propPrepRevRep
                        .getLatestSubmittedRevision(propEntity.getNsfPropId());
                if (submittedRev != null) {
                    revStatusDate = submittedRev.getPropPrepSttsDate();
                }
            }
            String propPrepRevnTypeCode = null;

            if ("null".equalsIgnoreCase(propRevId)) {
                ProposalPrepRevision revision = propPrepRevRep.getMaxRevision(Long.valueOf(propPrepId));
                revId = revision.getPropPrepRevnId().toString();
                revNum = revision.getRevnNum().intValue();
                createdDate = revision.getCreatedDate();
                updateDate = revision.getLastUpdtTmsp();
                propPrepRevnTypeCode = revision.getPropPrepRevnTypeCode();
            } else {
                ProposalPrepRevision revision = propPrepRevRep.findOne(Long.valueOf(propRevId));
                if (revision != null) {
                    revNum = revision.getRevnNum().intValue();
                    revId = propRevId;
                    createdDate = revision.getCreatedDate();
                    updateDate = revision.getLastUpdtTmsp();
                    propPrepRevnTypeCode = revision.getPropPrepRevnTypeCode();
                }
            }

            if (propEntity == null) {
                LOGGER.info("No proposal data exists for id " + propPrepId);
                throw new Exception("No proposal data exists for this proposal id " + propPrepId);
            }

            // Get PI
            Personnel pi = null;
            ProposalPersonsEntity propPersEntity = proposalPersonalRep.getSeniorPersonnelByRole(Long.parseLong(revId),
                    PSMRole.ROLE_PI);
            if (propPersEntity != null) {
                pi = ConverterUtility.convertSeniorPersonnelEntityToDto(propPersEntity);
            }

            // Get Awardee Institution Id
            Institution awardeeInst = new Institution();
            awardeeInst.setId(getPrimaryAwardeeOrganizationId(propPrepId, revId));

            propDto = ConverterUtility.convertProposalEntityToDto(propEntity, revId);
            propDto.setRevNum(revNum);
            propDto.setProposalType(proposalTypes.get(propDto.getProposalType()));
            propDto.setSubmissionType(submissionTypes.get(propDto.getSubmissionType()));
            if (revStatusDate != null) {
                propDto.setSubmissionDate(revStatusDate);
            }
            propDto.setPropPrepId(String.valueOf(propEntity.getPropPrepId()));
            propDto.setCollabType(collabTypes.get(propDto.getCollabType()));
            propDto.setProposalStatus(propDto.getProposalStatus());
            propDto.setProposalStatusDesc(statusTypes.get(propDto.getProposalStatus()));
            propDto.setPropRevId(revId);
            if (revStatusDate != null) {
                propDto.setRevisionStatusDate(revStatusDate);
            }
            propDto.setPropPrepId(propPrepId);
            if (!StringUtils.isEmpty(propPrepRevnTypeCode)) {
                propDto.setPropPrepRevnTypeCode(propPrepRevnTypeCode.trim());
            }
            propDto.setPi(pi);
            propDto.setInstitution(awardeeInst);
            if (propEntity.getNsfPropId() != null) {
                propDto.setNsfPropId(propEntity.getNsfPropId());
            }
            propDto.setCreateDate(createdDate);
            propDto.setUpdateDate(updateDate);

            ProposalElectronicSign aorSignature = getAORSignature(propPrepId, propRevId);
            if (aorSignature != null) {

                if (aorSignature.getUserLastname() != null && aorSignature.getUserFirstName() != null
                        && aorSignature.getUserMiddleInit() != null) {
                    propDto.setAorName(aorSignature.getUserFirstName() + ' ' + aorSignature.getUserMiddleInit() + ' '
                            + aorSignature.getUserLastname());
                } else if (aorSignature.getUserLastname() != null && aorSignature.getUserFirstName() != null) {
                    propDto.setAorName(aorSignature.getUserFirstName() + ' ' + aorSignature.getUserLastname());
                } else {
                    propDto.setAorName(aorSignature.getUserLastname());
                }

                if (aorSignature.getUserPhoneNumber() != null) {
                    propDto.setAorPhoneNumber(aorSignature.getUserPhoneNumber());
                }

                if (aorSignature.getUserEmailAddress() != null) {
                    propDto.setAorEmail(aorSignature.getUserEmailAddress());
                }

                if (aorSignature.getUserFaxNumber() != null) {
                    propDto.setAorFaxNumber(aorSignature.getUserFaxNumber());
                }

                if (aorSignature.getElecSignDate() != null) {
                    propDto.setAorElecSignDate(aorSignature.getElecSignDate());
                }
                propDto.setDebrFlag(aorSignature.getDebarFlag());
                propDto.setDebrFlagExpLanation(aorSignature.getDebarText());

            }

            ProposalPrepRevision rev = propPrepRevRep.getMaxRevision(Long.valueOf(propPrepId));
            if (rev != null) {
                propDto.setLatestPropRevId(String.valueOf(rev.getPropPrepRevnId()));
                if (rev.getPropPrepRevnTypeCode().equalsIgnoreCase(ProposalRevisionType.PROPOSAL_FILE_UPDATE)) {
                    propDto.setHasPFU(true);
                }
                if (rev.getPropPrepSttsCode().trim().equals(ProposalStatus.SUBMITTED_TO_NSF)) {
                    propDto.setIsAvailableForRevision(true);
                    propDto.setHasPFU(false);
                } else {
                    propDto.setIsAvailableForRevision(false);
                    propDto.setHasPFU(true);
                }
            }

            ProposalPrepRevision submittedRev = propPrepRevRep.getLatestRevision(Long.valueOf(propPrepId));
            if (submittedRev != null) {
                LOGGER.info("***********Latest Submitted Revision Id : " + submittedRev.getPropPrepRevnId());
                propDto.setLatestSubmittedRevId(String.valueOf(submittedRev.getPropPrepRevnId()));
                ProposalPersonsEntity latestSubmittedPropPersEntity = proposalPersonalRep
                        .getSeniorPersonnelByRole(submittedRev.getPropPrepRevnId(), PSMRole.ROLE_PI);
                if (latestSubmittedPropPersEntity != null) {
                    propDto.setLatestSubmittedPiNsfId(
                            (ConverterUtility.convertSeniorPersonnelEntityToDto(latestSubmittedPropPersEntity))
                                    .getNsfId());
                }
            }
            propDto.setTimeZone(CoverSheetUtility.getCoverSheet(propDto.getPropRevId(),cvRep).getTimeZone());
            return propDto;

        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_PREP_ERROR, e);
        }

    }

    @Override
    public ProjectSummary getProjectSummary(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProjectSummary()");
        try {
            ProjectSummaryEntity projectSummaryEntity = projectSummaryRep.findOne(Long.valueOf(propRevId));
            if (projectSummaryEntity == null) {
                return new ProjectSummary();
            }
            return ConverterUtility.convertProjectSummaryEntityToDto(projectSummaryEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROJECT_SUMMARY_ERROR, e);
        }

    }

    @Override
    public boolean saveUploadableProposalSection(UploadableProposalSection ups, String propPrepId, String propRevId,
            Section section, Map<String, Object> metaData) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveUploadableProposalSection()");

        try {
            boolean status = false;
            List<PSMMessage> psmMsgs = ups.getPropMessages();
            LOGGER.info("ProposalDataServiceImpl.saveUploadableProposalSection() psmMsgs List : " + psmMsgs);
            String secCode = section.getCode();
            String lupdUser = ups.getLastUpdatedUser();

            switch (section) {
            case PROJ_SUMM:
                status = saveProjectSummary(ups, Long.valueOf(propRevId));
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;

            case REF_CITED:
                status = saveReferenceCited(ups, Long.valueOf(propRevId));
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;

            case FER:
                status = saveFacilitiesEquipment(ups, Long.valueOf(propRevId));
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;

            case PROJ_DESC:
                status = saveProjectDescription(ups, Long.valueOf(propRevId));
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;

            case BUDGET_JUST:
                status = saveBudgetJustification(ups, Long.valueOf(propRevId));
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;
            case SRL:
                status = saveSuggestedReviewers(ups, Long.valueOf(propRevId));
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;

            case OPBIO:
                status = UploadUtility.saveOthrPersBioInfo(ups, Long.valueOf(propRevId), othPersRep, propPrepRevRep);
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;

            case RNI:
                status = UploadUtility.saveReviewersNotInclude(ups, Long.valueOf(propRevId), rvrsNotInclRep,
                        propPrepRevRep);
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;
            case DMP:
                status = UploadUtility.saveDataManagementPlan(ups, Long.valueOf(propRevId), dmpRep, propPrepRevRep);
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;
            case PMP:
                status = UploadUtility.savePostDocMentoringPlan(ups, Long.valueOf(propRevId), pdocMPRep,
                        propPrepRevRep);
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;

            case OSD:
                status = UploadUtility.saveOtherSuppDocs(ups, Long.valueOf(propRevId), otherSuppDocRep, propPrepRevRep);
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;

            case BUDI:
                status = UploadUtility.saveBudgetImpact(ups, Long.valueOf(propRevId), bImpactRep, propPrepRevRep);
                if (psmMsgs != null && !psmMsgs.isEmpty()) {
                    saveProposalMessages(propPrepId, propRevId, "persId", secCode, lupdUser, psmMsgs);
                }
                break;

            default:
                break;
            }

            return status;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_UPLOADABLE_PROPOSAL_SECTION_ERROR, e);
        }

    }

    public void deleteProjectSummary(Long propRevId, String nsfId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteProjectSummary()");
        try {

            ProjectSummaryEntity psEntity = projectSummaryRep.findOne(propRevId);

            psEntity.setPropPrepRevisionId(propRevId);
            psEntity.setProjectSummaryDocFileLocation("");
            psEntity.setProjectSummaryOrigFileName("");
            psEntity.setProjectSummaryDocPageCount(0);
            psEntity.setProjectSummaryOverViewText(
                    StringEscapeUtils.escapeHtml(psEntity.getProjectSummaryOverViewText()));
            psEntity.setProjectSummaryBroderImpactText(
                    StringEscapeUtils.escapeHtml(psEntity.getProjectSummaryBroderImpactText()));
            psEntity.setProjectSummaryIntellectualMeritText(
                    StringEscapeUtils.escapeHtml(psEntity.getProjectSummaryIntellectualMeritText()));
            ConverterUtility.setAuditFields(psEntity);
            psEntity.setLastUpdtUser(nsfId);

            projectSummaryRep.save(psEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_PROJECT_SUMMARY_ERROR, e);
        }
    }

    public void deleteReferenceCited(Long propRevId, String nsfId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteReferenceCited()");
        try {
            ReferencesCitedEntity referenceCitedEntity = referenceCitedRepository.findOne(propRevId);
            referenceCitedEntity.setRefCitnDocfileLoc("");
            referenceCitedEntity.setRefCitnDocpageCount(0);
            referenceCitedEntity.setRefCitnText("");
            referenceCitedEntity.setRefCitnOrigFileName("");
            ConverterUtility.setAuditFields(referenceCitedEntity);
            referenceCitedEntity.setLastUpdtUser(nsfId);
            referenceCitedRepository.save(referenceCitedEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_REFERENCE_CITED_ERROR, e);
        }
    }

    public void deleteSuggestedReviewers(Long propRevId, String nsfId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteSuggestedReviewers()");
        try {
            SuggestedReviewerEntity srEntity = suggRevrRep.findOne(propRevId);
            srEntity.setSuggRevrFileLocation("");
            srEntity.setSuggRevrDocPageCount(0);
            srEntity.setSuggRevrDocText("");
            srEntity.setSuggRevrDocOrigFileName("");
            ConverterUtility.setAuditFields(srEntity);
            srEntity.setLastUpdtUser(nsfId);
            suggRevrRep.save(srEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_SUGGESTED_REVIEWERS_ERROR, e);
        }
    }

    public void deleteFacilitiesEquipment(Long propRevId, String nsfId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteFacilitiesEquipment()");
        try {
            FacilityEquipmentEntity facEquipEntity = facEquipRep.findOne(propRevId);
            facEquipEntity.setPropPrepRevnId(propRevId);
            facEquipEntity.setFacltEquipDocFileLocation("");
            facEquipEntity.setFacltEquipDocPageCount(0);
            facEquipEntity.setFacltEquipText("");
            facEquipEntity.setFacltEquipOrgFileName("");
            ConverterUtility.setAuditFields(facEquipEntity);
            facEquipEntity.setLastUpdtUser(nsfId);
            facEquipRep.save(facEquipEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_FAC_EQUP_ERROR, e);
        }
    }

    public void deleteProjectDescription(Long propRevId, String nsfId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteProjectDescription()");
        try {
            ProjectDescriptionEntity projDescEntity = projDescRep.findOne(propRevId);
            projDescEntity.setPropPrepRevnId(propRevId);
            projDescEntity.setProjDescFileLocation("");
            projDescEntity.setProjDescDocPageCount(0);
            projDescEntity.setProjDescDocText("");
            projDescEntity.setProjDescDocOrigFileName("");
            ConverterUtility.setAuditFields(projDescEntity);
            projDescEntity.setLastUpdtUser(nsfId);
            projDescRep.save(projDescEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_PROJECT_DESC_ERROR, e);
        }
    }

    public void deleteBudgetJustification(Long propRevId, String nsfId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteBudgetJustification()");
        try {
            BudgetJustificationEntity budgJustEntity = budgJustRep.findOne(propRevId);
            budgJustEntity.setPropPrepRevnId(propRevId);
            budgJustEntity.setBudgJustFileLocation("");
            budgJustEntity.setBudgJustDocPageCount(0);
            budgJustEntity.setBudgJustText(budgJustEntity.getBudgJustText());
            budgJustEntity.setBudgJustOrigFileName("");
            ConverterUtility.setAuditFields(budgJustEntity);
            budgJustEntity.setLastUpdtUser(nsfId);
            budgJustRep.save(budgJustEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_BUDGET_JUST_ERROR, e);
        }
    }

    public void deleteBioSketches(String propRevId, String propPersId, String nsfId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteBioSketches()");
        try {
            BiographicalSketchEntity bioSketchEntity = ConverterUtility.getNewBiographicalSketchEntity(propRevId,
                    propPersId, nsfId);
            LOGGER.debug("ProposalDataServiceImpl.deleteSeniorPersonnelDocuments() BiographicalSketchEntity : "
                    + bioSketchEntity.toString());
            bioSketchRep.save(bioSketchEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_BIOSKETCHES_ERROR, e);
        }
    }

    public void deleteCurrentAndPendingSupport(String propRevId, String propPersId, String nsfId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteCurrentAndPendingSupport()");
        try {
            CurrentAndPendingSupportEntity cpsEntity = ConverterUtility.getNewCurrentAndPendingSupportEntity(propRevId,
                    propPersId, nsfId);
            LOGGER.debug("ProposalDataServiceImpl.deleteSeniorPersonnelDocuments() CurrentAndPendingSupportEntity : "
                    + cpsEntity.toString());
            cpsRep.save(cpsEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_CURRENT_AND_PENDING_SUPPORT_ERROR, e);
        }
    }

    private Long getProposalRevisionId(String propPrepId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProposalRevisionId()");
        try {
            Long propRevId = null;
            ProposalPrep propPrepRes = propPrepRep.findOne(Long.valueOf(propPrepId));
            if (propPrepRes == null) {
                return propRevId;
            }

            // Retrieve saved proposal prep revisions from DB
            Set<ProposalPrepRevision> revs = propPrepRes.getRevisions();

            // Cycle through the associated proposal prep revisions
            for (ProposalPrepRevision rev : revs) {
                // identify the "active" revision
                propRevId = rev.getPropPrepRevnId();
                break;
            }
            return propRevId;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_REV_ID_ERROR, e);
        }
    }

    private Long getInstRecordId(Long propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getInstRecordId()");
        try {
            BudgetInstitutionEntity budgetInstitutionEntity = budgetInstitutionRep.findByPropRevId(propRevId);

            return budgetInstitutionEntity.getPropInstRecId();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPSAL_INST_RECORD_ID_ERROR, e);
        }
    }

    private boolean saveProjectSummary(UploadableProposalSection summary, Long propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveProjectSummary()");
        try {
            ProjectSummaryEntity projectSummaryEntity = ConverterUtility.convertProjectSummaryDtoToEntity(summary,
                    propRevId);
            ProjectSummaryEntity entity = projectSummaryRep.save(projectSummaryEntity);
            if (entity != null) {
                ProposalDataUtility.updateRevisionLastUpdateTS(entity.getLastUpdtTmsp(), propRevId, propPrepRevRep);
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PRORJECT_SUMMARY_ERROR, e);
        }
        return true;
    }

    public boolean saveReferenceCited(UploadableProposalSection uploadableProposalSection, Long propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveReferenceCited()");
        try {
            ReferencesCitedEntity referenceCitedEntity = ConverterUtility
                    .convertReferenceCitedDtoToEntity(uploadableProposalSection, propRevId);
            ReferencesCitedEntity entity = referenceCitedRepository.save(referenceCitedEntity);
            if (entity != null) {
                ProposalDataUtility.updateRevisionLastUpdateTS(entity.getLastUpdtTmsp(), propRevId, propPrepRevRep);
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_REFERENCE_CITED_ERROR, e);
        }
        return true;
    }

    @Override
    public boolean deleteUploadableProposalSection(String propPrepId, String propRevId, Section section, String nsfId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteUploadableProposalSection()");
        try {
            switch (section) {

            case PROJ_SUMM:
                deleteProjectSummary(Long.valueOf(propRevId), nsfId);
                break;

            case REF_CITED:
                deleteReferenceCited(Long.valueOf(propRevId), nsfId);
                break;

            case FER:
                deleteFacilitiesEquipment(Long.valueOf(propRevId), nsfId);
                break;

            case PROJ_DESC:
                deleteProjectDescription(Long.valueOf(propRevId), nsfId);
                // LOGGER.info("Deleting Proposal WarningMessages");
                // propMsgRep.delete(Integer.parseInt(propPrepId),
                // Integer.parseInt(propRevId),
                // section.PROJ_DESC.getCode());
                break;
            case BUDGET_JUST:
                deleteBudgetJustification(Long.valueOf(propRevId), nsfId);
                break;

            case SRL:
                deleteSuggestedReviewers(Long.valueOf(propRevId), nsfId);
                break;
            case OPBIO:
                UploadUtility.deleteOthrPersBioInfo(Long.valueOf(propRevId), othPersRep, nsfId);
                break;
            case RNI:
                UploadUtility.deleteReviewersNotInclude(Long.valueOf(propRevId), rvrsNotInclRep, nsfId);
                break;
            case DMP:
                UploadUtility.deleteDataManagementPlan(Long.valueOf(propRevId), dmpRep, nsfId);
                break;
            case PMP:
                UploadUtility.deletePostDocMentoringPlan(Long.valueOf(propRevId), pdocMPRep, nsfId);
                break;
            case OSD:
                UploadUtility.deleteOtherSuppDocs(Long.valueOf(propRevId), otherSuppDocRep, nsfId);
                break;
            case BUDI:
                UploadUtility.deleteBudgetImpact(Long.valueOf(propRevId), bImpactRep, nsfId);
                break;

            default:
                break;
            }

            LOGGER.info("Deleting Proposal WarningMessages section code : " + section.getCode());
            propMsgRep.delete(Integer.parseInt(propPrepId), Integer.parseInt(propRevId), section.getCode());

        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_UPLOADABLE_PROPOSAL_SECTION_ERROR, e);
        }
        return true;
    }

    @Override
    public boolean saveInstitutionBudget(InstitutionBudget instBudget, boolean pcvFlag) throws CommonUtilException {

        LOGGER.debug("ProposalDataServiceImpl.saveInstitutionBudget()");
        Monitor saveBudgetMonitor = MonitorFactory.start("SaveBudgetMonitor");

        try {
            // Section: A
            saveSeniorPersonalBudget(instBudget);
            // B.Section
            saveOtherPersonalBudget(instBudget);

            // C.Section
            saveFringeBenfitBudget(instBudget);

            // D:Section
            saveEquipmentBudget(instBudget);

            // E:Section
            saveTraveBudget(instBudget);

            // F:Section
            saveParticipantSupportBudget(instBudget);

            // G:Section
            saveOtherDirectCostBudget(instBudget);

            // I:Section
            saveIndirectCostBudget(instBudget);

            // Save Warning/Error Messages
            saveProposalMessages(instBudget.getPropPrepId(), instBudget.getPropRevId(), "persId",
                    Section.BUDGETS.getCode().trim(), instBudget.getLastUpdatedUser(), instBudget.getPropMessages());

            // update pcv check indication in Institution table.
            BudgetInstitutionEntity instEntity = budgetInstitutionRep
                    .findByPropRevId(Long.valueOf(instBudget.getPropRevId()));
            instEntity.setPcvCheckIndicator(pcvFlag);
            budgetInstitutionRep.save(instEntity);

            saveBudgetMonitor.stop();
            LOGGER.info("ProposalDataServiceImpl.saveInstitutionBudget() : " + saveBudgetMonitor);
            ProposalDataUtility.updateRevisionLastUpdateTS(new Date(), Long.valueOf(instBudget.getPropRevId()),
                    propPrepRevRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_INSTITUTION_BUDGET_ERROR, e);
        }

        return true;
    }

    @Override
    public InstitutionBudget getInstitutionBudget(String propPrepId, String propRevId, String instId)
            throws CommonUtilException {

        LOGGER.debug("ProposalDataServiceImpl.getInstitutionBudget()");
        try {
            InstitutionBudget institutionBudgetDto = null;

            Long propInstRecId = getInstRecordId(Long.valueOf(propRevId));

            if (propInstRecId == null) {
                throw new CommonUtilException(
                        "No Proposal Institution exists for this proposal preparation id " + propPrepId);
            }

            BudgetInstitutionEntity bie = budgetInstitutionRep.findOne(propInstRecId);

            // Get latest update date from all cost tables
            List<Date> updateDates = new ArrayList<>();
            Date maxUpdateDateBC = seniorPersonsBCRep.getMaxLastUpdateDate(propInstRecId);
            if (maxUpdateDateBC != null) {
                updateDates.add(maxUpdateDateBC);
            }
            Date maxUpdateDateOP = otherPersonsBudgetCostRep.getMaxLastUpdateDate(propInstRecId);
            if (maxUpdateDateOP != null) {
                updateDates.add(maxUpdateDateOP);
            }
            Date maxUpdateDateFringe = fringeBenefitBudgetRep.getMaxLastUpdateDate(propInstRecId);
            if (maxUpdateDateFringe != null) {
                updateDates.add(maxUpdateDateFringe);
            }
            Date maxUpdateDateEquip = equipBCRep.getMaxLastUpdateDate(propInstRecId);
            if (maxUpdateDateEquip != null) {
                updateDates.add(maxUpdateDateEquip);
            }
            Date maxUpdateDateTravel = travelBCRep.getMaxLastUpdateDate(propInstRecId);
            if (maxUpdateDateTravel != null) {
                updateDates.add(maxUpdateDateTravel);
            }
            Date maxUpdateDateSC = partSCEntityRep.getMaxLastUpdateDate(propInstRecId);
            if (maxUpdateDateSC != null) {
                updateDates.add(maxUpdateDateSC);
            }
            Date maxUpdateDateOther = otherDCEntityRep.getMaxLastUpdateDate(propInstRecId);
            if (maxUpdateDateOther != null) {
                updateDates.add(maxUpdateDateOther);
            }
            Date maxUpdateDateInd = indirectBCRep.getMaxLastUpdateDate(propInstRecId);
            if (maxUpdateDateInd != null) {
                updateDates.add(maxUpdateDateInd);
            }

            // Section - A Senior Personnel Cost
            List<SeniorPersonsBudgetCostEntity> spbceList = seniorPersonsBCRep.findByPropInstRecId(propInstRecId);

            List<ProposalPersonsEntity> propPersonalEntityList = proposalPersonalRep
                    .findByPropPrepRevisionId(Long.valueOf(propRevId));

            // Section - B Other Personnel Budget
            List<OtherPersonsBudgetCostEntity> opbceList = otherPersonsBudgetCostRep.findByPropInstRecId(propInstRecId);

            // Section - C Fringe Benefit
            List<FringeBenefitBudgetEntity> fbbeList = fringeBenefitBudgetRep.findByPropInstRecId(propInstRecId);

            // Section - D Equipment Cost
            List<EquipmentBudgetCostEntity> ebceList = equipBCRep.findByPropInstRecId(propInstRecId);

            // Section- E Travel Cost
            List<TravelBudgetCostEntity> tbceList = travelBCRep.findByPropInstRecId(propInstRecId);

            // Section - F Participant Support Cost
            List<ParticipantsSupportCostEntity> psceList = partSCEntityRep.findByPropInstRecId(propInstRecId);

            // Section - G Other Direct Cost
            List<OtherDirectCostEntity> odceList = otherDCEntityRep.findByPropInstRecId(propInstRecId);

            // Section - I Indirect Cost
            List<IndirectBudgetCostEntity> ibceList = indirectBCRep.findByPropInstRecId(propInstRecId);

            InstitutionBudgetParameter param = new InstitutionBudgetParameter();
            param.setBie(bie);
            param.setEbceList(ebceList);
            param.setFbbeList(fbbeList);
            param.setIbceList(ibceList);
            param.setOdceList(odceList);
            param.setOpbceList(opbceList);
            param.setPropPersList(propPersonalEntityList);
            param.setPsceList(psceList);
            param.setTbceList(tbceList);
            param.setSpbceList(spbceList);

            institutionBudgetDto = ConverterUtility.convertBudgetEntitiestoDto(propPrepId, param);

            if (!updateDates.isEmpty()) {
                institutionBudgetDto.setLastUpdtTmsp(Collections.max(updateDates));
            }

            // Get Proposal Warning Messages.
            WarnMsgs warnMsgs = getProposalWarningMessages(propPrepId, propRevId, Constants.ZERO,
                    Section.BUDGETS.getCode());
            if (warnMsgs != null && !warnMsgs.getPsmMsgList().isEmpty()) {
                LOGGER.debug("ProposalDataServiceImpl.getInstitutionBudget()  :: getPsmMsgList() :: "
                        + warnMsgs.getPsmMsgList());
                institutionBudgetDto.setPropMessages(warnMsgs.getPsmMsgList());
            }

            // Check for to display Budget Impact Statement.

            ProposalRevision rev = getProposalRevision(propPrepId, propRevId);
            String revType = rev.getRevisionType().getType().trim();
            boolean displayAlertMsg = false;
            if ((revType.equalsIgnoreCase(ProposalRevisionType.PROPOSAL_FILE_UPDATE)
                    || revType.equalsIgnoreCase(ProposalRevisionType.BUDGET_REVISION)) && (bie.isPcvCheckIndicator())) {
                BudgetImpactEntity bImpEntity = bImpactRep.findOne(Long.valueOf(propRevId));
                if (bImpEntity != null) {
                    String fileLoc = bImpEntity.getBudgImpactFileLocation();
                    LOGGER.debug(" Budget Impact File Location : " + fileLoc);
                    if (StringUtils.isEmpty(fileLoc.trim())) {
                        displayAlertMsg = true;
                    }
                } else {
                    displayAlertMsg = true;
                }
            }
            LOGGER.debug("Revision Type [" + revType + "]  PCV Check Indicator [" + bie.isPcvCheckIndicator()
                    + "] Display Alert Message [" + displayAlertMsg + "]");
            institutionBudgetDto.setBudgetImpactAlertMsgDisplay(displayAlertMsg);
            return institutionBudgetDto;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_INSTITUTION_BUDGET_ERROR, e);
        }
    }

    @Override
    public ReferencesCited getReferenceCited(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getReferenceCited()");
        try {
            ReferencesCitedEntity referenceCitedEntity = referenceCitedRepository.findOne(Long.valueOf(propRevId));
            if (referenceCitedEntity == null) {
                return new ReferencesCited();
            }
            return ConverterUtility.convertReferenceCitedEntityToDto(referenceCitedEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_REFERENCE_CITED_ERROR, e);
        }
    }

    @Override
    public List<InstitutionBudget> getBudget(String propPrepId, String propRevId) throws CommonUtilException {
        try {

            List<InstitutionBudget> institutionsBudgetList = new ArrayList<InstitutionBudget>();
            BudgetInstitutionEntity budgetInstitutionEntity = budgetInstitutionRep
                    .findByPropRevId(Long.valueOf(propRevId));

            if (budgetInstitutionEntity != null) {
                InstitutionBudget institutionBudgetDto = null;
                institutionBudgetDto = getInstitutionBudget(propPrepId, propRevId, budgetInstitutionEntity.getInstId());
                institutionsBudgetList.add(institutionBudgetDto);
            }

            return institutionsBudgetList;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_BUDGET_ERROR, e);
        }
    }

    public boolean saveFacilitiesEquipment(UploadableProposalSection uploadableProposalSection, Long propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveFacilitiesEquipment()");
        try {
            FacilityEquipmentEntity facEqupEntity = ConverterUtility
                    .convertFacilitiesEquipmentDtoToEntity(uploadableProposalSection, propRevId);
            FacilityEquipmentEntity entity = facEquipRep.save(facEqupEntity);
            if (entity != null) {
                ProposalDataUtility.updateRevisionLastUpdateTS(entity.getLastUpdtTmsp(), propRevId, propPrepRevRep);
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_FAC_EQUP_ERROR, e);
        }
        return true;
    }

    public boolean saveProjectDescription(UploadableProposalSection uploadableProposalSection, Long propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveProjectDescription()");
        try {
            ProjectDescriptionEntity projDescEntity = ConverterUtility
                    .convertProjectDescriptionDtoToEntity(uploadableProposalSection, propRevId);
            projDescEntity = projDescRep.save(projDescEntity);
            if (projDescEntity != null) {
                ProposalDataUtility.updateRevisionLastUpdateTS(projDescEntity.getLastUpdtTmsp(), propRevId,
                        propPrepRevRep);
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PROJECT_DESC_ERROR, e);
        }
        return true;
    }

    public boolean saveSuggestedReviewers(UploadableProposalSection uploadableProposalSection, Long propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveSuggestedReviewers()");
        try {
            SuggestedReviewerEntity srEntity = ConverterUtility
                    .convertToSuggestedReviewerEntity(uploadableProposalSection, propRevId);
            SuggestedReviewerEntity entity = suggRevrRep.save(srEntity);
            if (entity != null) {
                ProposalDataUtility.updateRevisionLastUpdateTS(entity.getLastUpdtTmsp(), propRevId, propPrepRevRep);
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_SUGGESTED_REVIEWERS_ERROR, e);
        }
        return true;
    }

    public boolean saveBudgetJustification(UploadableProposalSection uploadableProposalSection, Long propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveBudgetJustification()");
        try {
            BudgetJustificationEntity budgJustEntity = ConverterUtility
                    .convertBudgetJustificationDtoToEntity(uploadableProposalSection, propRevId);
            BudgetJustificationEntity entity = budgJustRep.save(budgJustEntity);
            if (entity != null) {
                ProposalDataUtility.updateRevisionLastUpdateTS(entity.getLastUpdtTmsp(), propRevId, propPrepRevRep);
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_BUDGET_JUST_ERROR, e);
        }
        return true;
    }

    public boolean saveBioSketch(UploadableProposalSection uploadableProposalSection, Long propRevId, Long propPersId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveBioSketch()");
        try {
            BiographicalSketchEntity bioSketchEntity = ConverterUtility
                    .convertBiographicalSketchDtoToEntity(uploadableProposalSection, propPersId, propRevId);
            bioSketchRep.save(bioSketchEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_BIO_SKETCH_ERROR, e);
        }
        return true;
    }

    public boolean saveCurrentAndPendingSupport(UploadableProposalSection uploadableProposalSection, Long propRevId,
            Long propPersId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveCurrentAndPendingSupport()");
        try {
            CurrentAndPendingSupportEntity cpsEntity = ConverterUtility
                    .convertCurrentAndPendingSupportDtoToEntity(uploadableProposalSection, propPersId, propRevId);
            cpsRep.save(cpsEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_CURRENT_PENDING_SUPPORT_ERROR, e);
        }
        return true;
    }

    @Override
    public FacilitiesEquipment getFacilitiesEquipment(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getFacilitiesEquipment()");
        try {
            FacilityEquipmentEntity facEquipEntity = facEquipRep.findOne(Long.valueOf(propRevId));
            if (facEquipEntity == null) {
                return new FacilitiesEquipment();
            }
            return ConverterUtility.convertFacilitiesEquipmentEntityToDto(facEquipEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_FAC_EQUIPMENT_ERROR, e);
        }
    }

    @Override
    public Personnel getPersonnel(String propPrepId, String propRevId, String propPersId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getPersonnel()");
        try {
            ProposalPersonsEntity pe = proposalPersonalRep.findOne(Long.valueOf(propPersId));
            if (pe != null) {
                return ConverterUtility.convertSeniorPersonnelEntityToDto(pe);
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PERSONNEL_ERROR, e);
        }
        return new Personnel();
    }

    @Override
    public List<Personnel> getPersonnels(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getPersonnels()");
        List<Personnel> srPersDtoList = new ArrayList<Personnel>();

        try {
            List<ProposalPersonsEntity> proposalPersonsEntityList = proposalPersonalRep
                    .findByPropPrepRevisionId(Long.valueOf(propRevId));
            if (proposalPersonsEntityList.isEmpty()) {
                LOGGER.info("Personnel is not exists for this proposal [" + Constants.REVISION_ID + propRevId + "]");
                return new ArrayList<Personnel>();
            }
            for (ProposalPersonsEntity pe : proposalPersonsEntityList) {
                Personnel spDto = ConverterUtility.convertSeniorPersonnelEntityToDto(pe);
                srPersDtoList.add(spDto);
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PERSONNELS_ERROR, e);

        }
        return srPersDtoList;
    }

    @Override
    public Personnel savePersonnel(Personnel personnel) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.savePersonnel()");
        try {
            ProposalPersonsEntity proposalPersonsEntity = proposalPersonalRep
                    .save(ConverterUtility.convertSeniorPersonnelDtoToEntity(personnel));

            Personnel newPersonnel = ConverterUtility.convertSeniorPersonnelEntityToDto(proposalPersonsEntity);
            // check if budget exits, if yes, insert new senior personnel record
            // in Senior Personnel cost table.
            List<SeniorPersonsBudgetCostEntity> spbceList = isPersonnelBudgetExists(personnel);

            if (!spbceList.isEmpty() && (!personnel.getPSMRole().getCode().trim().equalsIgnoreCase(PSMRole.ROLE_OAU))) {
                List<SeniorPersonsBudgetCostEntity> persList = seniorPersonsBCRep
                        .findByPropPersonId(spbceList.get(0).getPropPersonId());

                SeniorPersonnelCost spc = new SeniorPersonnelCost();
                spc.setSeniorPersonDollarAmount(BigDecimal.valueOf(0));
                spc.setSeniorPersonMonthCount(0);
                spc.setSeniorPersonJustificationText("");
                spc.setPropPersId(String.valueOf(proposalPersonsEntity.getProposalPersonId()));
                spc.setLastUpdatedUser(personnel.getLastUpdatedUser());

                for (SeniorPersonsBudgetCostEntity spbce : persList) {
                    // A.Section -Data need to Insert into
                    // prop_budg_sr_pers_cst table
                    SeniorPersonsBudgetCostEntity seniorPersonsBudgetCostEntity = ConverterUtility
                            .convertSeniorPersonsBudgetCostEntityDtoToEntity(spc, spbce.getPropInstRecId(),
                                    spbce.getBudgetYear());

                    LOGGER.info(
                            "Inserting : seniorPersonsBudgetCostEntity : " + seniorPersonsBudgetCostEntity.toString());
                    seniorPersonsBCRep.save(seniorPersonsBudgetCostEntity);

                }
            }

            ProposalPrepRevision rev = propPrepRevRep.findOne(Long.valueOf(personnel.getPropRevnId()));
            if (rev != null) {
                rev.setLastUpdtTmsp(new Date());
                propPrepRevRep.save(rev);
            }

            return newPersonnel;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PERSONNEL_ERROR, e);
        }

    }

    public List<SeniorPersonsBudgetCostEntity> isPersonnelBudgetExists(Personnel personnel) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.isPersonnelBudgetExists()");
        try {
            Long propInstRecId = getInstRecordId(Long.valueOf(personnel.getPropRevnId()));
            return seniorPersonsBCRep.findByPropInstRecId(propInstRecId);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.CHECK_IS_PERSONNEL_BUDGET_EXISTS_ERROR, e);
        }
    }

    @Override
    @Transactional
    public boolean deletePersonnel(String propPrepId, String propRevId, String propPersId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deletePersonnel()");

        boolean deleteStatus = false;
        Long propPersonId = Long.valueOf(propPersId);
        try {

            deleteStatus = deleteSeniorPersonnelBudgetCost(propPersonId, Long.valueOf(propRevId));
            if (bioSketchRep.exists(propPersonId)) {
                bioSketchRep.delete(propPersonId);
                propMsgRep.deleteSrPerson(Integer.parseInt(propRevId), Integer.parseInt(propPersId),
                        Section.BIOSKETCH.getCode());
            }

            if (cpsRep.exists(propPersonId)) {
                cpsRep.delete(propPersonId);
            }

            ProposalCOAEntity entity = propCOARep.findByPersIdAndRevnId(Long.valueOf(propRevId),
                    Long.valueOf(propPersId));
            if (entity != null) {
                propCOARep.delete(entity);
            }

            proposalPersonalRep.delete(propPersonId);

            budgetInstitutionRep.updateBudgetDateTime(Long.valueOf(propRevId));
            budgetInstitutionRep.updatePcvIndicator(Long.valueOf(propRevId));

            ProposalDataUtility.updateRevisionLastUpdateTS(new Date(), Long.valueOf(propRevId), propPrepRevRep);

        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_PERSONNEL_ERROR, e);
        }

        return deleteStatus;
    }

    public boolean deleteSeniorPersonnelBudgetCost(Long propPersId, Long propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteSeniorPersonnelBudgetCost()");
        try {
            List<SeniorPersonsBudgetCostEntity> spbceList = seniorPersonsBCRep.findByPropPersonId(propPersId);
            if (spbceList != null && !spbceList.isEmpty()) {
                for (SeniorPersonsBudgetCostEntity spbceEntity : spbceList) {
                    LOGGER.info("Deleting deleteSeniorPersonnelBudgetCost : " + spbceEntity.toString());
                    seniorPersonsBCRep.delete(spbceEntity);
                }
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_SENIOR_PERSONNEL_BUDGET_ERROR, e);
        }
        return true;
    }

    @Override
    public ProjectDescription getProjectDescription(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProjectDescription()");
        try {
            ProjectDescriptionEntity projDescEntity = projDescRep.findOne(Long.valueOf(propRevId));
            if (projDescEntity == null) {
                LOGGER.info("Project Description is not exists for this proposal [propPrepId: " + propPrepId
                        + Constants.REVISION_ID + propRevId + "]");
                return new ProjectDescription();
            }
            return ConverterUtility.convertProjectDescriptionEntityToDto(projDescEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROJECT_DESC_ERROR, e);
        }
    }

    @Override
    public BudgetJustification getBudgetJustification(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getBudgetJustification()");
        try {
            BudgetJustificationEntity budgJustEntity = budgJustRep.findOne(Long.valueOf(propRevId));
            if (budgJustEntity == null) {
                LOGGER.info("Budget Justification is not exists for this proposal [propPrepId: " + propPrepId
                        + Constants.REVISION_ID + propRevId + "]");
                return new BudgetJustification();
            }
            return ConverterUtility.convertBudgetJustificationEntityToDto(budgJustEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_BUDGET_JUST_ERROR, e);
        }
    }

    @Override
    public BiographicalSketch getBiographicalSketch(String propPrepId, String propRevId, String propPersId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getBiographicalSketch()");
        try {
            BiographicalSketchEntity bioSketchEntity = bioSketchRep.findOne(Long.valueOf(propPersId));
            if (bioSketchEntity == null) {
                LOGGER.info("Bio Sketch is not exists for this proposal [propPrepId: " + propPrepId
                        + Constants.REVISION_ID + propRevId + " PropPersId : = " + propPersId + "]");
                return new BiographicalSketch();
            }

            return ConverterUtility.convertBiographicalSketchEntityToDto(bioSketchEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_BIO_SKETCH_ERROR, e);
        }
    }

    @Override
    public List<BiographicalSketch> getBiographicalSketchesForProposal(String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getBiographicalSketchesForProposal()");
        try {
            List<BiographicalSketch> bioSketches = new ArrayList<>();
            List<BiographicalSketchEntity> bioSketchEntities = bioSketchRep
                    .getBioSketchesForProposal(Long.valueOf(propRevId));
            if (bioSketchEntities == null || bioSketchEntities.isEmpty()) {
                LOGGER.info("Bio sketches do not exist for this proposal [" + Constants.REVISION_ID + propRevId + "]");
                return new ArrayList<>();
            }
            for (BiographicalSketchEntity bioSketchEntity : bioSketchEntities) {
                bioSketches.add(ConverterUtility.convertBiographicalSketchEntityToDto(bioSketchEntity));
            }
            return bioSketches;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_BIO_SKETCHS_ERROR, e);
        }
    }

    @Override
    public boolean saveUploadableSeniorPersonnelDocuments(UploadableProposalSection uploadableProposalSection,
            String propPrepId, String propRevId, String propPersId, Section section, Map<String, Object> metaData)
            throws CommonUtilException {

        LOGGER.debug("ProposalDataServiceImpl.saveUploadableSeniorPersonnelDocuments()");
        try {

            boolean status = false;

            switch (section) {

            case BIOSKETCH:
                status = saveBioSketch(uploadableProposalSection, Long.valueOf(propRevId), Long.valueOf(propPersId));
                saveProposalMessages(propPrepId, propRevId, propPersId, section.BIOSKETCH.getCode(),
                        uploadableProposalSection.getLastUpdatedUser(), uploadableProposalSection.getPropMessages());
                break;

            case CURR_PEND_SUPP:
                status = saveCurrentAndPendingSupport(uploadableProposalSection, Long.valueOf(propRevId),
                        Long.valueOf(propPersId));
                saveProposalMessages(propPrepId, propRevId, propPersId, section.CURR_PEND_SUPP.getCode(),
                        uploadableProposalSection.getLastUpdatedUser(), uploadableProposalSection.getPropMessages());
                break;

            default:
                break;
            }
            if (status) {
                ProposalDataUtility.updateRevisionLastUpdateTS(new Date(), Long.valueOf(propRevId), propPrepRevRep);
            }
            return status;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_UPLOADABLE_SENIOR_PERSONNEL_DOC_ERROR, e);
        }

    }

    @Override
    public boolean deleteSeniorPersonnelDocuments(String propPrepId, String propRevId, String propPersId,
            Section section, String nsfId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteSeniorPersonnelDocuments()");
        try {
            switch (section) {
            case BIOSKETCH:
                deleteBioSketches(propRevId, propPersId, nsfId);
                break;

            case CURR_PEND_SUPP:
                deleteCurrentAndPendingSupport(propRevId, propPersId, nsfId);
                break;

            default:
                break;
            }
            LOGGER.info("**** Deleting Proposal WarningMessages for Senior Peronnel...!!");
            propMsgRep.deleteSrPerson(Integer.parseInt(propRevId), Integer.parseInt(propPersId), section.getCode());
            ProposalDataUtility.updateRevisionLastUpdateTS(new Date(), Long.valueOf(propRevId), propPrepRevRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE__UPLOADABLE_SENIOR_PERSONNEL_DOC_ERROR, e);
        }
        return true;
    }

    @Override
    public CurrentAndPendingSupport getCurrentAndPendingSupport(String propPrepId, String propRevId, String propPersId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getCurrentAndPendingSupport()");
        try {
            CurrentAndPendingSupportEntity cpsEntity = cpsRep.findOne(Long.valueOf(propPersId));
            if (cpsEntity == null) {
                LOGGER.info("Current And Pending Support is not exists for this proposal [propPrepId: " + propPrepId
                        + Constants.REVISION_ID + propRevId + " PropPersId : = " + propPersId + "]");
                return new CurrentAndPendingSupport();
            }

            return ConverterUtility.convertCurrentAndPendingSupportEntityToDto(cpsEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_CURRENT_PENDING_SUPPORT_PERSONNEL_ERROR, e);
        }

    }

    @Override
    public List<CurrentAndPendingSupport> getCurrentAndPendingSupportForProposal(String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getCurrentAndPendingSupportForProposal()");
        try {
            List<CurrentAndPendingSupport> curPendSupportList = new ArrayList<>();
            List<CurrentAndPendingSupportEntity> cpsEntities = cpsRep
                    .getCurrentAndPendingSupportForProposal(Long.valueOf(propRevId));
            if (cpsEntities == null) {
                LOGGER.info("Current And Pending Support does not exists for this proposal [" + Constants.REVISION_ID
                        + propRevId + "]");
                return new ArrayList<>();
            }
            for (CurrentAndPendingSupportEntity cpsEntity : cpsEntities) {
                curPendSupportList.add(ConverterUtility.convertCurrentAndPendingSupportEntityToDto(cpsEntity));
            }
            return curPendSupportList;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_CURRENT_PENDING_SUPPORT_FOR_PROPOSAL_ERROR, e);
        }
    }

    /**
     * A.Section - Delete and Insert into prop_budg_sr_pers_cst table
     * 
     * @param srPersonBudgetList
     * @param propInstRecId
     * @param budgetYear
     */
    public void saveSeniorPersonalBudget(InstitutionBudget instBudget) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveSeniorPersonalBudget()");
        try {
            Long propInstRecId = instBudget.getPropInstRecId();
            List<BudgetRecord> budgetList = instBudget.getBudgetRecordList();

            // A.Section - Delete from prop_budg_sr_pers_cst table
            List<SeniorPersonsBudgetCostEntity> spbceList = seniorPersonsBCRep.findByPropInstRecId(propInstRecId);
            if (spbceList != null && !spbceList.isEmpty()) {
                LOGGER.info(" Delete spbceList : " + spbceList.size());
                for (SeniorPersonsBudgetCostEntity spbceEntity : spbceList) {
                    LOGGER.info("Deleting spbceEntity : " + spbceEntity.toString());
                    seniorPersonsBCRep.delete(spbceEntity);
                }
            }

            // Insert
            for (BudgetRecord budgetRecord : budgetList) {
                int budgetYear = budgetRecord.getBudgetYear();
                List<SeniorPersonnelCost> srPersonBudgetList = budgetRecord.getSrPersonnelList();
                if (srPersonBudgetList != null && !srPersonBudgetList.isEmpty()) {
                    for (SeniorPersonnelCost seniorPersonnel : srPersonBudgetList) {
                        seniorPersonnel.setLastUpdatedUser(instBudget.getLastUpdatedUser());
                        SeniorPersonsBudgetCostEntity seniorPersonsBudgetCostEntity = ConverterUtility
                                .convertSeniorPersonsBudgetCostEntityDtoToEntity(seniorPersonnel, propInstRecId,
                                        budgetYear);
                        LOGGER.info("Inserting : seniorPersonsBudgetCostEntity : "
                                + seniorPersonsBudgetCostEntity.toString());
                        seniorPersonsBCRep.save(seniorPersonsBudgetCostEntity);
                    }
                }
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_SENIOR_PERSONNEL_BUDGET_ERROR, e);
        }
    }

    /**
     * B.Section -Delete and Insert into prop_budg_oth_pers_cst
     * 
     * @param opList
     * @param propInstRecId
     * @param budgetYear
     */
    public void saveOtherPersonalBudget(InstitutionBudget instBudget) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveOtherPersonalBudget()");
        try {
            Long propInstRecId = instBudget.getPropInstRecId();
            List<BudgetRecord> budgetList = instBudget.getBudgetRecordList();

            // Section: B Delete from prop_budg_oth_pers_cst
            List<OtherPersonsBudgetCostEntity> opbceList = otherPersonsBudgetCostRep.findByPropInstRecId(propInstRecId);
            if (opbceList != null && !opbceList.isEmpty()) {
                for (OtherPersonsBudgetCostEntity opbceEntity : opbceList) {
                    otherPersonsBudgetCostRep.delete(opbceEntity);
                }
            }

            // insert
            for (BudgetRecord budgetRecord : budgetList) {
                int budgetYear = budgetRecord.getBudgetYear();
                List<OtherPersonnelCost> opList = budgetRecord.getOtherPersonnelList();
                if (opList != null && !opList.isEmpty()) {
                    for (OtherPersonnelCost op : opList) {
                        op.setLastUpdatedUser(instBudget.getLastUpdatedUser());
                        OtherPersonsBudgetCostEntity otherPersonsBudgetCostEntity = ConverterUtility
                                .convertOtherPersonBudgetDtoToEntity(propInstRecId, budgetYear, op);
                        otherPersonsBudgetCostRep.save(otherPersonsBudgetCostEntity);
                    }
                }
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_OTHER_PERSONNEL_BUDGET_ERROR, e);
        }
    }

    /**
     * C.Section - Delete and Insert into prop_budg_frng_bnft_cst table
     * 
     * @param budgetRecord
     * @param propInstRecId
     * @param budgetYear
     */
    public void saveFringeBenfitBudget(InstitutionBudget instBudget) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveFringeBenfitBudget()");
        try {
            Long propInstRecId = instBudget.getPropInstRecId();
            List<BudgetRecord> budgetList = instBudget.getBudgetRecordList();

            // Section : C.Delete from prop_budg_frng_bnft_cst
            List<FringeBenefitBudgetEntity> fbbeList = fringeBenefitBudgetRep.findByPropInstRecId(propInstRecId);
            if (fbbeList != null && !fbbeList.isEmpty()) {
                LOGGER.info(" Delete fbbeList : " + fbbeList.size());
                for (FringeBenefitBudgetEntity fbbEntity : fbbeList) {
                    fringeBenefitBudgetRep.delete(fbbEntity);
                }
            }

            // insert
            for (BudgetRecord budgetRecord : budgetList) {
                budgetRecord.setLastUpdatedUser(instBudget.getLastUpdatedUser());
                FringeBenefitBudgetEntity fringeBenefitBudgetEntity = ConverterUtility
                        .convertFringeBenefitCostDtoToEntity(propInstRecId, budgetRecord);
                fringeBenefitBudgetRep.save(fringeBenefitBudgetEntity);
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_FRINGE_BENEFIT_BUDGET_ERROR, e);
        }

    }

    /**
     * D:Section - Delete and Insert into Equipment Cost.
     * 
     * @param equipBCList
     * @param propInstRecId
     * @param budgetYear
     */
    public void saveEquipmentBudget(InstitutionBudget instBudget) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveEquipmentBudget()");
        try {
            Long propInstRecId = instBudget.getPropInstRecId();
            List<BudgetRecord> budgetList = instBudget.getBudgetRecordList();

            // D:Section - delete data from Equipment Cost.
            List<EquipmentBudgetCostEntity> equipmentBCList = equipBCRep.findByPropInstRecId(propInstRecId);
            if (equipmentBCList != null && !equipmentBCList.isEmpty()) {
                LOGGER.info(" Delete equipmentBCList : " + equipmentBCList.size());
                for (EquipmentBudgetCostEntity equipBCEntity : equipmentBCList) {
                    LOGGER.info("Deleting equipBCEntity : " + equipBCEntity.toString());
                    equipBCRep.delete(equipBCEntity);
                }

            }

            // insert
            for (BudgetRecord budgetRecord : budgetList) {
                int budgetYear = budgetRecord.getBudgetYear();
                List<EquipmentCost> equipBCList = budgetRecord.getEquipmentList();
                if (equipBCList != null && !equipBCList.isEmpty()) {
                    for (EquipmentCost equipmentCost : equipBCList) {
                        equipmentCost.setLastUpdatedUser(instBudget.getLastUpdatedUser());
                        EquipmentBudgetCostEntity eqBCEntity = ConverterUtility
                                .convertEquipmentBCDtotoEntity(equipmentCost, propInstRecId, budgetYear);
                        LOGGER.info("Inserting : eqBCEntity : " + eqBCEntity.toString());
                        equipBCRep.save(eqBCEntity);
                    }
                }
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_EQUIPMENT_BUDGET_ERROR, e);
        }

    }

    /**
     * E:Section - Delete and Insert into Travel Cost
     * 
     * @param tbCost
     * @param propInstRecId
     * @param budgetYear
     */
    public void saveTraveBudget(InstitutionBudget instBudget) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveTraveBudget()");
        try {
            Long propInstRecId = instBudget.getPropInstRecId();
            List<BudgetRecord> budgetList = instBudget.getBudgetRecordList();

            // E:Section - delete data from Travel Cost
            List<TravelBudgetCostEntity> tBCEntityList = travelBCRep.findByPropInstRecId(propInstRecId);
            if (tBCEntityList != null && !tBCEntityList.isEmpty()) {
                LOGGER.info(" Delete tBCEntityList : " + tBCEntityList.size());
                for (TravelBudgetCostEntity tBCEnt : tBCEntityList) {
                    LOGGER.info("Deleting tBCEnt : " + tBCEnt.toString());
                    travelBCRep.delete(tBCEnt);
                }
            }

            // insert
            for (BudgetRecord budgetRecord : budgetList) {
                int budgetYear = budgetRecord.getBudgetYear();
                TravelCost tbCost = budgetRecord.getTravelCost();
                if (tbCost != null) {
                    tbCost.setLastUpdatedUser(instBudget.getLastUpdatedUser());
                    TravelBudgetCostEntity tBCEntity = ConverterUtility.convertTBCostDtoToTBCostEntity(tbCost,
                            propInstRecId, budgetYear);
                    LOGGER.info("Inserting : tBCEntity : " + tBCEntity.toString());
                    travelBCRep.save(tBCEntity);
                }
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_TRAVEL_BUDGET_ERROR, e);
        }

    }

    /**
     * F:Section - Delete and Insert into ParticipantSupportCost
     * 
     * @param pSuppCost
     * @param propInstRecId
     * @param budgetYear
     */
    public void saveParticipantSupportBudget(InstitutionBudget instBudget) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveParticipantSupportBudget()");
        try {
            Long propInstRecId = instBudget.getPropInstRecId();
            List<BudgetRecord> budgetList = instBudget.getBudgetRecordList();

            // F:Section - delete data from ParticipantSupportCost
            List<ParticipantsSupportCostEntity> partSCEntList = partSCEntityRep.findByPropInstRecId(propInstRecId);
            if (partSCEntList != null && !partSCEntList.isEmpty()) {
                LOGGER.info(" Delete partSCEntList : " + partSCEntList.size());
                for (ParticipantsSupportCostEntity psEnt : partSCEntList) {
                    LOGGER.info("Deleting psEnt : " + psEnt.toString());
                    partSCEntityRep.delete(psEnt);
                }
            }

            // insert
            for (BudgetRecord budgetRecord : budgetList) {
                int budgetYear = budgetRecord.getBudgetYear();
                ParticipantSupportCost pSuppCost = budgetRecord.getParticipantsSupportCost();
                if (pSuppCost != null) {
                    pSuppCost.setLastUpdatedUser(instBudget.getLastUpdatedUser());
                    ParticipantsSupportCostEntity pSCEntity = ConverterUtility
                            .convertPartSCDtoToPartSuppCostEntity(pSuppCost, propInstRecId, budgetYear);
                    LOGGER.info("Inserting : pSCEntity : " + pSCEntity.toString());
                    partSCEntityRep.save(pSCEntity);
                }
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PARTICIPANT_SUPPORT_BUDGET_ERROR, e);
        }
    }

    /**
     * G:Section - Delete and Insert into OtherDirectCost
     * 
     * @param othDC
     * @param propInstRecId
     * @param budgetYear
     */
    public void saveOtherDirectCostBudget(InstitutionBudget instBudget) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveOtherDirectCostBudget()");
        try {
            Long propInstRecId = instBudget.getPropInstRecId();
            List<BudgetRecord> budgetList = instBudget.getBudgetRecordList();

            // Section - G : Delete data from Other Direct Cost
            List<OtherDirectCostEntity> odceList = otherDCEntityRep.findByPropInstRecId(propInstRecId);
            if (odceList != null && !odceList.isEmpty()) {
                LOGGER.info(" Delete odceList : " + odceList.size());
                for (OtherDirectCostEntity odcEnt : odceList) {
                    LOGGER.info("Deleting odcEnt : " + odcEnt.toString());
                    otherDCEntityRep.delete(odcEnt);
                }
            }

            // insert
            for (BudgetRecord budgetRecord : budgetList) {
                int budgetYear = budgetRecord.getBudgetYear();
                OtherDirectCost othDC = budgetRecord.getOtherDirectCost();
                if (othDC != null) {
                    othDC.setLastUpdatedUser(instBudget.getLastUpdatedUser());
                    OtherDirectCostEntity othDCEntity = ConverterUtility.convertOthDCDtoToOthBCEntity(othDC,
                            propInstRecId, budgetYear);
                    LOGGER.info("Inserting : othDCEntity : " + othDCEntity.toString());
                    otherDCEntityRep.save(othDCEntity);
                }
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_OTHER_DIRECT_COST_BUDGET_ERROR, e);
        }
    }

    /**
     * I:Section - Delete and Insert into IndirectCost
     * 
     * @param idrList
     * @param propInstRecId
     * @param budgetYear
     */
    public void saveIndirectCostBudget(InstitutionBudget instBudget) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveIndirectCostBudget()");
        try {
            Long propInstRecId = instBudget.getPropInstRecId();
            List<BudgetRecord> budgetList = instBudget.getBudgetRecordList();

            // Section - I : Delete data from Indirect Cost
            List<IndirectBudgetCostEntity> ibceList = indirectBCRep.findByPropInstRecId(propInstRecId);
            if (ibceList != null && !ibceList.isEmpty()) {
                LOGGER.info("Delete ibceList : " + ibceList.size());
                for (IndirectBudgetCostEntity idrEnt : ibceList) {
                    LOGGER.info("Deleting idrEnt : " + idrEnt.toString());
                    indirectBCRep.delete(idrEnt);
                }
            }

            // insert
            for (BudgetRecord budgetRecord : budgetList) {
                int budgetYear = budgetRecord.getBudgetYear();
                List<IndirectCost> idrList = budgetRecord.getIndirectCostsList();
                if (idrList != null && !idrList.isEmpty()) {
                    for (IndirectCost idrCost : idrList) {
                        idrCost.setLastUpdatedUser(instBudget.getLastUpdatedUser());
                        IndirectBudgetCostEntity idrBCEntity = ConverterUtility.convertIdrBCDtoToIndirBCEntity(idrCost,
                                propInstRecId, budgetYear);
                        LOGGER.info("Inserting : idrBCEntity : " + idrBCEntity.toString());
                        indirectBCRep.save(idrBCEntity);
                    }
                }
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_INDIRECT_DIRECT_COST_BUDGET_ERROR, e);
        }
    }

    @Override
    public SuggestedReviewer getSuggestedReviewers(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getSuggestedReviewers()");
        try {
            SuggestedReviewerEntity srEntity = suggRevrRep.findOne(Long.valueOf(propRevId));
            if (srEntity == null) {
                LOGGER.info("Suggested Reviewers List is not exists for this proposal [propPrepId: " + propPrepId
                        + Constants.REVISION_ID + propRevId + "]");
                return new SuggestedReviewer();
            }
            return ConverterUtility.getSuggestedReviewersDto(srEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_SUGGESTED_REVIEWERS_ERROR, e);
        }
    }

    @Override
    public OthrPersBioInfo getOthrPersBioInfo(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getOthrPersBioInfo()");
        try {
            return UploadUtility.getOthrPersBioInfo(propPrepId, propRevId, othPersRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_OTHER_PERSONNEL_BIO_INFO_ERROR, e);
        }
    }

    @Override
    public ReviewersNotInclude getReviewersNotInclude(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getReviewersNotInclude()");
        try {

            return UploadUtility.getReviewersNotInclude(propPrepId, propRevId, rvrsNotInclRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_REVIEWERS_NOT_INCLUDE_ERROR, e);
        }
    }

    @Override
    @Transactional
    public boolean replacePersonnel(PersonnelParam personnelParam) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.replacePersonnel()");
        boolean status = false;
        String employerTaxId = null;
        try {

            Personnel personnel = getPersonnel(personnelParam.getPropPrepId(), personnelParam.getPropRevId(),
                    personnelParam.getOldPropPersId());
            // Step 1: Update Prop_pers table with new role code.
            proposalPersonalRep.updateSeniorPersonnelRole(personnelParam.getNewRoleCode(), personnel.getNsfId(),
                    Long.valueOf(personnelParam.getNewPropPersId()));

            // Step 2: Update the coversheet table and PPOP table with new
            // Awardee Organization
            // id, Duns , tax id and timezone.
            CoverSheetEntity coversheetEntity = cvRep.findByPropRevId(Long.valueOf(personnelParam.getPropRevId()));

            employerTaxId = personnelParam.getNewInstitution().getTaxId();
            if (employerTaxId != null) {
                employerTaxId = employerTaxId.replace("-", "");
            }

            if (ProposalDataUtility.isPPOPhasSpecialCharacters(personnelParam.getNewInstitution())) {
                LOGGER.debug("replacePersonnel-- PPOP has Special Characters");
                cvRep.updateCoversheet(personnelParam.getNewInstitution().getId(),
                        personnelParam.getNewInstitution().getDunsNumber(), employerTaxId,
                        personnelParam.getNewInstitution().getTimeZone(), personnelParam.getNsfId(),
                        Long.valueOf(personnelParam.getPropRevId()));
                LOGGER.debug("replacePersonnel-- PPOP has Special Characters -- updated coversheet : ");
                ppopRep.deletePpop(Long.valueOf(coversheetEntity.getPropCoverSheetId()));
                LOGGER.debug("replacePersonnel-- PPOP has Special Characters -- deleted PPOP ");
                status = true;

            } else {

                if (coversheetEntity != null) {
                    LOGGER.debug("replacePersonnel-- PPOP has  NO Special Characters");
                    coversheetEntity.setDunsNumber(personnelParam.getNewInstitution().getDunsNumber());

                    employerTaxId = personnelParam.getNewInstitution().getTaxId();
                    if (employerTaxId != null) {
                        employerTaxId = employerTaxId.replace("-", "");
                    }
                    coversheetEntity.setEmployerTIN(employerTaxId);
                    coversheetEntity.setTimeZone(personnelParam.getNewInstitution().getTimeZone());
                    coversheetEntity.setAwdOrgId(personnelParam.getNewInstitution().getId());
                    ConverterUtility.setAuditFields(coversheetEntity);
                    coversheetEntity.setLastUpdtUser(personnelParam.getNsfId());

                    PPOPEntity pEntity = coversheetEntity.getPpopEntity();
                    if (pEntity != null) {

                        pEntity.setCoverSheetEntity(coversheetEntity);

                        pEntity.setCityName(personnelParam.getNewInstitution().getAddress().getCityName());
                        if (personnelParam.getNewInstitution().getAddress().getCountryCode() != null
                                && Constants.COUNTRY_US.equalsIgnoreCase(
                                        personnelParam.getNewInstitution().getAddress().getCountryCode())) {
                            pEntity.setStateCode(personnelParam.getNewInstitution().getAddress().getStateCode());
                        } else {
                            pEntity.setStateCode(null);
                        }

                        pEntity.setCountryCode(personnelParam.getNewInstitution().getAddress().getCountryCode());
                        pEntity.setDepartmentName(pEntity.getDepartmentName());
                        pEntity.setStreetAddress(personnelParam.getNewInstitution().getAddress().getStreetAddress());
                        pEntity.setStreetAddress2(personnelParam.getNewInstitution().getAddress().getStreetAddress2());
                        pEntity.setPostalCode(personnelParam.getNewInstitution().getAddress().getPostalCode());
                        pEntity.setOrganizationName(personnelParam.getNewInstitution().getOrganizationName());

                        ConverterUtility.setAuditFields(pEntity);
                        pEntity.setLastUpdtUser(personnelParam.getNsfId());

                        coversheetEntity.setPpopEntity(pEntity);
                    } else {
                        LOGGER.debug("replacePersonnel-- Inserting new PPOP Record");
                        PPOPEntity pe = new PPOPEntity();
                        pe.setCoverSheetEntity(coversheetEntity);
                        pe.setCityName(personnelParam.getNewInstitution().getAddress().getCityName());
                        pe.setStreetAddress(personnelParam.getNewInstitution().getAddress().getStreetAddress());
                        pe.setStreetAddress2(personnelParam.getNewInstitution().getAddress().getStreetAddress2());
                        pe.setPostalCode(personnelParam.getNewInstitution().getAddress().getPostalCode());
                        pe.setOrganizationName(personnelParam.getNewInstitution().getOrganizationName());
                        pe.setCountryCode(personnelParam.getNewInstitution().getAddress().getCountryCode());
                        if (personnelParam.getNewInstitution().getAddress().getCountryCode() != null
                                && personnelParam.getNewInstitution().getAddress().getCountryCode()
                                        .equalsIgnoreCase(Constants.COUNTRY_US)) {
                            pe.setStateCode(personnelParam.getNewInstitution().getAddress().getStateCode());
                        } else {
                            pe.setStateCode(null);
                        }

                        ConverterUtility.setAuditFields(pe);
                        pe.setLastUpdtUser(personnelParam.getNsfId());
                        coversheetEntity.setPpopEntity(pe);

                    }
                    cvRep.save(coversheetEntity);
                    status = true;

                }
            }

            // Step 3: update the prop_inst table with new Institution id
            BudgetInstitutionEntity budgInst = budgetInstitutionRep
                    .findByPropRevId(Long.valueOf(personnelParam.getPropRevId()));
            if (budgInst != null) {
                budgInst.setInstId(personnelParam.getNewInstitution().getId());
                ConverterUtility.setAuditFields(budgInst);
                budgInst.setLastUpdtUser(personnelParam.getNsfId());
                budgetInstitutionRep.save(budgInst);

            }

            // Step 4: Delete old senior personnel from prop_pers table
            deletePersonnel(personnelParam.getPropPrepId(), personnelParam.getPropRevId(),
                    personnelParam.getOldPropPersId());

            status = true;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_PERSONNEL_UPDATED_ROLE_ERROR, e);
        }
        return status;
    }

    /**
     * This method used to pull the DataManagement Plan uploaded file.
     * 
     * @param propPrepId
     * @param propRevId
     * @param rvrsNotInclRep
     * @return
     * @throws CommonUtilException
     */
    @Override
    public DataManagementPlan getDataManagementPlan(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getDataManagementPlan()");
        try {
            return UploadUtility.getDataManagementPlan(propPrepId, propRevId, dmpRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_DATA_MGNT_PALAN_ERROR, e);
        }
    }

    /**
     * This method used to pull the Post Doc Mentoring Plan doc uploaded file.
     * 
     * @param propPrepId
     * @param propRevId
     * @param rvrsNotInclRep
     * @return
     * @throws CommonUtilException
     */
    @Override
    public PostDocMentPlan getPostDocMentoringPlan(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getPostDocMentoringPlan()");
        try {
            return UploadUtility.getPostDocMentoringPlan(propPrepId, propRevId, pdocMPRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_POSTDOCTORAL_MENT_PLAN_ERROR, e);
        }
    }

    /**
     * 
     */

    @Override
    public boolean updateProposal(ProposalHeader proposalHeader) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveBioSketch()");
        try {

            boolean status = false;
            if (proposalHeader.getProposalTitle() != null) {
                propPrepRevRep.updateProposalTitle(Long.valueOf(proposalHeader.getPropPrepId()),
                        Long.valueOf(proposalHeader.getPropRevId()), proposalHeader.getProposalTitle(),
                        proposalHeader.getLastUpdatedUser());
                status = true;
            }

            Deadline deadline = proposalHeader.getDeadline();
            if (deadline != null && deadline.getDeadlineDate() != null) {
                propPrepRevRep.updateProposalDeadline(Long.valueOf(proposalHeader.getPropRevId()),
                        deadline.getDeadlineDate(), deadline.getDeadlineTypeCode(),
                        proposalHeader.getLastUpdatedUser());
                status = true;
            }

            return status;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_PROPOSAL_TITLE_OR_DUEDATE_ERROR, e);
        }
    }

    @Override
    public boolean deleteProposalCOA(Long revId, Long persId, String nsfId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.deleteProposalCOA()");
        try {
            ProposalCOAEntity entity = removeProposalCOA(revId, persId);
            if (entity != null) {
                entity.setCoaExclFileLoc("");
                entity.setCoaPdfFileName("");
                entity.setCoaExclOrigFileName("");
                entity.setCoaPdfFileLoc("");
                entity.setCoaPdfPageCount(null);
                entity.setLastUpdtTmsp(new Date());
                propCOARep.save(entity);
            }
            return true;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_PROPOSAL_COA_ERROR, e);
        }
    }

    @Override
    public ProposalCOAEntity removeProposalCOA(Long revId, Long persId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.removeProposalCOA()");
        try {
            ProposalCOAEntity entity = propCOARep.findByPersIdAndRevnId(revId, persId);
            if (entity != null) {
                if (entity.getAdvisees() != null && !entity.getAdvisees().isEmpty()) {
                    propCOARep.deleteAdvisees(entity);
                }
                if (entity.getAffiliations() != null && !entity.getAffiliations().isEmpty()) {
                    propCOARep.deleteAffiliations(entity);
                }
                if (entity.getCoeditors() != null && !entity.getCoeditors().isEmpty()) {
                    propCOARep.deleteCoEditors(entity);
                }
                if (entity.getCollaborators() != null && !entity.getCollaborators().isEmpty()) {
                    propCOARep.deleteCollaborators(entity);
                }
                if (entity.getRelationships() != null && !entity.getRelationships().isEmpty()) {
                    propCOARep.deleteRelationships(entity);
                }
            }
            return entity;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_REMOVE_PROPOSAL_COA_ERROR, e);
        }
    }

    @Override
    public boolean updateProposalCOA(COA coa) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.updateProposalCOA()");
        try {
            ProposalCOAEntity entity = removeProposalCOA(coa.getPropPrepRevId(), Long.valueOf(coa.getPropPersId())); // Delete
            // previous
            // records, if
            // necessary

            if (entity == null) {
                entity = ConverterUtility.convertCOAToProposalCOAEntity(coa);
            } else {
                entity = ConverterUtility.convertCOAToExistingProposalCOAEntity(coa, entity);
            }

            List<AffiliationEntity> affls = ConverterUtility.convertAffiliationToAffiliationEntity(entity, coa);
            if (entity.getAffiliations() == null) {
                entity.setAffiliations(affls);
            } else {
                entity.getAffiliations().addAll(affls);
            }
            List<AdviseeEntity> advs = ConverterUtility.convertAdviseeToAdviseeEntity(entity, coa);
            if (entity.getAdvisees() == null) {
                entity.setAdvisees(advs);
            } else {
                entity.getAdvisees().addAll(advs);
            }
            List<CollaboratorEntity> clbrs = ConverterUtility.convertCollaboratorToCollaboratorEntity(entity, coa);
            if (entity.getCollaborators() == null) {
                entity.setCollaborators(clbrs);
            } else {
                entity.getCollaborators().addAll(clbrs);
            }
            List<CoEditorEntity> coeditors = ConverterUtility.convertCoEditorToCoEditorEntity(entity, coa);
            if (entity.getCoeditors() == null) {
                entity.setCoeditors(coeditors);
            } else {
                entity.getCoeditors().addAll(coeditors);
            }
            List<RelationshipEntity> relas = ConverterUtility.convertRelationshipToRelationshipEntity(entity, coa);
            if (entity.getRelationships() == null) {
                entity.setRelationships(relas);
            } else {
                entity.getRelationships().addAll(relas);
            }
            propCOARep.save(entity);
            saveProposalMessages(coa.getPropPrepId(), String.valueOf(coa.getPropPrepId()),
                    String.valueOf(coa.getPropPersId()), Section.COA.getCode(), coa.getLastUpdatedUser(),
                    coa.getPropMessages());
            return true;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_PROPOSAL_COA_ERROR, e);
        }
    }

    @Override
    public COA getProposalCOA(Long revId, Long persId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProposalCOA()");
        try {
            ProposalCOAEntity entity = propCOARep.findByPersIdAndRevnId(revId, persId);
            COA coa = new COA();
            if (entity != null) {
                coa = ConverterUtility.convertProposalCOAEntityToCOA(entity);
            } else {
                coa.setPropPrepRevId(revId);
                coa.setPropPersId(String.valueOf(persId));
                LOGGER.info("COA does not exist for this proposal [revId: " + revId + "; persId: " + persId + "]");
            }
            return coa;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_COA_ERROR, e);
        }
    }

    @Override
    public COA getLatestProposalCOA(Long revId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getLatestProposalCOA()");
        try {
            ProposalCOAEntity entity = propCOARep.findLatestProposalCOA(revId);
            COA coa = new COA();
            if (entity != null) {
                coa = ConverterUtility.convertProposalCOAEntityToCOA(entity);
            } else {
                coa.setPropPrepRevId(revId);
                LOGGER.info("COA does not exist for this proposal [revId: " + revId + "]");
            }
            return coa;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_LATEST_PROPOSAL_COA_ERROR, e);
        }
    }

    @Override
    public List<COA> getCOAsForProposal(Long revId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getCOAsForProposal()");
        try {
            List<COA> coas = new ArrayList<>();
            List<ProposalCOAEntity> entities = propCOARep.getCOAsForProposal(revId);
            for (ProposalCOAEntity entity : entities) {
                if (entity != null) {
                    COA coa = new COA();
                    coa = ConverterUtility.convertProposalCOAEntityToCOA(entity);
                    coa.setAffiliations(
                            ConverterUtility.convertAffiliationEntityToAffiliation(entity.getAffiliations()));
                    coa.setAdvisees(ConverterUtility.convertAdviseeEntityToAdvisee(entity.getAdvisees()));
                    coa.setCollaborators(
                            ConverterUtility.convertCollaboratorEntityToCollaborator(entity.getCollaborators()));
                    coa.setCoEditors(ConverterUtility.convertCoEditorEntityToCoEditor(entity.getCoeditors()));
                    coa.setRelationships(
                            ConverterUtility.convertRelationshipEntityToRelationship(entity.getRelationships()));
                    coas.add(coa);
                } else {
                    LOGGER.info("COA does not exist for this proposal [revId: " + revId + "]");
                }
            }
            return coas;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_COA_S_ERROR, e);
        }
    }

    @Override
    public CoverSheet getCoverSheet(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getCoverSheet()");
        try {
            CoverSheet covSheet = CoverSheetUtility.getCoverSheet(propRevId, cvRep);
            List<Personnel> personnelList = getPersonnels(propPrepId, propRevId);
            List<PiCoPiInformation> piCopiList = getPiCopiInformation(personnelList);
            if (piCopiList != null && !piCopiList.isEmpty()) {

                covSheet.setPiCopiList(piCopiList);
            }
            // Get Warning/Error Messages
            WarnMsgs warnMsgs = getProposalWarningMessages(propPrepId, propRevId, Constants.ZERO,
                    Section.COVER_SHEET.getCode());
            if (warnMsgs != null && !warnMsgs.getPsmMsgList().isEmpty()) {
                LOGGER.info(
                        "ProposalDataServiceImpl.getCoverSheet() :: getPsmMsgList() :: " + warnMsgs.getPsmMsgList());
                covSheet.setPsmMsgList(warnMsgs.getPsmMsgList());
            }
            covSheet.setPropPrepId(propPrepId);
            return covSheet;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_COVERSHEET_ERROR, e);
        }
    }

    private List<PiCoPiInformation> getPiCopiInformation(List<Personnel> personnelList) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getPiCopiInformation()");
        try {
            List<PiCoPiInformation> piCopiList = new ArrayList<PiCoPiInformation>();
            for (Personnel personnel : personnelList) {
                if ((personnel != null) && (("01".equalsIgnoreCase(personnel.getPSMRole().getCode()))
                        || ("02".equalsIgnoreCase(personnel.getPSMRole().getCode())))) {
                    PiCoPiInformation piCopi = new PiCoPiInformation();
                    piCopi.setPropPersId(Integer.valueOf(personnel.getPropPersId()));
                    piCopi.setNsfId(personnel.getNsfId());
                    piCopi.setPersonRoleCode(personnel.getPSMRole().getCode());
                    piCopiList.add(piCopi);
                } else {
                    LOGGER.info("Personnel does not exists ");
                }
            }
            return piCopiList;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PI_COPI_FOR_PROPOSAL_ERROR, e);
        }
    }

    @Override
    public boolean saveCoverSheet(CoverSheet coverSheet) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveCoverSheet()");
        try {
            boolean cvSaveStatus = false;
            cvSaveStatus = CoverSheetUtility.saveCoverSheet(coverSheet, cvRep, propPrepRevRep);
            saveProposalMessages(coverSheet.getPropPrepId(), coverSheet.getPropRevId(), "persId",
                    Section.COVER_SHEET.getCode(), coverSheet.getLastUpdatedUser(), coverSheet.getPsmMsgList());

            return cvSaveStatus;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_COVERSHEET_ERROR, e);
        }
    }

    @Override
    @Transactional
    public boolean changeAwardeeOrganization(String propPrepId, String propRevId, String coverSheetId,
            Institution institution) throws CommonUtilException {
        try {
            LOGGER.debug("ProposalDataServiceImpl.changeAwardeeOrganization()");
            return CoverSheetUtility.changeAwardeeOrganization(propRevId, institution, coverSheetId, cvRep,
                    budgetInstitutionRep, proposalPersonalRep, ppopRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_AWARDEE_ORGANIZATION_ERROR, e);
        }
    }

    @Override
    public String getPrimaryAwardeeOrganizationId(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getPrimaryAwardeeOrganizationId()");
        try {
            return CoverSheetUtility.getPrimaryAwardeeOrganizationId(propRevId, budgetInstitutionRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PRIMARY_AWARDEE_ORG_ID_ERROR, e);
        }
    }

    @Override
    public boolean isCoverSheetExists(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.isCoverSheetExists()");
        try {
            return CoverSheetUtility.isCoverSheetExists(propRevId, cvRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.CHECK_IS_COVERSHEET_EXISTS_ERROR, e);
        }
    }

    @Override
    public ProposalHeader getProposalHeader(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProposalHeader()");
        try {
            return ConverterUtility.getProposalHeader(propPrepId, propRevId, propPrepRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_HEADER_ERROR, e);
        }
    }

    @Override
    public boolean setProposalAccess(ProposalPackage proposalPackage) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.setProposalAccess()");
        try {
            boolean saveProposalAccessStatus = false;
            boolean saveProposalStatusHistory = false;
            saveProposalAccessStatus = ProposalDataUtility.setProposalAccess(proposalPackage, propPrepRep);
            if (saveProposalAccessStatus) {
                saveProposalStatusHistory = updateProposalStatusHistory(proposalPackage.getPropRevId(),
                        proposalPackage.getProposalStatus(), proposalPackage.getLastUpdatedUser());
            }
            return saveProposalStatusHistory;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_SET_PROPOSAL_ACCESS_ERROR, e);
        }
    }

    private boolean updateProposalStatusHistory(String propRevId, String proposalStatus, String lastUpdatedUser)
            throws CommonUtilException {
        try {
            ProposalPreparationStatusHistoryEntity propPrepstatusHistoryEntity = new ProposalPreparationStatusHistoryEntity();
            propPrepstatusHistoryEntity.setPropPrepRevisionId(Long.valueOf(propRevId));
            propPrepstatusHistoryEntity.setPropPrepStatusCode(proposalStatus);
            propPrepstatusHistoryEntity.setPropPrepStatusDate(ProposalDataUtility.getCurrDate());
            ConverterUtility.setAuditFields(propPrepstatusHistoryEntity);
            propPrepstatusHistoryEntity.setLastUpdtUser(lastUpdatedUser);
            proposalPreparationStatusHistoryRep.save(propPrepstatusHistoryEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PROPOSAL_STATUS_HISTORY_LOG_ERROR, e);
        }
        return true;
    }

    @Override
    public ProposalPackage getProposalAccess(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProposalAccess()");
        try {
            return ProposalDataUtility.getProposalAccess(propPrepId, propRevId, propPrepRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_ACCESS_ERROR, e);
        }
    }

    @Override
    public Map<Section, SectionStatus> getLatestSectionStatusData(SectionStatusParameter param)
            throws CommonUtilException {
        LinkedHashMap<Section, SectionStatus> sections = new LinkedHashMap<>();
        LOGGER.debug("ProposalDataServiceImpl.getLatestSectionStatusData()");

        try {

            String sectionCode = ProposalDataUtility.returnNullIfBlankOrNull(param.getSectionCode());

            String propPrepId = param.getPropPrepId();
            String propRevnId = param.getPropPrepRevnId();

            // Get proposal metadata
            ProposalPackage prop = getProposalPrepById(propPrepId, propRevnId);

            Date propStatusDate = prop.getCreateDate();
            LOGGER.debug("ProposalDataServiceImpl.getLatestSectionStatusData() :: Status Date: " + propStatusDate);

            String revisionType = prop.getPropPrepRevnTypeCode();
            LOGGER.debug("ProposalDataServiceImpl.getLatestSectionStatusData() :: Revision Type: " + revisionType);

            ProposalPrepRevision prevRev = propPrepRevRep.getLatestSubmittedRevision(prop.getNsfPropId());

            Set<ProposalPersonsEntity> personnel = new LinkedHashSet<>();
            if (prevRev != null) {
                personnel = prevRev.getPersons();
            }
            LOGGER.debug(
                    "ProposalDataServiceImpl.getLatestSectionStatusData() :: No of people in previous submitted revision: "
                            + personnel.size());

            // Get all revision messages if not otherwise retrieved
            WarnMsgs msgs = null;
            if (sectionCode == null || (!sectionCode.equals(Section.BUDGETS.getCode())
                    && !sectionCode.equals(Section.COVER_SHEET.getCode()))) {
                msgs = getProposalWarningMessagesForRevision(propRevnId);
                LOGGER.debug("ProposalDataServiceImpl.getLatestSectionStatusData() :: No of warning messages: "
                        + msgs.getPsmMsgList().size());
            }

            // Cover Sheet
            if (sectionCode == null || sectionCode.equals(Section.COVER_SHEET.getCode())) {
                SectionStatus status = new SectionStatus();
                CoverSheet covSheet = getCoverSheet(propPrepId, propRevnId);

                Date origRevCreatedDate = propPrepRevRep
                        .getOriginalSubmittedRevisionCreatedDate(Long.valueOf(propPrepId));
                if (origRevCreatedDate != null) {
                    status.setOrigRevCreatedDate(origRevCreatedDate);
                }
                status.setStatusCode(prop.getProposalStatus());
                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(covSheet.getLastUpdatedTmsp());
                status.setIsFormChecked(covSheet.isPcvCheckIndicator());
                status.setMsgs(covSheet.getPsmMsgList());

                sections.put(Section.COVER_SHEET, status);
            }

            // Project Summary
            if (sectionCode == null || sectionCode.equals(Section.PROJ_SUMM.getCode())) {
                SectionStatus status = new SectionStatus();
                ProjectSummary summary = getProjectSummary(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(summary.getLastUpdatedTmsp());
                status.setMsgs(
                        WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.PROJ_SUMM.getCode()));
                status.setFilePath(summary.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);

                sections.put(Section.PROJ_SUMM, status);
            }

            // Project Description
            if (sectionCode == null || sectionCode.equals(Section.PROJ_DESC.getCode())) {
                SectionStatus status = new SectionStatus();
                ProjectDescription desc = getProjectDescription(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(desc.getLastUpdatedTmsp());
                status.setMsgs(
                        WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.PROJ_DESC.getCode()));
                status.setFilePath(desc.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);

                sections.put(Section.PROJ_DESC, status);
            }

            // References Cited
            if (sectionCode == null || sectionCode.equals(Section.REF_CITED.getCode())) {
                SectionStatus status = new SectionStatus();
                ReferencesCited rc = getReferenceCited(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(rc.getLastUpdatedTmsp());
                status.setMsgs(
                        WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.REF_CITED.getCode()));
                status.setFilePath(rc.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);

                sections.put(Section.REF_CITED, status);
            }

            // Data Management Plan
            if (sectionCode == null || sectionCode.equals(Section.DMP.getCode())) {
                SectionStatus status = new SectionStatus();
                DataManagementPlan dmp = getDataManagementPlan(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(dmp.getLastUpdatedTmsp());
                status.setMsgs(WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.DMP.getCode()));
                status.setFilePath(dmp.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);

                sections.put(Section.DMP, status);
            }

            // Institution Budget
            if (sectionCode == null || sectionCode.equals(Section.BUDGETS.getCode())) {
                SectionStatus status = new SectionStatus();
                BudgetInstitutionEntity revInst = budgetInstitutionRep.findByPropRevId(Long.valueOf(propRevnId));
                InstitutionBudget budget = getInstitutionBudget(propPrepId, propRevnId, revInst.getInstId());

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(budget.getLastUpdtTmsp());
                status.setIsFormChecked(budget.isPcvCheckIndicator());
                status.setHasPostDoctoralFunding(BudgetDataUtility.hasPostdocFunding(budget));
                status.setMsgs(budget.getPropMessages());

                sections.put(Section.BUDGETS, status);
            }

            // Budget Justification
            if (sectionCode == null || sectionCode.equals(Section.BUDGET_JUST.getCode())) {
                SectionStatus status = new SectionStatus();
                BudgetJustification bj = getBudgetJustification(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(bj.getLastUpdatedTmsp());
                status.setMsgs(
                        WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.BUDGET_JUST.getCode()));
                status.setFilePath(bj.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);

                sections.put(Section.BUDGET_JUST, status);
            }

            // Facilities
            if (sectionCode == null || sectionCode.equals(Section.FER.getCode())) {
                SectionStatus status = new SectionStatus();
                FacilitiesEquipment fe = getFacilitiesEquipment(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(fe.getLastUpdatedTmsp());
                status.setMsgs(WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.FER.getCode()));
                status.setFilePath(fe.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);

                sections.put(Section.FER, status);
            }

            // Post Doc Mentoring Plan
            if (sectionCode == null || sectionCode.equals(Section.PMP.getCode())) {
                SectionStatus status = new SectionStatus();
                PostDocMentPlan plan = getPostDocMentoringPlan(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(plan.getLastUpdatedTmsp());
                status.setMsgs(WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.PMP.getCode()));
                status.setFilePath(plan.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);

                sections.put(Section.PMP, status);
            }

            // Suggested Reviewer
            if (sectionCode == null || sectionCode.equals(Section.SRL.getCode())) {
                SectionStatus status = new SectionStatus();
                SuggestedReviewer sr = getSuggestedReviewers(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(sr.getLastUpdatedTmsp());
                status.setMsgs(WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.SRL.getCode()));
                status.setFilePath(sr.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);

                sections.put(Section.SRL, status);
            }

            // Reviewers Not To Include
            if (sectionCode == null || sectionCode.equals(Section.RNI.getCode())) {
                SectionStatus status = new SectionStatus();
                ReviewersNotInclude rni = getReviewersNotInclude(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(rni.getLastUpdatedTmsp());
                status.setMsgs(WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.RNI.getCode()));
                status.setFilePath(rni.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);

                sections.put(Section.RNI, status);
            }

            // Senior Personnel Documents
            if (sectionCode == null || sectionCode.equals(Section.SPD.getCode())) {
                SectionStatus status = new SectionStatus();
                List<PSMMessage> spdMsgs = new ArrayList<>();
                List<Date> spdDates = new ArrayList<>();
                long emptyDocumentCount = 0;
                long documentCount = 0;
                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setSeniorPersonCount(proposalPersonalRep.getCountSrPersonnel(Long.valueOf(propRevnId)));
                List<BiographicalSketchEntity> sketches = bioSketchRep
                        .getBioSketchesForProposal(Long.valueOf(propRevnId));
                if (sketches != null && !sketches.isEmpty()) {
                    Date lastUpdateDate = sketches.stream().map(s -> s.getLastUpdtTmsp()).max(Date::compareTo).get();
                    spdMsgs.addAll(
                            WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.BIOSKETCH.getCode()));
                    if (lastUpdateDate != null) {
                        spdDates.add(lastUpdateDate);
                    }
                    for (BiographicalSketchEntity entity : sketches) {
                        if (entity.getBioSketchDocfileLoc() == null
                                || StringUtils.isEmpty(entity.getBioSketchDocfileLoc().trim())) {
                            emptyDocumentCount++;
                        }
                    }
                    documentCount = documentCount + sketches.size();
                }
                List<CurrentAndPendingSupportEntity> cps = cpsRep
                        .getCurrentAndPendingSupportForProposal(Long.valueOf(propRevnId));
                if (cps != null && !cps.isEmpty()) {
                    Date lastUpdateDate = cps.stream().map(c -> c.getLastUpdtTmsp()).max(Date::compareTo).get();
                    spdMsgs.addAll(WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(),
                            Section.CURR_PEND_SUPP.getCode()));
                    if (lastUpdateDate != null) {
                        spdDates.add(lastUpdateDate);
                    }
                    for (CurrentAndPendingSupportEntity entity : cps) {
                        if (entity.getCurrPendSuppDocfileLoc() == null
                                || StringUtils.isEmpty(entity.getCurrPendSuppDocfileLoc().trim())) {
                            emptyDocumentCount++;
                        }
                    }
                    documentCount = documentCount + cps.size();
                }
                List<ProposalCOAEntity> coas = propCOARep.getCOAsForProposal(Long.valueOf(propRevnId));
                if (coas != null && !coas.isEmpty()) {
                    Date lastUpdateDate = coas.stream().map(c -> c.getLastUpdtTmsp()).max(Date::compareTo).get();
                    spdMsgs.addAll(
                            WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.COA.getCode()));
                    if (lastUpdateDate != null) {
                        spdDates.add(lastUpdateDate);
                    }
                    for (ProposalCOAEntity entity : coas) {
                        if (entity.getCoaExclFileLoc() == null
                                || StringUtils.isEmpty(entity.getCoaExclFileLoc().trim())) {
                            emptyDocumentCount++;
                        }
                    }
                    documentCount = documentCount + coas.size();
                }
                status.setMsgs(spdMsgs);
                if (!spdDates.isEmpty()) {
                    status.setLastUpdatedTmsp(Collections.max(spdDates));
                }
                status.setEmptyDocumentCount(emptyDocumentCount);
                status.setDocumentCount(documentCount);
                sections.put(Section.SPD, status);
            }

            // Other Pers Bio Info
            if (sectionCode == null || sectionCode.equals(Section.OPBIO.getCode())) {
                SectionStatus status = new SectionStatus();
                OthrPersBioInfo info = getOthrPersBioInfo(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(info.getLastUpdatedTmsp());
                status.setMsgs(WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.OPBIO.getCode()));
                status.setFilePath(info.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);

                sections.put(Section.OPBIO, status);
            }

            // Other Supporting Docs
            if (sectionCode == null || sectionCode.equals(Section.OSD.getCode())) {
                SectionStatus status = new SectionStatus();
                OtherSuppDocs osd = getOtherSuppDocs(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(osd.getLastUpdatedTmsp());
                status.setMsgs(WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.OSD.getCode()));
                status.setFilePath(osd.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);

                sections.put(Section.OSD, status);
            }

            // Budget Impact
            if (sectionCode == null || sectionCode.equals(Section.BUDI.getCode())) {
                SectionStatus status = new SectionStatus();
                BudgetImpact bImpact = getBudgetImpact(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(bImpact.getLastUpdatedTmsp());
                status.setMsgs(WarnMsgUtility.getMessagesBySectionCode(msgs.getPsmMsgList(), Section.BUDI.getCode()));
                status.setFilePath(bImpact.getFilePath());
                status = SectionStatusUtility.setDefaultMissingDocumentCount(status);
                if (prevRev != null) {
                    BudgetImpactEntity prevBI = bImpactRep.findOne(prevRev.getPropPrepRevnId());
                    if (prevBI != null) {
                        status.setIsFormChecked(prevBI.getBudgImpactDocPageCount() > 0);
                    } else {
                        status.setIsFormChecked(false);
                    }
                }

                sections.put(Section.BUDI, status);
            }

            // Proposal Update Justification
            if (sectionCode == null || sectionCode.equals(Section.PUJ.getCode())) {
                SectionStatus status = new SectionStatus();
                ProposalUpdateJustification puj = getProposalUpdateJustification(propPrepId, propRevnId);

                status.setStatusDate(propStatusDate);
                status.setRevisionType(revisionType);
                status.setLastUpdatedTmsp(puj.getLastUpdatedTmsp());
                status.setIsFormChecked(puj.getJustificationText() != null);

                sections.put(Section.PUJ, status);
            }

        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_SECTION_STATUSES_ERROR, e);
        }

        LOGGER.debug("ProposalDataServiceImpl.getLatestSectionStatusData() :: No of Sections: " + sections.size());

        return sections;
    }

    @Override
    public List<PersonnelSectionData> getLatestPersonnelSectionStatusData(String propPrepId, String propRevnId)
            throws CommonUtilException {
        List<PersonnelSectionData> personnelStatusList = new ArrayList<>();
        LOGGER.debug("ProposalDataServiceImpl.getLatestPersonnelSectionStatusData()");

        try {

            List<Personnel> personnel = getPersonnels(propPrepId, propRevnId);

            // Get proposal metadata
            ProposalPackage prop = getProposalPrepById(propPrepId, propRevnId);

            Date propStatusDate = prop.getCreateDate();
            LOGGER.debug("ProposalDataServiceImpl.getLatestSectionStatusData() :: Status Date: " + propStatusDate);

            String revisionType = prop.getPropPrepRevnTypeCode();

            String statusCode = prop.getProposalStatus();

            LOGGER.debug("ProposalDataServiceImpl.getLatestSectionStatusData() :: Revision Type: " + revisionType);

            LOGGER.debug("ProposalDataServiceImpl.getLatestSectionStatusData() :: StatusCode " + statusCode);

            // Get all revision messages if not otherwise retrieved
            WarnMsgs msgs = null;
            msgs = getProposalWarningMessagesForRevision(propRevnId);
            LOGGER.debug("ProposalDataServiceImpl.getLatestSectionStatusData() :: No of warning messages: "
                    + msgs.getPsmMsgList().size());

            for (Personnel pers : personnel) {

                int changedSectionCount = 0;
                List<SectionStatus> statuses = new ArrayList<>();

                // Biographical Sketch
                List<BiographicalSketchEntity> sketches = bioSketchRep.getBioSketchesForProposalByPerson(
                        Long.valueOf(propRevnId), Long.valueOf(pers.getPropPersId()));
                if (sketches != null && !sketches.isEmpty()) {
                    for (BiographicalSketchEntity sketch : sketches) {
                        SectionStatus status = new SectionStatus();
                        status.setSectionCode(Section.BIOSKETCH.getCode());
                        status.setStatusDate(propStatusDate);
                        status.setStatusCode(statusCode);
                        status.setRevisionType(revisionType);
                        status.setFilePath(sketch.getBioSketchDocfileLoc());
                        status.setLastUpdatedTmsp(sketch.getLastUpdtTmsp());
                        List<PSMMessage> bioWarnMsgs = WarnMsgUtility.getWarnMessagesBySectionCodeAndPersonId(
                                msgs.getPsmMsgList(), Section.BIOSKETCH.getCode(), pers.getPropPersId());
                        status.setWarningMsgs(bioWarnMsgs);
                        List<PSMMessage> bioErrMsgs = WarnMsgUtility.getErrorMessagesBySectionCodeAndPersonId(
                                msgs.getPsmMsgList(), Section.BIOSKETCH.getCode(), pers.getPropPersId());
                        status.setErrorMsgs(bioErrMsgs);
                        statuses.add(status);
                        changedSectionCount++;
                    }
                } else {
                    SectionStatus status = new SectionStatus();
                    status.setSectionCode(Section.BIOSKETCH.getCode());
                    statuses.add(status);
                }

                // Current and Pending Support
                List<CurrentAndPendingSupportEntity> cpses = cpsRep.getCurrentAndPendingSupportForProposalByPerson(
                        Long.valueOf(propRevnId), Long.valueOf(pers.getPropPersId()));
                if (cpses != null && !cpses.isEmpty()) {
                    for (CurrentAndPendingSupportEntity cps : cpses) {
                        SectionStatus status = new SectionStatus();
                        status.setSectionCode(Section.CURR_PEND_SUPP.getCode());
                        status.setStatusDate(propStatusDate);
                        status.setStatusCode(statusCode);
                        status.setRevisionType(revisionType);
                        status.setLastUpdatedTmsp(cps.getLastUpdtTmsp());
                        status.setFilePath(cps.getCurrPendSuppDocfileLoc());
                        List<PSMMessage> cpsWarnMsgs = WarnMsgUtility.getWarnMessagesBySectionCodeAndPersonId(
                                msgs.getPsmMsgList(), Section.CURR_PEND_SUPP.getCode(), pers.getPropPersId());
                        status.setWarningMsgs(cpsWarnMsgs);
                        List<PSMMessage> cpsErrMsgs = WarnMsgUtility.getErrorMessagesBySectionCodeAndPersonId(
                                msgs.getPsmMsgList(), Section.CURR_PEND_SUPP.getCode(), pers.getPropPersId());
                        status.setErrorMsgs(cpsErrMsgs);
                        statuses.add(status);
                        changedSectionCount++;
                    }
                } else {
                    SectionStatus status = new SectionStatus();
                    status.setSectionCode(Section.CURR_PEND_SUPP.getCode());
                    statuses.add(status);
                }

                // COA
                List<ProposalCOAEntity> coas = propCOARep.getCOAsForProposalByPerson(Long.valueOf(propRevnId),
                        Long.valueOf(pers.getPropPersId()));
                if (coas != null && !coas.isEmpty()) {
                    for (ProposalCOAEntity coa : coas) {
                        SectionStatus status = new SectionStatus();
                        status.setSectionCode(Section.COA.getCode());
                        status.setStatusDate(propStatusDate);
                        status.setStatusCode(statusCode);
                        status.setRevisionType(revisionType);
                        status.setLastUpdatedTmsp(coa.getLastUpdtTmsp());
                        status.setFilePath(coa.getCoaExclFileLoc());
                        List<PSMMessage> coaWarnMsgs = WarnMsgUtility.getWarnMessagesBySectionCodeAndPersonId(
                                msgs.getPsmMsgList(), Section.COA.getCode(), pers.getPropPersId());
                        status.setWarningMsgs(coaWarnMsgs);
                        List<PSMMessage> coaErrMsgs = WarnMsgUtility.getErrorMessagesBySectionCodeAndPersonId(
                                msgs.getPsmMsgList(), Section.COA.getCode(), pers.getPropPersId());
                        status.setErrorMsgs(coaErrMsgs);
                        statuses.add(status);
                        changedSectionCount++;
                    }
                } else {
                    SectionStatus status = new SectionStatus();
                    status.setSectionCode(Section.COA.getCode());
                    statuses.add(status);
                }
                LOGGER.debug("ProposalDataServiceImpl.getLatestPersonnelSectionStatusData() :: " + pers.getFirstName()
                        + " " + pers.getLastName() + " :: No of changed sections: " + changedSectionCount);
                PersonnelSectionData data = new PersonnelSectionData();
                data.setStatuses(statuses);
                data.setPersonnel(pers);
                personnelStatusList.add(data);
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_SECTION_STATUSES_ERROR, e);
        }

        LOGGER.debug("ProposalDataServiceImpl.getLatestPersonnelSectionStatusData() :: No of personnel: "
                + personnelStatusList.size());

        return personnelStatusList;
    }

    @Override
    public BiographicalSketch getLatestBiographicalSketch(String propPrepId, String propRevnId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getLatestBiographicalSketch()");
        try {
            BiographicalSketchEntity bioSketchEntity = bioSketchRep.getLatestBioSketch(Long.valueOf(propPrepId),
                    Long.valueOf(propRevnId));
            if (bioSketchEntity == null) {
                LOGGER.info("Bio Sketch does not exist for this proposal [propPrepId: " + propPrepId + "]");
                return new BiographicalSketch();
            }
            LOGGER.info("Returning --> BiographicalSketch by getLatestBioSketch() :: " + bioSketchEntity);

            return ConverterUtility.convertBiographicalSketchEntityToDto(bioSketchEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_LATEST_BIO_SKETCH_ERROR, e);
        }
    }

    @Override
    public CurrentAndPendingSupport getLatestCurrAndPendSupport(String propPrepId, String propRevnId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getLatestCurrAndPendSupport()");
        try {
            CurrentAndPendingSupportEntity cpsEntity = cpsRep.getLatestCurrAndPendSupport(Long.valueOf(propPrepId),
                    Long.valueOf(propRevnId));
            if (cpsEntity == null) {
                LOGGER.info("Current and Pending Support does not exist for this proposal [propPrepId: " + propPrepId
                        + "]");
                return new CurrentAndPendingSupport();
            }
            LOGGER.info("Returning --> CurrentAndPendingSupport by getLatestCurrAndPendSupport() :: " + cpsEntity);

            return ConverterUtility.convertCurrentAndPendingSupportEntityToDto(cpsEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_LATEST_CURR_SUPPORT_ERROR, e);
        }
    }

    @Override
    public List<DeadlineTypeLookUp> getDueDateTypes() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getDueDateTypes()");
        try {
            return ConverterUtility.convertDueDateTypeEntitiesToDtos(deadlineTypeRep.findAll());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_DUE_DATE_TYPES_ERROR, e);
        }
    }

    @Override
    public List<Proposal> getProposals(List<ProposalQueryParams> params) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProposals()");

        // Execute proposal queries by parameter set
        try {
            List<Proposal> proposals = new ArrayList<>();
            LOGGER.debug("ProposalDataServiceImpl.getProposals()::Number of queries: " + params.size());
            for (ProposalQueryParams param : params) {
                Specification<ProposalPrep> specification = ProposalSpecifications.fromParams(param,
                        entityManager.getMetamodel());
                List<ProposalPrep> entities = propSearchRep.findAll(specification);
                LOGGER.debug("ProposalDataServiceImpl.getProposals()::Query type: "
                        + (param.getIsSubmitted() ? "SUBMITTED" : "IN PROGRESS"));
                LOGGER.debug("ProposalDataServiceImpl.getProposals()::Query is by: " + (param.getIsSPOAOR()
                        ? "INSTITUTION ID (" + param.getPersonnel().get(0).getInstitution().getId() + ")"
                        : "NSF ID (" + param.getPersonnel().get(0).getNsfId() + ")"));
                LOGGER.debug("ProposalDataServiceImpl.getProposals()::Number of proposals returned for this query: "
                        + entities.size());
                for (ProposalPrep propEntity : entities) {
                    boolean readOnly = false;
                    List<ProposalPrepRevision> minRevs = propEntity.getRevisions().stream()
                            .sorted(Comparator.comparing(ProposalPrepRevision::getPropPrepRevnId))
                            .collect(Collectors.toList());
                    minRevs = minRevs.stream()
                            .filter(r -> r.getPropPrepSttsCode().trim().equals(ProposalStatus.SUBMITTED_TO_NSF))
                            .collect(Collectors.toList());
                    Optional<ProposalPrepRevision> maxRev = propEntity.getRevisions().stream()
                            .max(Comparator.comparing(r -> r.getRevnNum()));
                    if (param.getIsSubmitted() && (minRevs == null || minRevs.isEmpty())
                            && !maxRev.get().getPropPrepSttsCode().equals(ProposalStatus.SUBMITTED_TO_NSF)) { // Find
                                                                                                              // logical
                                                                                                              // parent
                        List<ProposalPrepRevision> latestRevisions = new ArrayList<>();
                        latestRevisions.add(propPrepRevRep.getLatestRevision(propEntity.getPropPrepId()));
                        minRevs = latestRevisions;
                        readOnly = true;
                    }
                    Proposal prop = ConverterUtility.convertProposalPrepToProposal(propEntity, maxRev.get(), minRevs,
                            getAllProposalStatusTypes(), readOnly);
                    prop.setTimeZone(CoverSheetUtility.getCoverSheet(prop.getPropRevId(),cvRep).getTimeZone());
                    if (prop.getProposalStatus() != null
                            && !StringUtils.isEmpty(prop.getProposalStatus().getStatusCode())) {
                        prop.getProposalStatus()
                                .setStatusDesc(statusTypes.get(prop.getProposalStatus().getStatusCode().trim()));
                    }
                    if (!proposals.contains(prop)) {
                        proposals.add(prop);
                    } else {
                        LOGGER.debug(
                                "ProposalDataServiceImpl.getProposals()::A duplicate proposal was returned for prop_prep_id = "
                                        + prop.getPropPrepId() + ", prop_prep_revn_id = " + prop.getPropRevId());
                    }

                }
            }
            LOGGER.debug("ProposalDataServiceImpl.getProposals()::Number of proposals returned (total): "
                    + proposals.size());
            return proposals;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_SEARCH_ERROR, e);
        }
    }

    @Override
    public List<BiographicalSketch> getBiographicalSketches(String propPrepId, String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getBiographicalSketches()");
        try {

            List<Personnel> personnels = getPersonnels(propPrepId, propRevId);

            List<BiographicalSketch> biographicalSketches = new ArrayList<BiographicalSketch>();

            if (personnels.isEmpty()) {
                LOGGER.info("Personnel is not exists for this proposal [" + Constants.REVISION_ID + propRevId + "]");
                return new ArrayList<BiographicalSketch>();
            }
            for (Personnel pe : personnels) {
                BiographicalSketch biographicalSketch = getBiographicalSketch(propPrepId, propRevId,
                        pe.getPropPersId());
                biographicalSketches.add(biographicalSketch);
            }

            return biographicalSketches;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_LIST_OF_BIOSKETCHES_FOR_PROPOSAL_ERROR, e);
        }
    }

    @Override
    public List<CurrentAndPendingSupport> getCurrentAndPendingSupports(String propPrepId, String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getCurrentAndPendingSupports()");
        try {
            List<Personnel> personnels = getPersonnels(propPrepId, propRevId);

            List<CurrentAndPendingSupport> currentAndPendingSupports = new ArrayList<CurrentAndPendingSupport>();

            if (personnels.isEmpty()) {
                LOGGER.info("Personnel is not exists for this proposal [" + Constants.REVISION_ID + propRevId + "]");
                return new ArrayList<CurrentAndPendingSupport>();
            }
            for (Personnel pe : personnels) {
                CurrentAndPendingSupport currentAndPendingSupport = getCurrentAndPendingSupport(propPrepId, propRevId,
                        pe.getPropPersId());
                currentAndPendingSupports.add(currentAndPendingSupport);
            }

            return currentAndPendingSupports;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_LIST_OF_CURRENT_PENDING_SUPPORT_FOR_PROPOSAL_ERROR, e);
        }
    }

    @Override
    public ProposalPackage getProposalByNsfPropId(String nsfPropId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProposalByNsfId()");
        try {
            ProposalPackage pkg = null;
            ProposalPrep prop = propPrepRep.findByNsfPropId(nsfPropId);
            if (prop != null) {
                ProposalPrepRevision revision = propPrepRevRep.getMaxRevision(Long.valueOf(prop.getPropPrepId()));
                String revId = null;
                if (revision != null) {
                    revId = String.valueOf(revision.getPropPrepRevnId());
                }
                pkg = ConverterUtility.convertProposalEntityToDto(prop, revId);
                if (revision != null) {
                    pkg.setPropRevId(String.valueOf(revision.getPropPrepRevnId()));
                    pkg.setRevNum(revision.getRevnNum().intValue());
                }
            }
            return pkg;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_LIST_OF_PROPOSAL_BY_NSFID_ERROR, e);
        }
    }

    @Override
    public SubmittedProposal getSubmittedProposal(String nsfPropId) throws CommonUtilException {
        LOGGER.debug("*********ProposalDataServiceImpl.getSubmittedProposal()************");
        try {
            SubmittedProposal subProp = new SubmittedProposal();
            ProposalPrepRevision rev = propPrepRevRep.getLatestSubmittedRevision(nsfPropId);
            LOGGER.debug("********* Revision : " + rev);
            if (rev != null) {
                subProp.setPropPrepId(String.valueOf(rev.getPropPrepId()).trim());
                subProp.setPropRevId(String.valueOf(rev.getPropPrepRevnId()).trim());
                subProp.setPropPrepStatusCode(rev.getPropPrepSttsCode().trim());
                subProp.setStaticPdfPath(rev.getStaticPdfPath());
                subProp.setNsfPropId(nsfPropId);
            }
            LOGGER.debug("********* returning latest submitted revision : " + subProp.toString());
            return subProp;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_SUBMITTED_PROPOSAL_ERROR, e);
        }
    }

    public void saveProposalMessages(String propId, String revId, String propPersId, String secCode, String lupdUser,
            List<PSMMessage> listPropMsgs) throws CommonUtilException {
        LOGGER.info("ProposalDataServiceImpl.saveProposalMessages() ....Section Code : " + secCode);
        LOGGER.info("Section Code : " + secCode + " propId :" + propId + " revId: " + revId + " persId: " + propPersId);
        LOGGER.info("ProposalDataServiceImpl.saveProposalMessages() :: " + listPropMsgs);
        try {

            if (propId != null && revId != null && secCode != null) {
                if (!"persId".equalsIgnoreCase(propPersId)) {
                    LOGGER.debug("Deleting Bio Sketches Message : " + propPersId);
                    propMsgRep.deleteSrPerson(Integer.parseInt(revId), Integer.parseInt(propPersId), secCode);
                } else {
                    LOGGER.debug("Deleting other Section Message  : " + propPersId);
                    propMsgRep.delete(Integer.parseInt(propId), Integer.parseInt(revId), secCode);
                }
                if (listPropMsgs != null && !listPropMsgs.isEmpty()) {
                    for (PSMMessage propMsg : listPropMsgs) {
                        LOGGER.info("Saving Proposal Msg : " + propMsg);
                        if (lupdUser != null) {
                            propMsg.setLastUpdatedUser(lupdUser);
                        } else {
                            propMsg.setLastUpdatedUser("PropMsg");
                        }

                        ProposalMessageEntity propMsgEntity = ConverterUtility.getProposalMessageEntity(
                                Integer.parseInt(propId), Integer.parseInt(revId), propPersId, secCode, propMsg);

                        propMsgRep.save(propMsgEntity);

                    }
                }
            }

        } catch (

        Exception e) {
            throw new CommonUtilException(Constants.SAVE_PROPOSAL_ERROR_MESSAGES_ERROR, e);
        }
    }

    @Override
    public WarnMsgs getProposalWarningMessages(String propPrepId, String propRevId, String propPersId,
            String sectionCode) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProposalWarningMessages()");
        LOGGER.debug("ProposalDataServiceImpl.getProposalWarningMessages() --> propPrepId : " + propPrepId
                + " propRevId : " + propRevId + " propPersId : " + propPersId + " section code : " + sectionCode);
        try {
            WarnMsgs wmsgs = new WarnMsgs();
            List<PSMMessage> psmMsgList = new ArrayList<PSMMessage>();

            if (propPrepId != null && propRevId != null && sectionCode != null) {
                List<ProposalMessageEntity> pMsgEnityList;
                if (!Constants.ZERO.equalsIgnoreCase(propPersId)) {
                    LOGGER.debug("PropPersId : " + propPersId);
                    pMsgEnityList = propMsgRep.findMsgForSrPerson(Integer.parseInt(propRevId),
                            Integer.parseInt(propPersId), sectionCode.trim());
                } else {
                    pMsgEnityList = propMsgRep.findByPrepIdRevIdSectionCode(Integer.parseInt(propPrepId),
                            Integer.parseInt(propRevId), sectionCode.trim());
                }

                if (pMsgEnityList != null && !pMsgEnityList.isEmpty()) {
                    for (ProposalMessageEntity psmMsgEnt : pMsgEnityList) {
                        PSMMessage pMsg = new PSMMessage();
                        pMsg.setId(String.valueOf(psmMsgEnt.getPropMsgId()));
                        pMsg.setDescription(psmMsgEnt.getPsmMsgText());

                        if (PSMMessageType.WARNING.getCode().equalsIgnoreCase(psmMsgEnt.getPsmMsgLevel())) {
                            pMsg.setType(PSMMessageType.WARNING);
                        } else if (PSMMessageType.ERROR.getCode().equalsIgnoreCase(psmMsgEnt.getPsmMsgLevel())) {
                            pMsg.setType(PSMMessageType.ERROR);
                        } else if (PSMMessageType.INFORMATION.getCode().equalsIgnoreCase(psmMsgEnt.getPsmMsgLevel())) {
                            pMsg.setType(PSMMessageType.INFORMATION);
                        }

                        pMsg.setSectionCode(psmMsgEnt.getPropSectionType());
                        pMsg.setPersonId(psmMsgEnt.getPropPersId());
                        psmMsgList.add(pMsg);
                    }
                }
            }
            LOGGER.debug("ProposalDataServiceImpl.getProposalWarningMessages() psmMsgList  :: " + psmMsgList);
            wmsgs.setPsmMsgList(psmMsgList);
            return wmsgs;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_WARNING_MESSAGES_ERROR, e);
        }
    }

    @Override
    public WarnMsgs getProposalWarningMessagesForRevision(String propRevId) throws CommonUtilException {
        try {
            WarnMsgs wmsgs = new WarnMsgs();
            List<PSMMessage> psmMsgList = new ArrayList<PSMMessage>();
            if (propRevId != null) {
                List<ProposalMessageEntity> pMsgEntityList = propMsgRep.findByPrepIdRevId(Integer.parseInt(propRevId));
                psmMsgList = convertMessageEntityToPSMMessageList(pMsgEntityList);
            }
            LOGGER.debug("ProposalDataServiceImpl.getProposalWarningMessages() psmMsgList  :: " + psmMsgList);
            wmsgs.setPsmMsgList(psmMsgList);
            return wmsgs;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_WARNING_MESSAGES_ERROR, e);
        }
    }

    @Override
    @Transactional
    public boolean submitProposal(ProposalElectronicSign proposalElectronicSign) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.submitProposal()" + proposalElectronicSign.toString());
        String propPrepId = proposalElectronicSign.getPropPrepId();
        String propRevId = proposalElectronicSign.getPropRevId();
        String instId = proposalElectronicSign.getInstitutionId();

        BigDecimal totalRqstDolAmt = new BigDecimal(0);
        InstitutionBudget ib = getInstitutionBudget(propPrepId, propRevId, instId);
        BudgetTotals bTotals = new BudgetTotals().getRequestedTotalAmountsForThisProposal(ib.getBudgetRecordList());
        totalRqstDolAmt = bTotals.getAmountOfThisRequest();
        LOGGER.debug("*********** totalRqstDolAmt :" + totalRqstDolAmt);

        // Step 1: Save the Data in Electronic Sign log table.
        try {
            ProposalElectronicSignEntity proposalElectronicSignEntity = ConverterUtility
                    .convertProposalElectronicSignDTOtoEntity(proposalElectronicSign);
            proposalElectronicSignRepository.save(proposalElectronicSignEntity);
            LOGGER.debug("ProposalDataServiceImpl.submitProposal() Sucessfully Saved Electronic Sign Log table.");
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PROPOSAL_ELECTRONIC_SIGN_LOG_ERROR, e);
        }

        // Step 2: Update the Proposal Status
        try {
            propPrepRevRep.updateProposalStatus(Long.valueOf(proposalElectronicSign.getPropRevId()),
                    ProposalStatus.SUBMITTED_TO_NSF, proposalElectronicSign.getLastUpdatedUser(), totalRqstDolAmt,
                    proposalElectronicSign.getPropPrepRevnTypeCode());
            LOGGER.debug("ProposalDataServiceImpl.submitProposal() Successfully Updated Proposal Status Code");
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_PROPOSAL_SUBMISSION_STATUS_ERROR, e);
        }

        // Step 3: Update the Submission Date
        try {
            if (proposalElectronicSign.getPropPrepRevnTypeCode().trim()
                    .equals(ProposalRevisionType.ORIGINAL_PROPOSAL)) {
                propPrepRep.updateProposalSubmitDate(Long.valueOf(proposalElectronicSign.getPropPrepId()),
                        proposalElectronicSign.getLastUpdatedUser());
                LOGGER.debug("ProposalDataServiceImpl.submitProposal() Successfully Updated Proposal Submission Date");
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_PROPOSAL_SUBMISSION_DATE_ERROR, e);
        }

        // Step 4: Get the Awardee Institution Id from PSM database.
        Long instRecId = getInstRecordId(Long.valueOf(proposalElectronicSign.getPropRevId()));

        // Step 5: Save the snapshot of the Awardee Institution Address in
        // Proposal Institution Address table.
        try {
            InstitutionAddressEntity instAddressEntity = convertInstitutionAddressDTOtoEntity(
                    proposalElectronicSign.getAwardeeInstitution(), proposalElectronicSign.getLastUpdatedUser(),
                    instRecId);
            institutionAddressRep.save(instAddressEntity);
            LOGGER.debug("ProposalDataServiceImpl.submitProposal() Successfully Saved Institution Address Details ");
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PROPOSAL_INSTITUTION_SNAPSHOT_ADDRESS, e);
        }

        // Step 6: Get the PI/COPI from PSM Database and retrieve
        // Demographic Information and Address using Solicitation Data Svc
        try {
            List<PiCoPiInformation> piCopiList = proposalElectronicSign.getPiCopiList();
            List<ProposalPersonsEntity> persList = proposalPersonalRep
                    .findByPropPrepRevisionId(Long.valueOf(propRevId));
            for (ProposalPersonsEntity personEntity : persList) {
                for (PiCoPiInformation piCopi : piCopiList) {
                    if (personEntity.getProposalPersonId() == piCopi.getPropPersId()) {
                        personEntity.setPhoneNumber(piCopi.getTelephoneNum());
                        personEntity.setFaxNumber(piCopi.getFaxNumber());
                        personEntity.setEmailAddr(piCopi.getEmailAddress());
                        personEntity.setAcadDegree(piCopi.getDegree());
                        personEntity.setAcadYear(Integer.valueOf(piCopi.getDegreeYear()));
                        personEntity.setDeptName(piCopi.getDepartmentName());
                        personEntity.setAddrLine1(piCopi.getAddress().getStreetAddress());
                        personEntity.setAddrLine2(piCopi.getAddress().getStreetAddress2());
                        personEntity.setCityName(piCopi.getAddress().getCityName());
                        personEntity.setStateCode(piCopi.getAddress().getStateCode());
                        personEntity.setCountryCode(piCopi.getAddress().getCountryCode());
                        personEntity.setPostalCode(piCopi.getAddress().getPostalCode());
                        personEntity.setLastUpdtPgm(Constants.PDSVC_PGM);
                        personEntity.setLastUpdtTmsp(ProposalDataUtility.getCurrDate());
                        personEntity.setLastUpdtUser(proposalElectronicSign.getLastUpdatedUser());
                        proposalPersonalRep.save(personEntity);
                    }
                }
            }
            LOGGER.debug(
                    "ProposalDataServiceImpl.submitProposal() Successfully Saved Proposal Personnel Demographic Details ");
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PROPOSAL_PERSONNEL_DEMOGRAPHIC_ERROR, e);
        }

        // Step 7: Update the Proposal Status History Log table.
        try {
            updateProposalStatusHistory(proposalElectronicSign.getPropRevId(), ProposalStatus.SUBMITTED_TO_NSF,
                    proposalElectronicSign.getLastUpdatedUser());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PROPOSAL_STATUS_HISTORY_LOG_ERROR, e);
        }
        LOGGER.debug("ProposalDataServiceImpl.submitProposal() Successfully Submited to NSF");
        return true;
    }

    private InstitutionAddressEntity convertInstitutionAddressDTOtoEntity(Institution awardeeInstitution,
            String lastUpdatedUser, Long instRecId) {

        InstitutionAddressEntity institutionAddressEntity = new InstitutionAddressEntity();
        institutionAddressEntity.setPropInstRecId(instRecId.intValue());
        institutionAddressEntity.setInstName(awardeeInstitution.getOrganizationName());
        institutionAddressEntity.setInstStreetAddress1(awardeeInstitution.getAddress().getStreetAddress());
        institutionAddressEntity.setInstStreetAddress2(awardeeInstitution.getAddress().getStreetAddress2());
        institutionAddressEntity.setInstCityName(awardeeInstitution.getAddress().getCityName());
        if (awardeeInstitution.getAddress().getCountryCode() != null
                && awardeeInstitution.getAddress().getCountryCode().equalsIgnoreCase(Constants.COUNTRY_US)) {
            institutionAddressEntity.setInstStateCode(awardeeInstitution.getAddress().getStateCode());
        }
        institutionAddressEntity.setInstPostalCode(awardeeInstitution.getAddress().getPostalCode());
        institutionAddressEntity.setInstCountryCode(awardeeInstitution.getAddress().getCountryCode());
        institutionAddressEntity.setLastUpdtUser(lastUpdatedUser);
        institutionAddressEntity.setLastUpdtPgm(Constants.PDSVC_PGM);
        institutionAddressEntity.setLastUpdtTmsp(ProposalDataUtility.getCurrDate());
        return institutionAddressEntity;
    }

    @Override
    public ProposalElectronicSign getAORSignature(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getAORSignature()");
        try {
            ProposalElectronicSignEntity proposalElectronicSignEntity = proposalElectronicSignRepository
                    .findByPropPrepRevnId(Integer.valueOf(propRevId));
            if (proposalElectronicSignEntity == null) {
                return new ProposalElectronicSign();
            }

            return ConverterUtility.convertProposalElectronicSignEntityToDto(proposalElectronicSignEntity);

        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_ELECTRONIC_SIGNATURE_PACKAGE, e);
        }
    }

    @Override
    public ElectronicCertificationText getCertificationText(String electronicCertTypeCode) throws CommonUtilException {

        return convertElecCertTypeTextEntityToDTO(
                proposalElectronicCertificationTextRep.getCertificationText(electronicCertTypeCode));
    }

    @Override
    public ElectronicCertificationText getCertificationTextById(Integer electronicCertTypeId)
            throws CommonUtilException {

        return convertElecCertTypeTextEntityToDTO(
                proposalElectronicCertificationTextRep.getCertificationTextById(electronicCertTypeId));
    }

    private ElectronicCertificationText convertElecCertTypeTextEntityToDTO(
            ProposalElectronicCertificationTextTypeEntity entity) {
        ElectronicCertificationText certText = new ElectronicCertificationText();
        certText.setElectronicCertScreenText(entity.getElectronicCertScreenText());
        certText.setElectronicCertTypeCode(entity.getElectronicCertTypeCode());

        return certText;
    }

    @Override
    public OtherSuppDocs getOtherSuppDocs(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getOtherSuppDocs()");
        try {
            return UploadUtility.getOtherSuppDocs(propPrepId, propRevId, otherSuppDocRep);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_OSD_ERROR, e);
        }
    }

    @Override
    public PiCoPiInformation getPICoPIInfo(Integer propPersId) {
        return ConverterUtility.convertProposalPersonnelDemographicInfoEntity(
                proposalPersonnelDemographicInfoRep.getPersonnelDemographicEntityByPersId(propPersId));
    }

    @Override
    public boolean updateNsfPropId(String propPrepId, String propRevId,
            ProposalCompleteTransfer proposalCompleteTransfer) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.completeTransfer()");
        try {
            boolean status = false;
            if (proposalCompleteTransfer.getNsfPropId() != null) {
                propPrepRep.updateProposalNsfPropId(Long.valueOf(propPrepId), proposalCompleteTransfer.getNsfPropId(),
                        proposalCompleteTransfer.getLastUpdatedUser());
                status = true;
            }
            return status;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_PROPOSAL_STATIC_PATH_OR_NSF_ID_ERROR, e);
        }
    }

    @Override
    public boolean updateProposalStaticPathUrl(String propPrepId, String propRevId,
            ProposalCompleteTransfer proposalCompleteTransfer) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.updateProposalStaticPathUrl()");
        try {
            boolean status = false;
            if (proposalCompleteTransfer.getS3FilePath() != null) {
                propPrepRevRep.updateProposalPDFStaticPath(Long.valueOf(propPrepId), Long.valueOf(propRevId),
                        proposalCompleteTransfer.getS3FilePath(), proposalCompleteTransfer.getLastUpdatedUser());
                status = true;
            }
            return status;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_PROPOSAL_STATIC_PATH_OR_NSF_ID_ERROR, e);
        }
    }

    @Override
    public BudgetImpact getBudgetImpact(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getBudgetImpact() propPrepId [" + propPrepId + "] && propRevId ["
                + propRevId + "]");
        try {
            BudgetImpactEntity bImpactEntity = bImpactRep.findOne(Long.valueOf(propRevId));
            if (bImpactEntity == null) {
                return new BudgetImpact();
            }
            return ConverterUtility.convertToBudgetImpact(bImpactEntity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_BUDGET_IMPACT_ERROR, e);
        }
    }

    @Override
    public boolean updateProposalRevisionCreateDate(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            propPrepRevRep.updateProposalRevisionCreateDate(Long.valueOf(propPrepId), Long.valueOf(propRevId));
        } catch (Exception e) {
            throw new CommonUtilException(e);
        }
        return true;
    }

    @Override
    public boolean saveProposalUpdateJustification(ProposalUpdateJustification proposalUpdateJustification)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.saveProposalUpdateJustification()");
        try {
            ProposalUpdateJustificationEntity entity = ConverterUtility
                    .convertProposalUpdateJustificationDtoToEntity(proposalUpdateJustification);
            proposalUpdateJustificationRep.save(entity);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PROPOSAL_UPDATE_JUSTIFICATION_ERROR, e);
        }
        return true;
    }

    @Override
    public ProposalUpdateJustification getProposalUpdateJustification(String propPrepId, String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProposalUpdateJustification()");
        ProposalUpdateJustificationEntity proposalUpdateJustificationEntity = proposalUpdateJustificationRep
                .findOne(Long.valueOf(propRevId));

        if (proposalUpdateJustificationEntity == null) {
            return new ProposalUpdateJustification();
        }

        return ConverterUtility.convertProposalUpdateJustificationEntityToDto(proposalUpdateJustificationEntity);

    }

    @Override
    public ProposalRevision getProposalRevision(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getProposalRevision()");
        ProposalPrepRevision proposalPrepRevisionEntity = propPrepRevRep.findByPropPrepRevnId(Long.valueOf(propRevId));

        if (proposalPrepRevisionEntity == null) {
            return new ProposalRevision();
        }

        return ConverterUtility.convertProposalRevisioinEntityToDto(proposalPrepRevisionEntity);
    }

    @Override
    public Personnel getSeniorPersonnelByNsfIdAndRole(String propPrepRevisionId, String propPersonNsfId,
            String propPersonRoleCode) throws CommonUtilException {
        try {
            ProposalPersonsEntity pers = proposalPersonalRep.getSeniorPersonnelByNsfIdAndRole(
                    Long.valueOf(propPrepRevisionId), propPersonNsfId, propPersonRoleCode);
            if (pers != null) {
                return ConverterUtility.convertSeniorPersonnelEntityToDto(pers);
            } else {
                return new Personnel();
            }
        } catch (Exception e) {
            throw new CommonUtilException(e);
        }
    }

    @Override
    public WarnMsgs getOnlyProposalWarningMessages(String propPrepId, String propRevnId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getOnlyProposalWarningMessages() --> propPrepId : "
                + StringUtils.isEmpty(propPrepId) + "propRevnId : " + StringUtils.isEmpty(propRevnId));
        try {
            WarnMsgs wmsgs = new WarnMsgs();
            List<ProposalMessageEntity> pMsgEntityList = propMsgRep
                    .findOnlyWarningMessages(Integer.parseInt(propPrepId), Integer.parseInt(propRevnId));
            List<PSMMessage> psmMsgList = convertMessageEntityToPSMMessageList(pMsgEntityList);
            LOGGER.debug("ProposalDataServiceImpl.getOnlyProposalWarningMessages() psmMsgList  :: " + psmMsgList);
            wmsgs.setPsmMsgList(psmMsgList);
            return wmsgs;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_WARNING_MESSAGES_ERROR, e);
        }
    }

    @Override
    public List<Personnel> getPersonnelsforLatestSubmittedProposal(String propPrepId, Byte revNum)
            throws CommonUtilException {
        List<Personnel> persList = new ArrayList<Personnel>();
        try {

            ProposalPrepRevision originalRevision = propPrepRevRep.getRevisionByRevIdAndRevNum(Long.valueOf(propPrepId),
                    revNum);
            Set<ProposalPersonsEntity> orgPersonalList = originalRevision.getPersons();

            for (ProposalPersonsEntity sr : orgPersonalList) {
                Personnel spDto = ConverterUtility.convertSeniorPersonnelEntityToDto(sr);
                persList.add(spDto);
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PERSONNELS_ERROR, e);
        }
        return persList;
    }

    @Override
    public boolean updateProposalRevisionStatus(String propRevId, ProposalPackage proposalPackage)
            throws CommonUtilException {
        try {
            BigDecimal totalRqstDolAmt = new BigDecimal(0);
            propPrepRevRep.updateProposalStatus(Long.valueOf(propRevId), proposalPackage.getProposalStatus(),
                    proposalPackage.getLastUpdatedUser(), (proposalPackage.getTotalRqstDolAmt() == null)
                            ? totalRqstDolAmt : proposalPackage.getTotalRqstDolAmt(),
                    proposalPackage.getPropPrepRevnTypeCode());
            LOGGER.debug("ProposalDataServiceImpl.updateProposalStatus() Successfully Updated Proposal Status Code");
            return true;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_PROPOSAL_SUBMISSION_STATUS_ERROR, e);
        }
    }

    @Override
    public boolean checkRevnPersonnelUpdate(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            ProposalRevision propRevn = this.getProposalRevision(propPrepId, propRevId);
            int revNum = propRevn.getRevNo();

            if (revNum > 0) {
                int prevRevNum = revNum - 1;
                List<Personnel> currPersonnel = this.getPersonnelsforLatestSubmittedProposal(propPrepId, (byte) revNum);
                List<Personnel> prevPersonnel = this.getPersonnelsforLatestSubmittedProposal(propPrepId,
                        (byte) prevRevNum);
                return ProposalDataUtility.isPersonnelUpdated(currPersonnel, prevPersonnel);
            } else {
                return false; // ORIG proposal
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.CHECK_PERSONNEL_REVN_UPDATE, e);
        }
    }

    @Override
    public BudgetRevision getBudgetRevisions(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("****** GetBudgetRevisions() *************");

        BudgetRevision rev = new BudgetRevision();
        List<ProposalPrepRevision> revEntityList = propPrepRevRep.getBudgetRevisions(Long.valueOf(propPrepId));
        LOGGER.debug("****** revEntityList : " + revEntityList);
        List<ProposalRevision> budgRevList = new ArrayList<ProposalRevision>();
        if (!revEntityList.isEmpty()) {
            int newRevNum = 1;
            for (ProposalPrepRevision revEntity : revEntityList) {
                LOGGER.debug("****** PrepId : " + propPrepId + " RevId : " + revEntity.getPropPrepRevnId()
                        + " RevTypeCode : " + revEntity.getPropPrepRevnTypeCode() + " Status : "
                        + revEntity.getPropPrepSttsCode() + " Rev Number : " + revEntity.getRevnNum() + " New RevNum :"
                        + newRevNum);
                ProposalRevision propRev = ConverterUtility.convertProposalRevisioinEntityToDto(revEntity);
                propRev.setRevNo(newRevNum);
                budgRevList.add(propRev);
                newRevNum++;
            }
        }
        rev.setRevList(budgRevList);
        return rev;
    }

    // Convert MessageEntity to PSMMessageList
    private List<PSMMessage> convertMessageEntityToPSMMessageList(List<ProposalMessageEntity> pMsgEntityList) {
        List<PSMMessage> psmMsgList = new ArrayList<PSMMessage>();
        if (pMsgEntityList != null && !pMsgEntityList.isEmpty()) {
            for (ProposalMessageEntity psmMsgEnt : pMsgEntityList) {
                PSMMessage pMsg = new PSMMessage();
                pMsg.setId(String.valueOf(psmMsgEnt.getPropMsgId()));
                pMsg.setDescription(psmMsgEnt.getPsmMsgText());
                pMsg.setType(PSMMessageType.getMessageType(psmMsgEnt.getPsmMsgLevel()));
                pMsg.setSectionCode(psmMsgEnt.getPropSectionType());
                pMsg.setPersonId(psmMsgEnt.getPropPersId());
                psmMsgList.add(pMsg);
            }
        }
        return psmMsgList;
    }

    @Override
    public List<FundingOpportunity> getFundingOppExclusionList() throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceImpl.getFundingOppExclusionList()");
        try {
            return ConverterUtility
                    .convertFundingOppExclusionEntitiesToDtos(fundingOppExclusionRep.getFundingOppExclusionList());
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_FUNDING_OPP_EXCLUSION_ERROR, e);
        }
    }

}
