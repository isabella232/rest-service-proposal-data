package gov.nsf.psm.proposaldata.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.UnitOfConsideration;

@Transactional
public interface UnitOfConsiderationRepository extends JpaRepository<UnitOfConsideration, Long> {

}
