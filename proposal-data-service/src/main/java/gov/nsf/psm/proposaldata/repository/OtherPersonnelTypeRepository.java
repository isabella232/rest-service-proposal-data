package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.OtherPersonnelType;

public interface OtherPersonnelTypeRepository extends JpaRepository<OtherPersonnelType, Long> {

    @Override
    List<OtherPersonnelType> findAll();
}
