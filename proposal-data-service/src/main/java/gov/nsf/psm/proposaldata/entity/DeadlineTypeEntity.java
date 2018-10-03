package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_due_date_type_lkup")
public class DeadlineTypeEntity extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "prop_due_date_type_code", nullable = false)
    private String deadlineTypeCode;

    @Column(name = "due_date_type_txt", nullable = false)
    private String deadlineTypeText;

    protected DeadlineTypeEntity() {

    }

    public DeadlineTypeEntity(String deadlineTypeCode, String deadlineTypeText, Date effDate, Date endDate) {
        super();
        this.deadlineTypeCode = deadlineTypeCode;
        this.deadlineTypeText = deadlineTypeText;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);
    }

    public String getDeadlineTypeCode() {
        return deadlineTypeCode;
    }

    public void setDeadlineTypeCode(String deadlineTypeCode) {
        this.deadlineTypeCode = deadlineTypeCode;
    }

    public String getDeadlineTypeText() {
        return deadlineTypeText;
    }

    public void setDeadlineTypeText(String deadlineTypeText) {
        this.deadlineTypeText = deadlineTypeText;
    }

}