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
@Table(name = "prop_data_mgmt_plan")
public class DataManagementPlanEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevnId;

    @Column(name = "data_mgmt_plan_doc_file_loc", nullable = false)
    private String dataManagementPlanFileLocation;

    @Column(name = "data_mgmt_plan_doc_page_cnt", nullable = false)
    private int dataManagementPlanDocPageCount;

    @Column(name = "data_mgmt_plan_txt")
    private String dataManagementPlanDocText;

    @Column(name = "data_mgmt_plan_orig_file_name", nullable = false)
    private String dataManagementPlanDocOrigFileName;

    /**
     * 
     */
    public DataManagementPlanEntity() {
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
     * @return the dataManagementPlanFileLocation
     */
    public String getDataManagementPlanFileLocation() {
        return dataManagementPlanFileLocation;
    }

    /**
     * @param dataManagementPlanFileLocation
     *            the dataManagementPlanFileLocation to set
     */
    public void setDataManagementPlanFileLocation(String dataManagementPlanFileLocation) {
        this.dataManagementPlanFileLocation = dataManagementPlanFileLocation;
    }

    /**
     * @return the dataManagementPlanDocPageCount
     */
    public int getDataManagementPlanDocPageCount() {
        return dataManagementPlanDocPageCount;
    }

    /**
     * @param dataManagementPlanDocPageCount
     *            the dataManagementPlanDocPageCount to set
     */
    public void setDataManagementPlanDocPageCount(int dataManagementPlanDocPageCount) {
        this.dataManagementPlanDocPageCount = dataManagementPlanDocPageCount;
    }

    /**
     * @return the dataManagementPlanDocText
     */
    public String getDataManagementPlanDocText() {
        return dataManagementPlanDocText;
    }

    /**
     * @param dataManagementPlanDocText
     *            the dataManagementPlanDocText to set
     */
    public void setDataManagementPlanDocText(String dataManagementPlanDocText) {
        this.dataManagementPlanDocText = dataManagementPlanDocText;
    }

    /**
     * @return the dataManagementPlanDocOrigFileName
     */
    public String getDataManagementPlanDocOrigFileName() {
        return dataManagementPlanDocOrigFileName;
    }

    /**
     * @param dataManagementPlanDocOrigFileName
     *            the dataManagementPlanDocOrigFileName to set
     */
    public void setDataManagementPlanDocOrigFileName(String dataManagementPlanDocOrigFileName) {
        this.dataManagementPlanDocOrigFileName = dataManagementPlanDocOrigFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "DataManagementPlanEntity [propPrepRevnId=%s, dataManagementPlanFileLocation=%s, dataManagementPlanDocPageCount=%s, dataManagementPlanDocText=%s, dataManagementPlanDocOrigFileName=%s]",
                propPrepRevnId, dataManagementPlanFileLocation, dataManagementPlanDocPageCount,
                dataManagementPlanDocText, dataManagementPlanDocOrigFileName);
    }

}
