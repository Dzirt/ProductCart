package productCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Cart {

    private ProductRepository repository;

    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    public void initializeCart() {
        for (int i = 0; i < 5; i++) {
            repository.addRandom();
        }
    }

    public Product getProductByID(int id) {
        return repository.getProductByID(id);
    }

    public boolean deleteProductByID(int id) {
        Product product = repository.getProductByID(id);
        if (product != null) {
            repository.deleteProduct(product);
            return true;
        } else {
            return false;
        }
    }

    public void addProduct(Product product) {
        repository.add(product);
    }

    public void printCart() {
        System.out.println(repository.toString());
    }

}
