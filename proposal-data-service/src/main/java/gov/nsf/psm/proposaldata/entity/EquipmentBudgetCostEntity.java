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
@Table(name = "prop_budg_equp_cst")
public class EquipmentBudgetCostEntity extends BudgetCostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_budg_equp_cst_id", nullable = false)
    private Long equipmentCostBudgetId;

    @Column(name = "equp_name", nullable = false)
    private String equipmentName;

    @Column(name = "equp_dol_amt", nullable = false)
    private BigDecimal equipmentDollarAmount;

    @Column(name = "equp_cst_budg_jstf_txt", nullable = false)
    private String equipmentCostBudgetJsutificationText;

    public EquipmentBudgetCostEntity() {
        /**
         * Default constructor
         */
    }

    /**
     * @param propInstRectId
     * @param budgetYear
     * @param equipmentName
     * @param equipmentDollarAmount
     * @param equipmentCostBudgetJsutificationText
     */
    public EquipmentBudgetCostEntity(Long propInstRecId, int budgetYear, String equipmentName,
            BigDecimal equipmentDollarAmount, String equipmentCostBudgetJsutificationText) {
        super();
        this.propInstRecId = propInstRecId;
        this.budgetYear = budgetYear;
        this.equipmentName = equipmentName;
        this.equipmentDollarAmount = equipmentDollarAmount;
        this.equipmentCostBudgetJsutificationText = equipmentCostBudgetJsutificationText;
    }

    /**
     * @return the equipmentCostBudgetId
     */
    public Long getEquipmentCostBudgetId() {
        return equipmentCostBudgetId;
    }

    /**
     * @param equipmentCostBudgetId
     *            the equipmentCostBudgetId to set
     */
    public void setEquipmentCostBudgetId(Long equipmentCostBudgetId) {
        this.equipmentCostBudgetId = equipmentCostBudgetId;
    }

    /**
     * @return the equipmentName
     */
    public String getEquipmentName() {
        return equipmentName;
    }

    /**
     * @param equipmentName
     *            the equipmentName to set
     */
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    /**
     * @return the equipmentDollarAmount
     */
    public BigDecimal getEquipmentDollarAmount() {
        return equipmentDollarAmount;
    }

    /**
     * @param equipmentDollarAmount
     *            the equipmentDollarAmount to set
     */
    public void setEquipmentDollarAmount(BigDecimal equipmentDollarAmount) {
        this.equipmentDollarAmount = equipmentDollarAmount;
    }

    /**
     * @return the equipmentCostBudgetJsutificationText
     */
    public String getEquipmentCostBudgetJsutificationText() {
        return equipmentCostBudgetJsutificationText;
    }

    /**
     * @param equipmentCostBudgetJsutificationText
     *            the equipmentCostBudgetJsutificationText to set
     */
    public void setEquipmentCostBudgetJsutificationText(String equipmentCostBudgetJsutificationText) {
        this.equipmentCostBudgetJsutificationText = equipmentCostBudgetJsutificationText;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "EquipmentBudgetCostEntity [equipmentCostBudgetId=%s, propInstRecId=%s, budgetYear=%s, equipmentName=%s, equipmentDollarAmount=%s, equipmentCostBudgetJsutificationText=%s]",
                equipmentCostBudgetId, propInstRecId, budgetYear, equipmentName, equipmentDollarAmount,
                equipmentCostBudgetJsutificationText);
    }
}
