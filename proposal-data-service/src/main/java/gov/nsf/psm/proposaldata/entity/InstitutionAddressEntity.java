package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_inst_addr")
public class InstitutionAddressEntity extends BaseEntity {

	public InstitutionAddressEntity() {
		/**
		 * default constructor
		 */
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prop_inst_addr_id", nullable = false, insertable = false, updatable = false)
	private Integer propInstAddrId;

	@Column(name = "prop_inst_rec_id", nullable = false)
	private Integer propInstRecId;

	@Column(name = "inst_name", nullable = true)
	private String instName;

	@Column(name = "inst_str_addr_1", nullable = true)
	private String instStreetAddress1;

	@Column(name = "inst_str_addr_2", nullable = true)
	private String instStreetAddress2;

	@Column(name = "inst_city_name", nullable = true)
	private String instCityName;

	@Column(name = "inst_st_code", nullable = true)
	private String instStateCode;

	@Column(name = "inst_pstl_code", nullable = true)
	private String instPostalCode;

	@Column(name = "inst_ctry_code", nullable = true)
	private String instCountryCode;

	public Integer getPropInstAddrId() {
		return propInstAddrId;
	}

	public void setPropInstAddrId(Integer propInstAddrId) {
		this.propInstAddrId = propInstAddrId;
	}

	public Integer getPropInstRecId() {
		return propInstRecId;
	}

	public void setPropInstRecId(Integer propInstRecId) {
		this.propInstRecId = propInstRecId;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getInstStreetAddress1() {
		return instStreetAddress1;
	}

	public void setInstStreetAddress1(String instStreetAddress1) {
		this.instStreetAddress1 = instStreetAddress1;
	}

	public String getInstStreetAddress2() {
		return instStreetAddress2;
	}

	public void setInstStreetAddress2(String instStreetAddress2) {
		this.instStreetAddress2 = instStreetAddress2;
	}

	public String getInstCityName() {
		return instCityName;
	}

	public void setInstCityName(String instCityName) {
		this.instCityName = instCityName;
	}

	public String getInstStateCode() {
		return instStateCode;
	}

	public void setInstStateCode(String instStateCode) {
		this.instStateCode = instStateCode;
	}

	public String getInstPostalCode() {
		return instPostalCode;
	}

	public void setInstPostalCode(String instPostalCode) {
		this.instPostalCode = instPostalCode;
	}

	public String getInstCountryCode() {
		return instCountryCode;
	}

	public void setInstCountryCode(String instCountryCode) {
		this.instCountryCode = instCountryCode;
	}

	

}
