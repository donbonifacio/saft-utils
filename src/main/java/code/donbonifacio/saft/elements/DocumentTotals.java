package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class for the DocumentTotals SAF-T element.
 */
public final class DocumentTotals {

    @XmlElement(name="TaxPayable")
    private double taxPayable;

    @XmlElement(name="NetTotal")
    private double netTotal;

    @XmlElement(name="GrossTotal")
    private double grossTotal;

    /**
     * Gets the tax payable.
     *
     * @return the amount
     */
    public double getTaxPayable() {
        return taxPayable;
    }

    /**
     * Gets the net total.
     *
     * @return the amount
     */
    public double getNetTotal() {
        return netTotal;
    }

    /**
     * Gets the gross total.
     *
     * @return the amount
     */
    public double getGrossTotal() {
        return grossTotal;
    }
}
