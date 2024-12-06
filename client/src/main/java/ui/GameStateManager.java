package ui;

import chess.ChessGame;
import model.GameModel;

public class GameStateManager {
    private static GameStateManager instance;
    private GameModel currentGame;
    private ChessGame.TeamColor color;

    private GameStateManager() {
    }

    public static synchronized GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

    public synchronized GameModel getCurrentGame() {
        return currentGame;
    }

    public synchronized void setCurrentGame(GameModel game) {
        this.currentGame = game;
    }

    public synchronized ChessGame.TeamColor getColor() {
        return color;
    }

    public synchronized void setColor(ChessGame.TeamColor color) {
        this.color = color;
    }


    private void notifySubscribers() {
        // Implement notification logic, e.g., broadcasting the state to WebSocket clients
        System.out.println("Game state updated. Notifying subscribers...");
    }
}

