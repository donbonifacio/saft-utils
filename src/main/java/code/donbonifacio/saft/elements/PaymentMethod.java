package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class tha represents the PaymentMethod element on SAF-T.
 */
public final class PaymentMethod {

    @XmlElement(name="PaymentMechanism")
    private PaymentMechanism paymentMechanism;

    @XmlElement(name="PaymentAmount")
    private double paymentAmount;

    @XmlElement(name="PaymentDate")
    private String paymentDate;

    /**
     * Gets the PaymentMechanism.
     *
     * @return the payment mechanism
     */
    public PaymentMechanism getPaymentMechanism() {
        return paymentMechanism;
    }

    /**
     * Gets the Payment amount
     * @return the amount
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Gets the payment date.
     *
     * @return the payment date.
     */
    public String getPaymentDate() {
        return paymentDate;
    }
}
