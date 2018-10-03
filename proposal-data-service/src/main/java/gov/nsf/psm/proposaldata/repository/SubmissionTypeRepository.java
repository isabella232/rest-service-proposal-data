package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.SubmissionType;

public interface SubmissionTypeRepository extends JpaRepository<SubmissionType, String> {

    @Override
    List<SubmissionType> findAll();

}
