package chess;

import java.util.Collection;
import java.util.HashSet;

public class ChessGameImpl implements ChessGame {
    private ChessBoard chessBoard;
    private TeamColor currentTeam = TeamColor.WHITE;

    @Override
    public TeamColor getTeamTurn() {
        return currentTeam;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        currentTeam = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = chessBoard.getPiece(startPosition);
        if (piece == null) {
            return null;
        }
     
        return piece.computeValidMoves(startPosition, chessBoard);
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // TODO: Validate the move, check if it's in the validMoves() for the piece.
        // Move the piece on the board.
        // If the move is invalid, throw an InvalidMoveException.
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
 
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
   
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.chessBoard = board;
    }

    @Override
    public ChessBoard getBoard() {
        return chessBoard;
    }
}
