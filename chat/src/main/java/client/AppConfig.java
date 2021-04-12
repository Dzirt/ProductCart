package client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan("client")
@PropertySource("classpath:client\\application.properties")
public class AppConfig {
    @Value("${port}")
    private int PORT;

    @Value("${host}")
    private String HOST;

    @Value("${width}")
    private int WIDTH;

    @Value("${height}")
    private int HEIGHT;

    @Bean
    public ChatClient chatClient() throws Exception {
        ChatClient client = new ChatClient(HOST, PORT);
        client.setClientSize(WIDTH, HEIGHT);
        return client;
    }

    @Bean
    public ChatClientHandler chatClientHandler() {
        return new ChatClientHandler();
    }

    @Bean ChatClientInitializer chatClientInitializer() {
        return new ChatClientInitializer();
    }
}
