package ui;

import model.GameModel;
import websocket.messages.ServerMessage;

public interface NotificationHandler {
    void updateGameState(GameModel game);

    void notify(ServerMessage serverMessage);

    void showErrorNotification(String message);

    void showGameOverNotification(String winner, String reason);

    void showTurnTransition(String currentPlayer);

    void showInvalidMoveNotification(String reason);
}
