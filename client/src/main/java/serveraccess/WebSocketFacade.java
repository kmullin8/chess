package serveraccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameModel;
import ui.NotificationHandler;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

    private Session session;
    private String authToken;
    private Integer gameID;
    private String username;
    private ChessGame.TeamColor color;
    private NotificationHandler notificationHandler;
    private URI socketURI;

    public WebSocketFacade(String serverUrl, NotificationHandler notificationHandler, String authToken, Integer gameID, ChessGame.TeamColor color, String username) throws Exception {
        try {
            this.socketURI = new URI(serverUrl.replace("http", "ws") + "/ws");
            this.notificationHandler = notificationHandler;
            this.authToken = authToken;
            this.gameID = gameID;
            this.color = color;
            this.username = username;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, socketURI); // Endpoint is passed here
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new Exception("500 " + ex.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        System.out.println("WebSocket connection established. client");

        // Add a message handler for incoming messages
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                handleServerMessage(serverMessage);
            }
        });

        // Send the CONNECT command
        try {
            connect(); // Automatically send CONNECT command upon establishing connection
        } catch (Exception e) {
            System.err.println("Failed to send CONNECT command: " + e.getMessage());
        }
    }

    public void connect() throws Exception {
        try {
            // Ensure session is open before sending the CONNECT command
            if (this.session != null && this.session.isOpen()) {
                // Send the CONNECT command to the server
                UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID, color, username);
                String jsonCommand = new Gson().toJson(command);
                this.session.getBasicRemote().sendText(jsonCommand);  // Sends the CONNECT command
            } else {
                throw new IllegalStateException("WebSocket session is not open.");
            }
        } catch (IOException ex) {
            throw new Exception("500 " + ex.getMessage());
        }
    }

    public void disconnect() throws Exception {
        try {
            UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID, color, username);
            String jsonCommand = new Gson().toJson(command);
            this.session.getBasicRemote().sendText(jsonCommand);
            this.session.close();
        } catch (IOException ex) {
            throw new Exception("500 " + ex.getMessage());
        }
    }

    private void handleServerMessage(ServerMessage serverMessage) {
        String reason = null;
        switch (serverMessage.getServerMessageType()) {
            case LOAD_GAME:
                GameModel game = new Gson().fromJson(serverMessage.getGame(), GameModel.class);
                notificationHandler.updateGameState(game);
                break;
            case ERROR:
                handleError(serverMessage.getErrorMessage());
                break;
            case NOTIFICATION:
                notificationHandler.notify(serverMessage);
                break;
            default:
                System.err.println("Unknown message type: " + serverMessage.getServerMessageType());
                break;
        }
    }

    private void handleError(String errorMessage) {
        // You can use NotificationHandler or directly update UI components (e.g., show alerts)
        System.err.println("Error received from server: " + errorMessage);
//
//        // Propagate the error message to the NotificationHandler or any other front-end logic
//        // For instance, it could trigger a UI pop-up on the client side
//        notificationHandler.showErrorNotification(errorMessage);
    }

    public void sendCommand(UserGameCommand command) throws IOException {
        String jsonCommand = new Gson().toJson(command);
        session.getBasicRemote().sendText(jsonCommand);
    }

    public void sendMakeMoveCommand(MakeMoveCommand command) throws IOException {
        String jsonCommand = new Gson().toJson(command);
        session.getBasicRemote().sendText(jsonCommand);
    }

    public void updateAuthToken(String newAuthToken) {
        this.authToken = newAuthToken;

        try {
            if (this.session != null && this.session.isOpen()) {
                disconnect();
            }

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, socketURI); // Reconnect with updated token
        } catch (Exception e) {
            System.err.println("Failed to update auth token and reconnect: " + e.getMessage());
        }
    }

    public void updateGameID(Integer newGameID) {
        this.gameID = newGameID;

        try {
            if (this.session != null && this.session.isOpen()) {
                disconnect();
            }

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, socketURI); // Reconnect with updated token
        } catch (Exception e) {
            System.err.println("Failed to update game id and reconnect: " + e.getMessage());
        }
    }
}

