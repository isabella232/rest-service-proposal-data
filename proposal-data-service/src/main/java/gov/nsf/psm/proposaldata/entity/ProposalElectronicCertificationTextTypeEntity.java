package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_elec_sign_type_lkup")
public class ProposalElectronicCertificationTextTypeEntity extends BaseType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prop_elec_cert_type_id", nullable = false, insertable = false, updatable = false)
	private int proposalElectronicCertTypeId;

	@Column(name = "elec_cert_type_code", nullable = false)
	private String electronicCertTypeCode;
	
	@Column(name = "elec_cert_type_desc", nullable = false)
	private String electronicCertTypeDesc;
	
	@Column(name = "elec_cert_vers", nullable = false)
	private String electronicCertTypeVersion;
	
	@Column(name = "elec_cert_scrn_txt_dspl", nullable = false)
	private String electronicCertScreenText;

	public int getProposalElectronicCertTypeId() {
		return proposalElectronicCertTypeId;
	}

	public void setProposalElectronicCertTypeId(int proposalElectronicCertTypeId) {
		this.proposalElectronicCertTypeId = proposalElectronicCertTypeId;
	}

	public String getElectronicCertTypeCode() {
		return electronicCertTypeCode;
	}

	public void setElectronicCertTypeCode(String electronicCertTypeCode) {
		this.electronicCertTypeCode = electronicCertTypeCode;
	}

	public String getElectronicCertTypeDesc() {
		return electronicCertTypeDesc;
	}

	public void setElectronicCertTypeDesc(String electronicCertTypeDesc) {
		this.electronicCertTypeDesc = electronicCertTypeDesc;
	}

	public String getElectronicCertTypeVersion() {
		return electronicCertTypeVersion;
	}

	public void setElectronicCertTypeVersion(String electronicCertTypeVersion) {
		this.electronicCertTypeVersion = electronicCertTypeVersion;
	}

	public String getElectronicCertScreenText() {
		return electronicCertScreenText;
	}

	public void setElectronicCertScreenText(String electronicCertScreenText) {
		this.electronicCertScreenText = electronicCertScreenText;
	}
	
	
}
