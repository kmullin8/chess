package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMoves {

    public Collection<ChessMove> linearMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        Collection<ChessMove> pieceMoves = new ArrayList<>();

        boolean nextPositionValid;
        ChessPosition currentPosition;

        //down
        nextPositionValid = true;
        currentPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = currentPosition.getRow();
            int newCol = currentPosition.getColumn() - 1;

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        currentPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    currentPosition = nextPosition;
                }
            }
        }

        //right
        nextPositionValid = true;
        currentPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = currentPosition.getRow() + 1;
            int newCol = currentPosition.getColumn();

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        currentPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    currentPosition = nextPosition;
                }
            }
        }

        //up
        nextPositionValid = true;
        currentPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = currentPosition.getRow();
            int newCol = currentPosition.getColumn() + 1;

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        currentPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    currentPosition = nextPosition;
                }
            }
        }

        //left
        nextPositionValid = true;
        currentPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = currentPosition.getRow() - 1;
            int newCol = currentPosition.getColumn();

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        currentPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    currentPosition = nextPosition;
                }
            }
        }
        return pieceMoves;
    }

    public Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        Collection<ChessMove> pieceMoves = new ArrayList<>();

        boolean nextPositionValid;
        ChessPosition currentPosition;

        //down right
        nextPositionValid = true;
        currentPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = currentPosition.getRow() + 1;
            int newCol = currentPosition.getColumn() - 1;

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        currentPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    currentPosition = nextPosition;
                }
            }
        }

        //up right
        nextPositionValid = true;
        currentPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = currentPosition.getRow() + 1;
            int newCol = currentPosition.getColumn() + 1;

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        currentPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    currentPosition = nextPosition;
                }
            }
        }

        //up left
        nextPositionValid = true;
        currentPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = currentPosition.getRow() - 1;
            int newCol = currentPosition.getColumn() + 1;

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        currentPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    currentPosition = nextPosition;
                }
            }
        }

        //down left
        nextPositionValid = true;
        currentPosition = (ChessPosition) myPosition;
        while (nextPositionValid) {
            ChessPosition nextPosition;
            int newRow = currentPosition.getRow() - 1;
            int newCol = currentPosition.getColumn() - 1;

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // is new position outside of board
                nextPositionValid = false;
            } else {
                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                        pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                        //set myPosition to next nextPosition to increment move
                        currentPosition = nextPosition;
                    }
                    nextPositionValid = false;
                } else { //next position does not have a piece add move
                    pieceMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                    currentPosition = nextPosition;
                }
            }
        }
        return pieceMoves;
    }

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int rowIncrement, int colIncrement) {
        return null;
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int rowIncrement, int colIncrement) {
        return null;
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int rowIncrement, int colIncrement) {
        return null;
    }
}
