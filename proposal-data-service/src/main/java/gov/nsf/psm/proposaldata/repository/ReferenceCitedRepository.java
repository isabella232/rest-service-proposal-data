package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.ReferencesCitedEntity;

public interface ReferenceCitedRepository extends JpaRepository<ReferencesCitedEntity, Long> {

}
