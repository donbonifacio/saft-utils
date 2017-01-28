package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * Representation of the SpecialRegimes SAF-T XML element.
 * This is an immutable class
 */
public final class SpecialRegimes {

    @XmlElement(name="SelfBillingIndicator")
    private String selfBillingIndicator;

    @XmlElement(name="CashVATSchemeIndicator")
    private String cashVatSchemeIndicator;

    @XmlElement(name="ThirdPartiesBillingIndicator")
    private String thirdPartiesBillingIndicator;

    /**
     * Gets the SelfBillingIndicator
     *
     * @return the self billing indicator
     */
    public String getSelfBillingIndicator() {
        return selfBillingIndicator;
    }

    /**
     * Gets the CashVATSchemeIndicator
     *
     * @return the cash VAT scheme indicator
     */
    public String getCashVatSchemeIndicator() {
        return cashVatSchemeIndicator;
    }

    /**
     * Gets the ThirdPartiesBillingIndicator
     * @return the third parties billing indicator
     */
    public String getThirdPartiesBillingIndicator() {
        return thirdPartiesBillingIndicator;
    }
}
