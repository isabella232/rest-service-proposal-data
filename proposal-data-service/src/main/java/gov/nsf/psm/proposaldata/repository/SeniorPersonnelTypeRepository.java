package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.SeniorPersonnelType;

public interface SeniorPersonnelTypeRepository extends JpaRepository<SeniorPersonnelType, Long> {

    @Override
    List<SeniorPersonnelType> findAll();

}
