package chess;

import passoffTests.TestFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChessBoardImpl implements ChessBoard {

    public Map<ChessPosition, ChessPiece> chessBoard;
    private ChessPositionImpl kingPositionWhite;
    private ChessPositionImpl kingPositionBlack;

    public ChessBoardImpl(){
        this.chessBoard = new HashMap<>();
        kingPositionWhite = null;
        kingPositionBlack = null;
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {

        chessBoard.put(position, piece);

        if(piece.getPieceType() == ChessPiece.PieceType.KING){
            if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                kingPositionWhite = (ChessPositionImpl) position;
            }
            else{
                kingPositionBlack =  (ChessPositionImpl) position;
            }
        }
    }

    public void movePiece (ChessPosition startPosition,ChessPosition endPosition, ChessPiece piece){
        chessBoard.remove(startPosition);
        chessBoard.remove(endPosition);
        addPiece(endPosition, piece);
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

    ChessPositionImpl getKingPosition(ChessGame.TeamColor teamColor){
        if(teamColor == ChessGame.TeamColor.WHITE){
            return kingPositionWhite;
        }
        else{
            return kingPositionBlack;
        }
    }

    boolean isInCheck(ChessGame.TeamColor teamColor){
        boolean inCheck = false;
        ChessPositionImpl kingPosition;

        if(teamColor == ChessGame.TeamColor.WHITE){
            kingPosition = kingPositionWhite;
        }
        else{
            kingPosition = kingPositionBlack;
        }

        if(kingPosition == null){
            return inCheck;
        }

        for (Map.Entry<ChessPosition, ChessPiece> entry : chessBoard.entrySet()) {//iterate through board
            ChessPositionImpl currentPosition = (ChessPositionImpl) entry.getKey();
            ChessPieceImpl currentPiece = (ChessPieceImpl) entry.getValue();

            if(currentPiece.getTeamColor() != teamColor) {
                Collection<ChessMove> validPieceMoves = currentPiece.pieceMoves(this, currentPosition);

                //see if validPieceMoves contains kings position
                for (ChessMove move : validPieceMoves) {

                    if (move.getEndPosition().getColumn() == kingPosition.getColumn() && move.getEndPosition().getRow() == kingPosition.getRow()) {

                        inCheck = true;
                    }
                }
            }
        }

        return inCheck;
    }

    boolean isInCheckmate(ChessGame.TeamColor teamColor) {
        boolean inCheck = true;
        ChessPositionImpl kingPosition;

        if (teamColor == ChessGame.TeamColor.WHITE) {
            kingPosition = kingPositionWhite;
        } else {
            kingPosition = kingPositionBlack;
        }

        if (kingPosition == null) {
            return false;
        }

        for (Map.Entry<ChessPosition, ChessPiece> entry : chessBoard.entrySet()) {
            ChessPositionImpl friendlyPosition = (ChessPositionImpl) entry.getKey();
            ChessPieceImpl friendlyPiece = (ChessPieceImpl) entry.getValue();
            Collection<ChessMove> validPieceMoves = null;

            if (friendlyPiece.getTeamColor() == teamColor) {
                validPieceMoves = friendlyPiece.pieceMoves(this, friendlyPosition);

                for (ChessMove move : validPieceMoves) {
                    ChessBoardImpl chessBoardCopy = new ChessBoardImpl();
                    chessBoardCopy.chessBoard.putAll(chessBoard); // Create a copy of the board

                    chessBoardCopy.movePiece(move.getEndPosition(), move.getStartPosition(), friendlyPiece);
                    if (!chessBoardCopy.isInCheck(teamColor)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
