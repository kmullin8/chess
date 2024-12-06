package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;
import management.EscapeSequences;
import model.GameModel;
import serveraccess.ServerFacade;
import serveraccess.WebSocketFacade;
import websocket.messages.ServerMessage;

import java.util.Arrays;

public class GamePlayClient implements Client, NotificationHandler {
    private ServerFacade facade;
    private WebSocketFacade wsFacade;
    private String serverUrl;
    private ChessGame.TeamColor color;

    public GamePlayClient(String serverUrl) {
        var currentGame = GameStateManager.getInstance().getCurrentGame();
        var authToken = AuthManager.getInstance().getAuthToken();
        var color = GameStateManager.getInstance().getColor();
        var username = GameStateManager.getInstance().getUsername();

        try {
            wsFacade = new WebSocketFacade(serverUrl, this, authToken.getAuthToken(), currentGame.getGameID(), color, username);
            //wsFacade.connect();  // Connect to the server
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void notify(ServerMessage notification) {
        // Handle the notification (e.g., update UI, log message)
        System.out.println("\nReceived notification: " + notification.getMessage());
    }

    @Override
    public void updateGameState(GameModel game) {

        GameStateManager.getInstance().setCurrentGame(game); //update current game
        // Existing logic for updating the game state
        System.out.print(displayBoard());
    }

    @Override
    public void showErrorNotification(String message) {
        System.err.println("Error: " + message);
        // Add code to display an error notification in the UI
    }

    @Override
    public void showGameOverNotification(String winner, String reason) {
        System.out.println("Game Over! Winner: " + winner + ", Reason: " + reason);
        // Add logic to display a "Game Over" banner or modal
    }

    @Override
    public void showTurnTransition(String currentPlayer) {
        System.out.println("It's now " + currentPlayer + "'s turn.");
        // Add a visual cue in the UI (e.g., highlight the current player's area)
    }

    @Override
    public void showInvalidMoveNotification(String reason) {
        System.out.println("Invalid Move: " + reason);
        // Display the invalid move reason in the UI
    }

    private String displayBoard() {
        var currentGame = GameStateManager.getInstance().getCurrentGame();
        var color = GameStateManager.getInstance().getColor();

        if (currentGame == null) {
            return "No game loaded. Please connect to a game\n";
        }

        StringBuilder boardBuilder = new StringBuilder();
        ChessBoard chessBoard = currentGame.getGame().getBoard();

        if (color == ChessGame.TeamColor.WHITE) {// Draw from White's perspective
            boardBuilder.append("White's Perspective\n").append(drawBoard(chessBoard, true)).append("\n");
        } else if (color == ChessGame.TeamColor.BLACK) {// Draw from Black's perspective
            boardBuilder.append("Black's Perspective\n").append(drawBoard(chessBoard, false));
        } else {
            boardBuilder.append("White's Perspective\n").append(drawBoard(chessBoard, true)).append("\n");
            boardBuilder.append("Black's Perspective\n").append(drawBoard(chessBoard, false));
        }

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
                help - with possible commands
                redraw - redraws the chess board
                moves - highlights legal moves
                make move <a1><a1> - move piece
                leave - a game
                quit - playing chess
                """;
    }

}
