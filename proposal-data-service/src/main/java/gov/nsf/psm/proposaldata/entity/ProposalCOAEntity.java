package gov.nsf.psm.proposaldata.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

import gov.nsf.psm.proposaldata.entity.parameter.ProposalCOAParameter;

@Entity
@Table(name = "prop_coa")
public class ProposalCOAEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_coa_id", nullable = false)
    private Long propCOAId;

    @Column(name = "prop_prep_revn_id", nullable = false)
    private Long propPrepRevnId;

    @Column(name = "prop_pers_id", nullable = false)
    private Long propPersId;

    @Column(name = "coa_excl_file_loc", nullable = false)
    private String coaExclFileLoc;

    @Column(name = "coa_excl_orig_file_name", nullable = false)
    private String coaExclOrigFileName;

    @Column(name = "coa_pdf_file_loc", nullable = false)
    private String coaPdfFileLoc;

    @Column(name = "coa_pdf_file_name", nullable = false)
    private String coaPdfFileName;

    @Column(name = "coa_pdf_page_cnt", nullable = false)
    private Integer coaPdfPageCount;
    
    @OneToOne(orphanRemoval = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_pers_id", nullable = false, insertable = false, updatable = false)
    private ProposalPersonsEntity person;

    @OneToMany(mappedBy = "propCOA", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = AdviseeEntity.class)
    private List<AdviseeEntity> advisees;

    @OneToMany(mappedBy = "propCOA", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = AffiliationEntity.class)
    private List<AffiliationEntity> affiliations;

    @OneToMany(mappedBy = "propCOA", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = CoEditorEntity.class)
    private List<CoEditorEntity> coeditors;

    @OneToMany(mappedBy = "propCOA", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = CollaboratorEntity.class)
    private List<CollaboratorEntity> collaborators;

    @OneToMany(mappedBy = "propCOA", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = RelationshipEntity.class)
    private List<RelationshipEntity> relationships;

    public ProposalCOAEntity() {
    	super();
    }

    public ProposalCOAEntity(ProposalCOAParameter param) {
        this.propCOAId = param.getPropCOAId();
        this.propPrepRevnId = param.getPropPrepRevnId();
        this.propPersId = param.getPropPersId();
        this.coaExclFileLoc = param.getCoaExclFileLoc();
        this.coaExclOrigFileName = param.getCoaExclOrigFileName();
        this.coaPdfFileLoc = param.getCoaPdfFileLoc();
        this.coaPdfFileName = param.getCoaPdfFileName();
        this.lastUpdtUser = param.getLastUpdateUser();
        this.lastUpdtPgm = param.getLastUpdatePgm();
        this.lastUpdtTmsp = (Date) ObjectUtils.clone(param.getLastUpdateTmsp());
    }

    public Long getPropCOAId() {
        return propCOAId;
    }

    public void setPropCOAId(Long propCOAId) {
        this.propCOAId = propCOAId;
    }

    public Long getPropPrepRevnId() {
        return propPrepRevnId;
    }

    public void setPropPrepRevnId(Long propPrepRevnId) {
        this.propPrepRevnId = propPrepRevnId;
    }

    public Long getPropPersId() {
        return propPersId;
    }

    public void setPropPersId(Long propPersId) {
        this.propPersId = propPersId;
    }

    public String getCoaExclFileLoc() {
        return coaExclFileLoc;
    }

    public void setCoaExclFileLoc(String coaExclFileLoc) {
        this.coaExclFileLoc = coaExclFileLoc;
    }

    public String getCoaExclOrigFileName() {
        return coaExclOrigFileName;
    }

    public void setCoaExclOrigFileName(String coaExclOrigFileName) {
        this.coaExclOrigFileName = coaExclOrigFileName;
    }

    public String getCoaPdfFileLoc() {
        return coaPdfFileLoc;
    }

    public void setCoaPdfFileLoc(String coaPdfFileLoc) {
        this.coaPdfFileLoc = coaPdfFileLoc;
    }

    public String getCoaPdfFileName() {
        return coaPdfFileName;
    }

    public void setCoaPdfFileName(String coaPdfFileName) {
        this.coaPdfFileName = coaPdfFileName;
    }

    public Integer getCoaPdfPageCount() {
        return coaPdfPageCount;
    }

    public void setCoaPdfPageCount(Integer coaPdfPageCount) {
        this.coaPdfPageCount = coaPdfPageCount;
    }

    public List<AdviseeEntity> getAdvisees() {
        return advisees;
    }

    public void setAdvisees(List<AdviseeEntity> advisees) {
        this.advisees = advisees;
    }

    public List<AffiliationEntity> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<AffiliationEntity> affiliations) {
        this.affiliations = affiliations;
    }

    public List<CoEditorEntity> getCoeditors() {
        return coeditors;
    }

    public void setCoeditors(List<CoEditorEntity> coeditors) {
        this.coeditors = coeditors;
    }

    public List<CollaboratorEntity> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<CollaboratorEntity> collaborators) {
        this.collaborators = collaborators;
    }

    public List<RelationshipEntity> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<RelationshipEntity> relationships) {
        this.relationships = relationships;
    }
    
    public ProposalPersonsEntity getPerson() {
        return person;
    }

    public void setPerson(ProposalPersonsEntity person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return String.format(
                "ProposalPrepRevision [propCOAId=%s, propPrepRevnId=%s, propPersId=%s, coaExclFileLoc=%s, coaExclOrigFileName=%s, coaPdfFileLoc=%s, coaPdfPageCount=%s, coaPdfFileName=%s, lastUpdtPgm=%s, lastUpdtUser=%s, lastUpdtTmsp=%s]",
                propCOAId, propPrepRevnId, propPersId, coaExclFileLoc, coaExclOrigFileName, coaPdfFileLoc,
                coaPdfPageCount, coaPdfFileName, lastUpdtPgm, lastUpdtUser, lastUpdtTmsp);
    }
}
