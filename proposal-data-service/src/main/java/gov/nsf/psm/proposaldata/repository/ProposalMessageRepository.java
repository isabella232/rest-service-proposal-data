package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.nsf.psm.proposaldata.entity.ProposalMessageEntity;

public interface ProposalMessageRepository extends JpaRepository<ProposalMessageEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from ProposalMessageEntity m where m.propPrepId = ?1 and m.propRevnId = ?2 and propSectionType = ?3")
    void delete(@Param("propPrepId") Integer propPrepId, @Param("propRevnId") Integer propRevnId,
            @Param("propSectionType") String propSectionType);

    List<ProposalMessageEntity> findByPropPrepId(int propPrepId);

    @Transactional
    @Query(value = "select m from ProposalMessageEntity m where m.propPrepId = ?1 and m.propRevnId = ?2 and propSectionType = ?3")
    List<ProposalMessageEntity> findByPrepIdRevIdSectionCode(@Param("propPrepId") Integer propPrepId,
            @Param("propRevnId") Integer propRevnId, @Param("propSectionType") String propSectionType);

    @Transactional
    @Query(value = "select m from ProposalMessageEntity m where m.propRevnId = ?1 and m.propPersId = ?2 and propSectionType = ?3")
    List<ProposalMessageEntity> findMsgForSrPerson(@Param("propRevnId") Integer propPrepId,
            @Param("propPersId") Integer propPersId, @Param("propSectionType") String propSectionType);

    @Transactional
    @Query(value = "select m from ProposalMessageEntity m where m.propRevnId = ?1")
    List<ProposalMessageEntity> findByPrepIdRevId(@Param("propRevnId") Integer propRevnId);

    @Modifying
    @Transactional
    @Query(value = "delete from ProposalMessageEntity m where m.propRevnId = ?1 and m.propPersId = ?2 and propSectionType = ?3")
    void deleteSrPerson(@Param("propRevnId") Integer propRevnId, @Param("propPersId") Integer propPersId,
            @Param("propSectionType") String propSectionType);

    @Transactional
    @Query(value = "select m from ProposalMessageEntity m where m.propPrepId = ?1 and m.propRevnId = ?2 and m.psmMsgLevel='w' and m.propSectionType not in ('01')")
    List<ProposalMessageEntity> findOnlyWarningMessages(@Param("propPrepId") Integer propPrepId,
            @Param("propRevnId") Integer propRevnId);

}
