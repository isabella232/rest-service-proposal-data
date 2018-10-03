package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_user_role_pmsn_xref")
public class LoginUserRolePermissionXrefEntity extends BaseType {

    @Id
    @Column(name = "prop_user_role_pmsn_xref_id")
    private Long xrefId;

    @Column(name = "prop_logn_user_role_type_code")
    private String loginUserRoleCode;

    @Column(name = "prop_pmsn_type_code")
    private String loginPermissionCode;

    @Column(name = "prop_prep_stts_code")
    private String propStatusCode;

    public Long getXrefId() {
        return xrefId;
    }

    public void setXrefId(Long xrefId) {
        this.xrefId = xrefId;
    }

    public String getLoginUserRoleCode() {
        return loginUserRoleCode;
    }

    public void setLoginUserRoleCode(String loginUserRoleCode) {
        this.loginUserRoleCode = loginUserRoleCode;
    }

    public String getLoginPermissionCode() {
        return loginPermissionCode;
    }

    public void setLoginPermissionCode(String loginPermissionCode) {
        this.loginPermissionCode = loginPermissionCode;
    }

    public String getPropStatusCode() {
        return propStatusCode;
    }

    public void setPropStatusCode(String propStatusCode) {
        this.propStatusCode = propStatusCode;
    }

    @Override
    public String toString() {
        return String.format(
                "LoginUserRolePermission [ xrefId=%s, loginUserRoleCode=%s, loginPermissionCode=%s, propStatusCode=%s, effDate=%s, endDate=%s",
                xrefId, loginUserRoleCode, loginPermissionCode, propStatusCode, effDate.getTime(), endDate.getTime());
    }

}
