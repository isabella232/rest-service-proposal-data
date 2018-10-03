package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.DeadlineTypeEntity;

public interface DeadlineTypeRepository extends JpaRepository<DeadlineTypeEntity, String> {

    @Override
    List<DeadlineTypeEntity> findAll();

}
