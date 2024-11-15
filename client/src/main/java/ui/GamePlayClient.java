package ui;

import chess.ChessBoard;
import chess.ChessPosition;
import model.AuthTokenModel;
import model.GameModel;

import java.util.Arrays;

public class GamePlayClient implements Client {
    private ServerFacade facade;
    private String serverUrl;
    private AuthTokenModel authToken;
    private GameModel currentGame;

    public GamePlayClient(String serverUrl, AuthTokenModel authToken) {
        facade = new ServerFacade("http://localhost:8080");

        this.authToken = authToken;
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "display" -> displayBoard();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private String displayBoard() {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();
        StringBuilder boardBuilder = new StringBuilder();

        // Column letters for White's perspective
        String whiteColumnLabels = "   a  b  c  d  e  f  g  h";
        boardBuilder.append(whiteColumnLabels).append("\n");

        // Draw board from White's perspective (row 8 to 1)
        for (int row = 8; row >= 1; row--) {
            boardBuilder.append(row).append(" "); // Row number on the left
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);

                boolean isLightSquare = (row + col) % 2 == 0;
                String bgColor = isLightSquare ? EscapeSequences.SET_BG_COLOR_DARK_GREY : EscapeSequences.SET_BG_COLOR_LIGHT_GREY;

                String pieceSymbol = (chessBoard.getPiece(position) != null)
                        ? chessBoard.getPiece(position).getSymbol()
                        : " ";

                boardBuilder.append(bgColor).append(" ").append(pieceSymbol).append(" ").append(EscapeSequences.RESET_BG_COLOR);
            }
            boardBuilder.append(" ").append(row).append("\n"); // Row number on the right
        }
        boardBuilder.append(whiteColumnLabels).append("\n\n");

        // Separator for Black's perspective
        boardBuilder.append("Black's Perspective\n");

        // Column letters for Black's perspective (reversed)
        String blackColumnLabels = "   h  g  f  e  d  c  b  a";
        boardBuilder.append(blackColumnLabels).append("\n");

        // Draw board from Black's perspective (row 1 to 8, columns reversed)
        for (int row = 1; row <= 8; row++) {
            boardBuilder.append(row).append(" "); // Row number on the left
            for (int col = 8; col >= 1; col--) { // Iterate columns in reverse
                ChessPosition position = new ChessPosition(row, col);

                boolean isLightSquare = (row + col) % 2 == 0;
                String bgColor = isLightSquare ? EscapeSequences.SET_BG_COLOR_DARK_GREY : EscapeSequences.SET_BG_COLOR_LIGHT_GREY;

                String pieceSymbol = (chessBoard.getPiece(position) != null)
                        ? chessBoard.getPiece(position).getSymbol()
                        : " ";

                boardBuilder.append(bgColor).append(" ").append(pieceSymbol).append(" ").append(EscapeSequences.RESET_BG_COLOR);
            }
            boardBuilder.append(" ").append(row).append("\n"); // Row number on the right
        }
        boardBuilder.append(blackColumnLabels).append("\n");

        return boardBuilder.toString();
    }


    @Override
    public String help() {
        return """
                how do you not know how to play chess
                """;
    }

    @Override
    public void quit() {

    }

    @Override
    public void setAuthToken(AuthTokenModel authToken) {
        this.authToken = authToken;
    }

    @Override
    public AuthTokenModel getAuthToken() {
        return authToken;
    }

    public void setCurrentGame(GameModel currentGame) {
        this.currentGame = currentGame;
    }

    public GameModel getCurrentGame() {
        return currentGame;
    }
}
