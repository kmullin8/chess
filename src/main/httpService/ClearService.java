package httpService;

import requests.ClearRequest;
import results.ClearResult;

/**
 * Clears the database. Removes all users, games, and authTokens.
 */
public class ClearService {
    /**
     *
     * @param request request from user
     * @return return result of request
     */
    public ClearResult clearDatabase(ClearRequest request) {
        ClearResult result = new ClearResult();
        return result;
    }
}
