package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.BudgetImpactEntity;

public interface BudgetImpactRepository extends JpaRepository<BudgetImpactEntity, Long> {

}
