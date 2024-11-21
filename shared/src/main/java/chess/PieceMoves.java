package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMoves {

    public Collection<ChessMove> linearMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        Collection<ChessMove> pieceMoves = new ArrayList<>();
        // Handle moves in all four linear directions
        pieceMoves.addAll(generateMoves(board, myPosition, pieceColor, 0, -1)); // down
        pieceMoves.addAll(generateMoves(board, myPosition, pieceColor, 1, 0));  // right
        pieceMoves.addAll(generateMoves(board, myPosition, pieceColor, 0, 1));  // up
        pieceMoves.addAll(generateMoves(board, myPosition, pieceColor, -1, 0)); // left
        return pieceMoves;
    }

    public Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        Collection<ChessMove> pieceMoves = new ArrayList<>();
        // Handle moves in all four diagonal directions
        pieceMoves.addAll(generateMoves(board, myPosition, pieceColor, 1, -1));  // down right
        pieceMoves.addAll(generateMoves(board, myPosition, pieceColor, 1, 1));   // up right
        pieceMoves.addAll(generateMoves(board, myPosition, pieceColor, -1, 1));  // up left
        pieceMoves.addAll(generateMoves(board, myPosition, pieceColor, -1, -1)); // down left
        return pieceMoves;
    }

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        Collection<ChessMove> knightMoves = new ArrayList<>();

        int newRow;
        int newCol;
        ChessPosition nextPosition;

        //generate all valid rook moves and add to rookMoves
        //generate down-left move
        newRow = myPosition.getRow() - 2;
        newCol = myPosition.getColumn() - 1;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate down-right move
        newRow = myPosition.getRow() - 2;
        newCol = myPosition.getColumn() + 1;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate right-down
        newRow = myPosition.getRow() - 1;
        newCol = myPosition.getColumn() + 2;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate right-up
        newRow = myPosition.getRow() + 1;
        newCol = myPosition.getColumn() + 2;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate up-right
        newRow = myPosition.getRow() + 2;
        newCol = myPosition.getColumn() + 1;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate up-left
        newRow = myPosition.getRow() + 2;
        newCol = myPosition.getColumn() - 1;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate left-up
        newRow = myPosition.getRow() + 1;
        newCol = myPosition.getColumn() - 2;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        //generate left-down
        newRow = myPosition.getRow() - 1;
        newCol = myPosition.getColumn() - 2;

        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

            //initialize new position
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { //enter if piece at next position is different color
                    knightMoves.add(new ChessMove(myPosition, nextPosition, null));

                    //set myPosition to next nextPosition to increment move
                }
            } else {

                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }


        return knightMoves;
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
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

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        Collection<ChessMove> pawnMoves = new ArrayList<>();

        int direction = (pieceColor == ChessGame.TeamColor.WHITE) ? 1 : -1;
        int startRow = (pieceColor == ChessGame.TeamColor.WHITE) ? 2 : 7;
        int promotionRow = (pieceColor == ChessGame.TeamColor.WHITE) ? 8 : 1;

        ChessPosition forward = new ChessPosition(myPosition.getRow() + direction, myPosition.getColumn());
        ChessPosition forwardLeft = new ChessPosition(myPosition.getRow() + direction, myPosition.getColumn() - 1);
        ChessPosition forwardRight = new ChessPosition(myPosition.getRow() + direction, myPosition.getColumn() + 1);

        if (forward.getRow() <= 8 || forward.getColumn() <= 8 || forward.getRow() > 0 || forward.getColumn() > 0) { // check bounds

            // Forward move
            if (board.getPiece(forward) == null) {
                if (forward.getRow() == promotionRow) {
                    addPromotionMoves(pawnMoves, myPosition, forward);
                } else {
                    pawnMoves.add(new ChessMove(myPosition, forward, null));
                }
            }

            // capture moves
            //capture left
            if (forwardLeft.getRow() <= 8 || forwardLeft.getColumn() <= 8 || forwardLeft.getRow() > 0 || forwardLeft.getColumn() > 0) { // check bounds
                if (board.getPiece(forwardLeft) != null && board.getPiece(forwardLeft).getTeamColor() != pieceColor) {
                    if (forwardLeft.getRow() == promotionRow) {
                        addPromotionMoves(pawnMoves, myPosition, forwardLeft);
                    } else {
                        pawnMoves.add(new ChessMove(myPosition, forwardLeft, null));
                    }
                }
            }

            if (forwardRight.getRow() <= 8 || forwardRight.getColumn() <= 8 || forwardRight.getRow() > 0 || forwardRight.getColumn() > 0) { // check bounds
                if (board.getPiece(forwardRight) != null && board.getPiece(forwardRight).getTeamColor() != pieceColor) {
                    if (forwardRight.getRow() == promotionRow) {
                        addPromotionMoves(pawnMoves, myPosition, forwardRight);
                    } else {
                        pawnMoves.add(new ChessMove(myPosition, forwardRight, null));
                    }
                }
            }
        }


        // Double forward move
        if (myPosition.getRow() == startRow) {
            ChessPosition doubleForward = new ChessPosition(myPosition.getRow() + 2 * direction, myPosition.getColumn());
            if (board.getPiece(doubleForward) == null && board.getPiece(forward) == null) {
                pawnMoves.add(new ChessMove(myPosition, doubleForward, null));
            }
        }

        return pawnMoves;
    }

    private Collection<ChessMove> generateMoves(ChessBoard board, ChessPosition startPosition, ChessGame.TeamColor pieceColor, int rowIncrement, int colIncrement) {
        Collection<ChessMove> moves = new ArrayList<>();
        boolean nextPositionValid = true;
        ChessPosition currentPosition = startPosition;

        while (nextPositionValid) {
            int newRow = currentPosition.getRow() + rowIncrement;
            int newCol = currentPosition.getColumn() + colIncrement;

            if (newRow <= 0 || newCol <= 0 || newRow > 8 || newCol > 8) { // Check bounds
                nextPositionValid = false;
            } else {
                ChessPosition nextPosition = new ChessPosition(newRow, newCol);

                if (board.getPiece(nextPosition) != null) { // If there is a piece at the next position
                    if (board.getPiece(nextPosition).getTeamColor() != pieceColor) { // Opponent's piece
                        moves.add(new ChessMove(startPosition, nextPosition, null));
                    }
                    nextPositionValid = false; // Stop further moves in this direction
                } else { // Empty square
                    moves.add(new ChessMove(startPosition, nextPosition, null));
                    currentPosition = nextPosition; // Move to the next position
                }
            }
        }

        return moves;
    }

    private void addPromotionMoves(Collection<ChessMove> moves, ChessPosition from, ChessPosition to) {
        moves.add(new ChessMove(from, to, ChessPiece.PieceType.QUEEN));
        moves.add(new ChessMove(from, to, ChessPiece.PieceType.ROOK));
        moves.add(new ChessMove(from, to, ChessPiece.PieceType.BISHOP));
        moves.add(new ChessMove(from, to, ChessPiece.PieceType.KNIGHT));
    }
}
