import java.util.ArrayList;

public class Seller {

    ArrayList<Store> stores = new ArrayList();

    public Seller(ArrayList<Store> stores) {
        this.stores = stores;
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

    public void listProducts() {
        System.out.println("The available products for the store are:");
        for(int i = 0; i < this.stores.size(); i++) {
            System.out.println("Store: " + this.stores.get(i));
            System.out.println("Products of store:\t\tPrice");
            for(int j = 0; j < this.stores.get(i).product.size(); j++) {
                System.out.print(this.stores.get(i).product.get(i) + "\t\t");
                System.out.println(this.stores.get(i).product.get(i).getPrice());
            }
        }
    }
}
