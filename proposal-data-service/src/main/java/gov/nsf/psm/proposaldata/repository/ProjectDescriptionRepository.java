package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.ProjectDescriptionEntity;

public interface ProjectDescriptionRepository extends JpaRepository<ProjectDescriptionEntity, Long> {

}
