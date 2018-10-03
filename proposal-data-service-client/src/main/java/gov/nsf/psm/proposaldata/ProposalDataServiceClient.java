package gov.nsf.psm.proposaldata;

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
import gov.nsf.psm.foundation.model.PersonnelData;
import gov.nsf.psm.foundation.model.PersonnelParam;
import gov.nsf.psm.foundation.model.PostDocMentPlan;
import gov.nsf.psm.foundation.model.ProjectDescription;
import gov.nsf.psm.foundation.model.ProjectSummary;
import gov.nsf.psm.foundation.model.ProposalElectronicSign;
import gov.nsf.psm.foundation.model.ProposalUpdateJustification;
import gov.nsf.psm.foundation.model.ReferencesCited;
import gov.nsf.psm.foundation.model.ReviewersNotInclude;
import gov.nsf.psm.foundation.model.Section;
import gov.nsf.psm.foundation.model.SectionData;
import gov.nsf.psm.foundation.model.SectionResponse;
import gov.nsf.psm.foundation.model.SuggestedReviewer;
import gov.nsf.psm.foundation.model.UploadableProposalSection;
import gov.nsf.psm.foundation.model.WarnMsgs;
import gov.nsf.psm.foundation.model.budget.InstitutionBudget;
import gov.nsf.psm.foundation.model.coversheet.CoverSheet;
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
import gov.nsf.psm.foundation.model.proposal.ProposalTransfer;
import gov.nsf.psm.foundation.model.proposal.SubmittedProposal;

public interface ProposalDataServiceClient {
    public List<SubmissionTypeLookUp> getSubmissionTypes() throws CommonUtilException;

    public List<ProposalTypeLookUp> getProposalTypes() throws CommonUtilException;

    public List<ProposalStatusTypeLookUp> getProposalStatusTypes() throws CommonUtilException;

    public List<CollaborativeTypeLookUp> getCollaborativeTypes() throws CommonUtilException;

    public List<CollaboratorTypeLookUp> getCollaboratorTypes() throws CommonUtilException;

    public List<AdviseeTypeLookUp> getAdviseeTypes() throws CommonUtilException;

    public List<CoEditorTypeLookUp> getCoEditorTypes() throws CommonUtilException;

    public ProposalPackage saveProposalPrep(ProposalPackage propPayload) throws CommonUtilException;

    public ProposalPackage getProposalPrep(String propId, String propRevId) throws CommonUtilException;

    public ProjectSummary getProjectSummary(String propPrepId, String propRevId) throws CommonUtilException;

    public ReferencesCited getReferencesCited(String propPrepId, String propRevId) throws CommonUtilException;

    public FacilitiesEquipment getFacilitiesEquipment(String propPrepId, String propRevId) throws CommonUtilException;

    public ProjectDescription getProjectDescription(String propPrepId, String propRevId) throws CommonUtilException;

    public BudgetJustification getBudgetJustification(String propPrepId, String propRevId) throws CommonUtilException;

    public BiographicalSketch getBiographicalSketch(String propPrepId, String propRevId, String propPersId)
            throws CommonUtilException;

    public CurrentAndPendingSupport getCurrentAndPendingSupport(String propPrepId, String propRevId, String propPersId)
            throws CommonUtilException;

    public SuggestedReviewer getSuggestedReviewers(String propPrepId, String propRevId) throws CommonUtilException;

    public ReviewersNotInclude getReviewersNotInclude(String propPrepId, String propRevId) throws CommonUtilException;

    public OthrPersBioInfo getOthrPersBioInfo(String propPrepId, String propRevId) throws CommonUtilException;

    public DataManagementPlan getDataManagementPlan(String propPrepId, String propRevId) throws CommonUtilException;

    public PostDocMentPlan getPostDocMentoringPlan(String propPrepId, String propRevId) throws CommonUtilException;

    public OtherSuppDocs getOtherSuppDocs(String propPrepId, String propRevId) throws CommonUtilException;

    public BudgetImpact getBudgetImpact(String propPrepId, String propRevId) throws CommonUtilException;

    public SectionResponse saveSectionData(UploadableProposalSection uploadableProposalSection, String propPrepId,
            String propRevId, Section section, Map<String, Object> metaData) throws CommonUtilException;

    public SectionResponse saveSeniorPersonnelDocuments(UploadableProposalSection uploadableProposalSection,
            String propPrepId, String propRevId, String propPersId, Section section, Map<String, Object> metaData)
            throws CommonUtilException;

    public SectionResponse deleteSectionData(String propPrepId, String propRevId, Section section, String nsfId)
            throws CommonUtilException;

    public SectionResponse deleteSeniorPersonnelDocuments(String propPrepId, String propRevId, String propPersId,
            Section section, String nsfId) throws CommonUtilException;

    public SectionResponse saveInstitutionBudget(InstitutionBudget instBudget) throws CommonUtilException;

    public InstitutionBudget getInstitutionBudget(String propPrepId, String propRevId, String instId)
            throws CommonUtilException;

    public List<SeniorPersonRoleTypeLookUp> getSeniorPersonTypes() throws CommonUtilException;

    public List<OtherPersonnelRoleTypeLookUp> getOtherPersonnelTypes() throws CommonUtilException;

    public List<InstitutionRoleTypeLookUp> getInstitutionTypes() throws CommonUtilException;

    public Personnel getPersonnel(String propPrepId, String propRevId, String propPersId) throws CommonUtilException;

    public List<Personnel> getPersonnels(String propPrepId, String propRevId) throws CommonUtilException;

    public SectionResponse deletePersonnel(String propPrepId, String propRevId, String propPersId)
            throws CommonUtilException;

    public Personnel savePersonnel(Personnel seniorPersonnel) throws CommonUtilException;

    public void setServerURL(String serverURL);

    public SectionResponse saveProjectSummary(ProjectSummary projectSummary, String propPrepId, String propRevId)
            throws CommonUtilException;

    public SectionResponse saveBudgetJustification(BudgetJustification budgetJustification, String propPrepId,
            String propRevId) throws CommonUtilException;

    public SectionResponse replacePersonnel(PersonnelParam personnelParam) throws CommonUtilException;

    public SectionResponse updateProposal(ProposalHeader proposalHeader) throws CommonUtilException;

    public CoverSheet getCoverSheet(String propPrepId, String propRevId) throws CommonUtilException;

    public SectionResponse saveCoverSheet(CoverSheet coverSheet) throws CommonUtilException;

    public SectionResponse changeAwardeeOrganization(String propPrepId, String propRevId, String coverSheetId,
            Institution institution) throws CommonUtilException;

    public SectionResponse getPrimaryAwardeeOrganizationId(String propPrepId, String propRevId)
            throws CommonUtilException;

    public SectionResponse isCoverSheetExists(String propPrepId, String propRevId) throws CommonUtilException;

    public ProposalHeader getProposalHeader(String propPrepId, String propRevId) throws CommonUtilException;

    public SectionResponse deleteProposalCOA(Long revId, Long persId, String nsfId) throws CommonUtilException;

    public COA getProposalCOA(Long revId, Long persId) throws CommonUtilException;

    public COA getLatestProposalCOA(Long revId) throws CommonUtilException;

    public SectionResponse updateProposalCOA(COA coa) throws CommonUtilException;

    public List<RelationshipTypeLookUp> getRelationshipTypes() throws CommonUtilException;

    public SectionResponse setProposalAccess(ProposalPackage proposalPackage) throws CommonUtilException;

    public ProposalPackage getProposalAccess(String propPrepId, String propRevId) throws CommonUtilException;

    public BiographicalSketch getLatestBioSketch(String propPrepId, String propRevnId) throws CommonUtilException;

    public CurrentAndPendingSupport getLatestCurrAndPendSupport(String propPrepId, String propRevnId)
            throws CommonUtilException;

    public List<DeadlineTypeLookUp> getDeadlineTypes() throws CommonUtilException;

    public List<Proposal> getProposals(List<ProposalQueryParams> params) throws CommonUtilException;

    public List<LoginUserRoleType> getLoginUserRoleTypes() throws CommonUtilException;

    public List<ProposalPermission> getLoginUserPermissions(String propStatusCode, String[] userRoleCodes)
            throws CommonUtilException;

    public List<COA> getCOAsForProposal(Long revId) throws CommonUtilException;

    public List<BiographicalSketch> getBioSketchesForProposal(String propRevnId) throws CommonUtilException;

    public List<CurrentAndPendingSupport> getCurrentAndPendingSupportForProposal(String propRevId)
            throws CommonUtilException;

    public SubmittedProposal getSubmittedProposal(String nsfPropId) throws CommonUtilException;

    public ProposalTransfer getProposalForTransfer(String propPrepId, String propRevId) throws CommonUtilException;

    public List<InstitutionBudget> getInstitutionBudgets(String propPrepId, String propRevId)
            throws CommonUtilException;

    public ProposalPackage createProposalRevision(ProposalCopy rev) throws CommonUtilException;

    public WarnMsgs getProposalWarningMessages(String propPrepId, String propRevId, String propPersId,
            String sectionCode) throws CommonUtilException;

    public ProposalPackage getProposalByNsfPropId(String nsfPropId) throws CommonUtilException;

    public SectionResponse submitProposal(ProposalElectronicSign proposalElectronicSign) throws CommonUtilException;

    public ProposalElectronicSign getAORSignature(String propPrepId, String propRevId) throws CommonUtilException;

    public SectionResponse updateNsfPropId(String propPrepId, String propRevId,
            ProposalCompleteTransfer proposalCompleteTransfer) throws CommonUtilException;

    public ElectronicCertificationText getElectronicCertificationText(String electronicCertTypeCode)
            throws CommonUtilException;

    public SectionResponse updateProposalStaticPathUrl(String propPrepId, String propRevId,
            ProposalCompleteTransfer proposalCompleteTransfer) throws CommonUtilException;

    public SectionResponse updateProposalRevisionCreateDate(String propPrepId, String propRevId)
            throws CommonUtilException;

    public SectionResponse saveProposalUpdateJustification(ProposalUpdateJustification proposalUpdateJustification)
            throws CommonUtilException;

    public ProposalUpdateJustification getProposalUpdateJustification(String propPrepId, String propRevId)
            throws CommonUtilException;

    public ProposalRevision getProposalRevision(String propPrepId, String propRevId) throws CommonUtilException;

    public SectionData getAllSectionStatusData(String propPrepId, String propRevnId) throws CommonUtilException;

    public SectionData getLatestSectionStatusData(String propPrepId, String propRevnId, String sectionCode)
            throws CommonUtilException;

    public PersonnelData getLatestPersonnelSectionStatusData(String propPrepId, String propRevnId)
            throws CommonUtilException;

    public Personnel getSeniorPersonnelByNsfIdAndRole(String propRevId, String propPersNfId, String propPersRoleCode)
            throws CommonUtilException;

    public WarnMsgs getOnlyProposalWarningMessages(String propPrepId, String propRevId) throws CommonUtilException;

    public SectionResponse updateProposalRevisionStatus(String propRevId, ProposalPackage proposalPackage)
            throws CommonUtilException;

    public Boolean getPersonnelRevnUpdateStatus(String propPrepId, String propRevId) throws CommonUtilException;

    public BudgetRevision getBudgetRevisions(String propPrepId, String propRevId) throws CommonUtilException;
    
    public List<FundingOpportunity> getFundingOppExclusionList()throws CommonUtilException;

}
