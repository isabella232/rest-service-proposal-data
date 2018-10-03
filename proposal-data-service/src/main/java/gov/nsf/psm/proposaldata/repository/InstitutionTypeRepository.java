package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.InstitutionType;

public interface InstitutionTypeRepository extends JpaRepository<InstitutionType, Long> {

    @Override
    List<InstitutionType> findAll();
}
