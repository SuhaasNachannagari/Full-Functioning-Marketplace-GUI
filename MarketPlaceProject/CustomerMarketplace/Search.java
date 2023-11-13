import java.util.*;
import java.lang.*;
/**
 * A Class that extends main in order to display and enact the code that runs whenever 
 * the customer chooses to "search" in the main method 
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @version November 13, 2023
 */

public class Search extends main {
    private ArrayList<Seller> listedSellers = getSellers();

    private ArrayList<Customer> listedCustomers = getCustomers();

    ArrayList<Product> listedProducts;

    public boolean searchProducts(String search) {
        ArrayList<Product> matchingProducts = null;
        for (Seller seller : listedSellers) {
            ArrayList<Store> stores = seller.getStores();
            for (Store store : stores) {
                ArrayList<Product> products = store.getProducts();
                for (Product product : products) {
                    listedProducts.add(product);
                }
            }
        }

        for (Product product : listedProducts) {
            if (product.getName().contains(search) || product.getDescription().contains(search) || product.getStoreName().contains(search)) {
                matchingProducts.add(product);
            }
        }

        if (matchingProducts == null) {
            System.out.println("There are no products that match your search:");
            return false;
        } else {
            System.out.println("Here are the products that match your search:");
            int i = 1;
            for (Product product : matchingProducts) {
                System.out.printf("%d. Store: %s, Name: %s, Price:%.2f\n", i, product.getStoreName(), product.getName(), product.getPrice());
                i++;
            }
        }
        return true;
    }

    public boolean showProduct(int num) {
        Scanner scanner = new Scanner(System.in);
        if (num < 1 || num > listedProducts.size()) {
            return false;
        }
        Product product = listedProducts.get(num - 1);
        System.out.printf("Name: %s\n" + "  Store Name: %s\n" + "  Price: %.2f\n" + "  Quantity Available: %d\n" + "  Description: %s\n", product.getName(), product.getStoreName(), product.getPrice(), product.getQuantAvailable(), product.getDescription());
        System.out.println("Would you like to see the reviews? 1 - Yes,  2 - No");
        int reviewInput = scanner.nextInt();
        if (reviewInput == 1) {
            ArrayList<String> reviews = product.getReviews();
            int j = 1;
            System.out.println("Reviews: ");
            for (String review : reviews) {
                System.out.println(j + ") \"" + review + "\"");
                j++;
            }
        }
        return true;
    }


    public void addToShoppingCart(String username, int quantity, int num) {
        Product productFromSeller = listedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedShoppingCart = customer.getShoppingCar();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    System.out.println("You are attempting to add more than the limit of " + productFromSeller.getLimit() + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    System.out.println("There is only " + productFromSeller.getQuantAvailable() + " units left, you are attempting to add above that limit");
                } else {
                    Product productToAdd = new Product(productFromSeller.getName(), productFromSeller.getStoreName(), productFromSeller.getDescription(), quantity, productFromSeller.getPrice());
                    updatedShoppingCart.add(productToAdd);
                    customer.setShoppingCar(updatedShoppingCart);
                }

            }
        }
    }

    public void purchaseItem(String username, int quantity, int num) {
        Product productFromSeller = listedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedPurchaseHistory = customer.getPurchaseHistory();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    System.out.println("You are attempting to buy more than the limit of " + productFromSeller.getLimit() + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    System.out.println("There is only " + productFromSeller.getQuantAvailable() + " units left, you are attempting to buy above that limit");
                } else {
                    Product productToBuy = new Product(productFromSeller.getName(), productFromSeller.getStoreName(), productFromSeller.getDescription(), quantity, productFromSeller.getPrice());
                    updatedPurchaseHistory.add(productToBuy);
                    customer.setPurchaseHistory(updatedPurchaseHistory);
                    productFromSeller.setQuantAvailable(productFromSeller.getQuantAvailable() - quantity);
                    Store storeToUpdate = null;
                    for (Seller seller : sellers) {
                        ArrayList<Store> stores = seller.getStores();
                        for (Store store : stores) {
                            ArrayList<Product> products = store.getProducts();
                            for (Product product : products) {
                                if (product.getName().equals(productToBuy.getName())) {
                                    storeToUpdate = store;
                                    storeToUpdate.editProduct(product.getName(), 4, ("" + (productFromSeller.getQuantAvailable() - quantity)));
                                    store = storeToUpdate;
                                }
                            }
                        }
                        seller.setStores(stores);
                    }
                    if (productFromSeller.getQuantAvailable() - quantity == 0) {
                        System.out.println("You have bought the entire stock");
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
