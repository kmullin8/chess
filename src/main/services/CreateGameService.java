package services;

import dataAccess.AuthTokenDAO;
import dataAccess.GameDOA;
import dataAccess.UserDOA;
import results.CreateGameResult;

/**
 * Creates a new game
 */
public class CreateGameService {

    private AuthTokenDAO authTokenDAO;
    private GameDOA gameDOA;
    private UserDOA userDOA;

    public CreateGameService(){
        this.authTokenDAO = new AuthTokenDAO();
        this.gameDOA = new GameDOA();
        this.userDOA = new UserDOA();
    }
}
