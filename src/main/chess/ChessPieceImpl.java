package chess;

import java.util.Collection;

public abstract class ChessPieceImpl implements ChessPiece{

    private ChessGame.TeamColor teamColor;
    private PieceType chessPiece;

    public ChessPieceImpl(ChessGame.TeamColor teamColorInput, PieceType chessPieceInput){
        teamColor = teamColorInput;
        chessPiece = chessPieceInput;
    }


    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return chessPiece;
    }

    public abstract Collection <ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}
