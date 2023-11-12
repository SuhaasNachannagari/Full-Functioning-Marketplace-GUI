import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;
import java.lang.*;
import java.io.*;


public class Sort extends main {
    private ArrayList<Seller> sortedSellers = getSellers();

    private ArrayList<Customer> sortedCustomers = getCustomers();

    ArrayList<Product> quantityListedProducts;
    ArrayList<Product> priceListedProducts;

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
            System.out.printf("%d. Store: %s, Name: %s, Price: %.2f\n",
                    i,
                    product.getStoreName(),
                    product.getName(),
                    product.getPrice());
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
    public boolean quantityShowProduct(int num) {
        Scanner scanner = new Scanner(System.in);
        if (num < 1 || num > quantityListedProducts.size()) {
            return false;
        }
        Product product = quantityListedProducts.get(num - 1);
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

    public void quantityAddToShoppingCart(String username, int quantity, int num) {
        Product productFromSeller = quantityListedProducts.get(num - 1);
        for (Customer customer : sortedCustomers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedShoppingCart = customer.getShoppingCar();
                if (quantity > productFromSeller.getLimit() ){
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
                    updatedShoppingCart.add(productToAdd);
                    customer.setShoppingCar(updatedShoppingCart);
                }

            }
        }
    }

    public void quantityPurchaseItems(String username, int quantity, int num) {
        Product productFromSeller = quantityListedProducts.get(num - 1);
        for (Customer customer : sortedCustomers) {
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
    public void priceAddToShoppingCart(String username, int quantity, int num) {
        Product productFromSeller = priceListedProducts.get(num - 1);
        for (Customer customer : sortedCustomers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedShoppingCart = customer.getShoppingCar();
                if (quantity > productFromSeller.getLimit() ){
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
                    updatedShoppingCart.add(productToAdd);
                    customer.setShoppingCar(updatedShoppingCart);
                }

            }
        }
    }

    public void pricePurchaseItem(String username, int quantity, int num) {
        Product productFromSeller = priceListedProducts.get(num - 1);
        for (Customer customer : sortedCustomers) {
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
        Product productToReview = quantityListedProducts.get(num - 1);
        ArrayList<String> updatedReviews = productToReview.getReviews();
        updatedReviews.add(review);
        productToReview.setReviews(updatedReviews);
    }




}





