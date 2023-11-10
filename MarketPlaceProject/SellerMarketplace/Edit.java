import java.util.ArrayList;
import java.util.Scanner;

public class Edit extends Marketplace {
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

        showAndEditProduct(index);
    }
    public void showAndEditProduct(int indexTemp) {
        int index = indexTemp - 1;
        Scanner scan = new Scanner(System.in);
        Store store = seller.getStores().get(index);

        int indexProductTemp;
        do {
            String[] nameCheck = new String[store.getProducts().size()]; //
            for (int i = 0; i < store.getProducts().size(); i++) {
                Product product = store.getProducts().get(i);
                int j = i + 1;
                System.out.print( j + ". " + product.getName() + "\t\t");
                nameCheck[i] = product.getName();
            }
            System.out.println("Enter the index of the store you want to edit: ");
            indexProductTemp = scan.nextInt();
            scan.nextLine();
        } while (indexProductTemp <= 0 || indexProductTemp > store.getProducts().size());

        int indexProduct = indexProductTemp - 1;
        boolean checkFormat;
        boolean checkOption;
        do {
            checkFormat = true;
            checkOption = true;
            try {
                String name = store.getProducts().get(indexProduct).getName();
                System.out.println("What do you want to change about this product? ");
                System.out.println("1. Name     2. Store Name      3. Description       4. Quantity Available       5. Price");
                int indexChange = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter the value you want to change: ");
                String valueChange = scan.nextLine();
                checkOption = store.editProduct(name, indexChange, valueChange);
            } catch (NumberFormatException e) {
                System.out.println("Please enter the right format!");
                checkFormat = false;
            }
        } while (!checkFormat || !checkOption);
    }

}
