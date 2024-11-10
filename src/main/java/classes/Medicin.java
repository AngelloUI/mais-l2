package classes;
import java.util.ArrayList;
import java.util.List;


public class Medicin {
    private String id;
    private String name;
    private String group;
    private List<Analog> analogs;
    private List<Version> versions;

    public Medicin(String id, String name, String group) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.analogs = new ArrayList<>();
        this.versions = new ArrayList<>();
    }
    public Medicin() {
        this.analogs = new ArrayList<>();
        this.versions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    
    public List<Analog> getAnalogs() {
        return analogs;
    }

    public void addAnalog(Analog analog) {
        this.analogs.add(analog);
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void addVersion(Version version) {
        this.versions.add(version);
    }
    @Override
    public String toString() {
        String analogsString = "";
        for (Analog analog : analogs) {
            analogsString += analog.getName() + " ";
        }

        String versionsString = "";
        for (Version version : versions) {
            versionsString += version.toString() + " ";
        }

        return "Medicin{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", analogs=[" + analogsString.trim() + "]" +
                ", versions=[" + versionsString.trim() + "]" +
                "}";
    }
}
