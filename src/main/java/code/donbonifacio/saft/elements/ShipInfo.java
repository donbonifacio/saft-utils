package code.donbonifacio.saft.elements;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;

/**
 * This class represents Ship information, usually on a StockMovement.
 */
public final class ShipInfo {

    @XmlElement(name="Address")
    private Address address;

    /**
     * Creates a new ShipInfo.
     */
    public ShipInfo() {
        afterUnmarshal(null, null);
    }

    /**
     * The Ship Address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
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
        if(address == null) {
            address = new Address();
        }
    }
}
