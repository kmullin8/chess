package websocket;

import com.google.gson.Gson;
import model.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import requests.*;
import services.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final Gson gson = new Gson();

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WebSocket connection established: " + session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        // Parse incoming message
        WebSocketRequest request = gson.fromJson(message, WebSocketRequest.class);

        switch (request.getType()) {
            case "ENTER":
                handleEnter(session, request);
                break;
            case "MOVE":
                handleMove(request);
                break;
            case "EXIT":
                handleExit(session, request);
                break;
            default:
                System.err.println("Unknown WebSocket message type: " + request.getType());
        }
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        System.out.println("WebSocket connection closed: " + session);
        connections.remove(session);
    }

    @OnWebSocketError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + throwable.getMessage());
    }

    private void handleEnter(Session session, WebSocketRequest request) {
        String username = request.getUsername();
        connections.add(username, session);
        System.out.println(username + " entered the game.");
        connections.broadcast(username, "Player " + username + " joined the game.");
    }

    private void handleMove(WebSocketRequest request) {
        // Broadcast the move to other players
        String move = request.getMove();
        System.out.println("Broadcasting move: " + move);
        connections.broadcast("", "Player made a move: " + move);
    }

    private void handleExit(Session session, WebSocketRequest request) {
        String username = request.getUsername();
        connections.remove(username);
        System.out.println(username + " exited the game.");
        connections.broadcast(username, "Player " + username + " left the game.");
    }
}
