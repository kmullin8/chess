package httpService;

import requests.CreateGameRequest;
import results.CreateGameResult;

/**
 * Creates a new game
 */
public class CreateGameService {

    /**
     *
     * @param authToken key to join game
     * @param gameName name of game
     * @return result of request
     */
    public CreateGameResult newGame(String authToken, String gameName) {
        CreateGameResult result = new CreateGameResult();
        return result;
    }
}
