package gov.nsf.psm.proposaldata.entity.parameter;

import java.util.Date;

import org.apache.commons.lang.ObjectUtils;

public class ProposalPrepRevisionParameter extends BaseEntityParameter {

    private Byte revisionNumber;
    private String propTitle;
    private String prepRevnTypeCode;
    private String prepSttsCode;
    private Date prepSttsDate;
    private String staticPdfPath;

    public Byte getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Byte revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public String getPropTitle() {
        return propTitle;
    }

    public void setPropTitle(String propTitle) {
        this.propTitle = propTitle;
    }

    public String getPrepRevnTypeCode() {
        return prepRevnTypeCode;
    }

    public void setPrepRevnTypeCode(String prepRevnTypeCode) {
        this.prepRevnTypeCode = prepRevnTypeCode;
    }

    public String getPrepSttsCode() {
        return prepSttsCode;
    }

    public void setPrepSttsCode(String prepSttsCode) {
        this.prepSttsCode = prepSttsCode;
    }

    public Date getPrepSttsDate() {
        return (Date) ObjectUtils.clone(prepSttsDate);
    }

    public void setPrepSttsDate(Date prepSttsDate) {
        this.prepSttsDate = (Date) ObjectUtils.clone(prepSttsDate);
    }

	public String getStaticPdfPath() {
		return staticPdfPath;
	}

	public void setStaticPdfPath(String staticPdfPath) {
		this.staticPdfPath = staticPdfPath;
	}

}
