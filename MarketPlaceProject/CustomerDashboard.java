import java.util.ArrayList;

/**
 * Project 4 - CustomerDashboard
 *
 * A Class that extends main in order to display and enact the code that runs whenever 
 * the customer chooses to view a dashboard.
 * It allows the customer to view all the stores and prints out the number of sales for each of them.
 * It also displays the products the customer has bought and the stores they have bought it from.
 *
 */

public class CustomerDashboard extends main {
    ArrayList<String> storeNames1 = new ArrayList<>();
    ArrayList<String> storeNames2 = new ArrayList<>();
    ArrayList<Integer> numberOfPurchases = new ArrayList<>();
    ArrayList<String> productNames = new ArrayList<>();

    private ArrayList<Customer> sortedCustomers = getCustomers();
    public void printDashboard(Customer customer) {
        for (int i = 0; i < sortedCustomers.size(); i++) {
            for (int j = 0; j < sortedCustomers.get(i).getPurchaseHistory().size();
                 j++) {
                String storeName =
                        sortedCustomers.get(i).getPurchaseHistory().get(j).getStoreName();
                int productBought =
                        sortedCustomers.get(i).getPurchaseHistory().get(j).getQuantAvailable();
                if (!storeNames2.contains(storeName)) {
                    storeNames2.add(storeName);
                    numberOfPurchases.add(productBought);
                } else {
                    int k = storeNames2.indexOf(storeName);
                    numberOfPurchases.set(k, numberOfPurchases.get(k) + productBought);
                }
            }
        }
        for (int i = 0; i < storeNames2.size(); i++) {
            System.out.println(storeNames2.get(i) + " - " + numberOfPurchases.get(i));
        }



        for (int i = 0; i < customer.getPurchaseHistory().size(); i++) {
            String storeName = customer.getPurchaseHistory().get(i).getStoreName();
            String productBought = customer.getPurchaseHistory().get(i).getName();
            if (!storeNames1.contains(storeName)) {
                storeNames1.add(storeName);
                productNames.add(productBought);
            } else {
                int j = storeNames1.indexOf(storeName);
                String toSet = productNames.get(j) + ", " + productBought;
                productNames.set(j, toSet);
            }
        }
        for (int i = 0; i < storeNames1.size(); i++) {
            System.out.println(storeNames1.get(i) + " - " + productNames.get(i));
        }
    }

}
