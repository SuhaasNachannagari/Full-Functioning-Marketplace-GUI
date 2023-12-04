import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;
import java.lang.*;
import java.io.*;
/**
 * A Class that extends main in order to display and enact the code that runs whenever
 * the customer chooses to "search" in the main method. Includes methods that sort the products by quantity or price,
 * display the products, and allow the user to write a review, buy the product, or add it to their
 * shopping cart
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @version November 13, 2023
 */

public class Sort extends main {
    private ArrayList<Seller> sortedSellers = getSellers();

    private ArrayList<Customer> sortedCustomers = getCustomers();

    ArrayList<Product> quantityListedProducts = new ArrayList<>();
    ArrayList<Product> priceListedProducts = new ArrayList<>();

    public void sortByQuantity() {
        for (Seller seller : sortedSellers) {
            ArrayList<Store> stores = seller.getStores();
            for (Store store : stores) {
                ArrayList<Product> products = store.getProducts();
                for (Product product : products) {
                    quantityListedProducts.add(product);
                }
            }
        }
        for (int i = 1; i < quantityListedProducts.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (quantityListedProducts.get(i).getQuantAvailable() <
                        quantityListedProducts.get(j).getQuantAvailable()) {
                    Product smallerproduct = quantityListedProducts.get(i);
                    quantityListedProducts.set(i, quantityListedProducts.get(j));
                    quantityListedProducts.set(j, smallerproduct);
                }
            }
        }

        System.out.println("Here are your available items sorted by quantity (lowest to highest)");
        int i = 1;
        for (Product product : quantityListedProducts) {
            System.out.printf("%d. Store: %s, Name: %s, Quantity: %d\n",
                    i,
                    product.getStoreName(),
                    product.getName(),
                    product.getQuantAvailable());
            i++;
        }
    }

    public void sortByPrice() {
        for (Seller seller : sortedSellers) {
            ArrayList<Store> stores = seller.getStores();
            for (Store store : stores) {
                ArrayList<Product> products = store.getProducts();
                for (Product product : products) {
                    priceListedProducts.add(product);
                }
            }
        }
        for (int i = 1; i < priceListedProducts.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (priceListedProducts.get(i).getPrice() < priceListedProducts.get(j).getPrice()) {
                    Product smallerproduct = priceListedProducts.get(i);
                    priceListedProducts.set(i, priceListedProducts.get(j));
                    priceListedProducts.set(j, smallerproduct);
                }
            }
        }

        System.out.println("Here are your available items sorted by price (lowest to highest)");
        int i = 1;
        for (Product product : priceListedProducts) {
            System.out.printf("%d. Store: %s, Name: %s, Price: %.2f\n",
                    i,
                    product.getStoreName(),
                    product.getName(),
                    product.getPrice());
            i++;
        }
    }

    public boolean priceShowProduct(int num) {
        Scanner scanner = new Scanner(System.in);
        if (num < 1 || num > priceListedProducts.size()) {
            return false;
        }
        Product product = priceListedProducts.get(num - 1);
        System.out.printf("Name: %s\n" + "  Store Name: %s\n" + "  Price: %.2f\n" +
                        "  Quantity Available: %d\n" + "  Description: %s\n",
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
                j++;
            }
        }
        return true;
    }

    public boolean quantityShowProduct(int num) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(quantityListedProducts.size() + " checkSizeShowProduct");
        if (num < 1 || num > quantityListedProducts.size()) {
            return false;
        }
        Product product = quantityListedProducts.get(num - 1);
        System.out.printf("Name: %s\n" + "  Store Name: %s\n" + "  Price: %.2f\n" +
                        "  Quantity Available: %d\n" + "  Description: %s\n",
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
                j++;
            }
        }
        return true;
    }

    public void quantityAddToShoppingCart(String username, int quantity, int num) {
        Product productFromSeller = quantityListedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedShoppingCart = customer.getShoppingCar();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    System.out.println("You are attempting to add more than the limit of "
                            + productFromSeller.getLimit()
                            + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    System.out.println("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to add above that limit");
                } else {
                    Product productToAdd = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity, productFromSeller.getPrice());

                    productToAdd.setLimit(productFromSeller.getLimit());
                    productToAdd.setReviews(productFromSeller.getReviews());
                    if (updatedShoppingCart.get(0).getName().equals("N/A")) {
                        updatedShoppingCart.set(0, productToAdd);
                    } else {
                        updatedShoppingCart.add(productToAdd);
                    }
                    customer.setShoppingCar(updatedShoppingCart);
                    System.out.println("Item added!");
                }
            }
        }
    }

    public void quantityPurchaseItems(String username, int quantity, int num) {
        Product productFromSeller = quantityListedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedPurchaseHistory = customer.getPurchaseHistory();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    System.out.println("You are attempting to buy more than the limit of "
                            + productFromSeller.getLimit() + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    System.out.println("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to buy above that limit");
                } else {
                    Product productToBuy = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity,
                            productFromSeller.getPrice());
                    productToBuy.setLimit(productFromSeller.getLimit());
                    productToBuy.setReviews(productFromSeller.getReviews());
                    if (updatedPurchaseHistory.get(0).getName().equals("N/A")) {
                        updatedPurchaseHistory.set(0, productToBuy);
                    } else {
                        updatedPurchaseHistory.add(productToBuy);
                    }
                    customer.setPurchaseHistory(updatedPurchaseHistory);
                    Store storeToUpdate = null;
                    for (Seller seller : sellers) {
                        ArrayList<Store> stores = seller.getStores();
                        for (Store store : stores) {
                            ArrayList<Product> products = store.getProducts();
                            for (Product product : products) {
                                if (product.getName().equals(productToBuy.getName())) {
                                    storeToUpdate = store;
                                    storeToUpdate.editProduct(product.getName(), 4,
                                            ("" + (productFromSeller.getQuantAvailable() - quantity)));
                                    store = storeToUpdate;
                                }
                            }
                        }
                        seller.setStores(stores);
                    }
                    System.out.println("Item purchased");
                }
            }
        }
    }

    public void priceAddToShoppingCart(String username, int quantity, int num) {
        Product productFromSeller = priceListedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedShoppingCart = customer.getShoppingCar();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    System.out.println("You are attempting to add more than the limit of "
                            + productFromSeller.getLimit()
                            + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    System.out.println("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to add above that limit");
                } else {
                    Product productToAdd = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity, productFromSeller.getPrice());

                    productToAdd.setLimit(productFromSeller.getLimit());
                    productToAdd.setReviews(productFromSeller.getReviews());
                    if (updatedShoppingCart.get(0).getName().equals("N/A")) {
                        updatedShoppingCart.set(0, productToAdd);
                    } else {
                        updatedShoppingCart.add(productToAdd);
                    }
                    customer.setShoppingCar(updatedShoppingCart);
                    System.out.println("Item added!");
                }
            }
        }
    }

    public void pricePurchaseItem(String username, int quantity, int num) {
        Product productFromSeller = priceListedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedPurchaseHistory = customer.getPurchaseHistory();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    System.out.println("You are attempting to buy more than the limit of "
                            + productFromSeller.getLimit() + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    System.out.println("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to buy above that limit");
                } else {
                    Product productToBuy = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity,
                            productFromSeller.getPrice());
                    productToBuy.setLimit(productFromSeller.getLimit());
                    productToBuy.setReviews(productFromSeller.getReviews());
                    if (updatedPurchaseHistory.get(0).getName().equals("N/A")) {
                        updatedPurchaseHistory.set(0, productToBuy);
                    } else {
                        updatedPurchaseHistory.add(productToBuy);
                    }
                    customer.setPurchaseHistory(updatedPurchaseHistory);
                    Store storeToUpdate = null;
                    for (Seller seller : sellers) {
                        ArrayList<Store> stores = seller.getStores();
                        for (Store store : stores) {
                            ArrayList<Product> products = store.getProducts();
                            for (Product product : products) {
                                if (product.getName().equals(productToBuy.getName())) {
                                    storeToUpdate = store;
                                    storeToUpdate.editProduct(product.getName(), 4,
                                            ("" + (productFromSeller.getQuantAvailable() - quantity)));
                                    store = storeToUpdate;
                                }
                            }
                        }
                        seller.setStores(stores);
                    }
                    System.out.println("Item purchased");
                }
            }
        }
    }

    public void addReviewPrice(String review, int num) {
        Product productToReview = priceListedProducts.get(num - 1);
        ArrayList<String> updatedReviews = productToReview.getReviews();
        updatedReviews.add(review);
        productToReview.setReviews(updatedReviews);

        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                for (Product product : store.getProducts()) {
                    if (product.getName().equals(productToReview.getName()) &&
                            product.getStoreName().equals(productToReview.getStoreName())) {
                        product.setReviews(productToReview.getReviews());
                    }
                }
            }
        }
    }

    public void addReviewQuantity( String review, int num) {

        Product productToReview = quantityListedProducts.get(num - 1);
        ArrayList<String> updatedReviews = productToReview.getReviews();
        updatedReviews.add(review);
        productToReview.setReviews(updatedReviews);

        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                for ( Product product : store.getProducts()) {
                    if (product.getName().equals(productToReview.getName()) &&
                            product.getStoreName().equals(productToReview.getStoreName())) {
                        product.setReviews(productToReview.getReviews());
                    }
                }
            }
        }
    }


}







