package ui;

import model.GameModel;
import websocket.messages.ServerMessage;

public interface NotificationHandler {
    void notify(ServerMessage notification);

    void updateGameState(GameModel game);
}
