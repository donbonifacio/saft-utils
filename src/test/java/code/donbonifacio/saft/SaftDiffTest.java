package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.elements.Header;
import code.donbonifacio.saft.exceptions.SaftLoaderException;
import junit.framework.Test;
import junit.framework.TestCase;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
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

        assertTrue(String.format("SAF-T diff should have failed: %s", result.summary()), result.isFailed());
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
     * Gets the Class under test
     *
     * @return the class under test
     */
    protected Class getTestClass() {
        return SaftDiff.class;
    }

    /**
     * Represents a pair of test values to apply to saft failure diffs.
     */
    protected static class TestValues {
        final Object value1;
        final Object value2;
        final static TestValues DEFAULT = new TestValues("1", "2");

        TestValues(Object v1, Object v2) {
            this.value1 = v1;
            this.value2 = v2;
        }
    }

    /**
     * Given a field name, returns a pair with test values. The key is
     * one field value and the value is the other. Should be different.
     *
     * @param field the field name
     * @return a pair with test values
     */
    protected TestValues getTestValues(String field) {
        return TestValues.DEFAULT;
    }

    /**
     * Creates a test that verifies that if we have different values on
     * the given field, we have a proper error.
     * @param field the field to consider
     * @return the DynamicTest
     */
    protected DynamicTest createFieldMismatchTest(String field) {
        String testName = "Test field mismatch " + field;
        Class testClass = getTestClass();
        try {
            final TestValues testValues = getTestValues(field);
            final String saft1 = singleElement(field, testValues.value1);
            final String saft2 = singleElement(field, testValues.value2);
            final AuditFile f1 = SaftLoader.loadFromString(saft1);
            final AuditFile f2 = SaftLoader.loadFromString(saft2);

            Executable exec = () -> {
                Result result = new SaftDiff(f1, f2).process();
                assertTrue(String.format("Result should fail: %s\nsaft1: %s\nsaft2: %s", result.summary(), saft1, saft2), result.isFailed());
                String expected = String.format("^(.*)%s '(%s|0)': %s mismatch \\['%s(.\\d+)?' != '%s(.\\d+)?'\\]$", testClass.getSimpleName(), "", field, testValues.value1, testValues.value2);
                assertTrue(String.format("%s doesn't match %s", result.summary(), expected ),
                        result.getReason().matches(expected));
            };

            return DynamicTest.dynamicTest(testName, exec);
        } catch(SaftLoaderException ex) {
            return DynamicTest.dynamicTest(testName, () -> {
                fail(ex.getMessage());
            });
        }
    }

    /**
     * Creates a test that verifies that if we have equal values on
     * the given field, the diff succeeds.
     *
     * @param field the field to consider
     * @return the DynamicTest
     */
    protected DynamicTest createFieldMatchTest(String field) {
        String testName = "Test field match " + field;
        Class testClass = getTestClass();
        try {
            final TestValues testValues = getTestValues(field);
            final String saft1 = singleElement(field, testValues.value1);
            final String saft2 = singleElement(field, testValues.value1);
            final AuditFile f1 = SaftLoader.loadFromString(saft1);
            final AuditFile f2 = SaftLoader.loadFromString(saft2);

            Executable exec = () -> {
                Result result = new SaftDiff(f1, f2).process();
                assertTrue(String.format("Result should succeeded: %s\nsaft1: %s\nsaft2: %s", result.summary(), saft1, saft2), result.isSucceeded());
            };

            return DynamicTest.dynamicTest(testName, exec);
        } catch(SaftLoaderException ex) {
            return DynamicTest.dynamicTest(testName, () -> {
                fail(ex.getMessage());
            });
        }
    }

    /**
     * For each model field given on the first param, generates a test
     * using the generator on the second param.
     *
     * @param fields the fields to consider
     * @param generator the generator function to apply
     * @param <T> the model class
     * @return a list of tests
     */
    protected <T> List<DynamicTest> generateTestForFields(Map<String, Function<T, Object>> fields, BiFunction<SaftDiffTest, String, DynamicTest> generator) {
        return fields
                .entrySet()
                .stream()
                .map(entry -> entry.getKey())
                .map(field -> generator.apply(this, field))
                .collect(Collectors.toList());
    }
}