package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_logn_user_role_type_lkup")
public class LoginUserRoleTypeEntity extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "prop_logn_user_role_type_code")
    private String propLognUserRoleTypeCode;

    @Column(name = "logn_user_role_type_txt")
    private String lognUserRoleTypeText;

    @Column(name = "logn_user_role_type_abbr")
    private String lognUserRoleTypeAbbr;

    protected LoginUserRoleTypeEntity() {

    }

    public String getPropLognUserRoleTypeCode() {
        return propLognUserRoleTypeCode;
    }

    public void setPropLognUserRoleTypeCode(String propLognUserRoleTypeCode) {
        this.propLognUserRoleTypeCode = propLognUserRoleTypeCode;
    }

    public String getLognUserRoleTypeText() {
        return lognUserRoleTypeText;
    }

    public void setLognUserRoleTypeText(String lognUserRoleTypeText) {
        this.lognUserRoleTypeText = lognUserRoleTypeText;
    }

    public String getLognUserRoleTypeAbbr() {
        return lognUserRoleTypeAbbr;
    }

    public void setLognUserRoleTypeAbbr(String lognUserRoleTypeAbbr) {
        this.lognUserRoleTypeAbbr = lognUserRoleTypeAbbr;
    }

    @Override
    public String toString() {
        return String.format(
                "LoginUserRole [ propLognUserRoleTypeCode=%s, lognUserRoleTypeText=%s, lognUserRoleTypeAbbr=%s, effDate=%s, endDate=%s",
                propLognUserRoleTypeCode, lognUserRoleTypeText, lognUserRoleTypeAbbr, effDate.getTime(),
                endDate.getTime());

    }
}
