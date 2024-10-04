package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private TeamColor teamturn;

    public ChessGame() {
        this.board = new ChessBoard();
        board.resetBoard(); //set board
        this.teamturn = TeamColor.WHITE;
    }

    public ChessGame(ChessGame copy) {
        this.board = new ChessBoard(copy.getBoard());
        this.teamturn = copy.getTeamTurn();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamturn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamturn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece currPiece = board.getPiece(startPosition);
        Collection<ChessMove> possMoves = currPiece.pieceMoves(board, startPosition); //all valid moves for current piece
        Collection<ChessMove> validMoves = new ArrayList<>();

        for(ChessMove move : possMoves){
            ChessGame tempGame = new ChessGame(this); //setup tempGame and see if move will leave king in check
            tempGame.board.movePiece(move, currPiece);
            if(!tempGame.isInCheck(currPiece.getTeamColor())){
                validMoves.add(move);
            }
        }
        return validMoves;// test
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece currentPiece = board.getPiece(move.getStartPosition());

        if(currentPiece != null) {
            Collection<ChessMove> pieceMoves = currentPiece.pieceMoves(board, move.startPosition);

            if (getTeamTurn() == currentPiece.getTeamColor()) {
                for (ChessMove possMove : pieceMoves) {
                    if (possMove.endPosition.getRow() == move.endPosition.getRow() && possMove.endPosition.getColumn() == move.endPosition.getColumn()) {

                        ChessGame tempGame = new ChessGame(this); //setup tempGame and see if move will leave king in check
                        tempGame.board.movePiece(move, currentPiece);
                        if(tempGame.isInCheck(currentPiece.getTeamColor())){
                            throw new InvalidMoveException("Invalid move: " + move);
                        }

                        board.movePiece(move, currentPiece);
                        if(teamturn == TeamColor.WHITE){ // chang team turn
                            teamturn = TeamColor.BLACK;
                        }else{
                            teamturn = TeamColor.WHITE;
                        }
                        return;
                    }
                }
            }
        }
        throw new InvalidMoveException("Invalid move: " + move);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        boolean inCheck = false;

        for(int row = 8; row >= 1; row--){ // iterate through board
            for (int col = 1; col <= 8; col++){
                ChessPosition currPosition = new ChessPosition(row, col);
                ChessPiece currPiece = board.getPiece(currPosition);

                if(currPiece != null) {
                    if(currPiece.getTeamColor() != teamColor) {// only look at moves from other team
                        Collection<ChessMove> pieceMoves = currPiece.pieceMoves(board, currPosition); //all valid moves for current piece

                        for (ChessMove move : pieceMoves) { //check if king is inside pieceMoves
                            ChessPiece targetPiece = board.getPiece(move.getEndPosition());
                            if (targetPiece != null) {
                                if (targetPiece.getPieceType() == ChessPiece.PieceType.KING) {
                                    inCheck = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return inCheck;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        if (isInCheck(teamColor) == false){
            return false;
        }

        for(int row = 8; row >= 1; row--){ // iterate through board
            for (int col = 1; col <= 8; col++){
                ChessPosition currPosition = new ChessPosition(row, col);
                ChessPiece currPiece = board.getPiece(currPosition);

                if(currPiece != null) {
                    if(currPiece.getTeamColor() == teamColor) {// only look at moves from current team
                        Collection<ChessMove> pieceMoves = currPiece.pieceMoves(board, currPosition); //all valid moves for current piece

                        for (ChessMove move : pieceMoves) {
                            ChessGame tempGame = new ChessGame(this); //setup tempGame

                            tempGame.board.movePiece(move, currPiece);
                            if(tempGame.isInCheck(teamColor) == false){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        for(int row = 8; row >= 1; row--){ // iterate through board
            for (int col = 1; col <= 8; col++){
                ChessPosition currPosition = new ChessPosition(row, col);
                ChessPiece currPiece = board.getPiece(currPosition);

                if(currPiece != null) {
                    if(currPiece.getTeamColor() == teamColor) {// only look at moves from current team
                        Collection<ChessMove> pieceMoves = currPiece.pieceMoves(board, currPosition); //all valid moves for current piece
                        for (ChessMove move : pieceMoves) {
                            ChessGame tempGame = this;
                            tempGame.board.movePiece(move, currPiece);
                            if(tempGame.isInCheck(teamColor) == false){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && teamturn == chessGame.teamturn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamturn);
    }
}
