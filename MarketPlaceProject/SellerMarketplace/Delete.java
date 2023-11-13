import java.util.ArrayList;
import java.util.Scanner;

public class Delete extends main {
    private Seller seller;
    private int sellerIndex;
    private int storeIndex;
    private int indexProduct;

    public void setSeller(String userName) {
        for (int i = 0; i < sellers.size(); i++) {
            Seller sellerTemp = sellers.get(i);
            if (sellerTemp.getUserName().equals(userName)) {
                this.seller = sellers.get(i);
                sellerIndex = i;
            }
            break;
        }
        showStore();
    }
    // show a list of stores and ask user to choose an index
    public void showStore() {
        Scanner scan = new Scanner(System.in);
        
        int index = 0;
        boolean checkFormat1;
        ArrayList<Store> stores = seller.getStores();
        do {
            for (int i = 0; i < stores.size(); i++) {
                Store store = stores.get(i);
                int j = i + 1;
                System.out.print( j + "." + store.getName() + "\t");
            }
            do {
                checkFormat1 = true;
                try {
                    System.out.println("Enter the index of the store you want to edit: ");
                    index = scan.nextInt();
                    scan.nextLine();
                } catch (NumberFormatException e) {
                    checkFormat1 = false;
                    System.out.println("Please enter the right format!");
                }
            } while(!checkFormat1);
        } while (index > stores.size() || index <= 0); // correct index is from 1 to store.size()

        showAndDeleteProduct(index);
    }

    public void showAndDeleteProduct(int indexTemp) {
        storeIndex = indexTemp - 1;
        Scanner scan = new Scanner(System.in);
        Store store = seller.getStores().get(storeIndex);
        
        int indexProductTemp = 0;
        boolean checkFormat2;
        do {
            String[] nameCheck = new String[store.getProducts().size()]; //
            for (int i = 0; i < store.getProducts().size(); i++) {
                Product product = store.getProducts().get(i);
                int j = i + 1;
                System.out.print(j + ". " + product.getName() + "\t\t");
                nameCheck[i] = product.getName();
            }
            do {
                checkFormat2 = true;
                try {
                    System.out.println("Enter the index of the store you want to edit: ");
                    indexProductTemp = scan.nextInt();
                    scan.nextLine();
                } catch (NumberFormatException e) {
                    checkFormat2 = false;
                    System.out.println("Please enter the right format!");
                }
            } while (!checkFormat2);
        } while (indexProductTemp <= 0 || indexProductTemp > store.getProducts().size());

        indexProduct = indexProductTemp - 1;
        sellers.get(sellerIndex).getStores().get(storeIndex).deleteProduct(indexProduct);
    }
}
