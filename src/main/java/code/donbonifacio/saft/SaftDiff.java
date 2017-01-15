package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;

/**
 * Calculates the difference between two SAF-T files
 */
public final class SaftDiff {

    private final AuditFile file1;
    private final AuditFile file2;

    /**
     * Creates a new differ for two AuditFiles
     * @param file1 the first file
     * @param file2 the second file
     */
    public SaftDiff(AuditFile file1, AuditFile file2) {
        this.file1 = file1;
        this.file2 = file2;
    }

    /**
     * Processes the given data and returns a Result
     * @return the result
     */
    public Result process() {
        return Result.success();

    }
}
