/**
 * 
 */
package gov.nsf.psm.proposaldata.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.DataManagementPlan;
import gov.nsf.psm.foundation.model.OtherSuppDocs;
import gov.nsf.psm.foundation.model.OthrPersBioInfo;
import gov.nsf.psm.foundation.model.PostDocMentPlan;
import gov.nsf.psm.foundation.model.ReviewersNotInclude;
import gov.nsf.psm.foundation.model.UploadableProposalSection;
import gov.nsf.psm.proposaldata.entity.BudgetImpactEntity;
import gov.nsf.psm.proposaldata.entity.DataManagementPlanEntity;
import gov.nsf.psm.proposaldata.entity.OtherSuppDocsEntity;
import gov.nsf.psm.proposaldata.entity.OthrPersBioInfoEntity;
import gov.nsf.psm.proposaldata.entity.PostDocMentPlanEntity;
import gov.nsf.psm.proposaldata.entity.ProposalPrepRevision;
import gov.nsf.psm.proposaldata.entity.ReviewersNotIncludeEntity;
import gov.nsf.psm.proposaldata.repository.BudgetImpactRepository;
import gov.nsf.psm.proposaldata.repository.DataManagementPlanRepository;
import gov.nsf.psm.proposaldata.repository.OtherSuppDocsRepository;
import gov.nsf.psm.proposaldata.repository.OthrPersBioInfoRepository;
import gov.nsf.psm.proposaldata.repository.PostDocMentPlanRepository;
import gov.nsf.psm.proposaldata.repository.ProposalPrepRevisionRepository;
import gov.nsf.psm.proposaldata.repository.ReviewersNotIncludeRepository;

/**
 * @author pkatrapa
 *
 */
public class UploadUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadUtility.class);

	/**
	 * default Constructor
	 */
	private UploadUtility() {

	}

    /**
     * This method is used to Save Uploaded Other Personal Biographical
     * Information Section.
     * 
     * @param uploadableProposalSection
     * @param propRevId
     * @param section
     * @return
     * @throws CommonUtilException
     */
	public static boolean saveOthrPersBioInfo(UploadableProposalSection uploadableProposalSection, Long propRevId,
			OthrPersBioInfoRepository othPersRep, ProposalPrepRevisionRepository propPrepRevRep)
			throws CommonUtilException {
		LOGGER.debug("${artificatId}ServiceImpl.saveOthrPersBioInfo()");
		try {
			OthrPersBioInfoEntity entity = ConverterUtility.getOthrPersBioInfoEntity(uploadableProposalSection,
					propRevId);
			othPersRep.save(entity);
			if (entity != null) {
				ProposalDataUtility.updateRevisionLastUpdateTS(entity.getLastUpdtTmsp(), propRevId, propPrepRevRep);
			}
		} catch (Exception e) {
			throw new CommonUtilException(Constants.UNABLE_SAVE_SECTION, e);
		}
		return true;
	}

    /**
     * This method is used to pull the Uploaded Other Personal Biographical
     * Information Section.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public static OthrPersBioInfo getOthrPersBioInfo(String propPrepId, String propRevId,
            OthrPersBioInfoRepository othPersRep) throws CommonUtilException {
        OthrPersBioInfoEntity othPersBioEntity = othPersRep.findOne(Long.valueOf(propRevId));
        if (othPersBioEntity == null) {
            LOGGER.debug("Other Personal Biographical Information is not exists for this proposal [propPrepId: "
                    + propPrepId + Constants.REVISION_ID + propRevId + "]");
            return new OthrPersBioInfo();
        }
        LOGGER.debug("Returning --> Other Personal Biographical Information by findOne() :: " + othPersBioEntity);

        return ConverterUtility.getOthrPersBioInfoDTO(othPersBioEntity);
    }

    /**
     * This method deletes/updates Other Personal Biographical Information
     * uploaded file.
     * 
     * @param propRevId
     * @throws CommonUtilException
     */
    public static void deleteOthrPersBioInfo(Long propRevId, OthrPersBioInfoRepository othPersRep, String nsfId)
            throws CommonUtilException {
        LOGGER.debug("${artificatId}ServiceImpl.deleteOthrPersBioInfo()");
        OthrPersBioInfoEntity srEntity = othPersRep.findOne(propRevId);
        srEntity.setOthrPersBioInfoFileLocation("");
        srEntity.setOthrPersBioInfoDocPageCount(0);
        srEntity.setOthrPersBioInfoDocText("");
        srEntity.setOthrPersBioInfoDocOrigFileName("");
        ConverterUtility.setAuditFields(srEntity);
        srEntity.setLastUpdtUser(nsfId);
        othPersRep.save(srEntity);
    }

    /**
     * This method is used to Save Uploaded Reviewer Not Include Section.
     * 
     * @param uploadableProposalSection
     * @param propRevId
     * @param section
     * @return
     * @throws CommonUtilException
     */
	public static boolean saveReviewersNotInclude(UploadableProposalSection uploadableProposalSection, Long propRevId,
			ReviewersNotIncludeRepository rvrsNotInclRep, ProposalPrepRevisionRepository propPrepRevRep)
			throws CommonUtilException {
		LOGGER.info("${artificatId}ServiceImpl.saveReviewersNotInclude()");
		try {
			ReviewersNotIncludeEntity srEntity = ConverterUtility
					.convertToReviewersNotIncludeEntity(uploadableProposalSection, propRevId);
			ReviewersNotIncludeEntity entity = rvrsNotInclRep.save(srEntity);
			if (entity != null) {
				ProposalDataUtility.updateRevisionLastUpdateTS(entity.getLastUpdtTmsp(), propRevId, propPrepRevRep);
			}
		} catch (Exception e) {
			throw new CommonUtilException(Constants.UNABLE_SAVE_SECTION, e);
		}
		return true;
	}

    /**
     * This method used to pull the Reviewers not Include uploaded file.
     * 
     * @param propPrepId
     * @param propRevId
     * @param rvrsNotInclRep
     * @return
     * @throws CommonUtilException
     */
    public static ReviewersNotInclude getReviewersNotInclude(String propPrepId, String propRevId,
            ReviewersNotIncludeRepository rvrsNotInclRep) throws CommonUtilException {
        ReviewersNotIncludeEntity srEntity = rvrsNotInclRep.findOne(Long.valueOf(propRevId));
        if (srEntity == null) {
            LOGGER.info("Reviewers Not Include List is not exists for this proposal [propPrepId: " + propPrepId
                    + Constants.REVISION_ID + propRevId + "]");
            return new ReviewersNotInclude();
        }
        LOGGER.info("Returning --> Reviewers Not Includes by findOne() :: " + srEntity);

        return ConverterUtility.getReviewersNotIncludeDto(srEntity);
    }

    /**
     * This method deletes/updates Reviewer Not Include uploaded file.
     * 
     * @param propRevId
     * @throws CommonUtilException
     */
    public static void deleteReviewersNotInclude(Long propRevId, ReviewersNotIncludeRepository rvrsNotInclRep,
            String nsfId) throws CommonUtilException {
        LOGGER.debug("${artificatId}ServiceImpl.deleteReviewersNotInclude()");
        ReviewersNotIncludeEntity entity = rvrsNotInclRep.findOne(propRevId);
        entity.setRevrsNotIncludeFileLocation("");
        entity.setRevrsNotIncludeDocPageCount(0);
        entity.setRevrsNotIncludeDocText("");
        entity.setRevrsNotIncludeDocOrigFileName("");
        ConverterUtility.setAuditFields(entity);
        entity.setLastUpdtUser(nsfId);
        rvrsNotInclRep.save(entity);
    }

    /**
     * This method is used to pull the Data Management Plan Section.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public static DataManagementPlan getDataManagementPlan(String propPrepId, String propRevId,
            DataManagementPlanRepository dmpRep) throws CommonUtilException {
        DataManagementPlanEntity dmpEntity = dmpRep.findOne(Long.valueOf(propRevId));
        if (dmpEntity == null) {
            LOGGER.debug("Data Management Plan section is not exists for this proposal [propPrepId: " + propPrepId
                    + Constants.REVISION_ID + propRevId + "]");
            return new DataManagementPlan();
        }
        LOGGER.debug("Returning --> Data Management Plan Section by findOne() :: " + dmpEntity);

        return ConverterUtility.getDataManagementPlanDTO(dmpEntity);
    }

    /**
     * This method is used to Save Uploaded Data Management Plan Section.
     * 
     * @param uploadableProposalSection
     * @param propRevId
     * @param section
     * @return
     * @throws CommonUtilException
     */
	public static boolean saveDataManagementPlan(UploadableProposalSection uploadableProposalSection, Long propRevId,
			DataManagementPlanRepository dmpRep, ProposalPrepRevisionRepository propPrepRevRep)
			throws CommonUtilException {
		LOGGER.info("${artificatId}ServiceImpl.saveReviewersNotInclude()");
		try {
			DataManagementPlanEntity dmpEntity = ConverterUtility
					.convertToDataManagementPlanEntity(uploadableProposalSection, propRevId);
			DataManagementPlanEntity entity = dmpRep.save(dmpEntity);
			if (entity != null) {
				ProposalDataUtility.updateRevisionLastUpdateTS(entity.getLastUpdtTmsp(), propRevId, propPrepRevRep);
			}
		} catch (Exception e) {
			throw new CommonUtilException(Constants.UNABLE_SAVE_SECTION, e);
		}
		return true;
	}

    /**
     * This method deletes/updates DataManagement Plan uploaded file.
     * 
     * @param propRevId
     * @throws CommonUtilException
     */
    public static void deleteDataManagementPlan(Long propRevId, DataManagementPlanRepository dmpRep, String nsfId)
            throws CommonUtilException {
        LOGGER.debug("${artificatId}ServiceImpl.deleteReviewersNotInclude()");
        DataManagementPlanEntity entity = dmpRep.findOne(propRevId);
        entity.setDataManagementPlanFileLocation("");
        entity.setDataManagementPlanDocPageCount(0);
        entity.setDataManagementPlanDocText("");
        entity.setDataManagementPlanDocOrigFileName("");
        ConverterUtility.setAuditFields(entity);
        entity.setLastUpdtUser(nsfId);
        dmpRep.save(entity);
    }

    /**
     * This method is used to pull the Uploaded Post Doc Mentoring Plan Section.
     * 
     * @param propPrepId
     * @param propRevId
     * @return
     * @throws CommonUtilException
     */
    public static PostDocMentPlan getPostDocMentoringPlan(String propPrepId, String propRevId,
            PostDocMentPlanRepository pdocMPRep) throws CommonUtilException {
        PostDocMentPlanEntity pdocMPEntity = pdocMPRep.findOne(Long.valueOf(propRevId));
        if (pdocMPEntity == null) {
            LOGGER.debug("Post Doc Mentoring Plan section is not exists for this proposal [propPrepId: " + propPrepId
                    + Constants.REVISION_ID + propRevId + "]");
            return new PostDocMentPlan();
        }
        LOGGER.debug("Returning --> Post Doc Mentoring Plan section by findOne() :: " + pdocMPEntity);

        return ConverterUtility.getPostDocMentoringPlanDTO(pdocMPEntity);
    }

    /**
     * This method is used to Save Uploaded Post Doc Mentoring Plan Section.
     * 
     * @param uploadableProposalSection
     * @param propRevId
     * @param section
     * @return
     * @throws CommonUtilException
     */
	public static boolean savePostDocMentoringPlan(UploadableProposalSection uploadableProposalSection, Long propRevId,
			PostDocMentPlanRepository pdocMPRep, ProposalPrepRevisionRepository propPrepRevRep)
			throws CommonUtilException {
		LOGGER.info("${artificatId}ServiceImpl.saveReviewersNotInclude()");
		try {
			PostDocMentPlanEntity dmpEntity = ConverterUtility
					.convertToPostDocMentoringPlanEntity(uploadableProposalSection, propRevId);
			PostDocMentPlanEntity entity = pdocMPRep.save(dmpEntity);
			if (entity != null) {
				ProposalDataUtility.updateRevisionLastUpdateTS(entity.getLastUpdtTmsp(), propRevId, propPrepRevRep);
			}
		} catch (Exception e) {
			throw new CommonUtilException(Constants.UNABLE_SAVE_SECTION, e);
		}
		return true;
	}

    /**
     * This method deletes/updates Post Doc Mentoring Plan uploaded file.
     * 
     * @param propRevId
     * @throws CommonUtilException
     */
    public static void deletePostDocMentoringPlan(Long propRevId, PostDocMentPlanRepository pdocMPRep, String nsfId)
            throws CommonUtilException {
        LOGGER.debug("${artificatId}ServiceImpl.deleteReviewersNotInclude()");
        PostDocMentPlanEntity entity = pdocMPRep.findOne(propRevId);
        entity.setPostDocMentPlanFileLocation("");
        entity.setPostDocMentPlanDocPageCount(0);
        entity.setPostDocMentPlanDocText("");
        entity.setPostDocMentPlanDocOrigFileName("");
        ConverterUtility.setAuditFields(entity);
        entity.setLastUpdtUser(nsfId);
        pdocMPRep.save(entity);
    }

    /**
     * This method is used to Save Uploaded Other Supplementary Section.
     * 
     * @param uploadableProposalSection
     * @param propRevId
     * @param section
     * @return
     * @throws CommonUtilException
     */
	public static boolean saveOtherSuppDocs(UploadableProposalSection ups, Long propRevId,
			OtherSuppDocsRepository otherSuppDocRep, ProposalPrepRevisionRepository propPrepRevRep)
			throws CommonUtilException {
		LOGGER.info("${artificatId}ServiceImpl.saveOtherSuppDocs()");
		try {
			OtherSuppDocsEntity dmpEntity = ConverterUtility.convertToOtherSuppDocsEntity(ups, propRevId);
			OtherSuppDocsEntity entity = otherSuppDocRep.save(dmpEntity);
			if (entity != null) {
				ProposalDataUtility.updateRevisionLastUpdateTS(entity.getLastUpdtTmsp(), propRevId, propPrepRevRep);
			}
		} catch (Exception e) {
			throw new CommonUtilException(Constants.UNABLE_SAVE_SECTION, e);
		}
		return true;
	}

    /**
     * This method is used to Save Uploaded Other Supplementary Section.
     * 
     * @param uploadableProposalSection
     * @param propRevId
     * @param section
     * @return
     * @throws CommonUtilException
     */
	public static boolean saveBudgetImpact(UploadableProposalSection ups, Long propRevId,
			BudgetImpactRepository budgetImpactRep, ProposalPrepRevisionRepository propPrepRevRep)
			throws CommonUtilException {
		LOGGER.info("${artificatId}ServiceImpl.saveOtherSuppDocs()");
		try {
			BudgetImpactEntity entity = ConverterUtility.convertToBudgetImpactEntity(ups, propRevId);
			BudgetImpactEntity biEntity = budgetImpactRep.save(entity);
			if (biEntity != null) {
				ProposalDataUtility.updateRevisionLastUpdateTS(biEntity.getLastUpdtTmsp(), propRevId, propPrepRevRep);
			}
		} catch (Exception e) {
			throw new CommonUtilException(Constants.UNABLE_SAVE_SECTION, e);
		}
		return true;
	}

    /**
     * This method deletes/updates Uploaded Other Supplementary uploaded file.
     * 
     * @param propRevId
     * @throws CommonUtilException
     */
    public static void deleteOtherSuppDocs(Long propRevId, OtherSuppDocsRepository otherSuppDocRep, String nsfId)
            throws CommonUtilException {
        LOGGER.debug("${artificatId}ServiceImpl.deleteOtherSuppDocs()");
        OtherSuppDocsEntity entity = otherSuppDocRep.findOne(propRevId);
        entity.setOtherSuppDocFileLocation("");
        entity.setOtherSuppDocPageCount(0);
        entity.setOtherSuppDocText("");
        entity.setOtherSuppDocOrigFileName("");
        ConverterUtility.setAuditFields(entity);
        entity.setLastUpdtUser(nsfId);
        otherSuppDocRep.save(entity);
    }

    /**
     * This method deletes/updates Uploaded Budget Impact uploaded file.
     * 
     * @param propRevId
     * @throws CommonUtilException
     */
    public static void deleteBudgetImpact(Long propRevId, BudgetImpactRepository rep, String nsfId)
            throws CommonUtilException {
        LOGGER.debug("${artificatId}ServiceImpl.deleteBudgetImpact()");
        BudgetImpactEntity entity = rep.findOne(propRevId);
        entity.setBudgImpactFileLocation("");
        entity.setBudgImpactDocPageCount(0);
        entity.setBudgImpactOrigFileName("");
        ConverterUtility.setAuditFields(entity);
        entity.setLastUpdtUser(nsfId);
        rep.save(entity);
    }

    /**
     * 
     * @param propPrepId
     * @param propRevId
     * @param otherSuppDocRep
     * @return
     * @throws CommonUtilException
     */
    public static OtherSuppDocs getOtherSuppDocs(String propPrepId, String propRevId,
            OtherSuppDocsRepository otherSuppDocRep) throws CommonUtilException {
        OtherSuppDocsEntity entity = otherSuppDocRep.findOne(Long.valueOf(propRevId));
        if (entity == null) {
            LOGGER.debug("Other Supplementaty Document section is not exists for this proposal [propPrepId: "
                    + propPrepId + Constants.REVISION_ID + propRevId + "]");
            return new OtherSuppDocs();
        }
        LOGGER.debug("Returning --> Other Supplementaty Document section by findOne() :: " + entity);

        return ConverterUtility.getOtherSuppDocsDTO(entity);
    }

}
