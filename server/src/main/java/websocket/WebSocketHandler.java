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
            // Check if it's a CONNECT command
            if (message.contains("CONNECT")) {
                System.out.println("Handling CONNECT command, WebSocketHandler");

                // Perform the necessary logic to handle the CONNECT command, e.g., associate the user with the game
                UserGameCommand connectCommand = gson.fromJson(message, UserGameCommand.class);

                handleConnect(session, connectCommand);

                //extract nessesary data from connect command and send loadGameCommand
                String gameID = String.valueOf(connectCommand.getGameID());
                GameModel gameModel = fetchGameModel(gameID);
                sendLoadGame(session, gameModel);


                return; // Exit after handling the connect command
            }

            // Parse incoming JSON message for other commands
            WebSocketRequest request = gson.fromJson(message, WebSocketRequest.class);

            if (request == null) {
                throw new IllegalArgumentException("Request is null");
            }

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

    private void handleConnect(Session session, UserGameCommand connectCommand) {
        // Handle the connect command, e.g., validate the auth token and game ID
        System.out.println("Connecting user to game: " + connectCommand.getGameID());
        // Implement further logic to validate the connection and game
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

    private void sendLoadGame(Session session, GameModel gameModel) {
        try {
            // Create the ServerMessage with the LOAD_GAME type
            ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);

            // Serialize the game model as JSON and set it as the payload
            String gameStateJson = gson.toJson(gameModel);
            serverMessage.setPayload(gameStateJson);

            // Convert the entire server message to JSON
            String responseJson = gson.toJson(serverMessage);

            // Send the JSON message to the client
            session.getRemote().sendString(responseJson);
        } catch (IOException e) {
            System.err.println("Error sending LOAD_GAME command: " + e.getMessage());
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

    private void notifyGameOver(Session session, String winner, String reason) throws IOException {
        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.GAME_OVER);
        serverMessage.setPayload(winner + "," + reason);
        String responseJson = gson.toJson(serverMessage);
        session.getRemote().sendString(responseJson);
    }

    private void notifyTurnTransition(Session session, String currentPlayer) throws IOException {
        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.TURN_TRANSITION);
        serverMessage.setPayload(currentPlayer);
        String responseJson = gson.toJson(serverMessage);
        session.getRemote().sendString(responseJson);
    }

    private void notifyInvalidMove(Session session, String reason) throws IOException {
        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.INVALID_MOVE);
        serverMessage.setPayload(reason);
        String responseJson = gson.toJson(serverMessage);
        session.getRemote().sendString(responseJson);
    }
}
