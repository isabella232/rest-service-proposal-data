package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_advr_adse_type_lkup")
public class AdviseeType extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "prop_advr_adse_type_code", nullable = false)
    private String propAdvrTypeCode;

    @Column(name = "prop_advr_adse_type_txt", nullable = false)
    private String propAdvrTypeTxt;

    protected AdviseeType() {

    }

    public AdviseeType(String propAdvrTypeCode, String propAdvrTypeTxt, Date effDate, Date endDate) {
        super();
        this.propAdvrTypeCode = propAdvrTypeCode;
        this.propAdvrTypeTxt = propAdvrTypeTxt;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);
    }

    public String getPropAdvrTypeCode() {
        return propAdvrTypeCode;
    }

    public void setPropAdvrTypeCode(String propAdvrTypeCode) {
        this.propAdvrTypeCode = propAdvrTypeCode;
    }

    public String getPropAdvrTypeTxt() {
        return propAdvrTypeTxt;
    }

    public void setPropColbTypeTxt(String propAdvrTypeTxt) {
        this.propAdvrTypeTxt = propAdvrTypeTxt;
    }

    @Override
    public String toString() {
        return String.format("AdviseeType [propAdvrTypeCode=%s, propAdvrTypeTxt=%s, effDate=%s, endDate=%s]",
                propAdvrTypeCode, propAdvrTypeTxt, effDate.getTime(), endDate.getTime());
    }

}
