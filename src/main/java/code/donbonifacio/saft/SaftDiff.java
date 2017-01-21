package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.elements.Customer;
import code.donbonifacio.saft.elements.Header;
import code.donbonifacio.saft.elements.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
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
        this.file1 = file1;
        this.file2 = file2;
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
        final Function<T, E> keyGetter;

        /**
         * Creates a new ModelData
         *
         * @param models1 the first batch of data
         * @param models2 the second batch of data
         * @param modelMethods the methods that compare two instances
         * @param keyGetter the method that gets the key value
         */
        ModelData(String modelName, List<T> models1, List<T> models2, Map<String, Function<T, Object>> modelMethods, Function<T, E> keyGetter) {
            this.modelName = modelName;
            this.models1 = models1;
            this.models2 = models2;
            this.modelMethods = modelMethods;
            this.keyGetter = keyGetter;
        }

        /**
         * Usually the operations are relative from models1 applied to
         * models2. This method switches that order.
         *
         * @return a new ModelData with collections switched
         */
        private ModelData<T,E> switchModels() {
            return new ModelData<>(modelName, models2, models1, modelMethods, keyGetter);
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
                Product::getProductCode
        );
        results.addAll(modelDiff(productsData));

        logger.trace("Testing customers...");
        ModelData<Customer, String> customersData = new ModelData<>(
                "Customer",
                file1.getMasterFiles().getCustomers(),
                file2.getMasterFiles().getCustomers(),
                Customer.FIELDS,
                Customer::getCustomerId
        );
        results.addAll(modelDiff(customersData));

        return Result.fromResults(results);
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

        if(modelData.models1.size() != modelData.models2.size()) {
            return Result.failure(String.format("%ss size mismatch [%s != %s]", modelData.modelName, modelData.models1.size(), modelData.models2.size())).asList();
        }

        List<Result> p1diffs = checkModels(modelData, true);
        List<Result> p2diffs = checkModels(modelData, false);

        p1diffs.addAll(p2diffs);

        return p1diffs;
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

        return modelData.models1.stream()
                .map(m1 -> {
                    E code = modelData.keyGetter.apply(m1);
                    Optional<T> m2 = findModel(modelData.models2, modelData.keyGetter, code);
                    if(!m2.isPresent()) {
                        return Result.failure(String.format("%s '%s' not present on %s file", modelData.modelName, code, firstPass ? "second" : "first"));
                    }

                    if(firstPass) {
                        List<Result> fieldResults = modelData.modelMethods.entrySet()
                                .stream()
                                .map(entry -> compareField(modelData.modelName, code, m1, m2.get(), entry.getKey(), entry.getValue()))
                                .collect(Collectors.toList());

                        return Result.fromResults(fieldResults);
                    }

                    logger.trace("{} '{}' OK", modelData.modelName, code);

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

        if(!v1.equals(v2)) {
            return Result.failure(String.format("%s '%s': %s mismatch ['%s' != '%s']", modelName, key == null ? "" : key, methodName, v1, v2));
        }

        return Result.success();
    }

}
