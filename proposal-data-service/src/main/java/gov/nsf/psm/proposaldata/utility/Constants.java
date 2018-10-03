package gov.nsf.psm.proposaldata.utility;

import java.math.BigDecimal;

public class Constants {

    public static final String PDSVC_USER = "pdsvc";
    public static final String PDSVC_PGM = "pdsvc";

    public static final String GPG_VERSION = "18-1";
    public static final String COUNTRY_US = "US";
    public static final String PRIMARY_INSTITUTION_TYPE_CODE = "01";

    public static final String REVISION_ID = " Revison Id : ";
    public static final String UNABLE_SAVE_SECTION = "Unable to Save the Section Details";
    public static final String UNABLE_SAVE_CV = "Unable to Save CoverSheet : ";
    public static final String UNABLE_UPDATE_CV = "Unable to Update CoverSheet : ";
    public static final String UNABLE_SAVE_IA = "Problem while saving International Activities : ";
    public static final String UNABLE_SAVE_FA = "Problem while saving Other Federal Agencies : ";
    public static final String UNABLE_SAVE_PPOP = "Problem while saving Primary Place of Performance : ";
    public static final String PRMY_NOT_AVIL = "Primary Awardee Organization is not available :";
    public static final String CV_NOT_AVIL = "CoverSheet doesn't exist : ";
    public static final String UNABLE_UPDT_PPOP = "Problem while Updating Primary Place of Performance : ";
    public static final String GET_ALL_SUBMISSION_TYPE_ERROR = "Proposal Data Service encountered an error while trying to get submission type look up values ";
    public static final String GET_ALL_PROPOSAL_TYPE_ERROR = "Proposal Data Service encountered an error while trying to get proposal type look up values ";
    public static final String GET_ALL_COLLABORATIVE_TYPE_ERROR = "Proposal Data Service encountered an error while trying to get collaborative type look up values ";
    public static final String GET_ALL_COLLABORATOR_TYPE_ERROR = "Proposal Data Service encountered an error while trying to get collaborator type look up values ";
    public static final String GET_ALL_ADVISEE_TYPE_ERROR = "Proposal Data Service encountered an error while trying to get advisee type look up values ";
    public static final String GET_ALL_COEDITOR_TYPE_ERROR = "Proposal Data Service encountered an error while trying to get CO editor type look up values ";
    public static final String GET_ALL_RELATIONSHIP_TYPE_ERROR = "Proposal Data Service encountered an error while trying to get relatioinship  type look up values ";
    public static final String GET_ALL_OTHER_PERSONNEL_ROLE_TYPE_ERROR = "Proposal Data Service encountered an error while trying to get other personnel role type look up values ";
    public static final String GET_ALL_SENIOR_PERSONNEL_ROLE_TYPE_ERROR = "Proposal Data Service encountered an error while trying to get senior personnel role type look up values ";
    public static final String GET_PROPOSAL_STATUS_TYPE_ERROR = "Proposal Data Service encountered an error while trying to get proposal status look up values ";
    public static final String GET_PROPOSAL_PREP_ERROR = "Proposal Data Service encountered an error while trying to get proposal preparation data ";
    public static final String GET_PROPOSAL_REV_ID_ERROR = "Proposal Data Service encountered an error while trying to get proposal revision id  ";
    public static final String GET_PROPSAL_INST_RECORD_ID_ERROR = "Proposal Data Service encountered an error while trying to get proposal institution record id  ";
    public static final String GET_PROJECT_SUMMARY_ERROR = "Proposal Data Service encountered an error while trying to get proposal preparation data ";
    public static final String GET_BUDGET_IMPACT_ERROR = "Proposal Data Service encountered an error while trying to get proposal budget Imapact statement";
    public static final String GET_INSTITUTION_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to get proposal institution budget ";
    public static final String GET_PROPOSAL_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to get proposal budget ";
    public static final String GET_REFERENCE_CITED_ERROR = "Proposal Data Service encountered an error while trying to get reference cited ";
    public static final String GET_FAC_EQUIPMENT_ERROR = "Proposal Data Service encountered an error while trying to get facilities and equipment ";
    public static final String GET_PERSONNEL_ERROR = "Proposal Data Service encountered an error while trying to get personnel ";
    public static final String GET_PERSONNELS_ERROR = "Proposal Data Service encountered an error while trying to get personnels ";
    public static final String GET_PROJECT_DESC_ERROR = "Proposal Data Service encountered an error while trying to get project description ";
    public static final String GET_BUDGET_JUST_ERROR = "Proposal Data Service encountered an error while trying to get budget justification ";
    public static final String GET_BIO_SKETCH_ERROR = "Proposal Data Service encountered an error while trying to get biosketche ";
    public static final String GET_OSD_ERROR = "Proposal Data Service encountered an error while trying to get Other Supplementary Documents ";
    public static final String GET_BIO_SKETCHS_ERROR = "Proposal Data Service encountered an error while trying to get biosketches ";
    public static final String GET_CURRENT_PENDING_SUPPORT_PERSONNEL_ERROR = "Proposal Data Service encountered an error while trying to get current and pending support for a personnel ";
    public static final String GET_CURRENT_PENDING_SUPPORT_FOR_PROPOSAL_ERROR = "Proposal Data Service encountered an error while trying to get current and pending support documents for a proposal ";
    public static final String GET_SUGGESTED_REVIEWERS_ERROR = "Proposal Data Service encountered an error while trying to get suggested reviewers ";
    public static final String GET_OTHER_PERSONNEL_BIO_INFO_ERROR = "Proposal Data Service encountered an error while trying to get other personnel biographical information ";
    public static final String GET_REVIEWERS_NOT_INCLUDE_ERROR = "Proposal Data Service encountered an error while trying to get reviewers not include details ";
    public static final String GET_DATA_MGNT_PALAN_ERROR = "Proposal Data Service encountered an error while trying to get data management plan ";
    public static final String GET_POSTDOCTORAL_MENT_PLAN_ERROR = "Proposal Data Service encountered an error while trying to get postdoctoral mentoring plan ";
    public static final String GET_PROPOSAL_COA_ERROR = "Proposal Data Service encountered an error while trying to get proposal coa";
    public static final String GET_LATEST_PROPOSAL_COA_ERROR = "Proposal Data Service encountered an error while trying to get latest proposal coa";
    public static final String GET_PROPOSAL_COA_S_ERROR = "Proposal Data Service encountered an error while trying to get list of proposal coa";
    public static final String GET_COVERSHEET_ERROR = "Proposal Data Service encountered an error while trying to get coversheet ";
    public static final String GET_PI_COPI_FOR_PROPOSAL_ERROR = "Proposal Data Service encountered an error while trying to get pi and copi information for a proposal ";
    public static final String GET_PRIMARY_AWARDEE_ORG_ID_ERROR = "Proposal Data Service encountered an error while trying to get primary awardee organization id ";
    public static final String GET_PROPOSAL_HEADER_ERROR = "Proposal Data Service encountered an error while trying to get proposal header information ";
    public static final String GET_PROPOSAL_ACCESS_ERROR = "Proposal Data Service encountered an error while trying to get proposal access information ";
    public static final String GET_LATEST_BIO_SKETCH_ERROR = "Proposal Data Service encountered an error while trying to get latest bio sketch detals ";
    public static final String GET_LATEST_CURR_SUPPORT_ERROR = "Proposal Data Service encountered an error while trying to get latest current and pending support ";
    public static final String GET_DUE_DATE_TYPES_ERROR = "Proposal Data Service encountered an error while trying to get due date types ";
    public static final String GET_PROPOSAL_SEARCH_ERROR = "Proposal Data Service encountered an error while trying to get proposal search results ";
    public static final String GET_LIST_OF_BIOSKETCHES_FOR_PROPOSAL_ERROR = "Proposal Data Service encountered an error while trying to get list of bio sketches of a proposal ";
    public static final String GET_LIST_OF_CURRENT_PENDING_SUPPORT_FOR_PROPOSAL_ERROR = "Proposal Data Service encountered an error while trying to get list of current and pending support for a proposal ";
    public static final String GET_LIST_OF_PROPOSAL_BY_NSFID_ERROR = "Proposal Data Service encountered an error while trying to get list of propsals by NSF Id ";
    public static final String GET_SUBMITTED_PROPOSAL_ERROR = "Proposal Data Service encountered an error while trying to get list of propsals by NSF Id ";
    public static final String GET_PROPOSAL_WARNING_MESSAGES_ERROR = "Proposal Data Service encountered an error while trying to get proposal warning messages ";
    public static final String GET_PROPOSAL_TRANSFER_PACKAGE = "Proposal Data Service encountered an error while trying to prepare a package for proposal transfer ";
    public static final String GET_PROPOSAL_ELECTRONIC_SIGNATURE_PACKAGE = "Proposal Data Service encountered an error while trying to get electronic signature information  ";
    public static final String GET_PROPOSAL_SECTION_STATUSES_ERROR = "Proposal Data Service encountered an error while trying to get section statuses for a proposal  ";
    public static final String GET_PROPOSAL_REVISION_ERROR = "Proposal Data Service encountered an error while trying to get proposal revision information  ";
    public static final String GET_FUNDING_OPP_EXCLUSION_ERROR = "Proposal Data Service encountered an error while trying to get funding opportunity exclusion list ";
    public static final String SAVE_PROPOSAL_ERROR = "Proposal Data Service encountered an error while trying to save proposal ";
    public static final String SAVE_PROPOSAL_ERROR_MESSAGES_ERROR = "Proposal Data Service encountered an error while trying to save proposal error messages ";
    public static final String SAVE_SET_PROPOSAL_ACCESS_ERROR = "Proposal Data Service encountered an error while trying to set proposal access ";
    public static final String SAVE_COVERSHEET_ERROR = "Proposal Data Service encountered an error while trying to save coversheet ";
    public static final String SAVE_PERSONNEL_ERROR = "Proposal Data Service encountered an error while trying to save personnel ";
    public static final String SAVE_PRORJECT_SUMMARY_ERROR = "Proposal Data Service encountered an error while trying to save project summary ";
    public static final String SAVE_FAC_EQUP_ERROR = "Proposal Data Service encountered an error while trying to save facilities and equipment ";
    public static final String SAVE_PROJECT_DESC_ERROR = "Proposal Data Service encountered an error while trying to save project description ";
    public static final String SAVE_SUGGESTED_REVIEWERS_ERROR = "Proposal Data Service encountered an error while trying to save suggested reviewers ";
    public static final String SAVE_BUDGET_JUST_ERROR = "Proposal Data Service encountered an error while trying to save budget justification ";
    public static final String SAVE_BIO_SKETCH_ERROR = "Proposal Data Service encountered an error while trying to save bio sketches";
    public static final String SAVE_CURRENT_PENDING_SUPPORT_ERROR = "Proposal Data Service encountered an error while trying to save current and pending support";
    public static final String SAVE_REFERENCE_CITED_ERROR = "Proposal Data Service encountered an error while trying to save reference cited ";
    public static final String SAVE_INSTITUTION_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to save institution budget ";
    public static final String SAVE_UPLOADABLE_PROPOSAL_SECTION_ERROR = "Proposal Data Service encountered an error while trying to upload  proposal section ";
    public static final String SAVE_UPLOADABLE_SENIOR_PERSONNEL_DOC_ERROR = "Proposal Data Service encountered an error while trying to save uploadable senior personnel documents";
    public static final String SAVE_SENIOR_PERSONNEL_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to save senior personnel budget cost ";
    public static final String SAVE_OTHER_PERSONNEL_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to save other personnel budget cost ";
    public static final String SAVE_FRINGE_BENEFIT_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to save fringe benefit cost ";
    public static final String SAVE_EQUIPMENT_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to save equipment budget cost ";
    public static final String SAVE_TRAVEL_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to save travel budget cost ";
    public static final String SAVE_PARTICIPANT_SUPPORT_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to save participant support budget cost ";
    public static final String SAVE_OTHER_DIRECT_COST_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to save other direct cost budget ";
    public static final String SAVE_INDIRECT_DIRECT_COST_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to save indirect direct cost budget ";
    public static final String SAVE_INSTITUTION_BUDGET_MESSAGES_ERROR = "Proposal Data Service encountered an error while trying to save institution budget messages ";
    public static final String UPDATE_PROPOSAL_TITLE_OR_DUEDATE_ERROR = "Proposal Data Service encountered an error while trying to update the proposal title or due date ";
    public static final String UPDATE_PROPOSAL_STATIC_PATH_OR_NSF_ID_ERROR = "Proposal Data Service encountered an error while trying to update the proposal static pdf path or Nsf proposal id ";
    public static final String UPDATE_PROPOSAL_COA_ERROR = "Proposal Data Service encountered an error while trying to update the proposal coa ";
    public static final String UPDATE_AWARDEE_ORGANIZATION_ERROR = "Proposal Data Service encountered an error while trying to update the awardee organization ";
    public static final String SAVE_ELECTRONIC_SIGNATURE_ERROR = "Proposal Data Service encountered an error while trying to submit the proposal to NSF ";
    public static final String SAVE_PROPOSAL_INSTITUTION_SNAPSHOT_ADDRESS = "Proposal Data Service encountered an error while trying to save proposal institution address";
    public static final String SAVE_PROPOSAL_UPDATE_JUSTIFICATION_ERROR = "Proposal Data Service encountered an error while trying to save proposal update justification ";
    public static final String SAVE_PROPOSAL_ELECTRONIC_SIGN_LOG_ERROR = "Proposal Data Service encountered an error while saving the data to Proposal Electronic Sign log table ";
    public static final String UPDATE_PROPOSAL_SUBMISSION_STATUS_ERROR = "Proposal Data Service encountered an error while trying to update the proposal status ";
    public static final String UPDATE_PROPOSAL_SUBMISSION_DATE_ERROR = "Proposal Data Service encountered an error while trying to update the proposal submission date ";
    public static final String DELETE_REFERENCE_CITED_ERROR = "Proposal Data Service encountered an error while trying to delete reference cited ";
    public static final String DELETE_UPLOADABLE_PROPOSAL_SECTION_ERROR = "Proposal Data Service encountered an error while trying to delete uploadable proposal section";
    public static final String DELETE_PROJECT_SUMMARY_ERROR = "Proposal Data Service encountered an error while trying to delete project summary cited ";
    public static final String DELETE_SUGGESTED_REVIEWERS_ERROR = "Proposal Data Service encountered an error while trying to delete suggested reviewers ";
    public static final String DELETE_FAC_EQUP_ERROR = "Proposal Data Service encountered an error while trying to delete facilities and equipment ";
    public static final String DELETE_PROJECT_DESC_ERROR = "Proposal Data Service encountered an error while trying to delete project description ";
    public static final String DELETE_BUDGET_JUST_ERROR = "Proposal Data Service encountered an error while trying to delete budget justification ";
    public static final String DELETE_BIOSKETCHES_ERROR = "Proposal Data Service encountered an error while trying to delete bio sketches ";
    public static final String DELETE_CURRENT_AND_PENDING_SUPPORT_ERROR = "Proposal Data Service encountered an error while trying to delete current and pending support ";
    public static final String DELETE_PERSONNEL_ERROR = "Proposal Data Service encountered an error while trying to delete personnel ";
    public static final String DELETE_SENIOR_PERSONNEL_BUDGET_ERROR = "Proposal Data Service encountered an error while trying to delete senior personnel budget cost ";
    public static final String DELETE__UPLOADABLE_SENIOR_PERSONNEL_DOC_ERROR = "Proposal Data Service encountered an error while trying to delete uploadable senior personnel documents";
    public static final String DELETE_PERSONNEL_UPDATED_ROLE_ERROR = "Proposal Data Service encountered an error while trying to delete personnel and update the new role ";
    public static final String DELETE_PROPOSAL_COA_ERROR = "Proposal Data Service encountered an error while trying to delete proposal coa ";
    public static final String DELETE_REMOVE_PROPOSAL_COA_ERROR = "Proposal Data Service encountered an error while trying to remove proposal coa ";
    public static final String SAVE_PROPOSAL_PERSONNEL_DEMOGRAPHIC_ERROR = "Proposal Data Service encountered an error while trying to save proposal personnel demographic data";
    public static final String SAVE_PROPOSAL_STATUS_HISTORY_LOG_ERROR = "Proposal Data Service encountered an error while trying to save proposal status history log";

    public static final String CHECK_IS_PERSONNEL_BUDGET_EXISTS_ERROR = "Proposal Data Service encountered an error while trying to check personnel budget  ";
    public static final String CHECK_IS_COVERSHEET_EXISTS_ERROR = "Proposal Data Service encountered an error while trying to check to see if coversheet exists   ";
    public static final String CHECK_PERSONNEL_REVN_UPDATE = "Proposal Data Service encountered an error while trying  to check to if personnel updates are made to a revision";

    public static final BigDecimal DEFAULT_BIG_DECIMAL_VALUE = BigDecimal.ZERO;

    public static final Long DEFAULT_SECTION_MISSING_DOCUMENT_COUNT = 1L;
    public static final Long DEFAULT_SECTION_NO_MISSING_DOCUMENT_COUNT = 0L;
    public static final String ZERO = "0";

    public static final String PPOP_ORG_NAME_UNALLOWABLE_CHARS = "*?[\\]^`{|}~€‚ƒ„…†‡ˆ‰™Š‹ŒŽ‘’“”•–—š›œžŸ¡¢£¤¥¦§¨©ª«¬®¯°±²³´¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿŽ*?[\\]^`{|}~€‚ƒ„…†‡‡";
    public static final String PPOP_STREET_ADD_UNALLOWABLE_CHARS = "*?[\\]^`{|}~€‚ƒ„…†‡ˆ‰™Š‹ŒŽ‘’“”•–—š›œžŸ¡¢£¤¥¦§¨©ª«¬®¯°±²³´¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿŽ*?[\\]^`{|}~€‚ƒ„…†‡‡";
    public static final String PPOP_STREET_ADD_2_UNALLOWABLE_CHARS = "*?[\\]^`{|}~€‚ƒ„…†‡ˆ‰™Š‹ŒŽ‘’“”•–—š›œžŸ¡¢£¤¥¦§¨©ª«¬®¯°±²³´¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿŽ*?[\\]^`{|}~€‚ƒ„…†‡‡";
    public static final String PPOP_CITY_NAME_UNALLOWABLE_CHARS = "*?[\\]^`{|}~€‚ƒ„…†‡ˆ‰™Š‹ŒŽ‘’“”•–—š›œžŸ¡¢£¤¥¦§¨©ª«¬®¯°±²³´¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿŽ*?[\\]^`{|}~€‚ƒ„…†‡‡";
    public static final String PPOP_POSTAL_CODE_UNALLOWABLE_CHARS = "*?[\\]^`{|}~€‚ƒ„…†‡ˆ‰™Š‹ŒŽ‘’“”•–—š›œžŸ¡¢£¤¥¦§¨©ª«¬®¯°±²³´¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿŽ!#&(),./:;-%@$+<=>_";

    /**
     * 
     */
    private Constants() {
        /**
         * default constructor
         */
    }

}
