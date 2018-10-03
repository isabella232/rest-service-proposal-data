package gov.nsf.psm.proposaldata.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.nsf.psm.foundation.controller.PsmBaseController;
import gov.nsf.psm.foundation.ember.model.EmberModel;
import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.BiographicalSketch;
import gov.nsf.psm.foundation.model.BudgetJustification;
import gov.nsf.psm.foundation.model.BudgetRevision;
import gov.nsf.psm.foundation.model.COA;
import gov.nsf.psm.foundation.model.CurrentAndPendingSupport;
import gov.nsf.psm.foundation.model.Institution;
import gov.nsf.psm.foundation.model.OthrPersBioInfo;
import gov.nsf.psm.foundation.model.Personnel;
import gov.nsf.psm.foundation.model.PersonnelParam;
import gov.nsf.psm.foundation.model.PostDocMentPlan;
import gov.nsf.psm.foundation.model.ProjectSummary;
import gov.nsf.psm.foundation.model.ProposalElectronicSign;
import gov.nsf.psm.foundation.model.ProposalUpdateJustification;
import gov.nsf.psm.foundation.model.ReviewersNotInclude;
import gov.nsf.psm.foundation.model.Section;
import gov.nsf.psm.foundation.model.SectionResponse;
import gov.nsf.psm.foundation.model.UploadableProposalSection;
import gov.nsf.psm.foundation.model.budget.InstitutionBudget;
import gov.nsf.psm.foundation.model.coversheet.CoverSheet;
import gov.nsf.psm.foundation.model.lookup.ElectronicCertificationText;
import gov.nsf.psm.foundation.model.proposal.Proposal;
import gov.nsf.psm.foundation.model.proposal.ProposalCompleteTransfer;
import gov.nsf.psm.foundation.model.proposal.ProposalCopy;
import gov.nsf.psm.foundation.model.proposal.ProposalHeader;
import gov.nsf.psm.foundation.model.proposal.ProposalPackage;
import gov.nsf.psm.foundation.model.proposal.ProposalQueryParams;
import gov.nsf.psm.foundation.model.proposal.ProposalRevision;
import gov.nsf.psm.proposaldata.service.ProposalDataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/api/v1")
@ApiResponses(value = { @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error") })
public class ProposalDataServiceController extends PsmBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalDataServiceController.class);

    @Autowired
    ProposalDataService dataService;

    @ApiOperation(value = "Get a proposal", notes = "This API returns a proposal given a preparation id and revision id", response = ProposalPackage.class)
    @RequestMapping(path = "/proposal/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getProposal(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getProposal()");
        ProposalPackage propPkg = dataService.getProposalPrepById(propPrepId, propRevId);
        return new EmberModel.Builder<>(ProposalPackage.getClassCamelCaseName(), propPkg).build();
    }

    @ApiOperation(value = "Save a proposal", notes = "This API saves a given proposal to the PSM database", response = ProposalPackage.class)
    @RequestMapping(path = "/proposal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel saveProposal(@RequestBody ProposalPackage proposalPackage) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.saveProposal()");
        ProposalPackage propRes = dataService.saveProposalPrep(proposalPackage);
        return new EmberModel.Builder<>(ProposalPackage.getClassCamelCaseName(), propRes).build();
    }

    @ApiOperation(value = "Save a budget justification", notes = "This API save a budget justification to the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/{propPrepId}/{propRevId}/budgJust", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel saveBudgetJustification(@RequestBody BudgetJustification budgetJustification,
            @PathVariable String propPrepId, @PathVariable String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.saveBudgetJustification()");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        Section section = Section.getSection(Section.BUDGET_JUST.getCode());
        saveStatus = dataService.saveUploadableProposalSection(budgetJustification, propPrepId, propRevId, section,
                null);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Save a proposal update justification", notes = "This API save a proposal update justification to the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/proposalUpdateJustification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel saveProposalUpdateJustification(
            @RequestBody ProposalUpdateJustification proposalUpdtJustification) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.saveProposalUpdateJustification()");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        saveStatus = dataService.saveProposalUpdateJustification(proposalUpdtJustification);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Get Other Proposal update justification  object", notes = "This API retrieves a proposal update justification object from the PSM database", response = ProposalUpdateJustification.class)
    @RequestMapping(path = "/proposal/proposalUpdateJustification/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getProposalUpdateJustification(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getProposalUpdateJustification()");
        ProposalUpdateJustification proposalUpdateJustification = dataService.getProposalUpdateJustification(propPrepId,
                propRevId);
        return new EmberModel.Builder<>("proposalUpdateJustification", proposalUpdateJustification).build();
    }

    @ApiOperation(value = "Get Reviewers not Include object", notes = "This API retrieves a Reviewers not Include object from the PSM database", response = ReviewersNotInclude.class)
    @RequestMapping(path = "/proposal/getrevrsnotincl/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getReviewersNotInclude(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getReviewersNotInclude()");
        ReviewersNotInclude reviewersNotInclude = dataService.getReviewersNotInclude(propPrepId, propRevId);
        return new EmberModel.Builder<>("reviewersNotInclude", reviewersNotInclude).build();
    }

    @ApiOperation(value = "Get Other Personal Biographical Information object", notes = "This API retrieves a Other Personal Biographical Information object from the PSM database", response = OthrPersBioInfo.class)
    @RequestMapping(path = "/proposal/getothrpersbioinfo/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getOthrPersBioInfo(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getOthrPersBioInfo()");
        OthrPersBioInfo othrPersBioInfo = dataService.getOthrPersBioInfo(propPrepId, propRevId);
        return new EmberModel.Builder<>("othrPersBioInfo", othrPersBioInfo).build();
    }

    @ApiOperation(value = "Get Post Doc Mentoring Plan object", notes = "This API retrieves a Post Doc Mentoring Plan object from the PSM database", response = PostDocMentPlan.class)
    @RequestMapping(path = "/proposal/getmentplan/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getPostDocMentoringPlan(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getPostDocMentoringPlan()");
        PostDocMentPlan postDocMentPlan = dataService.getPostDocMentoringPlan(propPrepId, propRevId);
        return new EmberModel.Builder<>("postDocMentPlan", postDocMentPlan).build();
    }

    @ApiOperation(value = "Save a project summary", notes = "This API save a project summary to the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/{propPrepId}/{propRevId}/projSumm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel saveProjectSummary(@RequestBody ProjectSummary projectSummary, @PathVariable String propPrepId,
            @PathVariable String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.saveProjectSummary()");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        Section section = Section.getSection(Section.PROJ_SUMM.getCode());
        saveStatus = dataService.saveUploadableProposalSection(projectSummary, propPrepId, propRevId, section, null);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Get a biographical sketch", notes = "This API retrieves a biosketch object from the PSM database", response = BiographicalSketch.class)
    @RequestMapping(path = "/proposal/getbiosketch/{propPrepId}/{propRevId}/{propPersId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getBiographicalSketch(@PathVariable String propPrepId, @PathVariable String propRevId,
            @PathVariable String propPersId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getBiographicalSketch()");
        BiographicalSketch biographicalSketch = dataService.getBiographicalSketch(propPrepId, propRevId, propPersId);
        return new EmberModel.Builder<>(BiographicalSketch.getClassCamelCaseName(), biographicalSketch).build();
    }

    @ApiOperation(value = "Get a current and pending support object", notes = "This API retrieves a current and pending object from the PSM database", response = CurrentAndPendingSupport.class)
    @RequestMapping(path = "/proposal/getcurrpendsupp/{propPrepId}/{propRevId}/{propPersId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getCurrentAndPendingSupport(@PathVariable String propPrepId, @PathVariable String propRevId,
            @PathVariable String propPersId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getCurrentAndPendingSupport()");
        CurrentAndPendingSupport currentAndPendingSupport = dataService.getCurrentAndPendingSupport(propPrepId,
                propRevId, propPersId);
        return new EmberModel.Builder<>(CurrentAndPendingSupport.getClassCamelCaseName(), currentAndPendingSupport)
                .build();
    }

    @ApiOperation(value = "Save a proposal section object", notes = "This API saves a generic proposal section to the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/{propPrepId}/{propRevId}/{sectionCode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel saveUploadableProposalSection(@RequestBody UploadableProposalSection uploadableProposalSection,
            @PathVariable String propPrepId, @PathVariable String propRevId, @PathVariable String sectionCode,
            Map<String, Object> metaData) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.saveUploadableProposalSection()");
        SectionResponse sectionResponse = new SectionResponse();

        boolean saveStatus = false;
        Section section = Section.getSection(sectionCode);
        saveStatus = dataService.saveUploadableProposalSection(uploadableProposalSection, propPrepId, propRevId,
                section, metaData);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Delete a proposal section object", notes = "This API deletes a generic proposal section object to the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/deleteSection/{propPrepId}/{propRevId}/{sectionCode}/{nsfId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel deleteUploadableProposalSection(@PathVariable String propPrepId, @PathVariable String propRevId,
            @PathVariable String sectionCode, @PathVariable String nsfId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.deleteUploadableProposalSection()");
        boolean deleteStatus = false;
        SectionResponse sectionResponse = new SectionResponse();
        Section section = Section.getSection(sectionCode);
        deleteStatus = dataService.deleteUploadableProposalSection(propPrepId, propRevId, section, nsfId);
        sectionResponse.setSaveStatus(deleteStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Save a senior personnel proposal section object", notes = "This API saves a senior personnel proposal section object to the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/{propPrepId}/{propRevId}/{propPersId}/{sectionCode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel saveUploadableSeniorPersonnelDocuments(
            @RequestBody UploadableProposalSection uploadableProposalSection, @PathVariable String propPrepId,
            @PathVariable String propRevId, @PathVariable String propPersId, @PathVariable String sectionCode,
            Map<String, Object> metaData) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.saveUploadableSeniorPersonnelDocuments()");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        Section section = Section.getSection(sectionCode);
        saveStatus = dataService.saveUploadableSeniorPersonnelDocuments(uploadableProposalSection, propPrepId,
                propRevId, propPersId, section, metaData);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Delete a senior personnel proposal section object", notes = "This API deletes a senior personnel proposal section object from the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/{propPrepId}/{propRevId}/{propPersId}/{sectionCode}/{nsfId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel deleteSeniorPersonnelDocuments(@PathVariable String propPrepId, @PathVariable String propRevId,
            @PathVariable String propPersId, @PathVariable String sectionCode, @PathVariable String nsfId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.deleteSeniorPersonnelDocuments()");
        boolean deleteStatus = false;
        SectionResponse sectionResponse = new SectionResponse();
        Section section = Section.getSection(sectionCode);
        deleteStatus = dataService.deleteSeniorPersonnelDocuments(propPrepId, propRevId, propPersId, section, nsfId);
        sectionResponse.setSaveStatus(deleteStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Save an institution budget object", notes = "This API saves an institution budget object to the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/budget/saveInstBudget", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel saveInstitutionBudget(@RequestBody InstitutionBudget institutionBudget)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.saveInstitutionBudget()");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        saveStatus = dataService.saveInstitutionBudget(institutionBudget, true);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Get an institution budget object", notes = "This API retrieves an institution budget object from the PSM database", response = InstitutionBudget.class)
    @RequestMapping(path = "/proposal/budget/getInstBudget/{propPrepId}/{propRevId}/{instId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getInstitutionBudget(@PathVariable String propPrepId, @PathVariable String propRevId,
            @PathVariable String instId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getInstitutionBudget()");
        InstitutionBudget institutionBudget = dataService.getInstitutionBudget(propPrepId, propRevId, instId);
        return new EmberModel.Builder<>("institutionBudget", institutionBudget).build();
    }

    @ApiOperation(value = "Get a personnel object", notes = "This API retrieves a personnel object from the PSM database", response = Personnel.class)
    @RequestMapping(path = "/proposal/personnel/getPersonnel/{propPrepId}/{propRevId}/{propPersId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getPersonnel(@PathVariable String propPrepId, @PathVariable String propRevId,
            @PathVariable String propPersId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getPersonnel()");
        Personnel personnel = dataService.getPersonnel(propPrepId, propRevId, propPersId);
        return new EmberModel.Builder<>("personnel", personnel).build();
    }

    @ApiOperation(value = "Get a list of personnel objects", notes = "This API retrieves a list of personnel objects from the PSM database", response = Personnel.class)
    @RequestMapping(path = "/proposal/personnel/getPersonnels/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getPersonnels(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getPersonnels()");
        List<Personnel> personnelsList = dataService.getPersonnels(propPrepId, propRevId);
        return new EmberModel.Builder<>("personnel", personnelsList).build();
    }

    @ApiOperation(value = "Save a personnel object", notes = "This API saves a personnel object to the PSM database", response = Personnel.class)
    @RequestMapping(path = "/proposal/personnel/savePersonnel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel savePersonnel(@RequestBody Personnel seniorPersonnel) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.savePersonnel()");
        Personnel pers = dataService.savePersonnel(seniorPersonnel);
        return new EmberModel.Builder<>("personnel", pers).build();
    }

    @ApiOperation(value = "Delete a personnel object", notes = "This API deletes a personnel object from the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/personnel/deletePersonnel/{propPrepId}/{propRevId}/{propPersId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel deletePersonnel(@PathVariable String propPrepId, @PathVariable String propRevId,
            @PathVariable String propPersId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.deletePersonnel()");
        boolean deleteStatus = false;
        SectionResponse sectionResponse = new SectionResponse();
        deleteStatus = dataService.deletePersonnel(propPrepId, propRevId, propPersId);
        sectionResponse.setSaveStatus(deleteStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Delete Personnel and Update new Role Code ", notes = "This API is used to delete Personnel and update new role code to the PSM database.", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/personnel/replacePersonnel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel replacePersonnel(@RequestBody PersonnelParam personnelParam) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.replacePersonnel()");
        boolean status = false;
        SectionResponse sectionResponse = new SectionResponse();
        status = dataService.replacePersonnel(personnelParam);
        sectionResponse.setSaveStatus(status);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Update Proposal ", notes = "This API updates proposal to the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/updateProposal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel updateProposal(@RequestBody ProposalHeader proposalHeader) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.updateProposal()");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        saveStatus = dataService.updateProposal(proposalHeader);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Save a coversheet object", notes = "This API saves a coversheet object to the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/saveCoverSheet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel saveCoverSheet(@RequestBody CoverSheet coverSheet) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.saveCoverSheet()");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        coverSheet.setInitialCreation(false);
        saveStatus = dataService.saveCoverSheet(coverSheet);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "save awardee Organization object", notes = "This API updates  awardee organizaton and Primary Place of Performance object to the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/changeAwardeeOrganization/{propPrepId}/{propRevId}/{coverSheetId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel changeAwardeeOrganization(@PathVariable String propPrepId, @PathVariable String propRevId,
            @PathVariable String coverSheetId, @RequestBody Institution awardeeInstitution) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.changeAwardeeOrganization()");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        saveStatus = dataService.changeAwardeeOrganization(propPrepId, propRevId, coverSheetId, awardeeInstitution);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Get a project Awardee Organization Id", notes = "This API retrieves a Awardee Organization Id from the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/getAwdOrgId/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getPimaryAwardeeOrganizationId(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getPimaryAwardeeOrganizationId()");
        String awdOrgId = null;
        SectionResponse sectionResponse = new SectionResponse();
        awdOrgId = dataService.getPrimaryAwardeeOrganizationId(propPrepId, propRevId);
        sectionResponse.setAwdOrgId(awdOrgId);
        return new EmberModel.Builder<>("sectionResponse", sectionResponse).build();
    }

    @ApiOperation(value = "Checks a whether CoverSheet exists or not", notes = "This Checks a whether CoverSheet exists or not from the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/isCVExists/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel isCoverSheetExists(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.isCoverSheetExists()");
        boolean status = false;
        SectionResponse sectionResponse = new SectionResponse();
        status = dataService.isCoverSheetExists(propPrepId, propRevId);
        sectionResponse.setIsCoverSheetExists(status);
        return new EmberModel.Builder<>("sectionResponse", sectionResponse).build();
    }

    @ApiOperation(value = "Get a Proposal header Information ", notes = "This API retrieves a Proposal Header  Informatioin from the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/getProposalHeader/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getProposalHeader(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getProposalHeader()");
        ProposalHeader proposalHeader = dataService.getProposalHeader(propPrepId, propRevId);
        return new EmberModel.Builder<>("proposalHeader", proposalHeader).build();
    }

    @ApiOperation(value = "delete proposal COA object", notes = "This API deletes proposal COA data", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/coa/{revId}/{persId}/proposalCOA/{nsfId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel deleteProposalCOA(@PathVariable Long revId, @PathVariable Long persId, @PathVariable String nsfId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.deleteProposalCOA()");
        SectionResponse sectionResponse = new SectionResponse();
        boolean deleteStatus = false;
        deleteStatus = dataService.deleteProposalCOA(revId, persId, nsfId);
        sectionResponse.setSaveStatus(deleteStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "save proposal COA object", notes = "This API updates proposal COA data", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/coa/updateProposalCOA", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel updateProposalCOA(@RequestBody COA coa) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.updateProposalCOA()");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        saveStatus = dataService.updateProposalCOA(coa);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "get proposal COA object", notes = "This API retrieves proposal COA data", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/coa/{revId}/{persId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel getProposalCOA(@PathVariable Long revId, @PathVariable Long persId) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getProposalCOA()");
        COA coa = dataService.getProposalCOA(revId, persId);
        return new EmberModel.Builder<>(COA.getClassCamelCaseName(), coa).build();
    }

    @ApiOperation(value = "Save a proposal Access Status", notes = "This API saves a given proposal Access Status to the PSM database", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/propAccess", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel setProposalAccess(@RequestBody ProposalPackage proposalPackage) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.setProposalAccess()");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        saveStatus = dataService.setProposalAccess(proposalPackage);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Gets a proposal access status", notes = "This API gets a given proposal access status from PSM database", response = ProposalPackage.class)
    @RequestMapping(path = "/proposal/propAccess/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel getProposalAccess(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getProposalAccess()");
        ProposalPackage propRes = dataService.getProposalAccess(propPrepId, propRevId);
        return new EmberModel.Builder<>("proposalPackage", propRes).build();
    }

    @ApiOperation(value = "get proposals", notes = "This API retrieves proposals based on various parameters", response = Proposal.class, responseContainer = "List")
    @RequestMapping(path = "/proposal/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel getProposals(@RequestBody List<ProposalQueryParams> params) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getProposals");
        List<Proposal> props = dataService.getProposals(params);
        return new EmberModel.Builder<>(Proposal.class, props).build();
    }

    @ApiOperation(value = "Creates a revision of a submitted proposal", notes = "This API creates a revision of a submitted proposal", response = Proposal.class, responseContainer = "List")
    @RequestMapping(path = "/proposal/revision/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel createProposalRevision(@RequestBody ProposalCopy revision) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.createProposalRevision");
        ProposalPackage pkg = dataService.createProposalRevision(revision);
        return new EmberModel.Builder<>(ProposalPackage.getClassCamelCaseName(), pkg).build();
    }

    @ApiOperation(value = "Submit a Proposal", notes = "This API submits a proposal", response = SectionResponse.class)
    @RequestMapping(path = "/proposal/submit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel submitProposal(@RequestBody ProposalElectronicSign proposalElectronicSign)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.SubmitProposal" + proposalElectronicSign.toString());
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        saveStatus = dataService.submitProposal(proposalElectronicSign);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "get AOR Electronic Signature Information ", notes = "This API retrieves AOR electronic signature information from database", response = ProposalElectronicSign.class)
    @RequestMapping(path = "/proposal/aorSignature/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel getAORSignature(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getAORSignature");
        ProposalElectronicSign proposalElectronicSign = dataService.getAORSignature(propPrepId, propRevId);
        return new EmberModel.Builder<>("proposalElectronicSign", proposalElectronicSign).build();
    }

    @ApiOperation(value = "get Electronic Signature Certificatoin Text ", notes = "This API retrieves electronic signature certificatoin text from database", response = ElectronicCertificationText.class)
    @RequestMapping(path = "/proposal/elecSignCertText/{electronicCertTypeCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel getElectronicSignCertificationText(@PathVariable String electronicCertTypeCode)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getElectronicSignCertificationText");
        ElectronicCertificationText electronicCertificationText = dataService
                .getCertificationText(electronicCertTypeCode);
        return new EmberModel.Builder<>("electronicCertificationText", electronicCertificationText).build();
    }

    @ApiOperation(value = "update Proposal Static Path Url ", notes = "This API updates proposal summart file path and nsfPropId on proposal")
    @RequestMapping(path = "/proposal/proposalStaticPathUrl/{propPrepId}/{propRevId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel updateProposalStaticPathUrl(@PathVariable String propPrepId, @PathVariable String propRevId,
            @RequestBody ProposalCompleteTransfer proposalCompleteTransfer) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.updateProposalStaticPathUrl");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        saveStatus = dataService.updateProposalStaticPathUrl(propPrepId, propRevId, proposalCompleteTransfer);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "update NSFPropId ", notes = "This API endpoint updates nsfPropId on proposal")
    @RequestMapping(path = "/proposal/nsfPropId/{propPrepId}/{propRevId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel updateNsfPropId(@PathVariable String propPrepId, @PathVariable String propRevId,
            @RequestBody ProposalCompleteTransfer proposalCompleteTransfer) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.updateNsfPropId");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        saveStatus = dataService.updateNsfPropId(propPrepId, propRevId, proposalCompleteTransfer);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "update revision create date ", notes = "This API endpoint updates the revision create date")
    @RequestMapping(path = "/proposal/createdate/{propPrepId}/{propRevId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel updateProposalRevisionCreateDate(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.updateProposalRevisionCreateDate");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        saveStatus = dataService.updateProposalRevisionCreateDate(propPrepId, propRevId);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

    @ApiOperation(value = "Get Other Proposal Revision object", notes = "This API retrieves a proposal revision  object from the PSM database", response = ProposalRevision.class)
    @RequestMapping(path = "/proposal/proposalRevision/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getProposalRevision(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getProposalRevision()");
        ProposalRevision proposalRevision = dataService.getProposalRevision(propPrepId, propRevId);
        return new EmberModel.Builder<>("proposalRevision", proposalRevision).build();
    }

    @ApiOperation(value = "Get Submitted Budget Revisions (B01's)", notes = "This API retrieves a submitted budget revisions(B01's)", response = ProposalRevision.class)
    @RequestMapping(path = "/proposal/budgetRevisions/{propPrepId}/{propRevId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getBudgetRevisions(@PathVariable String propPrepId, @PathVariable String propRevId)
            throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.getProposalRevision()");
        BudgetRevision budgetRevision = dataService.getBudgetRevisions(propPrepId, propRevId);
        return new EmberModel.Builder<>("budgetRevision", budgetRevision).build();
    }

    @ApiOperation(value = "update proposal status ", notes = "This API endpoint updates proposal status in proposal revision")
    @RequestMapping(path = "/proposal/updateProposalRevisionStatus/{propRevId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EmberModel updateProposalRevisionStatus(@PathVariable String propRevId,
            @RequestBody ProposalPackage proposalPackage) throws CommonUtilException {
        LOGGER.debug("ProposalDataServiceController.updateProposalStatus");
        SectionResponse sectionResponse = new SectionResponse();
        boolean saveStatus = false;
        saveStatus = dataService.updateProposalRevisionStatus(propRevId, proposalPackage);
        sectionResponse.setSaveStatus(saveStatus);
        return new EmberModel.Builder<>(SectionResponse.getClassCamelCaseName(), sectionResponse).build();
    }

}