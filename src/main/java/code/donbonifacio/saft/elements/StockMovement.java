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
 * Represents the StockMovement XML element of a SAF-T file.
 */
public final class StockMovement {

    // maps a friendly name to a StockMovement field getter
    public static final Map<String, Function<StockMovement, Object>> FIELDS =
            ImmutableMap.<String, Function<StockMovement, Object>>builder()
                    .put("SourceDocuments.MovementOfGoods.StockMovement.Hash", StockMovement::getHash)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentStatus.MovementStatus", compose(StockMovement::getDocumentStatus, DocumentStatus::getInvoiceStatus))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentStatus.MovementStatusDate", compose(StockMovement::getDocumentStatus, DocumentStatus::getInvoiceStatusDate))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentStatus.SourceID", compose(StockMovement::getDocumentStatus, DocumentStatus::getSourceId))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentStatus.SourceBilling", compose(StockMovement::getDocumentStatus, DocumentStatus::getSourceBilling))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.SourceID", StockMovement::getSourceId)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.SystemEntryDate", StockMovement::getSystemEntryDate)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.CustomerID", StockMovement::getCustomerId)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentTotals.TaxPayable", compose(StockMovement::getDocumentTotals, DocumentTotals::getTaxPayable))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentTotals.NetTotal", compose(StockMovement::getDocumentTotals, DocumentTotals::getNetTotal))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentTotals.GrossTotal", compose(StockMovement::getDocumentTotals, DocumentTotals::getGrossTotal))
                    .build();

    @XmlElement(name="DocumentNumber")
    private String documentNumber;

    @XmlElement(name="DocumentStatus")
    private DocumentStatus documentStatus;

    @XmlElement(name="Hash")
    private String hash;

    @XmlElement(name="SourceID")
    private String sourceId;

    @XmlElement(name="SystemEntryDate")
    private String systemEntryDate;

    @XmlElement(name="CustomerID")
    private int customerId;

    @XmlElement(name="DocumentTotals")
    private DocumentTotals documentTotals;

    @XmlElement(name="Line")
    private List<StockMovementLine> lines;

    /**
     * Gets the lines of this StockMovement.
     *
     * @return the lines
     */
    public List<StockMovementLine> getLines() {
        return lines;
    }

    /**
     * Gets the DocumentTotals.
     *
     * @return the document totals
     */
    public DocumentTotals getDocumentTotals() {
        return documentTotals;
    }

    /**
     * Gets the InvoiceNo.
     *
     * @return the invoice number
     */
    public String getDocumentNumber() {
        return documentNumber;
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
