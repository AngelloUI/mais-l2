package parsing;

import java.util.List;
import classes.Medicin;

public abstract class AbstractParseBuilder {
    protected List<Medicin> medicines;

    public abstract void buildSetMedicines(String fileName);

    public List<Medicin> getMedicines() {
        return medicines;
    }
}
