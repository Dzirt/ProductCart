package productCart;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class ProductRepository {
    private List<Product> productList;

    public ProductRepository() {
        productList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Product getProductByID(int id) {
        for (Product p : productList) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    public void deleteProductByID(int id) {
        productList.remove(getProductByID(id));
    }
    public void deleteProduct(Product product) {
        productList.remove(product);
    }

    public void addRandom() {
        productList.add(new Product((int) (Math.random() * 100), "test"+productList.size(), (int) (Math.random() * 1000)));
    }

    public void add(Product product) {
        productList.add(product);
    }

    @Override
    public String toString() {
        return "\nProductList={" + productList +
                '}';
    }
}
