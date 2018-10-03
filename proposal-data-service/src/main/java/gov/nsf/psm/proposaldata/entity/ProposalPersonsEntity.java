package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_pers")
public class ProposalPersonsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_pers_id", nullable = false)
    private Long proposalPersonId;

    @Column(name = "prop_prep_revn_id", nullable = false)
    private Long propPrepRevisionId;

    @Column(name = "prop_pers_role_code", nullable = false)
    private String propPersonRoleCode;

    @Column(name = "prop_pers_nsf_id", nullable = false)
    private String propPersonNsfId;

    @Column(name = "prop_pers_inst_id", nullable = false)
    private String propPersInstitutionId;

    @Column(name = "oth_sr_pers_frst_name", nullable = false)
    private String othrSeniorPersonFirstName;

    @Column(name = "oth_sr_pers_last_name", nullable = false)
    private String othrSeniorPersonLasttName;

    @Column(name = "oth_sr_pers_mid_init", nullable = false)
    private String othrSeniorPersonMiddleInitial;

    @Column(name = "emai_addr")
    private String emailAddr;

    @Column(name = "user_phon_num")
    private String phoneNumber;

    @Column(name = "user_fax_num")
    private String faxNumber;

    @Column(name = "user_acad_degr")
    private String acadDegree;

    @Column(name = "user_acad_yr")
    private int acadYear;

    @Column(name = "inst_name_at_subm")
    private String instNameAtSubmission;

    @Column(name = "user_dept_name")
    private String deptName;

    @Column(name = "pers_str_addr_line_1")
    private String addrLine1;

    @Column(name = "pers_str_addr_line_2")
    private String addrLine2;

    @Column(name = "pers_city_name")
    private String cityName;

    @Column(name = "pers_st_code")
    private String stateCode;

    @Column(name = "pers_ctry_code")
    private String countryCode;

    @Column(name = "pers_pstl_code")
    private String postalCode;

    public ProposalPersonsEntity() {
        /**
         * default constructor
         */
    }

    public ProposalPersonsEntity(Long propPrepRevisionId, String propPersonRoleCode, String propPersonNsfId,
            String propPersInstitutionId, String othrSeniorPersonFirstName, String othrSeniorPersonLasttName,
            String othrSeniorPersonMiddleInitial) {
        super();
        this.propPrepRevisionId = propPrepRevisionId;
        this.propPersonRoleCode = propPersonRoleCode;
        this.propPersonNsfId = propPersonNsfId;
        this.propPersInstitutionId = propPersInstitutionId;
        this.othrSeniorPersonFirstName = othrSeniorPersonFirstName;
        this.othrSeniorPersonLasttName = othrSeniorPersonLasttName;
        this.othrSeniorPersonMiddleInitial = othrSeniorPersonMiddleInitial;
    }

    public Long getProposalPersonId() {
        return proposalPersonId;
    }

    public void setProposalPersonId(Long proposalPersonId) {
        this.proposalPersonId = proposalPersonId;
    }

    public Long getPropPrepRevisionId() {
        return propPrepRevisionId;
    }

    public void setPropPrepRevisionId(Long propPrepRevisionId) {
        this.propPrepRevisionId = propPrepRevisionId;
    }

    public String getPropPersonRoleCode() {
        return propPersonRoleCode;
    }

    public void setPropPersonRoleCode(String propPersonRoleCode) {
        this.propPersonRoleCode = propPersonRoleCode;
    }

    public String getPropPersonNsfId() {
        return propPersonNsfId;
    }

    public void setPropPersonNsfId(String propPersonNsfId) {
        this.propPersonNsfId = propPersonNsfId;
    }

    public String getPropPersInstitutionId() {
        return propPersInstitutionId;
    }

    public void setPropPersInstitutionId(String propPersInstitutionId) {
        this.propPersInstitutionId = propPersInstitutionId;
    }

    public String getOthrSeniorPersonFirstName() {
        return othrSeniorPersonFirstName;
    }

    public void setOthrSeniorPersonFirstName(String othrSeniorPersonFirstName) {
        this.othrSeniorPersonFirstName = othrSeniorPersonFirstName;
    }

    public String getOthrSeniorPersonLasttName() {
        return othrSeniorPersonLasttName;
    }

    public void setOthrSeniorPersonLasttName(String othrSeniorPersonLasttName) {
        this.othrSeniorPersonLasttName = othrSeniorPersonLasttName;
    }

    public String getOthrSeniorPersonMiddleInitial() {
        return othrSeniorPersonMiddleInitial;
    }

    public void setOthrSeniorPersonMiddleInitial(String othrSeniorPersonMiddleInitial) {
        this.othrSeniorPersonMiddleInitial = othrSeniorPersonMiddleInitial;
    }

    /**
     * @return the emailAddr
     */
    public String getEmailAddr() {
        return this.emailAddr;
    }

    /**
     * @param emailAddr
     *            the emailAddr to set
     */
    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the faxNumber
     */
    public String getFaxNumber() {
        return this.faxNumber;
    }

    /**
     * @param faxNumber
     *            the faxNumber to set
     */
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    /**
     * @return the acadDegree
     */
    public String getAcadDegree() {
        return this.acadDegree;
    }

    /**
     * @param acadDegree
     *            the acadDegree to set
     */
    public void setAcadDegree(String acadDegree) {
        this.acadDegree = acadDegree;
    }

    /**
     * @return the acadYear
     */
    public int getAcadYear() {
        return this.acadYear;
    }

    /**
     * @param acadYear
     *            the acadYear to set
     */
    public void setAcadYear(int acadYear) {
        this.acadYear = acadYear;
    }

    /**
     * @return the instNameAtSubmission
     */
    public String getInstNameAtSubmission() {
        return this.instNameAtSubmission;
    }

    /**
     * @param instNameAtSubmission
     *            the instNameAtSubmission to set
     */
    public void setInstNameAtSubmission(String instNameAtSubmission) {
        this.instNameAtSubmission = instNameAtSubmission;
    }

    /**
     * @return the deptName
     */
    public String getDeptName() {
        return this.deptName;
    }

    /**
     * @param deptName
     *            the deptName to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * @return the addrLine1
     */
    public String getAddrLine1() {
        return this.addrLine1;
    }

    /**
     * @param addrLine1
     *            the addrLine1 to set
     */
    public void setAddrLine1(String addrLine1) {
        this.addrLine1 = addrLine1;
    }

    /**
     * @return the addrLine2
     */
    public String getAddrLine2() {
        return this.addrLine2;
    }

    /**
     * @param addrLine2
     *            the addrLine2 to set
     */
    public void setAddrLine2(String addrLine2) {
        this.addrLine2 = addrLine2;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return this.cityName;
    }

    /**
     * @param cityName
     *            the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the stateCode
     */
    public String getStateCode() {
        return this.stateCode;
    }

    /**
     * @param stateCode
     *            the stateCode to set
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return this.countryCode;
    }

    /**
     * @param countryCode
     *            the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return this.postalCode;
    }

    /**
     * @param postalCode
     *            the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "ProposalPersonsEntity [proposalPersonId=%s, propPrepRevisionId=%s, propPersonRoleCode=%s, propPersonNsfId=%s, propPersInstitutionId=%s, othrSeniorPersonFirstName=%s, othrSeniorPersonLasttName=%s, othrSeniorPersonMiddleInitial=%s, emailAddr=%s, phoneNumber=%s, faxNumber=%s, acadDegree=%s, acadYear=%s, instNameAtSubmission=%s, deptName=%s, addrLine1=%s, addrLine2=%s, cityName=%s, stateCode=%s, countryCode=%s, postalCode=%s]",
                this.proposalPersonId, this.propPrepRevisionId, this.propPersonRoleCode, this.propPersonNsfId,
                this.propPersInstitutionId, this.othrSeniorPersonFirstName, this.othrSeniorPersonLasttName,
                this.othrSeniorPersonMiddleInitial, this.emailAddr, this.phoneNumber, this.faxNumber, this.acadDegree,
                this.acadYear, this.instNameAtSubmission, this.deptName, this.addrLine1, this.addrLine2, this.cityName,
                this.stateCode, this.countryCode, this.postalCode);
    }

}
