package code.donbonifacio.saft.elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents an `AuditFile` xml element on the standard
 * SAF-T file.
 * The class is immutable.
 */
@XmlRootElement(name="AuditFile", namespace="urn:OECD:StandardAuditFile-Tax:PT_1.03_01")
public final class AuditFile {

    @XmlElement(name="Header")
    private Header header;

    /**
     * Gets the Header information
     * @return Header
     */
    public Header getHeader() {
        if(header == null) {
            return new Header();
        }
        return header;
    }
}
