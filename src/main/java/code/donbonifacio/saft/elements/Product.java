package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents a `Product` xml element on the standard
 * SAF-T file.
 *
 * The class is immutable.
 */
@XmlType(name="Product")
public final class Product {

    @XmlElement(name="ProductCode")
    private String productCode;

    @XmlElement(name="ProductType")
    private String productType;

    @XmlElement(name="ProductDescription")
    private String productDescription;

    @XmlElement(name="ProductNumberCode")
    private String productNumberCode;

    /**
     * Gets the ProductCode
     *
     * @return the product code
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Gets the ProductType
     *
     * @return the product type
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Gets the ProductDescription
     *
     * @return the product description
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Gets the ProductNumberCode
     *
     * @return the product number code
     */
    public String getProductNumberCode() {
        return productNumberCode;
    }

}
