package websocket.commands;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.annotations.SerializedName;

public class MakeMoveCommand extends UserGameCommand {

    @SerializedName("move")
    ChessMove chessMove;

    public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID, ChessGame.TeamColor color, String username, ChessMove move) {
        super(commandType, authToken, gameID, color, username);
        this.chessMove = move;

    }

    public ChessMove getChessMove() {
        return chessMove;
    }

    public void setChessMove(ChessMove chessMove) {
        this.chessMove = chessMove;
    }
}
