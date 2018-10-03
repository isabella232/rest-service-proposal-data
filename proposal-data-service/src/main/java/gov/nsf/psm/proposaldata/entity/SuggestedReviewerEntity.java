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
@Table(name = "prop_sugg_revr")
public class SuggestedReviewerEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevnId;

    @Column(name = "sugg_revr_doc_file_loc", nullable = false)
    private String suggRevrFileLocation;

    @Column(name = "sugg_revr_doc_page_cnt", nullable = false)
    private int suggRevrDocPageCount;

    @Column(name = "sugg_revr_txt")
    private String suggRevrDocText;

    @Column(name = "sugg_revr_orig_file_name", nullable = false)
    private String suggRevrDocOrigFileName;

    /**
     * 
     */
    public SuggestedReviewerEntity() {
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
     * @return the suggRevrFileLocation
     */
    public String getSuggRevrFileLocation() {
        return suggRevrFileLocation;
    }

    /**
     * @param suggRevrFileLocation
     *            the suggRevrFileLocation to set
     */
    public void setSuggRevrFileLocation(String suggRevrFileLocation) {
        this.suggRevrFileLocation = suggRevrFileLocation;
    }

    /**
     * @return the suggRevrDocPageCount
     */
    public int getSuggRevrDocPageCount() {
        return suggRevrDocPageCount;
    }

    /**
     * @param suggRevrDocPageCount
     *            the suggRevrDocPageCount to set
     */
    public void setSuggRevrDocPageCount(int suggRevrDocPageCount) {
        this.suggRevrDocPageCount = suggRevrDocPageCount;
    }

    /**
     * @return the suggRevrDocText
     */
    public String getSuggRevrDocText() {
        return suggRevrDocText;
    }

    /**
     * @param suggRevrDocText
     *            the suggRevrDocText to set
     */
    public void setSuggRevrDocText(String suggRevrDocText) {
        this.suggRevrDocText = suggRevrDocText;
    }

    /**
     * @return the suggRevrDocOrigFileName
     */
    public String getSuggRevrDocOrigFileName() {
        return suggRevrDocOrigFileName;
    }

    /**
     * @param suggRevrDocOrigFileName
     *            the suggRevrDocOrigFileName to set
     */
    public void setSuggRevrDocOrigFileName(String suggRevrDocOrigFileName) {
        this.suggRevrDocOrigFileName = suggRevrDocOrigFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "SuggestedReviewerEntity [propPrepRevnId=%s, suggRevrFileLocation=%s, suggRevrDocPageCount=%s, suggRevrDocText=%s, suggRevrDocOrigFileName=%s]",
                propPrepRevnId, suggRevrFileLocation, suggRevrDocPageCount, suggRevrDocText, suggRevrDocOrigFileName);
    }

}
