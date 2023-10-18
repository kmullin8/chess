package requests;

/**
 * process request Gives a list of all games
 */
public class ListGamesRequest {
    private String authToken;

    /**
     *
     * @param authToken key to join game
     */
    public ListGamesRequest(String authToken) {
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
