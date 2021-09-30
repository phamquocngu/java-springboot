package io.marklove.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.username}")
    private String userName;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${endpoint}")
    private String endpoint;
    @Value("${destination.prefix}")
    private String destinationPrefix;
    @Value("${stomp.broker.relay}")
    private String stompBrokerRelay;

    @Autowired
    private HttpHandshakeInterceptor httpHandshakeInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .withSockJS()
                .setInterceptors(httpHandshakeInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enables a simple in-memory broker
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic");
        // Using message broker
//        config.enableStompBrokerRelay(stompBrokerRelay)
//                .setRelayHost(host)
//                .setRelayPort(port)
//                .setSystemLogin(userName)
//                .setSystemPasscode(password);
    }
}
