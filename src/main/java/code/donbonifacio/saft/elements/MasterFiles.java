package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the MasterFiles XML element of the SAF-T file.
 */
public class MasterFiles {

    @XmlElements({
            @XmlElement(name = "Product",     type = Product.class),
            @XmlElement(name = "Customer",     type = Customer.class),
            @XmlElement(name = "TaxTable",     type = TaxTable.class)
    })
    private List<Object> masterFilesElements;

    private List<Product> products;
    private List<Customer> customers;
    private TaxTable taxTable;

    /**
     * Gets the products defined on this MasterFiles
     *
     * @return a list of products
     */
    public List<Product> getProducts() {
        if (products == null) {
            products = getMasterFilesElements()
                    .stream()
                    .filter(obj -> Product.class.equals(obj.getClass()))
                    .map(obj -> (Product) obj)
                    .collect(Collectors.toList());
        }
        return products;
    }

    /**
     * Gets the customers defined on this MasterFiles
     *
     * @return a list of customers
     */
    public List<Customer> getCustomers() {
        if(customers == null) {
            customers = masterFilesElements.stream()
                    .filter(obj -> Customer.class.equals(obj.getClass()))
                    .map(obj-> (Customer) obj)
                    .collect(Collectors.toList());
        }
        return customers;
    }

    /**
     * Gets the TaxTable
     *
     * @return the tax table
     */
    public TaxTable getTaxTable() {
        if(taxTable == null) {
            final List<TaxTable> taxes = masterFilesElements
                    .stream()
                    .filter(obj -> TaxTable.class.equals(obj.getClass()))
                    .map(obj-> (TaxTable) obj)
                    .collect(Collectors.toList());
            if(taxes.isEmpty()) {
                taxTable = new TaxTable();
            } else {
                taxTable = taxes.get(0);
            }
        }
        return taxTable;
    }

    /**
     * Gets the master files elements, creates an empty one of it's null
     *
     * @return the master files elements
     */
    private List<Object> getMasterFilesElements() {
        if(masterFilesElements == null) {
            masterFilesElements = new ArrayList<Object>(0);
        }
        return masterFilesElements;
    }
}
