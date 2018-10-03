package gov.nsf.psm.proposaldata.repository.query;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import gov.nsf.psm.proposaldata.entity.ProposalPrep;

public interface ProposalSearchRepository
		extends Repository<ProposalPrep, Long>, JpaSpecificationExecutor<ProposalPrep> {

	@EntityGraph(value = "ProposalPrep.revisions", type = EntityGraphType.LOAD)
	@Override
	public List<ProposalPrep> findAll(Specification<ProposalPrep> specification);
}
