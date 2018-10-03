package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "inst_prop_role_type_lkup")
public class InstitutionType extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "inst_prop_role_type_code", nullable = false)
    private String instPropRoleTypeCode;

    @Column(name = "inst_prop_role_type_txt", nullable = false)
    private String instPropRoleTypeTxt;

    protected InstitutionType() {

    }

    public InstitutionType(String instPropRoleTypeCode, String instPropRoleTypeTxt, Date effDate, Date endDate) {
        super();
        this.instPropRoleTypeCode = instPropRoleTypeCode;
        this.instPropRoleTypeTxt = instPropRoleTypeTxt;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);
    }

    public String getInstPropRoleTypeCode() {
        return instPropRoleTypeCode;
    }

    public void setInstPropRoleTypeCode(String instPropRoleTypeCode) {
        this.instPropRoleTypeCode = instPropRoleTypeCode;
    }

    public String getInstPropRoleTypeTxt() {
        return instPropRoleTypeTxt;
    }

    public void setInstPropRoleTypeTxt(String instPropRoleTypeTxt) {
        this.instPropRoleTypeTxt = instPropRoleTypeTxt;
    }

    @Override
    public String toString() {
        return String.format(
                "InstitutionType [instPropRoleTypeCode=%s, instPropRoleTypeTxt=%s, effDate=%s, endDate=%s]",
                instPropRoleTypeCode, instPropRoleTypeTxt, effDate.getTime(), endDate.getTime());
    }

}
