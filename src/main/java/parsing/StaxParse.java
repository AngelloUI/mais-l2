package parsing;

import classes.*;
import classes.Package;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

public class StaxParse extends AbstractParseBuilder {

    @Override
    public void buildSetMedicines(String fileName) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(fileName));
            medicines = parseMedicines(reader);
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Medicin> parseMedicines(XMLStreamReader reader) throws XMLStreamException {
        ArrayList<Medicin> medicines = new ArrayList<>();
        Medicin currentMedicine = null;
        Version currentVersion = null;
        Manufacture currentManufacture = null;
        Certificate currentCertificate = null;
        Package currentPackage = null;
        String currentElement = null;

        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    currentElement = reader.getLocalName();
                    switch (currentElement) {
                        case "medicin":
                            currentMedicine = new Medicin();
                            currentMedicine.setId(reader.getAttributeValue(null, "id"));
                            currentMedicine.setName(reader.getAttributeValue(null, "name"));
                            currentMedicine.setGroup(reader.getAttributeValue(null, "group"));
                            break;

                        case "analog":
                            if (currentMedicine != null) {
                                currentMedicine.addAnalog(new Analog(reader.getAttributeValue(null, "name")));
                            }
                            break;

                        case "version":
                            currentVersion = new Version(reader.getAttributeValue(null, "type"));
                            break;

                        case "manufacture":
                            currentManufacture = new Manufacture(
                                    reader.getAttributeValue(null, "name"),
                                    null, null, null);
                            break;

                        case "certificate":
                            currentCertificate = new Certificate(
                                    reader.getAttributeValue(null, "number"),
                                    LocalDate.parse(reader.getAttributeValue(null, "dateCreate")),
                                    LocalDate.parse(reader.getAttributeValue(null, "dateExpiration")),
                                    reader.getAttributeValue(null, "registrationCompany")
                            );
                            break;

                        case "package":
                            currentPackage = new Package(
                                    reader.getAttributeValue(null, "type"),
                                    Integer.parseInt(reader.getAttributeValue(null, "weight")),
                                    Double.parseDouble(reader.getAttributeValue(null, "price"))
                            );
                            break;
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    String content = reader.getText().trim();
                    if ("dosage".equals(currentElement) && currentManufacture != null) {
                        currentManufacture.setDosage(content);
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    currentElement = reader.getLocalName();
                    switch (currentElement) {
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
                                if (currentCertificate != null) {
                                    currentManufacture.setCertificate(currentCertificate);
                                }
                                if (currentPackage != null) {
                                    currentManufacture.setMedicinPackage(currentPackage);
                                }
                                currentVersion.addManufacture(currentManufacture);
                            }
                            currentManufacture = null;
                            currentCertificate = null;
                            currentPackage = null;
                            break;
                    }
                    break;
            }
        }

        return medicines;
    }
}
