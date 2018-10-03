package gov.nsf.psm.proposaldata.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.nsf.psm.foundation.controller.PsmBaseController;
import gov.nsf.psm.foundation.ember.model.EmberModel;
import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.BiographicalSketch;
import gov.nsf.psm.foundation.model.BooleanResponse;
import gov.nsf.psm.foundation.model.BudgetImpact;
import gov.nsf.psm.foundation.model.BudgetJustification;
import gov.nsf.psm.foundation.model.COA;
import gov.nsf.psm.foundation.model.CurrentAndPendingSupport;
import gov.nsf.psm.foundation.model.DataManagementPlan;
import gov.nsf.psm.foundation.model.FacilitiesEquipment;
import gov.nsf.psm.foundation.model.OtherSuppDocs;
import gov.nsf.psm.foundation.model.Personnel;
import gov.nsf.psm.foundation.model.PersonnelData;
import gov.nsf.psm.foundation.model.PersonnelSectionData;
import gov.nsf.psm.foundation.model.PostDocMentPlan;
import gov.nsf.psm.foundation.model.ProjectDescription;
import gov.nsf.psm.foundation.model.ProjectSummary;
import gov.nsf.psm.foundation.model.ReferencesCited;
import gov.nsf.psm.foundation.model.Section;
import gov.nsf.psm.foundation.model.SectionData;
import gov.nsf.psm.foundation.model.SectionResponse;
import gov.nsf.psm.foundation.model.SectionStatus;
import gov.nsf.psm.foundation.model.SuggestedReviewer;
import gov.nsf.psm.foundation.model.WarnMsgs;
import gov.nsf.psm.foundation.model.budget.InstitutionBudget;
import gov.nsf.psm.foundation.model.coversheet.CoverSheet;
import gov.nsf.psm.foundation.model.proposal.ProposalPackage;
import gov.nsf.psm.foundation.model.proposal.ProposalTransfer;
import gov.nsf.psm.foundation.model.proposal.SubmittedProposal;
import gov.nsf.psm.proposaldata.service.ProposalDataService;
import gov.nsf.psm.proposaldata.service.ProposalTransferBuilderService;
import gov.nsf.psm.proposaldata.service.parameter.SectionStatusParameter;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/api/v1")
@ApiResponses(value = { @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error") })
public class ProposalDataReadController extends PsmBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalDataReadController.class);

    @Autowired
    ProposalDataService dataService;

    @Autowired
    ProposalTransferBuilderService transferBuilderService;

    @ApiOperation(value = "Get a Section object", notes = "This API retrieves a Section object(Cover Sheet,Project Summary,Budget Justification, Facilties Equipment & Resources, Project Description, Referencies Cited,DataManagementPlan, Post Doc mentoring plan and List of suggested Reviewers) from the PSM database", response = Section.class)
    @RequestMapping(path = "/proposal/{sectionCode}/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getSection(@PathVariable String sectionCode, @PathVariable String propPrepId,
            @PathVariable String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getSection()");
        EmberModel emberModel = null;

        Section section = Section.getSection(sectionCode);

        switch (section) {

        case COVER_SHEET:
            emberModel = getCoverSheet(propPrepId, propRevId);
            break;

        case PROJ_SUMM:
            emberModel = getProjectSummary(propPrepId, propRevId);
            break;

        case BUDGET_JUST:
            emberModel = getBudgetJustification(propPrepId, propRevId);
            break;

        case FER:
            emberModel = getFacilitiesEquipment(propPrepId, propRevId);
            break;

        case PROJ_DESC:
            emberModel = getProjectDescription(propPrepId, propRevId);
            break;

        case REF_CITED:
            emberModel = getReferencesCited(propPrepId, propRevId);
            break;

        case DMP:
            emberModel = getDataManagementPlan(propPrepId, propRevId);
            break;

        case PMP:
            emberModel = getMentoringPlan(propPrepId, propRevId);
            break;

        case SRL:
            emberModel = getSuggestedReviewers(propPrepId, propRevId);
            break;

        case OSD:
            emberModel = getOtherSuppDocs(propPrepId, propRevId);
            break;

        case BUDI:
            emberModel = getBudgetImpact(propPrepId, propRevId);
            break;

        default:
            break;
        }

        return emberModel;
    }

    /**
     * This method pulls the CoverSheet info from DB.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public EmberModel getCoverSheet(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getCoverSheet()");
        CoverSheet coverSheet = dataService.getCoverSheet(propPrepId, propRevId);
        return new EmberModel.Builder<>("coverSheet", coverSheet).build();
    }

    /**
     * This method pulls the Project Summary info from DB.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public EmberModel getProjectSummary(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getProjectSummary()");
        ProjectSummary projectSummary = dataService.getProjectSummary(propPrepId, propRevId);
        return new EmberModel.Builder<>(ProjectSummary.getClassCamelCaseName(), projectSummary).build();
    }

    /**
     * This method pulls the References Cited info from DB.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public EmberModel getReferencesCited(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getReferencesCited()");
        ReferencesCited referencesCited = dataService.getReferenceCited(propPrepId, propRevId);
        return new EmberModel.Builder<>("referencesCited", referencesCited).build();
    }

    /**
     * This method pulls the Facilities & Equipment info from DB.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public EmberModel getFacilitiesEquipment(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getFacilitiesEquipment()");
        FacilitiesEquipment facEquipment = dataService.getFacilitiesEquipment(propPrepId, propRevId);
        return new EmberModel.Builder<>("facilitiesEquipment", facEquipment).build();
    }

    /**
     * This method pulls the Project Description info from DB.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public EmberModel getProjectDescription(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getProjectDescription()");
        ProjectDescription projectDescription = dataService.getProjectDescription(propPrepId, propRevId);
        return new EmberModel.Builder<>("projectDescription", projectDescription).build();
    }

    /**
     * This method pulls the Budget Justification info from DB.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public EmberModel getBudgetJustification(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getBudgetJustification()");
        BudgetJustification budgetJustification = dataService.getBudgetJustification(propPrepId, propRevId);
        return new EmberModel.Builder<>("budgetJustification", budgetJustification).build();
    }

    /**
     * This method pulls the Data Management PlN info from DB.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public EmberModel getDataManagementPlan(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getDataManagementPlan()");
        DataManagementPlan dataManagementPlan = dataService.getDataManagementPlan(propPrepId, propRevId);
        return new EmberModel.Builder<>("dataManagementPlan", dataManagementPlan).build();
    }

    /**
     * This method pulls the Post Doc Mentoring Plan info from DB.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public EmberModel getMentoringPlan(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getMentoringPlan()");
        PostDocMentPlan postDocMentPlan = dataService.getPostDocMentoringPlan(propPrepId, propRevId);
        return new EmberModel.Builder<>("postDocMentPlan", postDocMentPlan).build();
    }

    /**
     * This method pulls the Suggest Reviewers info from DB.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public EmberModel getSuggestedReviewers(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getSuggestedReviewers()");
        SuggestedReviewer suggestedReviewer = dataService.getSuggestedReviewers(propPrepId, propRevId);
        return new EmberModel.Builder<>("suggestedReviewer", suggestedReviewer).build();
    }

    /**
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public EmberModel getOtherSuppDocs(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getSuggestedReviewers()");
        OtherSuppDocs otherSuppDocs = dataService.getOtherSuppDocs(propPrepId, propRevId);
        return new EmberModel.Builder<>("otherSuppDocs", otherSuppDocs).build();
    }

    /**
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public EmberModel getBudgetImpact(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getBudgetImpact()");
        BudgetImpact budgetImpact = dataService.getBudgetImpact(propPrepId, propRevId);
        return new EmberModel.Builder<>("budgetImpact", budgetImpact).build();
    }

    /**
     * This method retrieves max update date for all Biosketch objects
     * associated with a single proposal from the PSM database
     * 
     * @param propPrepId
     * @return
     * @throws CommonUtilException
     */
    @ApiOperation(value = "Get max biographical sketch update date", notes = "This API retrieves max update date for all Biosketch objects associated with a single proposal from the PSM database", response = Date.class)
    @RequestMapping(path = "/proposal/biosketch/latest/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getLatestBiographicalSketch(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getLatestBiographicalSketch()");
        BiographicalSketch biographicalSketch = dataService.getLatestBiographicalSketch(propPrepId, propRevId);
        return new EmberModel.Builder<>(BiographicalSketch.getClassCamelCaseName(), biographicalSketch).build();
    }

    /**
     * 
     * @param propPrepId
     * @param propRevnId
     * @return
     * @throws CommonUtilException
     */
    @ApiOperation(value = "Get latest current and pending support object", notes = "This API retrieves the latest current and pending object from the PSM database", response = CurrentAndPendingSupport.class)
    @RequestMapping(path = "/proposal/currpendsupp/latest/{propPrepId}/{propRevnId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getLatestCurrentAndPendingSupport(@PathVariable String propPrepId,
            @PathVariable String propRevnId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getLatestCurrentAndPendingSupport()");
        CurrentAndPendingSupport currentAndPendingSupport = dataService.getLatestCurrAndPendSupport(propPrepId,
                propRevnId);
        return new EmberModel.Builder<>(CurrentAndPendingSupport.getClassCamelCaseName(), currentAndPendingSupport)
                .build();
    }

    @ApiOperation(value = "get latest proposal COA object", notes = "This API retrieves the latest proposal COA data", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/coa/latest/{revId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel getLatestProposalCOA(@PathVariable Long revId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getLatestProposalCOA()");
        COA coa = dataService.getLatestProposalCOA(revId);
        return new EmberModel.Builder<>(COA.getClassCamelCaseName(), coa).build();
    }

    @ApiOperation(value = "Get proposal for transfer", notes = "This API retrieves proposal data for transfer", response = ProposalTransfer.class)
    @RequestMapping(path = "/proposal/{propPrepId}/{propRevId}/transfer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getProposalForTransfer(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getProposalForTransfer()");
        return new EmberModel.Builder<>(ProposalTransfer.getClassCamelCaseName(),
                transferBuilderService.build(propPrepId, propRevId)).build();
    }

    @ApiOperation(value = "Get all COA objects for a proposal", notes = "This API retrieves all COA data for a proposal", response = COA.class, responseContainer = "List")
    @RequestMapping(path = "/proposal/coa/all/{propRevnId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel getCOAsForProposal(@PathVariable Long propRevnId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getCOAsForProposal()");
        List<COA> coas = dataService.getCOAsForProposal(propRevnId);
        return new EmberModel.Builder<>("coa", coas).build();
    }

    @ApiOperation(value = "Get all Bio Sketch objects for a proposal", notes = "This API retrieves all Bio Sketch data for a proposal", response = BiographicalSketch.class, responseContainer = "List")
    @RequestMapping(path = "/proposal/biosketch/all/{propRevnId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel getBioSketchesForProposal(@PathVariable String propRevnId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getBioSketchesForProposal()");
        List<BiographicalSketch> bios = dataService.getBiographicalSketchesForProposal(propRevnId);
        return new EmberModel.Builder<>(BiographicalSketch.class, bios).build();
    }

    @ApiOperation(value = "Get all Current and Pending Support objects for a proposal", notes = "This API retrieves all Current and Pending Support data for a proposal", response = CurrentAndPendingSupport.class, responseContainer = "List")
    @RequestMapping(path = "/proposal/currpendsupp/all/{propRevnId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel getCurrentAndPendingSupportForProposal(@PathVariable String propRevnId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getCurrentAndPendingSupportForProposal()");
        List<CurrentAndPendingSupport> cps = dataService.getCurrentAndPendingSupportForProposal(propRevnId);
        return new EmberModel.Builder<>("cpsup", cps).build();
    }

    @ApiOperation(value = "Get proposal by NSF ID", notes = "This API retrieves proposal data by NSF ID", response = ProposalPackage.class)
    @RequestMapping(path = "/proposal/any/{nsfPropId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getProposalByNsfPropId(@PathVariable String nsfPropId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getProposalByNsfPropId()");
        ProposalPackage prop = dataService.getProposalByNsfPropId(nsfPropId);
        return new EmberModel.Builder<>(ProposalPackage.getClassCamelCaseName(), prop).build();
    }

    @ApiOperation(value = "Get submitted proposal for transfer", notes = "This API retrieves proposal data for transfer", response = ProposalTransfer.class)
    @RequestMapping(path = "/proposal/submitted/{nsfPropId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getProposalSubmitted(@PathVariable String nsfPropId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getProposalSubmitted()");
        SubmittedProposal subProp = dataService.getSubmittedProposal(nsfPropId);
        return new EmberModel.Builder<>(SubmittedProposal.getClassCamelCaseName(), subProp).build();
    }

    @ApiOperation(value = "Get budgets for a proposal", notes = "This API retrieves all budgets for a proposal revision", response = ProposalTransfer.class)
    @RequestMapping(path = "/proposal/budgets/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getProposalBudgets(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getProposalBudgets()");
        List<InstitutionBudget> budgets = dataService.getBudget(propPrepId, propRevId);
        return new EmberModel.Builder<>("budgets", budgets).build();
    }

    @ApiOperation(value = "Get Proposal Warning messages", notes = "This API retrieves proposal Warning/Error Messages", response = ProposalTransfer.class)
    @RequestMapping(path = "/proposal/warnmsgs/{propPrepId}/{propRevId}/{propPersId}/{sectionCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getProposalWarningMessages(@PathVariable String propPrepId, @PathVariable String propRevId,
            @PathVariable String propPersId, @PathVariable String sectionCode) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getProposalWarningMessages()");
        WarnMsgs warnMsgs = dataService.getProposalWarningMessages(propPrepId, propRevId, propPersId, sectionCode);
        return new EmberModel.Builder<>("warnMsgs", warnMsgs).build();
    }

    @ApiOperation(value = "Get latest section data", notes = "This API retrieves all section data for a proposal revision", response = SectionData.class)
    @RequestMapping(path = "/proposal/sections/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getAllSectionStatusData(@PathVariable String propPrepId, @PathVariable String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getAllSectionStatusData()");
        SectionStatusParameter param = new SectionStatusParameter();
        param.setPropPrepId(propPrepId);
        param.setPropPrepRevnId(propRevId);
        Map<Section, SectionStatus> sections = dataService.getLatestSectionStatusData(param);
        SectionData data = new SectionData();
        data.setSections(sections);
        return new EmberModel.Builder<>(SectionData.getClassCamelCaseName(), data).build();
    }
    
    @ApiOperation(value = "Get latest section data", notes = "This API retrieves data for a specific section", response = SectionData.class)
    @RequestMapping(path = "/proposal/sections/{propPrepId}/{propRevId}/{sectionCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getLatestSectionStatusData(@PathVariable String propPrepId, @PathVariable String propRevId,
            @PathVariable String sectionCode) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getLatestSectionStatusData()");
        SectionStatusParameter param = new SectionStatusParameter();
        param.setPropPrepId(propPrepId);
        param.setPropPrepRevnId(propRevId);
        param.setSectionCode(sectionCode);
        Map<Section, SectionStatus> sections = dataService.getLatestSectionStatusData(param);
        SectionData data = new SectionData();
        data.setSections(sections);
        return new EmberModel.Builder<>(SectionData.getClassCamelCaseName(), data).build();
    }

    @ApiOperation(value = "Get latest personnel section data", notes = "This API retrieves all personnel section data for a proposal revision", response = PersonnelData.class)
    @RequestMapping(path = "/proposal/sections/personnel/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getLatestPersonnelSectionStatusData(@PathVariable String propPrepId,
            @PathVariable String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getLatestPersonnelSectionStatusData()");
        List<PersonnelSectionData> statuses = dataService.getLatestPersonnelSectionStatusData(propPrepId, propRevId);
        PersonnelData data = new PersonnelData();
        data.setStatuses(statuses);
        return new EmberModel.Builder<>(PersonnelData.getClassCamelCaseName(), data).build();
    }

    @ApiOperation(value = "Get person by nsf id and role code for a specific proposal revision", notes = "This API retrieves a person by nsf id and role code for a specific proposal revision", response = Personnel.class)
    @RequestMapping(path = "/proposal/personnel/getbynsfidandrole/{propRevId}/{propPersNsfId}/{propPersRoleCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getSeniorPersonnelByNsfIdAndRole(@PathVariable String propRevId,
            @PathVariable String propPersNsfId, @PathVariable String propPersRoleCode) throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getSeniorPersonnelByNsfIdAndRole()");
        Personnel pers = dataService.getSeniorPersonnelByNsfIdAndRole(propRevId, propPersNsfId, propPersRoleCode);
        return new EmberModel.Builder<>("personnel", pers).build();
    }

    @ApiOperation(value = "Get Only Proposal Warning messages for given Prep Id & Rev Id", notes = "This API retrieves proposal Warning/Error Messages", response = WarnMsgs.class)
    @RequestMapping(path = "/proposal/warningmsgs/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getOnlyProposalWarningMessages(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataReadController.getProposalWarningMessages()");
        WarnMsgs warnMsgs = dataService.getOnlyProposalWarningMessages(propPrepId, propRevId);
        return new EmberModel.Builder<>("warnMsgs", warnMsgs).build();
    }
    
    @ApiOperation(value = "Checks if there was a personnel change for the revision", notes = "This API retrieves status if personnel was changed for revision", response = BooleanResponse.class)
    @RequestMapping(path = "/proposal/personnel/updated/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getPersonnelRevnUpdateStatus(@PathVariable String propPrepId, @PathVariable String propRevId) throws CommonUtilException  {
    	BooleanResponse booleanResponse = new BooleanResponse(Boolean.valueOf(dataService.checkRevnPersonnelUpdate(propPrepId, propRevId)));
    	return new EmberModel.Builder<>(BooleanResponse.getClassCamelCaseName(), booleanResponse).build();
    }
}