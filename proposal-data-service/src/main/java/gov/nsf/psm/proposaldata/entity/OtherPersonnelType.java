package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_budg_oth_pers_type_lkup")
public class OtherPersonnelType extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "oth_pers_type_code", nullable = false)
    private String othPersTypeCode;

    @Column(name = "oth_pers_type_txt", nullable = false)
    private String othPersTypeTxt;

    public OtherPersonnelType() {
        // Auto-generated constructor stub
    }

    public OtherPersonnelType(String othPersTypeCode, String othPersTypeTxt, Date effDate, Date endDate) {
        super();
        this.othPersTypeCode = othPersTypeCode;
        this.othPersTypeTxt = othPersTypeTxt;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);

    }

    public String getOthPersTypeCode() {
        return othPersTypeCode;
    }

    public void setOthPersTypeCode(String othPersTypeCode) {
        this.othPersTypeCode = othPersTypeCode;
    }

    public String getOthPersTypeTxt() {
        return othPersTypeTxt;
    }

    public void setOthPersTypeTxt(String othPersTypeTxt) {
        this.othPersTypeTxt = othPersTypeTxt;
    }

    @Override
    public String toString() {
        return String.format("OtherPersonnelType [othPersTypeCode=%s, othPersTypeTxt=%s, effDate=%s, endDate=%s]",
                othPersTypeCode, othPersTypeTxt, effDate.getTime(), endDate.getTime());
    }

}
