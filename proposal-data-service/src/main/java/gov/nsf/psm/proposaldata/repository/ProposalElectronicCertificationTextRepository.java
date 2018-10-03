package gov.nsf.psm.proposaldata.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.nsf.psm.proposaldata.entity.ProposalElectronicCertificationTextTypeEntity;


public interface ProposalElectronicCertificationTextRepository
        extends JpaRepository<ProposalElectronicCertificationTextTypeEntity, Integer> {

	@Query(value = "select r from ProposalElectronicCertificationTextTypeEntity r where r.electronicCertTypeCode = ?1")
	ProposalElectronicCertificationTextTypeEntity getCertificationText(@Param("electronicCertTypeCode") String electronicCertTypeCode);

	@Query(value = "select r from ProposalElectronicCertificationTextTypeEntity r where r.proposalElectronicCertTypeId = ?1")
	ProposalElectronicCertificationTextTypeEntity getCertificationTextById(@Param("proposalElectronicCertTypeId") int proposalElectronicCertTypeId);

}
