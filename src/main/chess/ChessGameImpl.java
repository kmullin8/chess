package chess;

import java.util.Collection;

public class ChessGameImpl implements ChessGame{

    private ChessBoardImpl chessBoard;
    private TeamColor teamturn;

    public ChessGameImpl(){
        chessBoard = null;
        teamturn = null;
    }
    @Override
    public TeamColor getTeamTurn() {
        return teamturn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        teamturn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        if(startPosition == null){
            return null;
        }

        ChessPieceImpl currentPiece = (ChessPieceImpl) chessBoard.getPiece(startPosition);
        Collection <ChessMove> validPieceMoves = currentPiece.pieceMoves(chessBoard,startPosition);

        return validPieceMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {

        //get piece and valid moves from starting position
        ChessPieceImpl currentPiece = (ChessPieceImpl) chessBoard.getPiece(move.getStartPosition());

        if(currentPiece == null){
            validMoves(null);

            if(getTeamTurn() == TeamColor.WHITE){
                setTeamTurn(TeamColor.BLACK);
            }
            else{
                setTeamTurn(TeamColor.WHITE);
            }
        }
        else if(getTeamTurn() == currentPiece.getTeamColor()) {
            Collection<ChessMove> validPieceMoves = validMoves(move.getStartPosition());

            //check if pieceMoves contains ending position
            if (validPieceMoves.contains(move)) {

                //check to see if move would put king in check
                ChessBoardImpl chessBoardCopy = chessBoard;
                chessBoardCopy.movePiece(move.getStartPosition(), move.getEndPosition(), currentPiece);
                if(getTeamTurn() == TeamColor.WHITE){

                    if(chessBoardCopy.isInCheck(TeamColor.WHITE) == true){
                        throw new InvalidMoveException("Invalid move: " + move);
                    }
                }
                else{
                    if(chessBoardCopy.isInCheck(TeamColor.BLACK) == true){
                        throw new InvalidMoveException("Invalid move: " + move);
                    }
                }

                //if pawn is being promoted
                if(move.getPromotionPiece() != null){
                    ChessPiece.PieceType type = move.getPromotionPiece();
                    ChessGame.TeamColor pieceColor = currentPiece.getTeamColor();

                    if(type == ChessPiece.PieceType.ROOK){
                        chessBoard.movePiece(move.getStartPosition(), move.getEndPosition(), new Rook(pieceColor));
                    }
                    else if(type == ChessPiece.PieceType.KNIGHT){
                        chessBoard.movePiece(move.getStartPosition(), move.getEndPosition(), new Knight(pieceColor));
                    }
                    else if(type == ChessPiece.PieceType.BISHOP){
                        chessBoard.movePiece(move.getStartPosition(), move.getEndPosition(), new Bishop(pieceColor));
                    }
                    else if(type == ChessPiece.PieceType.QUEEN){
                        chessBoard.movePiece(move.getStartPosition(), move.getEndPosition(), new Queen(pieceColor));
                    }

//                    switch (type) {
//                        case ROOK:
//                            chessBoard.movePiece(move.getStartPosition(), move.getEndPosition(), new Rook(pieceColor));
//                        case KNIGHT:
//                            chessBoard.movePiece(move.getStartPosition(), move.getEndPosition(), new Knight(pieceColor));
//                        case BISHOP:
//                            chessBoard.movePiece(move.getStartPosition(), move.getEndPosition(), new Bishop(pieceColor));
//                        case QUEEN:
//                            chessBoard.movePiece(move.getStartPosition(), move.getEndPosition(), new Queen(pieceColor));
//                    }
                }
                else{
                   //move piece
                    chessBoard.movePiece(move.getStartPosition(), move.getEndPosition(), currentPiece);
                }

                //change team turn
                if(getTeamTurn() == TeamColor.WHITE){
                    setTeamTurn(TeamColor.BLACK);
                    if(isInCheck(TeamColor.BLACK)){
                        isInCheckmate(TeamColor.BLACK);
                    }
                }
                else{
                    setTeamTurn(TeamColor.WHITE);
                    if(isInCheck(TeamColor.WHITE)){
                        isInCheckmate(TeamColor.WHITE);
                    }
                }

            } else {
                throw new InvalidMoveException("Invalid move: " + move);
            }
        }
        else {
            throw new InvalidMoveException("Invalid move: " + move);
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {

        return chessBoard.isInCheck(teamColor);
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {

        return chessBoard.isInCheckmate(teamColor);
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {

        chessBoard = (ChessBoardImpl) board;
    }

    @Override
    public ChessBoard getBoard() {
        return chessBoard;
    }
}
