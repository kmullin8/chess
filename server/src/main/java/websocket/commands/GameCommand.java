package websocket.commands;

import chess.InvalidMoveException;

public interface GameCommand {
    void execute() throws InvalidMoveException;
}
