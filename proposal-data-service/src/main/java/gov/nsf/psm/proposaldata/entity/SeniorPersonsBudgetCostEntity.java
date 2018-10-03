package gov.nsf.psm.proposaldata.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prop_budg_sr_pers_cst")
public class SeniorPersonsBudgetCostEntity extends BudgetCostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_budg_sr_pers_cst_id", nullable = false)
    private Long srPersonBudgeCostId;

    @Column(name = "prop_pers_id", nullable = false)
    private Long propPersonId;

    @Column(name = "sr_pers_prsn_mnth_cnt", nullable = false)
    private double seniorPersonMonthCount;

    @Column(name = "sr_pers_dol_amt", nullable = false)
    private BigDecimal seniorPersonDollarAmount;

    @Column(name = "sr_pers_budg_jstf_txt")
    private String seniorPersonJustificationText;

    @Column(name = "sr_pers_flag")
    private boolean seniorPersonnelFlag;

    public SeniorPersonsBudgetCostEntity() {
        /**
         * default constructor
         */
    }

    public SeniorPersonsBudgetCostEntity(Long propInstRecId, Long propPersonId, int budgetYear,
            double seniorPersonMonthCount, BigDecimal seniorPersonDollarAmount, String seniorPersonJustificationText,
            boolean seniorPersonnelFlag) {
        super();
        this.propInstRecId = propInstRecId;
        this.propPersonId = propPersonId;
        this.budgetYear = budgetYear;
        this.seniorPersonMonthCount = seniorPersonMonthCount;
        this.seniorPersonDollarAmount = seniorPersonDollarAmount;
        this.seniorPersonJustificationText = seniorPersonJustificationText;
        this.seniorPersonnelFlag = seniorPersonnelFlag;
    }

    public boolean isSeniorPersonnelFlag() {
        return seniorPersonnelFlag;
    }

    public void setSeniorPersonnelFlag(boolean seniorPersonnelFlag) {
        this.seniorPersonnelFlag = seniorPersonnelFlag;
    }

    public Long getPropPersonId() {
        return propPersonId;
    }

    public void setPropPersonId(Long propPersonId) {
        this.propPersonId = propPersonId;
    }

    public double getSeniorPersonMonthCount() {
        return seniorPersonMonthCount;
    }

    public void setSeniorPersonMonthCount(double seniorPersonMonthCount) {
        this.seniorPersonMonthCount = seniorPersonMonthCount;
    }

    public BigDecimal getSeniorPersonDollarAmount() {
        return seniorPersonDollarAmount;
    }

    public void setSeniorPersonDollarAmount(BigDecimal seniorPersonDollarAmount) {
        this.seniorPersonDollarAmount = seniorPersonDollarAmount;
    }

    public String getSeniorPersonJustificationText() {
        return seniorPersonJustificationText;
    }

    public void setSeniorPersonJustificationText(String seniorPersonJustificationText) {
        this.seniorPersonJustificationText = seniorPersonJustificationText;
    }

    public Long getSrPersonBudgeCostId() {
        return srPersonBudgeCostId;
    }

    public void setSrPersonBudgeCostId(Long srPersonBudgeCostId) {
        this.srPersonBudgeCostId = srPersonBudgeCostId;
    }

    @Override
    public String toString() {
        return "SeniorPersonsBudgetCostEntity [srPersonBudgeCostId=" + srPersonBudgeCostId + ", propInstRecId="
                + propInstRecId + ", propPersonId=" + propPersonId + ", budgetYear=" + budgetYear
                + ", seniorPersonMonthCount=" + seniorPersonMonthCount + ", seniorPersonDollarAmount="
                + seniorPersonDollarAmount + ", seniorPersonJustificationText=" + seniorPersonJustificationText
                + ", seniorPersonnelFlag=" + seniorPersonnelFlag + "]";
    }

}
