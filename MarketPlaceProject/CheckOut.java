import java.io.*;
import java.util.ArrayList;

import static Project.Project4.Login.main.customers;

/**
 *project 4 CheckOut
 *
 *this class check out the item from the user's shopping cart and clean it while delete it from the seller then export it to cvs file
 */

public class CheckOut extends main {
    private ArrayList<Product> updatedShoppingCart;

    public void exportToCSV(ArrayList<Product> shoppingCart, String s) throws IOException {

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
            for (Customer customer : customers) {
                if (customer.getCustomerUserName().equals(username)) {
                    customer.setShoppingCar(new ArrayList<>());
                }
            }
            System.out.println("Export succeeded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
