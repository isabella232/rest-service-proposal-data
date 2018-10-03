package gov.nsf.psm.proposaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.InstitutionAddressEntity;

public interface InstitutionAddressRepository extends JpaRepository<InstitutionAddressEntity, Integer> {

}
