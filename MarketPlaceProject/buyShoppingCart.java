import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class buyShoppingCart extends Customer {
    public buyShoppingCart(ArrayList<Product> shoppingCart, String customerUserName) {
        super(shoppingCart, customerUserName);
        this.shoppingCart = shoppingCart;
    }

    public void generateTxtAndCleanArray() {

        try {
            writeToFileAndClearList(shoppingCart, "Purchase History.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFileAndClearList(ArrayList<Product> shoppingCart, String s) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(s))) {
            for (Product item : shoppingCart) {
                writer.write(String.valueOf(item));
                writer.newLine();
            }
        }
        shoppingCart.clear();
    }
}