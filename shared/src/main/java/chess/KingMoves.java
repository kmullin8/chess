package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoves extends PieceMoves {

    Collection<ChessMove> kingMoves = new ArrayList<>();

    public Collection<ChessMove> getKingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {

        kingMoves.addAll(kingMoves(board, myPosition, pieceColor));

        return kingMoves;
    }
}
