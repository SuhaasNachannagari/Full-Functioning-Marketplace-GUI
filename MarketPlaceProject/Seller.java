import java.io.*;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Project 5 - Seller
 *
 * This class represents a single seller.
 */

public class Seller {
    private String userName;
    ArrayList<Store> stores = new ArrayList();
    //tri: used to create new Seller in main class
    public Seller(ArrayList<Store> stores, String username) {
        this.stores = stores;
        this.userName = username;
    }
    //
    public String getUserName() { return this.userName; }
    public void setUserName(String name) {
        this.userName = name;
    }
    public ArrayList<Store> getStores() {
        return this.stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public void createStore(Store store) {
        this.stores.add(store);
    }

    public void view() {
        System.out.println("List of stores:");
        for(int i = 0; i < stores.size(); i++) {
            System.out.println(stores.get(i));
            System.out.println("Products\tQuantity available");
            for(int j = 0; j < stores.get(i).getProducts().size(); j++) {
                stores.get(i).listProducts();
            }
            System.out.println("Revenue of store: " + stores.get(i).getSales());
        }
    }

    public void saveToFileProductSeller(Product product, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            BufferedReader bfr = new BufferedReader(new BufferedReader(new FileReader(fileName)));
            String firstLine = bfr.readLine();
            if (firstLine == null
                    || !firstLine.equals("product_name,store_name,description,quantity_available,price")) {
                writer.write("product_name,store_name,description,quantity_available,price\n");
            }
            String toFile = product.getName() + "," + product.getStoreName() + "," + product.getDescription() + "," +
                    product.getQuantAvailable() + "," + product.getPrice();
            writer.write(toFile + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
