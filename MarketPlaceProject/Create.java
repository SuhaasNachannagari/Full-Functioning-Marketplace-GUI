import java.util.ArrayList;
import java.util.Scanner;
/**
 * Project 4 - Create
 *
 * This class creates new product for a choosen store
 */
public class Create extends main {
    private Seller seller;
    private int sellerIndex;
    private int storeIndex;
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

        int index = -1;
        boolean checkFormat;
        ArrayList<Store> stores = seller.getStores();
        do {
            System.out.print("1. Create new store");
            for (int i = 0; i < stores.size(); i++) {
                Store store = stores.get(i);
                if (!store.getName().equals("")) {
                    int j = i + 2;
                    System.out.print(j + ". " + store.getName() + "\t\t");
                }
            }
            do {
                checkFormat = true;
                try {
                    System.out.println("\nEnter the index you want to edit: ");
                    index = scan.nextInt();
                    scan.nextLine();
                } catch (NumberFormatException e) {
                    checkFormat = false;
                    System.out.println("Please enter the right format!");
                }
            } while (!checkFormat);
        } while (index > stores.size() || index <= 0); // correct index is from 1 to store.size()
        createProduct(index);
    }

    public void createProduct(int indexTemp) {
        storeIndex = indexTemp - 1;
        Scanner scan = new Scanner(System.in);
        Store store = seller.getStores().get(storeIndex);

        if (indexTemp == seller.getStores().size()) {
            System.out.println("Enter the name:");
            String name = scan.nextLine();
            sellers.get(sellerIndex).getStores().get(storeIndex).addProduct(new
                    Product("", name,"", 0, 0.0));
            System.out.println("Store created!");
        } else {
            boolean checkFormat;
            Product product = null;
            do {
                checkFormat = false;
                try {
                    boolean checkProductExist;
                    do {
                        checkProductExist = false;
                        System.out.println("Enter the information of the product: ");
                        System.out.println("Name: ");
                        String name = scan.nextLine();
                        boolean checkExistName = false;
                        for (int i = 0; i < store.getProducts().size(); i++) {
                            if (name.equals(store.getProducts().get(i).getName())) {
                                checkExistName = true;
                                break;
                            }
                        }
                        if (!checkExistName) {
                            String storeName = store.getName();
                            System.out.println("Description: ");
                            String desc = scan.nextLine();
                            System.out.println("Quantity Available: ");
                            int quantAvail = scan.nextInt();
                            scan.nextLine();
                            System.out.println("Price ");
                            double price = scan.nextDouble();
                            scan.nextLine();
                            product = new Product(name, storeName, desc, quantAvail, price);
                        } else {
                            System.out.println("This product has already existed, please add another product");
                            checkProductExist = true;
                        }
                    } while (checkProductExist);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter the right format!");
                    checkFormat = true;
                }
            } while (checkFormat);
            // add the newly created product to the picked store
            sellers.get(sellerIndex).getStores().get(storeIndex).addProduct(product);
        }
    }
}
