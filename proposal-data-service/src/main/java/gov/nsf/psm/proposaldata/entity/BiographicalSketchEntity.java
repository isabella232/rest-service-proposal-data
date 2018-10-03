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
@Table(name = "prop_bio_skch")
public class BiographicalSketchEntity extends BaseEntity {

    @Id
    @Column(name = "prop_pers_id", nullable = false)
    private Long propPersId;

    @Column(name = "prop_prep_revn_id", nullable = false)
    private Long propPrepRevnId;

    @Column(name = "bio_skch_doc_file_loc", nullable = false)
    private String bioSketchDocfileLoc;

    @Column(name = "bio_skch_doc_page_cnt", nullable = false)
    private int bioSketchDocpageCount;

    @Column(name = "bio_skch_txt")
    private String bioSketchText;

    @Column(name = "bio_skch_orig_file_name", nullable = false)
    private String bioSketchOrigFileName;
    
    @OneToOne(orphanRemoval = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_pers_id", nullable = false, insertable = false, updatable = false)
    private ProposalPersonsEntity person;

    /**
     * 
     */
    public BiographicalSketchEntity() {
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
     * @return the bioSketchDocfileLoc
     */
    public String getBioSketchDocfileLoc() {
        return bioSketchDocfileLoc;
    }

    /**
     * @param bioSketchDocfileLoc
     *            the bioSketchDocfileLoc to set
     */
    public void setBioSketchDocfileLoc(String bioSketchDocfileLoc) {
        this.bioSketchDocfileLoc = bioSketchDocfileLoc;
    }

    /**
     * @return the bioSketchDocpageCount
     */
    public int getBioSketchDocpageCount() {
        return bioSketchDocpageCount;
    }

    /**
     * @param bioSketchDocpageCount
     *            the bioSketchDocpageCount to set
     */
    public void setBioSketchDocpageCount(int bioSketchDocpageCount) {
        this.bioSketchDocpageCount = bioSketchDocpageCount;
    }

    /**
     * @return the bioSketchText
     */
    public String getBioSketchText() {
        return bioSketchText;
    }

    /**
     * @param bioSketchText
     *            the bioSketchText to set
     */
    public void setBioSketchText(String bioSketchText) {
        this.bioSketchText = bioSketchText;
    }

    /**
     * @return the bioSketchOrigFileName
     */
    public String getBioSketchOrigFileName() {
        return bioSketchOrigFileName;
    }

    /**
     * @param bioSketchOrigFileName
     *            the bioSketchOrigFileName to set
     */
    public void setBioSketchOrigFileName(String bioSketchOrigFileName) {
        this.bioSketchOrigFileName = bioSketchOrigFileName;
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
                "BiographicalSketchEntity [propPersId=%s, propPrepRevnId=%s, bioSketchDocfileLoc=%s, bioSketchDocpageCount=%s, bioSketchText=%s, bioSketchOrigFileName=%s]",
                propPersId, propPrepRevnId, bioSketchDocfileLoc, bioSketchDocpageCount, bioSketchText,
                bioSketchOrigFileName);
    }

}
