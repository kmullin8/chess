package services;

import DataAccessException.DataAccessException;
import dataAccess.DataAccess;
import model.GameModel;

import java.util.Collection;
import java.util.List;

/**
 * Gives a list of all games.
 */
public class ListGamesService {

    private DataAccess dataAccess;

    public ListGamesService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public Collection<GameModel> listGames() throws CodedException {
        try {
            return dataAccess.listGames();
        } catch (dataAccess.DataAccessException ex) {
            throw new CodedException(500, "Server error");
        }
    }
}
