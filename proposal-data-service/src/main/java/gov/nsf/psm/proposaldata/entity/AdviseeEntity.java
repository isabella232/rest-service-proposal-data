/**
 * 
 */
package gov.nsf.psm.proposaldata.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "prop_phd_advr_adse_info")
public class AdviseeEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 4101902161872055545L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_phd_advr_adse_info_id", nullable = false)
    private Long propPhdAdvrAdseInfoId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_coa_id")
    private ProposalCOAEntity propCOA;

    @Column(name = "prop_advr_adse_type_code", nullable = true)
    private String propPhdAdvrAdseInfoTypeCode;

    @Column(name = "prop_advr_adse_name", nullable = true)
    private String propPhdAdvrAdseInfoName;

    @Column(name = "org_affl_name", nullable = true)
    private String orgAfflName;

    @Column(name = "emai_dept_name", nullable = true)
    private String emailDeptName;

    /**
     * 
     */
    public AdviseeEntity() {
    	super();
    }

    public Long getPropPhdAdvrAdseInfoId() {
        return propPhdAdvrAdseInfoId;
    }

    public void setPropPhdAdvrAdseInfoId(Long propPhdAdvrAdseInfoId) {
        this.propPhdAdvrAdseInfoId = propPhdAdvrAdseInfoId;
    }

    public String getPropPhdAdvrAdseInfoTypeCode() {
        return propPhdAdvrAdseInfoTypeCode;
    }

    public void setPropPhdAdvrAdseInfoTypeCode(String propPhdAdvrAdseInfoTypeCode) {
        this.propPhdAdvrAdseInfoTypeCode = propPhdAdvrAdseInfoTypeCode;
    }

    public String getPropPhdAdvrAdseInfoName() {
        return propPhdAdvrAdseInfoName;
    }

    public void setPropPhdAdvrAdseInfoName(String propPhdAdvrAdseInfoName) {
        this.propPhdAdvrAdseInfoName = propPhdAdvrAdseInfoName;
    }

    public String getOrgAfflName() {
        return orgAfflName;
    }

    public void setOrgAfflName(String orgAfflName) {
        this.orgAfflName = orgAfflName;
    }

    public String getEmailDeptName() {
        return emailDeptName;
    }

    public void setEmailDeptName(String emailDeptName) {
        this.emailDeptName = emailDeptName;
    }

    public ProposalCOAEntity getPropCOA() {
        return propCOA;
    }

    public void setPropCOA(ProposalCOAEntity propCOA) {
        this.propCOA = propCOA;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
