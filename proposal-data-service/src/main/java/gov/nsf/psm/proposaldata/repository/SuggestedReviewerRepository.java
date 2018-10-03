package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.SuggestedReviewerEntity;

public interface SuggestedReviewerRepository extends JpaRepository<SuggestedReviewerEntity, Long> {

}
