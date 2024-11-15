package ui;

import chess.ChessGame;
import model.AuthTokenModel;
import model.GameModel;
import requests.JoinGameRequest;

import java.util.*;

public class PostLoginClient implements Client {
    private ServerFacade facade;
    private AuthTokenModel authToken;
    private GameModel currentGame;

    public PostLoginClient(String serverUrl, AuthTokenModel authToken) {
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
            GameModel gameModel = facade.createGame(gameName, authToken.getAuthToken());

            return ("Created game " + gameName + "\n");
        }
        return ("Expected: <NAME>\n");
    }

    private String listGames() throws Exception {
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
            String inputGameId = params[0];

            GameModel[] gameList = facade.listGames(authToken.getAuthToken()); // get list of games
            Map<Integer, Integer> idList = new HashMap<>();
            for (int i = 0; i < gameList.length; i++) { // map list game numbers to game IDs
                GameModel game = gameList[i];
                idList.put(i + 1, game.getGameID()); // use i + 1 as key to start numbering at 1
            }

            // Convert inputGameId to an Integer and check if it exists in idList
            int gameNumber;
            try {
                gameNumber = Integer.parseInt(inputGameId);
            } catch (NumberFormatException e) {
                return "Invalid input. Please enter a valid game number.\n";
            }

            if (!idList.containsKey(gameNumber)) {
                return "Invalid game number. Please enter a valid game number.\n";
            }

            int realGameId = idList.get(gameNumber);

            JoinGameRequest joinRequest = new JoinGameRequest(realGameId, ChessGame.TeamColor.BLACK);
            currentGame = facade.joinGame(joinRequest, authToken.getAuthToken());

            return "Joined Game as Observer\n";
        }
        return "Expected: <ID>\n";
    }

    private String joinGame(String... params) throws Exception {
        if (params.length == 2) {
            String inputGameId = params[0];
            String playerColor = params[1];

            GameModel[] gameList = facade.listGames(authToken.getAuthToken()); // get list of games
            Map<Integer, Integer> idList = new HashMap<>();
            for (int i = 0; i < gameList.length; i++) { // map list game numbers to game IDs
                GameModel game = gameList[i];
                idList.put(i + 1, game.getGameID()); // use i + 1 as key to start numbering at 1
            }

            // Convert inputGameId to an Integer and check if it exists in idList
            int gameNumber;
            try {
                gameNumber = Integer.parseInt(inputGameId);
            } catch (NumberFormatException e) {
                return "Invalid input. Please enter a valid game number.\n";
            }

            if (!idList.containsKey(gameNumber)) {
                return "Invalid game number. Please enter a valid game number.\n";
            }

            int realGameId = idList.get(gameNumber);


            if (Objects.equals(playerColor, "white")) {
                JoinGameRequest joinRequest = new JoinGameRequest(realGameId, ChessGame.TeamColor.WHITE);
                currentGame = facade.joinGame(joinRequest, authToken.getAuthToken());
                return "Joined Game\n";
            } else if (Objects.equals(playerColor, "black")) {
                JoinGameRequest joinRequest = new JoinGameRequest(realGameId, ChessGame.TeamColor.WHITE);
                currentGame = facade.joinGame(joinRequest, authToken.getAuthToken());
                return "Joined Game\n";
            } else {
                return "Expected: <ID> <WHITE> <BLACK>\n";
            }
        }
        return "Expected: <ID> <WHITE> <BLACK>\n";
    }

    private String logout() throws Exception {
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
