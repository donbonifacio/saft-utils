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
     * @return the result
     */
    public Result process() {
        List<Result> results = headerDiff(file1.getHeader(), file2.getHeader());
        results.addAll(productDiff(file1.getMasterFiles().getProducts(), file2.getMasterFiles().getProducts()));
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
    private List<Result> productDiff(List<Product> products1, List<Product> products2) {
        checkNotNull(products1);
        checkNotNull(products2);

        if(products1.isEmpty() && products2.isEmpty()) {
            return new ArrayList<>(Arrays.asList(Result.success()));
        }

        if(products1.size() != products2.size()) {
            return Result.failure(String.format("Products size mismatch [%s != %s]", products1.size(), products2.size())).asList();
        }

        List<Result> p1diffs = checkProducs(products1, products2, true);
        List<Result> p2diffs = checkProducs(products2, products1, false);

        p1diffs.addAll(p2diffs);

        return p1diffs;
    }

    /**
     * Checks that the products on the first collection exist on the
     * second collection. If checkFields and a match is found, then
     * all the fields will be compared.
     *
     * @param products1 first collection
     * @param products2 second collection
     * @param verifyFields true to verify model fields
     * @return
     */
    private List<Result> checkProducs(List<Product> products1, List<Product> products2, boolean verifyFields) {
        return products1.stream()
                .map(p1 -> {
                    String code = p1.getProductCode();
                    Optional<Product> p2 = findProduct(products2, code);
                    if(!p2.isPresent()) {
                        return Result.failure(String.format("Product code %s not present on %s file", code, verifyFields ? "second" : "first"));
                    }

                    if(verifyFields) {
                        List<Result> fieldResults = PRODUCT_METHODS.entrySet()
                                .stream()
                                .map(entry -> checkField(p1, p2.get(), entry.getKey(), entry.getValue()))
                                .collect(Collectors.toList());

                        return Result.fromResults(fieldResults);
                    }

                    return Result.success();

                }).collect(Collectors.toList());
    }

    /**
     * Finds a product on a list of products
     *
     * @param products the list of products
     * @param code the code to search for
     * @return an optional product
     */
    private Optional<Product> findProduct(List<Product> products, String code) {
        if(products == null) {
            return Optional.empty();
        }
        return products.stream()
                .filter(product -> product.getProductCode() == code ||
                        product.getProductCode().equals(code))
                .findAny();
    }
}
