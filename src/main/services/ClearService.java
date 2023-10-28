package services;

import dataAccess.AuthTokenDAO;
import dataAccess.GameDOA;
import dataAccess.UserDOA;
import requests.ClearRequest;
import results.ClearResult;

/**
 * Clears the database. Removes all users, games, and authTokens.
 */
public class ClearService {
    private AuthTokenDAO authTokenDAO;
    private GameDOA gameDOA;
    private UserDOA userDOA;

    public ClearService(){
        this.authTokenDAO = new AuthTokenDAO();
        this.gameDOA = new GameDOA();
        this.userDOA = new UserDOA();
    }
}
