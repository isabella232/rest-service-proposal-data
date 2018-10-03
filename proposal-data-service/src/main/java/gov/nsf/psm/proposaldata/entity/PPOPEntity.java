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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author pkatrapa
 *
 */

@Entity
@Table(name = "prop_ppop")
public class PPOPEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_ppop_id", nullable = false, insertable = false, updatable = false)
    private Long propPPOPId;

    @Column(name = "prop_cvr_shet_id", insertable = false, updatable = false)
    private Long propcovrSheetId;

    @Column(name = "org_name", nullable = false)
    private String organizationName;

    @Column(name = "str_addr", nullable = false)
    private String streetAddress;

    @Column(name = "str_addr_line2", nullable = false)
    private String streetAddress2;

    @Column(name = "dept_name", nullable = false)
    private String departmentName;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "st_code", nullable = false)
    private String stateCode;

    @Column(name = "pstl_code", nullable = false)
    private String postalCode;

    @Column(name = "ctry_code", nullable = false)
    private String countryCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_cvr_shet_id")
    private CoverSheetEntity coverSheetEntity;

    /**
     * 
     */
    public PPOPEntity() {
        /**
         * default constructor
         */
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
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * @param organizationName
     *            the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * @return the streetAddress
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * @param streetAddress
     *            the streetAddress to set
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * @return the streetAddress2
     */
    public String getStreetAddress2() {
        return streetAddress2;
    }

    /**
     * @param streetAddress2
     *            the streetAddress2 to set
     */
    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName
     *            the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName
     *            the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the stateCode
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * @param stateCode
     *            the stateCode to set
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode
     *            the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode
     *            the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
                "PPOPEntity [propPPOPId=%s, propcovrSheetId=%s, organizationName=%s, streetAddress=%s, streetAddress2=%s, departmentName=%s, cityName=%s, stateCode=%s, postalCode=%s, countryCode=%s, coverSheetEntity=%s]",
                propPPOPId, propcovrSheetId, organizationName, streetAddress, streetAddress2, departmentName, cityName,
                stateCode, postalCode, countryCode, coverSheetEntity);
    }

}
