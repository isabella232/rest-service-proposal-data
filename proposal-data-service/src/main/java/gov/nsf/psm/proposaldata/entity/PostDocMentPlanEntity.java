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
@Table(name = "prop_post_doct_ment_plan")
public class PostDocMentPlanEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevnId;

    @Column(name = "post_doct_ment_plan_doc_file_loc", nullable = false)
    private String postDocMentPlanFileLocation;

    @Column(name = "post_doct_ment_plan_doc_page_cnt", nullable = false)
    private int postDocMentPlanDocPageCount;

    @Column(name = "post_doct_ment_plan_txt")
    private String postDocMentPlanDocText;

    @Column(name = "post_doct_ment_plan_orig_file_name", nullable = false)
    private String postDocMentPlanDocOrigFileName;

    /**
     * 
     */
    public PostDocMentPlanEntity() {
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
     * @return the postDocMentPlanFileLocation
     */
    public String getPostDocMentPlanFileLocation() {
        return postDocMentPlanFileLocation;
    }

    /**
     * @param postDocMentPlanFileLocation
     *            the postDocMentPlanFileLocation to set
     */
    public void setPostDocMentPlanFileLocation(String postDocMentPlanFileLocation) {
        this.postDocMentPlanFileLocation = postDocMentPlanFileLocation;
    }

    /**
     * @return the postDocMentPlanDocPageCount
     */
    public int getPostDocMentPlanDocPageCount() {
        return postDocMentPlanDocPageCount;
    }

    /**
     * @param postDocMentPlanDocPageCount
     *            the postDocMentPlanDocPageCount to set
     */
    public void setPostDocMentPlanDocPageCount(int postDocMentPlanDocPageCount) {
        this.postDocMentPlanDocPageCount = postDocMentPlanDocPageCount;
    }

    /**
     * @return the postDocMentPlanDocText
     */
    public String getPostDocMentPlanDocText() {
        return postDocMentPlanDocText;
    }

    /**
     * @param postDocMentPlanDocText
     *            the postDocMentPlanDocText to set
     */
    public void setPostDocMentPlanDocText(String postDocMentPlanDocText) {
        this.postDocMentPlanDocText = postDocMentPlanDocText;
    }

    /**
     * @return the postDocMentPlanDocOrigFileName
     */
    public String getPostDocMentPlanDocOrigFileName() {
        return postDocMentPlanDocOrigFileName;
    }

    /**
     * @param postDocMentPlanDocOrigFileName
     *            the postDocMentPlanDocOrigFileName to set
     */
    public void setPostDocMentPlanDocOrigFileName(String postDocMentPlanDocOrigFileName) {
        this.postDocMentPlanDocOrigFileName = postDocMentPlanDocOrigFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "PostDocMentPlanEntity [propPrepRevnId=%s, postDocMentPlanFileLocation=%s, postDocMentPlanDocPageCount=%s, postDocMentPlanDocText=%s, postDocMentPlanDocOrigFileName=%s]",
                propPrepRevnId, postDocMentPlanFileLocation, postDocMentPlanDocPageCount, postDocMentPlanDocText,
                postDocMentPlanDocOrigFileName);
    }

}
