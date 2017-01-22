package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableList;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Represents the `TaxTable` element
 */
@XmlType(name="TaxTable")
public final class TaxTable {

    @XmlElement(name="TaxTableEntry")
    private List<TaxTableEntry> taxTableEntries;

    /**
     * Gets the tax table entries
     *
     * @return the tax table entries
     */
    public List<TaxTableEntry> getTaxTableEntries() {
        return taxTableEntries;
    }

    /**
     * Called by the deserializer when the object load is complete.
     * Transforms the entries in an immutable collection
     *
     * @param um the Unmarshaller
     * @param parent the parent object
     */
    // Used by the XML unmarshaller
    @SuppressWarnings("squid:UnusedPrivateMethod")
    private void afterUnmarshal(Unmarshaller um, Object parent) {
        if(taxTableEntries == null) {
            taxTableEntries = ImmutableList.of();
        } else {
            taxTableEntries = ImmutableList.copyOf(taxTableEntries);
        }
    }
}
