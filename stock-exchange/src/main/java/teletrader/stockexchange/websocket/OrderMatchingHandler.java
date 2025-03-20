package teletrader.stockexchange.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import teletrader.stockexchange.util.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class OrderMatchingHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    @Autowired
    private JwtService jwtService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String email = extractUserId(session);
        if (email != null) {
            userSessions.put(email, session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        userSessions.values().remove(session);
    }

    public void notifyUser(String userEmail, String message) {
        WebSocketSession session = userSessions.get(userEmail);
        if (session != null && session.isOpen()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonMessage = objectMapper.writeValueAsString(Map.of("message", message));
                session.sendMessage(new TextMessage(jsonMessage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Extract user email from auth token
    private String extractUserId(WebSocketSession session) {
        String token = session.getHandshakeHeaders().get("Sec-WebSocket-Protocol").get(0);
        return jwtService.extractUsername(token);
    }
}
