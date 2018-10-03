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
@Table(name = "prop_co_edit")
public class CoEditorEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 4101902161872055545L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_co_edit_id", nullable = false)
    private Long propCoEditId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_coa_id")
    private ProposalCOAEntity propCOA;

    @Column(name = "prop_co_edit_type_code", nullable = true)
    private String propCoEditTypeCode;

    @Column(name = "prop_co_edit_name", nullable = true)
    private String propCoEditName;

    @Column(name = "org_affl_name", nullable = true)
    private String orgAfflName;

    @Column(name = "jrnl_affl_name", nullable = true)
    private String jrnlAfflName;

    @Column(name = "last_actv_date", nullable = true)
    private Date lastActvDate;

    /**
     * 
     */
    public CoEditorEntity() {
        /**
         * default constructor
         */
    }

    public Long getPropCoEditId() {
        return propCoEditId;
    }

    public void setPropCoEditId(Long propCoEditId) {
        this.propCoEditId = propCoEditId;
    }

    public ProposalCOAEntity getPropCOA() {
        return propCOA;
    }

    public void setPropCOA(ProposalCOAEntity propCOA) {
        this.propCOA = propCOA;
    }

    public String getPropCoEditTypeCode() {
        return propCoEditTypeCode;
    }

    public void setPropCoEditTypeCode(String propCoEditTypeCode) {
        this.propCoEditTypeCode = propCoEditTypeCode;
    }

    public String getPropCoEditName() {
        return propCoEditName;
    }

    public void setPropCoEditName(String propCoEditName) {
        this.propCoEditName = propCoEditName;
    }

    public String getOrgAfflName() {
        return orgAfflName;
    }

    public void setOrgAfflName(String orgAfflName) {
        this.orgAfflName = orgAfflName;
    }

    public String getJrnlAfflName() {
        return jrnlAfflName;
    }

    public void setJrnlAfflName(String jrnlAfflName) {
        this.jrnlAfflName = jrnlAfflName;
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
