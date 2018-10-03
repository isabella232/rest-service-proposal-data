package gov.nsf.psm.proposaldata.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.annotations.Type;

/**
 * @author pkatrapa
 *
 */
@Entity
@Table(name = "prop_cvr_shet")
public class CoverSheetEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_cvr_shet_id", nullable = false, insertable = false, updatable = false)
    private Long propCoverSheetId;

    @Column(name = "prop_prep_revn_id", nullable = false)
    private Long propRevId;

    @Column(name = "awde_org_id", nullable = false)
    private String awdOrgId;

    @Column(name = "tz")
    private String timeZone;

    @Column(name = "duns_num", nullable = false)
    private String dunsNumber;

    @Column(name = "empr_TIN")
    private String employerTIN;

    @Type(type = "boolean")
    @Column(name = "for_prft_ind", nullable = false)
    private boolean forProfit;

    @Type(type = "boolean")
    @Column(name = "smal_bus_ind", nullable = false)
    private boolean smallBusiness;

    @Type(type = "boolean")
    @Column(name = "minr_bus_ind", nullable = false)
    private boolean minorityBusiness;

    @Type(type = "boolean")
    @Column(name = "wmen_own_bus_ind", nullable = false)
    private boolean womenOwnedBusiness;

    @Type(type = "boolean")
    @Column(name = "bgn_invstg_ind", nullable = false)
    private boolean beginningInvestigator;

    @Type(type = "boolean")
    @Column(name = "dcls_loby_acty_ind", nullable = false)
    private boolean disclosureLobbyingActivities;

    @Type(type = "boolean")
    @Column(name = "prptry_prvl_info_ind", nullable = false)
    private boolean proprietaryPrivilegedInformation;

    @Type(type = "boolean")
    @Column(name = "hist_plce_ind", nullable = false)
    private boolean historicPlaces;

    @Type(type = "boolean")
    @Column(name = "vrtb_anim_ind", nullable = false)
    private boolean vertebrateAnimals;

    @Column(name = "iacuc_appl_date", nullable = false)
    private Date iacucAppDate;

    @Column(name = "phs_anim_wlfr_asur_num", nullable = false)
    private String phsAnimalWelfareAssuranceNumber;

    @Type(type = "boolean")
    @Column(name = "humn_subj_ind", nullable = false)
    private boolean humanSubjects;

    @Column(name = "expt_sbsn", nullable = false)
    private String exemptionSubsection;

    @Column(name = "irb_appl_date", nullable = false)
    private Date irbAppDate;

    @Column(name = "humn_subj_asur_num", nullable = false)
    private String humanSubjectsAssurancNumber;

    @Type(type = "boolean")
    @Column(name = "intl_acty_ind", nullable = false)
    private boolean intlActivities;

    @Column(name = "rqst_strt_date", nullable = false)
    private Date requestedStartDate;

    @Column(name = "prop_dur", nullable = false)
    private int proposalDuration;

    @Column(name = "vrtb_anim_aprv_pend_type", nullable = false)
    private String vrtbAnimalAPType;

    @Column(name = "humn_subj_aprv_pend_expt_type", nullable = false)
    private String humanSubjectAPEType;

    @Type(type = "boolean")
    @Column(name = "pcv_ck_ind", nullable = false)
    private boolean pcvCheckIndicator;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "coverSheetEntity", targetEntity = PPOPEntity.class)
    private PPOPEntity ppopEntity;

    @OneToMany(mappedBy = "coverSheetEntity", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = InternationalActivitiesEntity.class)
    private List<InternationalActivitiesEntity> intActivities;

    @OneToMany(mappedBy = "coverSheetEntity", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = OtherFederalAgenciesEntity.class)
    private List<OtherFederalAgenciesEntity> othFedAgencies;

    /**
     * 
     */
    public CoverSheetEntity() {
        /**
         * default constructor
         */
    }

    /**
     * @return the propCoverSheetId
     */
    public Long getPropCoverSheetId() {
        return propCoverSheetId;
    }

    /**
     * @param propCoverSheetId
     *            the propCoverSheetId to set
     */
    public void setPropCoverSheetId(Long propCoverSheetId) {
        this.propCoverSheetId = propCoverSheetId;
    }

    /**
     * @return the propRevId
     */
    public Long getPropRevId() {
        return propRevId;
    }

    /**
     * @param propRevId
     *            the propRevId to set
     */
    public void setPropRevId(Long propRevId) {
        this.propRevId = propRevId;
    }

    /**
     * @return the awdOrgId
     */
    public String getAwdOrgId() {
        return awdOrgId;
    }

    /**
     * @param awdOrgId
     *            the awdOrgId to set
     */
    public void setAwdOrgId(String awdOrgId) {
        this.awdOrgId = awdOrgId;
    }

    /**
     * @return the timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @param timeZone
     *            the timeZone to set
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * @return the dunsNumber
     */
    public String getDunsNumber() {
        return dunsNumber;
    }

    /**
     * @param dunsNumber
     *            the dunsNumber to set
     */
    public void setDunsNumber(String dunsNumber) {
        this.dunsNumber = dunsNumber;
    }

    /**
     * @return the employerTIN
     */
    public String getEmployerTIN() {
        return employerTIN;
    }

    /**
     * @param employerTIN
     *            the employerTIN to set
     */
    public void setEmployerTIN(String employerTIN) {
        this.employerTIN = employerTIN;
    }

    /**
     * @return the forProfit
     */
    public boolean isForProfit() {
        return forProfit;
    }

    /**
     * @param forProfit
     *            the forProfit to set
     */
    public void setForProfit(boolean forProfit) {
        this.forProfit = forProfit;
    }

    /**
     * @return the smallBusiness
     */
    public boolean isSmallBusiness() {
        return smallBusiness;
    }

    /**
     * @param smallBusiness
     *            the smallBusiness to set
     */
    public void setSmallBusiness(boolean smallBusiness) {
        this.smallBusiness = smallBusiness;
    }

    /**
     * @return the minorityBusiness
     */
    public boolean isMinorityBusiness() {
        return minorityBusiness;
    }

    /**
     * @param minorityBusiness
     *            the minorityBusiness to set
     */
    public void setMinorityBusiness(boolean minorityBusiness) {
        this.minorityBusiness = minorityBusiness;
    }

    /**
     * @return the womenOwnedBusiness
     */
    public boolean isWomenOwnedBusiness() {
        return womenOwnedBusiness;
    }

    /**
     * @param womenOwnedBusiness
     *            the womenOwnedBusiness to set
     */
    public void setWomenOwnedBusiness(boolean womenOwnedBusiness) {
        this.womenOwnedBusiness = womenOwnedBusiness;
    }

    /**
     * @return the beginningInvestigator
     */
    public boolean isBeginningInvestigator() {
        return beginningInvestigator;
    }

    /**
     * @param beginningInvestigator
     *            the beginningInvestigator to set
     */
    public void setBeginningInvestigator(boolean beginningInvestigator) {
        this.beginningInvestigator = beginningInvestigator;
    }

    /**
     * @return the disclosureLobbyingActivities
     */
    public boolean isDisclosureLobbyingActivities() {
        return disclosureLobbyingActivities;
    }

    /**
     * @param disclosureLobbyingActivities
     *            the disclosureLobbyingActivities to set
     */
    public void setDisclosureLobbyingActivities(boolean disclosureLobbyingActivities) {
        this.disclosureLobbyingActivities = disclosureLobbyingActivities;
    }

    /**
     * @return the proprietaryPrivilegedInformation
     */
    public boolean isProprietaryPrivilegedInformation() {
        return proprietaryPrivilegedInformation;
    }

    /**
     * @param proprietaryPrivilegedInformation
     *            the proprietaryPrivilegedInformation to set
     */
    public void setProprietaryPrivilegedInformation(boolean proprietaryPrivilegedInformation) {
        this.proprietaryPrivilegedInformation = proprietaryPrivilegedInformation;
    }

    /**
     * @return the historicPlaces
     */
    public boolean isHistoricPlaces() {
        return historicPlaces;
    }

    /**
     * @param historicPlaces
     *            the historicPlaces to set
     */
    public void setHistoricPlaces(boolean historicPlaces) {
        this.historicPlaces = historicPlaces;
    }

    /**
     * @return the vertebrateAnimals
     */
    public boolean isVertebrateAnimals() {
        return vertebrateAnimals;
    }

    /**
     * @param vertebrateAnimals
     *            the vertebrateAnimals to set
     */
    public void setVertebrateAnimals(boolean vertebrateAnimals) {
        this.vertebrateAnimals = vertebrateAnimals;
    }

    /**
     * @return the iacucAppDate
     */
    public Date getIacucAppDate() {
        return (Date) ObjectUtils.clone(this.iacucAppDate);
    }

    /**
     * @param iacucAppDate
     *            the iacucAppDate to set
     */
    public void setIacucAppDate(Date iacucAppDate) {
        this.iacucAppDate = (Date) ObjectUtils.clone(iacucAppDate);
    }

    /**
     * @return the phsAnimalWelfareAssuranceNumber
     */
    public String getPhsAnimalWelfareAssuranceNumber() {
        return phsAnimalWelfareAssuranceNumber;
    }

    /**
     * @param phsAnimalWelfareAssuranceNumber
     *            the phsAnimalWelfareAssuranceNumber to set
     */
    public void setPhsAnimalWelfareAssuranceNumber(String phsAnimalWelfareAssuranceNumber) {
        this.phsAnimalWelfareAssuranceNumber = phsAnimalWelfareAssuranceNumber;
    }

    /**
     * @return the humanSubjects
     */
    public boolean isHumanSubjects() {
        return humanSubjects;
    }

    /**
     * @param humanSubjects
     *            the humanSubjects to set
     */
    public void setHumanSubjects(boolean humanSubjects) {
        this.humanSubjects = humanSubjects;
    }

    /**
     * @return the exemptionSubsection
     */
    public String getExemptionSubsection() {
        return exemptionSubsection;
    }

    /**
     * @param exemptionSubsection
     *            the exemptionSubsection to set
     */
    public void setExemptionSubsection(String exemptionSubsection) {
        this.exemptionSubsection = exemptionSubsection;
    }

    /**
     * @return the irbAppDate
     */
    public Date getIrbAppDate() {
        return (Date) ObjectUtils.clone(this.irbAppDate);
    }

    /**
     * @param irbAppDate
     *            the irbAppDate to set
     */
    public void setIrbAppDate(Date irbAppDate) {
        this.irbAppDate = (Date) ObjectUtils.clone(irbAppDate);
    }

    /**
     * @return the humanSubjectsAssurancNumber
     */
    public String getHumanSubjectsAssurancNumber() {
        return humanSubjectsAssurancNumber;
    }

    /**
     * @param humanSubjectsAssurancNumber
     *            the humanSubjectsAssurancNumber to set
     */
    public void setHumanSubjectsAssurancNumber(String humanSubjectsAssurancNumber) {
        this.humanSubjectsAssurancNumber = humanSubjectsAssurancNumber;
    }

    /**
     * @return the intlActivities
     */
    public boolean isIntlActivities() {
        return intlActivities;
    }

    /**
     * @param intlActivities
     *            the intlActivities to set
     */
    public void setIntlActivities(boolean intlActivities) {
        this.intlActivities = intlActivities;
    }

    /**
     * @return the requestedStartDate
     */
    public Date getRequestedStartDate() {
        return (Date) ObjectUtils.clone(this.requestedStartDate);
    }

    /**
     * @param requestedStartDate
     *            the requestedStartDate to set
     */
    public void setRequestedStartDate(Date requestedStartDate) {
        this.requestedStartDate = (Date) ObjectUtils.clone(requestedStartDate);
    }

    /**
     * @return the proposalDuration
     */
    public int getProposalDuration() {
        return proposalDuration;
    }

    /**
     * @param proposalDuration
     *            the proposalDuration to set
     */
    public void setProposalDuration(int proposalDuration) {
        this.proposalDuration = proposalDuration;
    }

    /**
     * @return the vrtbAnimalAPType
     */
    public String getVrtbAnimalAPType() {
        return vrtbAnimalAPType;
    }

    /**
     * @param vrtbAnimalAPType
     *            the vrtbAnimalAPType to set
     */
    public void setVrtbAnimalAPType(String vrtbAnimalAPType) {
        this.vrtbAnimalAPType = vrtbAnimalAPType;
    }

    /**
     * @return the humanSubjectAPEType
     */
    public String getHumanSubjectAPEType() {
        return humanSubjectAPEType;
    }

    /**
     * @param humanSubjectAPEType
     *            the humanSubjectAPEType to set
     */
    public void setHumanSubjectAPEType(String humanSubjectAPEType) {
        this.humanSubjectAPEType = humanSubjectAPEType;
    }

    /**
     * @return the pcvCheckIndicator
     */
    public boolean isPcvCheckIndicator() {
        return this.pcvCheckIndicator;
    }

    /**
     * @param pcvCheckIndicator
     *            the pcvCheckIndicator to set
     */
    public void setPcvCheckIndicator(boolean pcvCheckIndicator) {
        this.pcvCheckIndicator = pcvCheckIndicator;
    }

    /**
     * @return the ppopEntity
     */
    public PPOPEntity getPpopEntity() {
        return ppopEntity;
    }

    /**
     * @param ppopEntity
     *            the ppopEntity to set
     */
    public void setPpopEntity(PPOPEntity ppopEntity) {
        this.ppopEntity = ppopEntity;
    }

    /**
     * @return the intActivities
     */
    public List<InternationalActivitiesEntity> getIntActivities() {
        return intActivities;
    }

    /**
     * @param intActivities
     *            the intActivities to set
     */
    public void setIntActivities(List<InternationalActivitiesEntity> intActivities) {
        this.intActivities = intActivities;
    }

    /**
     * @return the othFedAgencies
     */
    public List<OtherFederalAgenciesEntity> getOthFedAgencies() {
        return othFedAgencies;
    }

    /**
     * @param othFedAgencies
     *            the othFedAgencies to set
     */
    public void setOthFedAgencies(List<OtherFederalAgenciesEntity> othFedAgencies) {
        this.othFedAgencies = othFedAgencies;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "CoverSheetEntity [propCoverSheetId=%s, propRevId=%s, awdOrgId=%s, timeZone=%s, dunsNumber=%s, employerTIN=%s, forProfit=%s, smallBusiness=%s, minorityBusiness=%s, womenOwnedBusiness=%s, beginningInvestigator=%s, disclosureLobbyingActivities=%s, proprietaryPrivilegedInformation=%s, historicPlaces=%s, vertebrateAnimals=%s, iacucAppDate=%s, phsAnimalWelfareAssuranceNumber=%s, humanSubjects=%s, exemptionSubsection=%s, irbAppDate=%s, humanSubjectsAssurancNumber=%s, intlActivities=%s, requestedStartDate=%s, proposalDuration=%s, vrtbAnimalAPType=%s, humanSubjectAPEType=%s, ppopEntity=%s, intActivities=%s, othFedAgencies=%s]",
                propCoverSheetId, propRevId, awdOrgId, timeZone, dunsNumber, employerTIN, forProfit, smallBusiness,
                minorityBusiness, womenOwnedBusiness, beginningInvestigator, disclosureLobbyingActivities,
                proprietaryPrivilegedInformation, historicPlaces, vertebrateAnimals, iacucAppDate,
                phsAnimalWelfareAssuranceNumber, humanSubjects, exemptionSubsection, irbAppDate,
                humanSubjectsAssurancNumber, intlActivities, requestedStartDate, proposalDuration, vrtbAnimalAPType,
                humanSubjectAPEType, ppopEntity, intActivities, othFedAgencies);
    }

}
