package services;

import dataaccess.*;
import model.*;

/**
 * Creates a new game
 */
public class CreateGameService {

    private DataAccess dataAccess;

    public CreateGameService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public GameModel createGame(String gameName) throws CodedException {
        try {
            return dataAccess.newGame(gameName);
        } catch (dataaccess.DataAccessException ex) {
            throw new CodedException(500, "Server error");
        }
    }


}
