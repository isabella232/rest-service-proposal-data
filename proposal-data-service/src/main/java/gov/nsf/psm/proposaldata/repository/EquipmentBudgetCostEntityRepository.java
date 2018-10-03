package gov.nsf.psm.proposaldata.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.nsf.psm.proposaldata.entity.EquipmentBudgetCostEntity;

public interface EquipmentBudgetCostEntityRepository extends JpaRepository<EquipmentBudgetCostEntity, Long> {
    List<EquipmentBudgetCostEntity> findByPropInstRecId(Long propInstRecId);

    @Transactional
    @Query(value = "select max(e.lastUpdtTmsp) from EquipmentBudgetCostEntity e where e.propInstRecId = ?1")
    Date getMaxLastUpdateDate(Long revnId);

}
