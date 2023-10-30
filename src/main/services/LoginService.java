package services;

import DataAccessException.DataAccessException;
import dataAccess.*;
import model.*;

/**
 * Logs in an existing user (returns a new authToken)
 */
public class LoginService {

    private AuthTokenDAO authTokenDAO;
    private GameDOA gameDOA;
    private UserDOA userDOA;

    public LoginService(){
        this.authTokenDAO = new AuthTokenDAO();
        this.gameDOA = new GameDOA();
        this.userDOA = new UserDOA();
    }

    /**
     * Create a session for a user. If the user already has a session then
     * the previous session is invalidated.
     *
     * @param user to create a session for.
     * @return the authToken for the session.
     */
    public Object createSession(UserModel user) throws CodedException {
        try {
            UserModel loggedInUser = UserDOA.getUser(user.getUsername());
            if (loggedInUser != null && loggedInUser.getPassword().equals(user.getPassword())) {
                String newAuthToken = authTokenDAO.setNewAuthToken(user.getUsername()); //set new authToken
                return newAuthToken;
            }
            throw new CodedException(401, "Invalid username or password");
        } catch (DataAccessException ex) {
            throw new CodedException(500, "Internal server error");
        }
    }
}
