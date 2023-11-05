import java.util.ArrayList;

public class Store extends Product{

    String name;
    ArrayList<Product> product;
    public Store(ArrayList<Product> product, String name) {
        super();
        this.name = name;
        this.product = product;
    }
}
