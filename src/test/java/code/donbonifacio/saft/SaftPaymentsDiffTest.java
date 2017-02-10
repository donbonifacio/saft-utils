package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.Payments;
import code.donbonifacio.saft.elements.SalesInvoices;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;

/**
 * Unit tests for diff on Payments class
 */
public final class SaftPaymentsDiffTest extends SaftDiffTest {

    /**
     * Gets the Class under test
     *
     * @return the class under test
     */
    @Override
    protected Class getTestClass() {
        return Payments.class;
    }

    /**
     * Given a list of Payments fields, generates tests that create specific
     * AuditFiles for each field and check that if they are different
     * we have an error.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMismatchTests() {
        return generateTestForFields(Payments.FIELDS, SaftDiffTest::createFieldMismatchTest);
    }

    /**
     * Given a list of SalesInvoices fields, generates tests that create specific
     * AuditFiles for each field and check that if they are equal the
     * diff succeeds.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMatchTests() {
        return generateTestForFields(Payments.FIELDS, SaftDiffTest::createFieldMismatchTest);
    }
}
