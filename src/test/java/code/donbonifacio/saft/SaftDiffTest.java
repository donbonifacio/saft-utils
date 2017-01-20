package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.exceptions.SaftLoaderException;
import junit.framework.TestCase;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Unit tests for the SAF-T differ
 */
public class SaftDiffTest extends TestCase {

    /**
     * Processes the diff between two AuditFiles
     *
     * @param f1 the first AuditFile
     * @param f2 the second AuditFile
     * @return the Result
     */
    protected Result assertDiffSuccess(AuditFile f1, AuditFile f2) {
        final SaftDiff diff = new SaftDiff(f1, f2);
        final Result result = diff.process();
        assertTrue(result.isSucceeded());
        return result;
    }

    /**
     * Checks that a diff failed
     *
     * @param f1 the first AuditFile
     * @param f2 the second AuditFile
     * @return the result
     */
    protected Result assertDiffFailure(AuditFile f1, AuditFile f2) {
        final SaftDiff diff = new SaftDiff(f1, f2);
        final Result result = diff.process();
        assertTrue("SAF-T diff should have failed", result.isFailed());
        return result;
    }

    /**
     * Should work for the basicSaft.xml when comparing
     * against itself
     *
     * @throws SaftLoaderException
     */
    public void testSampleSaft() throws SaftLoaderException {
        final AuditFile auditFile = SaftLoader.loadFromFile("resources/tests/basicSaft.xml");
        assertDiffSuccess(auditFile, auditFile);
    }

    /**
     * Should work for the saft.xml when comparing
     * against itself
     *
     * @throws SaftLoaderException
     */
    public void testSaftFile() throws SaftLoaderException {
        final AuditFile auditFile = SaftLoader.loadFromFile("resources/tests/saft.xml");
        assertDiffSuccess(auditFile, auditFile);
    }

    /**
     * Utility method that creates a simple SAF-T file just with the header
     * and a specific field
     *
     * @param element the element field to create
     * @param value the element value
     * @return the XML String representation
     */
    protected String singleElement(String element, Object value) {
        StringBuilder builder = new StringBuilder();
        builder.append("<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");

        String[] elements = element.split(Pattern.quote("."));
        if(elements.length < 2) {
            throw new IllegalArgumentException();
        }
        String field = elements[elements.length-1];
        String[] path = Arrays.copyOfRange(elements, 0, elements.length-1);
        for(String pathElement : path) {
            builder.append(String.format("<%s>", pathElement));
        }
        builder.append(String.format("<%s>%s</%s>", field, value, field));
        for(int i = path.length-1; i >= 0; --i) {
            String pathElement = path[i];
            builder.append(String.format("</%s>", pathElement));
        }
        builder.append("</AuditFile>");

        return builder.toString();
    }

    /**
     * Creates an AuditFile from a vargars of Strings. It will join them
     * all and create an AuditFile from it.
     *
     * @param args all the strings to join
     * @return the AuditFile
     * @throws SaftLoaderException
     */
    protected AuditFile createAuditFile(String... args) throws SaftLoaderException {
        String raw = Arrays.stream(args).collect(Collectors.joining());
        return SaftLoader.loadFromString(raw);
    }

    /**
     * Creates a test that verifies that if we have different values on
     * the given field, we have a proper error.
     * @param field the field to consider
     * @return the DynamicTest
     */
    protected DynamicTest createFieldMismatchTest(String field) {
        String testName = "Test header field " + field;
        try {
            final AuditFile f1 = SaftLoader.loadFromString(singleElement(field, "1"));
            final AuditFile f2 = SaftLoader.loadFromString(singleElement(field, "2"));

            Executable exec = () -> {
                Result result = assertDiffFailure(f1, f2);
                assertTrue("Result should fail", result.isFailed());
                assertEquals(
                   field + " mismatch ['1' != '2']",
                    result.getReason()
                );
            };

            return DynamicTest.dynamicTest(testName, exec);
        } catch(SaftLoaderException ex) {
            return DynamicTest.dynamicTest(testName, () -> {
                fail(ex.getMessage());
            });
        }
    }

}