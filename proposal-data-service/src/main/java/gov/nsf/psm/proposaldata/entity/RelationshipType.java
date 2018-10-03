package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_rela_type_lkup")
public class RelationshipType extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "prop_rela_type_code", nullable = false)
    private String propRelaTypeCode;

    @Column(name = "prop_rela_type_txt", nullable = false)
    private String propRelaTypeTxt;

    protected RelationshipType() {

    }

    public RelationshipType(String propRelaTypeCode, String propRelaTypeTxt, Date effDate, Date endDate) {
        super();
        this.propRelaTypeCode = propRelaTypeCode;
        this.propRelaTypeTxt = propRelaTypeTxt;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);
    }

    public String getPropRelaTypeCode() {
        return propRelaTypeCode;
    }

    public void setPropRelaTypeCode(String propRelaTypeCode) {
        this.propRelaTypeCode = propRelaTypeCode;
    }

    public String getPropRelaTypeTxt() {
        return propRelaTypeTxt;
    }

    public void setPropColbTypeTxt(String propRelaTypeTxt) {
        this.propRelaTypeTxt = propRelaTypeTxt;
    }

    @Override
    public String toString() {
        return String.format("RelationshipType [propRelaTypeCode=%s, propRelaTypeTxt=%s, effDate=%s, endDate=%s]",
                propRelaTypeCode, propRelaTypeTxt, effDate.getTime(), endDate.getTime());
    }

}
