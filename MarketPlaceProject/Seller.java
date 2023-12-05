import java.io.*;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Project 4 - Seller
 *
 * This class represents a single seller.
 */

public class Seller extends main {
    private String userName;
    ArrayList<Store> stores = new ArrayList();

    //tri: used to create new Seller in main class
    public Seller(ArrayList<Store> stores, String username) {
        this.stores = stores;
        this.userName = username;
    }
    //
    public String getUserName() { return this.userName; }
    public void setUserName(String name) {
        this.userName = name;
    }
    public ArrayList<Store> getStores() {
        return this.stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public void createStore(Store store) {
        this.stores.add(store);
    }

    public void view() {
        System.out.println("List of stores:");
        for(int i = 0; i < stores.size(); i++) {
            System.out.println(stores.get(i));
            System.out.println("Products\tQuantity available");
            for(int j = 0; j < stores.get(i).getProducts().size(); j++) {
                stores.get(i).listProducts();
            }
            System.out.println("Revenue of store: " + stores.get(i).getSales());
        }
    }

    public void saveToFileProduct(Product product) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SellerProductDetails.txt"))) {
            String toFile = product.getName() + "," + product.getStoreName() + "," + product.getDescription() + "," +
                    product.getQuantAvailable() + "," + product.getPrice();
            writer.write(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> loadFromFileProduct(String fileName) {
        ArrayList<String> productData = new ArrayList<>();
        ArrayList<String> sellerInfo = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)) ){
            String line;
            while ((line = reader.readLine()) != null) {
                productData.add(line);
            }
            if(productData == null || productData.isEmpty()) {
                System.out.println("No data in file.");
                return productData;
            } else {
                PrintWriter pwr = new PrintWriter(new FileOutputStream("SellerInfo.txt", true), true);
                for (int i = 1; i < productData.size(); i++) {
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
            return productData;

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //System.out.println("User data loaded from " + "filler.txt");
    }
}
