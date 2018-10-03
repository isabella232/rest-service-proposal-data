package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_rvsd_budg_impt_stmt")
public class BudgetImpactEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevnId;

    @Column(name = "budg_impt_stmt_doc_file_loc", nullable = false)
    private String budgImpactFileLocation;

    @Column(name = "budg_impt_stmt_doc_page_cnt", nullable = false)
    private int budgImpactDocPageCount;

    @Column(name = "budg_impt_stmt_orig_file_name")
    private String budgImpactOrigFileName;

    @Column(name = "cre_date", nullable = false)
    private Date createdDate;

    @Column(name = "cre_user", nullable = false)
    private String createdUser;

    /**
     * 
     */
    public BudgetImpactEntity() {
        /**
         * default constructor
         */
    }

    /**
     * @return the propPrepRevnId
     */
    public Long getPropPrepRevnId() {
        return this.propPrepRevnId;
    }

    /**
     * @param propPrepRevnId
     *            the propPrepRevnId to set
     */
    public void setPropPrepRevnId(Long propPrepRevnId) {
        this.propPrepRevnId = propPrepRevnId;
    }

    /**
     * @return the budgImpactFileLocation
     */
    public String getBudgImpactFileLocation() {
        return this.budgImpactFileLocation;
    }

    /**
     * @param budgImpactFileLocation
     *            the budgImpactFileLocation to set
     */
    public void setBudgImpactFileLocation(String budgImpactFileLocation) {
        this.budgImpactFileLocation = budgImpactFileLocation;
    }

    /**
     * @return the budgImpactDocPageCount
     */
    public int getBudgImpactDocPageCount() {
        return this.budgImpactDocPageCount;
    }

    /**
     * @param budgImpactDocPageCount
     *            the budgImpactDocPageCount to set
     */
    public void setBudgImpactDocPageCount(int budgImpactDocPageCount) {
        this.budgImpactDocPageCount = budgImpactDocPageCount;
    }

    /**
     * @return the budgImpactOrigFileName
     */
    public String getBudgImpactOrigFileName() {
        return this.budgImpactOrigFileName;
    }

    /**
     * @param budgImpactOrigFileName
     *            the budgImpactOrigFileName to set
     */
    public void setBudgImpactOrigFileName(String budgImpactOrigFileName) {
        this.budgImpactOrigFileName = budgImpactOrigFileName;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return (Date) ObjectUtils.clone(this.createdDate);
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = (Date) ObjectUtils.clone(createdDate);
    }

    /**
     * @return the createdUser
     */
    public String getCreatedUser() {
        return this.createdUser;
    }

    /**
     * @param createdUser
     *            the createdUser to set
     */
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

}
