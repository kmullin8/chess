package ui;

import model.AuthTokenModel;
import model.UserModel;
import serveraccess.ServerFacade;


import java.util.Arrays;

public class PreLoginClient implements Client {
    private ServerFacade facade;

    public PreLoginClient(String serverUrl) {
        facade = new ServerFacade(serverUrl);
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String login(String... params) throws Exception {
        if (params.length == 2) {
            var username = params[0];
            var password = params[1];
            var email = "null";
            UserModel user = new UserModel(username, password, email);

            try {
                AuthTokenModel authToken = facade.logIn(user);
                setAuthToken(authToken);
            } catch (Exception ex) {
                throw new Exception("Could not find username or password\n", ex);
            }

            return ("Logged in as " + username + "\n");
        }
        return ("Expected: <USERNAME> <PASSWORD>\n");
    }

    public String register(String... params) throws Exception {
        if (params.length == 3) {
            var username = params[0];
            var password = params[1];
            var email = params[2];
            UserModel user = new UserModel(username, password, email);

            try {
                AuthTokenModel authToken = facade.logIn(user);
                setAuthToken(authToken);
            } catch (Exception ex) {
                throw new Exception("User already exists\n", ex);
            }

            return ("Logged in as " + username + "\n");
        }
        return ("Expected: <USERNAME> <PASSWORD> <email>\n");
    }

    public String help() {
        return """
                register <USERNAME> <PASSWORD> <EMAIL> - to create account
                login <USERNAME> <PASSWORD> - to play chess
                quit - playing chess
                help - with possible commands
                """;
    }

    private void setAuthToken(AuthTokenModel authToken) {
        AuthManager.getInstance().setAuthToken(authToken); //set auth
    }
}
