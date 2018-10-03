package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.CollaboratorType;

public interface CollaboratorTypeRepository extends JpaRepository<CollaboratorType, String> {

    @Override
    List<CollaboratorType> findAll();
}
