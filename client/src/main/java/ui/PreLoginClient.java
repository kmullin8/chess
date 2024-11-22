package ui;

import model.AuthTokenModel;
import model.UserModel;


import java.util.Arrays;

public class PreLoginClient implements Client {
    private ServerFacade facade;
    private AuthTokenModel authToken;

    public PreLoginClient(String serverUrl) {
        facade = new ServerFacade("http://localhost:8080");
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
                authToken = facade.logIn(user);
            } catch (Exception ex) {
                throw new Exception("Could not find username or password\n", ex);
            }

            setAuthToken(authToken);

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
                authToken = facade.registerUser(user);
            } catch (Exception ex) {
                throw new Exception("User already exists\n", ex);
            }
            setAuthToken(authToken);

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


    public void setAuthToken(AuthTokenModel authToken) {
        this.authToken = authToken;
    }

    public AuthTokenModel getAuthToken() {
        return authToken;
    }
}
