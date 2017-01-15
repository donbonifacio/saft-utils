package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;

/**
  * This class represents a `Header` xml element from the standard
 * SAF-T file.
 * The class is immutable.
 */
public final class Header {

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
