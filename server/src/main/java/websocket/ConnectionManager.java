package websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private final Map<Integer, Session> connections = new ConcurrentHashMap<>();

    public void add(Integer gameID, Session session) {
        connections.put(gameID, session);
    }

    public void remove(Integer gameID) {
        connections.remove(gameID);
    }

    public void remove(Session session) {
        connections.values().remove(session);
    }

    public void broadcast(Integer gameID, String message) {
        for (Map.Entry<Integer, Session> entry : connections.entrySet()) {
            if (entry.getKey().equals(gameID)) {
                try {
                    // Create a ServerMessage with NOTIFICATION type
                    ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
                    serverMessage.setMessage(message);

                    // Convert the ServerMessage to JSON
                    String jsonMessage = new Gson().toJson(serverMessage);

                    // Send the JSON message to the client
                    System.err.println("sending broadcast: " + message); // testing
                    entry.getValue().getRemote().sendString(jsonMessage);
                } catch (IOException e) {
                    System.err.println("Error broadcasting message: " + e.getMessage());
                }
            }
        }
    }
}
