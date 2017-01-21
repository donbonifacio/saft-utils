package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Map;
import java.util.function.Function;

import static code.donbonifacio.saft.Util.compose;

/**
 * This class represents a `Customer` xml element on the standard
 * SAF-T file.
 *
 * This class is immutable.
 */
@XmlType(name="Customer")
public final class Customer {

    // maps a friendly name to a Customer field getter
    public static final Map<String, Function<Customer, Object>> FIELDS =
            ImmutableMap.<String, Function<Customer, Object>>builder()
                    .put("MasterFiles.Customer.AccountID", Customer::getAccountId)
                    .put("MasterFiles.Customer.CustomerTaxID", Customer::getCustomerTaxId)
                    .put("MasterFiles.Customer.CompanyName", Customer::getCompanyName)
                    .put("MasterFiles.Customer.BillingAddress.AddressDetail", compose(Customer::getBillingAddress, Address::getAddressDetail))
                    .put("MasterFiles.Customer.BillingAddress.City", compose(Customer::getBillingAddress, Address::getCity))
                    .put("MasterFiles.Customer.BillingAddress.PostalCode", compose(Customer::getBillingAddress, Address::getPostalCode))
                    .put("MasterFiles.Customer.BillingAddress.Country", compose(Customer::getBillingAddress, Address::getCountry))
                    .put("MasterFiles.Customer.Telephone", Customer::getTelephone)
                    .put("MasterFiles.Customer.Email", Customer::getEmail)
                    .put("MasterFiles.Customer.Website", Customer::getWebsite)
                    .put("MasterFiles.Customer.SelfBillingIndicator", Customer::getSelfBillingIndicator)
                    .build();

    @XmlElement(name="CustomerID")
    private String customerId;

    @XmlElement(name="AccountID")
    private String accountId;

    @XmlElement(name="CustomerTaxID")
    private String customerTaxId;

    @XmlElement(name="CompanyName")
    private String companyName;

    @XmlElement(name="BillingAddress")
    private Address billingAddress;

    @XmlElement(name="Telephone")
    private String telephone;

    @XmlElement(name="Fax")
    private String fax;

    @XmlElement(name="Email")
    private String email;

    @XmlElement(name="Website")
    private String website;

    @XmlElement(name="SelfBillingIndicator")
    private int selfBillingIndicator;

    /**
     * Gets the CustomerID
     *
     * @return the customer id
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Gets the AccountID
     *
     * @return the account id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Gets the CustomerTaxID
     *
     * @return the customer tax id
     */
    public String getCustomerTaxId() {
        return customerTaxId;
    }

    /**
     * Gets the CompanyName
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Gets the BillingAddress
     *
     * @return the billing address
     */
    public Address getBillingAddress() {
        if(billingAddress == null) {
            billingAddress = new Address();
        }
        return billingAddress;
    }

    /**
     * Gets the Telephone
     *
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Gets the Fax
     *
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Gets the email
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the Website
     *
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Gets the SelfBillingIndicator
     *
     * @return the self billing indicator
     */
    public int getSelfBillingIndicator() {
        return selfBillingIndicator;
    }
}
