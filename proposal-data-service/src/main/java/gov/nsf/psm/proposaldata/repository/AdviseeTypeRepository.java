package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.AdviseeType;

@Transactional
public interface AdviseeTypeRepository extends JpaRepository<AdviseeType, String> {

    @Override
    @Cacheable("adviseeTypes")
    List<AdviseeType> findAll();
}
