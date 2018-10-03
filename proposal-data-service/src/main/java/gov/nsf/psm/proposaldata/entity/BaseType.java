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
public abstract class BaseType extends BaseEntity {

    @Column(name = "eff_date", nullable = false)
    Date effDate;

    @Column(name = "end_date")
    Date endDate;

    protected BaseType() {
        /**
         * default constructor
         */
    }

    public Date getEffDate() {
        return (Date) ObjectUtils.clone(effDate);
    }

    public void setEffDate(Date effDate) {
        this.effDate = (Date) ObjectUtils.clone(effDate);
    }

    public Date getEndDate() {
        return (Date) ObjectUtils.clone(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = (Date) ObjectUtils.clone(endDate);
    }

}
