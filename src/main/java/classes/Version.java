package classes;
import java.util.ArrayList;
import java.util.List;

public class Version {
    private String type;
    private List<Manufacture> manufactures;

    public Version(String type) {
        this.type = type;
        this.manufactures = new ArrayList<>();
    }
    public Version() {
        this("таблетки");
    }

    // Геттеры и сеттеры
    public String getType() {
        return type;
    }

    public List<Manufacture> getManufactures() {
        return manufactures;
    }

    public void addManufacture(Manufacture manufacture) {
        this.manufactures.add(manufacture);
    }
    @Override
    public String toString() {
        return "Version{" +
                "type='" + type + '\'' +
                ", manufactures=" + manufactures +
                '}';
    }
}