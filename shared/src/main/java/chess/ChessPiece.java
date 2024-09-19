package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
            pieceMoves = getPawnMoves(board, myPosition);
        }
        else if(type == PieceType.ROOK){
            pieceMoves = getRookMoves(board, myPosition);
        }
        else if(type == PieceType.KNIGHT){
            pieceMoves = getKnightMoves(board, myPosition);
        }
        else if(type == PieceType.BISHOP){
            pieceMoves = getBishopMoves(board, myPosition);
        }
        else if (type == PieceType.QUEEN) {
            pieceMoves = getQueenMoves(board, myPosition);
        }
        else if (type == PieceType.KING){
            pieceMoves = getKingMoves(board, myPosition);
        }
        else {
            throw new RuntimeException("type not found");
        }

        return pieceMoves;
    }

    public Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> pawnMoves = new ArrayList<>();

        return pawnMoves;
    }
    public Collection<ChessMove> getRookMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> rookMoves = new ArrayList<>();
        return rookMoves;
    }

    public Collection<ChessMove> getKnightMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> knightMoves = new ArrayList<>();
        return knightMoves;
    }

    public Collection<ChessMove> getBishopMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> bishopMoves = new ArrayList<>();
        return bishopMoves;
    }

    public Collection<ChessMove> getQueenMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> queenMoves = new ArrayList<>();
        return queenMoves;
    }

    public Collection<ChessMove> getKingMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> kingMoves = new ArrayList<>();
        return kingMoves;
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
