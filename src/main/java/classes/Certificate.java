package classes;
import java.time.LocalDate;

public class Certificate {
    private String number;
    private LocalDate dateCreate;
    private LocalDate dateExpiration;
    private String registrationCompany;

    public Certificate(String number, LocalDate dateCreate, LocalDate dateExpiration, String registrationCompany) {
        this.number = number;
        this.dateCreate = dateCreate;
        this.dateExpiration = dateExpiration;
        this.registrationCompany = registrationCompany;

        System.out.println(this.number);
        System.out.println(this.dateCreate);
        System.out.println(this.dateExpiration);
        System.out.println(this.registrationCompany);
    }

    // Геттеры
    public String getNumber() {
        return number;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public String getRegistrationCompany() {
        return registrationCompany;
    }
    @Override
    public String toString() {
        return "Certificate{" +
                "number='" + number + '\'' +
                ", dateCreate=" + dateCreate +
                ", dateExpiration=" + dateExpiration +
                ", registrationCompany='" + registrationCompany + '\'' +
                '}';
    }
}
