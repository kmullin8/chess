package requests;

/**
 * processes request to Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested color to the game. If no color is specified the user is joined as an observer. This request is idempotent.
 */
public class JoinGameRequest {
    private String gameID;
    private String playerColor;

    /**
     *
     * @param gameID unique game ID
     * @param playerColor color of current player
     */
    public JoinGameRequest(String gameID, String playerColor) {
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public String getGameID() {
        return gameID;
    }

    public String getPlayerColor() {
        return playerColor;
    }
}
