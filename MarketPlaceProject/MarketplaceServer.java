import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.PrimitiveIterator;
import java.util.Scanner;

public class MarketplaceServer {
    public static ArrayList<Seller> sellers = new ArrayList<>();
    public static ArrayList<Customer> customers = new ArrayList<>();

    public synchronized static void createCustomer(Customer customer) {
        customers.add(customer);
    }
    public synchronized static void createSeller(Seller seller) {
        sellers.add(seller);
    }
    public  static String showStore(String username) {
        Seller seller = null;
        for (Seller sellerTemp: sellers) {
            if (sellerTemp.getUserName().equals(username)) {
                seller = sellerTemp;
            }
        }
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
    public static String showProducts(String storeName, String username) {
        Seller seller = null;
        for (Seller sellerTemp: sellers) {
            if (sellerTemp.getUserName().equals(username)) {
                seller = sellerTemp;
            }
        }
        for (Store store: seller.getStores()) {
            String productNames = "";
            if (store.getName().equals(storeName.substring(2))) {
                int j = 0;
                for (Product prod: store.getProducts()) {
                    j++;
                    productNames += j + "." + prod.getName() + "/-";
                }
                return productNames;
            }
        }
        return null;
    }
    public synchronized static String deleteDeleteProduct(String storeChoice, String productName, String username) {
        Seller seller = null;
        int sellerIndex = 0;
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getUserName().equals(username)) {
                seller = sellers.get(i);
                sellerIndex = i;
                break;
            }
        }
        int storeIndex = 0;
        int productIndex = 0;
        for (int i = 0; i < seller.getStores().size(); i++ ) {
            if (seller.getStores().get(i).getName().equals(storeChoice.substring(2))) {
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
    public  static String checkNoProductMessage(String storeChoice, String username) {
        Seller seller = null;
        for (Seller sellerTemp: sellers) {
            if (sellerTemp.getUserName().equals(username)) {
                seller = sellerTemp;    break;
            }
        }
        for (Store store: seller.getStores()) {
            if (store.getName().equals(storeChoice.substring(2))) {
                if (store.getProducts().get(0).getName().equals("N/A")) {
                    return "No Products";
                }
                return "Yes Products";
            }
        }
        return null;
    }
    public synchronized static String editProduct(String storeChoice, String productChoice, String choice, String valueChange, String username) {
        Seller seller = null;
        int sellerIndex = 0;
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getUserName().equals(username)) {
                seller = sellers.get(i);
                sellerIndex = i;
                break;
            }
        }
        int storeIndex = 0;
        int productIndex = 0;
        int newStoreIndex = 0;
        for (int i = 0; i < seller.getStores().size(); i++ ) {
            if (seller.getStores().get(i).getName().equals(storeChoice.substring(2))) {
                storeIndex = i;
                for (int j = 0; j < seller.getStores().get(i).getProducts().size(); j++) {
                    if (seller.getStores().get(i).getProducts().get(j).getName().equals(productChoice.substring(2))) {
                        productIndex = j;
                        break;
                    }
                }
            }
        }

        if (choice.equals("2.Store Name")) {
            for (int i = 0; i < seller.getStores().size(); i++ ) {
                if (seller.getStores().get(i).getName().equals(valueChange.substring(2))) {
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
            if (choice.equals("1.Name")) { indexChange = 1; }
            if (choice.equals("3.Description")) { indexChange = 3; }
            if (choice.equals("4.Quantity Available")) { indexChange = 4; }
            if (choice.equals("5.Price")) { indexChange = 5; } if (choice.equals("6.Limit")) { indexChange = 6; }
            boolean checkOption = sellers.get(sellerIndex).getStores().get(storeIndex).editProduct(name, indexChange, valueChange);
        }

        return "Product Edited";
    }
    public static String showAllStore(String username) {
        Seller seller = null;
        for (Seller sellerTemp: sellers) {
            if (sellerTemp.getUserName().equals(username)) {
                seller = sellerTemp; break;
            }
        }
        String showStores = "";
        int j = 0;
        for (Store store : seller.getStores()) {
            j++;
            showStores += j + "." + store.getName() + "/-";
        }
        showStores += ( (seller.getStores().size() + 1) + "." + "Create new store");
        return showStores;
    }
    public synchronized static String createStoreNA(String storeName, String username) {
        int sellerIndex = 0;
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getUserName().equals(username)) {
                sellerIndex = i;
                break;
            }
        }
        sellers.get(sellerIndex).getStores().get(0).setName(storeName);
        sellers.get(sellerIndex).getStores().get(0).setSales(0);
        return ("Store created!");
    }
    public synchronized static String createStore(String storeName, String username) {
        Seller seller = null;
        int sellerIndex = 0;
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getUserName().equals(username)) {
                seller = sellers.get(i);
                sellerIndex = i;
                break;
            }
        }
        int storeIndex = seller.getStores().size();
        sellers.get(sellerIndex).getStores().add( new Store(new ArrayList<>(),storeName));
        sellers.get(sellerIndex).getStores().get(storeIndex).setSales(0);
        return ("Store created!");
    }
    public static boolean checkProductName(String storeChoice, String productName, String username) {
        Seller seller = null;
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getUserName().equals(username)) {
                seller = sellers.get(i);
                break;
            }
        }
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
    public synchronized static String createProduct(String storeChoice, String prodName, String prodDes, String prodQuant,
                                       String prodPrice, String prodLimit, String username ) {
        Seller seller = null;
        int sellerIndex = 0;
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getUserName().equals(username)) {
                seller = sellers.get(i);
                sellerIndex = i;
                break;
            }
        }
        int storeIndex = 0;
        for (int i = 0; i < seller.getStores().size(); i++) {
            if (seller.getStores().get(i).getName().equals(storeChoice.substring(2))) {
                storeIndex = i;
                Product product = new Product(prodName,storeChoice.substring(2),prodDes,
                        Integer.parseInt(prodQuant),Double.parseDouble(prodPrice));
                product.setLimit(Integer.parseInt(prodLimit));
                product.setReviews(new ArrayList<>());
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
    public static String viewStores(String username) {
        int sellerIndex = 0;
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getUserName().equals(username)) {
                sellerIndex = i;
                break;
            }
        }
        String salesByStores = "";
        if (sellers.get(sellerIndex).getStores().get(0).getName().equals("N/A")) {
            return "No stores";
        } else {
            for (Store store : sellers.get(sellerIndex).getStores()) {
                salesByStores += store.getName() + ":/-";
                int totalSale = 0;
                for (Customer cust : customers) {
                    ArrayList<Product> purchaseTemp = cust.getPurchaseHistory();
                    for (int i = 0; i < purchaseTemp.size(); i++) {
                        if (store.getName().equals(purchaseTemp.get(i).getStoreName())) {
                            totalSale += purchaseTemp.get(i).getQuantAvailable();
                            double revenue = (purchaseTemp.get(i).getPrice() * purchaseTemp.get(i).getQuantAvailable());
                            salesByStores += String.format("Customer name: %s     Product: %s     Revenue: %.2f/-",
                                    cust.getCustomerUserName(), purchaseTemp.get(i).getName(), revenue);
                        }
                    }
                }
                salesByStores += "Total Sales: " + totalSale + " product(s)/-";
            }
        }
        return salesByStores;
    }
    public static String viewShoppingCart(String username) {
        int sellerIndex = 0;
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getUserName().equals(username)) {
                sellerIndex = i;
                break;
            }
        }
        String shoppingCartResult = "";
        if (sellers.get(sellerIndex).getStores().get(0).getName().equals("N/A")) {
            System.out.println("This user has no stores, please add some!");
            return "No stores";
        } else {
            for (Store store : sellers.get(sellerIndex).getStores()) {
                shoppingCartResult += (store.getName() + ":/-");
                for (Customer cust : customers) {
                    ArrayList<Product> cartTemp = cust.getPurchaseHistory();
                    for (Product prod : cartTemp) {
                        if (store.getName().equals(prod.getStoreName())) {
                            shoppingCartResult += (String.format("Customer name: %s     Product: %s     Description: %s" +
                                            "   Quantity: %d/-", cust.getCustomerUserName(), prod.getName(), prod.getDescription()
                                    , prod.getQuantAvailable()));
                        }
                    }
                }
                shoppingCartResult += "/-";
            }
        }
        return shoppingCartResult;
    }
    public static String saveToFileProduct(String storeName, String username, String fileName) {
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getUserName().equals(username)) {
                for( int j = 0; j < sellers.get(i).getStores().size(); j++) {
                    if (sellers.get(i).getStores().get(j).getName().equals(storeName.substring(2))) {
                        for(int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                            Product product = sellers.get(i).getStores().get(j).getProducts().get(k);
                            sellers.get(i).saveToFileProductSeller(product, fileName);
                        }
                    }
                }
            }
        }
        return "Saved to File";
    }
    public synchronized static String loadFromFileProduct(String fileName) {
        ArrayList<String> productData = new ArrayList<>();
        ArrayList<String> sellerInfo = new ArrayList<>();
        writeDataSeller();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)) ){
            String line;
            while ((line = reader.readLine()) != null) {
                productData.add(line);
            }
            if(productData == null || productData.isEmpty()) {
                return "No data in file!";
            } else {
                PrintWriter pwr = new PrintWriter(new FileOutputStream("SellerInfo.txt", true), true);
                for (int i = 1; i < productData.size(); i++) { //start from one cuz the first line is the title.
                    String[] parts = productData.get(i).split(",");
                    String toSellerInfo = "";
                    for (int j = 0; j < parts.length; j++) {
                        if (j == 0) {
                            toSellerInfo += parts[j];
                        } else if (j == 2) {
                            toSellerInfo += "/-0.0/-" + parts[j];
                        } else if (j == (parts.length-1)) {
                            String[] reviews = parts[j].split("/");
                            for (String review : reviews) {
                                toSellerInfo += "/-" + review;
                            }
                            toSellerInfo += "/-";
                        } else {
                            toSellerInfo += "/-" + parts[j];
                        }
                    }
                    sellerInfo.add(toSellerInfo);
                }
                for (String product : sellerInfo) {
                    pwr.println(product);
                }
                pwr.flush();
                pwr.close();
                ArrayList<String[]> products = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader("SellerInfo.txt"));
                String arrangeLine;
                while ((arrangeLine = br.readLine()) != null) {
                    String[] parts = arrangeLine.split("/-");
                    products.add(parts);
                }
                br.close();
                int n = products.size();
                for (int i = 0; i < n - 1; i++) {
                    for (int j = 0; j < n - i - 1; j++) {
                        String[] current = products.get(j);
                        String[] next = products.get(j + 1);
                        if (current[0].compareTo(next[0]) > 0 ||
                                (current[0].equals(next[0]) && current[1].compareTo(next[1]) > 0)) {
                            products.set(j, next);
                            products.set(j + 1, current);
                        }
                    }
                }
                BufferedWriter bw = new BufferedWriter(new FileWriter("SellerInfo.txt", false));
                for (String[] product : products) {
                    bw.write(String.join("/-", product));
                    bw.newLine();
                }
                bw.flush();
                bw.close();
                sellers = readDataSeller();
                System.out.println("File imported success");
            }
            return "Imported succeed!";

        } catch (IOException ex) {
            return "File doesn't exist!";
        }
        //System.out.println("User data loaded from " + "filler.txt");
    }

    public static ArrayList<Product> searchProducts(String search) {
        ArrayList<Product> listedProducts = new ArrayList<>();
        for (Seller seller : sellers) {
            ArrayList<Store> stores = seller.getStores();
            for (Store store : stores) {
                ArrayList<Product> products = store.getProducts();
                for (Product product : products) {
                    listedProducts.add(product);
                }
            }
        }
        ArrayList<Product> matchingProducts = new ArrayList<>();


        for (Product product : listedProducts) {
            if (product.getName().contains(search) || product.getDescription().contains(search) ||
                    product.getStoreName().contains(search)) {
                matchingProducts.add(product);
            }
        }

        if (matchingProducts.size() == 0) {
            return null;
        }
        return matchingProducts;
    }

    public static ArrayList<Product> sortProducts(int sortType, String sortBy) {
        ArrayList<Product> quantityListedProducts = new ArrayList<>();
        ArrayList<Product> priceListedProducts = new ArrayList<>();
        //below is sorting by quantity
        for (Seller seller : sellers) {
            ArrayList<Store> stores = seller.getStores();
            for (Store store : stores) {
                ArrayList<Product> products = store.getProducts();
                for (Product product : products) {
                    quantityListedProducts.add(product);
                }
            }
        }
        if (sortBy.equals(" Low To High")) {
            for (int i = 1; i < quantityListedProducts.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (quantityListedProducts.get(i).getQuantAvailable() <
                            quantityListedProducts.get(j).getQuantAvailable()) {
                        Product smallerproduct = quantityListedProducts.get(i);
                        quantityListedProducts.set(i, quantityListedProducts.get(j));
                        quantityListedProducts.set(j, smallerproduct);
                    }
                }
            }
        } else {
            for (int i = 1; i < quantityListedProducts.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (quantityListedProducts.get(i).getQuantAvailable() >
                            quantityListedProducts.get(j).getQuantAvailable()) {
                        Product largerProduct = quantityListedProducts.get(i);
                        quantityListedProducts.set(i, quantityListedProducts.get(j));
                        quantityListedProducts.set(j, largerProduct);
                    }
                }
            }
        }
        // below is sorting for price
        for (Seller seller : sellers) {
            ArrayList<Store> stores = seller.getStores();
            for (Store store : stores) {
                ArrayList<Product> products = store.getProducts();
                for (Product product : products) {
                    priceListedProducts.add(product);
                }
            }
        }
        if (sortBy.equals(" Low To High")) {
            for (int i = 1; i < priceListedProducts.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (priceListedProducts.get(i).getPrice() < priceListedProducts.get(j).getPrice()) {
                        Product smallerproduct = priceListedProducts.get(i);
                        priceListedProducts.set(i, priceListedProducts.get(j));
                        priceListedProducts.set(j, smallerproduct);
                    }
                }
            }
        } else {
            for (int i = 1; i < priceListedProducts.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (priceListedProducts.get(i).getPrice() > priceListedProducts.get(j).getPrice()) {
                        Product largerProduct = priceListedProducts.get(i);
                        priceListedProducts.set(i, priceListedProducts.get(j));
                        priceListedProducts.set(j, largerProduct);
                    }
                }
            }

        }
        //below is returning the proper arraylist
        if (sortType == 1) {
            return priceListedProducts;
        } else {
            return quantityListedProducts;
        }

       /* System.out.println("Here are your available items sorted by quantity (lowest to highest)");
        int i = 1;
        for (Product product : quantityListedProducts) {
            System.out.printf("%d. Store: %s, Name: %s, Quantity: %d\n",
                    i,
                    product.getStoreName(),
                    product.getName(),
                    product.getQuantAvailable());
            i++;
        } */
    }

    public static ArrayList<Product> viewProducts() {
        ArrayList<Product> viewProducts = new ArrayList<>();
        //below is sorting by quantity
        for (Seller seller : sellers) {
            ArrayList<Store> stores = seller.getStores();
            for (Store store : stores) {
                ArrayList<Product> products = store.getProducts();
                for (Product product : products) {
                    viewProducts.add(product);
                }
            }
        }

        //below is returning the proper arraylist
        return viewProducts;
    }

    public synchronized static String priceAddToShoppingCart(ArrayList<Product> priceListedProducts, String username, int quantity, int num) {
        Product productFromSeller = priceListedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedShoppingCart = customer.getShoppingCar();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    return ("You are attempting to add more than the limit of "
                            + productFromSeller.getLimit()
                            + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    return ("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to add above that limit");
                } else {
                    Product productToAdd = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity, productFromSeller.getPrice());

                    productToAdd.setLimit(productFromSeller.getLimit());
                    productToAdd.setReviews(productFromSeller.getReviews());
                    if (updatedShoppingCart != null && updatedShoppingCart.size() != 0) {
                        if (updatedShoppingCart.get(0).getName().equals("N/A")) {
                            updatedShoppingCart.set(0, productToAdd);
                        } else {
                            updatedShoppingCart.add(productToAdd);
                        }
                    } else {
                        updatedShoppingCart.add(productToAdd);
                    }
                    ArrayList<Product> combinedList = new ArrayList<>();
                    for (Product currentProduct : updatedShoppingCart) {
                        boolean found = false;
                        // Check if the product is already in the combined list
                        for (Product combinedProduct : combinedList) {
                            if (currentProduct.getName().equals(combinedProduct.getName()) &&
                                    currentProduct.getStoreName().equals(combinedProduct.getStoreName()) &&
                                    currentProduct.getDescription().equals(combinedProduct.getDescription()) &&
                                    currentProduct.getPrice() == combinedProduct.getPrice()) {
                                // If found, update the quantity
                                combinedProduct.setQuantAvailable(combinedProduct.getQuantAvailable() + currentProduct.getQuantAvailable());
                                found = true;
                                break;
                            }
                        }
                        // If not found, add it to the combined list
                        if (!found) {
                            combinedList.add(new Product(
                                    currentProduct.getName(),
                                    currentProduct.getStoreName(),
                                    currentProduct.getDescription(),
                                    currentProduct.getQuantAvailable(),
                                    currentProduct.getPrice()
                            ));
                        }
                    }
                    customer.setShoppingCar(combinedList);
                }
            }
        }
        return ("Item added!");
    }

    public synchronized static String quantityAddToShoppingCart(ArrayList<Product> quantityListedProducts, String username, int quantity, int num) {
        Product productFromSeller = quantityListedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedShoppingCart = customer.getShoppingCar();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    return ("You are attempting to add more than the limit of "
                            + productFromSeller.getLimit()
                            + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    return ("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to add above that limit");
                } else {
                    Product productToAdd = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity, productFromSeller.getPrice());

                    productToAdd.setLimit(productFromSeller.getLimit());
                    productToAdd.setReviews(productFromSeller.getReviews());
                    if (updatedShoppingCart != null && updatedShoppingCart.size() != 0) {
                        if (updatedShoppingCart.get(0).getName().equals("N/A")) {
                            updatedShoppingCart.set(0, productToAdd);
                        }
                        else {
                            updatedShoppingCart.add(productToAdd);
                        }
                    } else {
                        updatedShoppingCart.add(productToAdd);
                    }
                    ArrayList<Product> combinedList = new ArrayList<>();
                    for (Product currentProduct : updatedShoppingCart) {
                        boolean found = false;
                        // Check if the product is already in the combined list
                        for (Product combinedProduct : combinedList) {
                            if (currentProduct.getName().equals(combinedProduct.getName()) &&
                                    currentProduct.getStoreName().equals(combinedProduct.getStoreName()) &&
                                    currentProduct.getDescription().equals(combinedProduct.getDescription()) &&
                                    currentProduct.getPrice() == combinedProduct.getPrice()) {
                                // If found, update the quantity
                                combinedProduct.setQuantAvailable(combinedProduct.getQuantAvailable() + currentProduct.getQuantAvailable());
                                found = true;
                                break;
                            }
                        }
                        // If not found, add it to the combined list
                        if (!found) {
                            combinedList.add(new Product(
                                    currentProduct.getName(),
                                    currentProduct.getStoreName(),
                                    currentProduct.getDescription(),
                                    currentProduct.getQuantAvailable(),
                                    currentProduct.getPrice()
                            ));
                        }
                    }
                    customer.setShoppingCar(combinedList);
                }
            }
        }
        return ("Item added!");
    }
    public synchronized static String addToShoppingCart(ArrayList<Product> listedProducts, String username, int quantity, int num) {
        Product productFromSeller = listedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedShoppingCart = customer.getShoppingCar();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    return ("You are attempting to add more than the limit of "
                            + productFromSeller.getLimit()
                            + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    return ("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to add above that limit");
                } else {
                    Product productToAdd = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity, productFromSeller.getPrice());

                    productToAdd.setLimit(productFromSeller.getLimit());
                    productToAdd.setReviews(productFromSeller.getReviews());
                    if (updatedShoppingCart != null && updatedShoppingCart.size() != 0) {
                        if (updatedShoppingCart.get(0).getName().equals("N/A")) {
                            updatedShoppingCart.set(0, productToAdd);
                        }
                        else {
                            updatedShoppingCart.add(productToAdd);
                        }
                    } else {
                        updatedShoppingCart.add(productToAdd);
                    }
                    ArrayList<Product> combinedList = new ArrayList<>();
                    for (Product currentProduct : updatedShoppingCart) {
                        boolean found = false;
                        // Check if the product is already in the combined list
                        for (Product combinedProduct : combinedList) {
                            if (currentProduct.getName().equals(combinedProduct.getName()) &&
                                    currentProduct.getStoreName().equals(combinedProduct.getStoreName()) &&
                                    currentProduct.getDescription().equals(combinedProduct.getDescription()) &&
                                    currentProduct.getPrice() == combinedProduct.getPrice()) {
                                // If found, update the quantity
                                combinedProduct.setQuantAvailable(combinedProduct.getQuantAvailable() + currentProduct.getQuantAvailable());
                                found = true;
                                break;
                            }
                        }
                        // If not found, add it to the combined list
                        if (!found) {
                            combinedList.add(new Product(
                                    currentProduct.getName(),
                                    currentProduct.getStoreName(),
                                    currentProduct.getDescription(),
                                    currentProduct.getQuantAvailable(),
                                    currentProduct.getPrice()
                            ));
                        }
                    }
                    customer.setShoppingCar(combinedList);
                }
            }
        }
        return ("Item added!");
    }

    public synchronized static String pricePurchaseItem(ArrayList<Product> priceListedProducts, String username, int quantity, int num) {
        Product productFromSeller = priceListedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedPurchaseHistory = customer.getPurchaseHistory();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    return ("You are attempting to buy more than the limit of "
                            + productFromSeller.getLimit() + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    return ("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to buy above that limit");
                } else {
                    Product productToBuy = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity,
                            productFromSeller.getPrice());
                    productToBuy.setLimit(productFromSeller.getLimit());
                    productToBuy.setReviews(productFromSeller.getReviews());
                    if (updatedPurchaseHistory != null && updatedPurchaseHistory.size() != 0) {
                        if (updatedPurchaseHistory.get(0).getName().equals("N/A")) {
                            updatedPurchaseHistory.set(0, productToBuy);
                        } else {
                            updatedPurchaseHistory.add(productToBuy);
                        }
                    } else {
                        updatedPurchaseHistory.add(productToBuy);
                    }
                    ArrayList<Product> combinedList = new ArrayList<>();
                    for (Product currentProduct : updatedPurchaseHistory) {
                        boolean found = false;
                        // Check if the product is already in the combined list
                        for (Product combinedProduct : combinedList) {
                            if (currentProduct.getName().equals(combinedProduct.getName()) &&
                                    currentProduct.getStoreName().equals(combinedProduct.getStoreName()) &&
                                    currentProduct.getDescription().equals(combinedProduct.getDescription()) &&
                                    currentProduct.getPrice() == combinedProduct.getPrice()) {
                                // If found, update the quantity
                                combinedProduct.setQuantAvailable(combinedProduct.getQuantAvailable() + currentProduct.getQuantAvailable());
                                found = true;
                                break;
                            }
                        }
                        // If not found, add it to the combined list
                        if (!found) {
                            combinedList.add(new Product(
                                    currentProduct.getName(),
                                    currentProduct.getStoreName(),
                                    currentProduct.getDescription(),
                                    currentProduct.getQuantAvailable(),
                                    currentProduct.getPrice()
                            ));
                        }
                    }
                    customer.setPurchaseHistory(combinedList);
                    Store storeToUpdate = null;
                    for (Seller seller : sellers) {
                        ArrayList<Store> stores = seller.getStores();
                        for (Store store : stores) {
                            ArrayList<Product> products = store.getProducts();
                            for (Product product : products) {
                                if (product.getName().equals(productToBuy.getName())) {
                                    storeToUpdate = store;
                                    storeToUpdate.editProduct(product.getName(), 4,
                                            ("" + (productFromSeller.getQuantAvailable() - quantity)));
                                    store = storeToUpdate;
                                }
                            }
                        }
                        seller.setStores(stores);
                    }
                }
            }
        }
        return ("Item purchased");
    }

    public synchronized static String quantityPurchaseItems(ArrayList<Product> quantityListedProducts, String username, int quantity, int num) {
        Product productFromSeller = quantityListedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedPurchaseHistory = customer.getPurchaseHistory();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    return ("You are attempting to buy more than the limit of "
                            + productFromSeller.getLimit() + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    return ("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to buy above that limit");
                } else {
                    Product productToBuy = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity,
                            productFromSeller.getPrice());
                    productToBuy.setLimit(productFromSeller.getLimit());
                    productToBuy.setReviews(productFromSeller.getReviews());
                    if (updatedPurchaseHistory != null && updatedPurchaseHistory.size() != 0) {
                        if (updatedPurchaseHistory.get(0).getName().equals("N/A")) {
                            updatedPurchaseHistory.set(0, productToBuy);
                        } else {
                            updatedPurchaseHistory.add(productToBuy);
                        }
                    } else {
                        updatedPurchaseHistory.add(productToBuy);
                    }
                    ArrayList<Product> combinedList = new ArrayList<>();
                    for (Product currentProduct : updatedPurchaseHistory) {
                        boolean found = false;
                        // Check if the product is already in the combined list
                        for (Product combinedProduct : combinedList) {
                            if (currentProduct.getName().equals(combinedProduct.getName()) &&
                                    currentProduct.getStoreName().equals(combinedProduct.getStoreName()) &&
                                    currentProduct.getDescription().equals(combinedProduct.getDescription()) &&
                                    currentProduct.getPrice() == combinedProduct.getPrice()) {
                                // If found, update the quantity
                                combinedProduct.setQuantAvailable(combinedProduct.getQuantAvailable() + currentProduct.getQuantAvailable());
                                found = true;
                                break;
                            }
                        }
                        // If not found, add it to the combined list
                        if (!found) {
                            combinedList.add(new Product(
                                    currentProduct.getName(),
                                    currentProduct.getStoreName(),
                                    currentProduct.getDescription(),
                                    currentProduct.getQuantAvailable(),
                                    currentProduct.getPrice()
                            ));
                        }
                    }
                    customer.setPurchaseHistory(combinedList);
                    Store storeToUpdate = null;
                    for (Seller seller : sellers) {
                        ArrayList<Store> stores = seller.getStores();
                        for (Store store : stores) {
                            ArrayList<Product> products = store.getProducts();
                            for (Product product : products) {
                                if (product.getName().equals(productToBuy.getName())) {
                                    storeToUpdate = store;
                                    storeToUpdate.editProduct(product.getName(), 4,
                                            ("" + (productFromSeller.getQuantAvailable() - quantity)));
                                    store = storeToUpdate;
                                }
                            }
                        }
                        seller.setStores(stores);
                    }
                }
            }
        }
        return ("Item purchased");
    }
    public synchronized static String purchaseItem(ArrayList<Product> listedProducts, String username, int quantity, int num) {
        Product productFromSeller = listedProducts.get(num - 1);
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> updatedPurchaseHistory = customer.getPurchaseHistory();
                if ((productFromSeller.getLimit() != -1) && (quantity > productFromSeller.getLimit())) {
                    return ("You are attempting to buy more than the limit of "
                            + productFromSeller.getLimit() + " units set by the seller");
                } else if (quantity > productFromSeller.getQuantAvailable()) {
                    return ("There is only " + productFromSeller.getQuantAvailable()
                            + " units left, you are attempting to buy above that limit");
                } else {
                    Product productToBuy = new Product(productFromSeller.getName(),
                            productFromSeller.getStoreName(),
                            productFromSeller.getDescription(),
                            quantity,
                            productFromSeller.getPrice());
                    productToBuy.setLimit(productFromSeller.getLimit());
                    productToBuy.setReviews(productFromSeller.getReviews());
                    if (updatedPurchaseHistory != null && updatedPurchaseHistory.size() != 0) {
                        if (updatedPurchaseHistory.get(0).getName().equals("N/A")) {
                            updatedPurchaseHistory.set(0, productToBuy);
                        } else {
                            updatedPurchaseHistory.add(productToBuy);
                        }
                    } else {
                        updatedPurchaseHistory.add(productToBuy);
                    }
                    ArrayList<Product> combinedList = new ArrayList<>();
                    for (Product currentProduct : updatedPurchaseHistory) {
                        boolean found = false;
                        // Check if the product is already in the combined list
                        for (Product combinedProduct : combinedList) {
                            if (currentProduct.getName().equals(combinedProduct.getName()) &&
                                    currentProduct.getStoreName().equals(combinedProduct.getStoreName()) &&
                                    currentProduct.getDescription().equals(combinedProduct.getDescription()) &&
                                    currentProduct.getPrice() == combinedProduct.getPrice()) {
                                // If found, update the quantity
                                combinedProduct.setQuantAvailable(combinedProduct.getQuantAvailable() + currentProduct.getQuantAvailable());
                                found = true;
                                break;
                            }
                        }
                        // If not found, add it to the combined list
                        if (!found) {
                            combinedList.add(new Product(
                                    currentProduct.getName(),
                                    currentProduct.getStoreName(),
                                    currentProduct.getDescription(),
                                    currentProduct.getQuantAvailable(),
                                    currentProduct.getPrice()
                            ));
                        }
                    }
                    customer.setPurchaseHistory(combinedList);
                    Store storeToUpdate = null;
                    for (Seller seller : sellers) {
                        ArrayList<Store> stores = seller.getStores();
                        for (Store store : stores) {
                            ArrayList<Product> products = store.getProducts();
                            for (Product product : products) {
                                if (product.getName().equals(productToBuy.getName())) {
                                    storeToUpdate = store;
                                    storeToUpdate.editProduct(product.getName(), 4,
                                            ("" + (productFromSeller.getQuantAvailable() - quantity)));
                                    store = storeToUpdate;
                                }
                            }
                        }
                        seller.setStores(stores);
                    }
                }
            }
        }
        return ("Item purchased");
    }
    public synchronized static String addReviewPrice(ArrayList<Product> priceListedProducts, String review, int num) {
        Product productToReview = priceListedProducts.get(num - 1);
        ArrayList<String> updatedReviews = productToReview.getReviews();
        updatedReviews.add(review);
        productToReview.setReviews(updatedReviews);

        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                for (Product product : store.getProducts()) {
                    if (product.getName().equals(productToReview.getName()) &&
                            product.getStoreName().equals(productToReview.getStoreName())) {
                        product.setReviews(productToReview.getReviews());
                    }
                }
            }
        }
        return "Review added";
    }

    public static String addReviewQuantity(ArrayList<Product> quantityListedProducts, String review, int num) {
        Product productToReview = quantityListedProducts.get(num - 1);
        ArrayList<String> updatedReviews = productToReview.getReviews();
        updatedReviews.add(review);
        productToReview.setReviews(updatedReviews);

        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                for ( Product product : store.getProducts()) {
                    if (product.getName().equals(productToReview.getName()) &&
                            product.getStoreName().equals(productToReview.getStoreName())) {
                        product.setReviews(productToReview.getReviews());
                    }
                }
            }
        }

        return "Review added";
    }
    public synchronized static String addReview(ArrayList<Product> listedProducts, String review, int num) {
        Product productToReview = listedProducts.get(num - 1);
        ArrayList<String> updatedReviews = productToReview.getReviews();
        updatedReviews.add(review);
        productToReview.setReviews(updatedReviews);

        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                for ( Product product : store.getProducts()) {
                    if (product.getName().equals(productToReview.getName()) &&
                            product.getStoreName().equals(productToReview.getStoreName())) {
                        product.setReviews(productToReview.getReviews());
                    }
                }
            }
        }

        return "Review added";
    }

    public static ArrayList<Product> getCustomerShoppingCart(String username) {
        int customerIndex = 0;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerUserName().equals(username)) {
                customerIndex = i;
                break;
            }
        }
        ArrayList<Product> shoppingCartResult = new ArrayList<>();
        if (customers.get(customerIndex).getShoppingCar().size() == 0 || customers.get(customerIndex).getShoppingCar().get(0).getName().equals("N/A")) {
            return null;
        } else {
            for (Product product : customers.get(customerIndex).getShoppingCar()) {
                shoppingCartResult.add(product);
            }
        }
        return shoppingCartResult;
    }

    public synchronized static String removeItem(String username, ArrayList<Product> shoppingCart, int num) {
        int customerIndex = 0;
        Product productToRemove = shoppingCart.get(num-1);
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerUserName().equals(username)) {
                customerIndex = i;
                break;
            }
        }
        for (Product product : shoppingCart) {
            if (product.equals(productToRemove)) {
                shoppingCart.remove(product);
                break;
            }
        }
        customers.get(customerIndex).setShoppingCar(shoppingCart);
        return "Removed";
    }

    public synchronized static String purchaseItemALL(String username) {
        ArrayList<Product> updatedShoppingCart = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equalsIgnoreCase(username)) {
                updatedShoppingCart = customer.getShoppingCar();
                customer.setShoppingCar(new ArrayList<>());
                Store storeToUpdate;
                int index = 1;
                for (Product product : updatedShoppingCart) {
                    for (Seller seller : sellers) {
                        ArrayList<Store> stores = seller.getStores();
                        for (Store store : stores) {
                            ArrayList<Product> products = store.getProducts();
                            for (Product productFromSeller : products) {
                                if (product.getName().equals(productFromSeller.getName()) &&
                                        product.getStoreName().equals(productFromSeller.getStoreName())) {
                                    storeToUpdate = store;
                                    if (productFromSeller.getQuantAvailable() - product.getQuantAvailable() < 0 ) {
                                        return ("Not enough stocks for item number " + index + "\nHave only " +
                                                productFromSeller.getQuantAvailable() +
                                                " left, but your order requires " + product.getQuantAvailable()
                                                + "\n return to shopping cart and remove this item");
                                    } else {
                                        storeToUpdate.editProduct(product.getName(), 4, ("" + (productFromSeller.getQuantAvailable() - product.getQuantAvailable())));
                                        store = storeToUpdate;
                                        ArrayList<Product> updatedPurchaseHist = customer.getPurchaseHistory();
                                        updatedPurchaseHist.add(product);
                                        customer.setPurchaseHistory(updatedPurchaseHist);
                                    }
                                }
                                index++;
                            }
                        }
                        seller.setStores(stores);
                    }
                }
                customer.setShoppingCar(new ArrayList<>());
            }
        }
        return "All Items Purchased, Your Shopping Cart Is Now Empty";
    }

    public static ArrayList<Product> customerViewHistory(String username) {
        int customerIndex = 0;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerUserName().equals(username)) {
                customerIndex = i;
                break;
            }
        }
        Customer historyCustomer = customers.get(customerIndex);
        if (historyCustomer.getPurchaseHistory().size() == 0 || historyCustomer.getPurchaseHistory().get(0).getName().equals("N/A")) {
            return null;
        }
        return historyCustomer.getPurchaseHistory();
    }
    public static String exportPurchaseHistory(ArrayList<Product> purchaseHistory, String fileName, String username) {
        if (fileName.contains(" ")) {
            fileName = fileName.replace(" ", "_");
        }
        if (fileName.contains(".")) {
            fileName = fileName.replace(".", "~");
            fileName = fileName.split("~")[0];
        }
        try (FileWriter fw = new FileWriter(fileName + ".txt")) {
            // write header
            fw.append(username + "'s " + "Purchase history:\n");
            fw.append("Name/Description/Price/Quantity Bought\n");

            //data
            for (Product product : purchaseHistory) {
                fw.append(product.getName());
                fw.append("/");
                fw.append(product.getDescription());
                fw.append("/");
                fw.append(String.valueOf(product.getPrice()));
                fw.append("/");
                fw.append(String.valueOf(product.getQuantAvailable()));
                fw.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ("Export successful!");
    }

    public static String dashboardCheckSell(boolean yesSort, String sortBy) {
        ArrayList<Product> allProducts = new ArrayList<>();
        for (Customer customer : customers) {
            ArrayList<Product> relativeHistory = customer.purchaseHistory;
            if (!(relativeHistory.get(0).getName().equals("N/A"))) {
                for (Product product : relativeHistory) {
                    allProducts.add(product);
                }
            }
        }
        ArrayList<String> salesByStore = new ArrayList<>();
        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                String storeName = store.getName();
                int storeSales = 0;
                for (Product product : allProducts) {
                    if (storeName.equals(product.getStoreName())) {
                        storeSales += product.getQuantAvailable();
                    }
                }
                salesByStore.add(storeName + ": " + storeSales);
            }
        }
        if (yesSort) {
            if (sortBy.equals("Low to High")) {
                int n = salesByStore.size();
                for (int i = 0; i < n - 1; i++) {
                    for (int j = 0; j < n - i - 1; j++) {
                        int sales1 = Integer.parseInt(salesByStore.get(j).split(": ")[1]);
                        int sales2 = Integer.parseInt(salesByStore.get(j + 1).split(": ")[1]);
                        if (sales1 > sales2) {
                            // Swap if the current entry has greater sales than the next entry
                            String temp = salesByStore.get(j);
                            salesByStore.set(j, salesByStore.get(j + 1));
                            salesByStore.set(j + 1, temp);
                        }
                    }
                }
            } else if (sortBy.equals("High to Low")) {
                int n = salesByStore.size();
                for (int i = 0; i < n - 1; i++) {
                    for (int j = 0; j < n - i - 1; j++) {
                        int sales1 = Integer.parseInt(salesByStore.get(j).split(": ")[1]);
                        int sales2 = Integer.parseInt(salesByStore.get(j + 1).split(": ")[1]);
                        if (sales1 < sales2) {
                            // Swap if the current entry has greater sales than the next entry
                            String temp = salesByStore.get(j);
                            salesByStore.set(j, salesByStore.get(j + 1));
                            salesByStore.set(j + 1, temp);
                        }
                    }
                }
            }
        }
        String output = "";
        for (String store : salesByStore) {
            output += store + "...";
        }
        return output;
    }

    public static String dashBoardCheckCust(boolean yesSort, String username, String sortBy) {
        ArrayList<Product> customerPurchasedProducts = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getCustomerUserName().equals(username)) {
                ArrayList<Product> relativeHistory = customer.purchaseHistory;
                for (Product product : relativeHistory) {
                    customerPurchasedProducts.add(product);
                }
            }
        }
        ArrayList<String> salesByStore = new ArrayList<>();
        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                String storeName = store.getName();
                int storeSales = 0;
                for (Product product : customerPurchasedProducts) {
                    if (storeName.equals(product.getStoreName())) {
                        storeSales += product.getQuantAvailable();
                    }
                }
                salesByStore.add(storeName + ": " + storeSales);
            }
        }
        if (yesSort) {
            if (sortBy.equals("Low to High")) {
                int n = salesByStore.size();
                for (int i = 0; i < n - 1; i++) {
                    for (int j = 0; j < n - i - 1; j++) {
                        int sales1 = Integer.parseInt(salesByStore.get(j).split(": ")[1]);
                        int sales2 = Integer.parseInt(salesByStore.get(j + 1).split(": ")[1]);
                        if (sales1 > sales2) {
                            // Swap if the current entry has greater sales than the next entry
                            String temp = salesByStore.get(j);
                            salesByStore.set(j, salesByStore.get(j + 1));
                            salesByStore.set(j + 1, temp);
                        }
                    }
                }
            } else if (sortBy.equals("High to Low")){
                int n = salesByStore.size();
                for (int i = 0; i < n - 1; i++) {
                    for (int j = 0; j < n - i - 1; j++) {
                        int sales1 = Integer.parseInt(salesByStore.get(j).split(": ")[1]);
                        int sales2 = Integer.parseInt(salesByStore.get(j + 1).split(": ")[1]);
                        if (sales1 < sales2) {
                            // Swap if the current entry has greater sales than the next entry
                            String temp = salesByStore.get(j);
                            salesByStore.set(j, salesByStore.get(j + 1));
                            salesByStore.set(j + 1, temp);
                        }
                    }
                }
            }
        }
        String output = "";
        for (String store : salesByStore) {
            output += store + "...";
        }
        return output;
    }


    public synchronized static void writeDataSeller() {
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
    public synchronized static ArrayList<Seller> readDataSeller() {
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
        MarketplaceServer.sellers = sellersTemp;
        return sellersTemp;
    }
    public synchronized static void writeDataCustomer(){
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
                    if (!(prodShop.get(k).getReviews() == null)) {
                        for (int o = 0; o < prodShop.get(k).getReviews().size(); o++ ) {
                            reviews.add(prodShop.get(k).getReviews().get(o));
                        }
                    }
                    else {
                        reviews.add("N/A");
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
                if (prodShop.size() == 0) {
                    pw.write("N/A/-N/A/-N/A/-0/-0.0/-0/-\n");
                    pw.write("change/-value\n");
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
                    if (prodHistory.get(k).getReviews() == null) {
                        reviews.add("N/A");
                    } else {
                        for (int o = 0; o < prodHistory.get(k).getReviews().size(); o++ ) {
                            reviews.add(prodHistory.get(k).getReviews().get(o));
                        }
                    }
                    // tri,tri's store,sales,milk,taro flavour,10,5.4,5,review1,review2
                    String ans = String.format("%s/-%s/-%s/-%s/-%s/-%s/-",
                            productName, storeName, productDescription,
                            productQuant, productPrice, productLimit);
                    pw.write(ans);
                    for (int l = 0; l < reviews.size(); l++ ) {
                        pw.write(reviews.get(l) + "/-");
                    }
                    if (k == (prodHistory.size()-1) && i == (customersData.size()-1)) {
                        pw.write("");
                    } else {
                        pw.write("\n");
                    }
                }
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized static ArrayList<Customer> readDataCustomer() {
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
        MarketplaceServer.customers = customerTemp;
        return customerTemp;
    }

}
