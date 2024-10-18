package requests;

/**
 * process request Gives a list of all games
 */
public class ListGameRequest {
    /**
     * unique token for authentication
     */
    private String authToken;

    /**
     *
     * @param authToken key to join game
     */
    public ListGameRequest(String authToken) {
        this.authToken = authToken;
    }

    /**
     *
     * @return of request
     */
    public String getAuthToken() {
        return authToken;
    }
}
