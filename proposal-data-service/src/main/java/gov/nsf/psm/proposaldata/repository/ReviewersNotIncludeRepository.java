package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.ReviewersNotIncludeEntity;

public interface ReviewersNotIncludeRepository extends JpaRepository<ReviewersNotIncludeEntity, Long> {

}
