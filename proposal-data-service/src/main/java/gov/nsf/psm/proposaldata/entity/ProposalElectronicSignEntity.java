package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_elec_sign")
public class ProposalElectronicSignEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prop_elec_sign_id", nullable = false, insertable = false, updatable = false)
	private int propEelecSignId;

	@Column(name = "prop_prep_revn_id", nullable = false)
	private int propPrepRevnId;
	
	@Column(name = "prop_prep_revn_type_code", nullable = false)
	private String propPrepRevnTypeCode;
	
	@Column(name = "prop_elec_cert_type_id", nullable = false)
	private int propEleCertTypeId;
	
	@Column(name = "user_nsf_id", nullable = false)
	private String userNsfId;
	
	@Column(name = "user_last_name", nullable = false)
	private String userLastname;
	
	@Column(name = "user_frst_name", nullable = false)
	private String userFirstName;
	
	@Column(name = "user_mid_init", nullable = false)
	private String userMiddleInit;
	
	@Column(name = "user_fax_num", nullable = false)
	private String userFaxNumber;
	
	@Column(name = "user_phon_num", nullable = false)
	private String userPhoneNumber;
	
	@Column(name = "user_emai_addr", nullable = false)
	private String userEmailAddress;
	
	@Column(name = "ip_addr", nullable = false)
	private String ipAddress;
	
	@Column(name = "inst_id", nullable = false)
	private String institutionId;
	
	@Column(name = "elec_sign_date", nullable = false)
	private Date elecSignDate;
	
	@Column(name = "elec_sign_cert_agremt", nullable = false)
	private String elecSignCertAgreement;
	
	@Column(name = "debr_flag", nullable = false)
	private String debarFlag;
	
	@Column(name = "debr_txt", nullable = false)
	private String debarText;
	
	@Column(name = "cre_user", nullable = false)
	private String createdUser;	

	@Column(name = "cre_date", nullable = false)
	private Date createdDate;

	public int getPropEelecSignId() {
		return propEelecSignId;
	}

	public void setPropEelecSignId(int propEelecSignId) {
		this.propEelecSignId = propEelecSignId;
	}

	public int getPropPrepRevnId() {
		return propPrepRevnId;
	}

	public void setPropPrepRevnId(int propPrepRevnId) {
		this.propPrepRevnId = propPrepRevnId;
	}

	public String getPropPrepRevnTypeCode() {
		return propPrepRevnTypeCode;
	}

	public void setPropPrepRevnTypeCode(String propPrepRevnTypeCode) {
		this.propPrepRevnTypeCode = propPrepRevnTypeCode;
	}

	public int getPropEleCertTypeId() {
		return propEleCertTypeId;
	}

	public void setPropEleCertTypeId(int propEleCertTypeId) {
		this.propEleCertTypeId = propEleCertTypeId;
	}

	public String getUserNsfId() {
		return userNsfId;
	}

	public void setUserNsfId(String userNsfId) {
		this.userNsfId = userNsfId;
	}

	public String getUserLastname() {
		return userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserMiddleInit() {
		return userMiddleInit;
	}

	public void setUserMiddleInit(String userMiddleInit) {
		this.userMiddleInit = userMiddleInit;
	}

	public String getUserFaxNumber() {
		return userFaxNumber;
	}

	public void setUserFaxNumber(String userFaxNumber) {
		this.userFaxNumber = userFaxNumber;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserEmailAddress() {
		return userEmailAddress;
	}

	public void setUserEmailAddress(String userEmailAddress) {
		this.userEmailAddress = userEmailAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}

	public Date getElecSignDate() {
		return (Date) ObjectUtils.clone(this.elecSignDate);
	}

	public void setElecSignDate(Date elecSignDate) {
		this.elecSignDate = (Date) ObjectUtils.clone(elecSignDate);
	}

	public String getElecSignCertAgreement() {
		return elecSignCertAgreement;
	}

	public void setElecSignCertAgreement(String elecSignCertAgreement) {
		this.elecSignCertAgreement = elecSignCertAgreement;
	}

	public String getDebarFlag() {
		return debarFlag;
	}

	public void setDebarFlag(String debarFlag) {
		this.debarFlag = debarFlag;
	}

	public String getDebarText() {
		return debarText;
	}

	public void setDebarText(String debarText) {
		this.debarText = debarText;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedDate() {
		return (Date) ObjectUtils.clone(this.createdDate);
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date) ObjectUtils.clone(createdDate);
	}
	
	
	
}
