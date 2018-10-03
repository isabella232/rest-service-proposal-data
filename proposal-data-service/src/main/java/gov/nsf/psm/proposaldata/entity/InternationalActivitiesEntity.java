/**
 * 
 */
package gov.nsf.psm.proposaldata.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author pkatrapa
 *
 */

@Entity
@Table(name = "prop_intl_acty_ctry_name")
public class InternationalActivitiesEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_intl_acty_ctry_id", nullable = false)
    private Long intlActyCtryId;

    @Column(name = "prop_cvr_shet_id", insertable = false, updatable = false)
    private Long propcovrSheetId;

    @Column(name = "intl_acty_ctry_name", nullable = false)
    private String intlActyCountryName;

    @Column(name = "ctry_code", nullable = false)
    private String intlCountryCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_cvr_shet_id")
    private CoverSheetEntity coverSheetEntity;

    /**
     * 
     */
    public InternationalActivitiesEntity() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the intlActyCtryId
     */
    public Long getIntlActyCtryId() {
        return intlActyCtryId;
    }

    /**
     * @param intlActyCtryId
     *            the intlActyCtryId to set
     */
    public void setIntlActyCtryId(Long intlActyCtryId) {
        this.intlActyCtryId = intlActyCtryId;
    }

    /**
     * @return the propcovrSheetId
     */
    public Long getPropcovrSheetId() {
        return propcovrSheetId;
    }

    /**
     * @param propcovrSheetId
     *            the propcovrSheetId to set
     */
    public void setPropcovrSheetId(Long propcovrSheetId) {
        this.propcovrSheetId = propcovrSheetId;
    }

    /**
     * @return the intlActyCountryName
     */
    public String getIntlActyCountryName() {
        return intlActyCountryName;
    }

    /**
     * @param intlActyCountryName
     *            the intlActyCountryName to set
     */
    public void setIntlActyCountryName(String intlActyCountryName) {
        this.intlActyCountryName = intlActyCountryName;
    }

    /**
     * @return the intlCountryCode
     */
    public String getIntlCountryCode() {
        return intlCountryCode;
    }

    /**
     * @param intlCountryCode
     *            the intlCountryCode to set
     */
    public void setIntlCountryCode(String intlCountryCode) {
        this.intlCountryCode = intlCountryCode;
    }

    /**
     * @return the coverSheetEntity
     */
    public CoverSheetEntity getCoverSheetEntity() {
        return coverSheetEntity;
    }

    /**
     * @param coverSheetEntity
     *            the coverSheetEntity to set
     */
    public void setCoverSheetEntity(CoverSheetEntity coverSheetEntity) {
        this.coverSheetEntity = coverSheetEntity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "InternationalActivitiesEntity [intlActyCtryId=%s, propcovrSheetId=%s, intlActyCountryName=%s, intlCountryCode=%s, coverSheetEntity=%s]",
                intlActyCtryId, propcovrSheetId, intlActyCountryName, intlCountryCode, coverSheetEntity);
    }

}
