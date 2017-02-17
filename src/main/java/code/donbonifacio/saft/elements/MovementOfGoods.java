package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents the MovementOfGoods XML element of the SAF-T file.
 */
public final class MovementOfGoods {

    // maps a friendly name to a MovementOfGoods field getter
    public static final Map<String, Function<MovementOfGoods, Object>> FIELDS =
            ImmutableMap.<String, Function<MovementOfGoods, Object>>builder()
                    .put("SourceDocuments.MovementOfGoods.NumberOfMovementLines", MovementOfGoods::getNumberOfMovementLines)
                    .put("SourceDocuments.MovementOfGoods.TotalQuantityIssued", MovementOfGoods::getTotalQuantityIssued)
                    .build();

    @XmlElement(name="NumberOfMovementLines")
    private int numberOfMovementLines;

    @XmlElement(name="TotalQuantityIssued")
    private double totalQuantityIssued;

    @XmlElement(name="StockMovement")
    private List<StockMovement> stockMovements;

    /**
     * Returns the total number of entries.
     *
     * @return the number of entries
     */
    public int getNumberOfMovementLines() {
        return numberOfMovementLines;
    }

    /**
     * Returns the total quantity issued.
     *
     * @return the total quantity issued
     */
    public double getTotalQuantityIssued() {
        return totalQuantityIssued;
    }

    /**
     * Gets the list of invoices.
     *
     * @return the list of invoices
     */
    public List<StockMovement> getStockMovements() {
        return stockMovements;
    }

    /**
     * Called by the deserializer when the object load is complete. Transforms
     * invoices in an immutable list.
     *
     * @param um the Unmarshaller
     * @param parent the parent object
     */
    // Used by the XML unmarshaller
    @SuppressWarnings("squid:UnusedPrivateMethod")
    private void afterUnmarshal(Unmarshaller um, Object parent) {
        if(stockMovements == null) {
            stockMovements = ImmutableList.of();
        } else {
            stockMovements = ImmutableList.copyOf(stockMovements);
        }
    }
}
