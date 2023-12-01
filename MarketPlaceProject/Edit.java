import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Project 4 - Edit
 * <p>
 * This class will be used to edit products.
 */

public class Edit extends main {
    private Seller seller;
    private int sellerIndex;
    private int storeIndex;
    private int indexProduct;
    private String listStore = "";
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
    public void showStore() {
        Scanner scan = new Scanner(System.in);

        int index = 0;
        boolean checkFormat;
        ArrayList<Store> stores = seller.getStores();
        do {
            for (int i = 0; i < stores.size(); i++) {
                Store store = stores.get(i);
                int j = i + 1;
                listStore += (j + "." + store.getName() + "\t\t");
            }
            System.out.print(listStore);
            do {
                try {
                    checkFormat = true;
                    System.out.println("Enter the index of the store you want to edit: ");
                    index = scan.nextInt();
                    scan.nextLine();
                } catch (NumberFormatException e) {
                    checkFormat = false;
                    System.out.println("Please enter the right format!");
                }
            } while (!checkFormat);
        } while (index > stores.size() || index <= 0); // correct index is from 1 to store.size()

        // check if the store has any products
        if (seller.getStores().get(index-1).getProducts().get(0).getName().equals("N/A")) {
             System.out.println("This store doesn't have any products! You should create a new product for it");
        } else {
            showAndEditProduct(index);
        }
    }
    public void showAndEditProduct(int indexTemp) {
        storeIndex = indexTemp - 1;
        Scanner scan = new Scanner(System.in);
        Store store = seller.getStores().get(storeIndex);

        int indexProductTemp = 0;
        boolean checkFormat1;
        do {
            String[] nameCheck = new String[store.getProducts().size()]; //?????
            for (int i = 0; i < store.getProducts().size(); i++) {
                Product product = store.getProducts().get(i);
                int j = i + 1;
                System.out.print( j + ". " + product.getName() + "\t\t");
                nameCheck[i] = product.getName();
            }
            do {
                checkFormat1 = true;
                try {
                    System.out.println("Enter the index of the product you want to edit: ");
                    indexProductTemp = scan.nextInt();
                    scan.nextLine();
                } catch (InputMismatchException e) {
                    checkFormat1 = false;
                    System.out.println("Please enter the right format!");
                }
            } while (!checkFormat1);
        } while (indexProductTemp <= 0 || indexProductTemp > store.getProducts().size());

        indexProduct = indexProductTemp - 1;
        boolean checkFormat2;
        boolean checkOption;
        do {
            checkFormat2 = true;
            checkOption = true;
            try {
                String name = store.getProducts().get(indexProduct).getName();
                System.out.println("What do you want to change about this product? ");
                System.out.println("1. Name     2. Store Name      3. Description       4. Quantity Available       5. Price        6. Limit");
                int indexChange = scan.nextInt();
                scan.nextLine();

                //change storename;
                if (indexChange == 2) {
                    System.out.println("Which store you want to move to?");
                    System.out.println(listStore);
                    int newStoreIndex = scan.nextInt(); scan.nextLine();

                    String storeName = sellers.get(sellerIndex).getStores().get(newStoreIndex - 1).getName();
                    Product oldProduct = sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(indexProduct);
                    Product newProduct = new Product(oldProduct.getName(),storeName, oldProduct.getDescription(),
                                        oldProduct.getQuantAvailable(),oldProduct.getPrice());
                    newProduct.setLimit(oldProduct.getLimit());
                    newProduct.setReviews(oldProduct.getReviews());
                    sellers.get(sellerIndex).getStores().get(newStoreIndex-1).addProduct(newProduct);
                    sellers.get(sellerIndex).getStores().get(storeIndex).deleteProduct(indexProduct);
                } else {
                    System.out.println("Enter the value you want to change: ");
                    String valueChange = scan.nextLine();
                    checkOption = sellers.get(sellerIndex).getStores().get(storeIndex).editProduct(name, indexChange, valueChange);
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter the right format!");
                checkFormat2 = false;
            }
        } while (!checkFormat2 || !checkOption);
        System.out.println("Product edited");
    }

}
