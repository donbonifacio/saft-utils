package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * Represents a base Line used by documents.
 */
public abstract class AbstractLine {

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

    @XmlElement(name="Description")
    private String description;

    @XmlElement(name="CreditAmount")
    private double creditAmount;

    @XmlElement(name="DebitAmount")
    private double debitAmount;

    @XmlElement(name="ProductDescription")
    private String productDescription;

    @XmlElement(name="Tax")
    protected Tax tax;

    @XmlElement(name="TaxPointDate")
    private String taxPointDate;

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
    public double getCreditAmount() {
        return creditAmount;
    }

    /**
     * Gets the DebitAmout.
     *
     * @return the credit amount
     */
    public double getDebitAmount() {
        return debitAmount;
    }

    /**
     * Gets the Tax.
     *
     * @return the tax
     */
    public Tax getTax() {
        return tax;
    }

    /**
     * Gets the TaxPointDate.
     *
     * @return the tax point date
     */
    public String getTaxPointDate() {
        return taxPointDate;
    }

}
