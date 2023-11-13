import java.util.*;

public class Product implements Serializable { // most basic class, everything is built off of this
    private String name;
    private String storeName;
    private String description;
    private int quantAvailable;
    private double price;
    private ArrayList<String> reviews;
    
    private int limit;

    public Product(String name, String storeName, String description, int quantAvailable, double price) {
        this.name = name;
        this.storeName = storeName;
        this.description = description;
        this.quantAvailable = quantAvailable;
        this.price = price;
        this.reviews = null;
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

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
