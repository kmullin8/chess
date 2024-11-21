package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoves extends PieceMoves {

    Collection<ChessMove> bishopMoves = new ArrayList<>();

    public Collection<ChessMove> getBishopMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {

        bishopMoves.addAll(diagonalMoves(board, myPosition, pieceColor));

        return bishopMoves;
    }
}
