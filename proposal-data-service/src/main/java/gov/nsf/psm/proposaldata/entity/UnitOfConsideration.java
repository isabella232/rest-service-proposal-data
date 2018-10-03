package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_org_uoc")
public class UnitOfConsideration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_org_uoc_id", nullable = false)
    private Long propOrgUocId;

    @Column(name = "prop_prep_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepId;

    @Column(name = "fund_opp_id", nullable = false)
    private String fundOppId;

    @Column(name = "org_code", nullable = false)
    private String orgCode;

    @Column(name = "pgm_ele_code", nullable = false)
    private String pgmEleCode;

    @Column(name = "uoc_ordr_num", nullable = false)
    private int uocOrdrNum;

    public UnitOfConsideration() {
        /**
         * default constructor
         */
    }

    public UnitOfConsideration(String fundOppId, String orgCode, String pgmEleCode, int uocOrdrNum, String lastUpdtUser,
            String lastUpdtPgm, Date lastUpdtTmsp) {
        super();
        this.fundOppId = fundOppId;
        this.orgCode = orgCode;
        this.pgmEleCode = pgmEleCode;
        this.uocOrdrNum = uocOrdrNum;
        this.lastUpdtUser = lastUpdtUser;
        this.lastUpdtPgm = lastUpdtPgm;
        this.lastUpdtTmsp = (Date) ObjectUtils.clone(lastUpdtTmsp);
    }

    public UnitOfConsideration(String orgCode, String pgmEleCode, int uocOrdrNum, String lastUpdtUser,
            String lastUpdtPgm, Date lastUpdtTmsp) {
        super();
        this.orgCode = orgCode;
        this.pgmEleCode = pgmEleCode;
        this.uocOrdrNum = uocOrdrNum;
        this.lastUpdtUser = lastUpdtUser;
        this.lastUpdtPgm = lastUpdtPgm;
        this.lastUpdtTmsp = (Date) ObjectUtils.clone(lastUpdtTmsp);
    }

    public Long getPropOrgUocId() {
        return propOrgUocId;
    }

    public void setPropOrgUocId(Long propOrgUocId) {
        this.propOrgUocId = propOrgUocId;
    }

    public Long getPropPrepId() {
        return propPrepId;
    }

    public void setPropPrepId(Long propPrepId) {
        this.propPrepId = propPrepId;
    }

    public String getFundOppId() {
        return fundOppId;
    }

    public void setFundOppId(String fundOppId) {
        this.fundOppId = fundOppId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getPgmEleCode() {
        return pgmEleCode;
    }

    public void setPgmEleCode(String pgmEleCode) {
        this.pgmEleCode = pgmEleCode;
    }

    public int getUocOrdrNum() {
        return uocOrdrNum;
    }

    public void setUocOrdrNum(int uocOrdrNum) {
        this.uocOrdrNum = uocOrdrNum;
    }

    @Override
    public String toString() {
        return String.format(
                "Uoc [propOrgUocId=%s, propPrepId=%s, pgmAnncId=%s, orgCode=%s, pgmEleCode=%s, uocOrdrNum=%s, lastUpdtUser=%s, lastUpdtPgm=%s, lastUpdtTmsp=%s]",
                propOrgUocId, propPrepId, fundOppId, orgCode, pgmEleCode, uocOrdrNum, lastUpdtUser, lastUpdtPgm,
                lastUpdtTmsp);
    }
}
