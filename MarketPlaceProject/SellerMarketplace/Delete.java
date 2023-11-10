import java.util.ArrayList;
import java.util.Scanner;

public class Delete extends Marketplace {
    private ArrayList<Seller> sellers = getSeller();
    private Seller seller;
    public void setSeller(String userName) {
        for (int i = 0; i < sellers.size(); i++) {
            Seller sellerTemp = sellers.get(i);
            if (sellerTemp.getUserName().equals(userName)) {
                this.seller = sellers.get(i);
            }
            break;
        }
        showStore();
    }
    // show a list of stores and ask user to choose an index
    public void showStore() {
        Scanner scan = new Scanner(System.in);
        int index;
        ArrayList<Store> stores = seller.getStores();
        do {
            for (int i = 0; i < stores.size(); i++) {
                Store store = stores.get(i);
                int j = i + 1;
                System.out.print( j + "." + store.getName() + "\t");
            }
            System.out.println("Enter the index of the store you want to edit: ");
            index = scan.nextInt();
            scan.nextLine();
        } while (index > stores.size() || index <= 0); // correct index is from 1 to store.size()

        showAndDeleteProduct(index);
    }

    public void showAndDeleteProduct(int indexTemp) {
        int index = indexTemp - 1;
        Scanner scan = new Scanner(System.in);
        Store store = seller.getStores().get(index);

        int indexProductTemp;
        do {
            String[] nameCheck = new String[store.getProducts().size()]; //
            for (int i = 0; i < store.getProducts().size(); i++) {
                Product product = store.getProducts().get(i);
                int j = i + 1;
                System.out.print(j + ". " + product.getName() + "\t\t");
                nameCheck[i] = product.getName();
            }
            System.out.println("Enter the index of the store you want to edit: ");
            indexProductTemp = scan.nextInt();
            scan.nextLine();
        } while (indexProductTemp <= 0 || indexProductTemp > store.getProducts().size());

        int indexProduct = indexProductTemp - 1;

        store.deleteProduct(indexProduct);
    }
}
