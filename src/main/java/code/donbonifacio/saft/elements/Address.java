package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * This class represents an `Address` xml element on the standard
 * SAF-T file.
 * The class is immutable.
 */
public final class Address {

    @XmlElement(name="AddressDetail")
    private String addressDetail;

    @XmlElement(name="City")
    private String city;

    @XmlElement(name="PostalCode")
    private String postalCode;

    @XmlElement(name="Country")
    private String country;

    /**
     * Gets the AddressDetail
     * @return the address detail
     */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
     * Gets the City
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the PostalCode
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets the Countru
     * @return the country
     */
    public String getCountry() {
        return country;
    }
}
