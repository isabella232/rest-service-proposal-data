package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.ProposalStatusType;

public interface ProposalStatusTypeRepository extends JpaRepository<ProposalStatusType, Long> {

    @Override
    List<ProposalStatusType> findAll();
}
