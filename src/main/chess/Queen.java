package chess;

import java.util.Collection;

public class Queen extends ChessPieceImpl{

    public Queen(ChessGame.TeamColor teamColorInput) {
        super(teamColorInput, PieceType.QUEEN);
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
