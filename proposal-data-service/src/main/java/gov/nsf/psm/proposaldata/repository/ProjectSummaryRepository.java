package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.ProjectSummaryEntity;

public interface ProjectSummaryRepository extends JpaRepository<ProjectSummaryEntity, Long> {

}
