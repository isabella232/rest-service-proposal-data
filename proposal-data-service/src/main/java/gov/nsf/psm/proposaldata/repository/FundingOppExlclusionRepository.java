package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.nsf.psm.proposaldata.entity.FundingOppExlclusionEntity;

public interface FundingOppExlclusionRepository extends JpaRepository<FundingOppExlclusionEntity, Long> {

	@Query(value = "select e  from FundingOppExlclusionEntity e where e.fundOppExclFlag = 1")
	List<FundingOppExlclusionEntity>  getFundingOppExclusionList();
}
