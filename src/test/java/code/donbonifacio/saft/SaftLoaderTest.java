package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.elements.Header;
import code.donbonifacio.saft.exceptions.SaftLoaderException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SaftLoaderTest
        extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SaftLoaderTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(SaftLoaderTest.class);
    }

    /**
     * Loads the simple SAF-T xml example and checks that the data
     * is the expected one
     */
    public void testLoadSampleFile() throws SaftLoaderException
    {
        final AuditFile auditFile = SaftLoader.loadFromFile("resources/tests/basicSaft.xml");

        final Header header = auditFile.getHeader();
        assertEquals("1.03_01", header.getAuditFileVersion());
        assertEquals("999999990", header.getCompanyID());
        assertEquals("999999990", header.getTaxRegistrationNumber());
        assertEquals("Pedro Santos", header.getCompanyName());
        assertEquals("F", header.getTaxAccountingBasis());
        assertEquals(2012, header.getFiscalYear());
    }

    /**
     * Checks that if we try to load an invalid file, we get a
     * prover exception
     */
    public void testLoadInvalidFile() {
        try {
            SaftLoader.loadFromFile("resources/tests/doesNotExist.xml");
            fail();
        } catch(SaftLoaderException ex) {
        }
    }

    /**
     * Tests trying to load null as a file name
     */
    public void testLoadNullFile() throws SaftLoaderException {
        try {
            SaftLoader.loadFromFile(null);
            fail();
        } catch(Exception npe) {
        }
    }

}
