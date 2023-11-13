import java.io.*;
import java.util.ArrayList;

public class CheckOut extends main {
    private ArrayList<Product> updatedShoppingCart;

    public void exportToCSV(ArrayList<Product> shoppingCart, String s) throws IOException {
        ArrayList<Product> updatedShoppingCart = null;
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equalsIgnoreCase(username)) {
                updatedShoppingCart = customer.getShoppingCar();
                customer.setShoppingCar(null);
                Store storeToUpdate = null;

                for (Product product : updateShoppingCart) {
                    for (Seller seller : sellers) {
                        ArrayList<Store> stores = seller.getStores();
                        for (Store store : stores) {
                            ArrayList<Product> products = store.getProducts();
                            for (Product productFromSeller : products) {
                                if (product.getName().equals(productFromSeller.getName())) {
                                    storeToUpdate = store;
                                    storeToUpdate.editProduct(product.getName(), 4, (""+ (productFromSeller.getQuantAvailable()-product.getQuantAvailable())));
                                    store = storeToUpdate;
                                }
                            }
                        }
                        seller.setStores(stores);
                    }
                }
            }
        }



        try (FileWriter csvWriter = new FileWriter(s)) {
            // write header
            csvWriter.append("Name,Description,Price\n");

            //data
            for (Product product : shoppingCart) {
                csvWriter.append(product.getName());
                csvWriter.append(",");
                csvWriter.append(product.getDescription());
                csvWriter.append(",");
                csvWriter.append(String.valueOf(product.getPrice()));
                csvWriter.append("\n");
            }
            //clear shopping cart
            shoppingCart.clear();
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
