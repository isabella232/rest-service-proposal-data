package gov.nsf.psm.proposaldata.entity;

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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import gov.nsf.psm.proposaldata.entity.parameter.ProposalPrepParameter;

@Entity
@NamedEntityGraph(name = "ProposalPrep.revisions", attributeNodes = @NamedAttributeNode("revisions"))
@Table(name = "prop_prep")
public class ProposalPrep extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_prep_id", nullable = false)
    private Long propPrepId;

    @Column(name = "prop_subm_type_code", nullable = false)
    private String propSubmTypeCode;

    @Column(name = "prop_type_code", nullable = false)
    private String propTypeCode;

    @Column(name = "fund_opp_id", nullable = false)
    private String fundOppId;

    @Column(name = "prop_intt_date", nullable = false)
    private Date propInttDate;

    @Column(name = "prop_intt_nsf_id", nullable = false)
    private String propInttNsfId;

    @Column(name = "nsf_prop_id")
    private String nsfPropId;

    @Column(name = "prop_colb_type_code", nullable = false)
    private String propColbTypeCode;

    @Column(name = "nsf_prop_subm_date")
    private Date nsfPropSubmDate;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_prep_id", nullable = false)
    private Set<UnitOfConsideration> uocs;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_prep_id", nullable = false)
    private Set<ProposalPrepRevision> revisions;

    public ProposalPrep() {
        /**
         * Default constructor
         */
    }

    public ProposalPrep(Long propPrepId, ProposalPrepParameter param) {
        super();
        this.propPrepId = propPrepId;
        this.propSubmTypeCode = param.getSubmTypeCode();
        this.propTypeCode = param.getTypeCode();
        this.fundOppId = param.getFundOpporId();
        this.propInttDate = (Date) ObjectUtils.clone(param.getInttDate());
        this.propInttNsfId = param.getInttNsfId();
        this.nsfPropId = param.getNsfProposalId();
        this.nsfPropSubmDate = (Date) ObjectUtils.clone(param.getNsfProposalSubmDate());
        this.lastUpdtUser = param.getLastUpdateUser();
        this.lastUpdtPgm = param.getLastUpdatePgm();
        this.lastUpdtTmsp = (Date) ObjectUtils.clone(param.getLastUpdateTmsp());
        this.propColbTypeCode = param.getColbTypeCode();

    }

    public ProposalPrep(Long propPrepId, String propSubmTypeCode, String propTypeCode, String fundOppId,
            Set<UnitOfConsideration> uocs, Set<ProposalPrepRevision> revisions) {
        super();
        this.propPrepId = propPrepId;
        this.propSubmTypeCode = propSubmTypeCode;
        this.propTypeCode = propTypeCode;
        this.fundOppId = fundOppId;
        this.uocs = uocs;
        this.revisions = revisions;
    }

    public String getPropColbTypeCode() {
        return propColbTypeCode;
    }

    public void setPropColbTypeCode(String propColbTypeCode) {
        this.propColbTypeCode = propColbTypeCode;
    }

    public Long getPropPrepId() {
        return propPrepId;
    }

    public void setPropPrepId(Long propPrepId) {
        this.propPrepId = propPrepId;
    }

    public String getPropSubmTypeCode() {
        return propSubmTypeCode;
    }

    public void setPropSubmTypeCode(String propSubmTypeCode) {
        this.propSubmTypeCode = propSubmTypeCode;
    }

    public String getPropTypeCode() {
        return propTypeCode;
    }

    public void setPropTypeCode(String propTypeCode) {
        this.propTypeCode = propTypeCode;
    }

    public String getFundOppId() {
        return fundOppId;
    }

    public void setFundOppId(String fundOppId) {
        this.fundOppId = fundOppId;
    }

    public Date getPropInttDate() {
        return (Date) ObjectUtils.clone(propInttDate);
    }

    public void setPropInttDate(Date propInttDate) {
        this.propInttDate = (Date) ObjectUtils.clone(propInttDate);
    }

    public String getPropInttNsfId() {
        return propInttNsfId;
    }

    public void setPropInttNsfId(String propInttNsfId) {
        this.propInttNsfId = propInttNsfId;
    }

    public String getNsfPropId() {
        return nsfPropId;
    }

    public void setNsfPropId(String nsfPropId) {
        this.nsfPropId = nsfPropId;
    }

    public Date getNsfPropSubmDate() {
        return (Date) ObjectUtils.clone(nsfPropSubmDate);
    }

    public void setNsfPropSubmDate(Date nsfPropSubmDate) {
        this.nsfPropSubmDate = (Date) ObjectUtils.clone(nsfPropSubmDate);
    }

    public Set<UnitOfConsideration> getUocs() {
        return uocs;
    }

    public void setUocs(Set<UnitOfConsideration> uocs) {
        this.uocs = uocs;
    }

    public Set<ProposalPrepRevision> getRevisions() {
        return revisions;
    }

    public void setRevisions(Set<ProposalPrepRevision> revisions) {
        this.revisions = revisions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
