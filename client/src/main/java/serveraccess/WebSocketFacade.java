package serveraccess;

import com.google.gson.Gson;
import model.GameModel;
import ui.NotificationHandler;
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
    private NotificationHandler notificationHandler;
    private URI socketURI;

    public WebSocketFacade(String serverUrl, NotificationHandler notificationHandler, String authToken, Integer gameID) throws Exception {
        try {
            this.socketURI = new URI(serverUrl.replace("http", "ws") + "/ws");
            this.notificationHandler = notificationHandler;
            this.authToken = authToken;
            this.gameID = gameID;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, socketURI); // Endpoint is passed here
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new Exception("500 " + ex.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        System.out.println("WebSocket connection established.");

        // Add a message handler for incoming messages
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                handleServerMessage(serverMessage);
            }
        });

        try {
            connect(); // Automatically send CONNECT command upon establishing connection
        } catch (Exception e) {
            System.err.println("Failed to send CONNECT command: " + e.getMessage());
        }
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

    public void connect() throws Exception {
        try {
            UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
            String jsonCommand = new Gson().toJson(command);
            this.session.getBasicRemote().sendText(jsonCommand);
        } catch (IOException ex) {
            throw new Exception("500 " + ex.getMessage());
        }
    }

    public void disconnect() throws Exception {
        try {
            UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID);
            String jsonCommand = new Gson().toJson(command);
            this.session.getBasicRemote().sendText(jsonCommand);
            this.session.close();
        } catch (IOException ex) {
            throw new Exception("500 " + ex.getMessage());
        }
    }

    private void handleServerMessage(ServerMessage serverMessage) {
        switch (serverMessage.getServerMessageType()) {
            case LOAD_GAME:
                GameModel game = new Gson().fromJson(serverMessage.getPayload(), GameModel.class);
                notificationHandler.updateGameState(game);
                break;
            case ERROR:
                System.err.println("Server error: " + serverMessage.getPayload());
                break;
            case NOTIFICATION:
                notificationHandler.notify(serverMessage);
                break;
            default:
                System.err.println("Unknown message type: " + serverMessage.getServerMessageType());
                break;
        }
    }

    public void sendCommand(UserGameCommand command) throws IOException {
        String jsonCommand = new Gson().toJson(command);
        session.getBasicRemote().sendText(jsonCommand);
    }
}

