package gov.nsf.psm.proposaldata.entity.parameter;

import java.util.Date;

import org.apache.commons.lang.ObjectUtils;

public class BaseEntityParameter {

    private String lastUpdatePgm;
    private String lastUpdateUser;
    private Date lastUpdateTmsp;

    public String getLastUpdatePgm() {
        return lastUpdatePgm;
    }

    public void setLastUpdatePgm(String lastUpdatePgm) {
        this.lastUpdatePgm = lastUpdatePgm;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Date getLastUpdateTmsp() {
        return (Date) ObjectUtils.clone(lastUpdateTmsp);
    }

    public void setLastUpdateTmsp(Date lastUpdateTmsp) {
        this.lastUpdateTmsp = (Date) ObjectUtils.clone(lastUpdateTmsp);
    }

}
