package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_oth_supp_doc")
public class OtherSuppDocsEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevnId;

    @Column(name = "oth_supp_doc_num", nullable = false)
    private int otherSuppDocNumber;

    @Column(name = "oth_supp_doc_file_loc", nullable = false)
    private String otherSuppDocFileLocation;

    @Column(name = "oth_supp_doc_page_cnt", nullable = false)
    private int otherSuppDocPageCount;

    @Column(name = "oth_supp_doc_txt")
    private String otherSuppDocText;

    @Column(name = "oth_supp_doc_orig_file_name", nullable = false)
    private String otherSuppDocOrigFileName;

    /**
     * 
     */
    public OtherSuppDocsEntity() {
        /**
         * default constructor
         */
    }

    /**
     * @return the propPrepRevnId
     */
    public Long getPropPrepRevnId() {
        return propPrepRevnId;
    }

    /**
     * @param propPrepRevnId
     *            the propPrepRevnId to set
     */
    public void setPropPrepRevnId(Long propPrepRevnId) {
        this.propPrepRevnId = propPrepRevnId;
    }

    /**
     * @return the otherSuppDocNumber
     */
    public int getOtherSuppDocNumber() {
        return otherSuppDocNumber;
    }

    /**
     * @param otherSuppDocNumber
     *            the otherSuppDocNumber to set
     */
    public void setOtherSuppDocNumber(int otherSuppDocNumber) {
        this.otherSuppDocNumber = otherSuppDocNumber;
    }

    /**
     * @return the otherSuppDocFileLocation
     */
    public String getOtherSuppDocFileLocation() {
        return otherSuppDocFileLocation;
    }

    /**
     * @param otherSuppDocFileLocation
     *            the otherSuppDocFileLocation to set
     */
    public void setOtherSuppDocFileLocation(String otherSuppDocFileLocation) {
        this.otherSuppDocFileLocation = otherSuppDocFileLocation;
    }

    /**
     * @return the otherSuppDocPageCount
     */
    public int getOtherSuppDocPageCount() {
        return otherSuppDocPageCount;
    }

    /**
     * @param otherSuppDocPageCount
     *            the otherSuppDocPageCount to set
     */
    public void setOtherSuppDocPageCount(int otherSuppDocPageCount) {
        this.otherSuppDocPageCount = otherSuppDocPageCount;
    }

    /**
     * @return the otherSuppDocText
     */
    public String getOtherSuppDocText() {
        return otherSuppDocText;
    }

    /**
     * @param otherSuppDocText
     *            the otherSuppDocText to set
     */
    public void setOtherSuppDocText(String otherSuppDocText) {
        this.otherSuppDocText = otherSuppDocText;
    }

    /**
     * @return the otherSuppDocOrigFileName
     */
    public String getOtherSuppDocOrigFileName() {
        return otherSuppDocOrigFileName;
    }

    /**
     * @param otherSuppDocOrigFileName
     *            the otherSuppDocOrigFileName to set
     */
    public void setOtherSuppDocOrigFileName(String otherSuppDocOrigFileName) {
        this.otherSuppDocOrigFileName = otherSuppDocOrigFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "OtherSuppDocsEntity [propPrepRevnId=%s, otherSuppDocNumber=%s, otherSuppDocFileLocation=%s, otherSuppDocPageCount=%s, otherSuppDocText=%s, otherSuppDocOrigFileName=%s]",
                propPrepRevnId, otherSuppDocNumber, otherSuppDocFileLocation, otherSuppDocPageCount, otherSuppDocText,
                otherSuppDocOrigFileName);
    }

}
