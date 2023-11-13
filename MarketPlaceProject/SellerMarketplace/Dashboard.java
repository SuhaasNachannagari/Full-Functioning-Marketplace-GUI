import java.util.ArrayList;
import java.util.Scanner;

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
        System.out.println("1. List of customers    2. List of products");
        int indexSelection = scan.nextInt(); scan.nextLine();
        if (indexSelection == 1 ) {
            getListCustomers();
        }
        if (indexSelection == 2) {
            getListProducts();
        }

    }
    public void getListCustomers() {
        int index = 0;
        for (int i = 0; i < seller.getStores().size(); i++) {
            String storeNameSeller = seller.getStores().get(i).getName(); //looks at every store that a seller owns
            for (int j = 0; j < customers.size(); j++) {
                int itemNumber = 0;
                ArrayList<Product> purchasedProduct = customers.get(j).getPurchaseHistory();
                for (int k = 0; k < purchasedProduct.size(); k++ ) {
                    // if in purchaseHistory, there is a product that comes from the desired store
                    if (purchasedProduct.get(k).getStoreName().equals(storeNameSeller)) {
                        itemNumber += customers.get(j).getPurchaseNumber(); // accumulate purchased number
                        index++;
                        System.out.println( index + ". " + customers.get(j).getCustomerUserName()
                                        + ": " + customers.get(j).getPurchaseNumber());
                    }
                }
            }

        }
    }

    public void getListProducts() {
        int index = 0;
        for (int i = 0; i < seller.getStores().size(); i++) {
            String storeNameSeller = seller.getStores().get(i).getName(); //looks at every store that a seller owns
            for (int j = 0; j < customers.size(); j++) {
                ArrayList<Product> purchasedProduct = customers.get(j).getPurchaseHistory();
                for (int k = 0; k < purchasedProduct.size(); k++ ) {
                    // if in purchaseHistory, there is a product that comes from the desired store
                    if (purchasedProduct.get(k).getStoreName().equals(storeNameSeller)) {
                        index++;
                        System.out.println( index + ". " + purchasedProduct.get(k).getName()
                                + ": " + purchasedProduct.get(k).getSales() );
                    }
                }
            }

        }
    }

    public void sortDown();
    public void sortUp();

}
