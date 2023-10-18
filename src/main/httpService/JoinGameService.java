package httpService;

import requests.JoinGameRequest;
import results.JoinGameResult;

/**
 * Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested color to the game. If no color is specified the user is joined as an observer. This request is idempotent.
 */
public class JoinGameService {

    /**
     *
     * @param authToken key to join game
     * @param gameID unique number to identify game
     * @param playerColor color of current player
     * @return result of request
     */
    public JoinGameResult joinGame(String authToken, String gameID, String playerColor) {

        JoinGameResult result = new JoinGameResult();

        return result;
    }
}
