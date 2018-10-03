package gov.nsf.psm.proposaldata.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazelcast.util.StringUtil;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.OtherSuppDocs;
import gov.nsf.psm.foundation.model.PSMRole;
import gov.nsf.psm.foundation.model.Personnel;
import gov.nsf.psm.foundation.model.proposal.ProposalHeader;
import gov.nsf.psm.foundation.model.proposal.ProposalPackage;
import gov.nsf.psm.foundation.model.proposal.ProposalTransfer;
import gov.nsf.psm.foundation.model.proposal.ProposalTransferHeader;
import gov.nsf.psm.proposaldata.utility.Constants;
import gov.nsf.psm.proposaldata.utility.ProposalDataUtility;;

@Component("proposalTransferBuilderService")
public class ProposalTransferBuilderServiceImpl implements ProposalTransferBuilderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalTransferBuilderServiceImpl.class);

    @Autowired
    ProposalDataService dataService;
    
    private static final String NATURE_RQST_CODE_NEW_PROJECT = "1";
    private static final String RECOMMEND_AWD_INST_STD_GRANT = "0";

    @Override
    public ProposalTransfer build(String propPrepId, String propRevId) throws CommonUtilException {
        LOGGER.debug("ProposalTransferBuilderServiceImpl.build()");
        ProposalTransfer proposal = new ProposalTransfer();

        try {

            ProposalPackage proposalPackage = dataService.getProposalPrepById(propPrepId, propRevId);
            ProposalHeader proposalHeader = dataService.getProposalHeader(propPrepId, propRevId);
            List<Personnel> currPersonnel = dataService.getPersonnels(propPrepId, propRevId);
            
            proposal.setCoverSheet(dataService.getCoverSheet(propPrepId, propRevId));
            proposal.setProjectSummary(dataService.getProjectSummary(propPrepId, propRevId));
            proposal.setProjectDescription(dataService.getProjectDescription(propPrepId, propRevId));
            proposal.setReferencesCited(dataService.getReferenceCited(propPrepId, propRevId));
            proposal.setDataManagementPlan(dataService.getDataManagementPlan(propPrepId, propRevId));
            proposal.setPostDocMentPlan(dataService.getPostDocMentoringPlan(propPrepId, propRevId));
            proposal.setFacilitiesEquipment(dataService.getFacilitiesEquipment(propPrepId, propRevId));
            proposal.setBudget(dataService.getBudget(propPrepId, propRevId));
            proposal.setBudgetJustification(dataService.getBudgetJustification(propPrepId, propRevId));
            proposal.setListOfSuggReviewers(dataService.getSuggestedReviewers(propPrepId, propRevId));
            proposal.setListOfReviewersNotToInclude(dataService.getReviewersNotInclude(propPrepId, propRevId));
            proposal.setOtherPersonnelInfoBio(dataService.getOthrPersBioInfo(propPrepId, propRevId));
            proposal.setCurrPendSuppList(dataService.getCurrentAndPendingSupports(propPrepId, propRevId));
            proposal.setCoaList(dataService.getCOAsForProposal(Long.valueOf(propRevId)));
            proposal.setBioSketcheList(dataService.getBiographicalSketches(propPrepId, propRevId));
            proposal.setPersonnels(currPersonnel);

            /* Electronic Signature and Certification Text */
            proposal.setElectronicSignature(dataService.getAORSignature(propPrepId, propRevId));
            if (!StringUtil.isNullOrEmpty(proposal.getElectronicSignature().getPropEleCertTypeId())) {
                proposal.setElectonicCertificationText(dataService.getCertificationTextById(
                        Integer.valueOf(proposal.getElectronicSignature().getPropEleCertTypeId())));
            }

            /* Get Supplementary Docs */
            List<OtherSuppDocs> otherSuppDocsList = new ArrayList<OtherSuppDocs>();
            otherSuppDocsList.add(dataService.getOtherSuppDocs(propPrepId, propRevId));
            proposal.setOtherSupplementaryDocs(otherSuppDocsList);

            /* Assemble Proposal Header */
            ProposalTransferHeader header = populateProposalHeader(proposalPackage, proposalHeader);
            header.setProposalSubmitDate(proposal.getElectronicSignature().getElecSignDate());
            proposal.setProposalHeader(header);

            /* Proposal Update Specific Sections */
            if (header.getRevisionNumber() > 0) {
                proposal.setBudgetImpact(dataService.getBudgetImpact(propPrepId, propRevId));
                proposal.setUpdateJustification(dataService.getProposalUpdateJustification(propPrepId, propRevId));
                
                // check PICoPIUpdated Flag - checks if the pis or co-pis are updated in the PFU/BR 
                int prevRevNum = header.getRevisionNumber() - 1;
                List<Personnel> prevPersonnel = dataService.getPersonnelsforLatestSubmittedProposal(propPrepId, (byte) prevRevNum);
                proposal.getProposalHeader().setPICoPIUpdated(isPICoPIUpdatedinRevn(currPersonnel, prevPersonnel));
            } else {
            	proposal.getProposalHeader().setPICoPIUpdated(false); //ORIG proposals 
            }

        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROPOSAL_TRANSFER_PACKAGE, e);
        }
        return proposal;
    }

    /**
     * Populates proposal header for data transfer
     * 
     * @param proposalPackage
     * @param proposalHeader
     * @return
     */
    private ProposalTransferHeader populateProposalHeader(ProposalPackage proposalPackage, ProposalHeader proposalHeader) {

        ProposalTransferHeader header = new ProposalTransferHeader();
        header.setCollabType(proposalPackage.getCollabType());
        header.setDeadline(proposalPackage.getDeadline());
        header.setFundingOp(proposalPackage.getFundingOp());
        header.setProposalTitle(proposalPackage.getProposalTitle());
        header.setProposalType(proposalPackage.getProposalType());
        header.setSubmissionType(proposalPackage.getSubmissionType());
        header.setUocs(proposalPackage.getUocs());
        header.setAwardeeOrganization(proposalPackage.getInstitution());
        header.setPropInitiateDate(proposalPackage.getProposalInitiateDate());
        header.setPropInitiateUser(proposalHeader.getProposalInitiatedNSFId());
        header.setRevnCreateDate(proposalHeader.getRevnCreateDate());
        header.setRevnCreateUser(proposalHeader.getRevnCreateUser());
        header.setGpgVersion(proposalHeader.getCurrPPAPPGVer());
        header.setRevisionNumber(proposalPackage.getRevNum());
        header.setRevnType(proposalHeader.getRevnType());
        header.setTotalRqstDolAmt(proposalPackage.getTotalRqstDolAmt());
        
        //Static Values for April 2018 Release - Will be dynamic post-April 2018
        header.setNatureRequestCode(NATURE_RQST_CODE_NEW_PROJECT);
        header.setRecommendAwdInstr(RECOMMEND_AWD_INST_STD_GRANT);

        return header;
    }
    
    /**
     * Check if PIs or CoPIs are updated in the PFU/BR
     * 
     * @param currPersonnel
     * @param prevPersonnel
     * @return
     */
    private boolean isPICoPIUpdatedinRevn(List<Personnel> currPersonnel, List<Personnel> prevPersonnel) {

        List<Personnel> currPiCoPiList = getOnlyPICoPIs(currPersonnel);
        List<Personnel> prevPiCoPiList = getOnlyPICoPIs(prevPersonnel);

        return ProposalDataUtility.isPersonnelUpdated(currPiCoPiList, prevPiCoPiList);
    }
    
    /**
     * Filter Personnel list to only PI and Co-PI
     * 
     * @param prsList
     * @return
     */
    private List<Personnel> getOnlyPICoPIs(List<Personnel> prsList) {
        List<Personnel> piCoPIList = new ArrayList<Personnel>();
        for (Personnel pers : prsList) {
            String code = pers.getPsmRole().getCode().trim();
            if (PSMRole.ROLE_PI.equalsIgnoreCase(code) || PSMRole.ROLE_CO_PI.equalsIgnoreCase(code)) {
                piCoPIList.add(pers);
            }
        }
        return piCoPIList;
    }
}
