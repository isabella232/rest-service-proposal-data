/**
 * 
 */
package gov.nsf.psm.proposaldata.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author pkatrapa
 *
 */

@Entity
@Table(name = "prop_budg_idir_cst")
public class IndirectBudgetCostEntity extends BudgetCostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_budg_idir_cst_id", nullable = false)
    private Long indirectCostBudgetId;

    @Column(name = "idir_cst_item_name", nullable = false)
    private String indirectCostItemName;

    @Column(name = "idir_cst_rate", nullable = false)
    private double indirectCostRate;

    @Column(name = "idir_cst_base_dol_amt", nullable = false)
    private BigDecimal indirectCostBaseDollarAmount;

    @Column(name = "idir_cst_budg_jstf_txt", nullable = false)
    private String indirectCostBudgetJustificationText;

    /**
     * 
     */
    public IndirectBudgetCostEntity() {
        /**
         * Default constructor
         */
    }

    /**
     * @param propInstRectId
     * @param budgetYear
     * @param indirectCostItemName
     * @param indirectCostRate
     * @param indirectCostBaseDollarAmount
     * @param indirectCostBudgetJustificationText
     */
    public IndirectBudgetCostEntity(Long propInstRecId, int budgetYear, String indirectCostItemName,
            double indirectCostRate, BigDecimal indirectCostBaseDollarAmount,
            String indirectCostBudgetJustificationText) {
        super();
        this.propInstRecId = propInstRecId;
        this.budgetYear = budgetYear;
        this.indirectCostItemName = indirectCostItemName;
        this.indirectCostRate = indirectCostRate;
        this.indirectCostBaseDollarAmount = indirectCostBaseDollarAmount;
        this.indirectCostBudgetJustificationText = indirectCostBudgetJustificationText;
    }

    /**
     * @return the indirectCostBudgetId
     */
    public Long getIndirectCostBudgetId() {
        return indirectCostBudgetId;
    }

    /**
     * @param indirectCostBudgetId
     *            the indirectCostBudgetId to set
     */
    public void setIndirectCostBudgetId(Long indirectCostBudgetId) {
        this.indirectCostBudgetId = indirectCostBudgetId;
    }

    /**
     * @return the indirectCostItemName
     */
    public String getIndirectCostItemName() {
        return indirectCostItemName;
    }

    /**
     * @param indirectCostItemName
     *            the indirectCostItemName to set
     */
    public void setIndirectCostItemName(String indirectCostItemName) {
        this.indirectCostItemName = indirectCostItemName;
    }

    /**
     * @return the indirectCostRate
     */
    public double getIndirectCostRate() {
        return indirectCostRate;
    }

    /**
     * @param indirectCostRate
     *            the indirectCostRate to set
     */
    public void setIndirectCostRate(double indirectCostRate) {
        this.indirectCostRate = indirectCostRate;
    }

    /**
     * @return the indirectCostBaseDollarAmount
     */
    public BigDecimal getIndirectCostBaseDollarAmount() {
        return indirectCostBaseDollarAmount;
    }

    /**
     * @param indirectCostBaseDollarAmount
     *            the indirectCostBaseDollarAmount to set
     */
    public void setIndirectCostBaseDollarAmount(BigDecimal indirectCostBaseDollarAmount) {
        this.indirectCostBaseDollarAmount = indirectCostBaseDollarAmount;
    }

    /**
     * @return the indirectCostBudgetJustificationText
     */
    public String getIndirectCostBudgetJustificationText() {
        return indirectCostBudgetJustificationText;
    }

    /**
     * @param indirectCostBudgetJustificationText
     *            the indirectCostBudgetJustificationText to set
     */
    public void setIndirectCostBudgetJustificationText(String indirectCostBudgetJustificationText) {
        this.indirectCostBudgetJustificationText = indirectCostBudgetJustificationText;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "IndirectBudgetCostEntity [indirectCostBudgetId=%s, propInstRectId=%s, budgetYear=%s, indirectCostItemName=%s, indirectCostRate=%s, indirectCostBaseDollarAmount=%s, indirectCostBudgetJustificationText=%s]",
                indirectCostBudgetId, propInstRecId, budgetYear, indirectCostItemName, indirectCostRate,
                indirectCostBaseDollarAmount, indirectCostBudgetJustificationText);
    }
}
