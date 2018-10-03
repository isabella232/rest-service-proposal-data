package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.nsf.psm.proposaldata.entity.PPOPEntity;

public interface PPOPRepository extends JpaRepository<PPOPEntity, Long> {

	PPOPEntity findByPropcovrSheetId(Long propcovrSheetId);

	@Modifying
	@Query(value = "delete from PPOPEntity  where propcovrSheetId=?1 ")
	void deletePpop(@Param("propcovrSheetId") Long propcovrSheetId);

}
