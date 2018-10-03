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
@Table(name = "prop_budg_part_sup_cst")
public class ParticipantsSupportCostEntity extends BudgetCostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_budg_part_sup_cst_id", nullable = false)
    private Long participantSupportCostBudgetId;

    @Column(name = "part_num_cnt", nullable = false)
    private int participantNumberCount;

    @Column(name = "stpn_dol_amt", nullable = false)
    private BigDecimal stipendDollarAmout;

    @Column(name = "trav_dol_amt", nullable = false)
    private BigDecimal travelDollarAmount;

    @Column(name = "sbsst_dol_amt", nullable = false)
    private BigDecimal subsistenceDollarAmount;

    @Column(name = "oth_part_sup_dol_amt", nullable = false)
    private BigDecimal otherDollarAmount;

    @Column(name = "part_sup_budg_jstf_txt")
    private String participantSupportJustificationText;

    public ParticipantsSupportCostEntity() {
        /**
         * Default constructor
         */
    }

    /**
     * @return the participantSupportCostBudgetId
     */
    public Long getParticipantSupportCostBudgetId() {
        return participantSupportCostBudgetId;
    }

    /**
     * @param participantSupportCostBudgetId
     *            the participantSupportCostBudgetId to set
     */
    public void setParticipantSupportCostBudgetId(Long participantSupportCostBudgetId) {
        this.participantSupportCostBudgetId = participantSupportCostBudgetId;
    }

    /**
     * @return the participantNumberCount
     */
    public int getParticipantNumberCount() {
        return participantNumberCount;
    }

    /**
     * @param participantNumberCount
     *            the participantNumberCount to set
     */
    public void setParticipantNumberCount(int participantNumberCount) {
        this.participantNumberCount = participantNumberCount;
    }

    /**
     * @return the stipendDollarAmout
     */
    public BigDecimal getStipendDollarAmout() {
        return stipendDollarAmout;
    }

    /**
     * @param stipendDollarAmout
     *            the stipendDollarAmout to set
     */
    public void setStipendDollarAmout(BigDecimal stipendDollarAmout) {
        this.stipendDollarAmout = stipendDollarAmout;
    }

    /**
     * @return the travelDollarAmount
     */
    public BigDecimal getTravelDollarAmount() {
        return travelDollarAmount;
    }

    /**
     * @param travelDollarAmount
     *            the travelDollarAmount to set
     */
    public void setTravelDollarAmount(BigDecimal travelDollarAmount) {
        this.travelDollarAmount = travelDollarAmount;
    }

    /**
     * @return the subsistenceDollarAmount
     */
    public BigDecimal getSubsistenceDollarAmount() {
        return subsistenceDollarAmount;
    }

    /**
     * @param subsistenceDollarAmount
     *            the subsistenceDollarAmount to set
     */
    public void setSubsistenceDollarAmount(BigDecimal subsistenceDollarAmount) {
        this.subsistenceDollarAmount = subsistenceDollarAmount;
    }

    /**
     * @return the otherDollarAmount
     */
    public BigDecimal getOtherDollarAmount() {
        return otherDollarAmount;
    }

    /**
     * @param otherDollarAmount
     *            the otherDollarAmount to set
     */
    public void setOtherDollarAmount(BigDecimal otherDollarAmount) {
        this.otherDollarAmount = otherDollarAmount;
    }

    /**
     * @return the participantSupportJustificationText
     */
    public String getParticipantSupportJustificationText() {
        return participantSupportJustificationText;
    }

    /**
     * @param participantSupportJustificationText
     *            the participantSupportJustificationText to set
     */
    public void setParticipantSupportJustificationText(String participantSupportJustificationText) {
        this.participantSupportJustificationText = participantSupportJustificationText;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "ParticipantsSupportCostEntity [participantSupportCostBudgetId=%s, propInstRectId=%s, budgetYear=%s, participantNumberCount=%s, stipendDollarAmout=%s, travelDollarAmount=%s, subsistenceDollarAmount=%s, otherDollarAmount=%s, participantSupportJustificationText=%s]",
                participantSupportCostBudgetId, propInstRecId, budgetYear, participantNumberCount, stipendDollarAmout,
                travelDollarAmount, subsistenceDollarAmount, otherDollarAmount, participantSupportJustificationText);
    }
}
