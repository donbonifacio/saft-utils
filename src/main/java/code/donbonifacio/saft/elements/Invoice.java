package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * Represents the Invoice XML element of a SAF-T file.
 */
public final class Invoice {

    @XmlElement(name="InvoiceNo")
    private String invoiceNo;

    /**
     * Gets the InvoiceNo.
     *
     * @return the invoice number
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }
}
