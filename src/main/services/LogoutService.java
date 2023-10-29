package services;

import dataAccess.AuthTokenDAO;
import dataAccess.GameDOA;
import dataAccess.UserDOA;
import DataAccessException.DataAccessException;


/**
 * Logs out the user represented by the authToken
 */
public class LogoutService {

    private AuthTokenDAO authTokenDAO;
    private GameDOA gameDOA;
    private UserDOA userDOA;

    public LogoutService(){
        this.authTokenDAO = new AuthTokenDAO();
        this.gameDOA = new GameDOA();
        this.userDOA = new UserDOA();
    }

    public void deleteSession(String authToken) throws CodedException {
        try {
            authTokenDAO.deleteAuth(authToken);
        } catch (DataAccessException ex) {
            throw new CodedException(500, "Internal server error");
        }
    }
}
