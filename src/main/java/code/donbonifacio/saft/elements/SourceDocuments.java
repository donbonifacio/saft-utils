package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * Represents the SourceDocuments XML element of the SAF-T file.
 */
public final class SourceDocuments {

    @XmlElement(name="SalesInvoices")
    private SalesInvoices salesInvoices;

    public SalesInvoices getSalesInvoices() {
        if(salesInvoices == null) {
            salesInvoices = new SalesInvoices();
        }
        return salesInvoices;
    }

}
