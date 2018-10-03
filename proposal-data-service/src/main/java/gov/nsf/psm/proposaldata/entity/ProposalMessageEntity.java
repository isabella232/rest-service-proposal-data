/**
 * 
 */
package gov.nsf.psm.proposaldata.entity;

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
@Table(name = "prop_msg")
public class ProposalMessageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_msg_id", nullable = false)
    private Long propMsgId;

    @Column(name = "prop_prep_id", nullable = false)
    private int propPrepId;

    @Column(name = "prop_revn_id", nullable = false)
    private int propRevnId;

    @Column(name = "prop_sect_type", nullable = false)
    private String propSectionType;

    @Column(name = "psm_msg_lvl", nullable = false)
    private String psmMsgLevel;

    @Column(name = "psm_msg_id", nullable = false)
    private String psmMsgId;

    @Column(name = "psm_msg_txt")
    private String psmMsgText;

    @Column(name = "prop_pers_id")
    private int propPersId;

    /**
     * 
     */
    public ProposalMessageEntity() {
        /**
         * Default constructor
         */
    }

    /**
     * @return the propMsgId
     */
    public Long getPropMsgId() {
        return this.propMsgId;
    }

    /**
     * @param propMsgId
     *            the propMsgId to set
     */
    public void setPropMsgId(Long propMsgId) {
        this.propMsgId = propMsgId;
    }

    /**
     * @return the propPrepId
     */
    public int getPropPrepId() {
        return propPrepId;
    }

    /**
     * @param propPrepId
     *            the propPrepId to set
     */
    public void setPropPrepId(int propPrepId) {
        this.propPrepId = propPrepId;
    }

    /**
     * @return the propRevnId
     */
    public int getPropRevnId() {
        return propRevnId;
    }

    /**
     * @param propRevnId
     *            the propRevnId to set
     */
    public void setPropRevnId(int propRevnId) {
        this.propRevnId = propRevnId;
    }

    /**
     * @return the propSectionType
     */
    public String getPropSectionType() {
        return propSectionType;
    }

    /**
     * @param propSectionType
     *            the propSectionType to set
     */
    public void setPropSectionType(String propSectionType) {
        this.propSectionType = propSectionType;
    }

    /**
     * @return the psmMsgLevel
     */
    public String getPsmMsgLevel() {
        return psmMsgLevel;
    }

    /**
     * @param psmMsgLevel
     *            the psmMsgLevel to set
     */
    public void setPsmMsgLevel(String psmMsgLevel) {
        this.psmMsgLevel = psmMsgLevel;
    }

    /**
     * @return the psmMsgId
     */
    public String getPsmMsgId() {
        return psmMsgId;
    }

    /**
     * @param psmMsgId
     *            the psmMsgId to set
     */
    public void setPsmMsgId(String psmMsgId) {
        this.psmMsgId = psmMsgId;
    }

    /**
     * @return the psmMsgText
     */
    public String getPsmMsgText() {
        return psmMsgText;
    }

    /**
     * @param psmMsgText
     *            the psmMsgText to set
     */
    public void setPsmMsgText(String psmMsgText) {
        this.psmMsgText = psmMsgText;
    }

    /**
     * @return the propPersId
     */
    public int getPropPersId() {
        return propPersId;
    }

    /**
     * @param propPersId
     *            the propPersId to set
     */
    public void setPropPersId(int propPersId) {
        this.propPersId = propPersId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "ProposalMessageEntity [propMsgId=%s, propPrepId=%s, propRevnId=%s, propSectionType=%s, psmMsgLevel=%s, psmMsgId=%s, psmMsgText=%s, propPersId=%s]",
                propMsgId, propPrepId, propRevnId, propSectionType, psmMsgLevel, psmMsgId, psmMsgText, propPersId);
    }

}
