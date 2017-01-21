package code.donbonifacio.saft;

import code.donbonifacio.saft.elements.AuditFile;
import code.donbonifacio.saft.exceptions.SaftLoaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Loads and AuditFile from specific sources
 */
public final class SaftLoader {
    static final Logger logger = LoggerFactory.getLogger(SaftLoader.class);

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
        logger.trace("Loading AuditFile from {}", fileName);

        try {
            FileInputStream fis = new FileInputStream(fileName);
            AuditFile auditFile = loadFrom(fis);
            fis.close();
            return auditFile;
        } catch(IOException ex) {
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
        logger.trace("Loading AuditFile from raw String");
        InputStream stream = new ByteArrayInputStream(raw.getBytes(StandardCharsets.UTF_8));
        return loadFrom(stream);
    }

    /**
     * Loads an AuditFile from an arbitrary stream
     * @param stream the source stream
     * @return the AuditFile
     * @throws SaftLoaderException
     */
    public static AuditFile loadFrom(InputStream stream) throws SaftLoaderException {
        checkNotNull(stream);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AuditFile.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (AuditFile) jaxbUnmarshaller.unmarshal(stream);
        } catch(Exception ex) {
            throw new SaftLoaderException(ex);
        }

    }
}
