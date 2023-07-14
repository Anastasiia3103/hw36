package hw36;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ShoppingCartManager {
    private static final Logger LOGGER = Logger.getLogger(ShoppingCartManager.class.getName());

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public Cart cart;

    public void showAllProducts() {
        LOGGER.info("Available Products:");
        List<Product> products = productRepository.getAllProducts();
        for (Product product : products) {
            LOGGER.info("ID: " + product.getId() + ", Name: " + product.getName() + ", Price: $" + product.getPrice());
        }
    }

    public void addToCart(int productId) {
        Product product = productRepository.getProductById(productId);
        if (product != null) {
            cart.addProduct(product);
            LOGGER.info("Product added to cart: " + product.getName());
        } else {
            LOGGER.info("Product not found with ID: " + productId);
        }
    }

    public void removeFromCart(int productId) {
        Product product = productRepository.getProductById(productId);
        if (product != null) {
            cart.removeProduct(product);
            LOGGER.info("Product removed from cart: " + product.getName());
        } else {
            LOGGER.info("Product not found with ID: " + productId);
        }
    }

    public void showCartItems() {
        List<Product> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            LOGGER.info("The cart is empty.");
        } else {
            LOGGER.info("Cart Items:");
            for (Product product : cartItems) {
                LOGGER.info("ID: " + product.getId() + ", Name: " + product.getName() + ", Price: $" + product.getPrice());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ShoppingCartManager manager = context.getBean(ShoppingCartManager.class);

        BufferedReader reader = new BufferedReader(new InputStreamReader (System.in));
        int choice = 0;

        do {
            System.out.println("1. Show all products");
            System.out.println("2. Add product to cart");
            System.out.println("3. Remove product from cart");
            System.out.println("4. Show cart items");
            System.out.println("0. Exit");
            System.out.println("Enter your choice: ");
            choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1 -> manager.showAllProducts ();
                case 2 -> {
                    System.out.println ("Enter the product ID to add: ");
                    int addProductId = Integer.parseInt (reader.readLine ());
                    manager.addToCart (addProductId);
                }
                case 3 -> {
                    System.out.println ("Enter the product ID to remove: ");
                    int removeProductId = Integer.parseInt (reader.readLine ());
                    manager.removeFromCart (removeProductId);
                }
                case 4 -> manager.showCartItems ();
                case 0 -> System.out.println ("Exiting...");
                default -> System.out.println ("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        reader.close();
        context.close();
    }
}
