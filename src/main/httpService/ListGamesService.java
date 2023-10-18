package httpService;

import requests.ListGamesRequest;
import results.ListGamesResult;

/**
 * Gives a list of all games.
 */
public class ListGamesService {

    /**
     *
     * @param authToken key to join game
     * @return result of request
     */
    public ListGamesResult listGames(String authToken) {

        ListGamesResult result = new ListGamesResult();

        return result;
    }
}
