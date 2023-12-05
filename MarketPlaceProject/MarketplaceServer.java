import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.PrimitiveIterator;
import java.util.Scanner;

public class MarketplaceServer {
    private static ArrayList<Seller> sellers = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static Seller seller;
    private static int sellerIndex;
    private static Customer customer;
    private static int customerIndex;
    public static void delete() {
        if ( seller.getStores().size() == 0) {
            System.out.println("This seller doesn't have any stores!");
        } else {
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
                    } catch (InputMismatchException e) {
                        checkFormat1 = false;
                        System.out.println("Please enter the right format!");
                    }
                } while(!checkFormat1);
            } while (index > stores.size() || index <= 0);

            int storeIndex = index - 1;
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
                    // explain message
                    if (nameCheck[i].equals("N/A")) {
                        System.out.println("\n*N/A means the store doesn't contain any products*" +
                                "\n*if you remove N/A, you will remove the store itself*");
                    }
                }

                do {
                    checkFormat2 = true;
                    try {
                        System.out.println("Enter the index of the product you want to delete: ");
                        indexProductTemp = scan.nextInt();
                        scan.nextLine();
                    } catch (InputMismatchException e) {
                        checkFormat2 = false;
                        System.out.println("Please enter the right format!");
                    }
                } while (!checkFormat2);
            } while (indexProductTemp <= 0 || indexProductTemp > store.getProducts().size());

            int indexProduct = indexProductTemp - 1;

            if ( sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(0).getName().equals("N/A") ) {
                sellers.get(sellerIndex).getStores().remove(storeIndex);
                System.out.println("Store removed!");
            } else {
                sellers.get(sellerIndex).getStores().get(storeIndex).deleteProduct(indexProduct);
                System.out.println("Product deleted!");

                // when the last product is deleted, add a dummy product named "N/A"
                if (sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().size() == 0) {
                    Product prodNull = new Product("N/A","N/A","N/A",0,0.0);
                    prodNull.setReviews(new ArrayList<>());
                    prodNull.setLimit(0);
                    sellers.get(sellerIndex).getStores().get(storeIndex).addProduct(prodNull);
                }
            }
        }
    }
    public static void setSeller(String username) {
        int i = 0;
        for (Seller seller: sellers) {
            if (seller.getUserName().equals(username)) {
                MarketplaceServer.seller = seller;
                sellerIndex = i;
                i++;
            }
        }
    }
    public static void setCustomer(String username) {
        int i = 0;
        for (Customer customer: customers) {
            if (customer.getCustomerUserName().equals(username)) {
                MarketplaceServer.customer = customer;
                customerIndex = i;
                i++;
            }
        }
    }

    public static void writeDataSeller() {
        ArrayList<Seller> sellerData = sellers ;
        String sellerName;
        String storeName;
        String storeSale;
        String productName;
        String productDescription;
        String productQuant;
        String productPrice;
        String productLimit;
        try {
            PrintWriter pw = new PrintWriter( new FileOutputStream("SellerInfo.txt"));
            for (int i = 0; i < sellerData.size(); i++) {
                sellerName = sellerData.get(i).getUserName();
                ArrayList<Store> stores = sellerData.get(i).getStores();
                for (int j = 0; j < stores.size(); j++) {
                    storeName = stores.get(j).getName();
                    storeSale = String.valueOf(stores.get(j).getSales());
                    ArrayList<Product> products = stores.get(j).getProducts();
                    if (products.size() == 0) {
                        pw.write(String.format("%s/-%s/-%s/-%s/-%s/-%s/-%s/-%s/-",
                                sellerName, storeName, storeSale, "N/A", "N/A", "0", "0.0", "0" ));
                        pw.write("\n");
                    }

                    for (int k = 0; k < products.size(); k++) {
                        productName = products.get(k).getName();
                        productDescription = products.get(k).getDescription();
                        productQuant = String.valueOf(products.get(k).getQuantAvailable());
                        productPrice = String.valueOf(products.get(k).getPrice());
                        productLimit = String.valueOf(products.get(k).getLimit());
                        ArrayList<String> reviews = new ArrayList<>();
                        for (int o = 0; o < products.get(k).getReviews().size(); o++ ) {
                            reviews.add(products.get(k).getReviews().get(o));
                        }
                        // tri,tri's store,sales,milk,taro flavour,10,5.4,5,review1,review2
                        String ans = String.format("%s/-%s/-%s/-%s/-%s/-%s/-%s/-%s/-",
                                sellerName, storeName, storeSale, productName, productDescription,
                                productQuant, productPrice, productLimit);
                        pw.write(ans);
                        for (int l = 0; l < reviews.size(); l++ ) {
                            pw.write(reviews.get(l) + "/-");
                        }
                        pw.write("\n");
                    }
                }
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<Seller> readDataSeller() {
        ArrayList<String> tempList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("SellerInfo.txt"));
            String line = br.readLine();
            while (line != null) {
                tempList.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String nameTemp = "";
        ArrayList<Seller> sellersTemp = new ArrayList<>();
        Seller seller;
        int indexSeller = -1;
        Store store;
        String storeName = "";
        double storeSales;
        int indexStore = -1;
        Product product;
        ArrayList<String> reviewsTemp;

        // tri,tri's store,sales,milk,taro flavour,10,5.4,5,review1,review2
        for (int i = 0; i < tempList.size(); i++) {
            String[] arr = (tempList.get(i)).split("/-");
            // Seller
            if (nameTemp.equals(arr[0])) {  // check if is still the same Seller
                if (storeName.equals(arr[1])) {  // check if is still in the same Store
                    // Product
                    reviewsTemp = new ArrayList<>();
                    for (int j = 8; j < arr.length; j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[5]);
                    double doublePrice = Double.parseDouble(arr[6]);
                    int intLimit = Integer.parseInt(arr[7]);
                    product = new Product(arr[3],arr[1],arr[4],intQuantAvail, doublePrice);
                    product.setReviews(reviewsTemp);
                    product.setLimit(intLimit);
                    sellersTemp.get(indexSeller).getStores().get(indexStore).addProduct(product);

                } else {
                    // from new Store
                    indexStore++;
                    storeName = arr[1];
                    storeSales = Double.parseDouble(arr[2]); // from new store
                    store = new Store(null,storeName);
                    store.setSales(storeSales); // set Sales of store
                    sellersTemp.get(indexSeller).createStore(store); // add new Store to seller's stores
                    // Product
                    reviewsTemp = new ArrayList<>();
                    for (int j = 8; j < arr.length; j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[5]);
                    double doublePrice = Double.parseDouble(arr[6]);
                    int intLimit = Integer.parseInt(arr[7]);
                    product = new Product(arr[3],arr[1],arr[4],intQuantAvail, doublePrice);
                    product.setReviews(reviewsTemp);
                    product.setLimit(intLimit);
                    // intialize an ArrayList<Product>
                    sellersTemp.get(indexSeller).getStores().get(indexStore).setProducts(new ArrayList<>());
                    sellersTemp.get(indexSeller).getStores().get(indexStore).addProduct(product); // add Product

                }
            } else {
                // from new Seller
                indexSeller++;
                indexStore = -1;
                nameTemp = arr[0];
                seller = new Seller(null, nameTemp);
                sellersTemp.add(seller); // add new Seller to List of Sellers
                if (storeName.equals(arr[1])) {  // check if is still in the same Store
                    // Product
                    reviewsTemp = new ArrayList<>();
                    for (int j = 8; j < tempList.size(); j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[5]);
                    double doublePrice = Double.parseDouble(arr[6]);
                    int intLimit = Integer.parseInt(arr[7]);
                    product = new Product(arr[3],arr[1],arr[4],intQuantAvail, doublePrice);
                    product.setReviews(reviewsTemp);
                    product.setLimit(intLimit);
                    sellersTemp.get(indexSeller).getStores().get(indexStore).addProduct(product); // add new product
                } else {
                    // Store
                    indexStore++;
                    storeName = arr[1];
                    storeSales = Double.parseDouble(arr[2]); // from new store
                    store = new Store(null,storeName);
                    store.setSales(storeSales); // set Sales of store
                    sellersTemp.get(indexSeller).setStores(new ArrayList<>()); // initialize an ArrayList<Store>
                    sellersTemp.get(indexSeller).createStore(store); // add new Store to seller's stores
                    // Product
                    reviewsTemp = new ArrayList<>();
                    for (int j = 8; j < arr.length; j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[5]);
                    double doublePrice = Double.parseDouble(arr[6]);
                    int intLimit = Integer.parseInt(arr[7]);
                    product = new Product(arr[3],arr[1],arr[4],intQuantAvail, doublePrice);
                    product.setReviews(reviewsTemp);
                    product.setLimit(intLimit);
                    // initialize an ArrayList<Product>
                    sellersTemp.get(indexSeller).getStores().get(indexStore).setProducts(new ArrayList<>());
                    sellersTemp.get(indexSeller).getStores().get(indexStore).addProduct(product); // add new Product
                }
            }
        }
        return sellersTemp;
    }
    public static void writeDataCustomer(){
        ArrayList<Customer> customersData = customers;
        try {
            String custName;
            String productName;
            String storeName;
            String productDescription;
            String productQuant;
            String productPrice;
            String productLimit;
            PrintWriter pw = new PrintWriter(new FileOutputStream("CustomerInfo.txt"));
            for (int i = 0; i < customersData.size(); i++) {
                Customer custTemp = customersData.get(i);
                custName = custTemp.getCustomerUserName();
                pw.write(custName + "\n");
                ArrayList<Product> prodShop = custTemp.getShoppingCar();
                for (int k = 0; k < prodShop.size(); k++) {
                    productName = prodShop.get(k).getName();
                    storeName = prodShop.get(k).getStoreName();
                    productDescription = prodShop.get(k).getDescription();
                    productQuant = String.valueOf(prodShop.get(k).getQuantAvailable());
                    productPrice = String.valueOf(prodShop.get(k).getPrice());
                    productLimit = String.valueOf(prodShop.get(k).getLimit());
                    ArrayList<String> reviews = new ArrayList<>();
                    for (int o = 0; o < prodShop.get(k).getReviews().size(); o++ ) {
                        reviews.add(prodShop.get(k).getReviews().get(o));
                    }
                    // tri,tri's store,sales,milk,taro flavour,10,5.4,5,review1,review2
                    String ans = String.format("%s/-%s/-%s/-%s/-%s/-%s/-",
                            productName, storeName, productDescription,
                            productQuant, productPrice, productLimit);
                    pw.write(ans);
                    for (int l = 0; l < reviews.size(); l++ ) {
                        pw.write(reviews.get(l) + "/-");
                    }
                    pw.write("\n");
                }
                if (prodShop.size() != 0) {
                    pw.write("change/-value\n");
                }
                ArrayList<Product> prodHistory = custTemp.getPurchaseHistory();
                for (int k = 0; k < prodHistory.size(); k++) {
                    productName = prodHistory.get(k).getName();
                    storeName = prodHistory.get(k).getStoreName();
                    productDescription = prodHistory.get(k).getDescription();
                    productQuant = String.valueOf(prodHistory.get(k).getQuantAvailable());
                    productPrice = String.valueOf(prodHistory.get(k).getPrice());
                    productLimit = String.valueOf(prodHistory.get(k).getLimit());
                    ArrayList<String> reviews = new ArrayList<>();
                    for (int o = 0; o < prodHistory.get(k).getReviews().size(); o++ ) {
                        reviews.add(prodHistory.get(k).getReviews().get(o));
                    }
                    // tri,tri's store,sales,milk,taro flavour,10,5.4,5,review1,review2
                    String ans = String.format("%s/-%s/-%s/-%s/-%s/-%s/-",
                            productName, storeName, productDescription,
                            productQuant, productPrice, productLimit);
                    pw.write(ans);
                    for (int l = 0; l < reviews.size(); l++ ) {
                        pw.write(reviews.get(l) + "/-");
                    }
                    pw.write("\n");
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<Customer> readDataCustomer() {
        ArrayList<String> tempList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("CustomerInfo.txt"));
            String line = br.readLine();
            while (line != null){
                tempList.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int index = 0;
        String custName = "";
        int indexCust = 0;
        Customer cust ;
        ArrayList<Customer> customerTemp = new ArrayList<>();
        while (index < tempList.size()) {
            // milk,tri's store,taro flavor,10,5.5,7,review1,review2
            String[] arr = tempList.get(index).split("/-");
            if (arr.length == 1) {
                if (!custName.equals(tempList.get(index)) && !custName.equals("")) {
                    indexCust++;
                }
                custName = tempList.get(index);
                cust = new Customer(new ArrayList<>(), custName);
                cust.setPurchaseHistory(new ArrayList<>());
                customerTemp.add(cust);
                index++;
            } else {
                ArrayList<Product> listShop = new ArrayList<>();
                while (arr.length != 1 && arr.length != 2 && index < tempList.size() ) {
                    Product prodShop;
                    ArrayList<String> reviewsTemp = new ArrayList<>();
                    for (int j = 6; j < arr.length; j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[3]);
                    double doublePrice = Double.parseDouble(arr[4]);
                    int intLimit = Integer.parseInt(arr[5]);
                    prodShop = new Product(arr[0], arr[1], arr[2], intQuantAvail, doublePrice);
                    prodShop.setReviews(reviewsTemp);
                    prodShop.setLimit(intLimit);
                    listShop.add(prodShop);
                    // continue on the next line
                    index++;
                    try {
                        arr = tempList.get(index).split("/-");
                    } catch (IndexOutOfBoundsException e) {

                    }
                }

                if (arr.length != 1 && arr.length == 2) {
                    index++;
                    try {
                        arr = tempList.get(index).split("/-");
                    } catch (IndexOutOfBoundsException e) {
                    }
                }

                ArrayList<Product> listHist = new ArrayList<>();
                while (arr.length != 1 && index < tempList.size()) {
                    Product prodHistory;
                    ArrayList<String> reviewsTemp = new ArrayList<>();
                    for (int j = 6; j < arr.length; j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[3]);
                    double doublePrice = Double.parseDouble(arr[4]);
                    int intLimit = Integer.parseInt(arr[5]);
                    prodHistory = new Product(arr[0], arr[1], arr[2], intQuantAvail, doublePrice);
                    prodHistory.setReviews(reviewsTemp);
                    prodHistory.setLimit(intLimit);
                    listHist.add(prodHistory);
                    index++;
                    try {
                        arr = tempList.get(index).split("/-");
                    } catch (IndexOutOfBoundsException e) {
                    }
                }
                customerTemp.get(indexCust).setShoppingCar(listShop);
                customerTemp.get(indexCust).setPurchaseHistory(listHist);
                customerTemp.get(indexCust).setCustomerUserName(custName);
            }
        }
        return customerTemp;
    }

}
