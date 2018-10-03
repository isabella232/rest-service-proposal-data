package gov.nsf.psm.proposaldata.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import gov.nsf.psm.foundation.controller.PsmBaseController;
import gov.nsf.psm.foundation.ember.model.EmberModel;
import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.FundingOpportunity;
import gov.nsf.psm.foundation.model.lookup.AdviseeTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CoEditorTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CollaborativeTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.CollaboratorTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.DeadlineTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.InstitutionRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.LoginUserRoleType;
import gov.nsf.psm.foundation.model.lookup.OtherPersonnelRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.ProposalStatusTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.ProposalTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.RelationshipTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.SeniorPersonRoleTypeLookUp;
import gov.nsf.psm.foundation.model.lookup.SubmissionTypeLookUp;
import gov.nsf.psm.foundation.model.proposal.ProposalPermission;
import gov.nsf.psm.proposaldata.service.ProposalDataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/api/v1")
@ApiResponses(value = { @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error") })
public class ProposalDataLookupController extends PsmBaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProposalDataLookupController.class);

	@Autowired
	ProposalDataService dataService;

	@ApiOperation(value = "Get a list of other personnel role types", notes = "This API returns a list of all other role personnel types", response = OtherPersonnelRoleTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/otherPersonnelTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getOtherPersonnelRoleTypes()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getOtherPersonnelRoleTypes()");
		List<OtherPersonnelRoleTypeLookUp> othPersTypeList = dataService.getOtherPersonnelRoleTypes();
		return new EmberModel.Builder<>(OtherPersonnelRoleTypeLookUp.class, othPersTypeList).build();
	}

	@ApiOperation(value = "Get a list of senior personnel role types", notes = "This API returns a list of all senior personnel role types", response = SeniorPersonRoleTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/seniorPersonnelTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getSeniorPersonRoleTypes()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getSeniorPersonRoleTypes()");
		List<SeniorPersonRoleTypeLookUp> snrPersTypeList = dataService.getSeniorPersonnelRoleTypes();
		return new EmberModel.Builder<>(SeniorPersonRoleTypeLookUp.class, snrPersTypeList).build();
	}

	@ApiOperation(value = "Get a list of institution role types", notes = "This API returns a list of all institution role types", response = InstitutionRoleTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/institutionTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getInstitutionRoleTypes() throws CommonUtilException {
		LOGGER.debug("ProposalDataLookupController.getInstitutionRoleTypes()");
		List<InstitutionRoleTypeLookUp> instTypeList = dataService.getInstitutionRoleTypes();
		return new EmberModel.Builder<>(InstitutionRoleTypeLookUp.class, instTypeList).build();
	}

	@ApiOperation(value = "Get a list of submission types", notes = "This API returns a list of all submission types", response = SubmissionTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/submTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getSubmissionTypes()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getSubmissionTypes()");
		List<SubmissionTypeLookUp> submTypeList = dataService.getAllSubmissionTypes();
		return new EmberModel.Builder<>(SubmissionTypeLookUp.class, submTypeList).build();
	}

	@ApiOperation(value = "Get a list of proposal types", notes = "This API returns a list of all proposal types", response = ProposalTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/propTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getProposalTypes() throws CommonUtilException {
		LOGGER.debug("ProposalDataLookupController.getProposalTypes()");
		List<ProposalTypeLookUp> propTypeList = dataService.getAllProposalTypes();
		return new EmberModel.Builder<>(ProposalTypeLookUp.class, propTypeList).build();
	}

	@ApiOperation(value = "Get a list of proposal status types", notes = "This API returns a list of all proposal status types", response = ProposalTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/propStatusTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getProposalStatusTypes()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getProposalStatusTypes()");
		List<ProposalStatusTypeLookUp> propSttsTypeList = dataService.getAllProposalStatusTypes();
		return new EmberModel.Builder<>(ProposalStatusTypeLookUp.class, propSttsTypeList).build();
	}

	@ApiOperation(value = "Get a list of collaboration types", notes = "This API returns a list of all collaboration types", response = CollaborativeTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/collabTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getCollaborativeTypes()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getCollaborativeTypes()");
		List<CollaborativeTypeLookUp> collabTypeList = dataService.getAllCollaborativeTypes();
		return new EmberModel.Builder<>(CollaborativeTypeLookUp.class, collabTypeList).build();
	}

	@ApiOperation(value = "Get a list of collaborator types", notes = "This API returns a list of all collaborator types", response = CollaboratorTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/collaboratorTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getCollaboratorTypes()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getCollaboratorTypes()");
		List<CollaboratorTypeLookUp> collaboratorTypeList = dataService.getAllCollaboratorTypes();
		return new EmberModel.Builder<>(CollaboratorTypeLookUp.class, collaboratorTypeList).build();
	}

	@ApiOperation(value = "Get a list of login user role types", notes = "This API returns a list of all login user role types", response = LoginUserRoleType.class, responseContainer = "List")
	@RequestMapping(path = "/loginUserRoleTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getLoginUserRoleTypes()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getLoginUserRoleTypes()");
		List<LoginUserRoleType> loginUserRoleTypeList = dataService.getAllLoginUserRoleTypes();
		return new EmberModel.Builder<>(LoginUserRoleType.class, loginUserRoleTypeList).build();
	}

	@RequestMapping(path = "/loginUserPermissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getLoginUserPermissions(@RequestParam("propStatusCode") String propStatusCode,
	        @RequestParam("userRoleCodes") String[] userRoleCodes)  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getLoginUserPermissions()");
		List<ProposalPermission> loginUserPermissionList = dataService.getLoginUserRolePermissions(propStatusCode,
		        userRoleCodes);
		return new EmberModel.Builder<>(ProposalPermission.class, loginUserPermissionList).build();
	}

	@ApiOperation(value = "Get a list of advisee types", notes = "This API returns a list of all advisee types", response = AdviseeTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/adviseeTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getAdviseeTypes()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getAdviseeTypes()");
		List<AdviseeTypeLookUp> adviseeTypeList = dataService.getAllAdviseeTypes();
		return new EmberModel.Builder<>(AdviseeTypeLookUp.class, adviseeTypeList).build();
	}

	@ApiOperation(value = "Get a list of coeditor types", notes = "This API returns a list of all coeditor types", response = CoEditorTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/coeditorTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getCoEditorTypes()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getCoEditorTypes()");
		List<CoEditorTypeLookUp> coeditorTypeList = dataService.getAllCoEditorTypes();
		return new EmberModel.Builder<>(CoEditorTypeLookUp.class, coeditorTypeList).build();
	}

	@ApiOperation(value = "Get a list of relationship types", notes = "This API returns a list of all relationship types", response = RelationshipTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/relationshipTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getRelationshipTypes()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getRelationshipTypes()");
		List<RelationshipTypeLookUp> relationshipTypeList = dataService.getAllRelationshipTypes();
		return new EmberModel.Builder<>(RelationshipTypeLookUp.class, relationshipTypeList).build();
	}

	@ApiOperation(value = "Get a list of due date types", notes = "This API returns a list of all due date types", response = DeadlineTypeLookUp.class, responseContainer = "List")
	@RequestMapping(path = "/deadlineTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getDueDateTypes()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getDueDateTypes()");
		List<DeadlineTypeLookUp> deadlineTypeList = dataService.getDueDateTypes();
		return new EmberModel.Builder<>(DeadlineTypeLookUp.class, deadlineTypeList).build();
	}
	
	@ApiOperation(value = "Get funding opportunity exclusion list", notes = "This API returns a list of funding opportunities for exclusion", response = FundingOpportunity.class, responseContainer = "List")
	@RequestMapping(path = "/fundingOppExclusionList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getFundingOppExclusionList()  throws CommonUtilException{
		LOGGER.debug("ProposalDataLookupController.getFundingOppExclusionList()");
		List<FundingOpportunity> fundingOpportunityExclusionList = dataService.getFundingOppExclusionList();
		return new EmberModel.Builder<>(FundingOpportunity.class, fundingOpportunityExclusionList).build();
	}
	
}
