package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.elements.Customer;
import code.donbonifacio.saft.exceptions.SaftLoaderException;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit tests for diff on the Product class
 */
public class SaftCustomerDiffTest extends SaftDiffTest {

    /**
     * Given a list of Customer fields, generates tests that create specific
     * AuditFiles for each field and check that if they are different
     * we have an error.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMismatchCustomerTests() {
        return Customer.FIELDS
                .entrySet()
                .stream()
                .map(entry -> entry.getKey())
                .map(field -> createFieldMismatchTest(field))
                .collect(Collectors.toList());
    }

    /**
     * Test that a file with two customers detects missing customers on the second
     *
     * @throws SaftLoaderException
     */
    public void testTwoCustomersMismatch() throws SaftLoaderException {
        AuditFile f1 = createAuditFile(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "<MasterFiles>",
                                "<Customer>",
                                    "<CustomerID>1</CustomerID>",
                                "</Customer>",
                                "<Customer>",
                                    "<CustomerID>2</CustomerID>",
                                "</Customer>",
                                "<Customer>",
                                    "<CustomerID>3</CustomerID>",
                                "</Customer>",
                            "</MasterFiles>",
                        "</AuditFile>");

        AuditFile f2 = createAuditFile(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "<MasterFiles>",
                                "<Customer>",
                                    "<CustomerID>1</CustomerID>",
                                "</Customer>",
                                "<Customer>",
                                    "<CustomerID>2</CustomerID>",
                                "</Customer>",
                                "<Customer>",
                                    "<CustomerID>2</CustomerID>",
                                "</Customer>",
                            "</MasterFiles>",
                        "</AuditFile>");

        Result result = new SaftDiff(f1, f2).process();
        assertTrue(result.isFailed());
        assertEquals("Customer '3' not present on second file", result.getReason());
    }

    /**
     * Test that a file with three customers detects missing customers on
     * the first file
     *
     * @throws SaftLoaderException
     */
    public void testCustomerMissingFileOne() throws SaftLoaderException {
        AuditFile f1 = createAuditFile(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                        "<MasterFiles>",
                            "<Customer>",
                                "<CustomerID>1</CustomerID>",
                            "</Customer>",
                            "<Customer>",
                                "<CustomerID>2</CustomerID>",
                            "</Customer>",
                            "<Customer>",
                                "<CustomerID>2</CustomerID>",
                            "</Customer>",
                        "</MasterFiles>",
                        "</AuditFile>");

        AuditFile f2 = createAuditFile(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "<MasterFiles>",
                                "<Customer>",
                                    "<CustomerID>1</CustomerID>",
                                "</Customer>",
                                "<Customer>",
                                    "<CustomerID>2</CustomerID>",
                                "</Customer>",
                                "<Customer>",
                                    "<CustomerID>3</CustomerID>",
                                "</Customer>",
                            "</MasterFiles>",
                        "</AuditFile>");

        Result result = new SaftDiff(f1, f2).process();
        assertTrue(result.isFailed());
        assertEquals("Customer '3' not present on first file", result.getReason());
    }
}
