package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_proj_desc")
public class ProjectDescriptionEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevnId;

    @Column(name = "proj_desc_doc_file_loc", nullable = false)
    private String projDescFileLocation;

    @Column(name = "proj_desc_doc_page_cnt", nullable = false)
    private int projDescDocPageCount;

    @Column(name = "proj_desc_txt")
    private String projDescDocText;

    @Column(name = "proj_desc_orig_file_name", nullable = false)
    private String projDescDocOrigFileName;

    public ProjectDescriptionEntity() {
        /**
         * default constructor
         */
    }

    /**
     * @param propPrepRevnId
     * @param projDescFileLocation
     * @param projDescDocPageCount
     * @param projDescDocText
     * @param projDescDocOrigFileName
     */
    public ProjectDescriptionEntity(Long propPrepRevnId, String projDescFileLocation, int projDescDocPageCount,
            String projDescDocText, String projDescDocOrigFileName) {
        super();
        this.propPrepRevnId = propPrepRevnId;
        this.projDescFileLocation = projDescFileLocation;
        this.projDescDocPageCount = projDescDocPageCount;
        this.projDescDocText = projDescDocText;
        this.projDescDocOrigFileName = projDescDocOrigFileName;
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
     * @return the projDescFileLocation
     */
    public String getProjDescFileLocation() {
        return projDescFileLocation;
    }

    /**
     * @param projDescFileLocation
     *            the projDescFileLocation to set
     */
    public void setProjDescFileLocation(String projDescFileLocation) {
        this.projDescFileLocation = projDescFileLocation;
    }

    /**
     * @return the projDescDocPageCount
     */
    public int getProjDescDocPageCount() {
        return projDescDocPageCount;
    }

    /**
     * @param projDescDocPageCount
     *            the projDescDocPageCount to set
     */
    public void setProjDescDocPageCount(int projDescDocPageCount) {
        this.projDescDocPageCount = projDescDocPageCount;
    }

    /**
     * @return the projDescDocText
     */
    public String getProjDescDocText() {
        return projDescDocText;
    }

    /**
     * @param projDescDocText
     *            the projDescDocText to set
     */
    public void setProjDescDocText(String projDescDocText) {
        this.projDescDocText = projDescDocText;
    }

    /**
     * @return the projDescDocOrigFileName
     */
    public String getProjDescDocOrigFileName() {
        return projDescDocOrigFileName;
    }

    /**
     * @param projDescDocOrigFileName
     *            the projDescDocOrigFileName to set
     */
    public void setProjDescDocOrigFileName(String projDescDocOrigFileName) {
        this.projDescDocOrigFileName = projDescDocOrigFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "ProjectDescriptionEntity [propPrepRevnId=%s, projDescFileLocation=%s, projDescDocPageCount=%s, projDescDocText=%s, projDescDocOrigFileName=%s]",
                propPrepRevnId, projDescFileLocation, projDescDocPageCount, projDescDocText, projDescDocOrigFileName);
    }

}
