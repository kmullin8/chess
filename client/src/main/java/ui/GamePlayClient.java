package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;
import management.EscapeSequences;
import model.AuthTokenModel;
import model.GameModel;
import serveraccess.ServerFacade;
import serveraccess.WebSocketFacade;
import websocket.messages.ServerMessage;

import java.util.Arrays;

public class GamePlayClient implements Client, NotificationHandler {
    private ServerFacade facade;
    private WebSocketFacade wsFacade;
    private String serverUrl;
    private AuthTokenModel authToken;
    private GameModel currentGame;

    public GamePlayClient(String serverUrl, AuthTokenModel authToken) {
        this.authToken = authToken;
        this.serverUrl = serverUrl;

        try {
            wsFacade = new WebSocketFacade(serverUrl, this, authToken.getAuthToken(), currentGame.getGameID());
            wsFacade.connect();  // Connect to the server
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notify(ServerMessage notification) {
        // Handle the notification (e.g., update UI, log message)
        System.out.println("Received notification: " + notification.getServerMessageType());
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "display" -> displayBoard();
                case "quit" -> {
                    wsFacade.disconnect(); // Calls WebSocketFacade to disconnect
                    yield "quit";
                }
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private String displayBoard() {
        if (currentGame == null) {
            return "No game loaded. Please connect to a game.";
        }

        StringBuilder boardBuilder = new StringBuilder();
        ChessBoard chessBoard = currentGame.getGame().getBoard();

        // Draw from White's perspective
        boardBuilder.append("White's Perspective\n").append(drawBoard(chessBoard, true)).append("\n");

        // Draw from Black's perspective
        boardBuilder.append("Black's Perspective\n").append(drawBoard(chessBoard, false));

        return boardBuilder.toString();
    }

    private String drawBoard(ChessBoard chessBoard, boolean isWhitePerspective) {
        StringBuilder boardBuilder = new StringBuilder();

        // Column labels with perspective adjustment
        String columnLabels = isWhitePerspective ? "   a  b  c  d  e  f  g  h" : "   h  g  f  e  d  c  b  a";
        boardBuilder.append(columnLabels).append("\n");

        // Determine row range and step based on perspective
        int startRow = isWhitePerspective ? 8 : 1;
        int endRow = isWhitePerspective ? 0 : 9;
        int step = isWhitePerspective ? -1 : 1;

        for (int row = startRow; row != endRow; row += step) {
            boardBuilder.append(row).append(" "); // Row number with padding
            for (int col = 1; col <= 8; col++) {
                int actualCol = isWhitePerspective ? col : 9 - col; // Adjust column based on perspective
                ChessPosition position = new ChessPosition(row, actualCol);

                boolean isLightSquare = (row + actualCol) % 2 == 0;
                String bgColor = isLightSquare ? EscapeSequences.SET_BG_COLOR_DARK_GREY : EscapeSequences.SET_BG_COLOR_LIGHT_GREY;

                String pieceSymbol;
                if (chessBoard.getPiece(position) != null) {
                    pieceSymbol = chessBoard.getPiece(position).getSymbol();
                    // Apply piece color based on team
                    String pieceColor = chessBoard.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE
                            ? EscapeSequences.SET_TEXT_COLOR_BLUE
                            : EscapeSequences.SET_TEXT_COLOR_RED;
                    pieceSymbol = pieceColor + pieceSymbol + EscapeSequences.RESET_TEXT_COLOR;
                } else {
                    pieceSymbol = " ";
                }

                // Use a padded format for each square
                boardBuilder.append(bgColor).append(" ").append(pieceSymbol).append(" ").append(EscapeSequences.RESET_BG_COLOR);
            }
            boardBuilder.append(" ").append(row).append("\n"); // Row number on the other side
        }
        boardBuilder.append(columnLabels).append("\n");

        return boardBuilder.toString();
    }


    @Override
    public String help() {
        return """
                how do you not know how to play chess
                """;
    }

    @Override
    public AuthTokenModel getAuthToken() {
        return authToken;
    }

    @Override
    public void setAuthToken(AuthTokenModel authToken) {
        this.authToken = authToken;
        if (wsFacade != null) {
            wsFacade.updateAuthToken(authToken.getAuthToken());
        }
    }

    public GameModel getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(GameModel currentGame) {
        this.currentGame = currentGame;
    }

    @Override
    public void updateGameState(GameModel game) {
        // Update the current game state
        setCurrentGame(game);
    }

}
