package requests;

import chess.ChessGame;

/**
 * processes request to Verifies that the specified game exists, and, if a color is specified, adds the caller as the
 * requested color to the game. If no color is specified the user is joined as an observer. This request is idempotent.
 */
public class JoinGameRequest {
    /**
     * game ID to join
     */
    private int gameID;
    /**
     * color of current player
     */
    private ChessGame.TeamColor playerColor;

    /**
     * @param gameID      unique game ID
     * @param playerColor color of current player
     */
    public JoinGameRequest(int gameID, ChessGame.TeamColor playerColor) {
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }
}
