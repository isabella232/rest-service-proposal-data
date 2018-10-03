package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.nsf.psm.proposaldata.entity.ProposalCOAEntity;

@Transactional
public interface ProposalCOARepository extends JpaRepository<ProposalCOAEntity, Long> {

    @Transactional
    @Query(value = "select c from ProposalCOAEntity c where c.propPrepRevnId = ?1 and c. propPersId = ?2")
    ProposalCOAEntity findByPersIdAndRevnId(Long revnId, Long persId);

    @Transactional
    @Query(value = "select c from ProposalCOAEntity c where c.lastUpdtTmsp in (select max(c.lastUpdtTmsp) from ProposalCOAEntity c where c.propPrepRevnId = ?1)")
    ProposalCOAEntity findLatestProposalCOA(Long propRevnId);
    
    @Transactional
    @Query(value = "select c from ProposalCOAEntity c where c.propPrepRevnId = ?1")
    List<ProposalCOAEntity> getCOAsForProposal(Long propRevnId);
    
    @Transactional
    @Query(value = "select c from ProposalCOAEntity c where c.propPrepRevnId = ?1 and c.person.proposalPersonId = ?2")
    List<ProposalCOAEntity> getCOAsForProposalByPerson(Long propRevnId, Long persId);
    
    @Modifying
    @Transactional
    @Query(value = "delete from AdviseeEntity a where a.propCOA = ?1")
    void deleteAdvisees(ProposalCOAEntity coa);
    
    @Modifying
    @Transactional
    @Query(value = "delete from AffiliationEntity a where a.propCOA = ?1")
    void deleteAffiliations(ProposalCOAEntity coa);
    
    @Modifying
    @Transactional
    @Query(value = "delete from CoEditorEntity c where c.propCOA = ?1")
    void deleteCoEditors(ProposalCOAEntity coa);
    
    @Modifying
    @Transactional
    @Query(value = "delete from CollaboratorEntity c where c.propCOA = ?1")
    void deleteCollaborators(ProposalCOAEntity coa);
    
    @Modifying
    @Transactional
    @Query(value = "delete from RelationshipEntity r where r.propCOA = ?1")
    void deleteRelationships(ProposalCOAEntity coa);

}