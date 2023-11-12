import java.util.ArrayList;
import java.util.Scanner;

public class Create extends Marketplace {
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
        int index = -1;
        ArrayList<Store> stores = seller.getStores();
        do {
            for (int i = 0; i < stores.size(); i++) {
                Store store = stores.get(i);
                int j = i + 1;
                System.out.print( j + ". " + store.getName() + "\t\t");
            }
            System.out.println("Enter the index of the store you want to edit: ");
            index = scan.nextInt();
            scan.nextLine();
        } while (index > stores.size() || index <= 0); // correct index is from 1 to store.size()
        createProduct(index);
    }

    public void createProduct(int indexTemp) {
        int index = indexTemp - 1;
        Scanner scan = new Scanner(System.in);
        Store store = seller.getStores().get(index);

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
        seller.getStores().get(index).addProduct(product);
    }

    // public static void main(String[] args) {
    // Create create = new Create();
    // Scanner scan = new Scanner(System.in);
    // System.out.println("Enter your name");
    // String name = scan.nextLine();
    // create.setSeller(name);
    // int index = create.showStore();
    // create.createProduct(index);
    //}
}
