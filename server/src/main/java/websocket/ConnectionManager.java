package websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private final Map<Integer, List<Session>> connections = new HashMap<>();

    // Add a session for a specific game ID
    public void add(Integer gameID, Session session) {
        connections.computeIfAbsent(gameID, k -> new ArrayList<>()).add(session);
    }

    // Remove a session for a specific game ID
    public void remove(Session session) {
        connections.values().forEach(list -> list.remove(session));
        connections.entrySet().removeIf(entry -> entry.getValue().isEmpty()); // Cleanup empty lists
    }

    // Broadcast a message to all sessions associated with a game ID
    public void broadcast(Integer gameID, String message) {
        List<Session> sessions = connections.get(gameID);
        if (sessions != null) {
            for (Session session : sessions) {
                try {
                    // Create a ServerMessage with NOTIFICATION type
                    ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
                    serverMessage.setMessage(message);

                    // Convert the ServerMessage to JSON
                    String jsonMessage = new Gson().toJson(serverMessage);

                    // Send the JSON message to the client
                    System.err.println("sending broadcast to gameID " + gameID + ": " + message); // Testing
                    session.getRemote().sendString(jsonMessage);
                } catch (IOException e) {
                    System.err.println("Error broadcasting message: " + e.getMessage());
                }
            }
        } else {
            System.err.println("No sessions found for gameID " + gameID);
        }
    }
}
