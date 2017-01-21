package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.Header;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit tests for the SaftDiff for Header element
 */
public final class SaftHeaderDiffTest extends SaftDiffTest {

    /**
     * Gets the class under test
     *
     * @return the class under test
     */
    @Override protected Class getTestClass() {
        return Header.class;
    }

    /**
     * Given a list of header fields, generates tests that create specific
     * AuditFiles for each field and check that if they are different
     * we have an error.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMismatchHeaderTests() {
     return Header.FIELDS
             .entrySet()
             .stream()
             .map(entry -> entry.getKey())
             .map(headerField -> createFieldMismatchTest(headerField))
             .collect(Collectors.toList());
    }

}
