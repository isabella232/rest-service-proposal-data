/**
 * 
 */
package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.nsf.psm.proposaldata.entity.CoverSheetEntity;

/**
 * @author pkatrapa
 *
 */
public interface CoverSheetRepository extends JpaRepository<CoverSheetEntity, Long> {
    CoverSheetEntity findByPropRevId(Long propRevId);
    
    @Modifying
    @Query(value = "update CoverSheetEntity  set awdOrgId=?1, dunsNumber=?2, employerTIN=?3, timeZone=?4, lastUpdtUser=?5, lastUpdtTmsp=getDate() where propRevId=?6 ")
    void updateCoversheet(@Param("awdOrgId") String awdOrgId, @Param("dunsNumber") String dunsNumber,
             @Param("employerTIN") String employerTIN, @Param("timeZone") String timeZone, @Param("lastUpdtUser") String lastUpdtUser,@Param("propRevId") Long propRevId);

}
