package code.donbonifacio.saft.elements;

import com.google.common.collect.ImmutableMap;

import javax.xml.bind.annotation.XmlElement;
import java.util.Map;
import java.util.function.Function;

import static code.donbonifacio.saft.Util.compose;

/**
  * This class represents a `Header` xml element from the standard
 * SAF-T file.
 * The class is immutable.
 */
public final class Header {

    // maps a friendly name to a Header field getter
    public static final Map<String, Function<Header, Object>> FIELDS =
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

    @XmlElement(name="AuditFileVersion")
    private String auditFileVersion;

    @XmlElement(name="CompanyID")
    private String companyId;

    @XmlElement(name="TaxRegistrationNumber")
    private String taxRegistrationNumber;

    @XmlElement(name="TaxAccountingBasis")
    private String taxAccountingBasis;

    @XmlElement(name="CompanyName")
    private String companyName;

    @XmlElement(name="FiscalYear")
    private int fiscalYear;

    @XmlElement(name="CompanyAddress")
    private Address companyAddress;

    @XmlElement(name="StartDate")
    private String startDate;

    @XmlElement(name="EndDate")
    private String endDate;

    @XmlElement(name="CurrencyCode")
    private String currencyCode;

    @XmlElement(name="DateCreated")
    private String dateCreated;

    @XmlElement(name="TaxEntity")
    private String taxEntity;

    @XmlElement(name="ProductCompanyTaxID")
    private String productCompanyTaxId;

    @XmlElement(name="SoftwareCertificateNumber")
    private String softwareCertificateNumber;

    @XmlElement(name="ProductID")
    private String productId;

    @XmlElement(name="ProductVersion")
    private String productVersion;

    /**
     * Gets the AuditFileVersion
     * @return the audit file version
     */
    public String getAuditFileVersion() {
        return auditFileVersion;
    }

    /**
     * Gets the CompanyID
     * @return the company id
     */
    public String getCompanyID() {
        return companyId;
    }

    /**
     * Gets the FiscalYear
     * @return the fiscal year
     */
    public int getFiscalYear() {
        return fiscalYear;
    }

    /**
     * Gets the TaxAccountingBasis
     * @return the tax accounting basis
     */
    public String getTaxAccountingBasis() {
        return taxAccountingBasis;
    }

    /**
     * Gets the TaxRegistrationNumber
     * @return the tax registration number
     */
    public String getTaxRegistrationNumber() {
        return taxRegistrationNumber;
    }

    /**
     * Gets the CompanyName
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Gets the CompanyAddress
     * @return the company address
     */
    public Address getCompanyAddress() {
        if(companyAddress == null) {
            return new Address();
        }
        return companyAddress;
    }

    /**
     * Gets the StartDate
     * @return the start date
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Gets the EndDate
     * @return the end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Gets the CurrencyCode
     * @return the currency code
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Gets the DateCreated
     * @return the date created
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Gets the TaxEntity
     * @return
     */
    public String getTaxEntity() {
        return taxEntity;
    }

    /**
     * Gets the ProductCompanyTaxID
     * @return the product company tax id
     */
    public String getProductCompanyTaxId() {
        return productCompanyTaxId;
    }

    /**
     * Gets the SoftwareCertificateNumber
     * @return the software certificate number
     */
    public String getSoftwareCertificateNumber() {
        return softwareCertificateNumber;
    }

    /**
     * Gets the ProductID
     * @return the product id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Gets the ProductVersion
     * @return the product version
     */
    public String getProductVersion() {
        return productVersion;
    }
}
