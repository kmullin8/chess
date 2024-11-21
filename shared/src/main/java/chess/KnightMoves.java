package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves extends PieceMoves {

    Collection<ChessMove> kningtMoves = new ArrayList<>();

    public Collection<ChessMove> getKnightMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {

        kningtMoves.addAll(knightMoves(board, myPosition, pieceColor));

        return kningtMoves;
    }
}
