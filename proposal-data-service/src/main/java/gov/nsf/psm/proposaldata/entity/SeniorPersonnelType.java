package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_pers_role_lkup")
public class SeniorPersonnelType extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "prop_pers_role_code", nullable = false)
    private String propPersRoleCode;

    @Column(name = "prop_pers_role_txt", nullable = false)
    private String propPersRoleTxt;

    @Column(name = "prop_pers_role_abbr", nullable = false)
    private String propPersRoleAbbr;

    @Column(name = "user_data_serv_role", nullable = false)
    private String userDataServiceRole;

    protected SeniorPersonnelType() {

    }

    public SeniorPersonnelType(String propPersRoleCode, String propPersRoleTxt, String propPersRoleAbbr, Date effDate,
            Date endDate, String userDataServiceRole) {
        super();
        this.propPersRoleCode = propPersRoleCode;
        this.propPersRoleTxt = propPersRoleTxt;
        this.propPersRoleAbbr = propPersRoleAbbr;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);
        this.userDataServiceRole = userDataServiceRole;
    }

    public String getUserDataServiceRole() {
        return userDataServiceRole;
    }

    public void setUserDataServiceRole(String userDataServiceRole) {
        this.userDataServiceRole = userDataServiceRole;
    }

    public String getPropPersRoleCode() {
        return propPersRoleCode;
    }

    public void setPropPersRoleCode(String propPersRoleCode) {
        this.propPersRoleCode = propPersRoleCode;
    }

    public String getPropPersRoleTxt() {
        return propPersRoleTxt;
    }

    public void setPropPersRoleTxt(String propPersRoleTxt) {
        this.propPersRoleTxt = propPersRoleTxt;
    }

    public String getPropPersRoleAbbr() {
        return propPersRoleAbbr;
    }

    public void setPropPersRoleAbbr(String propPersRoleAbbr) {
        this.propPersRoleAbbr = propPersRoleAbbr;
    }

    @Override
    public String toString() {
        return "SeniorPersonnelType [propPersRoleCode=" + propPersRoleCode + ", propPersRoleTxt=" + propPersRoleTxt
                + ", propPersRoleAbbr=" + propPersRoleAbbr + ", userDataServiceRole=" + userDataServiceRole
                + ", effDate=" + effDate + ", endDate=" + endDate + "]";
    }

}
