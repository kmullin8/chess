package ui;

import model.GameModel;
import websocket.messages.ServerMessage;

public interface NotificationHandler {
    void updateGameState(GameModel game);

    void notify(ServerMessage serverMessage);
}
