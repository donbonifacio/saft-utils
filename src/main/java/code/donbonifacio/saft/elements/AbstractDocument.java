package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * Common information for SAF-T documents.
 */
public abstract class AbstractDocument {

    @XmlElement(name="DocumentStatus")
    protected DocumentStatus documentStatus;

    @XmlElement(name="Hash")
    protected String hash;

    @XmlElement(name="SourceID")
    protected String sourceId;

    @XmlElement(name="SystemEntryDate")
    protected String systemEntryDate;

    @XmlElement(name="CustomerID")
    protected int customerId;

    @XmlElement(name="DocumentTotals")
    protected DocumentTotals documentTotals;

    /**
     * Gets the DocumentTotals.
     *
     * @return the document totals
     */
    public DocumentTotals getDocumentTotals() {
        return documentTotals;
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

    /**
     * Gets the document status
     *
     * @return the document status
     */
    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

}
