package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * Represents a Tax element on SAF-T XML.
 */
public final class Tax {

    @XmlElement(name="TaxType")
    private String taxType;

    @XmlElement(name="TaxCountryRegion")
    private String taxCountryRegion;

    @XmlElement(name="TaxCode")
    private String taxCode;

    @XmlElement(name="TaxPercentage")
    private double taxPercentage;

    /**
     * Gets the TaxType.
     *
     * @return the tax type
     */
    public String getTaxType() {
        return taxType;
    }

    /**
     * Gets the TaxCountryRegion.
     *
     * @return the tax country region
     */
    public String getTaxCountryRegion() {
        return taxCountryRegion;
    }

    /**
     * Gets the TaxCode.
     *
     * @return the tax code
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * Gets the TaxPercentage.
     *
     * @return the tax percentage
     */
    public double getTaxPercentage() {
        return taxPercentage;
    }
}
