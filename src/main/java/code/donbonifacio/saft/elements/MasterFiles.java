package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableList;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;
import java.util.Optional;

/**
 * Represents the MasterFiles XML element of the SAF-T file.
 */
public final class MasterFiles {

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
     * Empty ctor. Builds the fields with empty data.
     */
    public MasterFiles() {
        products = ImmutableList.of();
        customers = ImmutableList.of();
        taxTable = new TaxTable();
    }

    /**
     * Gets the products defined on this MasterFiles
     *
     * @return a list of products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Gets the customers defined on this MasterFiles
     *
     * @return a list of customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Gets the TaxTable
     *
     * @return the tax table
     */
    public TaxTable getTaxTable() {
        return taxTable;
    }

    /**
     * Given a specific Class, gathers all the elements from that class
     * that are on the master files, and returns an ImmutableList with them.
     *
     * @param klass the class to search for
     * @param <T> the type of the class
     * @return an immutable list
     */
    private <T> ImmutableList<T> gather(Class klass) {
        return masterFilesElements
                .stream()
                .filter(obj -> klass.equals(obj.getClass()))
                .map(obj -> (T) obj)
                .collect(ImmutableList.toImmutableList());
    }

    /**
     * Separates the data on the master files in specific collections
     * per type. Called by the deserializer when the object load is complete
     *
     * @param um the Unmarshaller
     * @param parent the parent object
     */
    // Used by the XML unmarshaller
    @SuppressWarnings("squid:UnusedPrivateMethod")
    private void afterUnmarshal(Unmarshaller um, Object parent) {
        if(masterFilesElements == null) {
            masterFilesElements = ImmutableList.of();
        }

        products = gather(Product.class);
        customers = gather(Customer.class);

        final Optional<TaxTable> taxes = masterFilesElements
                .stream()
                .filter(obj -> TaxTable.class.equals(obj.getClass()))
                .map(obj-> (TaxTable) obj)
                .findAny();

        if(taxes.isPresent()) {
            taxTable = taxes.get();
        } else if(taxTable == null) {
            taxTable = new TaxTable();
        }

        // we don't need this anymore
        this.masterFilesElements = null;
    }

}
