package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoves {
    public Collection<ChessMove> getBishopMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor){
        Collection<ChessMove> bishopMoves = new ArrayList<>();

        boolean nextPositionValid;
        ChessPosition inicialPosition;

        //generate all valid rook moves and add to rookMoves
        //generate down-left
        nextPositionValid = true;
        inicialPosition = (ChessPosition) myPosition;
        while(nextPositionValid){
            ChessPosition nextPosition;
            int newRow = inicialPosition.getRow() - 1;
            int newCol = inicialPosition.getColumn() - 1;

            if(newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8){ // is new position outside of board
                nextPositionValid = false;
            }
            else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                    if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                        bishopMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        inicialPosition = nextPosition;
                    }
                    nextPositionValid = false;
                }
                else { //next position does not have a piece add move
                    bishopMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        //generate up-left
        nextPositionValid = true;
        inicialPosition = (ChessPosition) myPosition;
        while(nextPositionValid){
            ChessPosition nextPosition;
            int newRow = inicialPosition.getRow() + 1;
            int newCol = inicialPosition.getColumn() - 1;

            if(newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8){ // is new position outside of board
                nextPositionValid = false;
            }
            else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                    if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                        bishopMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        inicialPosition = nextPosition;
                    }
                    nextPositionValid = false;
                }
                else { //next position does not have a piece add move
                    bishopMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        //generate down-right
        nextPositionValid = true;
        inicialPosition = (ChessPosition) myPosition;
        while(nextPositionValid){
            ChessPosition nextPosition;
            int newRow = inicialPosition.getRow() - 1;
            int newCol = inicialPosition.getColumn() + 1;

            if(newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8){ // is new position outside of board
                nextPositionValid = false;
            }
            else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                    if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                        bishopMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        inicialPosition = nextPosition;
                    }
                    nextPositionValid = false;
                }
                else { //next position does not have a piece add move
                    bishopMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        //generate up-right
        nextPositionValid = true;
        inicialPosition = (ChessPosition) myPosition;
        while(nextPositionValid){
            ChessPosition nextPosition;
            int newRow = inicialPosition.getRow() + 1;
            int newCol = inicialPosition.getColumn() + 1;

            if(newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8){ // is new position outside of board
                nextPositionValid = false;
            }
            else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if(board.getPiece(nextPosition) != null){ // enter if next position has a piece

                    if(board.getPiece(nextPosition).getTeamColor() != pieceColor){ //enter if piece at next position is different color
                        bishopMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        inicialPosition = nextPosition;
                    }
                    nextPositionValid = false;
                }
                else { //next position does not have a piece add move
                    bishopMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        //test code
//        System.out.println("rook moves:");
//        for (ChessMove move : rookMoves) {
//            System.out.println(move.getEndPosition().getRow() + ", " + move.getEndPosition().getColumn());
//        }

        return bishopMoves;
    }
}
