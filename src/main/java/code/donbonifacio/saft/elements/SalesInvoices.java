package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableMap;

import javax.xml.bind.annotation.XmlElement;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents the SalesInvoices XML element of the SAF-T file.
 */
public final class SalesInvoices {

    // maps a friendly name to a TaxTableEntry field getter
    public static final Map<String, Function<SalesInvoices, Object>> FIELDS =
            ImmutableMap.<String, Function<SalesInvoices, Object>>builder()
                    .put("SourceDocuments.SalesInvoices.NumberOfEntries", SalesInvoices::getNumberOfEntries)
                    .put("SourceDocuments.SalesInvoices.TotalDebit", SalesInvoices::getTotalDebit)
                    .put("SourceDocuments.SalesInvoices.TotalCredit", SalesInvoices::getTotalCredit)
                    .build();

    @XmlElement(name="NumberOfEntries")
    private int numberOfEntries;

    @XmlElement(name="TotalDebit")
    private double totalDebit;

    @XmlElement(name="TotalCredit")
    private double totalCredit;

    @XmlElement(name="Invoice")
    private List<Invoice> invoices;

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
    public List<Invoice> getInvoices() {
        if(invoices == null) {
            invoices = Collections.<Invoice>emptyList();
        }
        return invoices;
    }
}
