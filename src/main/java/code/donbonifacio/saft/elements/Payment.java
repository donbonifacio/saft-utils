package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static code.donbonifacio.saft.Util.compose;

/**
 * Represents the Payment XML element of a SAF-T file.
 */
public final class Payment {

    // maps a friendly name to a Invoice field getter
    public static final Map<String, Function<Payment, Object>> FIELDS =
            ImmutableMap.<String, Function<Payment, Object>>builder()
                    .put("SourceDocuments.Payments.Payment.TransactionDate", Payment::getTransactionDate)
                    .put("SourceDocuments.Payments.Payment.PaymentType", Payment::getPaymentType)
                    .put("SourceDocuments.Payments.Payment.SystemID", Payment::getSystemId)
                    .put("SourceDocuments.Payments.Payment.DocumentStatus.PaymentStatus", compose(Payment::getDocumentStatus, DocumentStatus::getInvoiceStatus))
                    .put("SourceDocuments.Payments.Payment.DocumentStatus.PaymentStatusDate", compose(Payment::getDocumentStatus, DocumentStatus::getInvoiceStatusDate))
                    .put("SourceDocuments.Payments.Payment.DocumentStatus.SourceID", compose(Payment::getDocumentStatus, DocumentStatus::getSourceId))
                    .put("SourceDocuments.Payments.Payment.DocumentStatus.SourceBilling", compose(Payment::getDocumentStatus, DocumentStatus::getSourceBilling))
                    .put("SourceDocuments.Payments.Payment.PaymentMethod.PaymentMechanism", compose(Payment::getPaymentMethod, PaymentMethod::getPaymentMechanism))
                    .put("SourceDocuments.Payments.Payment.PaymentMethod.PaymentAmount", compose(Payment::getPaymentMethod, PaymentMethod::getPaymentAmount))
                    .put("SourceDocuments.Payments.Payment.PaymentMethod.PaymentDate", compose(Payment::getPaymentMethod, PaymentMethod::getPaymentDate))
                    .put("SourceDocuments.Payments.Payment.SourceID", Payment::getSourceId)
                    .put("SourceDocuments.Payments.Payment.SystemEntryDate", Payment::getSystemEntryDate)
                    .put("SourceDocuments.Payments.Payment.CustomerID", Payment::getCustomerId)
                    .put("SourceDocuments.Payments.Payment.DocumentTotals.TaxPayable", compose(Payment::getDocumentTotals, DocumentTotals::getTaxPayable))
                    .put("SourceDocuments.Payments.Payment.DocumentTotals.NetTotal", compose(Payment::getDocumentTotals, DocumentTotals::getNetTotal))
                    .put("SourceDocuments.Payments.Payment.DocumentTotals.GrossTotal", compose(Payment::getDocumentTotals, DocumentTotals::getGrossTotal))
                    .build();

    @XmlElement(name="PaymentRefNo")
    private String paymentRefNo;

    @XmlElement(name="TransactionDate")
    private String transactionDate;

    @XmlElement(name="Line")
    private List<PaymentLine> paymentLines;

    @XmlElement(name="PaymentType")
    private PaymentType paymentType;

    @XmlElement(name="SystemID")
    private String systemId;

    @XmlElement(name="DocumentStatus")
    private DocumentStatus documentStatus;

    @XmlElement(name="PaymentMethod")
    private PaymentMethod paymentMethod;

    @XmlElement(name="SourceID")
    private String sourceId;

    @XmlElement(name="SystemEntryDate")
    private String systemEntryDate;

    @XmlElement(name="CustomerID")
    private int customerId;

    @XmlElement(name="DocumentTotals")
    private DocumentTotals documentTotals;

    /**
     * Gets the DocumentTotals.
     *
     * @return the document totals
     */
    public DocumentTotals getDocumentTotals() {
        return documentTotals;
    }

    /**
     * Gets the SourceID.
     *
     * @return the source id
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Gets the SystemEntryDate.
     *
     * @return the system entry date
     */
    public String getSystemEntryDate() {
        return systemEntryDate;
    }

    /**
     * Gets the CustomerID.
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Gets the PaymentMethod.
     *
     * @return the payment method
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Gets the DocumentStatus.
     *
     * @return the document status
     */
    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    /**
     * Gets the SystemID.
     *
     * @return the system id
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Gets the PaymentType.
     *
     * @return the payment type
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

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
        if(documentStatus == null) {
            documentStatus = new DocumentStatus();
        }
        if(paymentMethod == null) {
            paymentMethod = new PaymentMethod();
        }
        if(documentTotals == null) {
            documentTotals = new DocumentTotals();
        }
    }
}
