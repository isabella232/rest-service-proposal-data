package gov.nsf.psm.proposaldata.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.Institution;
import gov.nsf.psm.foundation.model.Personnel;
import gov.nsf.psm.foundation.model.proposal.ProposalPackage;
import gov.nsf.psm.proposaldata.entity.ProposalPrep;
import gov.nsf.psm.proposaldata.entity.ProposalPrepRevision;
import gov.nsf.psm.proposaldata.repository.ProposalPrepRepository;
import gov.nsf.psm.proposaldata.repository.ProposalPrepRevisionRepository;

public class ProposalDataUtility {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProposalDataUtility.class);

    private ProposalDataUtility() {
    }

    public static String returnNullIfBlank(String str) {
        if (StringUtils.isBlank(str) || StringUtils.isEmpty(str)) {
            return null;
        }
        return str;
    }
    
    public static String returnNullIfBlankOrNull(String str) {
        if (StringUtils.isBlank(str) || StringUtils.isEmpty(str) || "null".equals(str.trim())) {
            return null;
        }
        return str;
    }

    /**
     * This method sets the Proposal forward status to AOR/SPO.
     * 
     * @param propPrepId
     * @param propRevId
     * @param statusCode
     * @param propPrepRep
     * @return
     * @throws CommonUtilException
     */
    public static boolean setProposalAccess(ProposalPackage proposalPackage, ProposalPrepRepository propPrepRep)
            throws CommonUtilException {
        boolean status = false;
        try {
            ProposalPrep propEntity = propPrepRep.findByPropPrepId(Long.valueOf(proposalPackage.getPropPrepId()));
            if (propEntity != null) {
                for (ProposalPrepRevision revision : propEntity.getRevisions()) {
                    if (revision.getPropPrepRevnId().equals(Long.valueOf(proposalPackage.getPropRevId()))) {
                        LOGGER.debug("Changing status : " + proposalPackage.getProposalStatus());
                        revision.setPropPrepSttsCode(proposalPackage.getProposalStatus());
                        revision.setPropPrepSttsDate(getCurrDate());
                        ConverterUtility.setAuditFields(revision);
                        LOGGER.debug("ProposalDataUtility.setProposalAccess()"+proposalPackage.getLastUpdatedUser());
                        revision.setLastUpdtUser(proposalPackage.getLastUpdatedUser());                        
                        break;
                    }
                }
                propPrepRep.save(propEntity);               
                status = true;
            }
        } catch (Exception e) {
            throw new CommonUtilException("Problem while saving Proposal Status : ", e);
        }
        return status;
    }
    
	public static boolean isPPOPhasSpecialCharacters(Institution ppopInstitution) {
		boolean status = false;
		if ((ppopInstitution.getOrganizationName() != null && containSpecialChars(ppopInstitution.getOrganizationName(),
		        Constants.PPOP_ORG_NAME_UNALLOWABLE_CHARS))
		        || (ppopInstitution.getAddress().getStreetAddress() != null && containSpecialChars(
		                ppopInstitution.getAddress().getStreetAddress(), Constants.PPOP_STREET_ADD_UNALLOWABLE_CHARS))
		        || (ppopInstitution.getAddress().getStreetAddress2() != null
		                && containSpecialChars(ppopInstitution.getAddress().getStreetAddress2(),
		                        Constants.PPOP_STREET_ADD_2_UNALLOWABLE_CHARS))
		        || (ppopInstitution.getAddress().getCityName() != null && containSpecialChars(
		                ppopInstitution.getAddress().getCityName(), Constants.PPOP_CITY_NAME_UNALLOWABLE_CHARS))
		        || (ppopInstitution.getAddress().getPostalCode() != null && containSpecialChars(
		                ppopInstitution.getAddress().getPostalCode(), Constants.PPOP_POSTAL_CODE_UNALLOWABLE_CHARS))) {

			status = true;
		}
		return status;
	}

    /**
     * 
     * @param inputStr
     * @param specialCharacterString
     * @return
     */

	public static boolean containSpecialChars(String inputStr, String specialCharacterString) {

		for (int i = 0; i < inputStr.length(); i++) {
			if (specialCharacterString.indexOf(inputStr.charAt(i)) != -1) {
				return true;
			}
		}
		return false;
	}
    
   
    
    /**
     * This method returns Proposal Preparation Status.
     * 
     * @param propPrepId
     * @param propRevId
     * @param propPrepRep
     * @return
     * @throws CommonUtilException
     */
    public static ProposalPackage getProposalAccess(String propPrepId, String propRevId,
            ProposalPrepRepository propPrepRep) throws CommonUtilException {
        ProposalPrep propEntity = null;
        ProposalPackage pPackage = new ProposalPackage();
        try {
            propEntity = propPrepRep.findByPropPrepId(Long.valueOf(propPrepId));
            if (propEntity != null) {
                pPackage.setPropPrepId(Long.toString(propEntity.getPropPrepId()));
                pPackage.setPropRevId(propRevId);
                for (ProposalPrepRevision revision : propEntity.getRevisions()) {
                    if (revision.getPropPrepRevnId().equals(Long.valueOf(propRevId))) {
                        LOGGER.debug("PropRevId : " + propRevId + " : StatusCode : " + revision.getPropPrepSttsCode());
                        pPackage.setPropRevId(propRevId);
                        pPackage.setProposalStatus(revision.getPropPrepSttsCode().trim());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new CommonUtilException("Problem while getting Proposal Status : ", e);
        }
        return pPackage;
    }
    
    public static String buildFullName(String fn, String mn, String ln) {
        StringBuilder fullName = new StringBuilder();
        if(!StringUtils.isEmpty(ln)) {
            fullName.append(ln);
        }
        if(!StringUtils.isEmpty(fn)) {
            if(!StringUtils.isEmpty(ln)) {
                fullName.append(", ");
            }
            fullName.append(fn);
        }
        if(!StringUtils.isEmpty(mn)) {
            if(!StringUtils.isEmpty(fn) || !StringUtils.isEmpty(ln)) {
                fullName.append(" ");
            }
            fullName.append(mn);
        }
        return fullName.toString();
    }
    
    public static Date getCurrDate() {
        return new Date();
    }
    
    /**
     * Checks if personnel has been updated
     * 
     * @param currPersonnel
     * @param prevPersonnel
     * @return
     */
    public static boolean isPersonnelUpdated(List<Personnel> currPersonnel, List<Personnel> prevPersonnel) {
        Boolean isUpdated = false; //false until proven that PI/CoPI changed
        Map<String, Personnel> prevPersMap = new HashMap<String, Personnel>();
        
        LOGGER.debug("Size of currPiCoPiList : " + currPersonnel.size() + " Size of prevPiCoPiList : " + prevPersonnel.size());

        // Checking No of Personnels Greater or less.
        if (currPersonnel.size() > prevPersonnel.size() || currPersonnel.size() < prevPersonnel.size()) {
            return true;
        }
        
        //Load prevPiCoPiList into HashMap
        for(Personnel prevPers: prevPersonnel) {
        	LOGGER.debug("Prev Revn Personnel: " + prevPers.toString());
        	prevPersMap.put(prevPers.getNsfId(), prevPers);
        }
        
        //Check if each currPiCoPiList element is in prevPiCoPiList and have the same role code
        for(Personnel curPers : currPersonnel) {
        	LOGGER.debug("Curr Revn Personnel: " + curPers.toString());
        	if(!prevPersMap.containsKey(curPers.getNsfId())) {
        		return true; //nsfId not found
        	} else {
        		Personnel prevPers = prevPersMap.get(curPers.getNsfId());
        		if(!curPers.getPSMRole().getCode().trim().equals(prevPers.getPSMRole().getCode().trim())) {
        			return true; // nsfId found but not the same role code
        		}
        	}
        }

        return isUpdated;
    }
    
    public static void updateRevisionLastUpdateTS(Date lastUpdtTmsp, Long propRevId, ProposalPrepRevisionRepository propPrepRevRep) {
        try {
            ProposalPrepRevision rev = propPrepRevRep.findOne(propRevId);
            if (rev != null) {
                rev.setLastUpdtTmsp(lastUpdtTmsp == null?new Date():lastUpdtTmsp);
                propPrepRevRep.save(rev);
            }
        } catch (Exception e) {
            LOGGER.debug("Revision update time stamp could not be updated " + e);
        }
    }

    
}
