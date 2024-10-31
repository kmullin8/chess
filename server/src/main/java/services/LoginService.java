package services;

import dataaccess.*;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Logs in an existing user (returns a new authToken)
 */
public class LoginService {

    private DataAccess dataAccess;

    public LoginService(DataAccess dataAccess) {
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
            if (loggedInUser != null && verifyUser(loggedInUser.getPassword(), user.getPassword())) {
                return dataAccess.writeAuth(loggedInUser.getUsername()).getAuthToken();
            }
            throw new CodedException(401, "Invalid username or password");
        } catch (dataaccess.DataAccessException ex) {
            throw new CodedException(500, "Internal server error");
        }
    }

    boolean verifyUser(String providedHashedPassword, String providedClearTextPassword) {

        return BCrypt.checkpw(providedClearTextPassword, providedHashedPassword);
    }
}
