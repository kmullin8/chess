package ui;

import chess.*;
import management.EscapeSequences;
import model.AuthTokenModel;
import model.GameModel;
import serveraccess.ServerFacade;
import serveraccess.WebSocketFacade;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class GamePlayClient implements Client, NotificationHandler {
    private ServerFacade facade;
    private WebSocketFacade wsFacade;
    private String serverUrl;
    private GameModel currentGame;
    private AuthTokenModel authToken;
    private ChessGame.TeamColor color;
    private String username;

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

    void setParameters() {
        this.currentGame = GameStateManager.getInstance().getCurrentGame();
        this.authToken = AuthManager.getInstance().getAuthToken();
        this.color = GameStateManager.getInstance().getColor();
        this.username = GameStateManager.getInstance().getUsername();
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> displayBoard();
                case "moves" -> moves();
                case "move" -> makeMove(params);
                case "resign" -> resignGame();
                case "leave" -> leave();
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

    private String makeMove(String... params) throws IOException {
        if (params.length == 2) {
            setParameters();

            String from = params[0];
            String to = params[1];

            // Convert 'from' string (e.g., "A1") to startRow and startColumn
            int startRow = Character.getNumericValue(from.charAt(1)); // Rows are numbered 8-1
            int startColumn = from.toUpperCase().charAt(0) - 'A' + 1;        // Columns A-H map to 0-7

            // Convert 'to' string (e.g., "B2") to endRow and endColumn
            int endRow = Character.getNumericValue(to.charAt(1));    // Rows are numbered 8-1
            int endColumn = to.toUpperCase().charAt(0) - 'A' + 1;            // Columns A-H map to 0-7

            ChessMove move = new ChessMove(
                    new ChessPosition(startRow, startColumn),
                    new ChessPosition(endRow, endColumn),
                    null
            );

            // Create a new UserGameCommand for the move
            MakeMoveCommand makeMoveCommand = new MakeMoveCommand(
                    UserGameCommand.CommandType.MAKE_MOVE,
                    authToken.getAuthToken(),
                    currentGame.getGameID(),
                    color,
                    username,
                    move
            );

            // Send the command to the server
            wsFacade.sendMakeMoveCommand(makeMoveCommand);
            return "Move sent: " + from + " -> " + to;
        }
        return "Expected: <a1> <a1>\n";
    }

    private String moves(String... params) {
        return null;
    }

    private String leave() {
        return null;
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

    private String resignGame() throws IOException {
        Scanner scanner = new Scanner(System.in); // Create a scanner to accept user input
        System.out.println("Are you sure you want to resign? Type 'yes' to confirm or 'no' to cancel:");

        // Get user input
        String userInput = scanner.nextLine().trim().toLowerCase();

        // Check the input
        if (userInput.equals("yes")) {
            UserGameCommand resignCommand = new UserGameCommand(UserGameCommand.CommandType.RESIGN,
                    authToken.getAuthToken(),
                    currentGame.getGameID(),
                    color,
                    username
            );
            wsFacade.sendCommand(resignCommand);
            return "resigned\n";
        } else {
            return "not resigned\n";
        }
    }


    @Override
    public String help() {
        return """
                help - with possible commands
                redraw - redraws the chess board
                moves - highlights legal moves
                move <a1><a1> - move piece
                resign - resign game
                leave - a game
                quit - playing chess
                """;
    }

}
