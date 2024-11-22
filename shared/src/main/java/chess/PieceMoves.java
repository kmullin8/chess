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

        // Define the possible knight move offsets
        int[][] knightOffsets = {
                {-2, -1}, {-2, 1},  // down-left, down-right
                {-1, 2}, {1, 2},   // right-down, right-up
                {2, 1}, {2, -1},  // up-right, up-left
                {1, -2}, {-1, -2}  // left-up, left-down
        };

        // Use the helper function to process each potential move
        for (int[] offset : knightOffsets) {
            int newRow = myPosition.getRow() + offset[0];
            int newCol = myPosition.getColumn() + offset[1];
            addValidKnightMove(knightMoves, board, myPosition, newRow, newCol, pieceColor);
        }

        return knightMoves;
    }

    // Helper method to validate and add knight moves
    private void addValidKnightMove(Collection<ChessMove> knightMoves, ChessBoard board
            , ChessPosition myPosition, int newRow, int newCol, ChessGame.TeamColor pieceColor) {
        if (newRow > 0 && newCol > 0 && newRow <= 8 && newCol <= 8) { // Check bounds
            ChessPosition nextPosition = new ChessPosition(newRow, newCol);
            ChessPiece targetPiece = board.getPiece(nextPosition);

            // Add the move if the position is empty or contains an opponent's piece
            if (targetPiece == null || targetPiece.getTeamColor() != pieceColor) {
                knightMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        Collection<ChessMove> kingMoves = new ArrayList<>();
        int[][] directions = {
                {0, 1},   // right
                {1, 1},   // up-right
                {1, 0},   // up
                {1, -1},  // up-left
                {0, -1},  // left
                {-1, -1}, // down-left
                {-1, 0},  // down
                {-1, 1}   // down-right
        };

        for (int[] direction : directions) {
            addMoveIfValid(board, myPosition, pieceColor, kingMoves, direction[0], direction[1]);
        }

        return kingMoves;
    }

    private void addMoveIfValid(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor,
                                Collection<ChessMove> moves, int rowOffset, int colOffset) {
        int newRow = myPosition.getRow() + rowOffset;
        int newCol = myPosition.getColumn() + colOffset;

        if (newRow > 0 && newRow <= 8 && newCol > 0 && newCol <= 8) { // Inside the board
            ChessPosition nextPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);

            if (pieceAtNextPosition == null || pieceAtNextPosition.getTeamColor() != pieceColor) {
                moves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }
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
            if (forwardLeft.getRow() <= 8 || forwardLeft.getColumn() <= 8 || forwardLeft.getRow() > 0
                    || forwardLeft.getColumn() > 0) { // check bounds
                if (board.getPiece(forwardLeft) != null && board.getPiece(forwardLeft).getTeamColor() != pieceColor) {
                    if (forwardLeft.getRow() == promotionRow) {
                        addPromotionMoves(pawnMoves, myPosition, forwardLeft);
                    } else {
                        pawnMoves.add(new ChessMove(myPosition, forwardLeft, null));
                    }
                }
            }

            if (forwardRight.getRow() <= 8 || forwardRight.getColumn() <= 8 || forwardRight.getRow() > 0
                    || forwardRight.getColumn() > 0) { // check bounds
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

    private Collection<ChessMove> generateMoves(ChessBoard board, ChessPosition startPosition
            , ChessGame.TeamColor pieceColor, int rowIncrement, int colIncrement) {
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
