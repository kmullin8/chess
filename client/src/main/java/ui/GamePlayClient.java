package ui;

import model.AuthTokenModel;

import java.util.Arrays;

public class GamePlayClient implements Client {
    private ServerFacade server;
    private String serverUrl;

    public GamePlayClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
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

    }

    @Override
    public AuthTokenModel getAuthToken() {
        return null;
    }
}
