package ui;

import chess.ChessGame;
import model.AuthTokenModel;
import model.GameModel;
import requests.JoinGameRequest;
import serveraccess.ServerFacade;

import java.util.*;

public class PostLoginClient implements Client {
    private ServerFacade facade;

    public PostLoginClient(String serverUrl) {
        facade = new ServerFacade(serverUrl);
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = tokens[0];
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> createGame(params);
                case "list" -> listGames();
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "logout" -> logout();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private String createGame(String... params) throws Exception {
        if (params.length == 1) {
            var gameName = params[0];
            var authToken = AuthManager.getInstance().getAuthToken();
            GameModel gameModel = facade.createGame(gameName, authToken.getAuthToken());
            return "Created game " + gameName + "\n";
        }
        return "Expected: <NAME>\n";
    }

    private String listGames() throws Exception {
        var authToken = AuthManager.getInstance().getAuthToken();
        GameModel[] gameList = facade.listGames(authToken.getAuthToken());

        if (gameList == null || gameList.length == 0) {
            return "No games available.";
        }

        StringBuilder output = new StringBuilder("Available Games:\n");

        for (int i = 0; i < gameList.length; i++) {
            GameModel game = gameList[i];

            // Retrieve the usernames for the white and black players
            String whitePlayer = game.getWhiteUsername();
            String blackPlayer = game.getBlackUsername();

            // Format the game information
            output.append(i + 1).append(". ")
                    .append("Game: ").append(game.getGameName()).append("\n")
                    .append("Players: ").append(whitePlayer).append(" vs ").append(blackPlayer).append("\n\n");
        }

        return output.toString();
    }

    private String observeGame(String... params) throws Exception {
        if (params.length == 1) {
            int realGameId = validateAndGetGameId(params[0]);
            JoinGameRequest joinRequest = new JoinGameRequest(realGameId, ChessGame.TeamColor.BLACK);
            //currentGame = facade.joinGame(joinRequest, authToken.getAuthToken());
            return "Joined Game as Observer\n";
        }
        return "Expected: <ID>\n";
    }

    private String joinGame(String... params) throws Exception {
        if (params.length == 2) {
            var authToken = AuthManager.getInstance().getAuthToken();
            int realGameId = validateAndGetGameId(params[0]);

            ChessGame.TeamColor teamColor;
            if ("white".equalsIgnoreCase(params[1])) {
                teamColor = ChessGame.TeamColor.WHITE;
            } else if ("black".equalsIgnoreCase(params[1])) {
                teamColor = ChessGame.TeamColor.BLACK;
            } else {
                return "Expected: <ID> <WHITE|BLACK>\n";
            }

            JoinGameRequest joinRequest = new JoinGameRequest(realGameId, teamColor);
            var currentGame = facade.joinGame(joinRequest, authToken.getAuthToken());
            setCurrentGame(currentGame);

            return "Joined Game\n";
        }
        return "Expected: <ID> <WHITE|BLACK>\n";
    }

    private String logout() throws Exception {
        var authToken = AuthManager.getInstance().getAuthToken();
        facade.logOut(authToken.getAuthToken());
        return "Logged out\n";
    }

    @Override
    public String help() {
        return """
                create <NAME> - a game
                list - games
                join <ID> [WHITE|BLACK] - a game
                observe <ID> - a game
                logout - when you are done
                quit - playing chess
                help - with possible commands
                """;
    }

    private int validateAndGetGameId(String inputGameId) throws Exception {
        var authToken = AuthManager.getInstance().getAuthToken();
        GameModel[] gameList = facade.listGames(authToken.getAuthToken());

        Map<Integer, Integer> idList = new HashMap<>();
        for (int i = 0; i < gameList.length; i++) {
            idList.put(i + 1, gameList[i].getGameID());
        }

        int gameNumber;
        try {
            gameNumber = Integer.parseInt(inputGameId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid game number.\n");
        }

        if (!idList.containsKey(gameNumber)) {
            throw new IllegalArgumentException("Please enter a valid game number.\n");
        }

        return idList.get(gameNumber);
    }

    private void setAuthToken(AuthTokenModel authToken) {
        AuthManager.getInstance().setAuthToken(authToken); //set auth
    }

    private void setCurrentGame(GameModel currentGame) {
        GameStateManager.getInstance().setCurrentGame(currentGame); //set current game
    }
}
