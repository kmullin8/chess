package ui;

import java.util.Arrays;

public class PostLoginClient implements Client {
    private ServerFacade facade;

    public PostLoginClient(String serverUrl) {
        facade = new ServerFacade(serverUrl);
        facade = new ServerFacade("http://localhost:8080");
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

    private String createGame(String... params) {
        return null;
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
        return null;
    }

    @Override
    public void quit() {

    }
}
