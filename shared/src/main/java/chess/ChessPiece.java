package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    public String getSymbol() {
        switch (type) {
            case ROOK:   return pieceColor == ChessGame.TeamColor.WHITE ? "R" : "r";
            case KNIGHT: return pieceColor == ChessGame.TeamColor.WHITE ? "N" : "n";
            case BISHOP: return pieceColor == ChessGame.TeamColor.WHITE ? "B" : "b";
            case QUEEN:  return pieceColor == ChessGame.TeamColor.WHITE ? "Q" : "q";
            case KING:   return pieceColor == ChessGame.TeamColor.WHITE ? "K" : "k";
            case PAWN:   return pieceColor == ChessGame.TeamColor.WHITE ? "P" : "p";
            default:     return " ";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChessPiece)) {
            return false;
        }
        ChessPiece otherPiece = (ChessPiece) obj;
        return this.pieceColor == otherPiece.pieceColor && this.type == otherPiece.type;
    }
}
