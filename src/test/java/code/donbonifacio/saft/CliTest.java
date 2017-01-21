package code.donbonifacio.saft;

import code.donbonifacio.saft.exceptions.SaftLoaderException;
import junit.framework.TestCase;

/**
 * Tests for the command line interface
 */
public class CliTest extends TestCase {

    /**
     * Smoke test for the help option
     *
     * @throws SaftLoaderException
     */
    public void testHelp() throws SaftLoaderException {
        Cli.main(new String[] {"-help"});
    }

    /**
     * Smoke test for the diff operation
     *
     * @throws SaftLoaderException
     */
    public void testDiff() throws SaftLoaderException {
        Cli.main(new String[] {
                "-op", "diff",
                "-file1", "resources/tests/saft.xml",
                "-file2", "resources/tests/saft.xml",
        });
    }
}
