package gov.nsf.psm.proposaldata.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BudgetCostEntity extends BaseEntity {

    @Column(name = "prop_inst_rec_id", nullable = false)
    Long propInstRecId;

    @Column(name = "budg_yr", nullable = false)
    int budgetYear;

    public BudgetCostEntity() {
        /**
         * default constructor
         */
    }

    public Long getPropInstRecId() {
        return propInstRecId;
    }

    public void setPropInstRecId(Long propInstRecId) {
        this.propInstRecId = propInstRecId;
    }

    public int getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(int budgetYear) {
        this.budgetYear = budgetYear;
    }

}
