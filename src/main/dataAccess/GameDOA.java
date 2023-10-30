package dataAccess;

import DataAccessException.DataAccessException;
import chess.ChessGame;
import chess.ChessGameImpl;
import model.GameModel;
import model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public static GameModel findGameByID(int gameID) throws DataAccessException {
        for (GameModel gameModel : gameDatabase) {
            if (Objects.equals(gameModel.getGameID(), gameID)) {
                return gameModel;
            }
        }
        return null;
    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public List<GameModel> getGames() throws DataAccessException {
        return gameDatabase;
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
    public void updateGame(GameModel newGameModel) throws DataAccessException {
        int gameID = newGameModel.getGameID();

        for (int i = 0; i < gameDatabase.size(); i++) {
            GameModel gameModel = gameDatabase.get(i);
            if (Objects.equals(gameModel.getGameID(), gameID)) {
                gameDatabase.set(i, newGameModel); // Replace the old gameModel with the newGameModel
                break; // Exit the loop once the update is done
            }
        }
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
