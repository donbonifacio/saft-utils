package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import java.util.Map;
import java.util.function.Function;

import static code.donbonifacio.saft.Util.compose;

/**
 * Represents a Line element, present on a Payment of a SAF-T XML file.
 */
public final class PaymentLine {

    // maps a friendly name to a InvoiceLine field getter
    public static final Map<String, Function<PaymentLine, Object>> FIELDS =
            ImmutableMap.<String, Function<PaymentLine, Object>>builder()
                    .put("SourceDocuments.Payments.Payment.Line.CreditAmount", PaymentLine::getCreditAmount)
                    .build();

    @XmlElement(name="LineNumber")
    private int lineNumber;

    @XmlElement(name="CreditAmount")
    private double creditAmount;

    @XmlElement(name="DebitAmount")
    private double debitAmount;

    /**
     * Gets the LineNumber.
     *
     * @return the line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Gets the CreditAmout.
     *
     * @return the credit amount
     */
    public double getCreditAmount() {
        return creditAmount;
    }

    /**
     * Gets the DebitAmout.
     *
     * @return the credit amount
     */
    public double getDebitAmount() {
        return debitAmount;
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
    }

}
