package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.exceptions.SaftLoaderException;
import com.google.common.collect.ImmutableList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for the SAF-T differ
 */
public class SaftDiffTest {

    /**
     * Processes the diff between two AuditFiles
     * @param f1 the first AuditFile
     * @param f2 the second AuditFile
     * @return the Result
     */
    private Result assertDiffSuccess(AuditFile f1, AuditFile f2) {
        final SaftDiff diff = new SaftDiff(f1, f2);
        final Result result = diff.process();
        assertTrue(result.isSucceeded());
        return result;
    }

    /**
     * Checks that a diff failed
     * @param f1 the first AuditFile
     * @param f2 the second AuditFile
     * @return the result
     */
    private Result assertDiffFailure(AuditFile f1, AuditFile f2) {
        final SaftDiff diff = new SaftDiff(f1, f2);
        final Result result = diff.process();
        assertTrue("SAF-T diff should have failed", result.isFailed());
        return result;
    }

    /**
     * Should work for the basicSaft.xml when comparing
     * against itself
     * @throws SaftLoaderException
     */
    public void testSampleSaft() throws SaftLoaderException {
        final AuditFile auditFile = SaftLoader.loadFromFile("resources/tests/basicSaft.xml");
        assertDiffSuccess(auditFile, auditFile);
    }

    /**
     * Should work for the saft.xml when comparing
     * against itself
     * @throws SaftLoaderException
     */
    public void testSaftFile() throws SaftLoaderException {
        final AuditFile auditFile = SaftLoader.loadFromFile("resources/tests/saft.xml");
        assertDiffSuccess(auditFile, auditFile);
    }

    /**
     * Utility method that creates a simple SAF-T file just with the header
     * and a specific field
     * @param element the element field to create
     * @param value the element value
     * @return the XML String representation
     */
    private String singleHeaderElement(String element, Object value) {
        return "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                    "<Header>" +
                        "<"+element+">"+value+"</"+element+">" +
                    "</Header>" +
                "</AuditFile>";
    }

    /**
     * Given a list of header fields, generates tests that create specific
     * AuditFiles for each field and check that if they are different
     * we have an error
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> verifyMismatchHeaderTests() {
        final List<String> headerFields =
                ImmutableList.of("AuditFileVersion");

        final List<DynamicTest> tests = headerFields.stream()
                .map(headerField -> {
                    String testName = "Test header field " + headerField;
                    try {
                        final AuditFile f1 = SaftLoader.loadFromString(singleHeaderElement(headerField, "1"));
                        final AuditFile f2 = SaftLoader.loadFromString(singleHeaderElement(headerField, "2"));

                        Executable exec = () -> {
                            Result result = assertDiffFailure(f1, f2);
                            assertEquals(
                               "Header." + headerField + " mismatch ['1' != '2']",
                                result.getReason()
                            );
                        };

                        return DynamicTest.dynamicTest(testName, exec);
                    } catch(SaftLoaderException ex) {
                        return DynamicTest.dynamicTest(testName, () -> {
                            fail(ex.getMessage());
                        });
                    }
                })
                .collect(Collectors.toList());

        return tests;
    }
}