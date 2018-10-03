package gov.nsf.psm.proposaldata.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "prop_curr_pend_sup")
public class CurrentAndPendingSupportEntity extends BaseEntity {

    @Id
    @Column(name = "prop_pers_id", nullable = false)
    private Long propPersId;

    @Column(name = "prop_prep_revn_id", nullable = false)
    private Long propPrepRevnId;

    @Column(name = "curr_pend_sup_doc_file_loc", nullable = false)
    private String currPendSuppDocfileLoc;

    @Column(name = "curr_pend_sup_doc_page_cnt", nullable = false)
    private int currPendSuppDocPageCount;

    @Column(name = "curr_pend_sup_txt")
    private String currPendSuppText;

    @Column(name = "curr_pend_sup_orig_file_name", nullable = false)
    private String currPendSuppOrigFileName;
    
    @OneToOne(orphanRemoval = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_pers_id", nullable = false, insertable = false, updatable = false)
    private ProposalPersonsEntity person;

    /**
     * 
     */
    public CurrentAndPendingSupportEntity() {
        /**
         * default constructor
         */
    }

    /**
     * @return the propPersId
     */
    public Long getPropPersId() {
        return propPersId;
    }

    /**
     * @param propPersId
     *            the propPersId to set
     */
    public void setPropPersId(Long propPersId) {
        this.propPersId = propPersId;
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
     * @return the currPendSuppDocfileLoc
     */
    public String getCurrPendSuppDocfileLoc() {
        return currPendSuppDocfileLoc;
    }

    /**
     * @param currPendSuppDocfileLoc
     *            the currPendSuppDocfileLoc to set
     */
    public void setCurrPendSuppDocfileLoc(String currPendSuppDocfileLoc) {
        this.currPendSuppDocfileLoc = currPendSuppDocfileLoc;
    }

    /**
     * @return the currPendSuppDocpageCount
     */
    public int getCurrPendSuppDocPageCount() {
        return currPendSuppDocPageCount;
    }

    /**
     * @param currPendSuppDocpageCount
     *            the currPendSuppDocpageCount to set
     */
    public void setCurrPendSuppDocPageCount(int currPendSuppDocPageCount) {
        this.currPendSuppDocPageCount = currPendSuppDocPageCount;
    }

    /**
     * @return the currPendSuppText
     */
    public String getCurrPendSuppText() {
        return currPendSuppText;
    }

    /**
     * @param currPendSuppText
     *            the currPendSuppText to set
     */
    public void setCurrPendSuppText(String currPendSuppText) {
        this.currPendSuppText = currPendSuppText;
    }

    /**
     * @return the currPendSuppOrigFileName
     */
    public String getCurrPendSuppOrigFileName() {
        return currPendSuppOrigFileName;
    }

    /**
     * @param currPendSuppOrigFileName
     *            the currPendSuppOrigFileName to set
     */
    public void setCurrPendSuppOrigFileName(String currPendSuppOrigFileName) {
        this.currPendSuppOrigFileName = currPendSuppOrigFileName;
    }
    
    public ProposalPersonsEntity getPerson() {
        return person;
    }

    public void setPerson(ProposalPersonsEntity person) {
        this.person = person;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "CurrentAndPendingSupportEntity [propPersId=%s, propPrepRevnId=%s, currPendSuppDocfileLoc=%s, currPendSuppDocPageCount=%s, currPendSuppText=%s, currPendSuppOrigFileName=%s]",
                propPersId, propPrepRevnId, currPendSuppDocfileLoc, currPendSuppDocPageCount, currPendSuppText,
                currPendSuppOrigFileName);
    }

}
