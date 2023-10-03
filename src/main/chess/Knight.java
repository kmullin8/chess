package chess;

import java.util.Collection;

public class Knight extends ChessPieceImpl{

    public Knight(ChessGame.TeamColor teamColorInput) {
        super(teamColorInput, PieceType.KNIGHT);
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
