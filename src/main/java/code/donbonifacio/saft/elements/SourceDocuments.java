package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
 * Represents the SourceDocuments XML element of the SAF-T file.
 */
public final class SourceDocuments {

    @XmlElement(name="SalesInvoices")
    private SalesInvoices salesInvoices;

    @XmlElement(name="Payments")
    private Payments payments;

    @XmlElement(name="MovementOfGoods")
    private MovementOfGoods movementOfGoods;

    /**
     * Gets the Payments.
     *
     * @return the payments
     */
    public Payments getPayments() {
        if(payments == null) {
            payments = new Payments();
        }
        return payments;
    }

    /**
     * Gets the SalesInvoices.
     *
     * @return the sales invoices
     */
    public SalesInvoices getSalesInvoices() {
        if(salesInvoices == null) {
            salesInvoices = new SalesInvoices();
        }
        return salesInvoices;
    }

    /**
     * Returns the MovementOfGoods.
     *
     * @return the movement of goods.
     */
    public MovementOfGoods getMovementOfGoods() {
        if(movementOfGoods == null) {
            movementOfGoods = new MovementOfGoods();
        }
        return movementOfGoods;
    }

}
