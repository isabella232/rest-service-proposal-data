package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.RelationshipType;

@Transactional
public interface RelationshipTypeRepository extends JpaRepository<RelationshipType, String> {

    @Override
    @Cacheable("adviseeTypes")
    List<RelationshipType> findAll();
}
