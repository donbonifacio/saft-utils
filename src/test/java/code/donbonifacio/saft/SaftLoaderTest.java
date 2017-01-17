package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.*;
import code.donbonifacio.saft.exceptions.SaftLoaderException;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Unit tests for the SaftLoader
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
     * Given an AutitFile for the basicSaft.xml, tests it's content
     * @param auditFile the AuditFile for basicSaft.xml
     */
    private void assertBasicSaft(final AuditFile auditFile) {
        checkNotNull(auditFile);

        final Header header = auditFile.getHeader();
        checkNotNull(header);
        assertEquals("1.03_01", header.getAuditFileVersion());
        assertEquals("999999990", header.getCompanyID());
        assertEquals("999999990", header.getTaxRegistrationNumber());
        assertEquals("Pedro Santos", header.getCompanyName());
        assertEquals("F", header.getTaxAccountingBasis());
        assertEquals(2012, header.getFiscalYear());
        assertEquals("Example Street", header.getCompanyAddress().getAddressDetail());
        assertEquals("Lisbon", header.getCompanyAddress().getCity());
        assertEquals("2855-097", header.getCompanyAddress().getPostalCode());
        assertEquals("PT", header.getCompanyAddress().getCountry());
        assertEquals("2012-01-01", header.getStartDate());
        assertEquals("2012-12-31", header.getEndDate());
        assertEquals("EUR", header.getCurrencyCode());
        assertEquals("2017-01-14", header.getDateCreated());
        assertEquals("Global", header.getTaxEntity());
        assertEquals("508025338", header.getProductCompanyTaxId());
        assertEquals("123", header.getSoftwareCertificateNumber());
        assertEquals("ProductID", header.getProductId());
        assertEquals("1.0", header.getProductVersion());

        final MasterFiles masterFiles = auditFile.getMasterFiles();
        checkNotNull(masterFiles);

        final Product product = masterFiles.getProducts().get(0);
        checkNotNull(product);
        assertEquals("S", product.getProductType());
        assertEquals("Product Code", product.getProductCode());
        assertEquals("Product Description", product.getProductDescription());
        assertEquals("Product Number Code", product.getProductNumberCode());

        final Customer customer = masterFiles.getCustomers().get(0);
        checkNotNull(customer);
        assertEquals("123", customer.getCustomerId());
        assertEquals("Desconhecido", customer.getAccountId());
        assertEquals("123123123", customer.getCustomerTaxId());
        assertEquals("Claudinha", customer.getCompanyName());
        assertEquals("Desconhecido", customer.getBillingAddress().getAddressDetail());
        assertEquals("Desconhecido", customer.getBillingAddress().getCity());
        assertEquals("0000-000", customer.getBillingAddress().getPostalCode());
        assertEquals("PT", customer.getBillingAddress().getCountry());
        assertEquals("Desconhecido", customer.getTelephone());
        assertEquals("Desconhecido", customer.getFax());
        assertEquals("Desconhecido", customer.getEmail());
        assertEquals("Desconhecido", customer.getWebsite());
        assertEquals(0, customer.getSelfBillingIndicator());
    }

    /**
     * Loads the simple SAF-T xml example and checks that the data
     * is the expected one
     *
     * @throws SaftLoaderException
     */
    public void testLoadSampleFile() throws SaftLoaderException {
        final AuditFile auditFile = SaftLoader.loadFromFile("resources/tests/basicSaft.xml");
        assertBasicSaft(auditFile);
    }

    /**
     * Tests the loading of basicSaft.xml via raw string
     * @throws SaftLoaderException
     * @throws IOException
     */
    public void testLoadSampleFileString() throws SaftLoaderException, IOException {
        final String raw = Files.toString(new File("resources/tests/basicSaft.xml"), Charsets.UTF_8);
        final AuditFile auditFile = SaftLoader.loadFromString(raw);
        assertBasicSaft(auditFile);
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

    /**
     * Tests trying to load invalid data as String
     */
    public void testLoadInvalidString() throws SaftLoaderException {
        try {
            SaftLoader.loadFromString("waza");
            fail();
        } catch(SaftLoaderException ex) {
        }
    }
}
