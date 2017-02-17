package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static code.donbonifacio.saft.Util.compose;

/**
 * Represents the Invoice XML element of a SAF-T file.
 */
public final class Invoice extends AbstractDocument {

    // maps a friendly name to a Invoice field getter
    public static final Map<String, Function<Invoice, Object>> FIELDS =
            ImmutableMap.<String, Function<Invoice, Object>>builder()
                    .put("SourceDocuments.SalesInvoices.Invoice.Hash", Invoice::getHash)
                    .put("SourceDocuments.SalesInvoices.Invoice.HashControl", Invoice::getHashControl)
                    .put("SourceDocuments.SalesInvoices.Invoice.Period", Invoice::getPeriod)
                    .put("SourceDocuments.SalesInvoices.Invoice.InvoiceDate", Invoice::getInvoiceDate)
                    .put("SourceDocuments.SalesInvoices.Invoice.InvoiceType", Invoice::getInvoiceType)
                    .put("SourceDocuments.SalesInvoices.Invoice.SourceID", Invoice::getSourceId)
                    .put("SourceDocuments.SalesInvoices.Invoice.SystemEntryDate", Invoice::getSystemEntryDate)
                    .put("SourceDocuments.SalesInvoices.Invoice.CustomerID", Invoice::getCustomerId)
                    .put("SourceDocuments.SalesInvoices.Invoice.DocumentStatus.InvoiceStatus", compose(Invoice::getDocumentStatus, DocumentStatus::getInvoiceStatus))
                    .put("SourceDocuments.SalesInvoices.Invoice.DocumentStatus.InvoiceStatusDate", compose(Invoice::getDocumentStatus, DocumentStatus::getInvoiceStatusDate))
                    .put("SourceDocuments.SalesInvoices.Invoice.DocumentStatus.SourceID", compose(Invoice::getDocumentStatus, DocumentStatus::getSourceId))
                    .put("SourceDocuments.SalesInvoices.Invoice.DocumentStatus.SourceBilling", compose(Invoice::getDocumentStatus, DocumentStatus::getSourceBilling))
                    .put("SourceDocuments.SalesInvoices.Invoice.SpecialRegimes.SelfBillingIndicator", compose(Invoice::getSpecialRegimes, SpecialRegimes::getSelfBillingIndicator))
                    .put("SourceDocuments.SalesInvoices.Invoice.SpecialRegimes.CashVATSchemeIndicator", compose(Invoice::getSpecialRegimes, SpecialRegimes::getCashVatSchemeIndicator))
                    .put("SourceDocuments.SalesInvoices.Invoice.SpecialRegimes.ThirdPartiesBillingIndicator", compose(Invoice::getSpecialRegimes, SpecialRegimes::getThirdPartiesBillingIndicator))
                    .put("SourceDocuments.SalesInvoices.Invoice.DocumentTotals.TaxPayable", compose(Invoice::getDocumentTotals, DocumentTotals::getTaxPayable))
                    .put("SourceDocuments.SalesInvoices.Invoice.DocumentTotals.NetTotal", compose(Invoice::getDocumentTotals, DocumentTotals::getNetTotal))
                    .put("SourceDocuments.SalesInvoices.Invoice.DocumentTotals.GrossTotal", compose(Invoice::getDocumentTotals, DocumentTotals::getGrossTotal))
                    .build();

    @XmlElement(name="InvoiceNo")
    private String invoiceNo;

    @XmlElement(name="HashControl")
    private String hashControl;

    @XmlElement(name="Period")
    private int period;

    @XmlElement(name="InvoiceDate")
    private String invoiceDate;

    @XmlElement(name="InvoiceType")
    private InvoiceType invoiceType;

    @XmlElement(name="SpecialRegimes")
    private SpecialRegimes specialRegimes;

    @XmlElement(name="Line")
    private List<InvoiceLine> lines;

    /**
     * Gets the InvoiceNo.
     *
     * @return the invoice number
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * Gets the SpecialRegimes.
     *
     * @return the special regimes
     */
    public SpecialRegimes getSpecialRegimes() {
        return specialRegimes;
    }

    /**
     * Gets the HashControl
     *
     * @return the hash control
     */
    public String getHashControl() {
        return hashControl;
    }

    /**
     * Gets the Period.
     *
     * @return the period
     */
    public int getPeriod() {
        return period;
    }

    /**
     * Gets the InvoiceDate.
     *
     * @return the invoice date
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Gets the InvoiceType.
     *
     * @return the invoice type
     */
    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    /**
     * Gets the invoice Lines.
     *
     * @return the invoice lines
     */
    public List<InvoiceLine> getLines() {
        return lines;
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
        if(documentStatus == null) {
            documentStatus = new DocumentStatus();
        }
        if(specialRegimes == null) {
            specialRegimes = new SpecialRegimes();
        }
        if(lines == null) {
            lines = ImmutableList.of();
        } else {
            lines = ImmutableList.copyOf(lines);
        }
        if(documentTotals == null) {
            documentTotals = new DocumentTotals();
        }
    }
}
