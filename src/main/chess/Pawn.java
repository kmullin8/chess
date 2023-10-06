package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends ChessPieceImpl{

    public Pawn(ChessGame.TeamColor teamColorInput) {
        super(teamColorInput, PieceType.PAWN);
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> pawnMoves = new ArrayList<>();
        int newRow;
        int newCol;
        ChessPositionImpl nextPosition;

        //generate all valid pawn moves and add to pawnMoves
        //generate right move
        if(this.getTeamColor() == ChessGame.TeamColor.BLACK) {
            newRow = myPosition.getRow() - 1;
            newCol = myPosition.getColumn();

            if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                //initialize new position
                nextPosition = new ChessPositionImpl(newRow, newCol);

                if(newRow == 1){

                    pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                }
                else if (board.getPiece(nextPosition) == null) { // enter if next position has a piece

                    pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));

                    if(myPosition.getRow() == 7){

                        newRow = myPosition.getRow() - 2;
                        nextPosition = new ChessPositionImpl(newRow, newCol);

                        if (board.getPiece(nextPosition) == null) { // enter if next position has a piece

                            pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));
                        }
                    }
                }
            }

            newCol = myPosition.getColumn() + 1;
            nextPosition = new ChessPositionImpl(newRow, newCol);

            if(board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {

                    if(newRow == 1){

                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                    }
                    else{
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));
                    }
                }
            }

            newCol = myPosition.getColumn() - 1;
            nextPosition = new ChessPositionImpl(newRow, newCol);

            if(board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {

                    if(newRow == 1){

                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                    }
                    else{
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));
                    }
                }
            }
        }
        else {
            newRow = myPosition.getRow() + 1;
            newCol = myPosition.getColumn();

            if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                //initialize new position
                nextPosition = new ChessPositionImpl(newRow, newCol);

                if(newRow == 8){

                    pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                }
                else if(board.getPiece(nextPosition) == null) { // enter if next position has a piece

                    pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));

                    if(myPosition.getRow() == 2){

                        newRow = myPosition.getRow() + 2;
                        nextPosition = new ChessPositionImpl(newRow, newCol);

                        if (board.getPiece(nextPosition) == null) { // enter if next position has a piece

                            pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));
                        }
                    }
                }
            }

            newCol = myPosition.getColumn() + 1;
            nextPosition = new ChessPositionImpl(newRow, newCol);

            if(board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {

                    if(newRow == 8){

                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                    }
                    else{
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));
                    }
                }
            }

            newCol = myPosition.getColumn() - 1;
            nextPosition = new ChessPositionImpl(newRow, newCol);

            if(board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {

                    if(newRow == 8){

                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                    }
                    else{
                        pawnMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));
                    }
                }
            }
        }

        //test code
//        for (ChessMove move : pawnMoves) {
//            System.out.println(move.getEndPosition().getRow() + ", " + move.getEndPosition().getColumn());
//        }

        return pawnMoves;
    }
}
