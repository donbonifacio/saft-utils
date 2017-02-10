package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents the Payments XML element of the SAF-T file.
 */
public final class Payments {

    // maps a friendly name to a Payments field getter
    public static final Map<String, Function<Payments, Object>> FIELDS =
            ImmutableMap.<String, Function<Payments, Object>>builder()
                    .put("SourceDocuments.Payments.NumberOfEntries", Payments::getNumberOfEntries)
                    .put("SourceDocuments.Payments.TotalDebit", Payments::getTotalDebit)
                    .put("SourceDocuments.Payments.TotalCredit", Payments::getTotalCredit)
                    .build();

    @XmlElement(name="NumberOfEntries")
    private int numberOfEntries;

    @XmlElement(name="TotalDebit")
    private double totalDebit;

    @XmlElement(name="TotalCredit")
    private double totalCredit;

    @XmlElement(name="Payment")
    private List<Payment> payments;

    /**
     * Returns the total number of entries.
     *
     * @return the number of entries
     */
    public double getNumberOfEntries() {
        return numberOfEntries;
    }

    /**
     * Returns the total debit amount.
     *
     * @return the total debit
     */
    public double getTotalDebit() {
        return totalDebit;
    }

    /**
     * Returns the total crefit amount.
     *
     * @return the total credit
     */
    public double getTotalCredit() {
        return totalCredit;
    }

    /**
     * Gets the list of invoices.
     *
     * @return the list of invoices
     */
    public List<Payment> getPayments() {
        return payments;
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
        if(payments == null) {
            payments = ImmutableList.of();
        } else {
            payments = ImmutableList.copyOf(payments);
        }
    }
}
