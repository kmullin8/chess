package services;

import dataAccess.*;
import model.*;

/**
 * Logs in an existing user (returns a new authToken)
 */
public class LoginService {

    private DataAccess dataAccess;

    public LoginService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    /**
     * Create a session for a user. If the user already has a session then
     * the previous session is invalidated.
     *
     * @param user to create a session for.
     * @return the authToken for the session.
     */
    public String createSession(UserModel user) throws CodedException {
        try {
            UserModel loggedInUser = dataAccess.readUser(user.getUsername());
            if (loggedInUser != null && loggedInUser.getPassword().equals(user.getPassword())) {
                return dataAccess.writeAuth(loggedInUser.getUsername()).getAuthToken();
            }
            throw new CodedException(401, "Invalid username or password");
        } catch (dataAccess.DataAccessException ex) {
            throw new CodedException(500, "Internal server error");
        }
    }
}
