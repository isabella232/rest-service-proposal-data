package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_type_lkup")
public class ProposalType extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "prop_type_code", nullable = false)
    private String propTypeCode;

    @Column(name = "prop_type_txt", nullable = false)
    private String propTypeTxt;

    @Column(name = "prop_type_abbr")
    private String propTypeAbbr;

    protected ProposalType() {

    }

    public ProposalType(String propTypeCode, String propTypeTxt, String propTypeAbbr, Date effDate, Date endDate) {
        super();
        this.propTypeCode = propTypeCode;
        this.propTypeTxt = propTypeTxt;
        this.propTypeAbbr = propTypeAbbr;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);
    }

    public String getPropTypeCode() {
        return propTypeCode;
    }

    public void setPropTypeCode(String propTypeCode) {
        this.propTypeCode = propTypeCode;
    }

    public String getPropTypeTxt() {
        return propTypeTxt;
    }

    public void setPropTypeTxt(String propTypeTxt) {
        this.propTypeTxt = propTypeTxt;
    }

    public String getPropTypeAbbr() {
        return propTypeAbbr;
    }

    public void setPropTypeAbbr(String propTypeAbbr) {
        this.propTypeAbbr = propTypeAbbr;
    }

    @Override
    public String toString() {
        return String.format("ProposalType [propTypeCode=%s, propTypeTxt=%s, propTypeAbbr=%s, effDate=%s, endDate=%s]",
                propTypeCode, propTypeTxt, propTypeAbbr, effDate.getTime(), endDate.getTime());
    }

}
