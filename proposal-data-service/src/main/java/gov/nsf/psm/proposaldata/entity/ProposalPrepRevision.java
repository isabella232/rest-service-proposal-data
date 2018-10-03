package gov.nsf.psm.proposaldata.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

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
import javax.persistence.Transient;

import org.apache.commons.lang.ObjectUtils;

import gov.nsf.psm.proposaldata.entity.parameter.ProposalPrepRevisionParameter;

@Entity
@Table(name = "prop_prep_revn")
public class ProposalPrepRevision extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_prep_revn_id", nullable = false)
    private Long propPrepRevnId;

    @Column(name = "prop_prep_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepId;

    @Column(name = "revn_num", nullable = false)
    private Byte revnNum;

    @Column(name = "prop_titl", nullable = false)
    private String propTitl;

    @Column(name = "prop_prep_revn_type_code", nullable = false)
    private String propPrepRevnTypeCode;

    @Column(name = "prop_prep_stts_code", nullable = false)
    private String propPrepSttsCode;

    @Column(name = "prop_prep_stts_date", nullable = false)
    private Date propPrepSttsDate;

    @Column(name = "cre_date", nullable = false)
    private Date createdDate;

    @Column(name = "cre_user", nullable = false)
    private String createdUser;

    @Column(name = "sttc_pdf_path")
    private String staticPdfPath;

    @Column(name = "tot_rqst_dol_amt")
    private BigDecimal totalRqstDolAmt;

    @Column(name = "curr_pappg_vers", nullable = false)
    private String currPappgVers;

    @Column(name = "prop_due_date")
    private Date propDueDate;

    @Column(name = "prop_due_date_type_code")
    private String propDueDateTypeCode;

    @Column(name = "prop_due_date_updt_date", nullable = false)
    private Date propDueDateUpdateDate;

    @Column(name = "prop_titl_updt_date", nullable = false)
    private Date propTitleUpdateDate;

    @OneToOne(orphanRemoval = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_due_date_type_code", nullable = false, insertable = false, updatable = false)
    private DeadlineTypeEntity deadlineType;

    @OneToMany(orphanRemoval = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_prep_revn_id", insertable = false, updatable = false)
    private Set<ProposalPersonsEntity> persons;

    @OneToMany(orphanRemoval = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_prep_revn_id", insertable = false, updatable = false)
    private Set<BudgetInstitutionEntity> institutions;

    public ProposalPrepRevision() {
        /**
         * Default constructor
         */
    }

    public ProposalPrepRevision(ProposalPrepRevisionParameter param) {
        this.revnNum = param.getRevisionNumber();
        this.propTitl = param.getPropTitle();
        this.propPrepRevnTypeCode = param.getPrepRevnTypeCode();
        this.propPrepSttsCode = param.getPrepSttsCode();
        this.propPrepSttsDate = (Date) ObjectUtils.clone(param.getPrepSttsDate());
        this.lastUpdtUser = param.getLastUpdateUser();
        this.lastUpdtPgm = param.getLastUpdatePgm();
        this.lastUpdtTmsp = (Date) ObjectUtils.clone(param.getLastUpdateTmsp());
        this.staticPdfPath = param.getStaticPdfPath();
    }

    public Long getPropPrepRevnId() {
        return propPrepRevnId;
    }

    public void setPropPrepRevnId(Long propPrepRevnId) {
        this.propPrepRevnId = propPrepRevnId;
    }

    public Long getPropPrepId() {
        return propPrepId;
    }

    public void setPropPrepId(Long propPrepId) {
        this.propPrepId = propPrepId;
    }

    public Byte getRevnNum() {
        return revnNum;
    }

    public void setRevnNum(Byte revnNum) {
        this.revnNum = revnNum;
    }

    public String getPropTitl() {
        return propTitl;
    }

    public void setPropTitl(String propTitl) {
        this.propTitl = propTitl;
    }

    public String getPropPrepRevnTypeCode() {
        return propPrepRevnTypeCode;
    }

    public void setPropPrepRevnTypeCode(String propPrepRevnTypeCode) {
        this.propPrepRevnTypeCode = propPrepRevnTypeCode;
    }

    public String getPropPrepSttsCode() {
        return propPrepSttsCode;
    }

    public void setPropPrepSttsCode(String propPrepSttsCode) {
        this.propPrepSttsCode = propPrepSttsCode;
    }

    public Date getPropPrepSttsDate() {
        return (Date) ObjectUtils.clone(this.propPrepSttsDate);
    }

    public void setPropPrepSttsDate(Date propPrepSttsDate) {
        this.propPrepSttsDate = (Date) ObjectUtils.clone(propPrepSttsDate);
    }

    public Set<ProposalPersonsEntity> getPersons() {
        return persons;
    }

    public void setPersons(Set<ProposalPersonsEntity> persons) {
        this.persons = persons;
    }

    @Transient
    public Set<BudgetInstitutionEntity> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(Set<BudgetInstitutionEntity> institutions) {
        this.institutions = institutions;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return (Date) ObjectUtils.clone(this.createdDate);
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = (Date) ObjectUtils.clone(createdDate);
    }

    /**
     * @return the createdUser
     */
    public String getCreatedUser() {
        return this.createdUser;
    }

    /**
     * @param createdUser
     *            the createdUser to set
     */
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getStaticPdfPath() {
        return staticPdfPath;
    }

    public void setStaticPdfPath(String staticPdfPath) {
        this.staticPdfPath = staticPdfPath;
    }

    /**
     * @return the totalRqstDolAmt
     */
    public BigDecimal getTotalRqstDolAmt() {
        return this.totalRqstDolAmt;
    }

    /**
     * @param totalRqstDolAmt
     *            the totalRqstDolAmt to set
     */
    public void setTotalRqstDolAmt(BigDecimal totalRqstDolAmt) {
        this.totalRqstDolAmt = totalRqstDolAmt;
    }

    /**
     * @return the currPappgVers
     */
    public String getCurrPappgVers() {
        return this.currPappgVers;
    }

    /**
     * @param currPappgVers
     *            the currPappgVers to set
     */
    public void setCurrPappgVers(String currPappgVers) {
        this.currPappgVers = currPappgVers;
    }

    /**
     * @return the propDueDate
     */
    public Date getPropDueDate() {
        return (Date) ObjectUtils.clone(this.propDueDate);
    }

    /**
     * @param propDueDate
     *            the propDueDate to set
     */
    public void setPropDueDate(Date propDueDate) {
        this.propDueDate = (Date) ObjectUtils.clone(propDueDate);
    }

    /**
     * @return the propDueDateTypeCode
     */
    public String getPropDueDateTypeCode() {
        return this.propDueDateTypeCode;
    }

    /**
     * @param propDueDateTypeCode
     *            the propDueDateTypeCode to set
     */
    public void setPropDueDateTypeCode(String propDueDateTypeCode) {
        this.propDueDateTypeCode = propDueDateTypeCode;
    }

    /**
     * @return the propDueDateUpdateDate
     */
    public Date getPropDueDateUpdateDate() {
        return (Date) ObjectUtils.clone(this.propDueDateUpdateDate);
    }

    /**
     * @param propDueDateUpdateDate
     *            the propDueDateUpdateDate to set
     */
    public void setPropDueDateUpdateDate(Date propDueDateUpdateDate) {
        this.propDueDateUpdateDate = (Date) ObjectUtils.clone(propDueDateUpdateDate);
    }

    /**
     * @return the propTitleUpdateDate
     */
    public Date getPropTitleUpdateDate() {
        return (Date) ObjectUtils.clone(this.propTitleUpdateDate);
    }

    /**
     * @param propTitleUpdateDate
     *            the propTitleUpdateDate to set
     */
    public void setPropTitleUpdateDate(Date propTitleUpdateDate) {
        this.propTitleUpdateDate = (Date) ObjectUtils.clone(propTitleUpdateDate);
    }

    public DeadlineTypeEntity getDeadlineType() {
        return deadlineType;
    }

    public void setDeadlineType(DeadlineTypeEntity deadlineType) {
        this.deadlineType = deadlineType;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "ProposalPrepRevision [propPrepRevnId=%s, propPrepId=%s, revnNum=%s, propTitl=%s, propPrepRevnTypeCode=%s, propPrepSttsCode=%s, propPrepSttsDate=%s, createdDate=%s, createdUser=%s, staticPdfPath=%s, totalRqstDolAmt=%s, currPappgVers=%s, propDueDate=%s, propDueDateTypeCode=%s, propDueDateUpdateDate=%s, propTitleUpdateDate=%s, persons=%s, institutions=%s]",
                this.propPrepRevnId, this.propPrepId, this.revnNum, this.propTitl, this.propPrepRevnTypeCode,
                this.propPrepSttsCode, this.propPrepSttsDate, this.createdDate, this.createdUser, this.staticPdfPath,
                this.totalRqstDolAmt, this.currPappgVers, this.propDueDate, this.propDueDateTypeCode,
                this.propDueDateUpdateDate, this.propTitleUpdateDate, this.persons, this.institutions);
    }

}
