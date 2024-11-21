package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoves extends PieceMoves {
    Collection<ChessMove> pawnMoves = new ArrayList<>();

    public Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {

        pawnMoves.addAll(pawnMoves(board, myPosition, pieceColor));

        return pawnMoves;
    }
}
