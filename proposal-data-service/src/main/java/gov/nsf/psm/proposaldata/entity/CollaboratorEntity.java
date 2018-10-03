/**
 * 
 */
package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "prop_clbr")
public class CollaboratorEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 4101902161872055545L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_clbr_id", nullable = false)
    private Long propClbrId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_coa_id")
    private ProposalCOAEntity propCOA;

    @Column(name = "prop_clbr_type_code", nullable = true)
    private String propClbrTypeCode;

    @Column(name = "prop_clbr_name", nullable = true)
    private String propClbrName;

    @Column(name = "org_affl_name", nullable = true)
    private String orgAfflName;

    @Column(name = "emai_dept_name", nullable = true)
    private String emailDeptName;

    @Column(name = "last_actv_date", nullable = true)
    private Date lastActvDate;

    /**
     * 
     */
    public CollaboratorEntity() {
        /**
         * default constructor
         */
    }

    public Long getPropClbrId() {
        return propClbrId;
    }

    public void setPropClbrId(Long propClbrId) {
        this.propClbrId = propClbrId;
    }

    public ProposalCOAEntity getPropCOA() {
        return propCOA;
    }

    public void setPropCOA(ProposalCOAEntity propCOA) {
        this.propCOA = propCOA;
    }

    public String getPropClbrTypeCode() {
        return propClbrTypeCode;
    }

    public void setPropClbrTypeCode(String propClbrTypeCode) {
        this.propClbrTypeCode = propClbrTypeCode;
    }

    public String getPropClbrName() {
        return propClbrName;
    }

    public void setPropClbrName(String propClbrName) {
        this.propClbrName = propClbrName;
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

    public Date getLastActvDate() {
        return (Date) ObjectUtils.clone(this.lastActvDate);
    }

    public void setLastActvDate(Date lastActvDate) {
        this.lastActvDate = (Date) ObjectUtils.clone(lastActvDate);
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
