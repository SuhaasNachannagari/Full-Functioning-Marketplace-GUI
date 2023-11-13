import java.util.ArrayList;

public class CustomerDashboard extends main {
    ArrayList<String> storeNames1;
    ArrayList<String> storeNames2;
    ArrayList<Integer> numberOfPurchases;
    ArrayList<String> productNames;

    private ArrayList<Customer> sortedCustomers = getCustomers();
    public void printDashboard(Customer customer) {
        for (int i = 0; i < sortedCustomers.size(); i++) {
            for (int j = 0; i < sortedCustomers.get(i).getpurchaseHistory().size();
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



        for (int i = 0; i < customer.getPurchaseHistory.size(); i++) {
            String storeName = customer.getPurhaseHistory.get(i).getStoreName;
            String productBought = customer.getPurchaseHistory.get(i).getName;
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
