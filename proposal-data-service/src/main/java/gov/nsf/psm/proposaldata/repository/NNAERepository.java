package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.NNAEEntity;

public interface NNAERepository extends JpaRepository<NNAEEntity, Long> {

}
