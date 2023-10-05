package chess;

import passoffTests.TestFactory;

import java.util.ArrayList;
import java.util.Collection;

public class Rook extends ChessPieceImpl{
    public Rook(ChessGame.TeamColor teamColorInput) {
        super(teamColorInput, PieceType.ROOK);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> rookMoves = new ArrayList<>();
        boolean nextPositionValid;
        ChessPositionImpl inicialPosition;
        //validMoves.add(TestFactory.getNewMove(position, TestFactory.getNewPosition(2, 4), null));

        //generate all valid rook moves and add to rookMoves

        //generate right moves
        nextPositionValid = true;
        inicialPosition = (ChessPositionImpl) myPosition;
        while(nextPositionValid){
            ChessPositionImpl nextPosition;
            int newRow = inicialPosition.getRow();
            int newCol = inicialPosition.getColumn() + 1;

            if(newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8){ // is new position outside of board
                nextPositionValid = false;
            }
            else {
                //initialize new position
                nextPosition = new ChessPositionImpl(newRow, newCol);

                if(board.getPiece(nextPosition) != null){ // enter if next position has a piece
                    nextPositionValid = false;
                }
                else { //next position does not have a piece add move
                    rookMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        //generate left moves
        nextPositionValid = true;
        inicialPosition = (ChessPositionImpl) myPosition;
        while(nextPositionValid){
            ChessPositionImpl nextPosition;
            int newRow = inicialPosition.getRow();
            int newCol = inicialPosition.getColumn() - 1;

            if(newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8){ // is new position outside of board
                nextPositionValid = false;
            }
            else {
                //initialize new position
                nextPosition = new ChessPositionImpl(newRow, newCol);

                if(board.getPiece(nextPosition) != null){ // enter if next position has a piece
                    nextPositionValid = false;
                }
                else { //next position does not have a piece add move
                    rookMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        //generate down moves
        nextPositionValid = true;
        inicialPosition = (ChessPositionImpl) myPosition;
        while(nextPositionValid){
            ChessPositionImpl nextPosition;
            int newRow = inicialPosition.getRow() - 1;
            int newCol = inicialPosition.getColumn();

            if(newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8){ // is new position outside of board
                nextPositionValid = false;
            }
            else {
                //initialize new position
                nextPosition = new ChessPositionImpl(newRow, newCol);

                if(board.getPiece(nextPosition) != null){ // enter if next position has a piece
                    nextPositionValid = false;
                }
                else { //next position does not have a piece add move
                    rookMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        //generate up moves
        nextPositionValid = true;
        inicialPosition = (ChessPositionImpl) myPosition;
        while(nextPositionValid){
            ChessPositionImpl nextPosition;
            int newRow = inicialPosition.getRow() + 1;
            int newCol = inicialPosition.getColumn();

            if(newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8){ // is new position outside of board
                nextPositionValid = false;
            }
            else {
                //initialize new position
                nextPosition = new ChessPositionImpl(newRow, newCol);

                if(board.getPiece(nextPosition) != null){ // enter if next position has a piece
                    nextPositionValid = false;
                }
                else { //next position does not have a piece add move
                    rookMoves.add(new ChessMoveImpl(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        //test code
//        for (ChessMove move : rookMoves) {
//            System.out.println(move.getEndPosition().getRow() + ", " + move.getEndPosition().getColumn());
//        }

        return rookMoves;
    }
}
