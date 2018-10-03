package gov.nsf.psm.proposaldata.service;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.proposal.ProposalTransfer;

public interface ProposalTransferBuilderService {
	
	/**
	 * Assembles proposal object for transfer to downstream systems
	 * @param propPrepId
	 * @param propRevId
	 * @return
	 * @throws CommonUtilException
	 */
    public ProposalTransfer build(String propPrepId, String propRevId) throws CommonUtilException;

}
