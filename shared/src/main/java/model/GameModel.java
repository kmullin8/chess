package model;

import chess.ChessGame;

/**
 * creates a game
 */
public class GameModel {

    /**
     * unique game ID
     */
    private int gameID;
    /**
     * username of white player
     */
    private String whiteUsername;
    /**
     * username of black player
     */
    private String blackUsername;
    /**
     * name of current game
     */
    private String gameName;
    /**
     * \
     * current game
     */
    private ChessGame game;

    public GameModel(GameModel copy) {
        this.gameID = copy.gameID;
        this.gameName = copy.gameName;
        this.blackUsername = copy.blackUsername;
        this.whiteUsername = copy.whiteUsername;
        this.game = new chess.ChessGame(copy.getGame());
    }

    public GameModel(int gameID, String gameName) {
        this.gameID = gameID;
        this.blackUsername = null;
        this.whiteUsername = null;
        this.gameName = gameName;
        this.game = new chess.ChessGame();
    }

    public GameModel(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = game;
    }

    public int getGameID() {
        return gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public boolean isGameOver() {
        var game = this.getGame();

        if (game.isInCheckmate(ChessGame.TeamColor.BLACK) || game.isInStalemate(ChessGame.TeamColor.BLACK)) {
            return true;
        } else if (game.isInCheckmate(ChessGame.TeamColor.WHITE) || game.isInStalemate(ChessGame.TeamColor.WHITE)) {
            return true;
        } else if (!game.isValidGame()) {
            return true;
        }

        return false;
    }
}
