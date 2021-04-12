package client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientApp {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        ChatClient chatClient = applicationContext.getBean("chatClient", ChatClient.class);
        chatClient.runClient();
    }
}
