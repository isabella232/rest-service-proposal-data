package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.nsf.psm.proposaldata.entity.CurrentAndPendingSupportEntity;

public interface CurrentAndPendingSupportRepository extends JpaRepository<CurrentAndPendingSupportEntity, Long> {

    @Transactional
    @Query(value = "select c from CurrentAndPendingSupportEntity c where c.lastUpdtTmsp in (select max(c.lastUpdtTmsp) from CurrentAndPendingSupportEntity c, ProposalPrepRevision r where c.propPrepRevnId = r.propPrepRevnId and r.propPrepId = ?1 and r.propPrepRevnId = ?2))")
    CurrentAndPendingSupportEntity getLatestCurrAndPendSupport(Long propPrepId, Long propRevnId);
    
    @Transactional
    @Query(value = "select c from CurrentAndPendingSupportEntity c where c.propPrepRevnId = ?1")
    List<CurrentAndPendingSupportEntity> getCurrentAndPendingSupportForProposal(Long propRevnId);
    
    @Transactional
    @Query(value = "select c from CurrentAndPendingSupportEntity c where c.propPrepRevnId = ?1 and c.person.proposalPersonId = ?2")
    List<CurrentAndPendingSupportEntity> getCurrentAndPendingSupportForProposalByPerson(Long propRevnId, Long persId);

}
