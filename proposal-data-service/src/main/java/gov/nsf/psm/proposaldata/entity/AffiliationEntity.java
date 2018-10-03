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
@Table(name = "prop_name_affl")
public class AffiliationEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 4101902161872055545L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_name_affl_id", nullable = false)
    private Long propNameAfflId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_coa_id")
    private ProposalCOAEntity propCOA;

    @Column(name = "prop_sr_pers_name", nullable = true)
    private String srPersName;

    @Column(name = "org_affl_name", nullable = true)
    private String orgAfflName;

    @Column(name = "last_actv_date", nullable = true)
    private Date lastActvDate;

    /**
     * 
     */
    public AffiliationEntity() {
        /**
         * default constructor
         */
    }

    public Long getPropNameAfflId() {
        return propNameAfflId;
    }

    public void setPropNameAfflId(Long propNameAfflId) {
        this.propNameAfflId = propNameAfflId;
    }

    public ProposalCOAEntity getPropCOA() {
        return propCOA;
    }

    public void setPropCOA(ProposalCOAEntity propCOA) {
        this.propCOA = propCOA;
    }

    public String getSrPersName() {
        return srPersName;
    }

    public void setSrPersName(String srPersName) {
        this.srPersName = srPersName;
    }

    public String getOrgAfflName() {
        return orgAfflName;
    }

    public void setOrgAfflName(String orgAfflName) {
        this.orgAfflName = orgAfflName;
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
