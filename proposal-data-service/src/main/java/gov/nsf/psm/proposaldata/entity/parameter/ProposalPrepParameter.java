package gov.nsf.psm.proposaldata.entity.parameter;

import java.util.Date;

import org.apache.commons.lang.ObjectUtils;

public class ProposalPrepParameter extends BaseEntityParameter {

    private String submTypeCode;
    private String typeCode;
    private String fundOpporId;
    private Date inttDate;
    private String inttNsfId;
    private String pappgVers;
    private String nsfProposalId;
    private String colbTypeCode;
    private Date nsfProposalSubmDate;

    public String getSubmTypeCode() {
        return submTypeCode;
    }

    public void setSubmTypeCode(String submTypeCode) {
        this.submTypeCode = submTypeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getFundOpporId() {
        return fundOpporId;
    }

    public void setFundOpporId(String fundOpporId) {
        this.fundOpporId = fundOpporId;
    }

    public Date getInttDate() {
        return (Date) ObjectUtils.clone(inttDate);
    }

    public void setInttDate(Date inttDate) {
        this.inttDate = (Date) ObjectUtils.clone(inttDate);
    }

    public String getInttNsfId() {
        return inttNsfId;
    }

    public void setInttNsfId(String inttNsfId) {
        this.inttNsfId = inttNsfId;
    }

    public String getPappgVers() {
        return pappgVers;
    }

    public void setPappgVers(String pappgVers) {
        this.pappgVers = pappgVers;
    }

    public String getNsfProposalId() {
        return nsfProposalId;
    }

    public void setNsfProposalId(String nsfProposalId) {
        this.nsfProposalId = nsfProposalId;
    }

    public String getColbTypeCode() {
        return colbTypeCode;
    }

    public void setColbTypeCode(String colbTypeCode) {
        this.colbTypeCode = colbTypeCode;
    }

    public Date getNsfProposalSubmDate() {
        return (Date) ObjectUtils.clone(nsfProposalSubmDate);
    }

    public void setNsfProposalSubmDate(Date nsfProposalSubmDate) {
        this.nsfProposalSubmDate = (Date) ObjectUtils.clone(nsfProposalSubmDate);
    }

}
