package websocket;

import com.google.gson.Gson;
import model.GameModel;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager {
    private static ConnectionManager instance; // Singleton instance

    private final Map<Integer, List<Session>> connections = new HashMap<>();

    // Private constructor to prevent instantiation
    private ConnectionManager() {
    }

    // Public method to get the singleton instance
    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

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
    public void broadcast(Integer gameID, String message, Session ignoreSession) {
        List<Session> sessions = connections.get(gameID);
        if (sessions != null) {
            for (Session session : sessions) {
                try {
                    if (session.equals(ignoreSession)) {
                        continue; // Skip this session
                    }

                    ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
                    serverMessage.setMessage(message);

                    String jsonMessage = new Gson().toJson(serverMessage);

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

    public void broadcastLoadGame(Integer gameID, GameModel gameModel, Session ignoreSession) {
        List<Session> sessions = connections.get(gameID);
        if (sessions != null) {
            for (Session session : sessions) {
                try {
                    if (session.equals(ignoreSession)) {
                        continue; // Skip this session
                    }

                    ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);

                    String gameStateJson = new Gson().toJson(gameModel);
                    serverMessage.setGame(gameStateJson);

                    String responseJson = new Gson().toJson(serverMessage);

                    session.getRemote().sendString(responseJson);
                } catch (IOException e) {
                    System.err.println("Error broadcasting message: " + e.getMessage());
                }
            }
        } else {
            System.err.println("No sessions found for gameID " + gameID);
        }
    }
}
