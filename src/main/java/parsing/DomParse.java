package parsing;


import classes.*;
import classes.Package;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public  class DomParse extends AbstractParseBuilder {

    @Override
    public void buildSetMedicines(String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            document.getDocumentElement().normalize();
            NodeList medicinList = document.getElementsByTagName("medicin");
            medicines = new ArrayList<>();

            for (int i = 0; i < medicinList.getLength(); i++) {
                Element medicinElement = (Element) medicinList.item(i);
                Medicin medicine = new Medicin();
                medicine.setId(medicinElement.getAttribute("id"));
                medicine.setName(medicinElement.getAttribute("name"));
                medicine.setGroup(medicinElement.getAttribute("group"));

                NodeList analogList = medicinElement.getElementsByTagName("analog");
                for (int j = 0; j < analogList.getLength(); j++) {
                    Element analogElement = (Element) analogList.item(j);
                    String analogName = analogElement.getAttribute("name");
                    medicine.addAnalog(new Analog(analogName));
                }

                NodeList versionList = medicinElement.getElementsByTagName("version");
                for (int j = 0; j < versionList.getLength(); j++) {
                    Element versionElement = (Element) versionList.item(j);
                    String versionType = versionElement.getAttribute("type");
                    Version version = new Version(versionType);

                    NodeList manufactureList = versionElement.getElementsByTagName("manufacture");
                    for (int k = 0; k < manufactureList.getLength(); k++) {
                        Element manufactureElement = (Element) manufactureList.item(k);
                        String manufactureName = manufactureElement.getAttribute("name");

                        // Парсинг сертификата
                        Element certificateElement = (Element) manufactureElement.getElementsByTagName("certificate").item(0);
                        String certificateNumber = certificateElement.getAttribute("number");
                        LocalDate dateCreate = LocalDate.parse(certificateElement.getAttribute("dateCreate"));
                        LocalDate dateExpiration = LocalDate.parse(certificateElement.getAttribute("dateExpiration"));
                        String registrationCompany = certificateElement.getAttribute("registrationCompany");
                        Certificate certificate = new Certificate(certificateNumber, dateCreate, dateExpiration, registrationCompany);

                        Element packageElement = (Element) manufactureElement.getElementsByTagName("package").item(0);
                        String packageType = packageElement.getAttribute("type");
                        int packageWeight = Integer.parseInt(packageElement.getAttribute("weight"));
                        double packagePrice = Double.parseDouble(packageElement.getAttribute("price"));
                        Package medicinPackage = new Package(packageType, packageWeight, packagePrice); // Изменено на MedicinePackage

                        String dosage = manufactureElement.getElementsByTagName("dosage").item(0).getTextContent();

                        Manufacture manufacture = new Manufacture(manufactureName, certificate, medicinPackage, dosage);
                        version.addManufacture(manufacture);
                    }

                    medicine.addVersion(version);
                }

                medicines.add(medicine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}