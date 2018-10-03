package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.ProposalType;

public interface ProposalTypeRepository extends JpaRepository<ProposalType, String> {

    @Override
    List<ProposalType> findAll();
}
