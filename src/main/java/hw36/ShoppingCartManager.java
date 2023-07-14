package hw36;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
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

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ShoppingCartManager manager = context.getBean(ShoppingCartManager.class);
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            LOGGER.info("1. Show all products");
            LOGGER.info("2. Add product to cart");
            LOGGER.info("3. Remove product from cart");
            LOGGER.info("4. Show cart items");
            LOGGER.info("0. Exit");
            LOGGER.info("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> manager.showAllProducts ();
                case 2 -> {
                    LOGGER.info ("Enter the product ID to add: ");
                    int addProductId = scanner.nextInt ();
                    manager.addToCart (addProductId);
                }
                case 3 -> {
                    LOGGER.info ("Enter the product ID to remove: ");
                    int removeProductId = scanner.nextInt ();
                    manager.removeFromCart (removeProductId);
                }
                case 4 -> manager.showCartItems ();
                case 0 -> LOGGER.info ("Exiting...");
                default -> LOGGER.info ("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
        context.close();
    }
}
