package services;

import chess.ChessGame;
import dataAccess.*;

import model.AuthTokenModel;
import results.JoinGameResult;

/**
 * Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested color to the game. If no color is specified the user is joined as an observer. This request is idempotent.
 */
public class JoinGameService {

    private DataAccess dataAccess;

    public JoinGameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public Object joinGame(String username, ChessGame.TeamColor teamColor, int gameID) throws CodedException {
        try {
            var game = dataAccess.readGame(gameID);
            if (game == null) {
                throw new CodedException(400, "Unknown game");
            } else if (teamColor == null) {
                return game;
            } else if (game.isGameOver()) {
                throw new CodedException(403, "Game is over");
            } else {
                if (teamColor == ChessGame.TeamColor.WHITE) {
                    if (game.getWhiteUsername() == null || game.getWhiteUsername().equals(username)) {
                        game.setWhiteUsername(username);
                    } else {
                        throw new CodedException(403, "Color taken");
                    }
                } else if (teamColor == ChessGame.TeamColor.BLACK) {
                    if (game.getBlackUsername() == null || game.getBlackUsername().equals(username)) {
                        game.setBlackUsername(username);
                    } else {
                        throw new CodedException(403, "Color taken");
                    }
                }
                dataAccess.updateGame(game);
            }
            return game;
        } catch (dataAccess.DataAccessException ignored) {
            throw new CodedException(500, "Server error");
        }
    }

    public AuthTokenModel getAuthData(String authToken) throws CodedException {
        try {
            return dataAccess.readAuth(authToken);
        } catch (dataAccess.DataAccessException ignored) {
            throw new CodedException(500, "Internal server error");
        }
    }
}
