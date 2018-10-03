package gov.nsf.psm.proposaldata.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.nsf.psm.proposaldata.entity.SeniorPersonsBudgetCostEntity;

public interface SeniorPersonsBudgetCostRepository extends JpaRepository<SeniorPersonsBudgetCostEntity, Long> {

    List<SeniorPersonsBudgetCostEntity> findByPropInstRecId(Long propInstRecId);

    List<SeniorPersonsBudgetCostEntity> findByPropPersonId(Long propPersId);

    @Transactional
    @Query(value = "select max(e.lastUpdtTmsp) from SeniorPersonsBudgetCostEntity e where e.propInstRecId = ?1")
    Date getMaxLastUpdateDate(Long propInstRecId);

}
