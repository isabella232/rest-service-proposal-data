/**
 * 
 */
package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.OtherFederalAgenciesEntity;

/**
 * @author pkatrapa
 *
 */
public interface OtherFederalAgenciesRepository extends JpaRepository<OtherFederalAgenciesEntity, Long> {
    List<OtherFederalAgenciesEntity> findByPropcovrSheetId(Long propcovrSheetId);

}
