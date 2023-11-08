import java.util.ArrayList;

public class Customer {
    String customerUserName;
    ArrayList<Product> shoppingCar;

    public Customer(ArrayList<Product> shoppingCar, String customerUserName) {
        this.customerUserName = customerUserName;
        this.shoppingCar = shoppingCar;
    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public ArrayList<Product> getShoppingCar() {
        return shoppingCar;
    }

    public void setShoppingCar(ArrayList<Product> shoppingCar) {
        this.shoppingCar = shoppingCar;
    }

    public void addShoppingCar(Product product) {
        shoppingCar.add(product);
        //multiple
    }

    public void deleteShoppingCar(Product product) {
        shoppingCar.remove(product);
        //multiple
    }

    public void buyShoppingCar () {
        // export arraylist into txt
        //clean the arraylist
    }
}
