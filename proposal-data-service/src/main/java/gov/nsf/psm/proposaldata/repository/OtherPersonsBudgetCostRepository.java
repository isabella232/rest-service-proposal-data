package gov.nsf.psm.proposaldata.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.nsf.psm.proposaldata.entity.OtherPersonsBudgetCostEntity;

public interface OtherPersonsBudgetCostRepository extends JpaRepository<OtherPersonsBudgetCostEntity, Long> {

    List<OtherPersonsBudgetCostEntity> findByPropInstRecId(Long propInstRecId);

    @Transactional
    @Query(value = "select max(e.lastUpdtTmsp) from OtherPersonsBudgetCostEntity e where e.propInstRecId = ?1")
    Date getMaxLastUpdateDate(Long revnId);

}
