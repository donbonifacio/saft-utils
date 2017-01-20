package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.exceptions.SaftLoaderException;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit tests for diff on the Product class
 */
public class SaftProductDiffTest extends SaftDiffTest {

    /**
     * Given a list of Product fields, generates tests that create specific
     * AuditFiles for each field and check that if they are different
     * we have an error.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMismatchProductTests() {
        return SaftDiff.PRODUCT_METHODS
                .entrySet()
                .stream()
                .map(entry -> entry.getKey())
                .map(field -> createFieldMismatchTest(field))
                .collect(Collectors.toList());
    }

    /**
     * Test that files with different products report size mismatch error
     *
     * @throws SaftLoaderException
     */
    public void testSizeMismatch() throws SaftLoaderException {
        AuditFile withProducts = createAuditFile(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                    "<MasterFiles>",
                        "<Product>",
                            "<ProductCode>1</ProductCode>",
                        "</Product>",
                    "</MasterFiles>",
                "</AuditFile>");

        AuditFile emptyProducts = createAuditFile(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                        "<MasterFiles>",
                        "</MasterFiles>",
                    "</AuditFile>");

        Result result = new SaftDiff(emptyProducts, withProducts).process();
        assertFalse(result.isSucceeded());
        assertEquals("Products size mismatch [0 != 1]", result.getReason());
    }
}
