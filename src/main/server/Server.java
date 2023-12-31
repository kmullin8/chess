package server;

import com.google.gson.Gson;
import requests.JoinGameRequest;
import spark.*;
import java.util.HashMap;
import java.util.Map;

import services.*;
import model.*;
import dataAccess.*;

public class Server {

    private DataAccess dataAccess;
    private static RegisterUserService registerUserService;
    private static ListGamesService listGamesService;
    private static CreateGameService createGameService;
    private static JoinGameService joinGameService;
    private static ClearService clearService;
    private static LoginService loginService;
    private static LogoutService logoutService;

    public static void main(String[] args) throws DataAccessException {
        DataAccess access;
        if (args.length > 0 && args[0].equalsIgnoreCase("memory")) {
            access = new MemoryDataAccess();
        } else {
            access = new MySqlDataAccess();
        }
        new Server(access).run();
    }

    /**
     * server default constructor
     */
    public Server(DataAccess dataAccess) {
        String absolutePath = "C:\\Users\\kaden\\school\\23 Fall\\cs 240\\chess\\src\\web";
        Spark.externalStaticFileLocation(absolutePath);

        this.dataAccess = dataAccess;
        registerUserService = new RegisterUserService(dataAccess);
        listGamesService = new ListGamesService(dataAccess);
        createGameService = new CreateGameService(dataAccess);
        joinGameService = new JoinGameService(dataAccess);
        clearService = new ClearService(dataAccess);
        loginService = new LoginService(dataAccess);
        logoutService = new LogoutService(dataAccess);
    }

    private void run() {
        try {
            // Specify the port you want the server to listen on
            Spark.port(8080);

            Spark.post("/user", this::registerUser);
            Spark.post("/session", this::logInUser);
            Spark.delete("/session", this::logOutUser);
            Spark.get("/game", this::listGames);
            Spark.post("/game", this::createGame);
            Spark.put("/game", this::joinGame);
            Spark.delete("/db", this::clearApplication);

            Spark.exception(CodedException.class, this::errorHandler);
            Spark.exception(Exception.class, (e, req, res) -> errorHandler(new CodedException(500, e.getMessage()), req, res));
            Spark.notFound((req, res) -> {
                var msg = String.format("[%s] %s not found", req.requestMethod(), req.pathInfo());
                return errorHandler(new CodedException(404, msg), req, res);
            });

        } catch (Exception ex) {
            System.out.printf("Unable to start server: %s", ex.getMessage());
            System.exit(1);
        }
    }

    public Object errorHandler(CodedException e, Request req, Response res) {
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        res.type("application/json");
        res.status(e.statusCode());
        res.body(body);
        return body;
    }

    /**
     * Endpoint for [POST] /user - Register user
     * <pre>{ "username":"", "password":"", "email":"" }</pre>
     */
    private Object registerUser(Request req, Response ignore) throws CodedException, DataAccessException {
        var user = getBody(req, UserModel.class);
        var authToken = registerUserService.registerUser(user);

        return send("username", user.getUsername(), "authToken", authToken);
    }

    /**
     * Endpoint for [POST] /session
     * <pre>{ "username":"", "password":"" }</pre>
     */
    public Object logInUser(Request req, Response ignore) throws CodedException {
        var user = getBody(req, UserModel.class);
        var authToken = loginService.createSession(user);
        return send("username", user.getUsername(), "authToken", authToken);
    }

    /**
     * Endpoint for [DELETE] /session
     * Authorization header required.
     */
    public Object logOutUser(Request req, Response ignore) throws CodedException {
        var authToken = throwIfUnauthorized(req);
        logoutService.deleteSession(authToken.getAuthToken());
        return send("username", authToken.getUsername(), "authToken", authToken.getAuthToken());
    }

    /**
     * Endpoint for [GET] /game
     * Authorization header required.
     */
    public Object listGames(Request req, Response ignoreRes) throws CodedException {
        throwIfUnauthorized(req);
        var games = listGamesService.listGames();
        return send("games", games.toArray());
    }

    /**
     * Endpoint for [POST] / game
     * Authorization header required.
     */
    public Object createGame(Request req, Response ignoreRes) throws CodedException {
        throwIfUnauthorized(req);
        var game = getBody(req, GameModel.class);
        game = createGameService.createGame(game.getGameName());
        return send("gameID", game.getGameID());
    }

    /**
     * Endpoint for [PUT] /
     * Authorization header required.
     * <pre>{ "playerColor":"WHITE/BLACK/empty", "gameID": 1234 }</pre>
     */
    public Object joinGame(Request req, Response ignoreRes) throws CodedException {

        var authTokenModel = throwIfUnauthorized(req);
        var joinReq = getBody(req, JoinGameRequest.class);

        var game = joinGameService.joinGame(authTokenModel.getUsername(), joinReq.getPlayerColor(), joinReq.getGameID());
        return send("game", game);
    }

    /**
     * Endpoint for [DELETE] /db
     */
    public Object clearApplication(Request ignoreReq, Response res) throws CodedException {
        clearService.clearApplication();
        return send();
    }

    private <T> T getBody(Request request, Class<T> clazz) throws CodedException {
        var body = new Gson().fromJson(request.body(), clazz);
        if (body == null) {
            throw new CodedException(400, "Missing body");
        }
        return body;
    }

    private String send(Object... props) {
        Map<Object, Object> map = new HashMap<>();
        for (var i = 0; i + 1 < props.length; i = i + 2) {
            map.put(props[i], props[i + 1]);
        }
        return new Gson().toJson(map);
    }

    private AuthTokenModel throwIfUnauthorized(Request req) throws CodedException {
        var authToken = req.headers("authorization");
        if (authToken != null) {
            var authTokenModel = joinGameService.getAuthData(authToken);
            if (authTokenModel != null) {
                return authTokenModel;
            }
        }

        throw new CodedException(401, "Not authorized");

    }
}
