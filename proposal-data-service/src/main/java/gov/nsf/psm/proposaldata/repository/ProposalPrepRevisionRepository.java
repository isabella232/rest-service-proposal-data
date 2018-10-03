package gov.nsf.psm.proposaldata.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.nsf.psm.proposaldata.entity.ProposalPrepRevision;

@Transactional
public interface ProposalPrepRevisionRepository extends JpaRepository<ProposalPrepRevision, Long> {

    List<ProposalPrepRevision> findByPropPrepId(Long propPrepId);

    ProposalPrepRevision findByPropPrepRevnId(Long propRevId);

    @Query(value = "select r from ProposalPrepRevision r where r.propPrepId = ?1 and r.propPrepRevnTypeCode='BREV' and r.propPrepSttsCode='05' order by revnNum")
    List<ProposalPrepRevision> getBudgetRevisions(@Param("propPrepId") Long propPrepId);

    @Modifying
    @Query(value = "update ProposalPrepRevision m set m.propTitl =?3 ,m.lastUpdtTmsp=getDate(), m.propTitleUpdateDate=getDate(), m.lastUpdtUser=?4 where m.propPrepId = ?1 and m.propPrepRevnId=?2 ")
    void updateProposalTitle(@Param("propPrepId") Long propPrepId, @Param("propPrepRevnId") Long propPrepRevnId,
            @Param("propTitl") String propTitl, @Param("lastUpdtUser") String lastUpdtUser);

    @Query(value = "select r from ProposalPrepRevision r where r.propPrepRevnId in (select min(rs.propPrepRevnId) from ProposalPrepRevision rs where rs.propPrepId = r.propPrepId) and r.propPrepId = ?1")
    ProposalPrepRevision getMinRevision(@Param("propPrepId") Long propPrepId);

    @Query(value = "select r from ProposalPrepRevision r where r.propPrepRevnId in (select max(rs.propPrepRevnId) from ProposalPrepRevision rs where rs.propPrepId = r.propPrepId) and r.propPrepId = ?1")
    ProposalPrepRevision getMaxRevision(@Param("propPrepId") Long propPrepId);

    @Query(value = "select r from ProposalPrepRevision r where r.propPrepRevnId in (select max(rs.propPrepRevnId) from ProposalPrepRevision rs where rs.propPrepId = r.propPrepId and rs.propPrepSttsCode='05') and r.propPrepId = ?1")
    ProposalPrepRevision getMaxSubmittedRevision(@Param("propPrepId") Long propPrepId);

    @Query(value = "select r.createdDate from ProposalPrepRevision r where r.propPrepId = ?1 and r.propPrepRevnTypeCode = 'ORIG'")
    Date getOriginalSubmittedRevisionCreatedDate(@Param("propPrepId") Long propPrepId);

    @Modifying
    @Query(value = "update ProposalPrepRevision m set m.propPrepSttsCode =?2 , m.totalRqstDolAmt=?4, m.propPrepSttsDate=getDate(), m.lastUpdtTmsp=getDate(), m.lastUpdtUser=?3 , m.propPrepRevnTypeCode=?5 where m.propPrepRevnId=?1 ")
    void updateProposalStatus(@Param("propPrepRevnId") Long propPrepRevnId,
            @Param("propPrepSttsCode") String propPrepSttsCode, @Param("lastUpdtUser") String lastUpdtUser,
            @Param("totalRqstDolAmt") BigDecimal totalRqstDolAmt,
            @Param("propPrepRevnTypeCode") String propPrepRevnTypeCode);

    @Modifying
    @Query(value = "update ProposalPrepRevision m set m.staticPdfPath =?3 ,m.lastUpdtTmsp=getDate(), m.lastUpdtUser=?4 where m.propPrepId = ?1 and m.propPrepRevnId=?2 ")
    void updateProposalPDFStaticPath(@Param("propPrepId") Long propPrepId, @Param("propPrepRevnId") Long propPrepRevnId,
            @Param("staticPdfPath") String staticPdfPath, @Param("lastUpdtUser") String lastUpdtUser);

    @Modifying
    @Query(value = "update ProposalPrepRevision r set r.createdDate=getDate() where r.propPrepId = ?1 and r.propPrepRevnId=?2 ")
    void updateProposalRevisionCreateDate(@Param("propPrepId") Long propPrepId,
            @Param("propPrepRevnId") Long propPrepRevnId);

    @Query(value = "select r from ProposalPrepRevision r where r.propPrepRevnId in (select max(rs.propPrepRevnId) from ProposalPrepRevision rs where rs.propPrepId = r.propPrepId and rs.propPrepSttsCode in ('05')) and r.propPrepId = ?1")
    ProposalPrepRevision getLatestRevision(@Param("propPrepId") Long propPrepId);

    @Query(value = "select r from ProposalPrepRevision r where r.propPrepId = ?1 and r.revnNum = ?2")
    ProposalPrepRevision getRevisionByRevIdAndRevNum(@Param("propPrepId") Long propPrepId,
            @Param("revnNum") Byte revnNum);

    @Transactional
    @Query(value = "select r from ProposalPrepRevision r where r.propPrepRevnId in (select max(rs.propPrepRevnId) from ProposalPrepRevision rs,ProposalPrep p where rs.propPrepId = p.propPrepId and rs.propPrepSttsCode='05' and p.nsfPropId = ?1)")
    ProposalPrepRevision getLatestSubmittedRevision(@Param("nsfPropId") String nsfPropId);

    @Modifying
    @Query(value = "update ProposalPrepRevision r set r.propDueDate =?2 , r.propDueDateTypeCode=?3, r.lastUpdtTmsp=getDate(), r.propDueDateUpdateDate=getDate(), r.lastUpdtUser=?4 where r.propPrepRevnId = ?1 ")
    void updateProposalDeadline(@Param("propPrepRevnId") Long propPrepRevnId, @Param("deadlineDate") Date deadlineDate,
            @Param("deadlineTypeCode") String deadlineTypeCode, @Param("lastUpdtUser") String lastUpdtUser);

}
