package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import java.util.Map;
import java.util.function.Function;

import static code.donbonifacio.saft.Util.compose;

/**
 * Represents a Line element, present on an Invoice of a SAF-T XML file.
 */
public final class StockMovementLine {

    // maps a friendly name to a InvoiceLine field getter
    public static final Map<String, Function<StockMovementLine, Object>> FIELDS =
            ImmutableMap.<String, Function<StockMovementLine, Object>>builder()
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.ProductCode", StockMovementLine::getProductCode)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.ProductDescription", StockMovementLine::getProductDescription)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.Quantity", StockMovementLine::getQuantity)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.UnitOfMeasure", StockMovementLine::getUnitOfMeasure)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.UnitPrice", StockMovementLine::getUnitPrice)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.Description", StockMovementLine::getDescription)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.CreditAmount", StockMovementLine::getCreditAmount)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.DebitAmount", StockMovementLine::getDebitAmount)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.Tax.TaxType", compose(StockMovementLine::getTax, Tax::getTaxType))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.Tax.TaxCountryRegion", compose(StockMovementLine::getTax, Tax::getTaxCountryRegion))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.Tax.TaxCode", compose(StockMovementLine::getTax, Tax::getTaxCode))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Line.Tax.TaxPercentage", compose(StockMovementLine::getTax, Tax::getTaxPercentage))
                    .build();

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
    private Tax tax;

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
     * Prepares the object after XML deserialization.
     *
     * @param um the Unmarshaller
     * @param parent the parent object
     */
    // Used by the XML unmarshaller
    @SuppressWarnings("squid:UnusedPrivateMethod")
    private void afterUnmarshal(Unmarshaller um, Object parent) {
        if(tax == null) {
            tax = new Tax();
        }

    }
}
