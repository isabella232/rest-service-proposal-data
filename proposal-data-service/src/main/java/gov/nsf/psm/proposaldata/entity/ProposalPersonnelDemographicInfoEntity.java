package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_pers_dmog_info")
public class ProposalPersonnelDemographicInfoEntity extends BaseEntity {

	public ProposalPersonnelDemographicInfoEntity() {
		/**
		 * default constructor
		 */
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prop_pers_dmog_info_id", nullable = false, insertable = false, updatable = false)
	private Integer propPersDemographicInfoId;

	@Column(name = "prop_pers_id", nullable = true)
	private Integer propPersId;

	@Column(name = "user_phon_num", nullable = true)
	private String userPhoneNumber;

	@Column(name = "user_fax_num", nullable = true)
	private String userFaxNumber;

	@Column(name = "user_emai_addr", nullable = true)
	private String userEmailAddress;

	@Column(name = "user_acad_degr", nullable = true)
	private String userAcademicDegree;

	@Column(name = "user_acad_yr", nullable = true)
	private Integer userAcademicYear;

	@Column(name = "user_dept_name", nullable = true)
	private String userDeptName;

	public Integer getPropPersDemographicInfoId() {
		return propPersDemographicInfoId;
	}

	public void setPropPersDemographicInfoId(Integer propPersDemographicInfoId) {
		this.propPersDemographicInfoId = propPersDemographicInfoId;
	}

	public Integer getPropPersId() {
		return propPersId;
	}

	public void setPropPersId(Integer propPersId) {
		this.propPersId = propPersId;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserFaxNumber() {
		return userFaxNumber;
	}

	public void setUserFaxNumber(String userFaxNumber) {
		this.userFaxNumber = userFaxNumber;
	}

	public String getUserEmailAddress() {
		return userEmailAddress;
	}

	public void setUserEmailAddress(String userEmailAddress) {
		this.userEmailAddress = userEmailAddress;
	}

	public String getUserDeptName() {
		return userDeptName;
	}

	public void setUserDeptName(String userDeptName) {
		this.userDeptName = userDeptName;
	}

	public String getUserAcademicDegree() {
		return userAcademicDegree;
	}

	public void setUserAcademicDegree(String userAcademicDegree) {
		this.userAcademicDegree = userAcademicDegree;
	}

	public Integer getUserAcademicYear() {
		return userAcademicYear;
	}

	public void setUserAcademicYear(Integer userAcademicYear) {
		this.userAcademicYear = userAcademicYear;
	}

}
