package services;

import dataAccess.*;
import model.GameModel;

import java.util.Collection;
import java.util.List;

/**
 * Gives a list of all games.
 */
public class ListGameService {

    private DataAccess dataAccess;

    public ListGameService(DataAccess dataAccess){
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
