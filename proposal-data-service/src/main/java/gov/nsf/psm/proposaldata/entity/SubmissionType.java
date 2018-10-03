package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_subm_type_lkup")
public class SubmissionType extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "prop_subm_type_code", nullable = false)
    private String propSubmTypeCode;

    @Column(name = "prop_subm_type_txt", nullable = false)
    private String propSubmTypeTxt;

    protected SubmissionType() {

    }

    public SubmissionType(String propSubmTypeCode, String propSubmTypeTxt, Date effDate, Date endDate) {
        super();
        this.propSubmTypeCode = propSubmTypeCode;
        this.propSubmTypeTxt = propSubmTypeTxt;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);
    }

    public String getPropSubmTypeCode() {
        return propSubmTypeCode;
    }

    public void setPropSubmTypeCode(String propSubmTypeCode) {
        this.propSubmTypeCode = propSubmTypeCode;
    }

    public String getPropSubmTypeTxt() {
        return propSubmTypeTxt;
    }

    public void setPropSubmTypeTxt(String propSubmTypeTxt) {
        this.propSubmTypeTxt = propSubmTypeTxt;
    }

    @Override
    public String toString() {
        return String.format("SubmissionType [propSubmTypeCode=%s, propSubmTypeTxt=%s, effDate=%s, endDate=%s]",
                propSubmTypeCode, propSubmTypeTxt, effDate.getTime(), endDate.getTime());
    }

}
