package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

/**
 * Class for DocumentStatus XML element
 */
public final class DocumentStatus {

    @XmlElements({
            @XmlElement(name="InvoiceStatus"),
            @XmlElement(name="PaymentStatus"),
            @XmlElement(name="MovementStatus")
    })
    private String invoiceStatus;

    @XmlElements({
            @XmlElement(name="InvoiceStatusDate"),
            @XmlElement(name="PaymentStatusDate"),
            @XmlElement(name="MovementStatusDate")
    })
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
