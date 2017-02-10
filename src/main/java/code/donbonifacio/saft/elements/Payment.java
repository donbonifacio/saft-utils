package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents the Payment XML element of a SAF-T file.
 */
public final class Payment {

    // maps a friendly name to a Invoice field getter
    public static final Map<String, Function<Payment, Object>> FIELDS =
            ImmutableMap.<String, Function<Payment, Object>>builder()
                    .put("SourceDocuments.Payments.Payment.TransactionDate", Payment::getTransactionDate)
                    .build();

    @XmlElement(name="PaymentRefNo")
    private String paymentRefNo;

    @XmlElement(name="TransactionDate")
    private String transactionDate;

    @XmlElement(name="Line")
    private List<PaymentLine> paymentLines;

    /**
     * Gets the TransactionDate.
     *
     * @return the transaction date
     */
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * Gets the PaymentRefNo.
     *
     * @return the payment reference number
     */
    public String getPaymentRefNo() {
        return paymentRefNo;
    }

    /**
     * Gets the payment Lines,
     *
     * @return the payment lines
     */
    public List<PaymentLine> getLines() {
        return paymentLines;
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
        if(paymentLines == null) {
            paymentLines = ImmutableList.of();
        } else {
            paymentLines = ImmutableList.copyOf(paymentLines);
        }
    }
}
