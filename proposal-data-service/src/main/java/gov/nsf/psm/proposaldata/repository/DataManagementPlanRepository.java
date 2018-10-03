package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.DataManagementPlanEntity;

public interface DataManagementPlanRepository extends JpaRepository<DataManagementPlanEntity, Long> {

}
