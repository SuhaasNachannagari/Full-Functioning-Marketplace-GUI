import java.io.*;
import java.util.ArrayList;

public class Seller extends User implements Serializable {

    ArrayList<Store> stores = new ArrayList();
    public Seller(String username, String password, ArrayList<Store> stores) {
        super(username, password);
        this.stores = stores;
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

    public void saveToFileProduct(Product product) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SellerProductDetails.txt"))) {
            String toFile = product.getName() + "," + product.getStoreName() + "," + product.getDescription() + "," +
                    product.getQuantAvailable() + "," + product.getPrice();
            writer.write(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> loadFromFileProduct() {
        ArrayList<String> productData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("SellerProductDetails.txt")) ){
            String line;
            while ((line = reader.readLine()) != null) {
                productData.add(line);
            }
            return productData;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //System.out.println("User data loaded from " + "filler.txt");
    }
}
