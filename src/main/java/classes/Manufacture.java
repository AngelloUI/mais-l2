package classes;

public class Manufacture {
    private String name;
    private Certificate certificate;
    private Package medicinPackage;
    private String dosage;

    public Manufacture(String name, Certificate certificate, Package aPackage, String dosage) {
        this.name = name;
        this.certificate = certificate;
        this.medicinPackage = aPackage;
        this.dosage = dosage;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public Package getPackage() {
        return medicinPackage;
    }

    public String getDosage() {
        return dosage;
    }
    public String toString() {
        return "Manufacture{" +
                "name='" + name + '\'' +
                ", certificate=" + certificate +
                ", medicinPackage=" + medicinPackage +
                ", dosage='" + dosage + '\'' +
                '}';
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public void setMedicinPackage(Package medicinPackage) {
        this.medicinPackage = medicinPackage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
