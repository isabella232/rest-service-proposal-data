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
@Table(name = "prop_rela")
public class RelationshipEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 4101902161872055545L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_rela_id", nullable = false)
    private Long propRelaId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_coa_id")
    private ProposalCOAEntity propCOA;

    @Column(name = "prop_rela_type_code", nullable = true)
    private String propRelaTypeCode;

    @Column(name = "prop_rela_name", nullable = true)
    private String propRelaName;

    @Column(name = "org_affl_name", nullable = true)
    private String orgAfflName;

    @Column(name = "emai_dept_name", nullable = true)
    private String emailDeptName;

    @Column(name = "last_actv_date", nullable = true)
    private Date lastActvDate;

    /**
     * 
     */
    public RelationshipEntity() {
        /**
         * default constructor
         */
    }

    public Long getPropRelaId() {
        return propRelaId;
    }

    public void setPropRelaId(Long propRelaId) {
        this.propRelaId = propRelaId;
    }

    public String getPropRelaTypeCode() {
        return propRelaTypeCode;
    }

    public void setPropRelaTypeCode(String propRelaTypeCode) {
        this.propRelaTypeCode = propRelaTypeCode;
    }

    public String getPropRelaName() {
        return propRelaName;
    }

    public void setPropRelaName(String propRelaName) {
        this.propRelaName = propRelaName;
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
