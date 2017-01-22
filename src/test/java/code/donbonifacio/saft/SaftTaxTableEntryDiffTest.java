package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.Product;
import code.donbonifacio.saft.elements.TaxTableEntry;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit tests for diff on TaxTableEntry class
 */
public class SaftTaxTableEntryDiffTest extends SaftDiffTest {

    /**
     * Gets the Class under test
     *
     * @return the class under test
     */
    @Override
    protected Class getTestClass() {
        return TaxTableEntry.class;
    }

    /**
     * Given a list of Product fields, generates tests that create specific
     * AuditFiles for each field and check that if they are different
     * we have an error.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMismatchProductTests() {
        return TaxTableEntry.FIELDS
                .entrySet()
                .stream()
                .map(entry -> entry.getKey())
                .map(field -> createFieldMismatchTest(field))
                .collect(Collectors.toList());
    }
}
