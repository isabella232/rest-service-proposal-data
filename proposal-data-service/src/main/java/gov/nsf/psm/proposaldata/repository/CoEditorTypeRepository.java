package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.CoEditorType;

@Transactional
public interface CoEditorTypeRepository extends JpaRepository<CoEditorType, String> {

    @Override
    @Cacheable("adviseeTypes")
    List<CoEditorType> findAll();
}
