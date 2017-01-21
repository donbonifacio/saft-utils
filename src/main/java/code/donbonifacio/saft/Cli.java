package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.exceptions.SaftLoaderException;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command line interface to access the lib's functionality.
 */
public final class Cli {

    static final Logger logger = LoggerFactory.getLogger(Cli.class);

    /**
     * Represents CLI arguments passed to the program
     *
     */
    private static class CliArgs {

        /**
         * The operation to perform
         */
        @Parameter(names = "-op", description = "The operation to perform")
        public String operation;

        /**
         * The name of the first saft file
         */
        @Parameter(names = "-file1", description = "The first file to use")
        public String file1;

        /**
         * The name of a second saft file
         */
        @Parameter(names = "-file2", description = "The second file to use")
        public String file2;

        /**
         * Show help/usage flag
         */
        @Parameter(names = "-help", description = "Shows usage")
        public boolean help = false;

        /**
         * Represents this object as a String
         *
         * @return a representation of the args
         */
        @Override public String toString() {
            if(help) {
                return "Args: help";
            }
            return String.format("Args: %s file1:%s file2:%s", operation, file1, file2);
        }
    }

    /**
     * Disable creating objects of this type
     */
    private Cli() {
        throw new AssertionError();
    }

    /**
     * The main entry point of the CLI
     *
     * @param argv the input arguments
     * @throws SaftLoaderException
     */
    public static void main( String[] argv ) throws SaftLoaderException {

        CliArgs args = new CliArgs();
        JCommander commander = new JCommander(args, argv);
        logger.trace("Input %s", args);

        if(args.help) {
            commander.usage();
        } else {
            logger.info("SAF-T Utilities");

            if("diff".equals(args.operation)) {
                AuditFile f1 = SaftLoader.loadFromFile(args.file1);
                AuditFile f2 = SaftLoader.loadFromFile(args.file2);
                SaftDiff diff = new SaftDiff(f1, f2);
                Result result = diff.process();
                logger.info("%s", result);
                if(result.isFailed()) {
                    System.exit(1);
                }
            }
        }

    }

}
