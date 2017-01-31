package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;
import java.util.Optional;

/**
 * Represents a Line element, present on an Invoice of a SAF-T XML file.
 */
public final class InvoiceLine {

    @XmlElement(name="LineNumber")
    private int lineNumber;

    @XmlElement(name="ProductCode")
    private String productCode;

    @XmlElement(name="Quantity")
    private double quantity;

    @XmlElement(name="UnitOfMeasure")
    private String unitOfMeasure;

    @XmlElement(name="UnitPrice")
    private double unitPrice;

    @XmlElement(name="TaxPointDate")
    private String taxPointDate;

    @XmlElement(name="Description")
    private String description;

    @XmlElement(name="CreditAmount")
    private Optional<Double> creditAmount;

    @XmlElement(name="ProductDescription")
    private String productDescription;

    /**
     * Gets the LineNumber.
     *
     * @return the line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Gets the ProductCode.
     *
     * @return the product code
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Gets the ProductDescription.
     *
     * @return the product description
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Gets the Quantity.
     *
     * @return the quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Gets the UnitOfMeasure.
     *
     * @return the unit of measure.
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Gets the UnitPrice.
     *
     * @return the unit price
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Gets the TaxPointDate.
     *
     * @return the tax point date
     */
    public String getTaxPointDate() {
        return taxPointDate;
    }

    /**
     * Gets the Description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the CreditAmout.
     *
     * @return the credit amount
     */
    public Optional<Double> getCreditAmount() {
        return creditAmount;
    }
}
