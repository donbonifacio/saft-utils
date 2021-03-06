package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.InvoiceType;
import code.donbonifacio.saft.elements.MovementOfGoods;
import code.donbonifacio.saft.elements.MovementType;
import code.donbonifacio.saft.elements.StockMovement;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.Map;

/**
 * Unit tests for diff on StockMovement class
 */
public final class SaftStockMovementDiffTest extends SaftDiffTest {

    /**
     * Gets the Class under test
     *
     * @return the class under test
     */
    @Override
    protected Class getTestClass() {
        return StockMovement.class;
    }

    private static final Map<String, TestValues> TEST_VALUES =
            ImmutableMap.<String, TestValues>builder()
                    .put("SourceDocuments.MovementOfGoods.StockMovement.MovementType", new TestValues(MovementType.GR, MovementType.GT))
                    .build();

    @Override
    protected TestValues getTestValues(String field) {
        TestValues values = TEST_VALUES.get(field);
        if(values != null) {
            return values;
        }
        return super.getTestValues(field);
    }

    /**
     * Given a list of StockMovement fields, generates tests that create specific
     * AuditFiles for each field and check that if they are different
     * we have an error.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMismatchTests() {
        return generateTestForFields(StockMovement.FIELDS, SaftDiffTest::createFieldMismatchTest);
    }

    /**
     * Given a list of StockMovement fields, generates tests that create specific
     * AuditFiles for each field and check that if they are equal,
     * the diff succeeds.
     *
     * @return the list of generated tests
     */
    @TestFactory
    public List<DynamicTest> generateMatchTests() {
        return generateTestForFields(StockMovement.FIELDS, SaftDiffTest::createFieldMatchTest);
    }
}
