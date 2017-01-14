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
}
