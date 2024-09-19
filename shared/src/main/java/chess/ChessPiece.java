package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType type;
    Collection<ChessMove> pieceMoves;

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
        pieceMoves = new ArrayList<>();

        if(type == PieceType.PAWN){
            pieceMoves = new PawnMoves().getPawnMoves(board, myPosition, pieceColor);
        }
        else if(type == PieceType.ROOK){
            pieceMoves = new RookMoves().getRookMoves(board, myPosition, pieceColor);
        }
        else if(type == PieceType.KNIGHT){
            pieceMoves = new KnightMoves().getKnightMoves(board, myPosition, pieceColor);
        }
        else if(type == PieceType.BISHOP){
            pieceMoves = new BishopMoves().getBishopMoves(board, myPosition, pieceColor);
        }
        else if (type == PieceType.QUEEN) {
            pieceMoves = new QueenMoves().getQueenMoves(board, myPosition, pieceColor);
        }
        else if (type == PieceType.KING){
            pieceMoves = new KingMoves().getKingMoves(board, myPosition, pieceColor);
        }
        else {
            throw new RuntimeException("type not found");
        }

        return pieceMoves;
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

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type, pieceMoves);
    }
}
