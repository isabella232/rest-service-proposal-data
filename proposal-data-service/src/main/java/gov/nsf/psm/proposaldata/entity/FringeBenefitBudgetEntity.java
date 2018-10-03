package gov.nsf.psm.proposaldata.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_budg_frng_bnft_cst")
public class FringeBenefitBudgetEntity extends BudgetCostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_budg_frng_bnft_cst_id", nullable = false)
    private Long fringeBenfitBudgetId;

    @Column(name = "frng_bnft_dol_amt", nullable = false)
    private BigDecimal fringeBenefitDollarAmount;

    @Column(name = "frng_bnft_budg_jstf_txt")
    private String fringeBenefitBudgJustification;

    public FringeBenefitBudgetEntity() {
        /**
         * default constructor
         */
    }

    public FringeBenefitBudgetEntity(Long fringeBenfitBudgetId, Long propInstRecId, int budgetYear,
            BigDecimal fringeBenefitDollarAmount, String fringeBenefitBudgJustification) {
        super();
        this.fringeBenfitBudgetId = fringeBenfitBudgetId;
        this.propInstRecId = propInstRecId;
        this.budgetYear = budgetYear;
        this.fringeBenefitDollarAmount = fringeBenefitDollarAmount;
        this.fringeBenefitBudgJustification = fringeBenefitBudgJustification;
    }

    public BigDecimal getFringeBenefitDollarAmount() {
        return fringeBenefitDollarAmount;
    }

    public void setFringeBenefitDollarAmount(BigDecimal fringeBenefitDollarAmount) {
        this.fringeBenefitDollarAmount = fringeBenefitDollarAmount;
    }

    public String getFringeBenefitBudgJustification() {
        return fringeBenefitBudgJustification;
    }

    public void setFringeBenefitBudgJustification(String fringeBenefitBudgJustification) {
        this.fringeBenefitBudgJustification = fringeBenefitBudgJustification;
    }

    public Long getFringeBenfitBudgetId() {
        return fringeBenfitBudgetId;
    }

    public void setFringeBenfitBudgetId(Long fringeBenfitBudgetId) {
        this.fringeBenfitBudgetId = fringeBenfitBudgetId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "FringeBenefitBudgetEntity [fringeBenfitBudgetId=%s, propInstRecId=%s, budgetYear=%s, fringeBenefitDollarAmount=%s, fringeBenefitBudgJustification=%s]",
                fringeBenfitBudgetId, propInstRecId, budgetYear, fringeBenefitDollarAmount,
                fringeBenefitBudgJustification);
    }
}
