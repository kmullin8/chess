package results;

/**
 * result of Logs in an existing user (returns a new authToken).
 */
public class LoginResult {
    /**
     * username of login request
     */
    private String username;
    /**
     * new token for authentication
     */
    private String authToken;
    /**
     * message to return upon failure
     */
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
