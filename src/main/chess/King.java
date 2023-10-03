package chess;

import java.util.Collection;

public class King extends ChessPieceImpl{

    public King(ChessGame.TeamColor teamColorInput) {
        super(teamColorInput, PieceType.KING);
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
