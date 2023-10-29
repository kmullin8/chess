package dataAccess;

import DataAccessException.DataAccessException;
import chess.ChessGame;
import chess.ChessGameImpl;
import model.GameModel;
import model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * accesses database for game
 */
public class GameDOA {

    /**
     * game database
     */
    private static List<GameModel> gameDatabase;
    private int nextID = 1000;

    public GameDOA(){
        gameDatabase = new ArrayList<>();
    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public GameModel newGame(String gameName) throws DataAccessException {
        var gameID = nextID++;
        var gameModel = new GameModel(gameID, gameName);
        gameModel.getGame().setBoard( gameModel.getGame().getBoard());
        gameModel.getGame().getBoard().resetBoard(); // set board to default
        gameDatabase.add(gameModel);
        return gameModel;
    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public void findGameByID() throws DataAccessException {

    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public void findAllGames() throws DataAccessException {

    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public void claimSpot() throws DataAccessException {

    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public void updateGame() throws DataAccessException {

    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public void deleteGame() throws DataAccessException {

    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public void deleteAllGames() throws DataAccessException {

    }
}
