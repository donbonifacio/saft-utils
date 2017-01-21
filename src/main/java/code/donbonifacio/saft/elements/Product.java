package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Map;
import java.util.function.Function;

/**
 * This class represents a `Product` xml element on the standard
 * SAF-T file.
 *
 * The class is immutable.
 */
@XmlType(name="Product")
public final class Product {

    // maps a friendly name to a Product field getter
    public static final Map<String, Function<Product, Object>> FIELDS =
            ImmutableMap.<String, Function<Product, Object>>builder()
                    //.put("MasterFiles.Product.ProductCode", Product::getProductCode)
                    .put("MasterFiles.Product.ProductType", Product::getProductType)
                    .put("MasterFiles.Product.ProductDescription", Product::getProductDescription)
                    .put("MasterFiles.Product.ProductNumberCode", Product::getProductNumberCode)
                    .build();

    @XmlElement(name="ProductCode")
    private String productCode;

    @XmlElement(name="ProductType")
    private String productType;

    @XmlElement(name="ProductDescription")
    private String productDescription;

    @XmlElement(name="ProductNumberCode")
    private String productNumberCode;

    /**
     * Gets the ProductCode
     *
     * @return the product code
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Gets the ProductType
     *
     * @return the product type
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Gets the ProductDescription
     *
     * @return the product description
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Gets the ProductNumberCode
     *
     * @return the product number code
     */
    public String getProductNumberCode() {
        return productNumberCode;
    }

}
