package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * Represents a SourceDocumentID element on SAF-T.
 */
public final class SourceDocumentID {

    @XmlElement(name="OriginatingOn")
    private String originatingOn;

    @XmlElement(name="InvoiceDate")
    private String invoiceDate;

    /**
     * Gets the originating document.
     *
     * @return the document reference
     */
    public String getOriginatingOn() {
        return originatingOn;
    }

    /**
     * Gets the invoice date.
     *
     * @return the invoice date
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }

}
