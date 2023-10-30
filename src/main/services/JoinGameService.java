package services;

import chess.ChessGame;
import dataAccess.*;
import model.GameModel;
import DataAccessException.DataAccessException;

import results.JoinGameResult;

/**
 * Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested color to the game. If no color is specified the user is joined as an observer. This request is idempotent.
 */
public class JoinGameService {

    private AuthTokenDAO authTokenDAO;
    private GameDOA gameDOA;
    private UserDOA userDOA;

    public JoinGameService(){
        this.authTokenDAO = new AuthTokenDAO();
        this.gameDOA = new GameDOA();
        this.userDOA = new UserDOA();
    }

    public Object joinGame(String username, ChessGame.TeamColor teamColor, int gameID) throws CodedException {
        try {
            var gameModel = gameDOA.findGameByID(gameID);
            if (gameModel == null) {
                throw new CodedException(400, "Unknown game");
            } else if (teamColor == null) {
                return gameModel;
            } else if (gameModel.isGameOver()) {
                throw new CodedException(403, "Game is over");
            } else {
                if (teamColor == ChessGame.TeamColor.WHITE) {
                    if (gameModel.getWhiteUsername() == null || gameModel.getWhiteUsername().equals(username)) {
                        gameModel.setWhiteUsername(username);
                    } else {
                        throw new CodedException(403, "Color taken");
                    }
                } else if (teamColor == ChessGame.TeamColor.BLACK) {
                    if (gameModel.getBlackUsername() == null || gameModel.getBlackUsername().equals(username)) {
                        gameModel.setBlackUsername(username);
                    } else {
                        throw new CodedException(403, "Color taken");
                    }
                }
                gameDOA.updateGame(gameModel);
            }
            return gameModel;
        } catch (DataAccessException ignored) {
            throw new CodedException(500, "Server error");
        }
    }
}
