package gov.nsf.psm.proposaldata;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.BiographicalSketch;
import gov.nsf.psm.foundation.model.BiographicalSketchWrapper;
import gov.nsf.psm.foundation.model.BooleanResponseWrapper;
import gov.nsf.psm.foundation.model.BudgetImpact;
import gov.nsf.psm.foundation.model.BudgetImpactWrapper;
import gov.nsf.psm.foundation.model.BudgetJustification;
import gov.nsf.psm.foundation.model.BudgetJustificationWrapper;
import gov.nsf.psm.foundation.model.BudgetRevision;
import gov.nsf.psm.foundation.model.BudgetRevisionWrapper;
import gov.nsf.psm.foundation.model.COA;
import gov.nsf.psm.foundation.model.COAWrapper;
import gov.nsf.psm.foundation.model.CurrentAndPendingSupport;
import gov.nsf.psm.foundation.model.CurrentAndPendingSupportWrapper;
import gov.nsf.psm.foundation.model.DataManagementPlan;
import gov.nsf.psm.foundation.model.DataManagementPlanWrapper;
import gov.nsf.psm.foundation.model.FacilitiesEquipment;
import gov.nsf.psm.foundation.model.FacilitiesEquipmentWrapper;
import gov.nsf.psm.foundation.model.FundingOpportunities;
import gov.nsf.psm.foundation.model.FundingOpportunity;
import gov.nsf.psm.foundation.model.Institution;
import gov.nsf.psm.foundation.model.InstitutionBudgetWrapper;
import gov.nsf.psm.foundation.model.OtherSuppDocs;
import gov.nsf.psm.foundation.model.OtherSuppDocsWrapper;
import gov.nsf.psm.foundation.model.OthrPersBioInfo;
import gov.nsf.psm.foundation.model.OthrPersBioInfoWrapper;
import gov.nsf.psm.foundation.model.Personnel;
import gov.nsf.psm.foundation.model.PersonnelData;
import gov.nsf.psm.foundation.model.PersonnelDataWrapper;
import gov.nsf.psm.foundation.model.PersonnelParam;
import gov.nsf.psm.foundation.model.PersonnelWrapper;
import gov.nsf.psm.foundation.model.Personnels;
import gov.nsf.psm.foundation.model.PostDocMentPlan;
import gov.nsf.psm.foundation.model.PostDocMentPlanWrapper;
import gov.nsf.psm.foundation.model.ProjectDescription;
import gov.nsf.psm.foundation.model.ProjectDescriptionWrapper;
import gov.nsf.psm.foundation.model.ProjectSummary;
import gov.nsf.psm.foundation.model.ProjectSummaryWrapper;
import gov.nsf.psm.foundation.model.ProposalElectronicSign;
import gov.nsf.psm.foundation.model.ProposalElectronicSignWrapper;
import gov.nsf.psm.foundation.model.ProposalUpdateJustification;
import gov.nsf.psm.foundation.model.ProposalUpdateJustificationWrapper;
import gov.nsf.psm.foundation.model.ReferencesCited;
import gov.nsf.psm.foundation.model.ReferencesCitedWrapper;
import gov.nsf.psm.foundation.model.ReviewersNotInclude;
import gov.nsf.psm.foundation.model.ReviewersNotIncludeWrapper;
import gov.nsf.psm.foundation.model.Section;
import gov.nsf.psm.foundation.model.SectionData;
import gov.nsf.psm.foundation.model.SectionDataWrapper;
import gov.nsf.psm.foundation.model.SectionResponse;
import gov.nsf.psm.foundation.model.SectionResponseWrapper;
import gov.nsf.psm.foundation.model.SuggestedReviewer;
import gov.nsf.psm.foundation.model.SuggestedReviewerWrapper;
import gov.nsf.psm.foundation.model.UploadableProposalSection;
import gov.nsf.psm.foundation.model.WarnMsgs;
import gov.nsf.psm.foundation.model.WarnMsgsWrapper;
import gov.nsf.psm.foundation.model.budget.InstitutionBudget;
import gov.nsf.psm.foundation.model.coversheet.CoverSheet;
import gov.nsf.psm.foundation.model.coversheet.CoverSheetWrapper;
import gov.nsf.psm.foundation.model.lookup.AdviseeTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.AdviseeTypeLookUps;
import gov.nsf.psm.foundation.model.lookup.CoEditorTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CoEditorTypeLookUps;
import gov.nsf.psm.foundation.model.lookup.CollaborativeTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CollaborativeTypeLookUps;
import gov.nsf.psm.foundation.model.lookup.CollaboratorTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CollaboratorTypeLookUps;
import gov.nsf.psm.foundation.model.lookup.DeadlineTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.DeadlineTypeLookUps;
import gov.nsf.psm.foundation.model.lookup.ElectronicCertificationText;
import gov.nsf.psm.foundation.model.lookup.ElectronicCertificationTextWrapper;
import gov.nsf.psm.foundation.model.lookup.InstitutionRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.InstitutionRoleTypeLookUps;
import gov.nsf.psm.foundation.model.lookup.LoginUserRoleType;
import gov.nsf.psm.foundation.model.lookup.LoginUserRoleTypes;
import gov.nsf.psm.foundation.model.lookup.OtherPersonnelRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.OtherPersonnelRoleTypeLookUps;
import gov.nsf.psm.foundation.model.lookup.ProposalStatusTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.ProposalStatusTypeLookUps;
import gov.nsf.psm.foundation.model.lookup.ProposalTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.ProposalTypeLookUps;
import gov.nsf.psm.foundation.model.lookup.RelationshipTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.RelationshipTypeLookUps;
import gov.nsf.psm.foundation.model.lookup.SeniorPersonRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.SeniorPersonRoleTypeLookUps;
import gov.nsf.psm.foundation.model.lookup.SubmissionTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.SubmissionTypeLookUps;
import gov.nsf.psm.foundation.model.proposal.Proposal;
import gov.nsf.psm.foundation.model.proposal.ProposalCompleteTransfer;
import gov.nsf.psm.foundation.model.proposal.ProposalCopy;
import gov.nsf.psm.foundation.model.proposal.ProposalHeader;
import gov.nsf.psm.foundation.model.proposal.ProposalHeaderWrapper;
import gov.nsf.psm.foundation.model.proposal.ProposalPackage;
import gov.nsf.psm.foundation.model.proposal.ProposalPackageWrapper;
import gov.nsf.psm.foundation.model.proposal.ProposalPermission;
import gov.nsf.psm.foundation.model.proposal.ProposalPermissions;
import gov.nsf.psm.foundation.model.proposal.ProposalQueryParams;
import gov.nsf.psm.foundation.model.proposal.ProposalRevision;
import gov.nsf.psm.foundation.model.proposal.ProposalRevisionWrapper;
import gov.nsf.psm.foundation.model.proposal.ProposalTransfer;
import gov.nsf.psm.foundation.model.proposal.ProposalTransferWrapper;
import gov.nsf.psm.foundation.model.proposal.ProposalWrapper;
import gov.nsf.psm.foundation.model.proposal.SubmittedProposal;
import gov.nsf.psm.foundation.model.proposal.SubmittedProposalWrapper;
import gov.nsf.psm.foundation.restclient.NsfRestTemplate;
import gov.nsf.psm.proposaldata.constants.Constants;

public class ProposalDataServiceClientImpl implements ProposalDataServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalDataServiceClientImpl.class);

    private String serverURL;
    private String username;
    private String password;
    private boolean authenticationRequired;
    private int requestTimeout;
    private String submTypesURL = "/submTypes";
    private String deadlineTypesURL = "/deadlineTypes";
    private String propTypesURL = "/propTypes";
    private String propStatusTypesURL = "/propStatusTypes";
    private String collaboratorTypesURL = "/collaboratorTypes";
    private String adviseeTypesURL = "/adviseeTypes";
    private String coeditorTypesURL = "/coeditorTypes";
    private String relationshipTypesURL = "/relationshipTypes";
    private String collabTypesURL = "/collabTypes";
    private String instTypesURL = "/institutionTypes";
    private String seniorPersTypesURL = "/seniorPersonnelTypes";
    private String otherPersTypesURL = "/otherPersonnelTypes";
    private String deleteSection = "/deleteSection";
    private String propURL = "/proposal";
    private String slash = "/";
    private String proposalURL = "/proposal";
    private String budgetURL = "/budget/saveInstBudget";
    private String fundingOppExclusionURL ="/fundingOppExclusionList";
    private static final String GET_CURR_PEND_SUPP = "/getcurrpendsupp/";
    private static final String GET_BUDG_JUST = "/" + Section.BUDGET_JUST.getCode() + "/";
    private static final String GET_DMP = "/" + Section.DMP.getCode() + "/";
    private static final String GET_PMP = "/" + Section.PMP.getCode() + "/";
    private static final String GET_OSD = "/" + Section.OSD.getCode() + "/";
    private static final String GET_BUDI = "/" + Section.BUDI.getCode() + "/";
    private static final String GET_PROJ_DESC = "/" + Section.PROJ_DESC.getCode() + "/";
    private static final String GET_FAC_EQUIP = "/" + Section.FER.getCode() + "/";
    private static final String GET_REF_CITED = "/" + Section.REF_CITED.getCode() + "/";
    private static final String GET_PROJ_SUM = "/" + Section.PROJ_SUMM.getCode() + "/";
    private static final String GET_SUGG_REVRS = "/" + Section.SRL.getCode() + "/";
    private static final String GET_CV = "/" + Section.COVER_SHEET.getCode() + "/";
    private static final String GET_CURR_PEND_SUPP_LATEST = "/currpendsupp/latest/";
    private static final String GET_CURR_PEND_SUPP_FOR_PROP = "/currpendsupp/all/";
    private static final String GET_INST_BUDGETS = "/budgets/";
    private static final String GET_BIO_SKETCH = "/getbiosketch/";
    private static final String GET_BIO_SKETCH_LATEST = "/biosketch/latest/";
    private static final String GET_BIO_SKETCH_FOR_PROP = "/biosketch/all/";
    private static final String GET_BUDG = "/budget/getInstBudget/";
    private static final String DEL_PERS = "/personnel/deletePersonnel/";
    private static final String GET_PERS = "/personnel/getPersonnels/";
    private static final String GET_PER = "/personnel/getPersonnel/";
    private static final String UPDATE_PERS = "/personnel/replacePersonnel";
    private static final String GET_PER_BY_ID_AND_ROLE = "/personnel/getbynsfidandrole/";
    private static final String GET_PERSONNEL_REVN_UPDATE = "/personnel/updated";
    private static final String GET_PROP = "/search";
    private static final String UPDATE_CREATE_DATE = "/createdate/";
    private static final String DEL_COA = "/proposalCOA";
    private static final String COA = "/coa/";
    private static final String LATEST_COA = "/coa/latest/";
    private static final String UPDT_COA = "/coa/updateProposalCOA";
    private static final String GET_COA_FOR_PROP = "/coa/all/";
    private static final String REVRS_NOT_INCL = "/getrevrsnotincl/";
    private static final String GET_OTH_PERS_BIO_INFO = "/getothrpersbioinfo/";

    private static final String UPDT_PROPOSAL = "/updateProposal";
    private static final String GET_AWD_ORG_ID = "/getAwdOrgId/";
    private static final String IS_CV_EXISTS = "/isCVExists/";
    private static final String SAVE_CV = "/saveCoverSheet";
    private static final String SUBMIT_PROPOSAL = "/submit";
    private static final String GET_AOR_SIGNATURE = "/aorSignature";

    private static final String CREATE_PROP_REV = "/revision/create";

    private static final String PROP_ACCESS = "/propAccess";
    private static final String UPDT_ORG = "/changeAwardeeOrganization/";
    private static final String GET_PROP_HDR = "/getProposalHeader/";

    private static final String GET_LOGIN_USER_ROLES = "/loginUserRoleTypes";
    private static final String GET_LOGIN_USER_PERMISSIONS = "/loginUserPermissions";

    private static final String WARN_MSGS = "/warnmsgs";
    private static final String ONLY_WARN_MSGS = "/proposal/warningmsgs/";
    private static final String UPDATE_NSF_PROP_ID = "/nsfPropId";
    private static final String UPDATE_PROPOSAL_REVISION_STATUS = "/updateProposalRevisionStatus";
    private static final String UPDATE_PROPOSAL_STATIC_PATH_URL = "/proposalStaticPathUrl";

    private static final String PROPOSAL_UPDATE_JUSTIFICATION = "/proposalUpdateJustification";
    private static final String PROPOSAL_REVISION = "/proposalRevision";
    private static final String BUDGET_REVISIONS = "/budgetRevisions";

    private static final String SECTIONS = "/sections";

    private static final String PERSONNEL = "/personnel";

    public String getServerURL() {
        return serverURL;
    }

    @Override
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public boolean isAuthenticationRequired() {
        return authenticationRequired;
    }

    public void setAuthenticationRequired(boolean authenticationRequired) {
        this.authenticationRequired = authenticationRequired;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Create HttpEntity with basic authentication
     * 
     * @param authRequired
     * @return
     */
    private HttpEntity<String> getHttpEntity(boolean authRequired) {
        return authRequired ? NsfRestTemplate.createHttpEntityWithAuthentication(username, password) : null;
    }

    /**
     * Helper method to create headers with basic authentication
     * 
     * @param username
     * @param password
     * @return
     */
    private static HttpHeaders createHttpHeaders(boolean authenticationRequired, String username, String password) {
        return authenticationRequired ? NsfRestTemplate.createHeaderswithAuthentication(username, password)
                : new HttpHeaders();
    }

    private String getEndPointUrl(String section, String propPrepId, String propRevId) {
        StringBuilder endPointUrl = new StringBuilder(serverURL);
        endPointUrl.append(proposalURL);
        endPointUrl.append(section);
        endPointUrl.append(propPrepId);
        endPointUrl.append(propRevId);
        return endPointUrl.toString();
    }

    @Override
    public List<InstitutionRoleTypeLookUp> getInstitutionTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(instTypesURL);

            LOGGER.debug("Executing GET request to getInstitutionTypes : " + endpointUrl.toString());
            ResponseEntity<InstitutionRoleTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    InstitutionRoleTypeLookUps.class);
            return response.getBody().getInstitutionRoleTypeLookUps();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_INSTITUTION_TYPES_ERROR, e);
        }
    }

    @Override
    public List<SeniorPersonRoleTypeLookUp> getSeniorPersonTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(seniorPersTypesURL);

            LOGGER.debug("Executing GET request to getSeniorPersonTypes : " + endpointUrl.toString());
            ResponseEntity<SeniorPersonRoleTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    SeniorPersonRoleTypeLookUps.class);
            return response.getBody().getSeniorPersonRoleTypeLookUps();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_SENIOR_PERSONNEL_TYPES_ERROR, e);
        }
    }

    @Override
    public List<OtherPersonnelRoleTypeLookUp> getOtherPersonnelTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(otherPersTypesURL);

            LOGGER.debug("Executing GET request to getOtherPersonnelTypes : " + endpointUrl.toString());
            ResponseEntity<OtherPersonnelRoleTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    OtherPersonnelRoleTypeLookUps.class);
            return response.getBody().getOtherPersonnelRoleTypeLookUps();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_OTHER_PERSONNEL_TYPES_ERROR, e);
        }
    }

    @Override
    public List<SubmissionTypeLookUp> getSubmissionTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(submTypesURL);

            LOGGER.debug("Executing GET request to getSubmissionTypes : " + endpointUrl.toString());
            ResponseEntity<SubmissionTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    SubmissionTypeLookUps.class);
            return response.getBody().getSubmissionTypeLookUps();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_SUBMISSION_TYPES_ERROR, e);
        }
    }

    @Override
    public List<ProposalTypeLookUp> getProposalTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(propTypesURL);

            LOGGER.debug("Executing GET request to getProposalTypes : " + endpointUrl.toString());
            ResponseEntity<ProposalTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    ProposalTypeLookUps.class);
            return response.getBody().getProposalTypeLookUps();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_TYPES_ERROR, e);
        }
    }

    @Override
    public List<ProposalStatusTypeLookUp> getProposalStatusTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(propStatusTypesURL);

            LOGGER.debug("Executing GET request to getProposalStatusTypes : " + endpointUrl.toString());
            ResponseEntity<ProposalStatusTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    ProposalStatusTypeLookUps.class);
            return response.getBody().getProposalStatusTypeLookUps();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_STATUS_TYPES_ERROR, e);
        }
    }

    @Override
    public List<CollaboratorTypeLookUp> getCollaboratorTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(collaboratorTypesURL);

            LOGGER.debug("Executing GET request to getCollaboratorTypes : " + endpointUrl.toString());
            ResponseEntity<CollaboratorTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    CollaboratorTypeLookUps.class);
            return response.getBody().getCollaboratorTypeLookUps();
        } catch (CommonUtilException e) {
            throw new CommonUtilException(Constants.GET_COLLABORATOR_TYPES_ERROR, e);
        }
    }

    @Override
    public List<AdviseeTypeLookUp> getAdviseeTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(adviseeTypesURL);

            LOGGER.debug("Executing GET request to getAdviseeTypes : " + endpointUrl.toString());
            ResponseEntity<AdviseeTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    AdviseeTypeLookUps.class);
            return response.getBody().getAdviseeTypeLookUps();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_COLLABORATOR_TYPES_ERROR, e);
        }
    }

    @Override
    public List<CoEditorTypeLookUp> getCoEditorTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(coeditorTypesURL);

            LOGGER.debug("Executing GET request to getCoEditorTypes : " + endpointUrl.toString());
            ResponseEntity<CoEditorTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    CoEditorTypeLookUps.class);
            return response.getBody().getCoEditorTypeLookUps();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_CO_EDITOR_TYPES_ERROR, e);
        }
    }

    @Override
    public List<RelationshipTypeLookUp> getRelationshipTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(relationshipTypesURL);

            LOGGER.debug("Executing GET request to getRelationshipTypes : " + endpointUrl.toString());
            ResponseEntity<RelationshipTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    RelationshipTypeLookUps.class);
            return response.getBody().getRelationshipTypeLookUps();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_RELATIONSHIP_TYPES_ERROR, e);
        }
    }

    @Override
    public List<CollaborativeTypeLookUp> getCollaborativeTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(collabTypesURL);

            LOGGER.debug("Executing GET request to getCollaborativeTypes : " + endpointUrl.toString());

            ResponseEntity<CollaborativeTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    CollaborativeTypeLookUps.class);
            return response.getBody().getCollaborativeTypeLookUps();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_COLLABORATIVE_TYPES_ERROR, e);
        }
    }

    @Override
    public List<LoginUserRoleType> getLoginUserRoleTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(GET_LOGIN_USER_ROLES);

            LOGGER.debug("Executing GET request to getLoginUserRoleTypes : " + endpointUrl.toString());
            ResponseEntity<LoginUserRoleTypes> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    LoginUserRoleTypes.class);
            return response.getBody().getLoginUserRoleTypes();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_LOGIN_USER_ROLE_TYPES_ERROR, e);
        }
    }

    @Override
    public List<ProposalPermission> getLoginUserPermissions(String propStatusCode, String[] userRoleCodes)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(GET_LOGIN_USER_PERMISSIONS);
            endpointUrl.append("?");
            endpointUrl.append("propStatusCode=" + propStatusCode);
            endpointUrl.append("&");
            endpointUrl.append("userRoleCodes=" + arrayToCommaSeparatedString(userRoleCodes));

            LOGGER.debug("Executing GET request to getLoginUserPermissions : " + endpointUrl.toString());
            ResponseEntity<ProposalPermissions> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    ProposalPermissions.class);
            return response.getBody().getProposalPermissions();
        } catch (CommonUtilException e) {
            throw new CommonUtilException(Constants.GET_LOGIN_USER_PERMISSIONS_ERROR, e);
        }
    }

    /**
     * Generates a string of comma separated values from an array of strings
     * 
     * @param value
     */
    public static String arrayToCommaSeparatedString(String[] inputs) {
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < inputs.length; i++) {
            joiner.add(inputs[i]);
        }
        return joiner.toString();
    }

    @Override
    public ProposalPackage saveProposalPrep(ProposalPackage propPayload) throws CommonUtilException {

        ResponseEntity<ProposalPackageWrapper> response = null;

        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(propURL);

            LOGGER.debug("Executing POST request to saveProposalPrep : " + endpointUrl.toString());
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<ProposalPackage> httpEntity = new HttpEntity<ProposalPackage>(propPayload, headers);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.POST, httpEntity,
                    ProposalPackageWrapper.class);

            return response != null ? response.getBody().getProposalPackage() : new ProposalPackage();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PROPOSAL_PREP_ERROR, e);
        }
    }

    @Override
    public ProposalPackage getProposalPrep(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(propURL);
            endpointUrl.append(slash);
            endpointUrl.append(propPrepId);
            endpointUrl.append(slash);
            endpointUrl.append(propRevId);

            LOGGER.debug("Executing GET request to getProposalPrep : " + endpointUrl.toString());
            ResponseEntity<ProposalPackageWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    ProposalPackageWrapper.class);
            return response.getBody().getProposalPackage();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_PACKAGE_ERROR, e);
        }
    }

    @Override
    public SectionResponse saveSectionData(UploadableProposalSection uploadableProposalSection, String propPrepId,
            String propRevId, Section section, Map<String, Object> metaData) throws CommonUtilException {

        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(slash, propPrepId + slash, propRevId + slash + section.getCode());

            LOGGER.debug("Executing POST request to saveSectionData : " + endPointUrl);
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<UploadableProposalSection> httpEntity = new HttpEntity<UploadableProposalSection>(
                    uploadableProposalSection, headers);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_SECTION_DATA_ERROR, e);
        }

    }

    @Override
    public SectionResponse saveBudgetJustification(BudgetJustification budgetJustification, String propPrepId,
            String propRevId) throws CommonUtilException {

        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(slash, propPrepId + slash, propRevId + slash + "budgJust");

            LOGGER.debug("Executing POST request to saveBudgetJustification : " + endPointUrl);
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<BudgetJustification> httpEntity = new HttpEntity<BudgetJustification>(budgetJustification,
                    headers);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_BUDGET_JUST_ERROR, e);
        }

    }

    @Override
    public SectionResponse saveProjectSummary(ProjectSummary projectSummary, String propPrepId, String propRevId)
            throws CommonUtilException {

        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(slash, propPrepId + slash, propRevId + slash + "projSumm");

            LOGGER.debug("Executing POST request to saveProjectSummary : " + endPointUrl);
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<ProjectSummary> httpEntity = new HttpEntity<ProjectSummary>(projectSummary, headers);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PROJECT_SUMMARY_ERROR, e);
        }

    }

    @Override
    public ProjectSummary getProjectSummary(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_PROJ_SUM, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getProjectSummary : " + endPointUrl);
            ResponseEntity<ProjectSummaryWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    ProjectSummaryWrapper.class);
            return response.getBody().getProjectSummary();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROJECT_SUMMARY_ERROR, e);
        }
    }

	@Override
	public SectionResponse deleteSectionData(String propPrepId, String propRevId, Section section, String nsfId)
			throws CommonUtilException {
		try {
			RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
					requestTimeout);
			StringBuilder endPointUrl = new StringBuilder(serverURL);
			endPointUrl.append(proposalURL);
			endPointUrl.append(deleteSection);
			endPointUrl.append(slash);
			endPointUrl.append(propPrepId);
			endPointUrl.append(slash);
			endPointUrl.append(propRevId);
			endPointUrl.append(slash);
			endPointUrl.append(section.getCode());
			endPointUrl.append(slash);
			endPointUrl.append(nsfId);
			LOGGER.debug("Executing DELETE request to deleteSectionData : " + endPointUrl.toString());
			ResponseEntity<SectionResponseWrapper> response = proposalDataServiceClient.exchange(endPointUrl.toString(),
					HttpMethod.DELETE, getHttpEntity(authenticationRequired), SectionResponseWrapper.class);
			return response.getBody().getSectionResponse();
		} catch (Exception e) {
			throw new CommonUtilException(Constants.DELETE_SECTION_DATA_ERROR, e);
		}
	}

    @Override
    public SectionResponse saveInstitutionBudget(InstitutionBudget instBudget) throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(proposalURL);
            endpointUrl.append(budgetURL);

            LOGGER.debug("Executing POST request to saveInstitutionBudget : " + endpointUrl.toString());
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<InstitutionBudget> httpEntity = new HttpEntity<InstitutionBudget>(instBudget, headers);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_INSTITUTION_BUDGET_ERROR, e);
        }
    }

    @Override
    public InstitutionBudget getInstitutionBudget(String propPrepId, String propRevId, String instId)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_BUDG, propPrepId + slash, propRevId + slash + instId);

            LOGGER.debug("Executing GET request to getInstitutionBudget : " + endPointUrl);

            ResponseEntity<InstitutionBudgetWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    InstitutionBudgetWrapper.class);
            return response.getBody().getInstitutionBudget();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_INSTITUTION_BUDGET_ERROR, e);
        }
    }

    @Override
    public List<InstitutionBudget> getInstitutionBudgets(String propPrepId, String propRevId)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_INST_BUDGETS, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getInstitutionBudgets : " + endPointUrl);

            ResponseEntity<InstitutionBudgetWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    InstitutionBudgetWrapper.class);
            return response.getBody().getInstitutionBudgets();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_INSTITUTIONS_BUDGET_ERROR, e);
        }
    }

    @Override
    public ReferencesCited getReferencesCited(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_REF_CITED, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getReferencesCited : " + endPointUrl);

            ResponseEntity<ReferencesCitedWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    ReferencesCitedWrapper.class);
            return response.getBody().getReferencesCited();
        } catch (CommonUtilException e) {
            throw new CommonUtilException(Constants.GET_REFERENCE_CITED_ERROR, e);
        }
    }

    @Override
    public FacilitiesEquipment getFacilitiesEquipment(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_FAC_EQUIP, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getFacilitiesEquipment : " + endPointUrl);
            ResponseEntity<FacilitiesEquipmentWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    FacilitiesEquipmentWrapper.class);
            return response.getBody().getFacilitiesEquipment();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_FACILITIES_EQUIPMENT_ERROR, e);
        }
    }

    @Override
    public ProjectDescription getProjectDescription(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_PROJ_DESC, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getProjectDescription : " + endPointUrl);
            ResponseEntity<ProjectDescriptionWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    ProjectDescriptionWrapper.class);
            return response.getBody().getProjectDescription();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROJECT_DESC_ERROR, e);
        }
    }

    @Override
    public Personnel getPersonnel(String propPrepId, String propRevId, String propPersId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_PER, propPrepId + slash, propRevId + slash + propPersId);

            LOGGER.debug("Executing GET request to getPersonnel : " + endPointUrl);
            ResponseEntity<Personnels> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity, Personnels.class);
            return response.getBody().getPersonnel();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PERSONNEL_ERROR, e);
        }
    }

    @Override
    public List<Personnel> getPersonnels(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_PERS, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getPersonnels : " + endPointUrl);
            ResponseEntity<Personnels> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity, Personnels.class);

            return response.getBody().getPersonnels();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PERSONNELS_ERROR, e);
        }
    }

    @Override
    public SectionResponse deletePersonnel(String propPrepId, String propRevId, String propPersId)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(DEL_PERS, propPrepId + slash, propRevId + slash + propPersId);

            LOGGER.debug("Executing DELETE request to deletePersonnel : " + endPointUrl);

            ResponseEntity<SectionResponseWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.DELETE, httpEntity,
                    SectionResponseWrapper.class);

            return response.getBody().getSectionResponse();
        } catch (CommonUtilException e) {
            throw new CommonUtilException(Constants.DELETE_PERSONNEL_ERROR, e);
        }
    }

    @Override
    public Personnel savePersonnel(Personnel seniorPersonnel) throws CommonUtilException {
        ResponseEntity<PersonnelWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(slash, "personnel" + slash, "savePersonnel");

            LOGGER.debug("Executing POST request to savePersonnel : " + endPointUrl);

            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<Personnel> httpEntity = new HttpEntity<Personnel>(seniorPersonnel, headers);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.POST, httpEntity,
                    PersonnelWrapper.class);

            return response.getBody().getPersonnel();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_PERSONNEL_ERROR, e);
        }

    }

    @Override
    public BudgetJustification getBudgetJustification(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_BUDG_JUST, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getBudgetJustification : " + endPointUrl);

            ResponseEntity<BudgetJustificationWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    BudgetJustificationWrapper.class);
            return response.getBody().getBudgetJustification();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_BUDG_JUST_ERROR, e);
        }
    }

    @Override
    public SectionResponse saveSeniorPersonnelDocuments(UploadableProposalSection uploadableProposalSection,
            String propPrepId, String propRevId, String propPersId, Section section, Map<String, Object> metaData)
            throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(slash, propPrepId + slash,
                    propRevId + slash + propPersId + slash + section.getCode());

            LOGGER.debug("Executing POST request to saveSeniorPersonnelDocuments : " + endPointUrl);
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<UploadableProposalSection> httpEntity = new HttpEntity<UploadableProposalSection>(
                    uploadableProposalSection, headers);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_SENIOR_PERSONNEL_DOCS_ERROR, e);
        }

    }

    @Override
    public BiographicalSketch getBiographicalSketch(String propPrepId, String propRevId, String propPersId)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_BIO_SKETCH, propPrepId + slash, propRevId + slash + propPersId);

            LOGGER.debug("Executing GET request to getBiographicalSketch : " + endPointUrl);
            ResponseEntity<BiographicalSketchWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    BiographicalSketchWrapper.class);
            return response.getBody().getBiographicalSketch();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_BIO_SKETCH_ERROR, e);
        }
    }

    @Override
    public SectionResponse deleteSeniorPersonnelDocuments(String propPrepId, String propRevId, String propPersId,
            Section section, String nsfId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(slash);
            endPointUrl.append(propPrepId);
            endPointUrl.append(slash);
            endPointUrl.append(propRevId);
            endPointUrl.append(slash);
            endPointUrl.append(propPersId);
            endPointUrl.append(slash);
            endPointUrl.append(section.getCode());
            endPointUrl.append(slash);
            endPointUrl.append(nsfId);

            LOGGER.debug("Executing DELETE request to deleteSeniorPersonnelDocuments : " + endPointUrl.toString());
            ResponseEntity<SectionResponseWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.DELETE, httpEntity,
                    SectionResponseWrapper.class);

            return response.getBody().getSectionResponse();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_SENIOR_PERSONNEL_DOCS_ERROR, e);
        }
    }

    @Override
    public CurrentAndPendingSupport getCurrentAndPendingSupport(String propPrepId, String propRevId, String propPersId)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            String endPointUrl = getEndPointUrl(GET_CURR_PEND_SUPP, propPrepId + slash, propRevId + slash + propPersId);

            LOGGER.debug("Executing GET request to getCurrentAndPendingSupport : " + endPointUrl);

            ResponseEntity<CurrentAndPendingSupportWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    CurrentAndPendingSupportWrapper.class);
            return response.getBody().getCurrentAndPendingSupport();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_CURRENT_AND_PENDING_SUPPORT_ERROR, e);
        }
    }

    @Override
    public List<CurrentAndPendingSupport> getCurrentAndPendingSupportForProposal(String propRevId)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(GET_CURR_PEND_SUPP_FOR_PROP);
            endPointUrl.append(propRevId);

            LOGGER.debug("Executing GET request to getCurrentAndPendingSupportForProposal : " + endPointUrl);
            ResponseEntity<CurrentAndPendingSupportWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.GET, httpEntity,
                    CurrentAndPendingSupportWrapper.class);
            return response.getBody().getCpsups();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_CURRENT_AND_PENDING_SUPPORT_ERROR, e);
        }
    }

    @Override
    public SuggestedReviewer getSuggestedReviewers(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_SUGG_REVRS, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getSuggestedReviewers : " + endPointUrl);
            ResponseEntity<SuggestedReviewerWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    SuggestedReviewerWrapper.class);
            return response.getBody().getSuggestedReviewer();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_SUGGESTED_REVIEWERS_ERROR, e);
        }
    }

    @Override
    public OthrPersBioInfo getOthrPersBioInfo(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_OTH_PERS_BIO_INFO, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getOthrPersBioInfo : " + endPointUrl);
            ResponseEntity<OthrPersBioInfoWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    OthrPersBioInfoWrapper.class);
            return response.getBody().getOthrPersBioInfo();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_OTHER_PERSONNEL_BIO_INFO_ERROR, e);
        }
    }

    @Override
    public ReviewersNotInclude getReviewersNotInclude(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(REVRS_NOT_INCL, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getReviewersNotInclude : " + endPointUrl);
            ResponseEntity<ReviewersNotIncludeWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    ReviewersNotIncludeWrapper.class);
            return response.getBody().getReviewersNotInclude();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_REVIEWERS_NOT_INCLUDE_ERROR, e);
        }
    }

    @Override
    public SectionResponse replacePersonnel(PersonnelParam personnelParam) throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(UPDATE_PERS, "", "");

            LOGGER.debug("Executing POST request to replacePersonnel : " + endPointUrl);
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<PersonnelParam> httpEntity = new HttpEntity<PersonnelParam>(personnelParam, headers);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_UPDATE_ROLE_DELETE_PERSONNEL_ERROR, e);
        }

    }

    @Override
    public DataManagementPlan getDataManagementPlan(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_DMP, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getDataManagementPlan : " + endPointUrl);
            ResponseEntity<DataManagementPlanWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    DataManagementPlanWrapper.class);
            return response.getBody().getDataManagementPlan();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_DATA_MANAGEMENT_PLAN_ERROR, e);
        }
    }

    @Override
    public PostDocMentPlan getPostDocMentoringPlan(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_PMP, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getPostDocMentoringPlan : " + endPointUrl);
            ResponseEntity<PostDocMentPlanWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    PostDocMentPlanWrapper.class);
            return response.getBody().getPostDocMentPlan();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_POST_DOCTORAL_MENT_PLAN_ERROR, e);
        }
    }

    @Override
    public SectionResponse updateProposal(ProposalHeader proposalHeader) throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(proposalURL);
            endpointUrl.append(UPDT_PROPOSAL);

            LOGGER.debug("Executing POST request to updateProposal : " + endpointUrl.toString());
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<ProposalHeader> httpEntity = new HttpEntity<ProposalHeader>(proposalHeader, headers);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_PROPOSAL_TITLE_OR_DUEDATE_ERROR, e);
        }
    }

    @Override
    public CoverSheet getCoverSheet(String propPrepId, String propRevId) throws CommonUtilException {

        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_CV, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getCoverSheet : " + endPointUrl);
            ResponseEntity<CoverSheetWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    CoverSheetWrapper.class);
            return response.getBody().getCoverSheet();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_COVERSHEET_ERROR, e);
        }
    }

    @Override
    public SectionResponse saveCoverSheet(CoverSheet coverSheet) throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            String endPointUrl = getEndPointUrl(SAVE_CV, "", "");
            LOGGER.debug("Executing GET request to saveCoverSheet : " + endPointUrl);

            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<CoverSheet> httpEntity = new HttpEntity<CoverSheet>(coverSheet, headers);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();
        } catch (Exception e) {

            throw new CommonUtilException(Constants.SAVE_COVERSHEET_ERROR, e);
        }
    }

    @Override
    public SectionResponse changeAwardeeOrganization(String propPrepId, String propRevId, String coverSheetId,
            Institution institution) throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            String endPointUrl = getEndPointUrl(UPDT_ORG, propPrepId + slash + propRevId + slash, coverSheetId);

            LOGGER.debug("Executing POST request to changeAwardeeOrganization : " + endPointUrl);

            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<Institution> httpEntity = new HttpEntity<Institution>(institution, headers);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_AWARDEE_ORG_ERROR, e);
        }
    }

    @Override
    public SectionResponse deleteProposalCOA(Long revId, Long persId, String nsfId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(COA);
            endPointUrl.append(slash);
            endPointUrl.append(revId);
            endPointUrl.append(slash);
            endPointUrl.append(persId);
            endPointUrl.append(slash);
            endPointUrl.append(DEL_COA);
            endPointUrl.append(slash);
            endPointUrl.append(nsfId);

            LOGGER.debug("Executing DELETE request to deleteProposalCOA : " + endPointUrl.toString());
            ResponseEntity<SectionResponseWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.DELETE, httpEntity,
                    SectionResponseWrapper.class);

            return response.getBody().getSectionResponse();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DELETE_COA_ERROR, e);
        }
    }

    @Override
    public SectionResponse updateProposalCOA(COA coa) throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(UPDT_COA);

            LOGGER.debug("Executing POST request to updateProposalCOA : " + endPointUrl.toString());
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<COA> httpEntity = new HttpEntity<COA>(coa, headers);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_COA_ERROR, e);
        }
    }

    @Override
    public COA getProposalCOA(Long revId, Long persId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(COA);
            endPointUrl.append(slash);
            endPointUrl.append(revId);
            endPointUrl.append(slash);
            endPointUrl.append(persId);

            LOGGER.debug("Executing GET request to getProposalCOA : " + endPointUrl.toString());

            ResponseEntity<COAWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.GET, httpEntity,
                    COAWrapper.class);

            return response.getBody().getCoa();
        } catch (Exception e) {

            throw new CommonUtilException(Constants.GET_COA_ERROR, e);
        }
    }

    @Override
    public List<COA> getCOAsForProposal(Long revId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(GET_COA_FOR_PROP);
            endPointUrl.append(revId);

            LOGGER.debug("Executing GET request to getCOAsForProposal : " + endPointUrl.toString());
            ResponseEntity<COAWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.GET, httpEntity,
                    COAWrapper.class);

            return response.getBody().getCoas();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_COA_ERROR, e);
        }
    }

    @Override
    public COA getLatestProposalCOA(Long revId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(LATEST_COA);
            endPointUrl.append(revId);

            LOGGER.debug("Executing GET request to getLatestProposalCOA : " + endPointUrl.toString());

            ResponseEntity<COAWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.GET, httpEntity,
                    COAWrapper.class);

            return response.getBody().getCoa();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_LATEST_COA_ERROR, e);
        }
    }

    @Override
    public SectionResponse getPrimaryAwardeeOrganizationId(String propPrepId, String propRevId)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_AWD_ORG_ID, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getPrimaryAwardeeOrganizationId : " + endPointUrl);
            ResponseEntity<SectionResponseWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    SectionResponseWrapper.class);
            return response.getBody().getSectionResponse();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PRIMRAY_AWD_ORG_ID_ERROR, e);
        }
    }

    @Override
    public SectionResponse isCoverSheetExists(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(IS_CV_EXISTS, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to isCoverSheetExists : " + endPointUrl);
            ResponseEntity<SectionResponseWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    SectionResponseWrapper.class);
            return response.getBody().getSectionResponse();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.CHECK_COVERSHEET_EXISTS_ERROR, e);
        }
    }

    @Override
    public ProposalHeader getProposalHeader(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_PROP_HDR, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getProposalHeader : " + endPointUrl);

            ResponseEntity<ProposalHeaderWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    ProposalHeaderWrapper.class);
            return response.getBody().getProposalHeader();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_HEADER_ERROR, e);
        }
    }

    @Override
    public SectionResponse setProposalAccess(ProposalPackage proposalPackage) throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            String endPointUrl = getEndPointUrl(PROP_ACCESS, "", "");

            LOGGER.debug("Executing POST request to setProposalAccess : " + endPointUrl);
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<ProposalPackage> httpEntity = new HttpEntity<ProposalPackage>(proposalPackage, headers);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_SET_PROPOSAL_ACCESS_ERROR, e);
        }
    }

    @Override
    public ProposalPackage getProposalAccess(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(PROP_ACCESS, slash + propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getProposalAccess : " + endPointUrl);
            ResponseEntity<ProposalPackageWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    ProposalPackageWrapper.class);
            return response.getBody().getProposalPackage();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_ACCESS_ERROR, e);
        }
    }

    @Override
    public List<BiographicalSketch> getBioSketchesForProposal(String propRevnId) throws CommonUtilException {

        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(GET_BIO_SKETCH_FOR_PROP);
            endPointUrl.append(propRevnId);

            LOGGER.debug("Executing GET request to getBioSketchesForProposal : " + endPointUrl.toString());
            ResponseEntity<BiographicalSketchWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.GET, httpEntity,
                    BiographicalSketchWrapper.class);
            return response.getBody().getBiographicalSketches();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_BIO_SKETCHES_ERROR, e);
        }
    }

    @Override
    public BiographicalSketch getLatestBioSketch(String propPrepId, String propRevnId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(GET_BIO_SKETCH_LATEST);
            endPointUrl.append(propPrepId);
            endPointUrl.append(slash);
            endPointUrl.append(propRevnId);

            LOGGER.debug("Executing GET request to getLatestBioSketch : " + endPointUrl.toString());
            ResponseEntity<BiographicalSketchWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.GET, httpEntity,
                    BiographicalSketchWrapper.class);
            return response.getBody().getBiographicalSketch();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_LATEST_BIO_SKETCHES_ERROR, e);
        }
    }

    @Override
    public CurrentAndPendingSupport getLatestCurrAndPendSupport(String propPrepId, String propRevnId)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(GET_CURR_PEND_SUPP_LATEST);
            endPointUrl.append(propPrepId);
            endPointUrl.append(slash);
            endPointUrl.append(propRevnId);

            LOGGER.debug("Executing GET request to getLatestCurrAndPendSupport : " + endPointUrl.toString());
            ResponseEntity<CurrentAndPendingSupportWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.GET, httpEntity,
                    CurrentAndPendingSupportWrapper.class);
            return response.getBody().getCurrentAndPendingSupport();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_LATEST_CURR_PEND_SUPPORT_ERROR, e);
        }
    }

    @Override
    public List<Proposal> getProposals(List<ProposalQueryParams> params) throws CommonUtilException {
        ResponseEntity<ProposalWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(GET_PROP);

            LOGGER.debug("Executing POST request to getProposals : " + endPointUrl.toString());
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<List<ProposalQueryParams>> httpEntity = new HttpEntity<List<ProposalQueryParams>>(params,
                    headers);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.POST, httpEntity,
                    ProposalWrapper.class);

            return response.getBody().getProposals();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_SEARCH_PROPOSALS_ERROR, e);
        }
    }

    @Override
    public List<DeadlineTypeLookUp> getDeadlineTypes() throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(deadlineTypesURL);

            LOGGER.debug("Executing GET request to getDeadlineTypes : " + endpointUrl.toString());
            ResponseEntity<DeadlineTypeLookUps> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    DeadlineTypeLookUps.class);
            return response.getBody().getDeadlineTypeLookUps();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_DUE_DATE_TYPES_ERROR, e);
        }
    }

    @Override
    public SubmittedProposal getSubmittedProposal(String nsfPropId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal/submitted/" + nsfPropId);

            LOGGER.debug("Executing GET request to getSubmittedProposal : " + endpointUrl.toString());
            ResponseEntity<SubmittedProposalWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    SubmittedProposalWrapper.class);
            return response.getBody().getSubmittedProposal();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_SUBMITTED_PROPOSAL_ERROR, e);
        }
    }

    @Override
    public ProposalTransfer getProposalForTransfer(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(propURL);
            endpointUrl.append(slash);
            endpointUrl.append(propPrepId);
            endpointUrl.append(slash);
            endpointUrl.append(propRevId);
            endpointUrl.append(slash);
            endpointUrl.append("transfer");

            LOGGER.debug("Executing GET request to getProposalForTransfer : " + endpointUrl.toString());
            ResponseEntity<ProposalTransferWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    ProposalTransferWrapper.class);
            return response.getBody().getProposalTransfer();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_TRANSFER__ERROR, e);
        }

    }

    @Override
    public ProposalPackage createProposalRevision(ProposalCopy rev) throws CommonUtilException {
        ResponseEntity<ProposalPackageWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(CREATE_PROP_REV);

            LOGGER.debug("Executing GET request to createProposalRevision : " + endPointUrl.toString());

            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<ProposalCopy> httpEntity = new HttpEntity<ProposalCopy>(rev, headers);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.POST, httpEntity,
                    ProposalPackageWrapper.class);

            return response != null ? response.getBody().getProposalPackage() : new ProposalPackage();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_REVISION_COPY_ERROR, e);
        }
    }

    @Override
    public WarnMsgs getProposalWarningMessages(String propPrepId, String propRevId, String propPersId,
            String sectionCode) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(WARN_MSGS);
            endPointUrl.append(slash);
            endPointUrl.append(propPrepId);
            endPointUrl.append(slash);
            endPointUrl.append(propRevId);
            endPointUrl.append(slash);
            endPointUrl.append(propPersId);
            endPointUrl.append(slash);
            endPointUrl.append(sectionCode);

            LOGGER.debug("Executing GET request to getProposalWarningMessages : " + endPointUrl.toString());
            ResponseEntity<WarnMsgsWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.GET, httpEntity,
                    WarnMsgsWrapper.class);
            return response.getBody().getWarnMsgs();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_WARNING_MESSAGES_ERROR, e);
        }
    }

    @Override
    public ProposalPackage getProposalByNsfPropId(String nsfPropId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal/any/" + nsfPropId);

            LOGGER.debug("Executing GET request to getProposalByNsfId : " + endpointUrl.toString());
            ResponseEntity<ProposalPackageWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    ProposalPackageWrapper.class);
            return response.getBody().getProposalPackage();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_BY_NSF_ID_ERROR, e);
        }
    }

    @Override
    public SectionResponse submitProposal(ProposalElectronicSign proposalElectronicSign) throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal");
            endpointUrl.append(SUBMIT_PROPOSAL);

            LOGGER.debug("Executing post request to submitProposal : " + endpointUrl.toString());
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<ProposalElectronicSign> httpEntity = new HttpEntity<ProposalElectronicSign>(
                    proposalElectronicSign, headers);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();
        } catch (Exception e) {

            throw new CommonUtilException(Constants.SAVE_SUBMIT_PROPOSAL_ERROR, e);
        }
    }

    @Override
    public ProposalElectronicSign getAORSignature(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal");
            endpointUrl.append(GET_AOR_SIGNATURE);
            endpointUrl.append(slash);
            endpointUrl.append(propPrepId);
            endpointUrl.append(slash);
            endpointUrl.append(propRevId);

            LOGGER.debug("Executing GET request to getAORSignature : " + endpointUrl.toString());
            ResponseEntity<ProposalElectronicSignWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    ProposalElectronicSignWrapper.class);
            return response.getBody().getProposalElectronicSign();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_AOR_SIGNATURE_ERROR, e);
        }
    }

    @Override
    public OtherSuppDocs getOtherSuppDocs(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_OSD, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getOtherSuppDocs : " + endPointUrl);
            ResponseEntity<OtherSuppDocsWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    OtherSuppDocsWrapper.class);
            return response.getBody().getOtherSuppDocs();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_OSD_ERROR, e);
        }
    }

    @Override
    public ElectronicCertificationText getElectronicCertificationText(String electronicCertTypeCode)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(proposalURL);
            endPointUrl.append(slash);
            endPointUrl.append("elecSignCertText");
            endPointUrl.append(slash);
            endPointUrl.append(electronicCertTypeCode);

            LOGGER.debug("Executing GET request to getElectronicCertificationText : " + endPointUrl.toString());
            ResponseEntity<ElectronicCertificationTextWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.GET, httpEntity,
                    ElectronicCertificationTextWrapper.class);
            return response.getBody().getElectronicCertificationText();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ELECTRONIC_CERTIFICATION_TEXT_ERROR, e);
        }
    }

	@Override
	public SectionResponse updateProposalStaticPathUrl(String propPrepId, String propRevId,
			ProposalCompleteTransfer proposalCompleteTransfer) throws CommonUtilException {
		try {
			RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
					requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append("/proposal");
			endpointUrl.append(UPDATE_PROPOSAL_STATIC_PATH_URL);
			endpointUrl.append(slash + propPrepId);
			endpointUrl.append(slash + propRevId);
			LOGGER.debug("Executing post request for updateProposalStaticPathUrl : " + endpointUrl.toString());
			HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
			ResponseEntity<SectionResponseWrapper> response = proposalDataServiceClient.exchange(endpointUrl.toString(),
					HttpMethod.POST, new HttpEntity<ProposalCompleteTransfer>(proposalCompleteTransfer, headers),
					SectionResponseWrapper.class);
			return response.getBody().getSectionResponse();
		} catch (Exception e) {
			throw new CommonUtilException(Constants.COMPLETE_TRANSFER_ERROR, e);
		}
	}

    @Override
    public SectionResponse updateNsfPropId(String propPrepId, String propRevId,
            ProposalCompleteTransfer proposalCompleteTransfer) throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal");
            endpointUrl.append(UPDATE_NSF_PROP_ID);
            endpointUrl.append(slash);
            endpointUrl.append(propPrepId);
            endpointUrl.append(slash);
            endpointUrl.append(propRevId);
            LOGGER.debug("Executing post request for updateNsfPropId : " + endpointUrl.toString());

            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<ProposalCompleteTransfer> httpEntity = new HttpEntity<ProposalCompleteTransfer>(
                    proposalCompleteTransfer, headers);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);
			return response.getBody().getSectionResponse();

        } catch (Exception e) {
            throw new CommonUtilException(Constants.COMPLETE_TRANSFER_ERROR, e);
        }
    }

    @Override
    public BudgetImpact getBudgetImpact(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_BUDI, propPrepId + slash, propRevId);

            LOGGER.debug("Executing GET request to getOtherSuppDocs : " + endPointUrl);

            ResponseEntity<BudgetImpactWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity,
                    BudgetImpactWrapper.class);
            return response.getBody().getBudgetImpact();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_BUDI_ERROR, e);
        }
    }

    @Override
    public SectionResponse updateProposalRevisionCreateDate(String propPrepId, String propRevId)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = serverURL + proposalURL + UPDATE_CREATE_DATE + propPrepId + "/" + propRevId;

            LOGGER.debug("Executing PUT request to updateProposalRevisionCreateDate : " + endPointUrl);
            ResponseEntity<SectionResponseWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.PUT, httpEntity,
                    SectionResponseWrapper.class);
            return response.getBody().getSectionResponse();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_CREATE_DATE_ERROR, e);
        }
    }

    @Override
    public SectionResponse saveProposalUpdateJustification(ProposalUpdateJustification proposalUpdateJustification)
            throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append("/proposal");
            endPointUrl.append(PROPOSAL_UPDATE_JUSTIFICATION);

            LOGGER.debug("Executing POST request to saveProposalUpdateJustification : " + endPointUrl.toString());
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<ProposalUpdateJustification> httpEntity = new HttpEntity<ProposalUpdateJustification>(
                    proposalUpdateJustification, headers);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);

            return response != null ? response.getBody().getSectionResponse() : new SectionResponse();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.SAVE_SECTION_DATA_ERROR, e);
        }
    }

    @Override
    public ProposalUpdateJustification getProposalUpdateJustification(String propPrepId, String propRevId)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal");
            endpointUrl.append(PROPOSAL_UPDATE_JUSTIFICATION);
            endpointUrl.append(slash);
            endpointUrl.append(propPrepId);
            endpointUrl.append(slash);
            endpointUrl.append(propRevId);

            LOGGER.debug("Executing GET request to getProposalUpdateJustification : " + endpointUrl.toString());
            ResponseEntity<ProposalUpdateJustificationWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    ProposalUpdateJustificationWrapper.class);
            return response.getBody().getProposalUpdateJustification();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_UPDATE_JUSTIFICATION_ERROR, e);
        }
    }

    @Override
    public ProposalRevision getProposalRevision(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal");
            endpointUrl.append(PROPOSAL_REVISION);
            endpointUrl.append(slash);
            endpointUrl.append(propPrepId);
            endpointUrl.append(slash);
            endpointUrl.append(propRevId);

            LOGGER.debug("Executing GET request to getProposalRevision : " + endpointUrl.toString());
            ResponseEntity<ProposalRevisionWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    ProposalRevisionWrapper.class);
            return response.getBody().getProposalRevision();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_REVISION_ERROR, e);
        }
    }

    @Override
    public SectionData getAllSectionStatusData(String propPrepId, String propRevnId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal");
            endpointUrl.append(SECTIONS);
            endpointUrl.append(slash);
            endpointUrl.append(propPrepId);
            endpointUrl.append(slash);
            endpointUrl.append(propRevnId);

            LOGGER.debug("Executing GET request to getLatestSectionStatusData : " + endpointUrl.toString());
            ResponseEntity<SectionDataWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    SectionDataWrapper.class);
            return response.getBody().getSectionData();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_REVISION_ERROR, e);
        }
    }

    @Override
    public SectionData getLatestSectionStatusData(String propPrepId, String propRevnId, String sectionCode)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal");
            endpointUrl.append(SECTIONS);
            endpointUrl.append(slash);
            endpointUrl.append(propPrepId);
            endpointUrl.append(slash);
            endpointUrl.append(propRevnId);
            endpointUrl.append(slash);
            endpointUrl.append(sectionCode);

            LOGGER.debug("Executing GET request to getLatestSectionStatusData : " + endpointUrl.toString());
            ResponseEntity<SectionDataWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    SectionDataWrapper.class);
            return response.getBody().getSectionData();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_REVISION_ERROR, e);
        }
    }

    @Override
    public PersonnelData getLatestPersonnelSectionStatusData(String propPrepId, String propRevnId)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal");
            endpointUrl.append(SECTIONS);
            endpointUrl.append(slash);
            endpointUrl.append(PERSONNEL);
            endpointUrl.append(slash);
            endpointUrl.append(propPrepId);
            endpointUrl.append(slash);
            endpointUrl.append(propRevnId);

            LOGGER.debug("Executing GET request to getLatestPersonnelSectionStatusData : " + endpointUrl.toString());
            ResponseEntity<PersonnelDataWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    PersonnelDataWrapper.class);
            return response.getBody().getPersonnelData();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_REVISION_ERROR, e);
        }
    }

    @Override
    public Personnel getSeniorPersonnelByNsfIdAndRole(String propRevId, String propPersNfId, String propPersRoleCode)
            throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            String endPointUrl = getEndPointUrl(GET_PER_BY_ID_AND_ROLE, propRevId + slash,
                    propPersNfId + slash + propPersRoleCode);

            LOGGER.debug("Executing GET request to getSeniorPersonnelByNsfIdAndRole : " + endPointUrl);
            ResponseEntity<Personnels> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl, HttpMethod.GET, httpEntity, Personnels.class);
            return response.getBody().getPersonnel();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PERSONNEL_ERROR, e);
        }
    }

    @Override
    public WarnMsgs getOnlyProposalWarningMessages(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append(ONLY_WARN_MSGS + propPrepId + slash + propRevId);

            LOGGER.debug("Executing GET request to getOnlyProposalWarningMessages : " + endPointUrl.toString());
            ResponseEntity<WarnMsgsWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.GET, httpEntity,
                    WarnMsgsWrapper.class);
            return response.getBody().getWarnMsgs();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_WARNING_MESSAGES_ERROR, e);
        }
    }

    @Override
    public SectionResponse updateProposalRevisionStatus(String propRevId, ProposalPackage proposalPackage)
            throws CommonUtilException {
        ResponseEntity<SectionResponseWrapper> response = null;
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal");
            endpointUrl.append(UPDATE_PROPOSAL_REVISION_STATUS);
            endpointUrl.append(slash);
            endpointUrl.append(propRevId);
            LOGGER.debug("Executing post request for updateProposalStatus : " + endpointUrl.toString());
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<ProposalPackage> httpEntity = new HttpEntity<ProposalPackage>(proposalPackage, headers);
            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.POST, httpEntity,
                    SectionResponseWrapper.class);
	        return response.getBody().getSectionResponse();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.UPDATE_PROPOSAL_STATUS_ERROR, e);
        }
    }

    @Override
    public Boolean getPersonnelRevnUpdateStatus(String propPrepId, String propRevId) throws CommonUtilException {
        ResponseEntity<BooleanResponseWrapper> response = null;

        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endPointUrl = new StringBuilder(serverURL);
            endPointUrl.append("/proposal");
            endPointUrl.append(GET_PERSONNEL_REVN_UPDATE);
            endPointUrl.append(slash);
            endPointUrl.append(propPrepId);
            endPointUrl.append(slash);
            endPointUrl.append(propRevId);

            LOGGER.debug("Executing GET request to getPersonnelRevnUpdateStatus : " + endPointUrl.toString());
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endPointUrl.toString(), HttpMethod.GET, httpEntity,
                    BooleanResponseWrapper.class);
            return response.getBody().getBooleanResponse().getCondition();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PERSONNEL_REVISION_UPDATE_STATUS, e);
        }
    }

    @Override
    public BudgetRevision getBudgetRevisions(String propPrepId, String propRevId) throws CommonUtilException {
        try {
            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);

            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append("/proposal");
            endpointUrl.append(BUDGET_REVISIONS);
            endpointUrl.append(slash);
            endpointUrl.append(propPrepId);
            endpointUrl.append(slash);
            endpointUrl.append(propRevId);

            LOGGER.debug("Executing GET request to getProposalRevision : " + endpointUrl.toString());
            ResponseEntity<BudgetRevisionWrapper> response = null;
            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
                    BudgetRevisionWrapper.class);
            LOGGER.debug("********** Budget Revision List : " + response.getBody().getBudgetRevision());
            return response.getBody().getBudgetRevision();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_REVISION_ERROR, e);
        }
    }

	@Override
	public List<FundingOpportunity> getFundingOppExclusionList() throws CommonUtilException {
		 try {
	            RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
	                    requestTimeout);

	            StringBuilder endpointUrl = new StringBuilder(serverURL);
	            endpointUrl.append(fundingOppExclusionURL);

	            LOGGER.debug("Executing GET request to getFundingOppExclusionList : " + endpointUrl.toString());
	            ResponseEntity<FundingOpportunities> response = null;
	            HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);

	            response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
	            		FundingOpportunities.class);
	            return response.getBody().getFundingOpportunities();
	        } catch (CommonUtilException e) {
	            throw new CommonUtilException(Constants.GET_FUNDING_OPP_EXCLUSION_ERROR, e);
	        }
	}
}
