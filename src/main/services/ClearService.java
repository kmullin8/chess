package services;

import dataAccess.*;
import DataAccessException.DataAccessException;


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

    public void clearApplication()  throws CodedException  {
        try {
            authTokenDAO.clear();
            gameDOA.clear();
            userDOA.clear();
        } catch (DataAccessException ex) {
            throw new CodedException(500, "Server error");
        }
    }
}
