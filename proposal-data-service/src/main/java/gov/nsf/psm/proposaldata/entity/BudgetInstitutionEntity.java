package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "prop_inst")
public class BudgetInstitutionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_inst_rec_id", nullable = false)
    private Long propInstRecId;

    @Column(name = "prop_prep_revn_id", nullable = false)
    private Long propRevId;

    @Column(name = "inst_id", nullable = false)
    private String instId;

    @Column(name = "inst_prop_role_type_code", nullable = false)
    private String instPropRoleTypeCode;

    @Type(type = "boolean")
    @Column(name = "pcv_ck_ind", nullable = false)
    private boolean pcvCheckIndicator;

    public BudgetInstitutionEntity() {
        /**
         * default constructor
         */
    }

    public BudgetInstitutionEntity(Long propRevId, String instId, String instPropRoleTypeCode) {
        super();
        this.propRevId = propRevId;
        this.instId = instId;
        this.instPropRoleTypeCode = instPropRoleTypeCode;
    }

    public Long getPropInstRecId() {
        return propInstRecId;
    }

    public void setPropInstRecId(Long propInstRecId) {
        this.propInstRecId = propInstRecId;
    }

    public Long getPropRevId() {
        return propRevId;
    }

    public void setPropRevId(Long propRevId) {
        this.propRevId = propRevId;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getInstPropRoleTypeCode() {
        return instPropRoleTypeCode;
    }

    public void setInstPropRoleTypeCode(String instPropRoleTypeCode) {
        this.instPropRoleTypeCode = instPropRoleTypeCode;
    }

    /**
     * @return the pcvCheckIndicator
     */
    public boolean isPcvCheckIndicator() {
        return this.pcvCheckIndicator;
    }

    /**
     * @param pcvCheckIndicator
     *            the pcvCheckIndicator to set
     */
    public void setPcvCheckIndicator(boolean pcvCheckIndicator) {
        this.pcvCheckIndicator = pcvCheckIndicator;
    }

}
