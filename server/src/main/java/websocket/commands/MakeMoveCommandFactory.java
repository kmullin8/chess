package websocket.commands;


import chess.ChessMove;
import model.GameModel;

public class MakeMoveCommandFactory implements CommandFactory {
    @Override
    public GameCommand create(Object... params) {
        GameModel gameModel = (GameModel) params[0];
        ChessMove move = (ChessMove) params[1];
        return new MakeMoveCommand(gameModel, move);
    }
}
