package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves {
    public Collection<ChessMove> getKnightMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor){
        Collection<ChessMove> knightMoves = new ArrayList<>();

        int newRow;
        int newCol;
        ChessPosition nextPosition;

        //generate all valid rook moves and add to rookMoves
        //generate down-left move
        newRow = myPosition.getRow() - 2;
        newCol = myPosition.getColumn() - 1;

        if(newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8){ // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            }
            else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate down-right move
        newRow = myPosition.getRow() - 2;
        newCol = myPosition.getColumn() + 1;

        if(newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8){ // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            }
            else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate right-down
        newRow = myPosition.getRow() - 1;
        newCol = myPosition.getColumn() + 2;

        if(newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8){ // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            }
            else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate right-up
        newRow = myPosition.getRow() + 1;
        newCol = myPosition.getColumn() + 2;

        if(newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8){ // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            }
            else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate up-right
        newRow = myPosition.getRow() + 2;
        newCol = myPosition.getColumn() + 1;

        if(newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8){ // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            }
            else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate up-left
        newRow = myPosition.getRow() + 2;
        newCol = myPosition.getColumn() - 1;

        if(newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8){ // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            }
            else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate left-up
        newRow = myPosition.getRow() + 1;
        newCol = myPosition.getColumn() - 2;

        if(newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8){ // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            }
            else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate left-down
        newRow = myPosition.getRow() - 1;
        newCol = myPosition.getColumn() - 2;

        if(newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8){ // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            }
            else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //test code
//        System.out.println("knight moves:" +  myPosition.getRow() + "," + myPosition.getColumn());
//        for (ChessMove move : knightMoves) {
//            System.out.println(move.getEndPosition().getRow() + ", " + move.getEndPosition().getColumn());
//        }

        return knightMoves;
    }
}
