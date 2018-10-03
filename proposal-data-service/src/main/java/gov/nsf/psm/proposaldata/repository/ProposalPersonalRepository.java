package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.nsf.psm.proposaldata.entity.ProposalPersonsEntity;

public interface ProposalPersonalRepository extends JpaRepository<ProposalPersonsEntity, Long> {
    List<ProposalPersonsEntity> findByPropPrepRevisionId(Long propRevId);

    ProposalPersonsEntity findByProposalPersonId(Long propPersId);

    @Modifying
    @Query(value = "update ProposalPersonsEntity m set m.propPersonRoleCode =?1 , m.lastUpdtUser= ?2 , m.lastUpdtTmsp=getDate() where m.proposalPersonId = ?3 ")
    void updateSeniorPersonnelRole(@Param("propPersonRoleCode") String propPersonRoleCode,
            @Param("propPersonNsfId") String propPersonNsfId, @Param("proposalPersonId") Long proposalPersonId);

    @Query(value = "select p from ProposalPersonsEntity p where p.propPrepRevisionId = ?1 and p.propPersonRoleCode = ?2 ")
    ProposalPersonsEntity getSeniorPersonnelByRole(@Param("propPrepRevisionId") Long propPrepRevisionId,
            @Param("propPersonRoleCode") String propPersonRoleCode);
    
    @Query(value = "select p from ProposalPersonsEntity p where p.propPrepRevisionId = ?1 and p.propPersonNsfId = ?2 and p.propPersonRoleCode = ?3 ")
    ProposalPersonsEntity getSeniorPersonnelByNsfIdAndRole(@Param("propPrepRevisionId") Long propPrepRevisionId, @Param("propPersonNsfId") String propPersonNsfId,
            @Param("propPersonRoleCode") String propPersonRoleCode);
    
    @Query(value = "select count(*) from ProposalPersonsEntity p where p.propPrepRevisionId = ?1")
    long getCount(@Param("propPrepRevisionId") Long propPrepRevisionId);
    
    @Query(value = "select count(*) from ProposalPersonsEntity p where p.propPrepRevisionId = ?1 and (p.propPersonRoleCode = '01' or p.propPersonRoleCode = '02' or p.propPersonRoleCode = '03')")
    long getCountSrPersonnel(@Param("propPrepRevisionId") Long propPrepRevisionId);
    
    @Modifying
    @Query(value = "update ProposalPersonsEntity m set m.propPersInstitutionId=?1 ,m.lastUpdtUser= ?2 , m.lastUpdtTmsp=getDate() where m.propPrepRevisionId= ?3 and m.propPersonRoleCode= ?4 ")
    void updatePIInstitution(@Param("propPersInstitutionId") String propPersInstitutionId,    @Param("lastUpdtUser") String lastUpdtUser, 
    		@Param("propPrepRevisionId") Long propPrepRevisionId, @Param("propPersonRoleCode") String propPersonRoleCode );
}
