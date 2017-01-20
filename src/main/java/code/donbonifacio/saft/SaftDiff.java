package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.Address;
import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.elements.Header;
import code.donbonifacio.saft.elements.Product;
import com.google.common.collect.ImmutableMap;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static code.donbonifacio.saft.Util.compose;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Calculates the differences between two SAF-T files
 */
public final class SaftDiff {

    private final AuditFile file1;
    private final AuditFile file2;

    // maps a friendly name to a Header field getter
    static final Map<String, Function<Header, Object>> HEADER_METHODS =
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
                    .put("Header.CompanyAddress.AddressDetail", compose(Header::getCompanyAddress, Address::getAddressDetail))
                    .put("Header.CompanyAddress.City", compose(Header::getCompanyAddress, Address::getCity))
                    .put("Header.CompanyAddress.PostalCode", compose(Header::getCompanyAddress, Address::getPostalCode))
                    .put("Header.CompanyAddress.Country", compose(Header::getCompanyAddress, Address::getCountry))
                    .build();

    // maps a friendly name to a Product field getter
    static final Map<String, Function<Product, Object>> PRODUCT_METHODS =
            ImmutableMap.<String, Function<Product, Object>>builder()
                    //.put("MasterFiles.Product.ProductCode", Product::getProductCode)
                    .put("MasterFiles.Product.ProductType", Product::getProductType)
                    .put("MasterFiles.Product.ProductDescription", Product::getProductDescription)
                    .put("MasterFiles.Product.ProductNumberCode", Product::getProductNumberCode)
                    .build();

    /**
     * Creates a new differ for two AuditFiles
     * @param file1 the first file
     * @param file2 the second file
     *
     */
    public SaftDiff(AuditFile file1, AuditFile file2) {
        this.file1 = file1;
        this.file2 = file2;
    }

    /**
     * Processes the given data and returns a Result
     *
     * @return the result
     */
    public Result process() {
        List<Result> results = headerDiff(file1.getHeader(), file2.getHeader());
        results.addAll(modelDiff(file1.getMasterFiles().getProducts(), file2.getMasterFiles().getProducts(), PRODUCT_METHODS, Product::getProductCode));
        return Result.fromResults(results);
    }

    /**
     * Checks if the same field of two objects have the same value.
     * Returns an explanatory result.
     *
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
     *
     * @return the result of the diff
     */
    private List<Result> headerDiff(Header h1, Header h2) {
        return HEADER_METHODS.entrySet()
                .stream()
                .map(entry -> checkField(h1, h2, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Gets the diffs from the Product elements
     *
     * @return the result of the diff
     */
    private <T, E> List<Result> modelDiff(List<T> models1, List<T> models2, Map<String, Function<T, Object>> modelMethods, Function<T, E> keyGetter) {
        checkNotNull(models1);
        checkNotNull(models2);

        if(models1.isEmpty() && models2.isEmpty()) {
            return Result.success().asList();
        }

        if(models1.size() != models2.size()) {
            return Result.failure(String.format("%ss size mismatch [%s != %s]", "Product", models1.size(), models2.size())).asList();
        }

        List<Result> p1diffs = checkModels(models1, models2, modelMethods, keyGetter,true);
        List<Result> p2diffs = checkModels(models2, models1, modelMethods, keyGetter,false);

        p1diffs.addAll(p2diffs);

        return p1diffs;
    }

    /**
     * Checks that the products on the first collection exist on the
     * second collection. If checkFields and a match is found, then
     * all the fields will be compared.
     *
     * @param models1 the first collection of models
     * @param models2 the second collection of models
     * @param modelMethods the map of model fields to test
     * @param keyGetter a function that gets the key of the model
     * @param verifyFields true of the fields should be verified
     * @param <T> the model parameter
     * @param <E> the type of the model key
     * @return the list of results
     */
    private <T, E> List<Result> checkModels(List<T> models1, List<T> models2, Map<String, Function<T, Object>> modelMethods, Function<T, E> keyGetter, boolean verifyFields) {
        return models1.stream()
                .map(m1 -> {
                    E code = keyGetter.apply(m1);
                    Optional<T> m2 = findModel(models2, keyGetter, code);
                    if(!m2.isPresent()) {
                        return Result.failure(String.format("Product code %s not present on %s file", code, verifyFields ? "second" : "first"));
                    }

                    if(verifyFields) {
                        List<Result> fieldResults = modelMethods.entrySet()
                                .stream()
                                .map(entry -> checkField(m1, m2.get(), entry.getKey(), entry.getValue()))
                                .collect(Collectors.toList());

                        return Result.fromResults(fieldResults);
                    }

                    return Result.success();

                }).collect(Collectors.toList());
    }

    /**
     * Finds a model by a key on a collection of models
     *
     * @param models the collection of models
     * @param keyGetter the function that obtains the key value
     * @param code the key code to search for
     * @param <T> the model type
     * @param <E> the type of the key
     * @return an option model
     */
    private <T, E> Optional<T> findModel(List<T> models, Function<T, E> keyGetter, E code) {
        if(models == null) {
            return Optional.empty();
        }
        return models.stream()
                .filter(model -> {
                    E key = keyGetter.apply(model);
                    return key == code || key.equals(code);
                })
                .findAny();
    }
}
