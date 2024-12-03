package websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private final Map<String, Session> connections = new ConcurrentHashMap<>();

    public void add(String username, Session session) {
        connections.put(username, session);
    }

    public void remove(String username) {
        connections.remove(username);
    }

    public void remove(Session session) {
        connections.values().remove(session);
    }

    public void broadcast(String excludeUsername, String message) {
        for (Map.Entry<String, Session> entry : connections.entrySet()) {
            if (!entry.getKey().equals(excludeUsername)) {
                try {
                    entry.getValue().getRemote().sendString(message);
                } catch (IOException e) {
                    System.err.println("Error broadcasting message: " + e.getMessage());
                }
            }
        }
    }
}
