package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.BudgetJustificationEntity;

public interface BudgetJustificationRepository extends JpaRepository<BudgetJustificationEntity, Long> {

}
