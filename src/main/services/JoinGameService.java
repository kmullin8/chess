package services;

import dataAccess.AuthTokenDAO;
import dataAccess.GameDOA;
import dataAccess.UserDOA;
import results.JoinGameResult;

/**
 * Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested color to the game. If no color is specified the user is joined as an observer. This request is idempotent.
 */
public class JoinGameService {

    private AuthTokenDAO authTokenDAO;
    private GameDOA gameDOA;
    private UserDOA userDOA;

    public JoinGameService(){
        this.authTokenDAO = new AuthTokenDAO();
        this.gameDOA = new GameDOA();
        this.userDOA = new UserDOA();
    }
}
