package productCart;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Cart cart = context.getBean("cart", Cart.class);
        cart.setRepository(context.getBean("productRepository", ProductRepository.class));
        cart.initializeCart();

        Scanner scanner = new Scanner(System.in);

        int menuPointer = -1;
        while (menuPointer != 0) {
            System.out.println("\nChose action:"
                    +"\n\t1.show cart"
                    +"\n\t2.add product"
                    +"\n\t3.delete product"
                    +"\n\t0.exit");
            menuPointer = scanner.nextInt();

            switch (menuPointer) {
                case 1:
                    cart.printCart();
                    break;
                case 2:
                    int ID = -1;
                    String title = "";
                    double price = -1.0d;

                    System.out.println("Input product ID: ");
                    if (scanner.hasNextInt()) {
                        ID = scanner.nextInt();
                        scanner.nextLine();
                    }
                    System.out.println("Input product title: ");
                    if (scanner.hasNextLine()) {
                        title = scanner.nextLine();
                    }
                    System.out.println("Input price: ");
                    if (scanner.hasNextDouble()) {
                        price = scanner.nextDouble();
                    }

                    if (ID != -1 && !title.equals("") && price != -1.0d) {
                        Product product = context.getBean("product", Product.class);
                        product.setId(ID);
                        product.setTitle(title);
                        product.setPrice(price);

                        cart.addProduct(product);
                        System.out.println("Product added!");
                    } else {
                        System.out.println("Wrong values!");
                    }
                    break;
                case 3:
                    System.out.println("Input product ID: ");
                    int IDtoDel = scanner.nextInt();
                    if (cart.deleteProductByID(IDtoDel)) {
                        System.out.println("Product deleted!");
                    } else {
                        System.out.println("There is no product with such ID!");
                    }
                    break;
                default:
                    break;
            }
            scanner.nextLine();
        }

        scanner.close();
        ((AnnotationConfigApplicationContext) context).close();
    }
}
