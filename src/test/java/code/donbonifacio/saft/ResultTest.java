package code.donbonifacio.saft;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Tests for the Result class
 */
public class ResultTest extends TestCase {

    /**
     * Tests the Result.success singleton
     */
    public void testSuccessSingleton() {
        Result r1 = Result.success();
        assertTrue(r1.isSucceeded());
        assertFalse(r1.isFailed());

        Result r2 = Result.success();
        assertTrue(r2.isSucceeded());
        assertFalse(r2.isFailed());

        assertTrue(r1 == r2);
    }

    /**
     * Tests a failed result via exception
     */
    public void testResultException() {
        Exception ex = new RuntimeException();
        Result result = Result.exception(ex);
        assertFalse(result.isSucceeded());
        assertTrue(result.isException());
    }

    /**
     * Test toString generation
     */
    public void testToString() {
        Result r1 = Result.failure("Test1");
        Result r2 = Result.failure("Test2");
        Result r3 = Result.fromResults(Arrays.asList(r1, r2));

        assertEquals("Result{succeeded=false, reason='From list of results', results=[Result{succeeded=false, reason='Test1'}, Result{succeeded=false, reason='Test2'}, ]}", r3.toString());
    }

    /**
     * If fromResults gets a collection with only one result, return
     * that result
     */
    public void testFromResultsAsOne() {
        Result r1 = Result.success();
        Result r2 = Result.fromResults(r1.asList());

        assertTrue(r1 == r2);
    }
}
