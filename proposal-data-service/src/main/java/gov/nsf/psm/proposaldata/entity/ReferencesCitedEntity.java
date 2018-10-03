package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_ref_citn")
public class ReferencesCitedEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevnId;

    @Column(name = "ref_citn_doc_file_loc")
    private String refCitnDocfileLoc;

    @Column(name = "ref_citn_doc_page_cnt")
    private int refCitnDocpageCount;

    @Column(name = "ref_citn_txt")
    private String refCitnText;

    @Column(name = "ref_citn_orig_file_name")
    private String refCitnOrigFileName;

    public Long getPropPrepRevnId() {
        return propPrepRevnId;
    }

    public void setPropPrepRevnId(Long propPrepRevnId) {
        this.propPrepRevnId = propPrepRevnId;
    }

    public String getRefCitnDocfileLoc() {
        return refCitnDocfileLoc;
    }

    public void setRefCitnDocfileLoc(String refCitnDocfileLoc) {
        this.refCitnDocfileLoc = refCitnDocfileLoc;
    }

    public int getRefCitnDocpageCount() {
        return refCitnDocpageCount;
    }

    public void setRefCitnDocpageCount(int refCitnDocpageCount) {
        this.refCitnDocpageCount = refCitnDocpageCount;
    }

    public String getRefCitnText() {
        return refCitnText;
    }

    public void setRefCitnText(String refCitnText) {
        this.refCitnText = refCitnText;
    }

    public String getRefCitnOrigFileName() {
        return refCitnOrigFileName;
    }

    public void setRefCitnOrigFileName(String refCitnOrigFileName) {
        this.refCitnOrigFileName = refCitnOrigFileName;
    }

    @Override
    public String toString() {
        return "ReferencesCitedEntity [propPrepRevnId=" + propPrepRevnId + ", refCitnDocfileLoc=" + refCitnDocfileLoc
                + ", refCitnDocpageCount=" + refCitnDocpageCount + ", refCitnText=" + refCitnText
                + ", refCitnOrigFileName=" + refCitnOrigFileName + "]";
    }

}
