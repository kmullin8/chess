package services;

import dataAccess.*;
import model.*;
import DataAccessException.DataAccessException;
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

    public GameModel createGame(String gameName) throws CodedException {
        try {
            return gameDOA.newGame(gameName);
        } catch (DataAccessException ex) {
            throw new CodedException(500, "Server error");
        }
    }


}
