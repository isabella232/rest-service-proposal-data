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
@Table(name = "prop_revr_not_incl")
public class ReviewersNotIncludeEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevnId;

    @Column(name = "revr_not_incl_doc_file_loc", nullable = false)
    private String revrsNotIncludeFileLocation;

    @Column(name = "revr_not_incl_doc_page_cnt", nullable = false)
    private int revrsNotIncludeDocPageCount;

    @Column(name = "revr_not_incl_txt")
    private String revrsNotIncludeDocText;

    @Column(name = "revr_not_incl_orig_file_name", nullable = false)
    private String revrsNotIncludeDocOrigFileName;

    /**
     * 
     */
    public ReviewersNotIncludeEntity() {
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
     * @return the revrsNotIncludeFileLocation
     */
    public String getRevrsNotIncludeFileLocation() {
        return revrsNotIncludeFileLocation;
    }

    /**
     * @param revrsNotIncludeFileLocation
     *            the revrsNotIncludeFileLocation to set
     */
    public void setRevrsNotIncludeFileLocation(String revrsNotIncludeFileLocation) {
        this.revrsNotIncludeFileLocation = revrsNotIncludeFileLocation;
    }

    /**
     * @return the revrsNotIncludeDocPageCount
     */
    public int getRevrsNotIncludeDocPageCount() {
        return revrsNotIncludeDocPageCount;
    }

    /**
     * @param revrsNotIncludeDocPageCount
     *            the revrsNotIncludeDocPageCount to set
     */
    public void setRevrsNotIncludeDocPageCount(int revrsNotIncludeDocPageCount) {
        this.revrsNotIncludeDocPageCount = revrsNotIncludeDocPageCount;
    }

    /**
     * @return the revrsNotIncludeDocText
     */
    public String getRevrsNotIncludeDocText() {
        return revrsNotIncludeDocText;
    }

    /**
     * @param revrsNotIncludeDocText
     *            the revrsNotIncludeDocText to set
     */
    public void setRevrsNotIncludeDocText(String revrsNotIncludeDocText) {
        this.revrsNotIncludeDocText = revrsNotIncludeDocText;
    }

    /**
     * @return the revrsNotIncludeDocOrigFileName
     */
    public String getRevrsNotIncludeDocOrigFileName() {
        return revrsNotIncludeDocOrigFileName;
    }

    /**
     * @param revrsNotIncludeDocOrigFileName
     *            the revrsNotIncludeDocOrigFileName to set
     */
    public void setRevrsNotIncludeDocOrigFileName(String revrsNotIncludeDocOrigFileName) {
        this.revrsNotIncludeDocOrigFileName = revrsNotIncludeDocOrigFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "ReviewersNotIncludeEntity [propPrepRevnId=%s, revrsNotIncludeFileLocation=%s, revrsNotIncludeDocPageCount=%s, revrsNotIncludeDocText=%s, revrsNotIncludeDocOrigFileName=%s]",
                propPrepRevnId, revrsNotIncludeFileLocation, revrsNotIncludeDocPageCount, revrsNotIncludeDocText,
                revrsNotIncludeDocOrigFileName);
    }

}
