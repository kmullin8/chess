package services;

import dataAccess.AuthTokenDAO;
import dataAccess.GameDOA;
import dataAccess.UserDOA;
import results.ListGamesResult;

/**
 * Gives a list of all games.
 */
public class ListGamesService {

    private AuthTokenDAO authTokenDAO;
    private GameDOA gameDOA;
    private UserDOA userDOA;

    public ListGamesService(){
        this.authTokenDAO = new AuthTokenDAO();
        this.gameDOA = new GameDOA();
        this.userDOA = new UserDOA();
    }
}
