package teletrader.stockexchange.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Component
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final OrderMatchingHandler orderMatchingHandler;

    public WebSocketConfig(OrderMatchingHandler handler) {
        this.orderMatchingHandler = handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.orderMatchingHandler, "/order-matching")
                .setAllowedOrigins("*");
    }

    @Bean
    public OrderMatchingHandler handler() {
        return new OrderMatchingHandler();
    }

}
