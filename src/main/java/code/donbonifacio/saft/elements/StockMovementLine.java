package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableMap;

import javax.xml.bind.Unmarshaller;
import java.util.Map;
import java.util.function.Function;

import static code.donbonifacio.saft.Util.compose;

/**
 * Represents a Line element, present on an Invoice of a SAF-T XML file.
 */
public final class StockMovementLine extends AbstractLine {

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
