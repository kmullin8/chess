package services;

import dataaccess.*;


/**
 * Logs out the user represented by the authToken
 */
public class LogoutService {

    private DataAccess dataAccess;

    public LogoutService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void deleteSession(String authToken) throws CodedException {
        try {
            dataAccess.deleteAuth(authToken);
        } catch (dataaccess.DataAccessException ex) {
            throw new CodedException(500, "Internal server error");
        }
    }
}
