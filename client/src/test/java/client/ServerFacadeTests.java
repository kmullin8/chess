package client;

import chess.ChessGame;
import model.GameModel;
import model.UserModel;
import org.junit.jupiter.api.*;
import requests.JoinGameRequest;
import server.Server;
import serveraccess.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private static UserModel user1;
    private static UserModel user2;
    private static UserModel invalidUser;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);

        user1 = new UserModel("player1", "password1", "p1@email.com");
        invalidUser = new UserModel(null, null, null);
        user2 = new UserModel("player2", "password2", "p2@email.com");
    }

    @AfterAll
    public static void stopServer() throws Exception {
        facade.clearDatabase();
        server.stop();
    }

    @BeforeEach
    public void setup() throws Exception {
        // Clear database before each test
        facade.clearDatabase();
    }

    // Register User Tests
    @Test
    public void testRegisterUserSuccess() throws Exception {
        var authData = facade.registerUser(user1);
        assertNotNull(authData, "Auth data should not be null on success");

        assertTrue(authData.getAuthToken().length() > 10, "Auth token should be valid and non-empty");
    }

    @Test
    public void testRegisterUserFailure() {
        Exception exception = assertThrows(Exception.class, () -> {
            facade.registerUser(invalidUser);
        });
        assertTrue(exception.getMessage().contains("Request failed"), "Expected failure for invalid registration");
    }

    //login Tests
    @Test
    public void testLoginSuccess() throws Exception {
        facade.registerUser(user1);
        var authData = facade.logIn(user1);
        assertNotNull(authData);
        assertTrue(authData.getAuthToken().length() > 10);
    }

    @Test
    public void testLoginFailure() {
        assertThrows(java.lang.Exception.class, () -> {
            facade.logIn(invalidUser); // Invalid credentials
        });
    }

    //logout tests
    @Test
    public void testLogoutSuccess() throws Exception {
        // Register and login to get a valid auth token
        var authData = facade.registerUser(user1);
        assertNotNull(authData, "Auth data should not be null after registration");

        // Logout using the auth token
        facade.logOut(authData.getAuthToken());
        // There should be no exception; if logout was successful, the test will pass
    }

    @Test
    public void testLogoutFailure() {
        // Attempt to logout with an invalid auth token
        Exception exception = assertThrows(Exception.class, () -> {
            facade.logOut("invalidAuthToken");
        });
        assertTrue(exception.getMessage().contains("Request failed"), "Expected failure for invalid auth token");
    }

    //listGames tests
    @Test
    public void testListGamesSuccess() throws Exception {
        // Register and login to get a valid auth token
        var authData = facade.registerUser(user1);

        // Create a game to ensure there's something to list
        facade.createGame("TestGame1", authData.getAuthToken());

        // List games using the auth token
        GameModel[] games = facade.listGames(authData.getAuthToken());
        assertNotNull(games, "Games list should not be null");
        assertTrue(games.length > 0, "Games list should contain at least one game");
    }

    @Test
    public void testListGamesFailure() {
        // Attempt to list games with an invalid auth token
        Exception exception = assertThrows(Exception.class, () -> {
            facade.listGames("invalidAuthToken");
        });
        assertTrue(exception.getMessage().contains("Request failed"), "Expected failure for invalid auth token");
    }

    //create game tests
    @Test
    public void testCreateGameSuccess() throws Exception {
        // Register and login to get a valid auth token
        var authData = facade.registerUser(user1);

        // Create a game using a valid auth token
        GameModel game = facade.createGame("TestGame2", authData.getAuthToken());
        assertNotNull(game, "Game should not be null on successful creation");
        assertEquals("TestGame2", game.getGameName(), "Game name should match the input");
    }

    @Test
    public void testCreateGameFailure() {
        // Attempt to create a game with an invalid auth token
        Exception exception = assertThrows(Exception.class, () -> {
            facade.createGame("TestGame3", "invalidAuthToken");
        });
        assertTrue(exception.getMessage().contains("Request failed"), "Expected failure for invalid auth token");
    }

    //join game test
    @Test
    public void testJoinGameSuccess() throws Exception {
        // Register two users and have one create a game
        var authData1 = facade.registerUser(user1);
        int gameId = facade.createGame("TestGame4", authData1.getAuthToken()).getGameID();

        // Register a second user and have them join the game
        var authData2 = facade.registerUser(user2);

        JoinGameRequest joinRequest = new JoinGameRequest(gameId, ChessGame.TeamColor.BLACK);
        GameModel joinedGame = facade.joinGame(joinRequest, authData2.getAuthToken());
        assertNotNull(joinedGame, "Joined game should not be null on success");
        assertEquals("TestGame4", joinedGame.getGameName(), "Game name should match the joined game");
    }

    @Test
    public void testJoinGameFailure() {
        // Attempt to join a game with an invalid auth token
        JoinGameRequest joinRequest = new JoinGameRequest(00, ChessGame.TeamColor.BLACK);

        Exception exception = assertThrows(Exception.class, () -> {
            facade.joinGame(joinRequest, "invalidAuthToken");
        });
        assertTrue(exception.getMessage().contains("Request failed"), "Expected failure for invalid auth token");
    }
}
