package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoves {
    public Collection<ChessMove> getRookMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        Collection<ChessMove> rookMoves = new ArrayList<>();

        boolean nextPositionValid;
        ChessPosition inicialPosition;

        //generate all valid rook moves and add to rookMoves
        //generate right moves
        nextPositionValid = true;
        inicialPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = inicialPosition.getRow();
            int newCol = inicialPosition.getColumn() + 1;

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        rookMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        inicialPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    rookMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        //generate left moves
        nextPositionValid = true;
        inicialPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = inicialPosition.getRow();
            int newCol = inicialPosition.getColumn() - 1;

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        rookMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        inicialPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    rookMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        //generate down moves
        nextPositionValid = true;
        inicialPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = inicialPosition.getRow() - 1;
            int newCol = inicialPosition.getColumn();

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        rookMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        inicialPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    rookMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        //generate up moves
        nextPositionValid = true;
        inicialPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = inicialPosition.getRow() + 1;
            int newCol = inicialPosition.getColumn();

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        rookMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        inicialPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    rookMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    inicialPosition = nextPosition;
                }
            }
        }

        return rookMoves;
    }
}
