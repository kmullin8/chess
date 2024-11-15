package requests;

/**
 * processes Creates a new game request
 */
public class CreateGameRequest {

    /**
     * new game name
     */
    private String gameName;

    /**
     *
     * @param gameName new game name
     */
    public CreateGameRequest(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }
}
