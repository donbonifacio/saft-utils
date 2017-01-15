package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.elements.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
        return headerDiff();
    }

    private <T> Result checkField(T t1, T t2, Function<T, T> method) {
        final T v1 = method.apply(t1);
        final T v2 = method.apply(t2);

        if(!v1.equals(v2)) {
            return Result.failure("waza");
        }

        return Result.success();

    }

    private Result headerDiff() {
        List<Result> results = new ArrayList<Result>();
        Header h1 = file1.getHeader();
        Header h2 = file2.getHeader();

        //checkField(h1, h2, Header::getAuditFileVersion);

        if(!h1.getAuditFileVersion().equals(h2.getAuditFileVersion())) {
            results.add(Result.failure("Header.AuditFileVersion mismatch"));
        }

        return Result.fromResults(results);
    }
}
