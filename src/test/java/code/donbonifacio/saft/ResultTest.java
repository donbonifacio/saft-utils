package code.donbonifacio.saft;

import junit.framework.TestCase;

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
}
