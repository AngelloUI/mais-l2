package parsing;

import classes.*;
import classes.Package;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class SaxParse extends AbstractParseBuilder {
    @Override
    public void buildSetMedicines(String fileName) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MedicineHandler handler = new MedicineHandler();
            saxParser.parse(new File(fileName), handler);
            medicines = handler.getMedicines();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class MedicineHandler extends DefaultHandler {
        private ArrayList<Medicin> medicines;
        private Medicin currentMedicine;
        private Version currentVersion;
        private Manufacture currentManufacture;
        private Certificate currentCertificate;
        private Package currentPackage;
        private String currentElement;

        public ArrayList<Medicin> getMedicines() {
            return medicines;
        }

        @Override
        public void startDocument() throws SAXException {
            medicines = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentElement = qName;

            switch (qName) {
                case "medicin":
                    currentMedicine = new Medicin();
                    currentMedicine.setId(attributes.getValue("id"));
                    currentMedicine.setName(attributes.getValue("name"));
                    currentMedicine.setGroup(attributes.getValue("group"));
                    break;
                case "analog":
                    if (currentMedicine != null) {
                        currentMedicine.addAnalog(new Analog(attributes.getValue("name")));
                    }
                    break;
                case "version":
                    currentVersion = new Version(attributes.getValue("type"));
                    break;
                case "manufacture":
                    currentManufacture = new Manufacture(attributes.getValue("name"), null, null, null);
                    break;
                case "certificate":
                    currentCertificate = new Certificate(
                            attributes.getValue("number"),
                            LocalDate.parse(attributes.getValue("dateCreate")),
                            LocalDate.parse(attributes.getValue("dateExpiration")),
                            attributes.getValue("registrationCompany")
                    );
                    break;
                case "package":
                    currentPackage = new Package(
                            attributes.getValue("type"),
                            Integer.parseInt(attributes.getValue("weight")),
                            Double.parseDouble(attributes.getValue("price"))
                    );
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
                case "medicin":
                    if (currentMedicine != null) {
                        medicines.add(currentMedicine);
                    }
                    currentMedicine = null;
                    break;

                case "version":
                    if (currentMedicine != null && currentVersion != null) {
                        currentMedicine.addVersion(currentVersion);
                    }
                    currentVersion = null;
                    break;

                case "manufacture":
                    if (currentVersion != null && currentManufacture != null) {
                        // Устанавливаем сертификат и упаковку в manufacture до завершения элемента
                        if (currentCertificate != null) {
                            currentManufacture.setCertificate(currentCertificate);
                        }
                        if (currentPackage != null) {
                            currentManufacture.setMedicinPackage(currentPackage);
                        }

                        // Отладка для проверки, что certificate и package установлены
                        System.out.println("Добавлен Manufacture: " + currentManufacture);

                        currentVersion.addManufacture(currentManufacture);  // Добавляем в версию
                    }
                    // Обнуляем объекты после добавления
                    currentManufacture = null;
                    currentCertificate = null;
                    currentPackage = null;
                    break;

                case "certificate":
                    // Здесь ничего не делаем, так как certificate уже добавлен в manufacture
                    break;

                case "package":
                    // Здесь ничего не делаем, так как package уже добавлен в manufacture
                    break;
            }
            currentElement = null;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String content = new String(ch, start, length).trim();
            if (!content.isEmpty()) {
                if ("dosage".equals(currentElement) && currentManufacture != null) {
                    currentManufacture.setDosage(content);
                }
            }
        }
    }
}
