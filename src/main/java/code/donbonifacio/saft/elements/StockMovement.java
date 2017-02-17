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
public final class StockMovement extends AbstractDocument {

    // maps a friendly name to a StockMovement field getter
    public static final Map<String, Function<StockMovement, Object>> FIELDS =
            ImmutableMap.<String, Function<StockMovement, Object>>builder()
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentStatus.MovementStatus", compose(StockMovement::getDocumentStatus, DocumentStatus::getInvoiceStatus))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentStatus.MovementStatusDate", compose(StockMovement::getDocumentStatus, DocumentStatus::getInvoiceStatusDate))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentStatus.SourceID", compose(StockMovement::getDocumentStatus, DocumentStatus::getSourceId))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentStatus.SourceBilling", compose(StockMovement::getDocumentStatus, DocumentStatus::getSourceBilling))

                    .put("SourceDocuments.MovementOfGoods.StockMovement.Hash", StockMovement::getHash)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.MovementDate", StockMovement::getMovementDate)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.MovementStartTime", StockMovement::getMovementStartTime)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.MovementType", StockMovement::getMovementType)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.SourceID", StockMovement::getSourceId)

                    .put("SourceDocuments.MovementOfGoods.StockMovement.ShipTo.Address.AddressDetail", compose(StockMovement::getShipTo, ShipInfo::getAddress, Address::getAddressDetail))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.ShipTo.Address.City", compose(StockMovement::getShipTo, ShipInfo::getAddress, Address::getCity))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.ShipTo.Address.PostalCode", compose(StockMovement::getShipTo, ShipInfo::getAddress, Address::getPostalCode))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.ShipTo.Address.Country", compose(StockMovement::getShipTo, ShipInfo::getAddress, Address::getCountry))

                    .put("SourceDocuments.MovementOfGoods.StockMovement.ShipFrom.Address.AddressDetail", compose(StockMovement::getShipFrom, ShipInfo::getAddress, Address::getAddressDetail))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.ShipFrom.Address.City", compose(StockMovement::getShipFrom, ShipInfo::getAddress, Address::getCity))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.ShipFrom.Address.PostalCode", compose(StockMovement::getShipFrom, ShipInfo::getAddress, Address::getPostalCode))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.ShipFrom.Address.Country", compose(StockMovement::getShipFrom, ShipInfo::getAddress, Address::getCountry))

                    .put("SourceDocuments.MovementOfGoods.StockMovement.SystemEntryDate", StockMovement::getSystemEntryDate)
                    .put("SourceDocuments.MovementOfGoods.StockMovement.CustomerID", StockMovement::getCustomerId)

                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentTotals.TaxPayable", compose(StockMovement::getDocumentTotals, DocumentTotals::getTaxPayable))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentTotals.NetTotal", compose(StockMovement::getDocumentTotals, DocumentTotals::getNetTotal))
                    .put("SourceDocuments.MovementOfGoods.StockMovement.DocumentTotals.GrossTotal", compose(StockMovement::getDocumentTotals, DocumentTotals::getGrossTotal))
                    .build();

    @XmlElement(name="DocumentNumber")
    private String documentNumber;

    @XmlElement(name="ShipTo")
    private ShipInfo shipTo;

    @XmlElement(name="ShipFrom")
    private ShipInfo shipFrom;

    @XmlElement(name="Line")
    private List<StockMovementLine> lines;

    @XmlElement(name="MovementDate")
    private String movementDate;

    @XmlElement(name="MovementType")
    private MovementType movementType;

    @XmlElement(name="MovementStartTime")
    private String movementStartTime;

    /**
     * Gets the MovementStartTime.
     *
     * @return the movement start time
     */
    public String getMovementStartTime() {
        return movementStartTime;
    }

    /**
     * Gets the MovementType.
     *
     * @return the movement type
     */
    public MovementType getMovementType() {
        return movementType;
    }

    /**
     * Gets the MovementDate.
     *
     * @return the movement date
     */
    public String getMovementDate() {
        return movementDate;
    }

    /**
     * Gets the lines of this StockMovement.
     *
     * @return the lines
     */
    public List<StockMovementLine> getLines() {
        return lines;
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
     * Gets the ShipTo.
     *
     * @return the ship to info
     */
    public ShipInfo getShipTo() {
        return shipTo;
    }

    /**
     * Gets the ShipFrom.
     *
     * @return the ship from info
     */
    public ShipInfo getShipFrom() {
        return shipFrom;
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
        if(shipFrom == null) {
            shipFrom = new ShipInfo();
        }
        if(shipTo == null) {
            shipTo = new ShipInfo();
        }
    }
}
