package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
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
        if(taxTableEntries == null) {
            return new ArrayList<>();
        }
        return taxTableEntries;
    }

}
