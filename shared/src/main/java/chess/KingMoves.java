package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoves {
    public Collection<ChessMove> getKingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        Collection<ChessMove> kingMoves = new ArrayList<>();

        int newRow;
        int newCol;
        ChessPosition nextPosition;

        //generate all valid king moves and add to kingMoves
        //generate right move
        newRow = myPosition.getRow();
        newCol = myPosition.getColumn() + 1;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    kingMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                kingMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate up-right move
        newRow = myPosition.getRow() + 1;
        newCol = myPosition.getColumn() + 1;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    kingMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                kingMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate up move
        newRow = myPosition.getRow() + 1;
        newCol = myPosition.getColumn();

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    kingMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                kingMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate up-left move
        newRow = myPosition.getRow() + 1;
        newCol = myPosition.getColumn() - 1;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    kingMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                kingMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate left move
        newRow = myPosition.getRow();
        newCol = myPosition.getColumn() - 1;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    kingMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                kingMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate left-down move
        newRow = myPosition.getRow() - 1;
        newCol = myPosition.getColumn() - 1;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    kingMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                kingMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate down move
        newRow = myPosition.getRow() - 1;
        newCol = myPosition.getColumn();

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    kingMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                kingMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate down-right move
        newRow = myPosition.getRow() - 1;
        newCol = myPosition.getColumn() + 1;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    kingMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                kingMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        return kingMoves;
    }
}
