package chess;

import java.util.Collection;

public class Bishop extends ChessPieceImpl{

    public Bishop(ChessGame.TeamColor teamColorInput) {
        super(teamColorInput, PieceType.BISHOP);
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
