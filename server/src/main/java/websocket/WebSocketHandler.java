package websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.MySqlDataAccess;
import model.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import requests.WebSocketRequest;
import server.Server;
import websocket.commands.*;
import websocket.messages.ServerMessage;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final Gson gson = new Gson();
    private final CommandDispatcher commandDispatcher;

    public WebSocketHandler() {
        this.commandDispatcher = new CommandDispatcher();
        initializeCommands();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WebSocket connection established: " + session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        try {
            // Parse incoming JSON message
            WebSocketRequest request = gson.fromJson(message, WebSocketRequest.class);

            // Extract command type and parameters
            String commandType = request.getType();
            Object[] params = extractParams(request, session);

            // Dispatch the command
            GameCommand command = commandDispatcher.getCommand(commandType, params);
            command.execute();
        } catch (Exception e) {
            System.err.println("Error handling message: " + e.getMessage());
            e.printStackTrace();
            sendErrorResponse(session, e.getMessage());
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

    private void handleError(Session session, String errorMessage) {
        // Construct the server message with ERROR type
        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
        serverMessage.setPayload(errorMessage);

        // Convert the server message to JSON
        String responseJson = gson.toJson(serverMessage);

        // Send the JSON response back to the client
        try {
            session.getRemote().sendString(responseJson);
        } catch (IOException e) {
            System.err.println("Error sending error response: " + e.getMessage());
        }
    }

    private void initializeCommands() {
        // Register all commands with their corresponding factories
        commandDispatcher.registerCommand("MAKE_MOVE", new MakeMoveCommandFactory());
        commandDispatcher.registerCommand("RESIGN", new ResignCommandFactory());
        // Additional commands can be registered here
    }

    private Object[] extractParams(WebSocketRequest request, Session session) {
        switch (request.getType()) {
            case "MAKE_MOVE":
                GameModel gameModel = fetchGameModel(request.getGameId());
                ChessMove move = gson.fromJson(request.getMove(), ChessMove.class);
                return new Object[]{gameModel, move};

            case "RESIGN":
                GameModel resignGameModel = fetchGameModel(request.getGameId());
                return new Object[]{resignGameModel};

            default:
                throw new IllegalArgumentException("Unknown command type: " + request.getType());
        }
    }

    private GameModel fetchGameModel(String gameId) {
        // Convert the gameId to an integer, assuming it's a valid ID
        int gameID = Integer.parseInt(gameId);

        // Implement game retrieval logic from your data access layer
        GameModel gameModel = null;
        try {
            // Assuming you have an instance of MySqlDataAccess to access the database
            MySqlDataAccess dataAccess = new MySqlDataAccess();
            gameModel = dataAccess.readGame(gameID);
        } catch (DataAccessException e) {
            throw new IllegalArgumentException("Game not found for ID: " + gameId);
        }

        if (gameModel == null) {
            throw new IllegalArgumentException("Game not found for ID: " + gameId);
        }
        return gameModel;
    }

    private void sendErrorResponse(Session session, String error) throws IOException {
        // Detailed error message
        String detailedMessage = "An error occurred while processing the request: " + error;

        // Create the WebSocketResponse to send to the client
        WebSocketResponse response = new WebSocketResponse("ERROR", detailedMessage);

        // Convert the response to JSON
        String errorMessage = gson.toJson(response);

        // Send the error message back to the client
        session.getRemote().sendString(errorMessage);
    }
}
