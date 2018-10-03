package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.CollaborativeType;

public interface CollaborationTypeRepository extends JpaRepository<CollaborativeType, String> {

    @Override
    List<CollaborativeType> findAll();
}
