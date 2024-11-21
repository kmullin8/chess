package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesNew extends PieceMoves {

    Collection<ChessMove> queenMoves = new ArrayList<>();

    public Collection<ChessMove> getQueenMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {

        queenMoves.addAll(linearMoves(board, myPosition, pieceColor));
        queenMoves.addAll(diagonalMoves(board, myPosition, pieceColor));

        return queenMoves;
    }
}
