package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.exceptions.SaftLoaderException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit tests for the SAF-T differ
 */
public class SaftDiffTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SaftDiffTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(SaftDiffTest.class);
    }

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

    public void testAuditVersionMismatch() throws SaftLoaderException {
        final AuditFile f1 = SaftLoader.loadFromString(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "<Header>" +
                                "<AuditFileVersion>V1</AuditFileVersion>" +
                            "</Header>" +
                        "</AuditFile>"
        );
        final AuditFile f2 = SaftLoader.loadFromString(
                "<AuditFile xmlns=\"urn:OECD:StandardAuditFile-Tax:PT_1.03_01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "<Header>" +
                                "<AuditFileVersion>V2</AuditFileVersion>" +
                            "</Header>" +
                        "</AuditFile>"
        );
        final Result result = assertDiffFailure(f1, f2);
        assertEquals("Header.AuditFileVersion mismatch", result.getReason());

    }
}