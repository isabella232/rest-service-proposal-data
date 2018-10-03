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
@Table(name = "prop_budg_oth_drct_cst")
public class OtherDirectCostEntity extends BudgetCostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_budg_oth_drct_cst_id", nullable = false)
    private Long otherDirectCostBudgetId;

    @Column(name = "matl_dol_amt", nullable = false)
    private BigDecimal materialsDollarAmount;

    @Column(name = "pub_dol_amt", nullable = false)
    private BigDecimal publicationDollarAmount;

    @Column(name = "cnsl_serv_dol_amt", nullable = false)
    private BigDecimal consultantServicesDollarAmount;

    @Column(name = "cptr_serv_dol_amt", nullable = false)
    private BigDecimal computerServicesDollarAmount;

    @Column(name = "sub_awd_dol_amt", nullable = false)
    private BigDecimal subContractDollarAmount;

    @Column(name = "oth_drct_cst_dol_amt", nullable = false)
    private BigDecimal otherDirectCostDollarAmount;

    @Column(name = "oth_drct_cst_budg_jstf_txt")
    private String otherDirectCostBudgetJustificationText;

    /**
     * 
     */
    public OtherDirectCostEntity() {
        /**
         * Default constructor
         */
    }

    /**
     * @return the otherDirectCostBudgetId
     */
    public Long getOtherDirectCostBudgetId() {
        return otherDirectCostBudgetId;
    }

    /**
     * @param otherDirectCostBudgetId
     *            the otherDirectCostBudgetId to set
     */
    public void setOtherDirectCostBudgetId(Long otherDirectCostBudgetId) {
        this.otherDirectCostBudgetId = otherDirectCostBudgetId;
    }

    /**
     * @return the materialsDollarAmount
     */
    public BigDecimal getMaterialsDollarAmount() {
        return materialsDollarAmount;
    }

    /**
     * @param materialsDollarAmount
     *            the materialsDollarAmount to set
     */
    public void setMaterialsDollarAmount(BigDecimal materialsDollarAmount) {
        this.materialsDollarAmount = materialsDollarAmount;
    }

    /**
     * @return the publicationDollarAmount
     */
    public BigDecimal getPublicationDollarAmount() {
        return publicationDollarAmount;
    }

    /**
     * @param publicationDollarAmount
     *            the publicationDollarAmount to set
     */
    public void setPublicationDollarAmount(BigDecimal publicationDollarAmount) {
        this.publicationDollarAmount = publicationDollarAmount;
    }

    /**
     * @return the consultantServicesDollarAmount
     */
    public BigDecimal getConsultantServicesDollarAmount() {
        return consultantServicesDollarAmount;
    }

    /**
     * @param consultantServicesDollarAmount
     *            the consultantServicesDollarAmount to set
     */
    public void setConsultantServicesDollarAmount(BigDecimal consultantServicesDollarAmount) {
        this.consultantServicesDollarAmount = consultantServicesDollarAmount;
    }

    /**
     * @return the computerServicesDollarAmount
     */
    public BigDecimal getComputerServicesDollarAmount() {
        return computerServicesDollarAmount;
    }

    /**
     * @param computerServicesDollarAmount
     *            the computerServicesDollarAmount to set
     */
    public void setComputerServicesDollarAmount(BigDecimal computerServicesDollarAmount) {
        this.computerServicesDollarAmount = computerServicesDollarAmount;
    }

    /**
     * @return the subContractDollarAmount
     */
    public BigDecimal getSubContractDollarAmount() {
        return subContractDollarAmount;
    }

    /**
     * @param subContractDollarAmount
     *            the subContractDollarAmount to set
     */
    public void setSubContractDollarAmount(BigDecimal subContractDollarAmount) {
        this.subContractDollarAmount = subContractDollarAmount;
    }

    /**
     * @return the otherDirectCostDollarAmount
     */
    public BigDecimal getOtherDirectCostDollarAmount() {
        return otherDirectCostDollarAmount;
    }

    /**
     * @param otherDirectCostDollarAmount
     *            the otherDirectCostDollarAmount to set
     */
    public void setOtherDirectCostDollarAmount(BigDecimal otherDirectCostDollarAmount) {
        this.otherDirectCostDollarAmount = otherDirectCostDollarAmount;
    }

    /**
     * @return the otherDirectCostBudgetJustificationText
     */
    public String getOtherDirectCostBudgetJustificationText() {
        return otherDirectCostBudgetJustificationText;
    }

    /**
     * @param otherDirectCostBudgetJustificationText
     *            the otherDirectCostBudgetJustificationText to set
     */
    public void setOtherDirectCostBudgetJustificationText(String otherDirectCostBudgetJustificationText) {
        this.otherDirectCostBudgetJustificationText = otherDirectCostBudgetJustificationText;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "OtherDirectCostEntity [otherDirectCostBudgetId=%s, propInstRecId=%s, budgetYear=%s, materialsDollarAmount=%s, publicationDollarAmount=%s, consultantServicesDollarAmount=%s, computerServicesDollarAmount=%s, subContractDollarAmount=%s, otherDirectCostDollarAmount=%s, otherDirectCostBudgetJustificationText=%s]",
                otherDirectCostBudgetId, propInstRecId, budgetYear, materialsDollarAmount, publicationDollarAmount,
                consultantServicesDollarAmount, computerServicesDollarAmount, subContractDollarAmount,
                otherDirectCostDollarAmount, otherDirectCostBudgetJustificationText);
    }
}
