package requests;

import chess.ChessGame;
import chess.ChessMove;
import websocket.commands.UserGameCommand;

public class WebSocketRequest {
    private UserGameCommand.CommandType commandType;
    private String authToken;
    private Integer gameID;
    private ChessMove move;
    private String username;
    private ChessGame.TeamColor color;

    public UserGameCommand.CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(UserGameCommand.CommandType commandType) {
        this.commandType = commandType;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public ChessMove getMove() {
        return move;
    }

    public void setMove(ChessMove move) {
        this.move = move;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ChessGame.TeamColor getColor() {
        return color;
    }

    public void setColor(ChessGame.TeamColor color) {
        this.color = color;
    }
}
