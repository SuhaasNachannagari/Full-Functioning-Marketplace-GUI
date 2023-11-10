import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Customer {
    String customerUserName;
    ArrayList<Product> shoppingCart;

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

    public void addShoppingCar(Product product) {

        Scanner scan = new Scanner (System.in);
        System.out.println("Enter the product you want to buy:");
        String input = scan.nextLine();

        
        shoppingCart.add(product);
        //multiple
    }

    public void deleteShoppingCar(Product product) {
        shoppingCart.remove(product);
        //multiple
    }

    public void deleteMultipleItemsShoppingCar() {
        //Get user input for products to remove
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter products you want to remove(use comma to separate");
        String input = scanner.nextLine();
        String[] productsToRemove = input.split(",");

        //Use Iterator to remove products specified by the user
        Iterator<Product> iterator = shoppingCart.iterator();
        while (iterator.hasNext()) {
            Product items = iterator.next();
            for (String products : productsToRemove) {
                if (items.equals(products.trim())) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public void buyShoppingCar () {
        // export arraylist into txt
        //clean the arraylist
    }
}
