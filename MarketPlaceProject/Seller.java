import java.io.*;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Project 4 - Seller
 *
 * This class represents a single seller.
 */

public class Seller implements Serializable {
    private String userName;
    ArrayList<Store> stores = new ArrayList();
    
    //tri: used to create new Seller in main class
    public Seller(ArrayList<Store> stores, String username) {
        this.stores = stores;
        this.userName = username;
    }
    //
    public String getUserName() { return this.userName; }
    
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

    public void saveToFileProduct(Product product) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SellerProductDetails.txt"))) {
            String toFile = product.getName() + "," + product.getStoreName() + "," + product.getDescription() + "," +
                    product.getQuantAvailable() + "," + product.getPrice();
            writer.write(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> loadFromFileProduct(String fileName) {
        ArrayList<String> productData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)) ){
            String line;
            while ((line = reader.readLine()) != null) {
                productData.add(line);
            }
            if(productData == null || productData.isEmpty()) {
                System.out.println("No data in file.");
            } else {
                System.out.println("File imported success");
            }
            return productData;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //System.out.println("User data loaded from " + "filler.txt");
    }
}
