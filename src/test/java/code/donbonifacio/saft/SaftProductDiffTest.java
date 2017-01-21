package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.elements.Product;
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
        return Product.FIELDS
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

    /**
     * Test that a file with two products detects missing producs on the second
     *
     * @throws SaftLoaderException
     */
    public void testTwoProductsMismatch() throws SaftLoaderException {
        AuditFile f1 = createAuditFile(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "<MasterFiles>",
                                "<Product>",
                                    "<ProductCode>1</ProductCode>",
                                "</Product>",
                                "<Product>",
                                    "<ProductCode>2</ProductCode>",
                                "</Product>",
                                "<Product>",
                                    "<ProductCode>3</ProductCode>",
                                "</Product>",
                            "</MasterFiles>",
                        "</AuditFile>");

        AuditFile f2 = createAuditFile(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "<MasterFiles>",
                                "<Product>",
                                    "<ProductCode>1</ProductCode>",
                                "</Product>",
                                "<Product>",
                                    "<ProductCode>2</ProductCode>",
                                "</Product>",
                                "<Product>",
                                    "<ProductCode>2</ProductCode>",
                                "</Product>",
                            "</MasterFiles>",
                        "</AuditFile>");

        Result result = new SaftDiff(f1, f2).process();
        assertTrue(result.isFailed());
        assertEquals("Product code 3 not present on second file", result.getReason());
    }

    /**
     * Test that a file with two products detects missing producs on
     * the first file
     *
     * @throws SaftLoaderException
     */
    public void testProductMissingFileOne() throws SaftLoaderException {
        AuditFile f1 = createAuditFile(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "<MasterFiles>",
                                "<Product>",
                                    "<ProductCode>1</ProductCode>",
                                "</Product>",
                                "<Product>",
                                    "<ProductCode>2</ProductCode>",
                                "</Product>",
                                "<Product>",
                                    "<ProductCode>2</ProductCode>",
                                "</Product>",
                            "</MasterFiles>",
                        "</AuditFile>");

        AuditFile f2 = createAuditFile(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "<MasterFiles>",
                                "<Product>",
                                    "<ProductCode>1</ProductCode>",
                                "</Product>",
                                "<Product>",
                                    "<ProductCode>2</ProductCode>",
                                "</Product>",
                                "<Product>",
                                    "<ProductCode>3</ProductCode>",
                                "</Product>",
                            "</MasterFiles>",
                        "</AuditFile>");

        Result result = new SaftDiff(f1, f2).process();
        assertTrue(result.isFailed());
        assertEquals("Product code 3 not present on first file", result.getReason());
    }
}
