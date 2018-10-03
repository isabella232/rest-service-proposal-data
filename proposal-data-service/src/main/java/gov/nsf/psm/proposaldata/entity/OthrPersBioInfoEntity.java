/**
 * 
 */
package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author pkatrapa
 *
 */

@Entity
@Table(name = "prop_oth_prsn_bio_info")
public class OthrPersBioInfoEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevnId;

    @Column(name = "oth_prsn_bio_info_file_loc", nullable = false)
    private String othrPersBioInfoFileLocation;

    @Column(name = "oth_prsn_bio_info_doc_page_cnt", nullable = false)
    private int othrPersBioInfoDocPageCount;

    @Column(name = "oth_prsn_bio_info_txt")
    private String othrPersBioInfoDocText;

    @Column(name = "oth_prsn_bio_info_orig_file_name", nullable = false)
    private String othrPersBioInfoDocOrigFileName;

    /**
     * 
     */
    public OthrPersBioInfoEntity() {
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
     * @return the othrPersBioInfoFileLocation
     */
    public String getOthrPersBioInfoFileLocation() {
        return othrPersBioInfoFileLocation;
    }

    /**
     * @param othrPersBioInfoFileLocation
     *            the othrPersBioInfoFileLocation to set
     */
    public void setOthrPersBioInfoFileLocation(String othrPersBioInfoFileLocation) {
        this.othrPersBioInfoFileLocation = othrPersBioInfoFileLocation;
    }

    /**
     * @return the othrPersBioInfoDocPageCount
     */
    public int getOthrPersBioInfoDocPageCount() {
        return othrPersBioInfoDocPageCount;
    }

    /**
     * @param othrPersBioInfoDocPageCount
     *            the othrPersBioInfoDocPageCount to set
     */
    public void setOthrPersBioInfoDocPageCount(int othrPersBioInfoDocPageCount) {
        this.othrPersBioInfoDocPageCount = othrPersBioInfoDocPageCount;
    }

    /**
     * @return the othrPersBioInfoDocText
     */
    public String getOthrPersBioInfoDocText() {
        return othrPersBioInfoDocText;
    }

    /**
     * @param othrPersBioInfoDocText
     *            the othrPersBioInfoDocText to set
     */
    public void setOthrPersBioInfoDocText(String othrPersBioInfoDocText) {
        this.othrPersBioInfoDocText = othrPersBioInfoDocText;
    }

    /**
     * @return the othrPersBioInfoDocOrigFileName
     */
    public String getOthrPersBioInfoDocOrigFileName() {
        return othrPersBioInfoDocOrigFileName;
    }

    /**
     * @param othrPersBioInfoDocOrigFileName
     *            the othrPersBioInfoDocOrigFileName to set
     */
    public void setOthrPersBioInfoDocOrigFileName(String othrPersBioInfoDocOrigFileName) {
        this.othrPersBioInfoDocOrigFileName = othrPersBioInfoDocOrigFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "OthrPersBioInfoEntity [propPrepRevnId=%s, othrPersBioInfoFileLocation=%s, othrPersBioInfoDocPageCount=%s, othrPersBioInfoDocText=%s, othrPersBioInfoDocOrigFileName=%s]",
                propPrepRevnId, othrPersBioInfoFileLocation, othrPersBioInfoDocPageCount, othrPersBioInfoDocText,
                othrPersBioInfoDocOrigFileName);
    }

}
