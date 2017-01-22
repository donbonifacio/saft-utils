package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents the `TaxTableEntry` on a standard SAF-T file.
 *
 * This class is immutable.
 */
@XmlType(name="TaxEntryTable")
public final class TaxTableEntry {

    // maps a friendly name to a TaxTableEntry field getter
    public static final Map<String, Function<TaxTableEntry, Object>> FIELDS =
            ImmutableMap.<String, Function<TaxTableEntry, Object>>builder()
                    .put("MasterFiles.TaxTable.TaxTableEntry.TaxType", TaxTableEntry::getTaxType)
                    .put("MasterFiles.TaxTable.TaxTableEntry.TaxCountryRegion", TaxTableEntry::getTaxCountryRegion)
                    .put("MasterFiles.TaxTable.TaxTableEntry.TaxCode", TaxTableEntry::getTaxCode)
                    .put("MasterFiles.TaxTable.TaxTableEntry.TaxPercentage", TaxTableEntry::getTaxPercentage)
                    .build();

    @XmlElement(name="TaxType")
    private String taxType;

    @XmlElement(name="TaxCountryRegion")
    private String taxCountryRegion;

    @XmlElement(name="TaxCode")
    private String taxCode;

    @XmlElement(name="Description")
    private String description;

    @XmlElement(name="TaxPercentage")
    private double taxPercentage;

    /**
     * Gets the TaxType
     *
     * @return the tax type
     */
    public String getTaxType() {
        return taxType;
    }

    /**
     * Gets the TaxCountryRegion
     *
     * @return the tax country region
     */
    public String getTaxCountryRegion() {
        return taxCountryRegion;
    }

    /**
     * Gets the TaxCode
     *
     * @return the tax code
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * Gets the Description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the TaxPercentage
     *
     * @return the tax percentage
     */
    public double getTaxPercentage() {
        return taxPercentage;
    }
}
