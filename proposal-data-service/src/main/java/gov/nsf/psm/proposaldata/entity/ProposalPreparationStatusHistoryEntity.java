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
@Table(name = "prop_prep_stts_hist")
public class ProposalPreparationStatusHistoryEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prop_prep_stts_hist_id", nullable = false, insertable = false, updatable = false)
	private Long propPrepStatusId;

	@Column(name = "prop_prep_revn_id")
	private Long propPrepRevisionId;

	@Column(name = "prop_prep_stts_date")
	private Date propPrepStatusDate;

	@Column(name = "prop_prep_stts_code")
	private String propPrepStatusCode;

	public ProposalPreparationStatusHistoryEntity() {
		super();
	}

	public Long getPropPrepRevisionId() {
		return propPrepRevisionId;
	}

	public void setPropPrepRevisionId(Long propPrepRevisionId) {
		this.propPrepRevisionId = propPrepRevisionId;
	}

	public Date getPropPrepStatusDate() {
		return (Date) ObjectUtils.clone(this.propPrepStatusDate);
	}

	public void setPropPrepStatusDate(Date propPrepStatusDate) {
		this.propPrepStatusDate = (Date) ObjectUtils.clone(propPrepStatusDate);
	}

	public String getPropPrepStatusCode() {
		return propPrepStatusCode;
	}

	public void setPropPrepStatusCode(String propPrepStatusCode) {
		this.propPrepStatusCode = propPrepStatusCode;
	}

	@Override
	public String toString() {
		return String.format(
		        "ProposalPreparationStatusHistoryEntity [propPrepRevisionId=%s, propPrepStatusDate=%s, propPrepStatusCode=%s]",
		        propPrepRevisionId, propPrepStatusDate, propPrepStatusCode);
	}

}
