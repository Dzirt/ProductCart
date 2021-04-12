package server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("server")
@PropertySource("classpath:server\\application.properties")
public class AppConfig {

    @Value("${port}")
    private int PORT;

    @Bean
    public ChatServer chatServer() {
        return new ChatServer(PORT);
    }

    @Bean
    public ChatServerInitializer chatServerInitializer() {
        return new ChatServerInitializer();
    }

    @Bean
    public ChatServerHandler chatServerHandler() {
        return new ChatServerHandler();
    }

}
