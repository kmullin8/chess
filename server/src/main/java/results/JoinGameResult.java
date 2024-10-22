package results;

/**
 * result of Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested
 * color to the game. If no color is specified the user is joined as an observer. This request is idempotent.
 */
public class JoinGameResult {
    /**
     * message to return upon failure
     */
    private String message;

    /**
     * default constructor
     */
    public JoinGameResult() {
        // Default constructor
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
