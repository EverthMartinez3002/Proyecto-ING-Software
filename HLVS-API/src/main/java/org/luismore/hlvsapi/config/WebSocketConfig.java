package org.luismore.hlvsapi.config;

import org.luismore.hlvsapi.websocket.ServoWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(servoWebSocketHandler(), "/servo");
    }

    @Bean
    public ServoWebSocketHandler servoWebSocketHandler() {
        return new ServoWebSocketHandler();
    }
}
