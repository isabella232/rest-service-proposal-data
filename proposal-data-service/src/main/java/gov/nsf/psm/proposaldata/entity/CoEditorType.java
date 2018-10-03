package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_co_edit_type_lkup")
public class CoEditorType extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "prop_co_edit_type_code", nullable = false)
    private String propCoedTypeCode;

    @Column(name = "prop_co_edit_type_txt", nullable = false)
    private String propCoedTypeTxt;

    protected CoEditorType() {

    }

    public CoEditorType(String propCoedTypeCode, String propCoedTypeTxt, Date effDate, Date endDate) {
        super();
        this.propCoedTypeCode = propCoedTypeCode;
        this.propCoedTypeTxt = propCoedTypeTxt;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);
    }

    public String getPropCoedTypeCode() {
        return propCoedTypeCode;
    }

    public void setPropCoedTypeCode(String propCoedTypeCode) {
        this.propCoedTypeCode = propCoedTypeCode;
    }

    public String getPropCoedTypeTxt() {
        return propCoedTypeTxt;
    }

    public void setPropColbTypeTxt(String propCoedTypeTxt) {
        this.propCoedTypeTxt = propCoedTypeTxt;
    }

    @Override
    public String toString() {
        return String.format("CoEditorType [propCoedTypeCode=%s, propCoedTypeTxt=%s, effDate=%s, endDate=%s]",
                propCoedTypeCode, propCoedTypeTxt, effDate.getTime(), endDate.getTime());
    }

}
