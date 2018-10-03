package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.nsf.psm.proposaldata.entity.BiographicalSketchEntity;

public interface BiographicalSketchRepository extends JpaRepository<BiographicalSketchEntity, Long> {

    @Transactional
    @Query(value = "select s from BiographicalSketchEntity s where s.lastUpdtTmsp in (select max(s.lastUpdtTmsp) from BiographicalSketchEntity s, ProposalPrepRevision r where s.propPrepRevnId = r.propPrepRevnId and r.propPrepId = ?1 and r.propPrepRevnId = ?2))")
    BiographicalSketchEntity getLatestBioSketch(Long propPrepId, Long propRevnId);
    
    @Transactional
    @Query(value = "select s from BiographicalSketchEntity s where s.propPrepRevnId = ?1")
    List<BiographicalSketchEntity> getBioSketchesForProposal(Long propRevnId);
    
    @Transactional
    @Query(value = "select s from BiographicalSketchEntity s where s.propPrepRevnId = ?1 and s.person.proposalPersonId = ?2")
    List<BiographicalSketchEntity> getBioSketchesForProposalByPerson(Long propRevnId, Long persId);

}