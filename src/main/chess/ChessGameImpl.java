package chess;


import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.ArrayList;


public class ChessGameImpl implements ChessGame{

    private ChessBoardImpl chessBoard1;
    private TeamColor teamturn;

    public ChessGameImpl(){
        chessBoard1 = new ChessBoardImpl();
        teamturn = TeamColor.WHITE;
    }

    public ChessGameImpl(ChessGame copy) {
        this.chessBoard1 = new ChessBoardImpl(copy.getBoard());
        this.teamturn = copy.getTeamTurn();
    }

    public static ChessGameImpl create(String serializedGame) {
        return serializer().fromJson(serializedGame, ChessGameImpl.class);
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

        ChessPieceImpl currentPiece = (ChessPieceImpl) chessBoard1.getPiece(startPosition);
        Collection <ChessMove> pieceMoves = currentPiece.pieceMoves(chessBoard1,startPosition);
        Collection <ChessMove> validPieceMoves = new ArrayList<>();

        ChessBoardImpl chessBoardCopy = chessBoard1;

//        for (ChessMove move : pieceMoves) {
////            chessBoardCopy.movePiece(move.getStartPosition(), move.getEndPosition(), currentPiece);
////
////            if(!chessBoardCopy.isInCheck(currentPiece.getTeamColor())){
////                validPieceMoves.add(move);
////            }
////        }

        for (ChessMove move : pieceMoves) {

            if(checkValidMoves(move, currentPiece)){

                validPieceMoves.add(move);
            }
        }

        return validPieceMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {

        //get piece and valid moves from starting position
        ChessPieceImpl currentPiece = (ChessPieceImpl) chessBoard1.getPiece(move.getStartPosition());

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
                ChessBoardImpl chessBoardCopy = chessBoard1;
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
                        chessBoard1.movePiece(move.getStartPosition(), move.getEndPosition(), new Rook(pieceColor));
                    }
                    else if(type == ChessPiece.PieceType.KNIGHT){
                        chessBoard1.movePiece(move.getStartPosition(), move.getEndPosition(), new Knight(pieceColor));
                    }
                    else if(type == ChessPiece.PieceType.BISHOP){
                        chessBoard1.movePiece(move.getStartPosition(), move.getEndPosition(), new Bishop(pieceColor));
                    }
                    else if(type == ChessPiece.PieceType.QUEEN){
                        chessBoard1.movePiece(move.getStartPosition(), move.getEndPosition(), new Queen(pieceColor));
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
                    chessBoard1.movePiece(move.getStartPosition(), move.getEndPosition(), currentPiece);
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

    boolean checkValidMoves(ChessMove move, ChessPieceImpl currentPiece){

        return chessBoard1.checkValidMoves(move, currentPiece);
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {

        return chessBoard1.isInCheck(teamColor);
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {

        return chessBoard1.isInCheckmate(teamColor);
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {

        return chessBoard1.isInStalemate(teamColor);
    }

    @Override
    public void setBoard(ChessBoard board) {

        chessBoard1 = (ChessBoardImpl) board;
    }

    @Override
    public ChessBoard getBoard() {
        return chessBoard1;
    }

    public static Gson serializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessGame.class, new ChessGameAdapter());
        gsonBuilder.registerTypeAdapter(ChessGameImpl.class, new ChessGameAdapter());
        return gsonBuilder.create();
    }

    public static class ChessGameAdapter implements JsonDeserializer<ChessGame> {
        @Override
        public ChessGame deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            JsonObject jsonObject = el.getAsJsonObject();
            Gson gson = new Gson();

            ChessGameImpl chessGame = gson.fromJson(jsonObject, ChessGameImpl.class);

            // Deserialize ChessBoard
            ChessBoardImpl chessBoard = gson.fromJson(jsonObject.getAsJsonObject("chessBoard"), ChessBoardImpl.class);
            chessGame.setChessBoard(chessBoard);

            // Deserialize KingPositions
            ChessPositionImpl kingPositionWhite = gson.fromJson(jsonObject.getAsJsonObject("kingPositionWhite"), ChessPositionImpl.class);
            chessGame.setKingPositionWhite(kingPositionWhite);

            ChessPositionImpl kingPositionBlack = gson.fromJson(jsonObject.getAsJsonObject("kingPositionBlack"), ChessPositionImpl.class);
            chessGame.setKingPositionBlack(kingPositionBlack);

            return chessGame;
        }
    }

    private void setKingPositionBlack(ChessPositionImpl kingPositionBlack) {
        this.setKingPositionBlack(kingPositionBlack);
    }

    private void setKingPositionWhite(ChessPositionImpl kingPositionWhite) {
        this.setKingPositionWhite(kingPositionWhite);
    }

    private void setChessBoard(ChessBoardImpl chessBoard) {
        this.setBoard(chessBoard);
    }

    //format of json object:
    // chessBoard1(chessBoard2((chess.chessPositionImpl, ChessPiece(teamColor, chessPiece), ..., ,kingPositionWhite, kingPositionBlack,)) teamturn
    // chessGameImpl(chessBoardImpl((Map<ChessPosition, ChessPiece>, ChessPositionImpl kingPositionWhite, ChessPositionImpl kingPositionBlack)), teamturn

    //chessGame adapter, chessBoard + teamturn

    //chessBoard adapter, Map<ChessPosition, ChessPiece> + ChessPositionImpl kingPositionWhite + ChessPositionImpl kingPositionBlack

    //chessPiece adapter

    //ChessPosition adapter

    public static class ChessBoardAdapter implements JsonDeserializer<ChessBoard> {
        @Override
        public ChessBoard deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            JsonObject jsonObject = el.getAsJsonObject();
            Gson gson = new Gson();

            ChessBoardImpl chessBoard = gson.fromJson(jsonObject.getAsJsonObject("chessBoard"), ChessBoardImpl.class);

            // Handle other properties if needed

            return chessBoard;
        }
    }

    public static class ChessPositionAdapter implements JsonDeserializer<ChessPosition> {
        @Override
        public ChessPosition deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            JsonObject jsonObject = el.getAsJsonObject();
            int row = jsonObject.get("row").getAsInt();
            int col = jsonObject.get("col").getAsInt();

            // Create and return a new ChessPositionImpl object
            return new ChessPositionImpl(row, col);
        }
    }
}
