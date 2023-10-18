package results;

/**
 * result of Logs in an existing user (returns a new authToken).
 */
public class LoginResult {
    private String username;
    private String authToken;
    private String message;

    /**
     * default constructor
     */
    public LoginResult() {
        // Default constructor
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
