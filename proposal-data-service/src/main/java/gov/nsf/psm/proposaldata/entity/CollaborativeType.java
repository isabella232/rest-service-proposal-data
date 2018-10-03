package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_colb_type_lkup")
public class CollaborativeType extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "prop_colb_type_code", nullable = false)
    private String propColbTypeCode;

    @Column(name = "prop_colb_type_txt", nullable = false)
    private String propColbTypeTxt;

    protected CollaborativeType() {

    }

    public CollaborativeType(String propColbTypeCode, String propColbTypeTxt, Date effDate, Date endDate) {
        super();
        this.propColbTypeCode = propColbTypeCode;
        this.propColbTypeTxt = propColbTypeTxt;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);
    }

    public String getPropColbTypeCode() {
        return propColbTypeCode;
    }

    public void setPropColbTypeCode(String propColbTypeCode) {
        this.propColbTypeCode = propColbTypeCode;
    }

    public String getPropColbTypeTxt() {
        return propColbTypeTxt;
    }

    public void setPropColbTypeTxt(String propColbTypeTxt) {
        this.propColbTypeTxt = propColbTypeTxt;
    }

    @Override
    public String toString() {
        return String.format("CollaborationType [propColbTypeCode=%s, propColbTypeTxt=%s, effDate=%s, endDate=%s]",
                propColbTypeCode, propColbTypeTxt, effDate.getTime(), endDate.getTime());
    }

}
