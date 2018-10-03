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
@Table(name = "prop_oth_fed_agcy")
public class OtherFederalAgenciesEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_oth_fed_agcy_id", nullable = false)
    private Long othFedAgencyId;

    @Column(name = "prop_cvr_shet_id", insertable = false, updatable = false)
    private Long propcovrSheetId;

    @Column(name = "fed_agcy_name_abbr")
    private String fedAgencyNameAbbreviation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_cvr_shet_id")
    private CoverSheetEntity coverSheetEntity;

    /**
    * 
    */
    public OtherFederalAgenciesEntity() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the othFedAgencyId
     */
    public Long getOthFedAgencyId() {
        return othFedAgencyId;
    }

    /**
     * @param othFedAgencyId
     *            the othFedAgencyId to set
     */
    public void setOthFedAgencyId(Long othFedAgencyId) {
        this.othFedAgencyId = othFedAgencyId;
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
     * @return the fedAgencyNameAbbreviation
     */
    public String getFedAgencyNameAbbreviation() {
        return fedAgencyNameAbbreviation;
    }

    /**
     * @param fedAgencyNameAbbreviation
     *            the fedAgencyNameAbbreviation to set
     */
    public void setFedAgencyNameAbbreviation(String fedAgencyNameAbbreviation) {
        this.fedAgencyNameAbbreviation = fedAgencyNameAbbreviation;
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
                "OtherFederalAgenciesEntity [othFedAgencyId=%s, propcovrSheetId=%s, fedAgencyNameAbbreviation=%s, coverSheetEntity=%s]",
                othFedAgencyId, propcovrSheetId, fedAgencyNameAbbreviation, coverSheetEntity);
    }

}
