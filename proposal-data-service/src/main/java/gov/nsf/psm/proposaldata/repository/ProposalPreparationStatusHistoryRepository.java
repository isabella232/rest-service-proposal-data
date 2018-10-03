package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.ProposalPreparationStatusHistoryEntity;



public interface ProposalPreparationStatusHistoryRepository extends JpaRepository<ProposalPreparationStatusHistoryEntity, Long> {

}
