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
@Table(name = "prop_nnae")
public class NNAEEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevnId;

    @Column(name = "nnae_doc_file_loc", nullable = false)
    private String nnaeDocFileLocation;

    @Column(name = "nnae_doc_page_cnt", nullable = false)
    private int nNAEventDocPageCount;

    @Column(name = "nnae_txt")
    private String nNAEventDocText;

    @Column(name = "nnae_orig_file_name", nullable = false)
    private String nNAEventDocOrigFileName;

    /**
     * 
     */
    public NNAEEntity() {
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
     * @return the nnaeDocFileLocation
     */
    public String getNnaeDocFileLocation() {
        return nnaeDocFileLocation;
    }

    /**
     * @param nnaeDocFileLocation
     *            the nnaeDocFileLocation to set
     */
    public void setNnaeDocFileLocation(String nnaeDocFileLocation) {
        this.nnaeDocFileLocation = nnaeDocFileLocation;
    }

    /**
     * @return the nNAEventDocPageCount
     */
    public int getnNAEventDocPageCount() {
        return nNAEventDocPageCount;
    }

    /**
     * @param nNAEventDocPageCount
     *            the nNAEventDocPageCount to set
     */
    public void setnNAEventDocPageCount(int nNAEventDocPageCount) {
        this.nNAEventDocPageCount = nNAEventDocPageCount;
    }

    /**
     * @return the nNAEventDocText
     */
    public String getnNAEventDocText() {
        return nNAEventDocText;
    }

    /**
     * @param nNAEventDocText
     *            the nNAEventDocText to set
     */
    public void setnNAEventDocText(String nNAEventDocText) {
        this.nNAEventDocText = nNAEventDocText;
    }

    /**
     * @return the nNAEventDocOrigFileName
     */
    public String getnNAEventDocOrigFileName() {
        return nNAEventDocOrigFileName;
    }

    /**
     * @param nNAEventDocOrigFileName
     *            the nNAEventDocOrigFileName to set
     */
    public void setnNAEventDocOrigFileName(String nNAEventDocOrigFileName) {
        this.nNAEventDocOrigFileName = nNAEventDocOrigFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "NNAEEntity [propPrepRevnId=%s, postDocMentPlanFileLocation=%s, nNAEventDocPageCount=%s, nNAEventDocText=%s, nNAEventDocOrigFileName=%s]",
                propPrepRevnId, nnaeDocFileLocation, nNAEventDocPageCount, nNAEventDocText, nNAEventDocOrigFileName);
    }

}
