// purspose: call this class when we need to generate a dataset when the program is run for the first time
public class InitialDataset extends main {
    public ArrayList<Seller> createSeller() {
        Product product1 = new Product("coffe", "Store1", "sucked", 10, 5.0);
        Product product2 = new Product("tea", "Store1", "sucked too", 5, 5.0);
        Product product3 = new Product("macbook", "Store2", "get a windowsoft", 5, 10.0);
        Product product4 = new Product("airpod", "Store2", "sucked", 10, 5.0);
        Product product5 = new Product("paper", "Store3", "sucked too", 5, 5.0);
        Product product6 = new Product("piano", "Store3", "get a windowsoft", 5, 10.0);

        ArrayList<Product> productList1 = new ArrayList<>();
        productList1.add(product1); productList1.add(product2);
        ArrayList<Product> productList2 = new ArrayList<>();
        productList2.add(product3); productList2.add(product4);
        ArrayList<Product> productList3 = new ArrayList<>();
        productList3.add(product5); productList3.add(product6);

        Store store1 = new Store(productList1, "Store1");
        Store store2 = new Store(productList2, "Store2");
        Store store3 = new Store(productList3, "Store3");

        ArrayList <Store> storeList1 = new ArrayList<>();
        storeList1.add(store1); storeList1.add(store2);
        ArrayList <Store> storeList2 = new ArrayList<>();
        storeList1.add(store3);

        Seller seller1 = new Seller(storeList1, "tri");
        Seller seller2 = new Seller(storeList2, "rohan");

        ArrayList<Seller> listSeller = null;
        listSeller.add(seller1);
        listSeller.add(seller2);

        return listSeller;
    }
    public void createCustomer() {

    }
}
