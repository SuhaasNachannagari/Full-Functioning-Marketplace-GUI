import java.util.ArrayList;
import java.util.InputMismatchException;
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
            System.out.println(sellers.get(i).getUserName() + " checkName");
            if (sellers.get(i).getUserName().equals(userName)) {
                this.seller = sellers.get(i);
                sellerIndex = i;
                break;
            }
        }
        showStore();
    }
    public void showStore() {
        Scanner scan = new Scanner(System.in);

        int index = -1;
        boolean checkFormat;
        System.out.println(seller.getUserName() + "check01");
        System.out.println(seller.getStores().get(0).getName() + "check02");
        System.out.println(seller.getStores().get(0).getProducts().get(0).getName() + "check03");
        ArrayList<Store> stores = seller.getStores();
        do {
            int j = 0;
            for (int i = 0; i < stores.size(); i++) {
                Store store = stores.get(i);
                if (!store.getName().equals("N/A")) {
                    j = i + 1;
                    System.out.print(j + ". " + store.getName() + "\t\t");
                }
            }
            j++;
            System.out.print( j + ". Create new store");
            do {
                checkFormat = true;
                try {
                    System.out.println("\nEnter the index you want to edit: ");
                    index = scan.nextInt();
                    scan.nextLine();
                } catch (InputMismatchException e) {
                    checkFormat = false;
                    System.out.println("Please enter the right format!");
                }
            } while (!checkFormat);
        } while (index > stores.size() + 1 || index <= 0); // correct index is from 1 to store.size()
        createProduct(index);
    }

    public void createProduct(int indexTemp) {
        storeIndex = indexTemp - 1;
        Scanner scan = new Scanner(System.in);

        if (seller.getStores().get(0).getName().equals("N/A")) {
            System.out.println("Enter the name of the new store:");
            String name = scan.nextLine();
            sellers.get(sellerIndex).getStores().get(0).setName(name);
            sellers.get(sellerIndex).getStores().get(0).setSales(0);
            System.out.println("Store created!");
        } else if (storeIndex == seller.getStores().size()) {
            System.out.println("Enter the name of the new store:");
            String name = scan.nextLine();
            //sellers.get(sellerIndex).getStores().get(storeIndex).addProduct(new
            //        Product("", name,"", 0, 0.0));
            //Product dummyProd = new Product("", name,"", 0, 0.0);
            sellers.get(sellerIndex).getStores().add( new Store(new ArrayList<>(),name));
            sellers.get(sellerIndex).getStores().get(storeIndex).setSales(0);
            System.out.println("Store created!");
        } else {
            Store store = seller.getStores().get(storeIndex);
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
                            System.out.println("Price: ");
                            double price = scan.nextDouble();
                            scan.nextLine();
                            System.out.println("Limit: ");
                            int limit = scan.nextInt(); scan.nextLine();
                            product = new Product(name, storeName, desc, quantAvail, price);
                            product.setReviews(new ArrayList<>());
                            product.setLimit(limit);
                        } else {
                            System.out.println("This product has already existed, please add another product");
                            checkProductExist = true;
                        }
                    } while (checkProductExist);
                } catch (InputMismatchException e) {
                    System.out.println("Please enter the right format!");
                    checkFormat = true;
                }
            } while (checkFormat);
            // add the newly created product to the designated store
            try {
                if (sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(0).getName().equals("N/A")) {
                    // if the store hasn't contained any product yet
                    sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(0).setName(product.getName());
                    sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(0).setStoreName(product.getStoreName());
                    sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(0).setDescription(product.getDescription());
                    sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(0).setPrice(product.getPrice());
                    sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(0).setQuantAvailable(product.getQuantAvailable());
                    sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(0).setLimit(product.getLimit());
                    sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(0).setReviews(product.getReviews());
                } else {
                    // if the store already has at least 1 product existed.
                    sellers.get(sellerIndex).getStores().get(storeIndex).addProduct(product);
                }
            } catch (IndexOutOfBoundsException e) {
                // if the store already has at least 1 product existed.
                System.out.println("This will be the first product of the store!");
                sellers.get(sellerIndex).getStores().get(storeIndex).addProduct(product);
            }

            System.out.println("Product created!");
        }
    }
}
