package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "fund_opp_excl_list")
public class FundingOppExlclusionEntity extends BaseEntity {
	@Id
	@Column(name = "fund_opp_id", nullable = false)
	private String fundOppId;

	@Type(type = "boolean")
	@Column(name = "fund_opp_excl_flag", nullable = false)
	private boolean fundOppExclFlag;

	public FundingOppExlclusionEntity() {
		/**
		 * default constructor
		 */
	}

	public String getFundOppId() {
		return fundOppId;
	}

	public void setFundOppId(String fundOppId) {
		this.fundOppId = fundOppId;
	}

	public boolean isFundOppExclFlag() {
		return fundOppExclFlag;
	}

	public void setFundOppExclFlag(boolean fundOppExclFlag) {
		this.fundOppExclFlag = fundOppExclFlag;
	}

}
