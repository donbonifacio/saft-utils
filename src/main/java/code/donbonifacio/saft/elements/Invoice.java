package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableMap;

import javax.xml.bind.annotation.XmlElement;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents the Invoice XML element of a SAF-T file.
 */
public final class Invoice {

    // maps a friendly name to a TaxTableEntry field getter
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
                    .build();

    @XmlElement(name="InvoiceNo")
    private String invoiceNo;

    @XmlElement(name="Hash")
    private String hash;

    @XmlElement(name="HashControl")
    private String hashControl;

    @XmlElement(name="Period")
    private int period;

    @XmlElement(name="InvoiceDate")
    private String invoiceDate;

    @XmlElement(name="InvoiceType")
    private String invoiceType;

    @XmlElement(name="SourceID")
    private String sourceId;

    @XmlElement(name="SystemEntryDate")
    private String systemEntryDate;

    @XmlElement(name="CustomerID")
    private int customerId;

    /**
     * Gets the InvoiceNo.
     *
     * @return the invoice number
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * Gets the Hash.
     *
     * @return the hash
     */
    public String getHash() {
        return hash;
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
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * Gets the SourceID.
     *
     * @return the source id
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Gets the SystemEntryDate.
     *
     * @return the system entry date
     */
    public String getSystemEntryDate() {
        return systemEntryDate;
    }

    /**
     * Gets the CustomerID.
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }
}
