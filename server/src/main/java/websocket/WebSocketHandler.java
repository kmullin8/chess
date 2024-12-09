package websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.MySqlDataAccess;
import model.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import requests.WebSocketRequest;
import websocket.commands.*;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Objects;

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

                //verify auth
                if (!verifyAuth(connectCommand.getUsername(), connectCommand.getAuthToken())) {
                    handleError(session, "auth token does not match");
                    return;
                }

                String gameID = String.valueOf(connectCommand.getGameID());
                GameModel gameModel = fetchGameModel(gameID);

                if (gameModel == null) {
                    handleError(session, "game id does not exist");
                } else {
                    handleConnect(session, connectCommand);
                    sendLoadGame(session, gameModel);
                }

                return; // Exit after handling the connect command
            }

            // Parse incoming JSON message for other commands
            WebSocketRequest request = gson.fromJson(message, WebSocketRequest.class);

            if (request == null) {
                throw new IllegalArgumentException("Request is null");
            }

            // Extract command type and parameters
            UserGameCommand.CommandType commandType = request.getCommandType();
            Object[] params = extractParams(request, session);

            // Dispatch the command
//            GameCommand command = commandDispatcher.getCommand(String.valueOf(commandType), params);
//            command.execute();

            //handle request
            if (commandType == UserGameCommand.CommandType.MAKE_MOVE) {
                handleMakeMove(request, session);
            } else if (commandType == UserGameCommand.CommandType.RESIGN) {
                handleResign(request, session);
            }

        } catch (Exception e) {
            System.err.println("Error handling message: " + e.getMessage());
            e.printStackTrace();
            sendErrorResponse(session, e.getMessage());
        }
    }

    private void handleConnect(Session session, UserGameCommand connectCommand) {
        Integer gameID = connectCommand.getGameID();
        String broadcast = connectCommand.getUsername() + " joined the game as " + connectCommand.getColor();

        // Handle the connect command, e.g., validate the auth token and game ID
        System.out.println("Connecting user to game: " + gameID);

        //add connection to connectionManager
        connections.broadcast(gameID, broadcast, null);
        connections.add(gameID, session);
    }

    private void handleMakeMove(WebSocketRequest request, Session session) throws IOException, DataAccessException {
        GameModel gameModel = fetchGameModel(request.getGameID().toString());
        //check valid game
        if (gameModel.isGameOver()) {
            handleError(session, "game is over");
            return;
        }

        String authTokenModel = request.getAuthToken();
        ChessMove move = request.getMove();
        ChessPiece piece = gameModel.getGame().getBoard().getPiece(move.getStartPosition());
        MySqlDataAccess dataAccess = new MySqlDataAccess();
        String username = dataAccess.getUsernameByAuthToken(authTokenModel);
        ChessGame.TeamColor color;

        if (Objects.equals(gameModel.getWhiteUsername(), username)) {
            color = ChessGame.TeamColor.WHITE;
        } else if (Objects.equals(gameModel.getBlackUsername(), username)) {
            color = ChessGame.TeamColor.BLACK;
        } else {
            handleError(session, "could not find color invalid auth");
            return;
        }

        //check colors
        if (color != piece.getTeamColor()) {
            handleError(session, "invalid move");
            return;
        }
        try {
            gameModel.getGame().makeMove(move);
        } catch (InvalidMoveException e) {
            handleError(session, "invalid move");
            return;
        }
        updateGame(gameModel);

        connections.broadcastLoadGame(request.getGameID(),
                gameModel,
                null);//send load game to all clients

        //create broadcast
        String broadcast = (
                request.getUsername() +
                        " moved " +
                        request.getMove().getStartPosition().getRow() +
                        request.getMove().getStartPosition().getColumn() +
                        " to " +
                        request.getMove().getStartPosition().getRow() +
                        request.getMove().getStartPosition().getColumn() +
                        "\n"
        );
        connections.broadcast(request.getGameID(), broadcast, session);
    }

    public void handleResign(WebSocketRequest request, Session session) throws DataAccessException {
        GameModel gameModel = fetchGameModel(request.getGameID().toString());
        MySqlDataAccess dataAccess = new MySqlDataAccess();
        String username = dataAccess.getUsernameByAuthToken(request.getAuthToken());

        //determine valid resign
        if (!Objects.equals(gameModel.getWhiteUsername(), username) && !Objects.equals(gameModel.getBlackUsername(), username)) {
            handleError(session, "observer cannot resign");
            return;
        } else if (gameModel.isGameOver()) {
            handleError(session, "game is over");
            return;
        }

        gameModel.getGame().setValidGame(false);
        updateGame(gameModel);

        String message = request.getUsername() + "has resigned";
        connections.broadcast(request.getGameID(), message, null);
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
        serverMessage.setErrorMessage(errorMessage);

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
        switch (request.getCommandType()) {
            case MAKE_MOVE -> {
                GameModel gameModel = fetchGameModel(request.getGameID().toString());
                ChessMove move = request.getMove();
                return new Object[]{gameModel, move};
            }
            case RESIGN -> {
                GameModel resignGameModel = fetchGameModel(request.getGameID().toString());
                return new Object[]{resignGameModel};
            }
            default -> throw new IllegalArgumentException("Unknown command type: " + request.getCommandType());
        }
    }

    private void sendLoadGame(Session session, GameModel gameModel) {
        try {
            // Create the ServerMessage with the LOAD_GAME type
            ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);

            // Serialize the game model as JSON and set it as the payload
            String gameStateJson = gson.toJson(gameModel);
            serverMessage.setGame(gameStateJson);

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


        return gameModel;
    }

    private void updateGame(GameModel gameModel) throws DataAccessException {
        // Initialize the data access layer
        MySqlDataAccess dataAccess = new MySqlDataAccess();

        // Update the game in the database
        dataAccess.updateGame(gameModel);
    }

    private ChessGame.TeamColor getColor(String authToken) throws DataAccessException {
        MySqlDataAccess dataAccess = new MySqlDataAccess();

        // Update the game in the database
        return null;
    }

    private void sendErrorResponse(Session session, String error) throws IOException {
        // Detailed error message
        String detailedMessage = "Error occurred while processing the request: " + error;

        // Create the WebSocketResponse to send to the client
        WebSocketResponse response = new WebSocketResponse("ERROR", detailedMessage);

        // Convert the response to JSON
        String errorMessage = gson.toJson(response);

        // Send the error message back to the client
        session.getRemote().sendString(errorMessage);
    }

    public boolean verifyAuth(String username, String keyAuthToken) throws DataAccessException {
        // Fetch the AuthTokenModel using the provided authToken
        MySqlDataAccess dataAccess = new MySqlDataAccess();
        AuthTokenModel authTokenModel = dataAccess.readAuth(keyAuthToken);

        // If no auth token is found, return false
        if (authTokenModel == null) {
            return false;
        }

        // Check if the username matches
        return true;
    }


}
