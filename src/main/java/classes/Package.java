package classes;

public class Package {
    private String type;
    private int weight;
    private double price;

    public Package(String type, int weight, double price) {
        this.type = type;
        this.weight = weight;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }
    public String toString() {
        return "Package{" +
                "type='" + type + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }

}
