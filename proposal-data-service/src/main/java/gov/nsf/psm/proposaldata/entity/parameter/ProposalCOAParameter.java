package gov.nsf.psm.proposaldata.entity.parameter;

public class ProposalCOAParameter extends BaseEntityParameter {

    private Long propCOAId;
    private Long propPrepRevnId;
    private Long propPersId;
    private String coaExclFileLoc;
    private String coaExclOrigFileName;
    private String coaPdfFileLoc;
    private String coaPdfFileName;
    private Integer coaPdfPageCount;

    public Long getPropCOAId() {
        return propCOAId;
    }

    public void setPropCOAId(Long propCOAId) {
        this.propCOAId = propCOAId;
    }

    public Long getPropPrepRevnId() {
        return propPrepRevnId;
    }

    public void setPropPrepRevnId(Long propPrepRevnId) {
        this.propPrepRevnId = propPrepRevnId;
    }

    public Long getPropPersId() {
        return propPersId;
    }

    public void setPropPersId(Long propPersId) {
        this.propPersId = propPersId;
    }

    public String getCoaExclFileLoc() {
        return coaExclFileLoc;
    }

    public void setCoaExclFileLoc(String coaExclFileLoc) {
        this.coaExclFileLoc = coaExclFileLoc;
    }

    public String getCoaExclOrigFileName() {
        return coaExclOrigFileName;
    }

    public void setCoaExclOrigFileName(String coaExclOrigFileName) {
        this.coaExclOrigFileName = coaExclOrigFileName;
    }

    public String getCoaPdfFileLoc() {
        return coaPdfFileLoc;
    }

    public void setCoaPdfFileLoc(String coaPdfFileLoc) {
        this.coaPdfFileLoc = coaPdfFileLoc;
    }

    public String getCoaPdfFileName() {
        return coaPdfFileName;
    }

    public void setCoaPdfFileName(String coaPdfFileName) {
        this.coaPdfFileName = coaPdfFileName;
    }

    public Integer getCoaPdfPageCount() {
        return coaPdfPageCount;
    }

    public void setCoaPdfPageCount(Integer coaPdfPageCount) {
        this.coaPdfPageCount = coaPdfPageCount;
    }

}
