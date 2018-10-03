package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.ObjectUtils;

/*
 * To make a model inheritable in other entity classes, use the following annotations: 
 * (1) Entity - @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
 * (2) Non-Entity- @MappedSuperclass
 */

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "last_updt_pgm")
    String lastUpdtPgm;

    @Column(name = "last_updt_user")
    String lastUpdtUser;

    @Column(name = "last_updt_tmsp")
    Date lastUpdtTmsp;

    protected BaseEntity() {
    }

    public String getLastUpdtPgm() {
        return lastUpdtPgm;
    }

    public void setLastUpdtPgm(String lastUpdtPgm) {
        this.lastUpdtPgm = lastUpdtPgm;
    }

    public String getLastUpdtUser() {
        return lastUpdtUser;
    }

    public void setLastUpdtUser(String lastUpdtUser) {
        this.lastUpdtUser = lastUpdtUser;
    }

    public Date getLastUpdtTmsp() {
        return (Date) ObjectUtils.clone(lastUpdtTmsp);
    }

    public void setLastUpdtTmsp(Date lastUpdtTmsp) {
        this.lastUpdtTmsp = (Date) ObjectUtils.clone(lastUpdtTmsp);
    }

}
