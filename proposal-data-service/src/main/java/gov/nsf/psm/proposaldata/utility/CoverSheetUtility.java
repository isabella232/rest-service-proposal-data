/**
 * 
 */
package gov.nsf.psm.proposaldata.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.Institution;
import gov.nsf.psm.foundation.model.PSMRole;
import gov.nsf.psm.foundation.model.coversheet.CoverSheet;
import gov.nsf.psm.foundation.model.coversheet.FederalAgency;
import gov.nsf.psm.foundation.model.coversheet.InternationalActyCountry;
import gov.nsf.psm.foundation.model.coversheet.PrimaryPlaceOfPerformance;
import gov.nsf.psm.proposaldata.entity.BudgetInstitutionEntity;
import gov.nsf.psm.proposaldata.entity.CoverSheetEntity;
import gov.nsf.psm.proposaldata.entity.InternationalActivitiesEntity;
import gov.nsf.psm.proposaldata.entity.OtherFederalAgenciesEntity;
import gov.nsf.psm.proposaldata.entity.PPOPEntity;
import gov.nsf.psm.proposaldata.entity.ProposalPrepRevision;
import gov.nsf.psm.proposaldata.repository.BudgetInstitutionRepository;
import gov.nsf.psm.proposaldata.repository.CoverSheetRepository;
import gov.nsf.psm.proposaldata.repository.PPOPRepository;
import gov.nsf.psm.proposaldata.repository.ProposalPersonalRepository;
import gov.nsf.psm.proposaldata.repository.ProposalPrepRevisionRepository;

/**
 * @author pkatrapa
 *
 */
public class CoverSheetUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoverSheetUtility.class);

    /**
     * 
     */
    private CoverSheetUtility() {
        super();
    }

    public static boolean isCoverSheetExists(String propRevId, CoverSheetRepository cvRep) throws CommonUtilException {
        boolean status = false;
        try {
            CoverSheetEntity cvEntity = cvRep.findByPropRevId(Long.valueOf(propRevId));
            if (cvEntity != null) {
                status = true;
            }
        } catch (Exception e) {
            LOGGER.debug(Constants.CV_NOT_AVIL + e);
            throw new CommonUtilException(Constants.CV_NOT_AVIL + e);
        }
        return status;
    }

    public static String getPrimaryAwardeeOrganizationId(String propRevId, BudgetInstitutionRepository budgInstRep)
            throws CommonUtilException {
        String awdOrdId = "";
        try {
            BudgetInstitutionEntity budgEntity = budgInstRep.findByPropRevId(Long.valueOf(propRevId));
            if (budgEntity != null) {
                awdOrdId = budgEntity.getInstId();
            }
        } catch (Exception e) {
            LOGGER.debug(Constants.PRMY_NOT_AVIL + e);
            throw new CommonUtilException(Constants.PRMY_NOT_AVIL + e);
        }
        return awdOrdId;
    }

	@Transactional
	public static boolean saveCoverSheet(CoverSheet cv, CoverSheetRepository cvRep,
			ProposalPrepRevisionRepository propPrepRevRep) throws CommonUtilException {
		LOGGER.debug("CoverSheetUtility.saveProposalCoverSheet() PropRevId : " + cv.getPropRevId() + " PropPrepId : "
				+ cv.getPropPrepId());
		try {
			CoverSheetEntity cvEnt = cvRep.findByPropRevId(Long.valueOf(cv.getPropRevId()));
			if (cvEnt != null) {
				LOGGER.debug("Deleting Existing CoverSheet : PropRevId : " + cvEnt.getPropRevId());
				cvRep.delete(cvEnt);
			}
			CoverSheetEntity cvEntity = new CoverSheetEntity();
			cvEntity.setPropRevId(Long.valueOf(cv.getPropRevId()));
			setDataforCoverSheetEntity(cvEntity, cv);
			if (cv.getPpop() != null) {
				setPPOPEntity(cv, cvEntity);
			}
			if (cv.getInternationalActyCountries() != null && !cv.getInternationalActyCountries().isEmpty()) {
				setInternationalActivities(cv.getInternationalActyCountries(), cv.getLastUpdatedUser(), cvEntity);
			}
			if (cv.getFederalAgencies() != null && !cv.getFederalAgencies().isEmpty()) {
				setOtherFederalAgencies(cv.getFederalAgencies(), cv.getLastUpdatedUser(), cvEntity);
			}
			CoverSheetEntity entity = cvRep.save(cvEntity);
			if (entity != null) {
				ProposalPrepRevision rev = propPrepRevRep.findOne(Long.valueOf(cv.getPropRevId()));
				if (rev != null) {
					rev.setLastUpdtTmsp(entity.getLastUpdtTmsp());
					propPrepRevRep.save(rev);
				}
			}
		} catch (Exception e) {
			throw new CommonUtilException(Constants.UNABLE_SAVE_CV + e);
		}
		return true;
	}

    /**
     * This method Gets Cover Sheet data.
     * 
     * @param propRevId
     * @param cvRep
     * @param ppopRep
     * @param othFedAgencyRep
     * @param intActRep
     * @return
     * @throws CommonUtilException
     */
    public static CoverSheet getCoverSheet(String propRevId, CoverSheetRepository cvRep) throws CommonUtilException {
        CoverSheet cv = new CoverSheet();
        LOGGER.debug("getCoverSheet -- propRevId : " + propRevId);
        try {

            CoverSheetEntity cve = cvRep.findByPropRevId(Long.valueOf(propRevId));
            if (cve != null) {
                cv = setCoverSheetData(cve);
            }
        } catch (Exception e) {
            LOGGER.debug(Constants.CV_NOT_AVIL + e);
            throw new CommonUtilException(Constants.CV_NOT_AVIL + e);
        }
        return cv;
    }

    /**
     * This method is useful to Setup Cover Sheet data before save.
     * 
     * @param cve
     * @param ppopRep
     * @param othFedAgencyRep
     * @param intActRep
     * @return
     */
    private static CoverSheet setCoverSheetData(CoverSheetEntity cve) {
        CoverSheet cv = new CoverSheet();
        cv.setCoverSheetId(cve.getPropCoverSheetId().intValue());
        cv.setForProfit(cve.isForProfit());
        cv.setSmallbusiness(cve.isSmallBusiness());
        cv.setMinoritybusiness(cve.isMinorityBusiness());
        cv.setWomenOwnedbusiness(cve.isWomenOwnedBusiness());
        cv.setBeginningInvestigator(cve.isBeginningInvestigator());
        cv.setDisclosureLobbyActy(cve.isDisclosureLobbyingActivities());
        cv.setProprietaryPrivileged(cve.isProprietaryPrivilegedInformation());
        cv.setHistoricPlace(cve.isHistoricPlaces());
        cv.setVertebrateAnimal(cve.isVertebrateAnimals());
        cv.setPcvCheckIndicator(cve.isPcvCheckIndicator());

        if (cve.getIacucAppDate() != null) {
            try {
                cv.setiAcucSAppDate(formatDate(cve.getIacucAppDate()));
            } catch (Exception e) {
            	LOGGER.debug("Error while formatting date",e);
            }
        }

        cv.setAnimalWelfareAssuNumber(cve.getPhsAnimalWelfareAssuranceNumber());
        cv.setHumanSubject(cve.isHumanSubjects());
        cv.setIntlActivities(cve.isIntlActivities());
        cv.setExemptionSubsection(cve.getExemptionSubsection());

        cv.setDunsNumber(cve.getDunsNumber());
        cv.setEmployerTIN(cve.getEmployerTIN());
        cv.setTimeZone(cve.getTimeZone());

        if (cve.getIrbAppDate() != null) {
            try {
                cv.setiRbAppDate(formatDate(cve.getIrbAppDate()));
            } catch (Exception e) {
            	LOGGER.debug("Error while formatting date",e);
            }
        }
        cv.setHumanSubjectAssuNumber(cve.getHumanSubjectsAssurancNumber());

        if (cve.getRequestedStartDate() != null) {
            try {
                cv.setRequestedStartDate(formatDate(cve.getRequestedStartDate()));
            } catch (Exception e) {
            	LOGGER.debug("Error while formatting date",e);
            }
        }

        cv.setProposalDuration(cve.getProposalDuration());

        if (cve.getHumanSubjectAPEType() != null) {
            cv.setHumanSubjectsAPEType(cve.getHumanSubjectAPEType().trim());
        }
        if (cve.getVrtbAnimalAPType() != null) {
            cv.setVrtbAnimalAPType(cve.getVrtbAnimalAPType().trim());
        }
        cv.setLastUpdatedTmsp(cve.getLastUpdtTmsp());

        // Adding PPOP info

        if (cve.getPpopEntity() != null) {

            PrimaryPlaceOfPerformance ppop = new PrimaryPlaceOfPerformance();
            ppop.setOrganizationName(cve.getPpopEntity().getOrganizationName());
            ppop.setCountryCode(cve.getPpopEntity().getCountryCode());
            ppop.setStreetAddress(cve.getPpopEntity().getStreetAddress());
            ppop.setStreetAddress2(cve.getPpopEntity().getStreetAddress2());
            ppop.setDepartmentName(cve.getPpopEntity().getDepartmentName());
            ppop.setCityName(cve.getPpopEntity().getCityName());
            ppop.setStateCode(cve.getPpopEntity().getStateCode());
            ppop.setPostalCode(cve.getPpopEntity().getPostalCode());
            ppop.setLastUpdatedTmsp(cve.getLastUpdtTmsp());
            cv.setPpop(ppop);
        }

        // get Other Federal
        List<FederalAgency> federalAgencies = new ArrayList<FederalAgency>();
        List<OtherFederalAgenciesEntity> fedEntyList = cve.getOthFedAgencies();
        if (!fedEntyList.isEmpty()) {
            for (OtherFederalAgenciesEntity ofe : fedEntyList) {
                FederalAgency fa = new FederalAgency();
                fa.setFedAgencyNameAbbreviation(ofe.getFedAgencyNameAbbreviation());
                fa.setLastUpdatedTmsp(ofe.getLastUpdtTmsp());
                federalAgencies.add(fa);
            }
        }
        cv.setFederalAgencies(federalAgencies);

        // get International Activities
        List<InternationalActyCountry> intActyCtrList = new ArrayList<InternationalActyCountry>();
        List<InternationalActivitiesEntity> intActCtryEntyList = cve.getIntActivities();
        if (!intActCtryEntyList.isEmpty()) {
            for (InternationalActivitiesEntity iae : intActCtryEntyList) {
                InternationalActyCountry ic = new InternationalActyCountry();
                ic.setIntlCountryCode(iae.getIntlCountryCode());
                ic.setIntlCountryName(iae.getIntlActyCountryName());
                intActyCtrList.add(ic);
            }
        }
        cv.setInternationalActyCountries(intActyCtrList);

        LOGGER.debug("CoverSheetUtility.setCoverSheetData(): " + cv.getRequestedStartDate() + " iRbAppDate :"
                + cv.getiRbAppDate() + " iAcucSAppDate: " + cv.getiAcucSAppDate());

        return cv;
    }

    /**
     * This method updated PPOP after changing Awardee Organization.
     * 
     * @param propPrepId
     * @param propRevId
     * @param coverSheetId
     * @param inst
     * @param ppopRep
     * @return
     * @throws CommonUtilException
     */
	public static boolean changeAwardeeOrganization(String propRevId, Institution inst, String coverSheetId,
			CoverSheetRepository cvRep, BudgetInstitutionRepository budgInstRep,
			ProposalPersonalRepository proposalPersonalRep, PPOPRepository ppopRep) throws CommonUtilException {
		LOGGER.debug("changeAwardeeOrganization -- propRevId : " + propRevId + "  coverSheetId: " + coverSheetId);
		String employerTaxId = null;
		try {
			employerTaxId = inst.getTaxId();
			if (employerTaxId != null) {
				employerTaxId = employerTaxId.replace("-", "");
			}
			if (ProposalDataUtility.isPPOPhasSpecialCharacters(inst)) {
				LOGGER.debug("changeAwardeeOrganization-- PPOP has Special Characters -- propRevId : " + propRevId
						+ "  coverSheetId: " + coverSheetId);
				cvRep.updateCoversheet(inst.getId(), inst.getDunsNumber(), employerTaxId, inst.getTimeZone(),
						inst.getLastUpdatedUser(), Long.valueOf(propRevId));
				ppopRep.deletePpop(Long.valueOf(coverSheetId));
			} else {
				LOGGER.debug("changeAwardeeOrganization-- NO Special Characters -- propRevId : " + propRevId
						+ "  coverSheetId: " + coverSheetId);
				CoverSheetEntity cvEntity = cvRep.findByPropRevId(Long.valueOf(propRevId));
				PPOPEntity pEntity = cvEntity.getPpopEntity();
				cvEntity.setAwdOrgId(inst.getId());
				cvEntity.setDunsNumber(inst.getDunsNumber());
				cvEntity.setEmployerTIN(employerTaxId);
				cvEntity.setTimeZone(inst.getTimeZone());
				ConverterUtility.setAuditFields(cvEntity);
				cvEntity.setLastUpdtUser(inst.getLastUpdatedUser());
				if (pEntity != null) {
					pEntity.setCoverSheetEntity(cvEntity);
					pEntity.setCityName(inst.getAddress().getCityName());
					pEntity.setDepartmentName(pEntity.getDepartmentName());
					pEntity.setStreetAddress(inst.getAddress().getStreetAddress());
					pEntity.setStreetAddress2(inst.getAddress().getStreetAddress2());
					pEntity.setPostalCode(inst.getAddress().getPostalCode());
					pEntity.setOrganizationName(inst.getOrganizationName());
					pEntity.setCountryCode(inst.getAddress().getCountryCode());
					if (inst.getAddress().getCountryCode() != null
							&& inst.getAddress().getCountryCode().equalsIgnoreCase(Constants.COUNTRY_US)) {
						pEntity.setStateCode(inst.getAddress().getStateCode());
					} else {
						pEntity.setStateCode(null);
					}

					ConverterUtility.setAuditFields(pEntity);
					pEntity.setLastUpdtUser(inst.getLastUpdatedUser());
					cvEntity.setPpopEntity(pEntity);

				} else {
					LOGGER.debug("changeAwardeeOrganization-- Inserting new PPOP Record");
					PPOPEntity pe = new PPOPEntity();
					pe.setCoverSheetEntity(cvEntity);
					pe.setCityName(inst.getAddress().getCityName());
					pe.setStreetAddress(inst.getAddress().getStreetAddress());
					pe.setStreetAddress2(inst.getAddress().getStreetAddress2());
					pe.setPostalCode(inst.getAddress().getPostalCode());
					pe.setOrganizationName(inst.getOrganizationName());
					pe.setCountryCode(inst.getAddress().getCountryCode());
					if (inst.getAddress().getCountryCode() != null
							&& inst.getAddress().getCountryCode().equalsIgnoreCase(Constants.COUNTRY_US)) {
						pe.setStateCode(inst.getAddress().getStateCode());
					} else {
						pe.setStateCode(null);
					}

					ConverterUtility.setAuditFields(pe);
					pe.setLastUpdtUser(inst.getLastUpdatedUser());
					cvEntity.setPpopEntity(pe);
				}
				cvRep.save(cvEntity);
			}
			// update prop_inst table also.
			BudgetInstitutionEntity budgInst = budgInstRep.findByPropRevId(Long.valueOf(propRevId));
			if (budgInst != null) {
				budgInst.setInstId(inst.getId());
				ConverterUtility.setAuditFields(budgInst);
				budgInst.setLastUpdtUser(inst.getLastUpdatedUser());
				budgInstRep.save(budgInst);
			}
			proposalPersonalRep.updatePIInstitution(inst.getId(), inst.getLastUpdatedUser(), Long.valueOf(propRevId),
					PSMRole.ROLE_PI);
		} catch (Exception e) {
			throw new CommonUtilException(Constants.UNABLE_UPDT_PPOP + e);
		}
		return true;
	}

    /**
     * This method sets data to insert into Cover Sheet.
     * 
     * @param cvEntity
     * @param cv
     */
    private static void setDataforCoverSheetEntity(CoverSheetEntity cvEntity, CoverSheet cv) {
        LOGGER.debug("SetDataforCoverSheetEntity ...!!");
        String employerTIN = null;
        Institution inst = cv.getAwdOrganization();
        if (inst != null) {
            cvEntity.setAwdOrgId(inst.getId());
            cvEntity.setTimeZone(inst.getTimeZone());
            cvEntity.setDunsNumber(inst.getDunsNumber());
            employerTIN = inst.getTaxId();
            if (employerTIN != null) {
                employerTIN = employerTIN.replace("-", "");
            }
            cvEntity.setEmployerTIN(employerTIN);
        }
        cvEntity.setForProfit(cv.isForProfit());
        cvEntity.setSmallBusiness(cv.isSmallbusiness());
        cvEntity.setMinorityBusiness(cv.isMinoritybusiness());
        cvEntity.setWomenOwnedBusiness(cv.isWomenOwnedbusiness());
        cvEntity.setBeginningInvestigator(cv.isBeginningInvestigator());
        cvEntity.setDisclosureLobbyingActivities(cv.isDisclosureLobbyActy());
        cvEntity.setProprietaryPrivilegedInformation(cv.isProprietaryPrivileged());
        cvEntity.setHistoricPlaces(cv.isHistoricPlace());
        cvEntity.setVertebrateAnimals(cv.isVertebrateAnimal());
        cvEntity.setHumanSubjects(cv.isHumanSubject());
        cvEntity.setExemptionSubsection(cv.getExemptionSubsection());

        if (cv.getiAcucSAppDate() != null) {
            try {
                cvEntity.setIacucAppDate(formatDate(cv.getiAcucSAppDate()));
            } catch (Exception e) {
            	LOGGER.debug("Error while formatting date",e);
            }
        }
        if (cv.getiRbAppDate() != null) {
            try {
                cvEntity.setIrbAppDate(formatDate(cv.getiRbAppDate()));
            } catch (Exception e) {
            	LOGGER.debug("Error while formatting date",e);
            }
        }
        ConverterUtility.setAuditFields(cvEntity);
        cvEntity.setLastUpdtUser(cv.getLastUpdatedUser());
        cvEntity.setIntlActivities(cv.isIntlActivities());
        cvEntity.setPhsAnimalWelfareAssuranceNumber(cv.getAnimalWelfareAssuNumber());
        cvEntity.setHumanSubjectsAssurancNumber(cv.getHumanSubjectAssuNumber());

        if (cv.getRequestedStartDate() != null) {
            try {
                cvEntity.setRequestedStartDate(formatDate(cv.getRequestedStartDate()));
            } catch (Exception e) {
            	LOGGER.debug("Error while formatting date",e);
            }
        }
        cvEntity.setProposalDuration(cv.getProposalDuration());
        cvEntity.setHumanSubjectAPEType(cv.getHumanSubjectsAPEType());
        cvEntity.setVrtbAnimalAPType(cv.getVrtbAnimalAPType());

        if (cv.isInitialCreation()) {
            cvEntity.setPcvCheckIndicator(false);
        } else {
            cvEntity.setPcvCheckIndicator(true);
        }

        LOGGER.debug("CoverSheetUtility.setDataforCoverSheetEntity(): " + cvEntity.getRequestedStartDate()
                + " iRbAppDate :" + cvEntity.getIrbAppDate() + " iAcucSAppDate: " + cvEntity.getIacucAppDate());

    }

    /**
     * This method sets data to insert into Primary Place of Performance.
     * 
     * @param ppop
     * @param cvEntity
     */
    private static void setPPOPEntity(CoverSheet cv, CoverSheetEntity cvEntity) {
        String postalCode = null;
        PPOPEntity pEntity = new PPOPEntity();
        pEntity.setCoverSheetEntity(cvEntity);
        PrimaryPlaceOfPerformance ppop = cv.getPpop();
        if (ppop != null) {
            pEntity.setOrganizationName(ppop.getOrganizationName());
            pEntity.setCountryCode(ppop.getCountryCode());
            pEntity.setDepartmentName(ppop.getDepartmentName());
            pEntity.setCityName(ppop.getCityName());
            if (ppop.getCountryCode() != null && ppop.getCountryCode().equalsIgnoreCase(Constants.COUNTRY_US)) {
                pEntity.setStateCode(ppop.getStateCode());
            }
            postalCode = ppop.getPostalCode();
            if (postalCode != null) {
                postalCode = postalCode.replace("-", "");
            }
            pEntity.setPostalCode(postalCode);
            pEntity.setStreetAddress(ppop.getStreetAddress());
            pEntity.setStreetAddress2(ppop.getStreetAddress2());
        }
        ConverterUtility.setAuditFields(pEntity);
        pEntity.setLastUpdtUser(cv.getLastUpdatedUser());
        cvEntity.setPpopEntity(pEntity);
    }

    /**
     * This method sets data to insert into International Activities..
     * 
     * @param internationalActyCountries
     * @param cvEntity
     */
    private static void setInternationalActivities(List<InternationalActyCountry> internationalActyCountries,
            String lastUpdtUser, CoverSheetEntity cvEntity) {
        List<InternationalActivitiesEntity> ieList = new ArrayList<InternationalActivitiesEntity>();
        for (InternationalActyCountry fe : internationalActyCountries) {
            InternationalActivitiesEntity ie = new InternationalActivitiesEntity();
            ie.setCoverSheetEntity(cvEntity);
            ie.setIntlActyCountryName(fe.getIntlCountryName());
            ie.setIntlCountryCode(fe.getIntlCountryCode());
            ConverterUtility.setAuditFields(ie);
            ie.setLastUpdtUser(lastUpdtUser);
            ieList.add(ie);
        }
        cvEntity.setIntActivities(ieList);
    }

    /**
     * This method sets data to insert into Other Federal Agencies.
     * 
     * @param federalAgencies
     * @param cvEntity
     */
    private static void setOtherFederalAgencies(List<FederalAgency> federalAgencies, String lastUpdtUser,
            CoverSheetEntity cvEntity) {
        List<OtherFederalAgenciesEntity> ofList = new ArrayList<OtherFederalAgenciesEntity>();
        for (FederalAgency fe : federalAgencies) {
            OtherFederalAgenciesEntity otEntity = new OtherFederalAgenciesEntity();
            otEntity.setCoverSheetEntity(cvEntity);
            otEntity.setFedAgencyNameAbbreviation(fe.getFedAgencyNameAbbreviation());
            ConverterUtility.setAuditFields(otEntity);
            otEntity.setLastUpdtUser(lastUpdtUser);
            ofList.add(otEntity);
        }
        cvEntity.setOthFedAgencies(ofList);
    }

    /**
     * 
     * @param tDate
     * @return
     * @throws ParseException
     */
    public static Date formatDate(Date tDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fdate = sdf.parse(sdf.format(tDate));
        return fdate;

    }
}
