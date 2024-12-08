package websocket.commands;


import chess.ChessMove;
import chess.InvalidMoveException;
import model.*;

public class MakeMoveCommand implements GameCommand {
    private final GameModel gameModel;
    private final ChessMove move;

    public MakeMoveCommand(GameModel gameModel, ChessMove move) {
        this.gameModel = gameModel;
        this.move = move;
    }

    @Override
    public void execute() throws InvalidMoveException {
        gameModel.getGame().makeMove(move); // Logic to execute the move

    }
}
