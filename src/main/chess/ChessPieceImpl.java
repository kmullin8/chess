package chess;

import java.util.Collection;

public abstract class ChessPieceImpl implements ChessPiece{
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return null;
    }

    @Override
    public PieceType getPieceType() {
        return null;
    }

    public abstract Collection <ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}
