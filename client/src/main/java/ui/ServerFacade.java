package ui;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.*;
import requests.JoinGameRequest;

import java.io.*;
import java.net.*;
import java.util.Map;

public class ServerFacade {

    private final String serverUrl;
    private final Gson gson = new Gson();

    public ServerFacade(String url) {
        serverUrl = url;
    }

    // Register User
    public UserModel registerUser(UserModel user) throws Exception {
        var path = "/user";
        return makeRequest("POST", path, user, UserModel.class);
    }

    // Login
    public AuthTokenModel logIn(UserModel user) throws Exception {
        var path = "/session";
        return makeRequest("POST", path, user, AuthTokenModel.class);
    }

    // Logout
    public void logOut(String authToken) throws Exception {
        var path = "/session";
        makeRequest("DELETE", path, null, null, authToken);
    }

    // List Games
    public GameModel[] listGames(String authToken) throws Exception {
        var path = "/game";
        var response = makeRequest("GET", path, null, Map.class, authToken);
        return gson.fromJson(gson.toJson(response.get("games")), GameModel[].class);
    }

    // Create Game
    public GameModel createGame(String gameName, String authToken) throws Exception {
        var path = "/game";
        GameModel game = new GameModel();
        game.setGameName(gameName);
        return makeRequest("POST", path, game, GameModel.class, authToken);
    }

    // Join Game
    public GameModel joinGame(JoinGameRequest request, String authToken) throws Exception {
        var path = "/game";
        return makeRequest("PUT", path, request, GameModel.class, authToken);
    }

    // Clear Database
    public void clearDatabase() throws Exception {
        var path = "/db";
        makeRequest("DELETE", path, null, null);
    }

    // Helper method for making HTTP requests
    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws Exception {
        return makeRequest(method, path, request, responseClass, null);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if (authToken != null) {
                http.setRequestProperty("Authorization", authToken);
            }

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new Exception("Request failed: " + ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException {
        int status = http.getResponseCode();
        if (status < 200 || status >= 300) {
            throw new IOException("Request failed with status code: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        if (responseClass == null) return null;
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader reader = new InputStreamReader(respBody);
            return new Gson().fromJson(reader, responseClass);
        }
    }
}
