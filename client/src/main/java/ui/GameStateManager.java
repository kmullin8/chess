package ui;

import model.GameModel;

public class GameStateManager {
    private static GameStateManager instance;
    private GameModel currentGame;

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

    private void notifySubscribers() {
        // Implement notification logic, e.g., broadcasting the state to WebSocket clients
        System.out.println("Game state updated. Notifying subscribers...");
    }
}

