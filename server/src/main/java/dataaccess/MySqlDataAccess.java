package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthTokenModel;
import model.GameModel;
import model.UserModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySqlDataAccess implements DataAccess {

    private DatabaseManager db;

    public MySqlDataAccess() throws DataAccessException {
        configureDatabase();
    }

    public void clear() throws DataAccessException {
        executeCommand("DELETE FROM `authentication`");
        executeCommand("DELETE FROM `user`");
        executeCommand("DELETE FROM `game`");
    }

    public UserModel writeUser(UserModel user) throws DataAccessException {
        if (user.getUsername() != null) {
            var newUser = new UserModel(user.getUsername(), user.getPassword(), user.getEmail());
            executeUpdate("INSERT INTO `user` (username, password, email) VALUES (?, ?, ?)", newUser.getUsername(), newUser.getPassword(), newUser.getEmail());
            return user;
        }

        return null;
    }

    public UserModel readUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("SELECT password, email FROM `user` WHERE username=?")) {

            preparedStatement.setString(1, username);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    var password = rs.getString("password");
                    var email = rs.getString("email");
                    return new UserModel(username, password, email);
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }

        return null;
    }

    public AuthTokenModel writeAuth(String username) throws DataAccessException {
        var newAuthToken = new AuthTokenModel(username);
        executeUpdate("INSERT INTO `authentication` (authToken, username) VALUES (?, ?)", newAuthToken.getAuthToken(), newAuthToken.getUsername());

        return newAuthToken;
    }

    public AuthTokenModel readAuth(String authToken) throws DataAccessException {
        AuthTokenModel authTokenToReturn = null;

        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("SELECT username FROM `authentication` WHERE authToken=?")) {

            preparedStatement.setString(1, authToken);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    authTokenToReturn = new AuthTokenModel(rs.getString("username"));
                    authTokenToReturn.setAuthToken(authToken);
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }

        return authTokenToReturn;
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        executeUpdate("DELETE from `authentication` WHERE authToken=?", authToken);
    }

    public GameModel newGame(String gameName) throws DataAccessException {
        var game = new ChessGame();
        game.getBoard().resetBoard();
        Gson gsonGame = new Gson();
        var ID = executeUpdate("INSERT INTO `game` (gameName, whitePlayerName, blackPlayerName, game) VALUES (?, ?, ?, ?)",
                gameName,
                null,
                null,
                gsonGame.toJson(game));
        if (ID != 0) {
            return new GameModel(ID, null, null, gameName, game);
        }

        return null;
    }

    public void updateGame(GameModel gameData) throws DataAccessException {
        executeUpdate("UPDATE `game` set gameName=?, whitePlayerName=?, blackPlayerName=?, game=?, state=? WHERE gameID=?",
                gameData.getGameName(),
                gameData.getWhiteUsername(),
                gameData.getBlackUsername(),
                gameData.getGame(),
                gameData.getGameID());
    }

    public GameModel readGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("SELECT gameID, gameName, whitePlayerName, blackPlayerName, game FROM `game` WHERE gameID=?")) {

            preparedStatement.setInt(1, gameID);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return readGameData(rs);
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }

        return null;
    }

    public Collection<GameModel> listGames() throws DataAccessException {
        var result = new ArrayList<GameModel>();

        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("SELECT gameID, gameName, whitePlayerName, blackPlayerName, game FROM `game`");
             var rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                var gameData = readGameData(rs);
                result.add(gameData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }

        return result;
    }

    private GameModel readGameData(ResultSet rs) throws SQLException {
        var gs = rs.getString("game");
        var gameID = rs.getInt("gameID");
        var gameName = rs.getString("gameName");
        var whitePlayerName = rs.getString("whitePlayerName");
        var blackPlayerName = rs.getString("blackPlayerName");
        var game = chess.ChessGame.create(gs);

        return new GameModel(gameID, whitePlayerName, blackPlayerName, gameName, game);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS `authentication` (
              `authToken` varchar(100) NOT NULL,
              `username` varchar(100) NOT NULL,
              PRIMARY KEY (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
            CREATE TABLE IF NOT EXISTS  `game` (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `gameName` varchar(45) DEFAULT NULL,
              `whitePlayerName` varchar(100) DEFAULT NULL,
              `blackPlayerName` varchar(100) DEFAULT NULL,
              `game` longtext NOT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
            CREATE TABLE IF NOT EXISTS `user` (
              `username` varchar(45) NOT NULL,
              `password` varchar(45) NOT NULL,
              `email` varchar(45) NOT NULL,
              PRIMARY KEY (`username`),
              UNIQUE KEY `username_UNIQUE` (`username`)
            ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


    private void configureDatabase() throws DataAccessException {
        db = new DatabaseManager();
        try {
            try (Connection conn = db.getConnection()) {
                db.createDatabase();

                for (var statement : createStatements) {
                    try (var preparedStatement = conn.prepareStatement(statement)) {
                        preparedStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Unable to configure database: %s", e.getMessage()));
        }
    }

    private void executeCommand(String statement) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Failed to execute command: %s", e.getMessage()));
        } finally {
            db.returnConnection(conn);
        }
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
            for (var i = 0; i < params.length; i++) {
                var param = params[i];
                switch (param) {
                    case String s -> preparedStatement.setString(i + 1, s);
                    case Integer x -> preparedStatement.setInt(i + 1, x);
                    case null -> preparedStatement.setNull(i + 1, NULL);
                    default -> {
                    }
                }
            }
            preparedStatement.executeUpdate();

            var rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        } catch (SQLException e) {
            throw new DataAccessException(String.format("executeUpdate error: %s, %s", statement, e.getMessage()));
        } finally {
            db.returnConnection(conn);
        }
    }
}
