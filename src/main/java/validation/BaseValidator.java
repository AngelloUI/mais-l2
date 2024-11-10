package validation;

import java.io.*;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseValidator {
    private static Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        String fileName = "C:\\Users\\Дмитрий\\OneDrive\\Рабочий стол\\lab2\\medicins.xml";
        String schemaName = "C:\\Users\\Дмитрий\\OneDrive\\Рабочий стол\\lab2\\schema.xsd";
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(schemaName);

        try {
            // schema creation
            Schema schema = factory.newSchema(schemaLocation);
            // creating a schema-based validator
            Validator validator = schema.newValidator();
            // Use File object to create Source
            File xmlFile = new File(fileName);
            Source source = new StreamSource(xmlFile);

            // document check
            validator.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException e) {
                    logger.warn(e.getLineNumber() + " : " + e.getColumnNumber() + " - " + e.getMessage());
                }

                @Override
                public void error(SAXParseException e) throws SAXException {
                    logger.error(e.getLineNumber() + " : " + e.getColumnNumber() + " - " + e.getMessage());
                    System.err.println("Error at line " + e.getLineNumber() + ", column " + e.getColumnNumber() + ": " + e.getMessage());
                }

                @Override
                public void fatalError(SAXParseException e) throws SAXException {
                    logger.fatal(e.getLineNumber() + " : " + e.getColumnNumber() + " - " + e.getMessage());
                    System.err.println("Fatal error at line " + e.getLineNumber() + ", column " + e.getColumnNumber() + ": " + e.getMessage());
                }
            });
            validator.validate(source);
        } catch (SAXException | IOException e) {
            System.err.println(fileName + " is not correct or valid: " + e.getMessage());
        }
    }
}
