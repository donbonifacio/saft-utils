package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.*;
import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Finds differences between two SAF-T files, given as two AuditFile
 * instances that have all the SAF-T data.
 *
 */
public final class SaftDiff {

    static final Logger logger = LoggerFactory.getLogger(SaftDiff.class);

    private final AuditFile file1;
    private final AuditFile file2;

    /**
     * Creates a new differ for two AuditFiles
     *
     * @param file1 the first file
     * @param file2 the second file
     *
     */
    public SaftDiff(AuditFile file1, AuditFile file2) {
        this.file1 = checkNotNull(file1);
        this.file2 = checkNotNull(file2);
    }

    /**
     * Utility class built to store arguments to pass to private
     * methods of this class. It holds two collections of a model T,
     * a map of the model fields to compare, and a lambda to obtain
     * the key attribute of the model.
     *
     * @param <T> The type of the model
     * @param <E> The type of the model's key
     */
    private final class ModelData<T, E> {
        final String modelName;
        final List<T> models1;
        final List<T> models2;
        final Map<String, Function<T, Object>> modelMethods;
        final Map<E, List<T>> models2Cache;
        final Function<T, E> keyGetter;
        final Optional<BiFunction<T, T, Result>> customDiffer;

        /**
         * Creates a new ModelData
         *
         * @param models1 the first batch of data
         * @param models2 the second batch of data
         * @param modelMethods the methods that compare two instances
         * @param keyGetter the method that gets the key value
         * @param customDiffer optional function for extra diff logic
         */
        ModelData(String modelName, List<T> models1, List<T> models2, Map<String, Function<T, Object>> modelMethods, Function<T, E> keyGetter, Optional<BiFunction<T, T, Result>> customDiffer) {
            this.modelName = checkNotNull(modelName);

            List<T> m1 = models1;
            if(m1 == null) {
                m1 = ImmutableList.of();
            }
            this.models1 = m1;

            List<T> m2 = models2;
            if(m2 == null) {
                m2 = ImmutableList.of();
            }
            this.models2 = m2;

            this.modelMethods = checkNotNull(modelMethods);
            this.keyGetter = checkNotNull(keyGetter);

            this.models2Cache = ImmutableMap.copyOf(
                    this.models2.stream()
                    .filter(model -> keyGetter.apply(model) != null)
                    .collect(Collectors.groupingBy(keyGetter)));

            this.customDiffer = customDiffer;
        }

        /**
         * Usually the operations are relative from models1 applied to
         * models2. This method switches that order.
         *
         * @return a new ModelData with collections switched
         */
        private ModelData<T,E> switchModels() {
            return new ModelData<>(modelName, models2, models1, modelMethods, keyGetter, customDiffer);
        }

        /**
         * Based on the firstPass boolean, returns the proper ModelData.
         * If true, returns itself. If false, then switches the model
         * collections.
         *
         * @param firstPass true if it's the first pass of the diff
         * @return the appropriate ModelData for the given pass
         */
        public ModelData<T, E> prepareFor(boolean firstPass) {
            if(firstPass) {
                return this;
            } else {
                return switchModels();
            }
        }
    }

    /**
     * Uses the given AuditFiles to find all the diffs on the data
     *
     * @return the result
     */
    public Result process() {
        logger.trace("Testing Header...");
        List<Result> results = headerDiff(file1.getHeader(), file2.getHeader());

        logger.trace("Testing products...");
        ModelData<Product, String> productsData = new ModelData<>(
                "Product",
                file1.getMasterFiles().getProducts(),
                file2.getMasterFiles().getProducts(),
                Product.FIELDS,
                Product::getProductCode,
                Optional.empty()
        );
        results.addAll(modelDiff(productsData));

        logger.trace("Testing customers...");
        ModelData<Customer, String> customersData = new ModelData<>(
                "Customer",
                file1.getMasterFiles().getCustomers(),
                file2.getMasterFiles().getCustomers(),
                Customer.FIELDS,
                Customer::getCustomerId,
                Optional.empty()
        );
        results.addAll(modelDiff(customersData));

        logger.trace("Testing tax table...");
        ModelData<TaxTableEntry, String> taxTableData = new ModelData<>(
                "TaxTableEntry",
                file1.getMasterFiles().getTaxTable().getTaxTableEntries(),
                file2.getMasterFiles().getTaxTable().getTaxTableEntries(),
                TaxTableEntry.FIELDS,
                TaxTableEntry::getDescription,
                Optional.empty()
        );
        results.addAll(modelDiff(taxTableData));

        logger.trace("Testing sales invoices...");
        results.addAll(paymentsDiff(file1.getSourceDocuments().getPayments(), file2.getSourceDocuments().getPayments()));

        logger.trace("Testing invoices...");
        ModelData<Invoice, String> invoicesData = new ModelData<>(
                "Invoice",
                file1.getSourceDocuments().getSalesInvoices().getInvoices(),
                file2.getSourceDocuments().getSalesInvoices().getInvoices(),
                Invoice.FIELDS,
                Invoice::getInvoiceNo,
                Optional.of((invoice1, invoice2) -> {
                    ModelData<InvoiceLine, Integer> linesData = new ModelData<>(
                            "InvoiceLine",
                            invoice1.getLines(),
                            invoice2.getLines(),
                            InvoiceLine.FIELDS,
                            InvoiceLine::getLineNumber,
                            Optional.empty()
                    );
                    return Result.fromResults(modelDiff(linesData));
                })
        );
        results.addAll(modelDiff(invoicesData));

        logger.trace("Testing payments...");
        results.addAll(salesInvoicesDiff(file1.getSourceDocuments().getSalesInvoices(), file2.getSourceDocuments().getSalesInvoices()));

        ModelData<Payment, String> paymentsData = new ModelData<>(
                "Payment",
                file1.getSourceDocuments().getPayments().getPayments(),
                file2.getSourceDocuments().getPayments().getPayments(),
                Payment.FIELDS,
                Payment::getPaymentRefNo,
                Optional.of((payment1, payment2) -> {
                    ModelData<PaymentLine, Integer> linesData = new ModelData<>(
                            "PaymentLine",
                            payment1.getLines(),
                            payment2.getLines(),
                            PaymentLine.FIELDS,
                            PaymentLine::getLineNumber,
                            Optional.empty()
                    );
                    return Result.fromResults(modelDiff(linesData));
                })
        );
        results.addAll(modelDiff(paymentsData));

        logger.trace("Testing movement of goods...");
        results.addAll(movementOfGoodsDiff(file1.getSourceDocuments().getMovementOfGoods(), file2.getSourceDocuments().getMovementOfGoods()));

        ModelData<StockMovement, String> movementData = new ModelData<>(
                "StockMovement",
                file1.getSourceDocuments().getMovementOfGoods().getStockMovements(),
                file2.getSourceDocuments().getMovementOfGoods().getStockMovements(),
                StockMovement.FIELDS,
                StockMovement::getDocumentNumber,
                Optional.of((mov1, mov2) -> {
                    ModelData<StockMovementLine, Integer> linesData = new ModelData<>(
                            "StockMovementLine",
                            mov1.getLines(),
                            mov2.getLines(),
                            StockMovementLine.FIELDS,
                            StockMovementLine::getLineNumber,
                            Optional.empty()
                    );
                    return Result.fromResults(modelDiff(linesData));
                })
        );
        results.addAll(modelDiff(movementData));

        return Result.fromResults(ImmutableList.copyOf(results));
    }

    /**
     * Gets the diffs from the header element
     *
     * @return the result of the diff
     */
    private List<Result> headerDiff(Header h1, Header h2) {
        return Header.FIELDS.entrySet()
                .stream()
                .map(entry -> compareField("Header", "", h1, h2, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Gets the diffs from the SalesInvoices elements.
     *
     * @param s1 one SalesInvoices
     * @param s2 other SalesInvoices
     * @return the list of results
     */
    private List<Result> salesInvoicesDiff(SalesInvoices s1, SalesInvoices s2) {
        return SalesInvoices.FIELDS.entrySet()
                .stream()
                .map(entry -> compareField("SalesInvoices", "", s1, s2, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Gets the diffs from the MovementOfGoods elements.
     *
     * @param s1 one MovementOfGoods
     * @param s2 other MovementOfGoods
     * @return the list of results
     */
    private List<Result> movementOfGoodsDiff(MovementOfGoods s1, MovementOfGoods s2) {
        return MovementOfGoods.FIELDS.entrySet()
                .stream()
                .map(entry -> compareField("MovementOfGoods", "", s1, s2, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Gets the diffs from the Payments elements.
     *
     * @param p1 one Payments
     * @param p2 other Payments
     * @return the list of results
     */
    private List<Result> paymentsDiff(final Payments p1, final Payments p2) {
        return Payments.FIELDS.entrySet()
                .stream()
                .map(entry -> compareField("Payments", "", p1, p2, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Gets the diffs from the model elements present on ModelData
     *
     * @param modelData the model data to operate on
     * @param <T> the type of the model
     * @param <E> the type of the model key
     * @return the list of diff results
     */
    private <T, E> List<Result> modelDiff(ModelData<T, E> modelData) {
        checkNotNull(modelData.models1);
        checkNotNull(modelData.models2);

        if(modelData.models1.isEmpty() && modelData.models2.isEmpty()) {
            return Result.success().asList();
        }

        List<Result> sizeResult = new ArrayList<>();
        if(modelData.models1.size() != modelData.models2.size()) {
            sizeResult = Result.failure(String.format("%ss size mismatch [%s != %s]", modelData.modelName, modelData.models1.size(), modelData.models2.size())).asList();
        }

        List<Result> p1diffs = checkModels(modelData, true);
        List<Result> p2diffs = checkModels(modelData, false);

        sizeResult.addAll(p1diffs);
        sizeResult.addAll(p2diffs);

        return sizeResult;
    }


    /**
     * Checks that the models on the first collection exist on the
     * second collection. If checkFields and a match is found, then
     * all the fields will be compared.
     *
     * @param sourceData the data to operate on
     * @param firstPass if this is the first or second pass
     * @param <T> the type of the model
     * @param <E> the type of the model's key
     * @return the list of results with the differences
     */
    private <T, E> List<Result> checkModels(ModelData<T, E> sourceData, boolean firstPass) {
        final ModelData<T, E> modelData = sourceData.prepareFor(firstPass);

        return modelData.models1.parallelStream()//.stream()
                .map(m1 -> {
                    E code = modelData.keyGetter.apply(m1);
                    Optional<T> m2 = findModel(modelData, code);
                    if(!m2.isPresent()) {
                        return Result.failure(String.format("%s '%s' not present on %s file", modelData.modelName, code, firstPass ? "second" : "first"));
                    }

                    if(firstPass) {
                        List<Result> fieldResults = modelData.modelMethods.entrySet()
                                .stream()
                                .map(entry -> compareField(modelData.modelName, code, m1, m2.get(), entry.getKey(), entry.getValue()))
                                .collect(Collectors.toList());

                        if(modelData.customDiffer.isPresent()) {
                            Result result = modelData.customDiffer.get().apply(m1, m2.get());
                            String reason = String.format("%s '%s' - %s", modelData.modelName, code, result.getReason());
                            fieldResults.add(result.setReason(reason));
                        }

                        return Result.fromResults(fieldResults);
                    }

                    return Result.success();

                }).collect(Collectors.toList());
    }

    /**
     * Finds a model by a key on a collection of models. It depends on the
     * models2Cache of ModelData, a map with key -> model. If the code to
     * be found is null, it's a specific scenario and it tried to find
     * a match with a null key (this happens in tests).
     *
     * @param modelData the models data to search on
     * @param code the key code to search for
     * @param <T> the model type
     * @param <E> the type of the key
     * @return an option model
     */
    private <T, E> Optional<T> findModel(ModelData modelData, E code) {
        if(code == null) {
            return modelData.models2.stream()
                    .filter(model -> modelData.keyGetter.apply(model) == null)
                    .findAny();
        }

        List<T> lucky = (List<T>) modelData.models2Cache.get(code);
        if(lucky == null || lucky.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(lucky.get(0));
    }

    /**
     * Compares that the same field of two objects have the same value.
     * Returns an explanatory result.
     *
     * @param modelName the name of the model being used
     * @param key the key of the object
     * @param t1 first object
     * @param t2 second object
     * @param methodName descriptive label
     * @param method the method to call
     * @param <T> The parameter of the objects' Type
     * @return the result of the operation
     */
    private <T> Result compareField(String modelName, Object key, T t1, T t2, String methodName, Function<T, Object> method) {
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

        if(v1 instanceof String && v2 instanceof String) {
            // Special care for Strings, because they can have weird
            // unicode chars that are not easy to compare
            v1 = CharMatcher.javaLetterOrDigit().retainFrom((String) v1);
            v2 = CharMatcher.javaLetterOrDigit().retainFrom((String) v2);
        }

        if(!v1.equals(v2)) {
            return Result.failure(String.format("%s '%s': %s mismatch ['%s' != '%s']", modelName, key == null ? "" : key, methodName, v1, v2));
        }

        return Result.success();
    }

}
