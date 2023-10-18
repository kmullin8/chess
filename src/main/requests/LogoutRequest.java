package requests;

/**
 * processes Logs out the user represented by the authToken
 */
public class LogoutRequest {

    private String authToken;

    /**
     *
     * @param authToken key to join game
     */
    public LogoutRequest(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
