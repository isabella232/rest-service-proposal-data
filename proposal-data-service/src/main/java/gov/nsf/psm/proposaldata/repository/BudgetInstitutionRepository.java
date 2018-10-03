package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.nsf.psm.proposaldata.entity.BudgetInstitutionEntity;

public interface BudgetInstitutionRepository extends JpaRepository<BudgetInstitutionEntity, Long> {

    BudgetInstitutionEntity findByPropRevId(Long propRevId);
    
    @Modifying
    @Query(value = "update BudgetInstitutionEntity b set b.lastUpdtTmsp = getDate() where b.propRevId = ?1")
    void updateBudgetDateTime(@Param("propRevnId") Long propRevnId);
    
    @Modifying
    @Query(value = "update BudgetInstitutionEntity b set b.pcvCheckIndicator = true where b.propRevId = ?1")
    void updatePcvIndicator(@Param("propRevnId") Long propRevnId);

}
