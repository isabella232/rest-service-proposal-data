package gov.nsf.psm.proposaldata.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_budg_oth_pers_cst")
public class OtherPersonsBudgetCostEntity extends BudgetCostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_budg_oth_pers_cst_id", nullable = false)
    private Long otherPersonBudgeCostId;

    @Column(name = "oth_pers_type_code", nullable = false)
    private String otherPersonTypeCode;

    @Column(name = "oth_pers_cnt", nullable = false)
    private int otherPersonCount;

    @Column(name = "oth_pers_prsn_mnth_cnt", nullable = false)
    private double otherPersonMonthCount;

    @Column(name = "oth_pers_dol_amt", nullable = false)
    private BigDecimal otherPersonDollarAmount;

    @Column(name = "oth_pers_budg_jstf_txt")
    private String otherPersonJustificationText;

    public OtherPersonsBudgetCostEntity() {
        /**
         * Default constructor
         */
    }

    public OtherPersonsBudgetCostEntity(Long propInstRecId, String otherPersonTypeCode, int budgetYear,
            int otherPersonCount, double otherPersonMonthCount, BigDecimal otherPersonDollarAmount,
            String otherPersonJustificationText) {
        super();
        this.propInstRecId = propInstRecId;
        this.otherPersonTypeCode = otherPersonTypeCode;
        this.budgetYear = budgetYear;
        this.otherPersonCount = otherPersonCount;
        this.otherPersonMonthCount = otherPersonMonthCount;
        this.otherPersonDollarAmount = otherPersonDollarAmount;
        this.otherPersonJustificationText = otherPersonJustificationText;
    }

    public String getOtherPersonTypeCode() {
        return otherPersonTypeCode;
    }

    public void setOtherPersonTypeCode(String otherPersonTypeCode) {
        this.otherPersonTypeCode = otherPersonTypeCode;
    }

    public int getOtherPersonCount() {
        return otherPersonCount;
    }

    public void setOtherPersonCount(int otherPersonCount) {
        this.otherPersonCount = otherPersonCount;
    }

    public double getOtherPersonMonthCount() {
        return otherPersonMonthCount;
    }

    public void setOtherPersonMonthCount(double otherPersonMonthCount) {
        this.otherPersonMonthCount = otherPersonMonthCount;
    }

    public BigDecimal getOtherPersonDollarAmount() {
        return otherPersonDollarAmount;
    }

    public void setOtherPersonDollarAmount(BigDecimal otherPersonDollarAmount) {
        this.otherPersonDollarAmount = otherPersonDollarAmount;
    }

    public String getOtherPersonJustificationText() {
        return otherPersonJustificationText;
    }

    public void setOtherPersonJustificationText(String otherPersonJustificationText) {
        this.otherPersonJustificationText = otherPersonJustificationText;
    }

    public Long getOtherPersonBudgeCostId() {
        return otherPersonBudgeCostId;
    }

    public void setOtherPersonBudgeCostId(Long otherPersonBudgeCostId) {
        this.otherPersonBudgeCostId = otherPersonBudgeCostId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "OtherPersonsBudgetCostEntity [propInstRecId=%s, otherPersonTypeCode=%s, budgetYear=%s, otherPersonCount=%s, otherPersonMonthCount=%s, otherPersonDollarAmount=%s, otherPersonJustificationText=%s]",
                propInstRecId, otherPersonTypeCode, budgetYear, otherPersonCount, otherPersonMonthCount,
                otherPersonDollarAmount, otherPersonJustificationText);
    }

}
