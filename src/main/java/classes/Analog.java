package classes;

public class Analog {
    private String name;

    public Analog(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Analog{" +
                "name='" + name + '\'' +
                '}';
    }
}