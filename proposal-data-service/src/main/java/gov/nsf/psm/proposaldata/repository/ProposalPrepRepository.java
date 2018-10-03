package gov.nsf.psm.proposaldata.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.nsf.psm.proposaldata.entity.ProposalPrep;

@Transactional
public interface ProposalPrepRepository extends JpaRepository<ProposalPrep, Long> {

    ProposalPrep findByPropPrepId(Long propPrepId);

    ProposalPrep findByNsfPropId(String nsfPropId);

    @Modifying
    @Query(value = "update ProposalPrep m set m.nsfPropSubmDate =getDate(), m.lastUpdtTmsp=getDate(), m.lastUpdtUser=?2 where m.propPrepId = ?1 ")
    void updateProposalSubmitDate(@Param("propPrepId") Long propPrepId, @Param("lastUpdtUser") String lastUpdtUser);
  
    @Modifying
    @Query(value = "update ProposalPrep m set m.nsfPropId =?2 , m.lastUpdtTmsp=getDate(), m.lastUpdtUser=?3 where m.propPrepId = ?1 ")
    void updateProposalNsfPropId(@Param("propPrepId") Long propPrepId, @Param("nsfPropId") String nsfPropId,
             @Param("lastUpdtUser") String lastUpdtUser);

}
