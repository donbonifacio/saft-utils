package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.InvoiceLine;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;

/**
 * Unit tests for diff on Invoice Line class
 */
public final class SaftInvoiceLineDiffTest extends SaftDiffTest {

    /**
     * Gets the Class under test
     *
     * @return the class under test
     */
    @Override
    protected Class getTestClass() {
        return InvoiceLine.class;
    }

    /**
     * Given a list of Invoice Line fields, generates tests that create specific
     * AuditFiles for each field and check that if they are different
     * we have an error.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMismatchTests() {
        return generateTestForFields(InvoiceLine.FIELDS, SaftDiffTest::createFieldMismatchTest);
    }

    /**
     * Given a list of Invoice Line fields, generates tests that create specific
     * AuditFiles for each field and check that if they are equal,
     * the diff succeeds.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMatchTests() {
        return generateTestForFields(InvoiceLine.FIELDS, SaftDiffTest::createFieldMatchTest);
    }
}
