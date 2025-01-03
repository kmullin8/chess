package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthTokenModel;
import model.GameModel;
import model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlDataAccess implements DataAccess {

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
            executeUpdate("INSERT INTO `user` (username, password, email) VALUES (?, ?, ?)",
                    newUser.getUsername(), newUser.getPassword(), newUser.getEmail());
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
        // Check if the user exists first
        if (!userExists(username)) { // Ensure this method checks the existence of the user
            throw new DataAccessException("User does not exist.");
        }

        var newAuthToken = new AuthTokenModel(username);
        executeUpdate("INSERT INTO `authentication` (authToken, username) VALUES (?, ?)", newAuthToken.getAuthToken(), newAuthToken.getUsername());

        return newAuthToken;
    }

    private boolean userExists(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("SELECT 1 FROM `user` WHERE username = ?")) { // Adjusted to use `SELECT 1`

            preparedStatement.setString(1, username);
            try (var rs = preparedStatement.executeQuery()) {
                return rs.next(); // Returns true if a user is found
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Error checking user existence: %s", e.getMessage()));
        }
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
        var id = executeUpdate("INSERT INTO `game` (gameName, whitePlayerName, blackPlayerName, game) VALUES (?, ?, ?, ?)",
                gameName,
                null,
                null,
                gsonGame.toJson(game));
        if (id != 0) {
            return new GameModel(id, null, null, gameName, game);
        }

        return null;
    }

    public void updateGame(GameModel gameData) throws DataAccessException {
        executeUpdate("UPDATE `game` set gameName=?, whitePlayerName=?, blackPlayerName=?, game=? WHERE gameID=?",
                gameData.getGameName(),
                gameData.getWhiteUsername(),
                gameData.getBlackUsername(),
                gameData.getGame(),
                gameData.getGameID());
    }

    public GameModel readGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("SELECT gameID, gameName, whitePlayerName, " +
                     "blackPlayerName, game FROM `game` WHERE gameID=?")) {

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
              `password` varchar(256) NOT NULL,
              `email` varchar(45) NOT NULL,
              PRIMARY KEY (`username`),
              UNIQUE KEY `username_UNIQUE` (`username`)
            ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try {
            try (Connection conn = DatabaseManager.getConnection()) {

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
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Failed to execute command: %s", e.getMessage()));
        }
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        // Get the connection outside of try-with-resources to manage it manually
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {

            for (var i = 0; i < params.length; i++) {
                var param = params[i];
                if (param instanceof String s) {
                    preparedStatement.setString(i + 1, s);
                } else if (param instanceof Integer x) {
                    preparedStatement.setInt(i + 1, x);
                } else if (param instanceof ChessGame g) {
                    preparedStatement.setString(i + 1, new Gson().toJson(g));
                } else if (param == null) {
                    preparedStatement.setNull(i + 1, java.sql.Types.NULL);
                }
            }

            int affectedRows = preparedStatement.executeUpdate();

            boolean isDeleteOperation = statement.trim().toUpperCase().startsWith("DELETE");
            if (!isDeleteOperation && affectedRows == 0) {
                throw new DataAccessException("No game found to update.");
            }

            // Use try-with-resources for the ResultSet
            try (var rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            return 0;
        } catch (SQLException e) {
            throw new DataAccessException(String.format("executeUpdate error: %s, %s", statement, e.getMessage()));
        }
    }

    public String getUsernameByAuthToken(String authToken) throws DataAccessException {
        String query = "SELECT username FROM `authentication` WHERE authToken=?";
        String username = null;

        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(query)) {

            // Set the parameter directly in the prepared statement
            preparedStatement.setString(1, authToken);

            // Execute the query
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    username = resultSet.getString("username");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Error retrieving username by authToken: %s", e.getMessage()));
        }

        return username;
    }
}
