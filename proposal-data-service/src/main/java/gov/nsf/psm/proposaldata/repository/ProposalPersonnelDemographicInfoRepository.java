package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.nsf.psm.proposaldata.entity.ProposalPersonnelDemographicInfoEntity;

public interface ProposalPersonnelDemographicInfoRepository
        extends JpaRepository<ProposalPersonnelDemographicInfoEntity, Integer> {

	@Query(value = "select p from ProposalPersonnelDemographicInfoEntity p where p.propPersId = ?1 ")
	ProposalPersonnelDemographicInfoEntity getPersonnelDemographicEntityByPersId(@Param("propPersId") Integer propPersId);
}
