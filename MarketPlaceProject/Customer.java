import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 4 - Customer
 *
 * This class represents a customer.
 */

public class Customer {
    String customerUserName;
    ArrayList<Product> shoppingCart;
    
    ArrayList<Product> purchaseHistory;

    public Customer(ArrayList<Product> shoppingCart, String customerUserName) {
        this.customerUserName = customerUserName;
        this.shoppingCart = shoppingCart;
    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public ArrayList<Product> getShoppingCar() {
        return shoppingCart;
    }

    public void setShoppingCar(ArrayList<Product> shoppingCar) {
        this.shoppingCart = shoppingCar;
    }

    public ArrayList<Product> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(ArrayList<Product> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public void addShoppingCar(Product product) {

        Scanner scan = new Scanner (System.in);
        boolean keepShopping = true;

        while (keepShopping) {
            keepShopping = false;
            System.out.println("Enter the product you want to buy:");
            String input = scan.nextLine();
            for (Product items : shoppingCart) {
                if (items.getName().equalsIgnoreCase(input)) {
                    shoppingCart.add(items);
                } else {
                    System.out.println("The product you enter does not exist please try again");
                }
            }
            System.out.println("Do you want to keep shopping?");
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                keepShopping = true;
            }
        }
    }

    public void deleteShoppingCar(Product product) {

        Scanner scan = new Scanner (System.in);
        boolean keepRemoving = true;

        while (keepRemoving) {
            keepRemoving = false;
            System.out.println("Enter the product you want to remove:");
            String input = scan.nextLine();

            for (Product items : shoppingCart) {
                if (items.getName().equalsIgnoreCase(input)) {
                    shoppingCart.remove((items));
                } else {
                    System.out.println("The product you enter does not exist please try again");
                }
                System.out.println("Do you want to keep removing?");
                String answer = scan.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    keepRemoving = true;
                }
            }
        }
    }
}
