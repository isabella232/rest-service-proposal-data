package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import gov.nsf.psm.proposaldata.entity.ProposalElectronicSignEntity;

public interface ProposalElectronicSignRepository extends JpaRepository<ProposalElectronicSignEntity, Integer> {
	
	ProposalElectronicSignEntity findByPropPrepRevnId(Integer propRevId);
}
