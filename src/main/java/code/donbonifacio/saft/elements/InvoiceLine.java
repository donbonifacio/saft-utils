package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableMap;

import javax.xml.bind.Unmarshaller;
import java.util.Map;
import java.util.function.Function;

import static code.donbonifacio.saft.Util.compose;

/**
 * Represents a Line element, present on an Invoice of a SAF-T XML file.
 */
public final class InvoiceLine extends AbstractLine {

    // maps a friendly name to a InvoiceLine field getter
    public static final Map<String, Function<InvoiceLine, Object>> FIELDS =
            ImmutableMap.<String, Function<InvoiceLine, Object>>builder()
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.ProductCode", InvoiceLine::getProductCode)
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.ProductDescription", InvoiceLine::getProductDescription)
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.Quantity", InvoiceLine::getQuantity)
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.UnitOfMeasure", InvoiceLine::getUnitOfMeasure)
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.UnitPrice", InvoiceLine::getUnitPrice)
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.TaxPointDate", InvoiceLine::getTaxPointDate)
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.Description", InvoiceLine::getDescription)
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.CreditAmount", InvoiceLine::getCreditAmount)
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.DebitAmount", InvoiceLine::getDebitAmount)
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.Tax.TaxType", compose(InvoiceLine::getTax, Tax::getTaxType))
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.Tax.TaxCountryRegion", compose(InvoiceLine::getTax, Tax::getTaxCountryRegion))
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.Tax.TaxCode", compose(InvoiceLine::getTax, Tax::getTaxCode))
                    .put("SourceDocuments.SalesInvoices.Invoice.Line.Tax.TaxPercentage", compose(InvoiceLine::getTax, Tax::getTaxPercentage))
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
