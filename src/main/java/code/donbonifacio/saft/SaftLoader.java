package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.exceptions.SaftLoaderException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Loads and AuditFile from specific sources
 */
public final class SaftLoader {

    /**
     * Prevent creating objects of this class
     */
    private SaftLoader() {
        throw new AssertionError();
    }

    /**
     * Loads an AuditFile from a given fileName
     * @param fileName the file name to load from
     * @return the audit file
     * @throws SaftLoaderException
     */
    public static AuditFile loadFromFile(String fileName) throws SaftLoaderException {
        checkNotNull(fileName);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AuditFile.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (AuditFile) jaxbUnmarshaller.unmarshal(new File(fileName));
        } catch(Exception ex) {
            throw new SaftLoaderException(ex);
        }
    }

    /**
     * Loads an AuditFile from a raw string
     * @param raw the raw xml string
     * @return the AuditFile
     * @throws SaftLoaderException
     */
    public static AuditFile loadFromString(String raw) throws SaftLoaderException {
        checkNotNull(raw);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AuditFile.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(raw);
            return (AuditFile) jaxbUnmarshaller.unmarshal(reader);
        } catch(Exception ex) {
            throw new SaftLoaderException(ex);
        }
    }
}
