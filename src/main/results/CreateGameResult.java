package results;

/**
 * result of Creates a new game
 */
public class CreateGameResult {
    private String message;
    private int gameID;

    /**
     * default constructor
     */
    public CreateGameResult() {
        // Default constructor
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
