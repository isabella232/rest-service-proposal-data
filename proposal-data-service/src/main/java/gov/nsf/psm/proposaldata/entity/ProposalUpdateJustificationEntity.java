package gov.nsf.psm.proposaldata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "prop_updt_jstf")
public class ProposalUpdateJustificationEntity extends BaseEntity {

	public ProposalUpdateJustificationEntity() {
		super();
	}

	@Id
	@Column(name = "prop_prep_revn_id", nullable = false, insertable = false, updatable = false)
	private Long propRevId;

	@Column(name = "prop_updt_jstf_txt", nullable = false)
	private String proposalUpdateJustificationText;

	@Column(name = "cre_user", nullable = false)
	private String createdUser;

	@Column(name = "cre_date", nullable = false)
	private Date createdDate;

	public Long getPropRevId() {
		return propRevId;
	}

	public void setPropRevId(Long propRevId) {
		this.propRevId = propRevId;
	}

	public String getProposalUpdateJustificationText() {
		return proposalUpdateJustificationText;
	}

	public void setProposalUpdateJustificationText(String proposalUpdateJustificationText) {
		this.proposalUpdateJustificationText = proposalUpdateJustificationText;
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
