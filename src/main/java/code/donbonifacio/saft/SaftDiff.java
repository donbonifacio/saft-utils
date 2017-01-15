package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.elements.Header;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Calculates the difference between two SAF-T files
 */
public final class SaftDiff {

    private final AuditFile file1;
    private final AuditFile file2;

    private static final Map<String, Function<Header, Object>> headerMethods =
            ImmutableMap.<String, Function<Header, Object>>builder()
                    .put("Header.AuditFileVersion", Header::getAuditFileVersion)
                    .put("Header.CompanyID", Header::getCompanyID)
                    .put("Header.TaxRegistrationNumber", Header::getTaxRegistrationNumber)
                    .put("Header.TaxAccountingBasis", Header::getTaxAccountingBasis)
                    .put("Header.CompanyName", Header::getCompanyName)
                    .put("Header.FiscalYear", Header::getFiscalYear)
                    .put("Header.StartDate", Header::getStartDate)
                    .put("Header.EndDate", Header::getEndDate)
                    .put("Header.DateCreated", Header::getDateCreated)
                    .put("Header.CurrencyCode", Header::getCurrencyCode)
                    .put("Header.TaxEntity", Header::getTaxEntity)
                    .put("Header.ProductCompanyTaxID", Header::getProductCompanyTaxId)
                    .put("Header.SoftwareCertificateNumber", Header::getSoftwareCertificateNumber)
                    .put("Header.ProductID", Header::getProductId)
                    .put("Header.ProductVersion", Header::getProductVersion)
                    .build();

    /**
     * Creates a new differ for two AuditFiles
     * @param file1 the first file
     * @param file2 the second file:59
     *
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
        return headerDiff(file1.getHeader(), file2.getHeader());
    }

    /**
     * Checks if the same field of two objects have the same value.
     * Returns an explanatory result.
     * @param t1 first object
     * @param t2 second object
     * @param methodName descriptive label
     * @param method the method to call
     * @param <T> The parameter of the objects' Type
     * @return the result of the operation
     */
    private <T> Result checkField(T t1, T t2, String methodName, Function<T, Object> method) {
        Object v1 = method.apply(t1);
        Object v2 = method.apply(t2);

        if(v1 == v2) {
            return Result.success();
        }

        if(v1 == null) {
            v1 = "";
        }

        if(v2 == null) {
            v2 = "";
        }

        if(!v1.equals(v2)) {
            return Result.failure(methodName + " mismatch ['" + v1 + "' != '" + v2 +"']");
        }

        return Result.success();
    }

    /**
     * Gets the diffs from the header element
     * @return the result of the diff
     */
    private Result headerDiff(Header h1, Header h2) {
        List<Result> results = headerMethods.entrySet().stream()
                .map(entry -> checkField(h1, h2, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return Result.fromResults(results);
    }
}
