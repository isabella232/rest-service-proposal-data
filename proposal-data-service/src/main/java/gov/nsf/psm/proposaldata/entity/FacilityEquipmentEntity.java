package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_fclt_equp")
public class FacilityEquipmentEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false)
    private Long propPrepRevnId;

    @Column(name = "fclt_equp_doc_file_loc", nullable = false)
    private String facltEquipDocFileLocation;

    @Column(name = "fclt_equp_doc_page_cnt", nullable = false)
    private int facltEquipDocPageCount;

    @Column(name = "fclt_equp_txt")
    private String facltEquipText;

    @Column(name = "fclt_equp_orig_file_name", nullable = false)
    private String facltEquipOrgFileName;

    public FacilityEquipmentEntity() {
        /**
         * Default constructor
         */
    }

    /**
     * @param propPrepRevnId
     * @param facltEquipDocFileLocation
     * @param facltEquipDocPageCount
     * @param facltEquipText
     * @param facltEquipOrgFileName
     */
    public FacilityEquipmentEntity(Long propPrepRevnId, String facltEquipDocFileLocation, int facltEquipDocPageCount,
            String facltEquipText, String facltEquipOrgFileName) {
        super();
        this.propPrepRevnId = propPrepRevnId;
        this.facltEquipDocFileLocation = facltEquipDocFileLocation;
        this.facltEquipDocPageCount = facltEquipDocPageCount;
        this.facltEquipText = facltEquipText;
        this.facltEquipOrgFileName = facltEquipOrgFileName;
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
     * @return the facltEquipDocFileLocation
     */
    public String getFacltEquipDocFileLocation() {
        return facltEquipDocFileLocation;
    }

    /**
     * @param facltEquipDocFileLocation
     *            the facltEquipDocFileLocation to set
     */
    public void setFacltEquipDocFileLocation(String facltEquipDocFileLocation) {
        this.facltEquipDocFileLocation = facltEquipDocFileLocation;
    }

    /**
     * @return the facltEquipDocPageCount
     */
    public int getFacltEquipDocPageCount() {
        return facltEquipDocPageCount;
    }

    /**
     * @param facltEquipDocPageCount
     *            the facltEquipDocPageCount to set
     */
    public void setFacltEquipDocPageCount(int facltEquipDocPageCount) {
        this.facltEquipDocPageCount = facltEquipDocPageCount;
    }

    /**
     * @return the facltEquipText
     */
    public String getFacltEquipText() {
        return facltEquipText;
    }

    /**
     * @param facltEquipText
     *            the facltEquipText to set
     */
    public void setFacltEquipText(String facltEquipText) {
        this.facltEquipText = facltEquipText;
    }

    /**
     * @return the facltEquipOrgFileName
     */
    public String getFacltEquipOrgFileName() {
        return facltEquipOrgFileName;
    }

    /**
     * @param facltEquipOrgFileName
     *            the facltEquipOrgFileName to set
     */
    public void setFacltEquipOrgFileName(String facltEquipOrgFileName) {
        this.facltEquipOrgFileName = facltEquipOrgFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "FacilityEquipmentEntity [propPrepRevnId=%s, facltEquipDocFileLocation=%s, facltEquipDocPageCount=%s, facltEquipText=%s, facltEquipOrgFileName=%s]",
                propPrepRevnId, facltEquipDocFileLocation, facltEquipDocPageCount, facltEquipText,
                facltEquipOrgFileName);
    }

}
