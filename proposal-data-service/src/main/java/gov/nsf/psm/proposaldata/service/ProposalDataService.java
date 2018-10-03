package gov.nsf.psm.proposaldata.service;

import java.util.List;
import java.util.Map;

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
import gov.nsf.psm.foundation.model.budget.InstitutionBudget;
import gov.nsf.psm.foundation.model.coversheet.CoverSheet;
import gov.nsf.psm.foundation.model.coversheet.PiCoPiInformation;
import gov.nsf.psm.foundation.model.lookup.AdviseeTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CoEditorTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CollaborativeTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CollaboratorTypeLookUp;
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
import gov.nsf.psm.foundation.model.proposal.SubmittedProposal;
import gov.nsf.psm.proposaldata.entity.ProposalCOAEntity;
import gov.nsf.psm.proposaldata.service.parameter.SectionStatusParameter;

public interface ProposalDataService {

    /**
     * Returns all submission types
     *
     * @return List<SubmissionTypeLookUp> - List of submission types.
     *
     */
    public List<SubmissionTypeLookUp> getAllSubmissionTypes() throws CommonUtilException;

    /**
     * Returns all proposal types
     *
     * @return List<ProposalTypeLookUp> - List of proposal types.
     *
     */
    public List<ProposalTypeLookUp> getAllProposalTypes() throws CommonUtilException;

    /**
     * Returns all collaborative types
     *
     * @return List<CollaborativeTypeLookUp> - List of collaborative types.
     *
     */
    public List<CollaborativeTypeLookUp> getAllCollaborativeTypes() throws CommonUtilException;

    /**
     * Returns all other personnel role types
     *
     * @return List<OtherPersonnelRoleTypeLookUp> - List of other personnel role
     *         types.
     *
     */
    public List<OtherPersonnelRoleTypeLookUp> getOtherPersonnelRoleTypes() throws CommonUtilException;

    /**
     * Returns all senior personnel role types
     *
     * @return List<SeniorPersonRoleTypeLookUp> - List of senior personnel role
     *         types.
     *
     */
    public List<SeniorPersonRoleTypeLookUp> getSeniorPersonnelRoleTypes() throws CommonUtilException;

    /**
     * Returns all collaborator types
     *
     * @return List<CollaboratorTypeLookUp> - List of collaborator types.
     *
     */
    public List<CollaboratorTypeLookUp> getAllCollaboratorTypes() throws CommonUtilException;

    /**
     * Returns all advisee types
     *
     * @return List<AdviseeTypeLookUp> - List of advisee types.
     *
     */
    public List<AdviseeTypeLookUp> getAllAdviseeTypes() throws CommonUtilException;

    /**
     * Returns all coeditor types
     *
     * @return List<CoEditorTypeLookUp> - List of coeditor types.
     *
     */
    public List<CoEditorTypeLookUp> getAllCoEditorTypes() throws CommonUtilException;

    /**
     * Returns all relationship types
     *
     * @return List<RelationshipTypeLookUp> - List of relationship types.
     *
     */
    public List<RelationshipTypeLookUp> getAllRelationshipTypes() throws CommonUtilException;

    /**
     * Returns all proposal status types
     *
     * @return List<ProposalStatusTypeLookUp> - List of proposal status types.
     *
     */
    public List<ProposalStatusTypeLookUp> getAllProposalStatusTypes() throws CommonUtilException;

    /**
     * Returns all institution role types
     *
     * @return List<InstitutionRoleTypeLookUp> - List of institution role types.
     *
     */
    public List<InstitutionRoleTypeLookUp> getInstitutionRoleTypes() throws CommonUtilException;

    /**
     * Returns the Proposal package for the passed proposal preparation Id and
     * proposal revision Id.
     *
     * @param -propPrepId,
     *            propRevId
     * @return ProposalPackage
     *
     * @throws CommonUtilException
     */
    public ProposalPackage getProposalPrepById(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * It Saves the Proposal wizard information to the database.
     *
     * @param -ProposalPackage
     *
     * @return ProposalPackage object
     *
     * @throws CommonUtilException
     */
    public ProposalPackage saveProposalPrep(ProposalPackage prop) throws CommonUtilException;

    /**
     * It returns the Project summary section details for the passed proposal
     * preparation Id and proposal revision Id.
     *
     * @param -propPrepId,
     *            propRevId
     *
     * @return ProjectSummary object
     *
     * @throws CommonUtilException
     */
    public ProjectSummary getProjectSummary(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * It returns the Reference Cited section details for the passed proposal
     * preparation Id and proposal revision Id.
     *
     * @param -propPrepId,
     *            propRevId
     *
     * @return ReferencesCited object
     *
     * @throws CommonUtilException
     */
    public ReferencesCited getReferenceCited(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * It returns the Facilities and Equipment section details for the passed
     * proposal preparation Id and proposal revision Id.
     *
     * @param -propPrepId,
     *            propRevId
     *
     * @return FacilitiesEquipment object
     *
     * @throws CommonUtilException
     */
    public FacilitiesEquipment getFacilitiesEquipment(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * It returns the Project Description section details for the passed
     * proposal preparation Id and proposal revision Id.
     *
     * @param -propPrepId,
     *            propRevId
     *
     * @return ProjectDescription object
     *
     * @throws CommonUtilException
     */
    public ProjectDescription getProjectDescription(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * It returns the Budget Justification section details for the passed
     * proposal preparation Id and proposal revision Id.
     *
     * @param -propPrepId,
     *            propRevId
     *
     * @return BudgetJustification object
     *
     * @throws CommonUtilException
     */
    public BudgetJustification getBudgetJustification(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * It returns the Biographical sketches section for a Senior Personnel for
     * the passed proposal preparation Id ,proposal revision Id and Proposal
     * Person Id.
     *
     * @param -propPrepId,
     *            propRevId, propPersId
     *
     * @return BiographicalSketch object
     *
     * @throws CommonUtilException
     */
    public BiographicalSketch getBiographicalSketch(String propPrepId, String propRevId, String propPersId)
            throws CommonUtilException;

    /**
     * It returns the Biographical sketches section for a Senior Personnel for
     * the passed proposal preparation Id ,proposal revision Id and Proposal
     * Person Id.
     *
     * @param -propPrepId,
     *            propRevId
     *
     * @return List of BiographicalSketch object
     *
     * @throws CommonUtilException
     */
    public List<BiographicalSketch> getBiographicalSketches(String propPrepId, String propRevId)
            throws CommonUtilException;

    /**
     * It returns the Biographical sketches section for a Senior Personnel for
     * the passed proposal preparation Id ,proposal revision Id and Proposal
     * Person Id.
     *
     * @param -propPrepId,
     *            propRevId
     *
     * @return BiographicalSketch object
     *
     * @throws CommonUtilException
     */
    public BiographicalSketch getLatestBiographicalSketch(String propPrepId, String propRevnId)
            throws CommonUtilException;

    /**
     * It returns the Current and Pending Support section for a Senior Personnel
     * for the passed proposal preparation Id ,proposal revision Id and Proposal
     * Person Id.
     *
     * @param -propPrepId,
     *            propRevId, propPersId
     *
     * @return CurrentAndPendingSupport object
     *
     * @throws CommonUtilException
     */
    public CurrentAndPendingSupport getCurrentAndPendingSupport(String propPrepId, String propRevId, String propPersId)
            throws CommonUtilException;

    /**
     * It returns the Current and Pending Support section for a Senior Personnel
     * for the passed proposal preparation Id ,proposal revision Id and Proposal
     * Person Id.
     *
     * @param -propPrepId,
     *            propRevId
     *
     * @return List of CurrentAndPendingSupport object
     *
     * @throws CommonUtilException
     */
    public List<CurrentAndPendingSupport> getCurrentAndPendingSupports(String propPrepId, String propRevId)
            throws CommonUtilException;

    /**
     * It returns the Biographical sketches section for a Senior Personnel for
     * the passed proposal preparation Id ,proposal revision Id and Proposal
     * Person Id.
     *
     * @param -propPrepId,
     *            propRevId
     *
     * @return BiographicalSketch object
     *
     * @throws CommonUtilException
     */
    public CurrentAndPendingSupport getLatestCurrAndPendSupport(String propPrepId, String propRevnId)
            throws CommonUtilException;

    /**
     * It returns the List of Suggested Reviewers section for the passed
     * proposal preparation Id ,proposal revision Id and Proposal Person Id.
     *
     * @param -propPrepId,
     *            propRevId, propPersId
     *
     * @return SuggestedReviewer object
     *
     * @throws CommonUtilException
     */
    public SuggestedReviewer getSuggestedReviewers(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * It returns the List of Suggested Reviewers section for the passed
     * proposal preparation Id ,proposal revision Id and Proposal Person Id.
     *
     * @param -propPrepId,
     *            propRevId, propPersId
     *
     * @return SuggestedReviewer object
     *
     * @throws CommonUtilException
     */
    public ReviewersNotInclude getReviewersNotInclude(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * It returns the OthrPersonal Biographical Information section for the
     * passed proposal preparation Id ,proposal revision Id and Proposal Person
     * Id.
     *
     * @param -propPrepId,
     *            propRevId, propPersId
     *
     * @return OthrPersBioInfo object
     *
     * @throws CommonUtilException
     */
    public OthrPersBioInfo getOthrPersBioInfo(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public ProposalUpdateJustification getProposalUpdateJustification(String propPrepId, String propRevId)
            throws CommonUtilException;

    /**
     * It returns the DataManagement Plan section for the passed proposal
     * preparation Id ,proposal revision Id and Proposal Person Id.
     *
     * @param -propPrepId,
     *            propRevId, propPersId
     *
     * @return DataManagementPlan object
     *
     * @throws CommonUtilException
     */
    public DataManagementPlan getDataManagementPlan(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * It returns the PostDocMentPlan Plan section for the passed proposal
     * preparation Id ,proposal revision Id and Proposal Person Id.
     *
     * @param -propPrepId,
     *            propRevId, propPersId
     *
     * @return PostDocMentPlan object
     *
     * @throws CommonUtilException
     */
    public PostDocMentPlan getPostDocMentoringPlan(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * This method is used to save all uploaded sections data to the database
     * for the passed proposal preparation Id ,proposal revision Id ,section and
     * meta data. Person Id.
     *
     * @param -propPrepId,
     *            propRevId, propPersId , section, metaData
     *
     * @return boolean (true or false)
     *
     * @throws CommonUtilException
     */
    public boolean saveUploadableProposalSection(UploadableProposalSection uploadableProposalSection, String propPrepId,
            String propRevId, Section section, Map<String, Object> metaData) throws CommonUtilException;

    /**
     * 
     * @param proposalUpdateJustification
     * @return
     * @throws CommonUtilException
     */
    public boolean saveProposalUpdateJustification(ProposalUpdateJustification proposalUpdateJustification)
            throws CommonUtilException;

    /**
     * This method is used to save all uploaded sections data for a senior
     * personnel to the database for the passed proposal preparation Id
     * ,proposal revision Id , propPersId, section and meta data. Person Id.
     *
     * @param -propPrepId,
     *            propRevId, propPersId , propPersId, section, metaData
     *
     * @return boolean (true or false)
     *
     * @throws CommonUtilException
     */
    public boolean saveUploadableSeniorPersonnelDocuments(UploadableProposalSection uploadableProposalSection,
            String propPrepId, String propRevId, String propPersId, Section section, Map<String, Object> metaData)
            throws CommonUtilException;

    /**
     * This method is used to delete any uploadable sections data from the
     * database for the passed proposal preparation Id ,proposal revision Id and
     * section.
     *
     * @param -propPrepId,
     *            propRevId, section
     *
     * @return boolean (true or false)
     *
     * @throws CommonUtilException
     */
    public boolean deleteUploadableProposalSection(String propPrepId, String propRevId, Section section, String nsfId)
            throws CommonUtilException;

    /**
     * This method is used to delete any uploadable sections related to Senior
     * Personnel from the database for the passed proposal preparation Id
     * ,proposal revision Id , propPersId and section.
     *
     * @param -propPrepId,
     *            propRevId, propPersId, propPersId, section
     *
     * @return boolean (true or false)
     *
     * @throws CommonUtilException
     */
    public boolean deleteSeniorPersonnelDocuments(String propPrepId, String propRevId, String propPersId,
            Section section, String nsfId) throws CommonUtilException;

    /**
     * This method is used to save the Institution Budget to the database for
     * the passed InstitutionBudget Object.
     *
     * @param -instBudget
     *
     * @return boolean (true or false)
     *
     * @throws CommonUtilException
     */
    public boolean saveInstitutionBudget(InstitutionBudget instBudget, boolean pcvFlag) throws CommonUtilException;

    /**
     * This method returns the Institution Budget from the database for the
     * passed Proposal Preparation Id, Proposal Revision Id and Institution Id.
     *
     * @param -instBudget
     *
     * @return boolean (true or false)
     *
     * @throws CommonUtilException
     */
    public InstitutionBudget getInstitutionBudget(String propPrepId, String propRevId, String instId)
            throws CommonUtilException;

    /**
     * This method returns the Budget for all institutions from the database for
     * the passed Proposal Preparation Id, Proposal Revision Id.
     *
     * @param -propPrepId,
     *            propRevId
     *
     * @return List<InstitutionBudget> List of institution budgets
     *
     * @throws CommonUtilException
     */
    public List<InstitutionBudget> getBudget(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * This method returns the Personnel section details from the database for
     * the passed Proposal Preparation Id, Proposal Revision Id and Proposal
     * Personal Id.
     *
     * @param -propPrepId,
     *            propRevId, propPersId
     *
     * @return Personnel object
     *
     * @throws CommonUtilException
     */
    public Personnel getPersonnel(String propPrepId, String propRevId, String propPersId) throws CommonUtilException;

    /**
     * This method returns all personnel's from the database for the passed
     * Proposal Preparation Id, Proposal Revision Id.
     *
     * @param -propPrepId,
     *            propRevId, propPersId
     *
     * @return List<Personnel> List of perosonnel's
     *
     * @throws CommonUtilException
     */
    public List<Personnel> getPersonnels(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * This method saves the Personnel section details to the database for the
     * passed Personnel object.
     *
     * @param -Personnel
     *
     * @return boolean (true or false)
     *
     * @throws CommonUtilException
     */
    public Personnel savePersonnel(Personnel seniorPersonnel) throws CommonUtilException;

    /**
     * This method is used to delete the personnel from the database for the
     * passed Proposal Preparation Id, Proposal Revision Id and Proposal
     * Personal Id.
     *
     * @param -propPrepId,propRevId,
     *            propPersId
     *
     * @return boolean (true or false)
     *
     * @throws CommonUtilException
     */
    public boolean deletePersonnel(String propPrepId, String propRevId, String propPersId) throws CommonUtilException;

    /**
     * 
     * @param personnelParam
     * @return
     * @throws CommonUtilException
     */
    public boolean replacePersonnel(PersonnelParam personnelParam) throws CommonUtilException;

    /**
     *
     * @param proposalHeader
     * @return
     * @throws CommonUtilException
     */
    public boolean updateProposal(ProposalHeader proposalHeader) throws CommonUtilException;

    /**
     *
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public CoverSheet getCoverSheet(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     *
     * @param coverSheet
     * @return
     * @throws CommonUtilException
     */
    public boolean saveCoverSheet(CoverSheet coverSheet) throws CommonUtilException;

    /**
     *
     * @param propPrepId
     * @param propRevId
     * @param coverSheetId
     * @param institution
     * @return
     * @throws CommonUtilException
     */
    public boolean changeAwardeeOrganization(String propPrepId, String propRevId, String coverSheetId,
            Institution institution) throws CommonUtilException;

    /**
     *
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public String getPrimaryAwardeeOrganizationId(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     *
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public boolean isCoverSheetExists(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     *
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public ProposalHeader getProposalHeader(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     *
     * @param coa
     * @return
     * @throws CommonUtilException
     */
    public boolean deleteProposalCOA(Long revId, Long persId, String nsfId) throws CommonUtilException;

    /**
     *
     * @param revId
     * @param persId
     * @return
     * @throws CommonUtilException
     */
    public ProposalCOAEntity removeProposalCOA(Long revId, Long persId) throws CommonUtilException;

    /**
     *
     * @param revId
     * @param persId
     * @return
     * @throws CommonUtilException
     */
    public COA getProposalCOA(Long revId, Long persId) throws CommonUtilException;

    /**
     *
     * @param revId
     * @return
     * @throws CommonUtilException
     */
    public COA getLatestProposalCOA(Long revId) throws CommonUtilException;

    /**
     *
     * @param coa
     * @return
     * @throws CommonUtilException
     */
    public boolean updateProposalCOA(COA coa) throws CommonUtilException;

    /**
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public boolean setProposalAccess(ProposalPackage proposalPackage) throws CommonUtilException;

    /**
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public ProposalPackage getProposalAccess(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public List<Proposal> getProposals(List<ProposalQueryParams> params) throws CommonUtilException;

    /**
     * 
     * @return
     */
    public List<DeadlineTypeLookUp> getDueDateTypes() throws CommonUtilException;

    /**
     * @return
     */
    public List<LoginUserRoleType> getAllLoginUserRoleTypes() throws CommonUtilException;

    /**
     * 
     * @param propStatusCode
     * @param userRoleCodes
     * @return
     */
    public List<ProposalPermission> getLoginUserRolePermissions(String propStatusCode, String[] userRoleCodes)
            throws CommonUtilException;

    /**
     * 
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public List<BiographicalSketch> getBiographicalSketchesForProposal(String propRevId) throws CommonUtilException;

    /**
     * 
     * @param revId
     * @return
     * @throws CommonUtilException
     */
    public List<COA> getCOAsForProposal(Long revId) throws CommonUtilException;

    /**
     * 
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public List<CurrentAndPendingSupport> getCurrentAndPendingSupportForProposal(String propRevId)
            throws CommonUtilException;

    /**
     * 
     * @param nsfPropId
     * @param sectionCode
     * @return
     * @throws CommonUtilException
     */
    public SubmittedProposal getSubmittedProposal(String nsfPropId) throws CommonUtilException;

    /**
     * 
     * @param ProposalCopy
     * @return
     * @throws CommonUtilException
     */
    public ProposalPackage createProposalRevision(ProposalCopy rev) throws CommonUtilException;

    /**
     * 
     * @param propPrepId
     * @param propRevId
     * @param sectionCode
     * @return
     * @throws CommonUtilException
     */
    public WarnMsgs getProposalWarningMessages(String propPrepId, String propRevId, String propPersId,
            String sectionCode) throws CommonUtilException;

    public WarnMsgs getOnlyProposalWarningMessages(String propPrepId, String propRevnId) throws CommonUtilException;

    /**
     * 
     * @param nsfPropId
     * @return
     * @throws CommonUtilException
     */

    public ProposalPackage getProposalByNsfPropId(String nsfPropId) throws CommonUtilException;

    /**
     * 
     * @param proposalElectronicSign
     * @return
     * @throws CommonUtilException
     */
    public boolean submitProposal(ProposalElectronicSign proposalElectronicSign) throws CommonUtilException;

    /**
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public ProposalElectronicSign getAORSignature(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * 
     * @param electronicCertTypeCode
     * @return
     * @throws CommonUtilException
     */

    public ElectronicCertificationText getCertificationText(String electronicCertTypeCode) throws CommonUtilException;

    /**
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public OtherSuppDocs getOtherSuppDocs(String propPrepId, String propRevId) throws CommonUtilException;

    /**
     * 
     * @param propPersId
     * @return
     */
    public PiCoPiInformation getPICoPIInfo(Integer propPersId);

    public boolean updateNsfPropId(String propPrepId, String propRevId,
            ProposalCompleteTransfer proposalCompleteTransfer) throws CommonUtilException;

    public boolean updateProposalStaticPathUrl(String propPrepId, String propRevId,
            ProposalCompleteTransfer proposalCompleteTransfer) throws CommonUtilException;

    /**
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public BudgetImpact getBudgetImpact(String propPrepId, String propRevId) throws CommonUtilException;

    public boolean updateProposalRevisionCreateDate(String propPrepId, String propRevId) throws CommonUtilException;

    public ElectronicCertificationText getCertificationTextById(Integer electronicCertTypeId)
            throws CommonUtilException;

    public ProposalRevision getProposalRevision(String propPrepId, String propRevId) throws CommonUtilException;

    public Map<Section, SectionStatus> getLatestSectionStatusData(SectionStatusParameter param)
            throws CommonUtilException;

    public WarnMsgs getProposalWarningMessagesForRevision(String propRevId) throws CommonUtilException;

    public List<PersonnelSectionData> getLatestPersonnelSectionStatusData(String propPrepId, String propRevnId)
            throws CommonUtilException;

    public Personnel getSeniorPersonnelByNsfIdAndRole(String propRevId, String propPerSNsfId, String roleCode)
            throws CommonUtilException;

    public List<Personnel> getPersonnelsforLatestSubmittedProposal(String propPrepId, Byte revNum)
            throws CommonUtilException;

    public boolean updateProposalRevisionStatus(String propRevId, ProposalPackage proposalPackage)
            throws CommonUtilException;

    public boolean checkRevnPersonnelUpdate(String propPrepId, String propRevId) throws CommonUtilException;

    public BudgetRevision getBudgetRevisions(String propPrepId, String propRevId) throws CommonUtilException;
    
    public List<FundingOpportunity> getFundingOppExclusionList() throws CommonUtilException;

}
