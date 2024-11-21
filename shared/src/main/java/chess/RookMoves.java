package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoves extends PieceMoves {

    Collection<ChessMove> rookMoves = new ArrayList<>();

    public Collection<ChessMove> getRookMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {

        rookMoves.addAll(linearMoves(board, myPosition, pieceColor));

        return rookMoves;
    }
}
