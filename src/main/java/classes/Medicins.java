package classes;
import java.util.ArrayList;
import java.util.List;

public class Medicins {
    private List<Medicin> medicins;

    public Medicins() {
        this.medicins = new ArrayList<>();
    }

    public List<Medicin> getMedicins() {
        return medicins;
    }

    public void setMedicins(List<Medicin> medicins) {
        this.medicins = medicins;
    }
}
