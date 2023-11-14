package services;

import dataAccess.*;
import DataAccessException.DataAccessException;


/**
 * Clears the database. Removes all users, games, and authTokens.
 */
public class ClearService {
    private DataAccess dataAccess;

    public ClearService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public void clearApplication()  throws CodedException  {
        try {
            dataAccess.clear();
        } catch (dataAccess.DataAccessException ex) {
            throw new CodedException(500, "Server error");
        }
    }
}
