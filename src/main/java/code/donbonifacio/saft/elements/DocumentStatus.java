package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class for DocumentStatus XML element
 */
public final class DocumentStatus {

    @XmlElement(name="InvoiceStatus")
    private String invoiceStatus;

    @XmlElement(name="InvoiceStatusDate")
    private String invoiceStatusDate;

    @XmlElement(name="SourceID")
    private String sourceId;

    @XmlElement(name="SourceBilling")
    private String sourceBilling;

    /**
     * Gets the invoice status
     *
     * @return the invoice status
     */
    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    /**
     * Gets the InvoiceStatusDate
     *
     * @return the invoice status date
     */
    public String getInvoiceStatusDate() {
        return invoiceStatusDate;
    }

    /**
     * Gets the SourceID
     *
     * @return the source id
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Gets the SourceBilling
     *
     * @return the source billing
     */
    public String getSourceBilling() {
        return sourceBilling;
    }
}
