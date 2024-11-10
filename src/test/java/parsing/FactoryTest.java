package parsing;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class FactoryTest {

    @Test
    public void testCreateDomParser() {
        AbstractParseBuilder builder = Factory.createMedicineBuilder("dom");
        assertNotNull(builder, "DOM parser should not be null");
        assertTrue(builder instanceof DomParse, "Expected instance of DomParse");
    }

    @Test
    public void testCreateStaxParser() {
        AbstractParseBuilder builder = Factory.createMedicineBuilder("stax");
        assertNotNull(builder, "STAX parser should not be null");
        assertTrue(builder instanceof StaxParse, "Expected instance of StaxParse");
    }

    @Test
    public void testCreateSaxParser() {
        AbstractParseBuilder builder = Factory.createMedicineBuilder("sax");
        assertNotNull(builder, "SAX parser should not be null");
        assertTrue(builder instanceof SaxParse, "Expected instance of SaxParse");
    }
}
