package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_clbr_type_lkup")
public class CollaboratorType extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "prop_clbr_type_code", nullable = false)
    private String propClbrTypeCode;

    @Column(name = "prop_clbr_type_txt", nullable = false)
    private String propClbrTypeTxt;

    protected CollaboratorType() {

    }

    public CollaboratorType(String propClbrTypeCode, String propClbrTypeTxt, Date effDate, Date endDate) {
        super();
        this.propClbrTypeCode = propClbrTypeCode;
        this.propClbrTypeTxt = propClbrTypeTxt;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);
    }

    public String getPropClbrTypeCode() {
        return propClbrTypeCode;
    }

    public void setPropClbrTypeCode(String propClbrTypeCode) {
        this.propClbrTypeCode = propClbrTypeCode;
    }

    public String getPropClbrTypeTxt() {
        return propClbrTypeTxt;
    }

    public void setPropColbTypeTxt(String propClbrTypeTxt) {
        this.propClbrTypeTxt = propClbrTypeTxt;
    }

    @Override
    public String toString() {
        return String.format("CollaboratorType [propClbrTypeCode=%s, propClbrTypeTxt=%s, effDate=%s, endDate=%s]",
                propClbrTypeCode, propClbrTypeTxt, effDate.getTime(), endDate.getTime());
    }

}
