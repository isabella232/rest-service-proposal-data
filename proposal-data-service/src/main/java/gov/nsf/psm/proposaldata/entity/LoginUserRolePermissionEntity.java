package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "prop_pmsn_type_lkup")
public class LoginUserRolePermissionEntity extends BaseType {

    @Id
    @Column(name = "prop_pmsn_type_code")
    private String propPermCode;

    @Column(name = "pmsn_type_txt")
    private String permDescription;

    @Column(name = "pmsn_type_appl_parm")
    private String permApplParam;

    public String getPropPermCode() {
        return propPermCode;
    }

    public void setPropPermCode(String propPermCode) {
        this.propPermCode = propPermCode;
    }

    public String getPermDescription() {
        return permDescription;
    }

    public void setPermDescription(String permDescription) {
        this.permDescription = permDescription;
    }

    public String getPermApplParam() {
        return permApplParam;
    }

    public void setPermApplParam(String permApplParam) {
        this.permApplParam = permApplParam;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
