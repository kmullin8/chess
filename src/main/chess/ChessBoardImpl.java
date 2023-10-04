package chess;

import passoffTests.TestFactory;

import java.util.HashMap;
import java.util.Map;

public class ChessBoardImpl implements ChessBoard {

    public Map<ChessPosition, ChessPiece> chessBoard;

    public ChessBoardImpl(){
        this.chessBoard = new HashMap<>();
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {

        chessBoard.put(position, piece);
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return chessBoard.get(position);
    }

    @Override
    public void resetBoard() {
        chessBoard.clear();

        //white back row
        addPiece(new ChessPositionImpl(1, 1), new Rook(ChessGame.TeamColor.WHITE));
        addPiece(new ChessPositionImpl(1, 2), new Knight(ChessGame.TeamColor.WHITE));
        addPiece(new ChessPositionImpl(1, 3), new Bishop(ChessGame.TeamColor.WHITE));
        addPiece(new ChessPositionImpl(1, 4), new Queen(ChessGame.TeamColor.WHITE));
        addPiece(new ChessPositionImpl(1, 5), new King(ChessGame.TeamColor.WHITE));
        addPiece(new ChessPositionImpl(1, 6), new Bishop(ChessGame.TeamColor.WHITE));
        addPiece(new ChessPositionImpl(1, 7), new Knight(ChessGame.TeamColor.WHITE));
        addPiece(new ChessPositionImpl(1, 8), new Rook(ChessGame.TeamColor.WHITE));

        //add pawns
        for (int col = 1; col <= 8; col++) {
            addPiece(new ChessPositionImpl(2, col), new Pawn(ChessGame.TeamColor.WHITE));
        }

        //black back row
        addPiece(new ChessPositionImpl(8, 1), new Rook(ChessGame.TeamColor.BLACK));
        addPiece(new ChessPositionImpl(8, 2), new Knight(ChessGame.TeamColor.BLACK));
        addPiece(new ChessPositionImpl(8, 3), new Bishop(ChessGame.TeamColor.BLACK));
        addPiece(new ChessPositionImpl(8, 4), new Queen(ChessGame.TeamColor.BLACK));
        addPiece(new ChessPositionImpl(8, 5), new King(ChessGame.TeamColor.BLACK));
        addPiece(new ChessPositionImpl(8, 6), new Bishop(ChessGame.TeamColor.BLACK));
        addPiece(new ChessPositionImpl(8, 7), new Knight(ChessGame.TeamColor.BLACK));
        addPiece(new ChessPositionImpl(8, 8), new Rook(ChessGame.TeamColor.BLACK));

        //add pawns
        for (int col = 1; col <= 8; col++) {
            addPiece(new ChessPositionImpl(7, col), new Pawn(ChessGame.TeamColor.BLACK));
        }

        // Debug statements to check if pieces are added correctly
//        for (int row = 1; row <= 8; row++) {
//            for (int col = 1; col <= 8; col++) {
//                ChessPosition position = new ChessPositionImpl(row, col);
//                ChessPiece piece = getPiece(position);
//                if (piece != null) {
//                    System.out.println("Piece at " + position + ": " + piece.getPieceType());
//                } else {
//                    System.out.println("No piece at " + position);
//                }
//            }
//        }
    }
}
