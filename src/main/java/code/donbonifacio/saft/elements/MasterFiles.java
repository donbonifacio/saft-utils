package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the MasterFiles XML element of the SAF-T file.
 */
public class MasterFiles {

    @XmlElements({
            @XmlElement(name = "Product",     type = Product.class)
    })
    private List<Object> masterFilesElements;

    private List<Product> products;

    /**
     * Gets the products defined on this MasterFiles
     *
     * @return a list of products
     */
    public List<Product> getProducts() {
        if(products == null) {
            products = masterFilesElements.stream()
                    .filter(obj -> Product.class.equals(obj.getClass()))
                    .map(obj-> (Product) obj)
                    .collect(Collectors.toList());
        }
        return products;
    }
}
