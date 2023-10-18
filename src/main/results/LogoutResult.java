package results;

/**
 * Logs out the user represented by the authToken
 */
public class LogoutResult {
    private String message;

    /**
     * default constructor
     */
    public LogoutResult() {
        // Default constructor
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
