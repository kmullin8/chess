package services;

import dataaccess.*;

/**
 * Clears the database. Removes all users, games, and authTokens.
 */
public class ClearService {
    private DataAccess dataAccess;

    public ClearService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void clearApplication() throws CodedException {
        try {
            dataAccess.clear();
        } catch (dataaccess.DataAccessException ex) {
            throw new CodedException(500, "Server error");
        }
    }
}
