package gov.nsf.psm.proposaldata.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_budg_trav_cst")
public class TravelBudgetCostEntity extends BudgetCostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_budg_trav_cst_id", nullable = false)
    private Long travelCostBudgetId;

    @Column(name = "dom_trav_dol_amt", nullable = false)
    private BigDecimal domesticTravelDollarAmount;

    @Column(name = "frgn_trav_dol_amt", nullable = false)
    private BigDecimal foreignTravelDollarAmount;

    @Column(name = "trav_cst_budg_jstf_txt", nullable = false)
    private String travelCostBudgetJustificationText;

    public TravelBudgetCostEntity() {
        /**
         * Default constructor
         */
    }

    /**
     * @param propInstRectId
     * @param budgetYear
     * @param domesticTravelDollarAmount
     * @param foreignTravelDollarAmount
     * @param travelCostBudgetJustificationText
     */
    public TravelBudgetCostEntity(Long propInstRecId, int budgetYear, BigDecimal domesticTravelDollarAmount,
            BigDecimal foreignTravelDollarAmount, String travelCostBudgetJustificationText) {
        super();
        this.propInstRecId = propInstRecId;
        this.budgetYear = budgetYear;
        this.domesticTravelDollarAmount = domesticTravelDollarAmount;
        this.foreignTravelDollarAmount = foreignTravelDollarAmount;
        this.travelCostBudgetJustificationText = travelCostBudgetJustificationText;
    }

    public Long getTravelCostBudgetId() {
        return travelCostBudgetId;
    }

    public void setTravelCostBudgetId(Long travelCostBudgetId) {
        this.travelCostBudgetId = travelCostBudgetId;
    }

    /**
     * @return the domesticTravelDollarAmount
     */
    public BigDecimal getDomesticTravelDollarAmount() {
        return domesticTravelDollarAmount;
    }

    /**
     * @param domesticTravelDollarAmount
     *            the domesticTravelDollarAmount to set
     */
    public void setDomesticTravelDollarAmount(BigDecimal domesticTravelDollarAmount) {
        this.domesticTravelDollarAmount = domesticTravelDollarAmount;
    }

    /**
     * @return the foreignTravelDollarAmount
     */
    public BigDecimal getForeignTravelDollarAmount() {
        return foreignTravelDollarAmount;
    }

    /**
     * @param foreignTravelDollarAmount
     *            the foreignTravelDollarAmount to set
     */
    public void setForeignTravelDollarAmount(BigDecimal foreignTravelDollarAmount) {
        this.foreignTravelDollarAmount = foreignTravelDollarAmount;
    }

    /**
     * @return the travelCostBudgetJustificationText
     */
    public String getTravelCostBudgetJustificationText() {
        return travelCostBudgetJustificationText;
    }

    /**
     * @param travelCostBudgetJustificationText
     *            the travelCostBudgetJustificationText to set
     */
    public void setTravelCostBudgetJustificationText(String travelCostBudgetJustificationText) {
        this.travelCostBudgetJustificationText = travelCostBudgetJustificationText;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "TravelBudgetEntity [travelCostBudgetId=%s, propInstRectId=%s, budgetYear=%s, domesticTravelDollarAmount=%s, foreignTravelDollarAmount=%s, travelCostBudgetJustificationText=%s]",
                travelCostBudgetId, propInstRecId, budgetYear, domesticTravelDollarAmount, foreignTravelDollarAmount,
                travelCostBudgetJustificationText);
    }
}
