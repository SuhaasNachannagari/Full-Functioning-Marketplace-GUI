import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PurchaseCart extends main {
    ArrayList<Product> updatedShoppingCart = new ArrayList<>();

    public void showProducts() {
        Scanner scanner = new Scanner(System.in);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equalsIgnoreCase(username)) {
                updatedShoppingCart = customer.getShoppingCar();
            }
        }

        System.out.println("Here are your available items: ");
        int i = 1;
        for (Product product: updatedShoppingCart) {
            System.out.printf("%d. Store: %s, Name: %s, Price: %.2f\n", i, product.getStoreName(), product.getName(),
                    product.getPrice());
            i++;
        }

        boolean checkFormat;
        int itemNum = 0;
        do {
            checkFormat = true;
            try {
                System.out.println("Which product number would you like to look at?");
                itemNum = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                checkFormat = false;
                System.out.println("Please enter the correct format!");
            }
            if (itemNum < 1 || itemNum > updatedShoppingCart.size()) {
                checkFormat = false;
                System.out.println("Please enter the correct format!");
            }
        } while (!checkFormat);

        purchaseItem(itemNum);
    }
    public void purchaseItem( int num ) {
        Scanner scanner = new Scanner(System.in);
        Product productFromCustomer = updatedShoppingCart.get(num - 1);
        for (Customer customer: customers) {
            if (customer.getCustomerUserName().equals(username)) {
                Store storeToUpdate;

                for (Seller seller : sellers) {
                    ArrayList<Store> stores = seller.getStores();
                    for (Store store : stores) {
                        ArrayList<Product> products = store.getProducts();
                        for (Product product : products) { //consider using storeName to compare when finding a correct product.
                            if (product.getName().equals(productFromCustomer.getName()) &&
                                    product.getStoreName().equals(productFromCustomer.getStoreName())) {
                                storeToUpdate = store;

                                if ( product.getQuantAvailable() - productFromCustomer.getQuantAvailable() < 0) {
                                    boolean checkFormat;
                                    int choiceNum = 0;
                                    do {
                                        checkFormat = true;
                                        System.out.print("Because the store only has " +
                                                productFromCustomer.getQuantAvailable() + " item(s) left. ");
                                        System.out.println("We can not afford the number of item you order, which is" +
                                                product.getQuantAvailable());
                                        System.out.println("Do you want to remove this item or adjust the quantity of " +
                                                "your order?    1. Adjust       2. Remove");
                                        try {
                                            System.out.println("Which product number would you like to look at?");
                                            choiceNum = scanner.nextInt();
                                            scanner.nextLine();
                                        } catch (InputMismatchException e) {
                                            checkFormat = false;
                                            System.out.println("Please enter the correct format!");
                                        }
                                        if (choiceNum != 1 || choiceNum != 2) {
                                            checkFormat = false;
                                            System.out.println("Please enter the correct format!");
                                        }
                                    } while (!checkFormat);


                                    if (choiceNum == 1) {
                                        int newQuant = 0;
                                        boolean checkFormat3;
                                        do {
                                            checkFormat3 = true;
                                            try {
                                                System.out.println("Enter the new quantity:");
                                                newQuant = scanner.nextInt();
                                                scanner.nextLine();
                                            } catch (InputMismatchException e) {
                                                checkFormat3 = false;
                                                System.out.println("Please enter the correct format!");
                                            }
                                            if (newQuant > product.getQuantAvailable()) {
                                                checkFormat3 = false;
                                                System.out.println("This number is bigger than the existing quantity");
                                            }
                                        } while (!checkFormat3);

                                        ArrayList<Product> updatedShoppingCart1 = customer.getShoppingCar();
                                        updatedShoppingCart1.get(num - 1).setQuantAvailable(newQuant);
                                        customer.setShoppingCar(updatedShoppingCart1);

                                    } else if (choiceNum == 2 ) {
                                        updatedShoppingCart.remove(num - 1);
                                        customer.setShoppingCar(updatedShoppingCart);
                                    }
                                } else {
                                    storeToUpdate.editProduct(product.getName(), 4, ("" + (product.getQuantAvailable() - productFromCustomer.getQuantAvailable())));
                                    updatedShoppingCart.remove(num - 1);
                                    customer.setShoppingCar(updatedShoppingCart);
                                }

                                store = storeToUpdate;
                            }
                        }
                    }
                    seller.setStores(stores);
                }
            }
        }
    }

    public boolean purchaseItemALL() {
        ArrayList<Product> updatedShoppingCart;

        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equalsIgnoreCase(username)) {
                updatedShoppingCart = customer.getShoppingCar();
                customer.setShoppingCar(new ArrayList<>());
                Store storeToUpdate;

                int index = 1;
                for (Product product : updatedShoppingCart) {
                    for (Seller seller : sellers) {
                        ArrayList<Store> stores = seller.getStores();
                        for (Store store : stores) {
                            ArrayList<Product> products = store.getProducts();
                            for (Product productFromSeller : products) {
                                if (product.getName().equals(productFromSeller.getName()) &&
                                        product.getStoreName().equals(productFromSeller.getStoreName())) {
                                    storeToUpdate = store;

                                    if (productFromSeller.getQuantAvailable() - product.getQuantAvailable() < 0 ) {
                                        System.out.println("Not enough stocks for item number " + index);
                                        System.out.println("Have only " + productFromSeller.getQuantAvailable() +
                                                " left, but your order requires " + product.getQuantAvailable());
                                        System.out.println("Please modify your cart in 'Buy one item' of Shopping Cart");
                                        return false;
                                    } else {
                                        storeToUpdate.editProduct(product.getName(), 4, ("" + (productFromSeller.getQuantAvailable() - product.getQuantAvailable())));
                                        store = storeToUpdate;
                                    }
                                }
                                index++;
                            }
                        }
                        seller.setStores(stores);
                    }
                }
                customer.setShoppingCar(new ArrayList<>());
            }
        }
        return true;
    }

}
