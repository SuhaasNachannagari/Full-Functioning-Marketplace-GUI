import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 4 - Store
 * <p>
 * This class represents a single store.
 */

// Tri_Vo
// Field: Name of the store and an ArrayList of Products in that store
// Method: get, set field value; Add product to store; Edit a product in store; Delete a product in store
//
// Note: for Add, Edit, and Delete, they will return a boolean value (see method's explanation for more info)

public class Store {

    String name;
    double sales;
    ArrayList<Product> products;

    public Store( ArrayList<Product> products, String name) {
        this.products = products;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    public double getSales() {
        return sales;
    }
    public void setSales(double sales) {
        this.sales = sales;
    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public void listProducts() {
        System.out.println("The available products for the store are:");

        for(int i = 0; i < this.products.size(); i++) {
            System.out.println(this.products.get(i) + "\t" + this.products.get(i).getPrice());
        }
    }
    // edit Product: name - name of Product, input - category wants to be edited, val - value to be changed to
    // the method returns a boolean value to check if the method runs into any error, then we could tell user to do again or not.
    public boolean editProduct(String name, int input, String val) {

        boolean checkItemExist = false;
        for (Product item: products) {
            if (item.getName().equals(name)) {
                checkItemExist = true;
                switch (input) {
                    case (1):
                        item.setName(val);
                        break;
                    // case(2) is in the Edit class, last method
                    case (3):
                        item.setDescription(val);
                        break;
                    case (4):
                        int quantAvailable = Integer.parseInt(val);
                        item.setQuantAvailable(quantAvailable);
                        if (quantAvailable == 0) {
                            System.out.println("You have bought the entire stock");
                        }
                        break;
                    case (5):
                        System.out.println("check44");
                        double price = Double.parseDouble(val);
                        item.setPrice(price);
                        break;
                    case (6):
                        int limit = Integer.parseInt(val);
                        item.setLimit(limit);
                        break;
                    default:
                        System.out.println("Please enter the correct number!");
                        break;
                }
            }
        }
        if (!checkItemExist) { System.out.println("Please enter the correct name"); }
        return checkItemExist;
    }

    // delete product: name - name of product want to be deleted
    // the method returns a boolean value to check if the method runs into any error, then we could tell user to do again or not.
    public void deleteProduct(int i) {
        products.remove(i);
    }

}
