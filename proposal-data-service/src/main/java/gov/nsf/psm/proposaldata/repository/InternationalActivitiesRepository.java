/**
 * 
 */
package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.InternationalActivitiesEntity;

/**
 * @author pkatrapa
 *
 */
public interface InternationalActivitiesRepository extends JpaRepository<InternationalActivitiesEntity, Long> {
    List<InternationalActivitiesEntity> findByPropcovrSheetId(Long propcovrSheetId);

}
