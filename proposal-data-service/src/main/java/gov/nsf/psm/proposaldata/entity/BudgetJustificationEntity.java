package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_budg_jstf")
public class BudgetJustificationEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevnId;

    @Column(name = "budg_jstf_doc_file_loc", nullable = false)
    private String budgJustFileLocation;

    @Column(name = "budg_jstf_doc_page_cnt", nullable = false)
    private int budgJustDocPageCount;

    @Column(name = "budg_jstf_txt")
    private String budgJustText;

    @Column(name = "budg_jstf_orig_file_name", nullable = false)
    private String budgJustOrigFileName;

    /**
     * 
     */
    public BudgetJustificationEntity() {
        /**
         * default constructor
         */
    }

    /**
     * @param propPrepRevnId
     * @param budgJustFileLocation
     * @param budgJustDocPageCount
     * @param budgJustText
     * @param budgJustOrigFileName
     */
    public BudgetJustificationEntity(Long propPrepRevnId, String budgJustFileLocation, int budgJustDocPageCount,
            String budgJustText, String budgJustOrigFileName) {
        super();
        this.propPrepRevnId = propPrepRevnId;
        this.budgJustFileLocation = budgJustFileLocation;
        this.budgJustDocPageCount = budgJustDocPageCount;
        this.budgJustText = budgJustText;
        this.budgJustOrigFileName = budgJustOrigFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "BudgetJustificationEntity [propPrepRevnId=%s, budgJustFileLocation=%s, budgJustDocPageCount=%s, budgJustText=%s, budgJustOrigFileName=%s]",
                propPrepRevnId, budgJustFileLocation, budgJustDocPageCount, budgJustText, budgJustOrigFileName);
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
     * @return the budgJustFileLocation
     */
    public String getBudgJustFileLocation() {
        return budgJustFileLocation;
    }

    /**
     * @param budgJustFileLocation
     *            the budgJustFileLocation to set
     */
    public void setBudgJustFileLocation(String budgJustFileLocation) {
        this.budgJustFileLocation = budgJustFileLocation;
    }

    /**
     * @return the budgJustDocPageCount
     */
    public int getBudgJustDocPageCount() {
        return budgJustDocPageCount;
    }

    /**
     * @param budgJustDocPageCount
     *            the budgJustDocPageCount to set
     */
    public void setBudgJustDocPageCount(int budgJustDocPageCount) {
        this.budgJustDocPageCount = budgJustDocPageCount;
    }

    /**
     * @return the budgJustText
     */
    public String getBudgJustText() {
        return budgJustText;
    }

    /**
     * @param budgJustText
     *            the budgJustText to set
     */
    public void setBudgJustText(String budgJustText) {
        this.budgJustText = budgJustText;
    }

    /**
     * @return the budgJustOrigFileName
     */
    public String getBudgJustOrigFileName() {
        return budgJustOrigFileName;
    }

    /**
     * @param budgJustOrigFileName
     *            the budgJustOrigFileName to set
     */
    public void setBudgJustOrigFileName(String budgJustOrigFileName) {
        this.budgJustOrigFileName = budgJustOrigFileName;
    }

}
