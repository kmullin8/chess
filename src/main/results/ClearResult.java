package results;

/**
 * result of Clears the database. Removes all users, games, and authTokens
 */
public class ClearResult {
    private String message;

    /**
     * default constructor
     */
    public ClearResult() {
        // Default constructor
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
