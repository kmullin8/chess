package services;

import DataAccessException.DataAccessException;
import dataAccess.AuthTokenDAO;
import dataAccess.GameDOA;
import dataAccess.UserDOA;
import model.GameModel;

import java.util.List;

/**
 * Gives a list of all games.
 */
public class ListGamesService {

    private AuthTokenDAO authTokenDAO;
    private GameDOA gameDOA;
    private UserDOA userDOA;

    public ListGamesService(){
        this.authTokenDAO = new AuthTokenDAO();
        this.gameDOA = new GameDOA();
        this.userDOA = new UserDOA();
    }

    public List<GameModel> listGames() throws CodedException {
        try {
            return gameDOA.getGames();
        } catch (DataAccessException ex) {
            throw new CodedException(500, "Server error");
        }
    }
}
