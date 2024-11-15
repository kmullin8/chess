package ui;

import model.AuthTokenModel;
import model.UserModel;

import java.util.Arrays;

public class PostLoginClient implements Client {
    private ServerFacade facade;
    private AuthTokenModel authToken;

    public PostLoginClient(String serverUrl, AuthTokenModel authToken) {
        facade = new ServerFacade(serverUrl);
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
            facade.createGame(gameName, authToken.getAuthToken());

            return ("Created game " + gameName + "\n");
        }
        return ("Expected: <NAME>\n");
    }

    private String observeGame(String... params) {
        return null;
    }

    private String joinGame(String... params) {
        return null;
    }

    private String listGames() {
        return null;
    }

    private String logout() {
        return null;
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
}
