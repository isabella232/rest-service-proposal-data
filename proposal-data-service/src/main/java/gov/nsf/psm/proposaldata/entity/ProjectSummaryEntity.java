package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_proj_smry")
public class ProjectSummaryEntity extends BaseEntity {

    @Id
    @Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
    private Long propPrepRevisionId;

    @Column(name = "proj_smry_doc_file_loc")
    private String projectSummaryDocFileLocation;

    @Column(name = "proj_smry_doc_page_cnt")
    private int projectSummaryDocPageCount;

    @Column(name = "proj_smry_ovrv_txt")
    private String projectSummaryOverViewText;

    @Column(name = "proj_smry_intul_mert_txt")
    private String projectSummaryIntellectualMeritText;

    @Column(name = "proj_smry_brodr_impt_txt")
    private String projectSummaryBroderImpactText;

    @Column(name = "proj_smry_orig_file_name")
    private String projectSummaryOrigFileName;

    public ProjectSummaryEntity() {
        /**
         * default constructor
         */

    }

    public Long getPropPrepRevisionId() {
        return propPrepRevisionId;
    }

    public void setPropPrepRevisionId(Long propPrepRevisionId) {
        this.propPrepRevisionId = propPrepRevisionId;
    }

    public String getProjectSummaryDocFileLocation() {
        return projectSummaryDocFileLocation;
    }

    public void setProjectSummaryDocFileLocation(String projectSummaryDocFileLocation) {
        this.projectSummaryDocFileLocation = projectSummaryDocFileLocation;
    }

    public int getProjectSummaryDocPageCount() {
        return projectSummaryDocPageCount;
    }

    public void setProjectSummaryDocPageCount(int projectSummaryDocPageCount) {
        this.projectSummaryDocPageCount = projectSummaryDocPageCount;
    }

    public String getProjectSummaryOverViewText() {
        return projectSummaryOverViewText;
    }

    public void setProjectSummaryOverViewText(String projectSummaryOverViewText) {
        this.projectSummaryOverViewText = projectSummaryOverViewText;
    }

    public String getProjectSummaryIntellectualMeritText() {
        return projectSummaryIntellectualMeritText;
    }

    public void setProjectSummaryIntellectualMeritText(String projectSummaryIntellectualMeritText) {
        this.projectSummaryIntellectualMeritText = projectSummaryIntellectualMeritText;
    }

    public String getProjectSummaryBroderImpactText() {
        return projectSummaryBroderImpactText;
    }

    public void setProjectSummaryBroderImpactText(String projectSummaryBroderImpactText) {
        this.projectSummaryBroderImpactText = projectSummaryBroderImpactText;
    }

    public String getProjectSummaryOrigFileName() {
        return projectSummaryOrigFileName;
    }

    public void setProjectSummaryOrigFileName(String projectSummaryOrigFileName) {
        this.projectSummaryOrigFileName = projectSummaryOrigFileName;
    }

    @Override
    public String toString() {
        return "ProjectSummaryEntity [propPrepRevisionId=" + propPrepRevisionId + ", projectSummaryDocFileLocation="
                + projectSummaryDocFileLocation + ", projectSummaryDocPageCount=" + projectSummaryDocPageCount
                + ", projectSummaryOverViewText=" + projectSummaryOverViewText
                + ", projectSummaryIntellectualMeritText=" + projectSummaryIntellectualMeritText
                + ", projectSummaryBroderImpactText=" + projectSummaryBroderImpactText + ", projectSummaryOrigFileName="
                + projectSummaryOrigFileName + "]";
    }

}
