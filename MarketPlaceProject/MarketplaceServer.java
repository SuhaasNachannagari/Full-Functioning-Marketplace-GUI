import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.PrimitiveIterator;
import java.util.Scanner;

public class MarketplaceServer {
    public static ArrayList<Seller> sellers = new ArrayList<>();
    public static ArrayList<Customer> customers = new ArrayList<>();
    private static Seller seller;
    private static int sellerIndex;
    private static Customer customer;
    private static int customerIndex;
    public static String showStore() {
        if (seller.getStores().isEmpty() || seller.getStores().get(0).getName().equals("N/A")) {
            return "No Stores";
        } else {
            String showStores = "";
            int j = 0;
            for (Store store : seller.getStores()) {
                j++;
                showStores += j + "." + store.getName() + "/-";
            }
            return showStores;
        }
    }
    public static String showProducts(String storeName) {
        for (Store store: seller.getStores()) {
            String productNames = "";
            if (store.getName().equals(storeName)) {
                int j = 0;
                for (Product prod: store.getProducts()) {
                    j++;
                    productNames += j + "." + prod.getName() + "/-";
                }
            }
            return productNames;
        }
        return null;
    }
    public static String deleteDeleteProduct(String storeName, String productName) {
        int storeIndex = 0;
        int productIndex = 0;
        for (int i = 0; i < seller.getStores().size(); i++ ) {
            if (seller.getStores().get(i).getName().equals(storeName)) {
                storeIndex = i;
                for (int j = 0; j < seller.getStores().get(i).getProducts().size(); j++) {
                    if (seller.getStores().get(i).getProducts().get(j).getName().equals(productName)) {
                        productIndex = j;
                        break;
                    }
                }
            }
        }

        if ( sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(0).getName().equals("N/A") ) {
            sellers.get(sellerIndex).getStores().remove(storeIndex);
            return "Store removed!";
        } else {
            sellers.get(sellerIndex).getStores().get(storeIndex).deleteProduct(productIndex);
            // when the last product is deleted, add a dummy product named "N/A"
            if (sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().size() == 0) {
                Product prodNull = new Product("N/A","N/A","N/A",0,0.0);
                prodNull.setReviews(new ArrayList<>());
                prodNull.setLimit(0);
                sellers.get(sellerIndex).getStores().get(storeIndex).addProduct(prodNull);
            }
            return "Product deleted!";
        }
    }
    public static String checkNoProductMessage(String storeChoice) {
        for (Store store: seller.getStores()) {
            if (store.getName().equals(storeChoice)) {
                if (store.getProducts().get(0).getName().equals("N/A")) {
                    return "No Products";
                }
                return "Yes Products";
            }
        }
        return null;
    }
    public static String editProduct(String storeChoice, String productChoice,String choice, String valueChange) {
        int storeIndex = 0;
        int productIndex = 0;
        int newStoreIndex = 0;
        for (int i = 0; i < seller.getStores().size(); i++ ) {
            if (seller.getStores().get(i).getName().equals(storeChoice)) {
                storeIndex = i;
                for (int j = 0; j < seller.getStores().get(i).getProducts().size(); j++) {
                    if (seller.getStores().get(i).getProducts().get(j).getName().equals(productChoice)) {
                        productIndex = j;
                        break;
                    }
                }
            }
        }

        if (choice.equals("2. Store Name")) {
            for (int i = 0; i < seller.getStores().size(); i++ ) {
                if (seller.getStores().get(i).getName().equals(valueChange)) {
                    newStoreIndex = i;
                }
            }

            String storeName = sellers.get(sellerIndex).getStores().get(newStoreIndex).getName();
            Product oldProduct = sellers.get(sellerIndex).getStores().get(storeIndex).getProducts().get(productIndex);
            Product newProduct = new Product(oldProduct.getName(),storeName, oldProduct.getDescription(),
                    oldProduct.getQuantAvailable(),oldProduct.getPrice());
            newProduct.setLimit(oldProduct.getLimit());
            newProduct.setReviews(oldProduct.getReviews());
            sellers.get(sellerIndex).getStores().get(newStoreIndex).addProduct(newProduct);
            sellers.get(sellerIndex).getStores().get(storeIndex).deleteProduct(productIndex);
        } else {
            String name = seller.getStores().get(storeIndex).getProducts().get(productIndex).getName();
            int indexChange = 0;
            if (choice.equals("1. Name")) { indexChange = 1; }
            if (choice.equals("3. Description")) { indexChange = 3; }
            if (choice.equals("4. Quantity Available")) { indexChange = 4; }
            if (choice.equals("5. Price")) { indexChange = 5; } if (choice.equals("6. Limit")) { indexChange = 6; }
            boolean checkOption = sellers.get(sellerIndex).getStores().get(storeIndex).editProduct(name, indexChange, valueChange);
        }

        return "Product Edited";
    }
    public static String showAllStore() {
        String showStores = "";
        int j = 0;
        for (Store store : seller.getStores()) {
            j++;
            showStores += j + "." + store.getName() + "/-";
        }
        showStores += ( seller.getStores().size() + "." + "Create new store");
        return showStores;
    }
    public static String createStoreNA(String storeName) {
        sellers.get(sellerIndex).getStores().get(0).setName(storeName);
        sellers.get(sellerIndex).getStores().get(0).setSales(0);
        return ("Store created!");
    }
    public static String createStore(String storeName) {
        int storeIndex = seller.getStores().size();
        sellers.get(sellerIndex).getStores().add( new Store(new ArrayList<>(),storeName));
        sellers.get(sellerIndex).getStores().get(storeIndex).setSales(0);
        return ("Store created!");
    }
    public static boolean checkProductName(String storeChoice, String productName) {
        for (int i = 0; i < seller.getStores().size(); i++) {
            if (seller.getStores().get(i).getName().equals(storeChoice.substring(2))) {
                for (Product prod: seller.getStores().get(i).getProducts()) {
                    if (prod.getName().equals(productName)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static String createProduct(String storeChoice, String prodName, String prodDes, String prodQuant,
                                       String prodPrice, String prodLimit ) {
        int storeIndex = 0;
        for (int i = 0; i < seller.getStores().size(); i++) {
            if (seller.getStores().get(i).getName().equals(storeChoice.substring(2))) {
                storeIndex = i;
                Product product = new Product(prodName,storeChoice.substring(2),prodDes,
                        Integer.parseInt(prodQuant),Double.parseDouble(prodPrice));
                product.setLimit(Integer.parseInt(prodLimit));
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
                        return ("This will be the first product of the store!");
                    } else {
                        // if the store already has at least 1 product existed.
                        sellers.get(sellerIndex).getStores().get(storeIndex).addProduct(product);
                        return ("Product created!");
                    }
                } catch (IndexOutOfBoundsException e) {
                    // if the store already has at least 1 product existed.
                    sellers.get(sellerIndex).getStores().get(storeIndex).addProduct(product);
                    return ("This will be the first product of the store!");
                }
            }
        }
        return null;
    }
    public static void setSeller(String username) {
        int i = 0;
        for (Seller seller: sellers) {
            if (seller.getUserName().equals(username)) {
                MarketplaceServer.seller = seller;
                sellerIndex = i;
            }
        }
    }
    public static void setCustomer(String username) {
        int i = 0;
        for (Customer customer: customers) {
            if (customer.getCustomerUserName().equals(username)) {
                MarketplaceServer.customer = customer;
                customerIndex = i;
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
