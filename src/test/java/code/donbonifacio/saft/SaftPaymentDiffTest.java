package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.Payment;
import code.donbonifacio.saft.elements.PaymentMechanism;
import code.donbonifacio.saft.elements.PaymentType;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.Map;

/**
 * Unit tests for diff on Payment class
 */
public final class SaftPaymentDiffTest extends SaftDiffTest {

    /**
     * Gets the Class under test
     *
     * @return the class under test
     */
    @Override
    protected Class getTestClass() {
        return Payment.class;
    }

    private static final Map<String, TestValues> TEST_VALUES =
            ImmutableMap.<String, TestValues>builder()
                    .put("SourceDocuments.Payments.Payment.PaymentType", new TestValues(PaymentType.RG, PaymentType.RC))
                    .put("SourceDocuments.Payments.Payment.PaymentMethod.PaymentMechanism", new TestValues(PaymentMechanism.CC, PaymentMechanism.MB))
                    .build();

    @Override
    protected TestValues getTestValues(String field) {
        TestValues values = TEST_VALUES.get(field);
        if(values != null) {
            return values;
        }
        return super.getTestValues(field);
    }

    /**
     * Given a list of Invoice fields, generates tests that create specific
     * AuditFiles for each field and check that if they are different
     * we have an error.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMismatchTests() {
        return generateTestForFields(Payment.FIELDS, SaftDiffTest::createFieldMismatchTest);
    }

    /**
     * Given a list of Invoice fields, generates tests that create specific
     * AuditFiles for each field and check that if they are equal,
     * the diff succeeds.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMatchTests() {
        return generateTestForFields(Payment.FIELDS, SaftDiffTest::createFieldMatchTest);
    }
}
