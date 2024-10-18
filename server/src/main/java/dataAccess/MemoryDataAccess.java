package dataAccess;

import chess.ChessGame;
import chess.ChessGame;
import model.AuthTokenModel;
import model.GameModel;
import model.UserModel;

import java.util.*;


/**
 * In memory representation of @DataAccess.
 */
public class MemoryDataAccess implements DataAccess {

    private int nextID = 1000;
    final private Map<String, UserModel> users = new HashMap<>();
    final private Map<String, AuthTokenModel> auths = new HashMap<>();
    final private Map<Integer, GameModel> games = new HashMap<>();

    public MemoryDataAccess() {
    }

    public void clear() {
        users.clear();
        auths.clear();
        games.clear();
    }

    public UserModel writeUser(UserModel user) throws DataAccessException {
        if (users.get(user.getUsername()) == null) {
            users.put(user.getUsername(), user);
            return user;
        }

        throw new DataAccessException("duplicate");
    }

    public UserModel readUser(String username) {
        return users.get(username);
    }

    public AuthTokenModel writeAuth(String username) {
        var auth = new AuthTokenModel(username);
        auths.put(auth.getAuthToken(), auth);
        return auth;
    }

    public AuthTokenModel readAuth(String authToken) {
        return auths.get(authToken);
    }

    public void deleteAuth(String authToken) {
        auths.remove(authToken);
    }

    public GameModel newGame(String gameName) {
        var gameID = nextID++;
        var newGame = new GameModel(gameID, null, null, gameName, new ChessGame());
        games.put(newGame.getGameID(), newGame);
        newGame.getGame().getBoard().resetBoard();
        newGame.getGame().setTeamTurn(ChessGame.TeamColor.WHITE);
        return newGame;
    }

    public void updateGame(GameModel game) {
        games.put(game.getGameID(), game);
    }

    public GameModel readGame(int gameID) {
        return games.get(gameID);
    }

    public Collection<GameModel> listGames() {
        return games.values();
    }


    public String description() {
        return "Memory Database";
    }
}