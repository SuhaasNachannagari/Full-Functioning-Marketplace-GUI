import java.util.ArrayList;
import java.util.Scanner;
/**
 * Project 4 - Dashboard for Seller
 * <p>
 * This class will be used to display dashboard.
 */
public class Dashboard extends main {
    private Seller seller;
    private ArrayList<Customer> customers = getCustomers();

    public void setSeller(String userName) {
        for (int i = 0; i < sellers.size(); i++) {
            Seller sellerTemp = sellers.get(i);
            if (sellerTemp.getUserName().equals(userName)) {
                this.seller = sellers.get(i);
            }
            break;
        }
        getSelection();
    }

    public void getSelection() {
        Scanner scan = new Scanner(System.in);
        boolean checkFormat;
        do {
            checkFormat = true;
            try {
                System.out.println("1. List of customers    2. List of products");
                int indexSelection = scan.nextInt();
                scan.nextLine();
                if (indexSelection == 1) {
                    getListCustomers();
                }
                if (indexSelection == 2) {
                    getListProducts();
                }
                if (indexSelection != 1 && indexSelection != 2) {
                    checkFormat = false;
                    System.out.println("Please choose a correct number!");
                }
            } catch (NumberFormatException e) {
                checkFormat = false;
                System.out.println("Please enter a correct format!");
            }
        } while (!checkFormat);

    }
    public void getListCustomers() {
        ArrayList<String> sortedCustomerInfo = new ArrayList<>();
        ArrayList<Integer> sortedPurchasedNumber = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < seller.getStores().size(); i++) {
            String storeNameSeller = seller.getStores().get(i).getName(); //looks at every store that a seller owns
            for (int j = 0; j < customers.size(); j++) {
                int itemNumber = 0;
                ArrayList<Product> purchasedProduct = customers.get(j).getPurchaseHistory();
                for (int k = 0; k < purchasedProduct.size(); k++ ) {
                    // if in purchaseHistory, there is a product that comes from the desired store
                    if (purchasedProduct.get(k).getStoreName().equals(storeNameSeller)) {
                        itemNumber += purchasedProduct.get(k).getQuantAvailable(); // accumulate purchased number
                    }
                }
                index++;
                System.out.println(String.format("%d Store: %s, Customer: %s, purchased items: %d",
                            index, storeNameSeller, main.customers.get(i).getCustomerUserName(), itemNumber));
                // sortedCustomerInfo contains of storeName and customerName " Store: store1, Customer: tri "
                String result = String.format("Store: %s, Customer: %s ",
                        storeNameSeller, customers.get(j).getCustomerUserName());
                sortedCustomerInfo.add(result);
                sortedPurchasedNumber.add(itemNumber);
            }
        }
        sortCustomers(sortedCustomerInfo, sortedPurchasedNumber);
    }
    public void getListProducts() {
        int index = 0;
        ArrayList<String> productsInfo = new ArrayList<>();
        ArrayList<Integer> productsSales = new ArrayList<>();
        for (int i = 0; i < seller.getStores().size(); i++) {
            String storeNameSeller = seller.getStores().get(i).getName(); //looks at every store that a seller owns
            ArrayList<Product> productsList = seller.getStores().get(i).getProducts();
            for (int j = 0; j < productsList.size(); j++) {
                index++;
                int totalSale = productsList.get(j).getQuantAvailable();
                System.out.println(String.format("%d Store: %s, Product: %s, Sales: %d", index, storeNameSeller,
                                productsList.get(j).getName(), totalSale));
                productsInfo.add(String.format("Store: %s, Product: %s", storeNameSeller,
                        productsList.get(j).getName()));
                productsSales.add(totalSale);
            }
        }
        sortProduct(productsInfo, productsSales);
    }

    public void sortCustomers(ArrayList<String> sortedCustomersInfo, ArrayList<Integer> sortedPurchasedNumber) {
        Scanner scan = new Scanner(System.in);
        boolean checkFormat;
        int option = 0;
        do {
            checkFormat = true;
            try {
                System.out.println("How do you want to sort the number of purchased items ? ( 1. Max to min   2. Min to max) ");
                option = scan.nextInt();
                scan.nextLine();
            } catch (NumberFormatException e) {
                System.out.println("Please enter a correct format!");
            }
            if (option !=  1 && option != 2) {
                System.out.println("Please enter a valid number!");
                checkFormat = false;
            }
        } while (!checkFormat);

        if (option == 1) {
            for (int i = 1; i < sortedPurchasedNumber.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (sortedPurchasedNumber.get(i) >
                            sortedPurchasedNumber.get(j)) {
                        int largerNumber = sortedPurchasedNumber.get(i);
                        sortedPurchasedNumber.set(i, sortedPurchasedNumber.get(j));
                        sortedPurchasedNumber.set(j, largerNumber);
                        String largeName = sortedCustomersInfo.get(i);
                        sortedCustomersInfo.set(i, sortedCustomersInfo.get(j));
                        sortedCustomersInfo.set(j, largeName);
                    }
                }
            }
            for (int i = 0; i < sortedPurchasedNumber.size(); i++) {
                int index = i++;
                System.out.println(index + " " + sortedCustomersInfo.get(i) + ", purchased items: "
                            + sortedPurchasedNumber.get(i));
            }
        }
        if (option == 2) {
            for (int i = 1; i < sortedPurchasedNumber.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (sortedPurchasedNumber.get(i) <
                            sortedPurchasedNumber.get(j)) {
                        int smallerNumber = sortedPurchasedNumber.get(i);
                        sortedPurchasedNumber.set(i, sortedPurchasedNumber.get(j));
                        sortedPurchasedNumber.set(j, smallerNumber);
                        String smallerName = sortedCustomersInfo.get(i);
                        sortedCustomersInfo.set(i, sortedCustomersInfo.get(j));
                        sortedCustomersInfo.set(j, smallerName);
                    }
                }
            }
            for (int i = 0; i < sortedPurchasedNumber.size(); i++) {
                int index = i++;
                System.out.println(index + " " + sortedCustomersInfo.get(i) + ", purchased items: "
                        + sortedPurchasedNumber.get(i));
            }
        }
    }
    public void sortProduct(ArrayList<String> names, ArrayList<Integer> totalSales) {
        Scanner scan = new Scanner(System.in);
        boolean checkFormat;
        int option = 0;
        do {
            checkFormat = true;
            try {
                System.out.println("How do you want to sort the number of sales ? ( 1. Max to min   2. Min to max) ");
                option = scan.nextInt();
                scan.nextLine();
            } catch (NumberFormatException e) {
                System.out.println("Please enter a correct format!");
            }
            if (option !=  1 && option != 2) {
                System.out.println("Please enter a valid number!");
                checkFormat = false;
            }
        } while (!checkFormat);

        if (option == 1) {
            for (int i = 1; i < totalSales.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (totalSales.get(i) >
                            totalSales.get(j)) {
                        int largerNumber = totalSales.get(i);
                        totalSales.set(i, totalSales.get(j));
                        totalSales.set(j, largerNumber);
                        String largeName = names.get(i);
                        names.set(i, names.get(j));
                        names.set(j, largeName);
                    }
                }
            }
            for (int i = 0; i < totalSales.size(); i++) {
                int index = i++;
                System.out.println(index + " " + names.get(i) + ", Sales: " + totalSales.get(i));
            }
        }
        if (option == 2) {
            for (int i = 1; i < totalSales.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (totalSales.get(i) <
                            totalSales.get(j)) {
                        int smallerNumber = totalSales.get(i);
                        totalSales.set(i, totalSales.get(j));
                        totalSales.set(j, smallerNumber);
                        String smallerName = names.get(i);
                        names.set(i, names.get(j));
                        names.set(j, smallerName);
                    }
                }
            }
            for (int i = 0; i < totalSales.size(); i++) {
                int index = i++;
                System.out.println(index + " " + names.get(i) + ", Sales: " + totalSales.get(i));
            }
        }

    }

}
