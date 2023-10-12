package chess;

import java.util.Collection;
import java.util.HashSet;

public abstract class AbstractChessPiece implements ChessPiece {
    private final ChessGame.TeamColor teamColor;
    private final PieceType pieceType;

    protected AbstractChessPiece(ChessGame.TeamColor teamColor, PieceType pieceType) {
        this.teamColor = teamColor;
        this.pieceType = pieceType;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}

class Pawn extends AbstractChessPiece {
    public Pawn(ChessGame.TeamColor teamColor) {
        super(teamColor, PieceType.PAWN);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();

        int forwardDirection = (getTeamColor() == ChessGame.TeamColor.WHITE) ? -1 : 1;
        ChessPosition oneStepAhead = new ChessPosition(myPosition.getRow() + forwardDirection, myPosition.getColumn());
        
        if (board.getPiece(oneStepAhead) == null) {
            moves.add(new ChessMoveImpl(myPosition, oneStepAhead));
        }
        
        return moves;
    }
}


