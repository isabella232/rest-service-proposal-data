package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_prep_stts_lkup")
public class ProposalStatusType extends BaseType implements LookUpEntity {

    @Id
    @Column(name = "prop_prep_stts_code", nullable = false)
    private String propPrepSttsCode;

    @Column(name = "prop_prep_stts_txt", nullable = false)
    private String propPrepSttsTxt;

    protected ProposalStatusType() {

    }

    public ProposalStatusType(String propPrepSttsCode, String propPrepSttsTxt, Date effDate, Date endDate) {
        super();
        this.propPrepSttsCode = propPrepSttsCode;
        this.propPrepSttsTxt = propPrepSttsTxt;
        this.effDate = (Date) ObjectUtils.clone(effDate);
        this.endDate = (Date) ObjectUtils.clone(endDate);
    }

    public String getPropPrepSttsCode() {
        return propPrepSttsCode;
    }

    public void setPropPrepSttsCode(String propPrepSttsCode) {
        this.propPrepSttsCode = propPrepSttsCode;
    }

    public String getPropPrepSttsTxt() {
        return propPrepSttsTxt;
    }

    public void setPropPrepSttsTxt(String propPrepSttsTxt) {
        this.propPrepSttsTxt = propPrepSttsTxt;
    }

    @Override
    public String toString() {
        return String.format("CollaborationType [propPrepSttsCode=%s, propPrepSttsTxt=%s, effDate=%s, endDate=%s]",
                propPrepSttsCode, propPrepSttsTxt, effDate.getTime(), endDate.getTime());
    }
}
