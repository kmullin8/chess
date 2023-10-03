package chess;

import java.util.HashMap;
import java.util.Map;

public class ChessBoardImpl implements ChessBoard {

    public Map<ChessPosition, ChessPiece> chessBoard;

    public ChessBoardImpl(){
        chessBoard = new HashMap<>();
    }
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {

        chessBoard.put(position, piece);
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return chessBoard.get(position);
    }

    @Override
    public void resetBoard() {
        chessBoard.clear();
    }
}
