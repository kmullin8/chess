package chess;

import java.util.Collection;

public class Pawn extends ChessPieceImpl{

    public Pawn(ChessGame.TeamColor teamColorInput) {
        super(teamColorInput, PieceType.PAWN);
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
