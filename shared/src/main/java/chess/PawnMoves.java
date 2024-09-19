package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoves {

    public Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor){
        Collection<ChessMove> pawnMoves = new ArrayList<>();

        int newRow;
        int newCol;
        ChessPosition nextPosition;

        //generate all valid pawn moves and add to pawnMoves
        //generate right move
        if(pieceColor == ChessGame.TeamColor.BLACK) {
            newRow = myPosition.getRow() - 1;
            newCol = myPosition.getColumn();

            if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if(newRow == 1){

                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                }
                else if (board.getPiece(nextPosition) == null) { // enter if next position has a piece

                    pawnMoves.add(new ChessMove(myPosition, nextPosition, null));

                    if(myPosition.getRow() == 7){

                        newRow = myPosition.getRow() - 2;
                        nextPosition = new ChessPosition(newRow, newCol);

                        if (board.getPiece(nextPosition) == null) { // enter if next position has a piece

                            pawnMoves.add(new ChessMove(myPosition, nextPosition, null));
                        }
                    }
                }
            }

            newCol = myPosition.getColumn() + 1;
            nextPosition = new ChessPosition(newRow, newCol);

            if(board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {

                    if(newRow == 1){

                        pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                    }
                    else{
                        pawnMoves.add(new ChessMove(myPosition, nextPosition, null));
                    }
                }
            }

            newCol = myPosition.getColumn() - 1;
            nextPosition = new ChessPosition(newRow, newCol);

            if(board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {

                    if(newRow == 1){

                        pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                    }
                    else{
                        pawnMoves.add(new ChessMove(myPosition, nextPosition, null));
                    }
                }
            }
        }
        else {
            newRow = myPosition.getRow() + 1;
            newCol = myPosition.getColumn();

            if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if(newRow == 8){

                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                }
                else if(board.getPiece(nextPosition) == null) { // enter if next position does not have a piece

                    pawnMoves.add(new ChessMove(myPosition, nextPosition, null));

                    if(myPosition.getRow() == 2){

                        newRow = myPosition.getRow() + 2;
                        nextPosition = new ChessPosition(newRow, newCol);

                        if (board.getPiece(nextPosition) == null) { // enter if next position does not have a piece

                            pawnMoves.add(new ChessMove(myPosition, nextPosition, null));
                        }
                    }
                }
            }

            //capture
            if(pieceColor == ChessGame.TeamColor.WHITE) {

                newRow = myPosition.getRow() + 1;
                newCol = myPosition.getColumn() + 1;

                if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                    //initialize new position
                    nextPosition = new ChessPosition(newRow, newCol);

                    if(board.getPiece(nextPosition) != null) { // enter if next position has a piece

                        if(board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {

                            if(newRow == 8){

                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                            }
                            else{
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, null));
                            }
                        }
                    }
                }

                newRow = myPosition.getRow() + 1;
                newCol = myPosition.getColumn() - 1;

                if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                    //initialize new position
                    nextPosition = new ChessPosition(newRow, newCol);

                    if(board.getPiece(nextPosition) != null) { // enter if next position has a piece

                        if(board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {

                            if(newRow == 8){

                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                            }
                            else{
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, null));
                            }
                        }
                    }
                }
            }
            else {
                newRow = myPosition.getRow() - 1;
                newCol = myPosition.getColumn() - 1;

                if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                    //initialize new position
                    nextPosition = new ChessPosition(newRow, newCol);

                    if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                        if (board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {

                            if (newRow == 1) {

                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                            } else {
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, null));
                            }
                        }
                    }
                }

                newRow = myPosition.getRow() - 1;
                newCol = myPosition.getColumn() - 1;

                if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                    //initialize new position
                    nextPosition = new ChessPosition(newRow, newCol);

                    if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                        if (board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {

                            if (newRow == 1) {

                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                            } else {
                                pawnMoves.add(new ChessMove(myPosition, nextPosition, null));
                            }
                        }
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
