    import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Project 5 - Customer
 *
 * This class represents a customer.
  * @author a
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @version December 11th, 2023
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

    public void deleteShoppingCart() {

        Scanner scan = new Scanner(System.in);
        boolean keepRemoving = true;

        while (keepRemoving) {
            keepRemoving = false;
            int indexProductTemp = 0;
            boolean checkFormat1;

            System.out.println("Here are the items in your shopping cart:");
            int ind = 0;
            for (Product prod : shoppingCart) {
                ind++;
                System.out.print(String.format("%d. Name: %s\t\t", ind, prod.getName()));
            }
            System.out.println();
            do {
                checkFormat1 = true;
                try {
                    System.out.println("Enter the index of the product you want to remove: ");
                    indexProductTemp = scan.nextInt();
                    scan.nextLine();
                } catch (InputMismatchException e) {
                    checkFormat1 = false;
                    System.out.println("Please enter the right format!");
                }
                if (indexProductTemp <= 0 || indexProductTemp > shoppingCart.size()) {
                    checkFormat1 = false;
                    System.out.println("Please enter the right format!");
                }
            } while (!checkFormat1);

            int indexProduct = indexProductTemp - 1;
            shoppingCart.remove(indexProduct);
            System.out.println("Item removed!");

            boolean checkFormat2;
            String ans = null;
            do {
                checkFormat2 =true;
                try {
                    System.out.println("Do you want to keep moving? 1. Yes      2. No");
                    ans = scan.nextLine();
                } catch (InputMismatchException e) {
                    checkFormat2 = false;
                    System.out.println("Please enter the right format!");
                }
                if (!(ans.equals("1") || ans.equals("2"))) {
                    checkFormat2 = false;
                    System.out.println("Please enter the right format!");
                }
            } while (!checkFormat2);

            if (ans.equalsIgnoreCase("1")) {
                keepRemoving = true;
            }
        }
    }

    public void viewShoppingCart() {
        for (int i = 0; i < shoppingCart.size(); i++) {
            System.out.printf("Name: %s" + "  Store Name: %s" + "  Price: %.2f" + "  Quantity Added: %d" +
                            "  Description: %s\n", shoppingCart.get(i).getName(), shoppingCart.get(i).getStoreName(),
                    shoppingCart.get(i).getPrice(), shoppingCart.get(i).getQuantAvailable(),
                    shoppingCart.get(i).getDescription());
            System.out.println("Review: ");
            int ind = 1;
            for (int j = 0; j < shoppingCart.get(i).getReviews().size(); j++) {
                System.out.println(ind + ") \"" +shoppingCart.get(i).getReviews().get(j)+ "\"");
                ind++;
            }
            System.out.println();
        }
    }

    public void viewPurchasedHistory() {
        for (int i = 0; i < purchaseHistory.size(); i++) {
            System.out.printf("Name: %s" + "  Store Name: %s" + "  Price: %.2f" + "  Quantity Purchased: %d" +
                            "  Description: %s\n", purchaseHistory.get(i).getName(),
                    purchaseHistory.get(i).getStoreName(),
                    purchaseHistory.get(i).getPrice(), purchaseHistory.get(i).getQuantAvailable(),
                    purchaseHistory.get(i).getDescription());
            System.out.print("Review: ");
            for (int j = 0; j < purchaseHistory.get(i).getReviews().size(); j++) {
                System.out.print(purchaseHistory.get(i).getReviews().get(j)+ "\t\t");
            }
            System.out.println();
        }
    }
}
