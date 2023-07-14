package hw36;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryProductRepository implements ProductRepository {
    private List<Product> products;

    public InMemoryProductRepository() {
        products = new ArrayList<> ();
        // Initialize with sample data
        products.add(new Product(1, "Product 1", 10.99));
        products.add(new Product(2, "Product 2", 19.99));
        products.add(new Product(3, "Product 3", 5.99));
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
