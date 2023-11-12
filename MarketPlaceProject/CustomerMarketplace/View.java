import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;
import java.lang.*;
import java.io.*;


public class View extends main {
    private ArrayList<Seller> listedSellers = getSellers();

    private ArrayList<Customer> listedCustomers = getCustomers();

    ArrayList<Product> listedProducts;

    public void createList() {
        for (Seller seller : listedSellers) {
            ArrayList<Store> stores = seller.getStores();
            for (Store store : stores) {
                ArrayList<Product> products = store.getProducts();
                for (Product product : products) {
                    listedProducts.add(product);
                }
            }
        }
    }
    public void listProducts() {
        System.out.println("Here are your available items: ");
        int i = 1;
        for (Product product : listedProducts) {
            System.out.printf("%d. Store: %s, Name: %s, Price: %.2f\n",
                    i,
                    product.getStoreName(),
                    product.getName(),
                    product.getPrice());
            i++;
        }
    }
    public boolean showProduct(int num) {
        Scanner scanner = new Scanner(System.in);
        if (num < 1 || num > listedProducts.size()) {
            return false;
        }
        Product product = listedProducts.get(num - 1);
        System.out.printf("Name: %s\n" + "  Store Name: %s\n" + "  Price: %.2f\n" +
                        "  Quantity Available: %d\n" + "Description: %s\n",
                product.getName(),
                product.getStoreName(),
                product.getPrice(),
                product.getQuantAvailable(),
                product.getDescription());
        System.out.println("Would you like to see the reviews? 1 - Yes,  2 - No");
        int reviewInput = scanner.nextInt();
        if (reviewInput == 1) {
            ArrayList<String> reviews = product.getReviews();
            int j = 1;
            System.out.println("Reviews: ");
            for (String review : reviews) {
                System.out.println(j + ") \"" + review + "\"");
            }
        }
        return true;
    }


    public void addToShoppingCart(String username, int quantity, int num) {
        Product productFromSeller = listedProducts.get(num - 1);
        for (Customer customer : listedCustomers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedShoppingCart = customer.getShoppingCar();
                if (quantity > productFromSeller.getLimit() ){
                    System.out.println("You are attempting to add more than the limit of "
                            + productFromSeller.getLimit() + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    System.out.println("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to add above that limit");
                } else {
                    Product productToAdd = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity,
                            productFromSeller.getPrice());
                    updatedShoppingCart.add(productToAdd);
                    customer.setShoppingCar(updatedShoppingCart);
                }

            }
        }
    }
    public void purchaseItem(String username, int quantity, int num) {
        Product productFromSeller = listedProducts.get(num - 1);
        for (Customer customer : listedCustomers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedPurchaseHistory = customer.getPurchaseHistory();
                if (quantity > productFromSeller.getLimit() ){
                    System.out.println("You are attempting to add more than the limit of "
                            + productFromSeller.getLimit() + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    System.out.println("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to add above that limit");
                } else {
                    Product productToBuy = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity,
                            productFromSeller.getPrice());
                    updatedPurchaseHistory.add(productToBuy);
                    customer.setPurchaseHistory(updatedPurchaseHistory);
                    productFromSeller.setQuantAvailable(productFromSeller.getQuantAvailable()-quantity);
                    if (productFromSeller.getQuantAvailable()-quantity == 0) {
                        System.out.println("You have bought ");
                    }
                }

            }
        }
    }
    public void addReview(String review, int num) {
        Product productToReview = listedProducts.get(num - 1);
        ArrayList<String> updatedReviews = productToReview.getReviews();
        updatedReviews.add(review);
        productToReview.setReviews(updatedReviews);

    }




}





