public class Product {
    private String name;
    private String storeName;
    private String description;
    private int quantAvailable;
    private double price;

    public Product(String name, String storeName, String description, int quantAvailable, double price) {
        this.name = name;
        this.storeName = storeName;
        this.description = description;
        this.quantAvailable = quantAvailable;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantAvailable() {
        return quantAvailable;
    }

    public void setQuantAvailable(int quantAvailable) {
        this.quantAvailable = quantAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
