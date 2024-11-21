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

        int newRow;
        int newCol;
        ChessPosition nextPosition;

        //generate all valid pawn moves and add to pawnMoves
        //generate right move
        if (pieceColor == ChessGame.TeamColor.BLACK) {
            newRow = myPosition.getRow() - 1;
            newCol = myPosition.getColumn();

            if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (newRow == 1) {

                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                } else if (board.getPiece(nextPosition) == null) { // enter if next position has a piece

                    pawnMoves.add(new ChessMove(myPosition, nextPosition, null));

                    if (myPosition.getRow() == 7) {

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

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {

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

            newCol = myPosition.getColumn() - 1;
            nextPosition = new ChessPosition(newRow, newCol);

            if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                if (board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {

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
        } else {
            newRow = myPosition.getRow() + 1;
            newCol = myPosition.getColumn();

            if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                //initialize new position
                nextPosition = new ChessPosition(newRow, newCol);

                if (newRow == 8) {

                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
                } else if (board.getPiece(nextPosition) == null) { // enter if next position does not have a piece

                    pawnMoves.add(new ChessMove(myPosition, nextPosition, null));

                    if (myPosition.getRow() == 2) {

                        newRow = myPosition.getRow() + 2;
                        nextPosition = new ChessPosition(newRow, newCol);

                        if (board.getPiece(nextPosition) == null) { // enter if next position does not have a piece

                            pawnMoves.add(new ChessMove(myPosition, nextPosition, null));
                        }
                    }
                }
            }

            //capture
            if (pieceColor == ChessGame.TeamColor.WHITE) {

                newRow = myPosition.getRow() + 1;
                newCol = myPosition.getColumn() + 1;

                if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                    //initialize new position
                    nextPosition = new ChessPosition(newRow, newCol);

                    if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                        if (board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {

                            if (newRow == 8) {

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

                newRow = myPosition.getRow() + 1;
                newCol = myPosition.getColumn() - 1;

                if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // is new position outside of board

                    //initialize new position
                    nextPosition = new ChessPosition(newRow, newCol);

                    if (board.getPiece(nextPosition) != null) { // enter if next position has a piece

                        if (board.getPiece(nextPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {

                            if (newRow == 8) {

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
            } else {
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


        return pawnMoves;
    }

    private Collection<ChessMove> generateMoves(
            ChessBoard board,
            ChessPosition startPosition,
            ChessGame.TeamColor pieceColor,
            int rowIncrement,
            int colIncrement
    ) {
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
}
