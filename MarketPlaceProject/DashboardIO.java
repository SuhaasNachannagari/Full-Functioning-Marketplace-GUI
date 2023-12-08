import java.util.ArrayList;
import java.util.Scanner;
/**
 * Project 4 - Dashboard for Seller
 * <p>
 * This class will be used to display dashboard.
 */
public class DashboardIO extends MarketplaceServer {
    private ArrayList<String> sortedCustomerInfo = new ArrayList<>();
    private ArrayList<Integer> sortedPurchasedNumber = new ArrayList<>();
    private ArrayList<String> productsInfo = new ArrayList<>();
    private ArrayList<Integer> productsSales = new ArrayList<>();
    private Seller sellerMain;
    public String getListCustomers(String username) {
        for (Seller sellerTemp: sellers) {
            if (sellerTemp.getUserName().equals(username)) {
                sellerMain = sellerTemp;
                break;
            }
        }

        String searchedResult = "";
        int index = 0;
        for (int i = 0; i < sellerMain.getStores().size(); i++) {
            String storeNameSeller = sellerMain.getStores().get(i).getName(); //looks at every store that a seller owns
            for (int j = 0; j < customers.size(); j++) {
                int itemNumber = 0;
                ArrayList<Product> purchasedProduct = customers.get(j).getPurchaseHistory();
                for (int k = 0; k < purchasedProduct.size(); k++ ) {
                    // if in purchaseHistory, there is a product that comes from the desired store
                    if (purchasedProduct.get(k).getStoreName().equals(storeNameSeller)) {
                        itemNumber += purchasedProduct.get(k).getQuantAvailable(); // accumulate purchased number
                    }
                }
                if (itemNumber != 0) {
                    index++;
                    searchedResult += (String.format("%d Store: %s, Customer: %s, purchased items: %d/-",
                            index, storeNameSeller, customers.get(j).getCustomerUserName(), itemNumber));
                    // sortedCustomerInfo contains of storeName and customerName " Store: store1, Customer: tri "
                    String result = String.format("Store: %s, Customer: %s ",
                            storeNameSeller, customers.get(j).getCustomerUserName());
                    sortedCustomerInfo.add(result);
                    sortedPurchasedNumber.add(itemNumber);
                }
            }
        }
        return searchedResult;
    }
    public String getListProducts(String username) {
        for (Seller sellerTemp: sellers) {
            if (sellerTemp.getUserName().equals(username)) {
                sellerMain = sellerTemp;
                break;
            }
        }

        String result = "";
        int index = 0;
        for (int i = 0; i < sellerMain.getStores().size(); i++) {
            String storeNameSeller = sellerMain.getStores().get(i).getName(); //looks at every store that a seller owns
            ArrayList<Product> productsList = sellerMain.getStores().get(i).getProducts();
            for (int j = 0; j < productsList.size(); j++) {
                index++;
                int totalSale = productsList.get(j).getQuantAvailable();
                result += (String.format("%d Store: %s, Product: %s, Sales: %d/-", index, storeNameSeller,
                        productsList.get(j).getName(), totalSale));
                productsInfo.add(String.format("Store: %s, Product: %s", storeNameSeller,
                        productsList.get(j).getName()));
                productsSales.add(totalSale);
            }
        }
        return result;
    }

    public String sortCustomers(String option) {
        String result = "";
        if (option.equals("1. Max to min")) {
            for (int i = 0; i < sortedPurchasedNumber.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (sortedPurchasedNumber.get(i) >
                            sortedPurchasedNumber.get(j)) {
                        int largerNumber = sortedPurchasedNumber.get(i);
                        sortedPurchasedNumber.set(i, sortedPurchasedNumber.get(j));
                        sortedPurchasedNumber.set(j, largerNumber);

                        String largeName = sortedCustomerInfo.get(i);
                        sortedCustomerInfo.set(i, sortedCustomerInfo.get(j));
                        sortedCustomerInfo.set(j, largeName);
                    }
                }
            }
            for (int i = 0; i < sortedPurchasedNumber.size(); i++) {
                int index = i + 1;
                result += (index + " " + sortedCustomerInfo.get(i) + ", purchased items: "
                        + sortedPurchasedNumber.get(i) + "/-");
            }
        }
        if (option.equals("2. Min to max")) {
            for (int i = 0; i < sortedPurchasedNumber.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (sortedPurchasedNumber.get(i) <
                            sortedPurchasedNumber.get(j)) {
                        int smallerNumber = sortedPurchasedNumber.get(i);
                        sortedPurchasedNumber.set(i, sortedPurchasedNumber.get(j));
                        sortedPurchasedNumber.set(j, smallerNumber);
                        String smallerName = sortedCustomerInfo.get(i);
                        sortedCustomerInfo.set(i, sortedCustomerInfo.get(j));
                        sortedCustomerInfo.set(j, smallerName);
                    }
                }
            }
            for (int i = 0; i < sortedPurchasedNumber.size(); i++) {
                int index = i + 1;
                result += (index + " " + sortedCustomerInfo.get(i) + ", purchased items: "
                        + sortedPurchasedNumber.get(i) + "/-");
            }
        }
        return result;
    }
    public String sortProduct(String option) {
        String result = "";
        if (option.equals("1. Max to min")) {
            for (int i = 0; i < productsSales.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (productsSales.get(i) >
                            productsSales.get(j)) {
                        int largerNumber = productsSales.get(i);
                        productsSales.set(i, productsSales.get(j));
                        productsSales.set(j, largerNumber);
                        String largeName = productsInfo.get(i);
                        productsInfo.set(i, productsInfo.get(j));
                        productsInfo.set(j, largeName);
                    }
                }
            }
            for (int i = 0; i < productsSales.size(); i++) {
                int index = i + 1;
                result += (index + " " + productsInfo.get(i) + ", Sales: " + productsSales.get(i) + "/-");
            }
        }
        if (option.equals("2. Min to max")) {
            for (int i = 0; i < productsSales.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (productsSales.get(i) <
                            productsSales.get(j)) {
                        int smallerNumber = productsSales.get(i);
                        productsSales.set(i, productsSales.get(j));
                        productsSales.set(j, smallerNumber);
                        String smallerName = productsInfo.get(i);
                        productsInfo.set(i, productsInfo.get(j));
                        productsInfo.set(j, smallerName);
                    }
                }
            }
            for (int i = 0; i < productsSales.size(); i++) {
                int index = i + 1;
                result += (index + " " + productsInfo.get(i) + ", Sales: " + productsSales.get(i) + "/-");
            }
        }
        return result;
    }
}
