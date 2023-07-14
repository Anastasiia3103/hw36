package hw36;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AppConfig {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Cart cart;

    @Bean
    public ShoppingCartManager shoppingCartManager() {
        return new ShoppingCartManager();
    }

    @Bean
    public ProductRepository productRepository() {
        return new InMemoryProductRepository();
    }

    @Bean
    public Cart cart() {
        return new Cart();
    }
}

