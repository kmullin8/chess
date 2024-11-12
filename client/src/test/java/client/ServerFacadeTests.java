package client;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.AuthTokenModel;
import model.GameModel;
import model.UserModel;
import org.junit.jupiter.api.*;
import requests.JoinGameRequest;
import server.Server;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private static UserModel newUser;
    private static UserModel secondUser;
    private static UserModel invalidUser;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);

        newUser = new UserModel("player1", "password1", "p1@email.com");
        invalidUser = new UserModel(null, null, null);
        secondUser = new UserModel("player2", "password2", "p2@email.com");
    }

    @AfterAll
    public static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void setup() throws Exception {
        // Clear database before each test
        facade.clearDatabase();
    }

    // Register User Tests
    @Test
    public void testRegisterUser_Success() throws Exception {
        var authData = facade.registerUser(newUser);
        assertNotNull(authData, "Auth data should not be null on success");

        assertTrue(authData.getAuthToken().length() > 10, "Auth token should be valid and non-empty");
    }

    @Test
    public void testRegisterUser_Failure() {
        Exception exception = assertThrows(Exception.class, () -> {
            facade.registerUser(invalidUser);
        });
        assertTrue(exception.getMessage().contains("Request failed"), "Expected failure for invalid registration");
    }

    //login Tests
    @Test
    public void testLogin_Success() throws Exception {
        facade.registerUser(newUser);
        var authData = facade.logIn(newUser);
        assertNotNull(authData);
        assertTrue(authData.getAuthToken().length() > 10);
    }

    @Test
    public void testLogin_Failure() {
        assertThrows(java.lang.Exception.class, () -> {
            facade.logIn(invalidUser); // Invalid credentials
        });
    }

    //logout tests
    @Test
    public void testLogout_Success() throws Exception {
        // Register and login to get a valid auth token
        var authData = facade.registerUser(newUser);
        assertNotNull(authData, "Auth data should not be null after registration");

        // Logout using the auth token
        facade.logOut(authData.getAuthToken());
        // There should be no exception; if logout was successful, the test will pass
    }

    @Test
    public void testLogout_Failure() {
        // Attempt to logout with an invalid auth token
        Exception exception = assertThrows(Exception.class, () -> {
            facade.logOut("invalidAuthToken");
        });
        assertTrue(exception.getMessage().contains("Request failed"), "Expected failure for invalid auth token");
    }

    //listGames tests
    @Test
    public void testListGames_Success() throws Exception {
        // Register and login to get a valid auth token
        var authData = facade.registerUser(newUser);

        // Create a game to ensure there's something to list
        facade.createGame("TestGame1", authData.getAuthToken());

        // List games using the auth token
        GameModel[] games = facade.listGames(authData.getAuthToken());
        assertNotNull(games, "Games list should not be null");
        assertTrue(games.length > 0, "Games list should contain at least one game");
    }

    @Test
    public void testListGames_Failure() {
        // Attempt to list games with an invalid auth token
        Exception exception = assertThrows(Exception.class, () -> {
            facade.listGames("invalidAuthToken");
        });
        assertTrue(exception.getMessage().contains("Request failed"), "Expected failure for invalid auth token");
    }

    //create game tests
    @Test
    public void testCreateGame_Success() throws Exception {
        // Register and login to get a valid auth token
        var authData = facade.registerUser(newUser);

        // Create a game using a valid auth token
        GameModel game = facade.createGame("TestGame2", authData.getAuthToken());
        assertNotNull(game, "Game should not be null on successful creation");
        assertEquals("TestGame2", game.getGameName(), "Game name should match the input");
    }

    @Test
    public void testCreateGame_Failure() {
        // Attempt to create a game with an invalid auth token
        Exception exception = assertThrows(Exception.class, () -> {
            facade.createGame("TestGame3", "invalidAuthToken");
        });
        assertTrue(exception.getMessage().contains("Request failed"), "Expected failure for invalid auth token");
    }

    //join game test
    @Test
    public void testJoinGame_Success() throws Exception {
        // Register two users and have one create a game
        var authData1 = facade.registerUser(newUser);
        int gameId = facade.createGame("TestGame4", authData1.getAuthToken()).getGameID();

        // Register a second user and have them join the game
        var authData2 = facade.registerUser(secondUser);

        JoinGameRequest joinRequest = new JoinGameRequest(gameId, ChessGame.TeamColor.BLACK);
        GameModel joinedGame = facade.joinGame(joinRequest, authData2.getAuthToken());
        assertNotNull(joinedGame, "Joined game should not be null on success");
        assertEquals("TestGame4", joinedGame.getGameName(), "Game name should match the joined game");
    }

    @Test
    public void testJoinGame_Failure() {
        // Attempt to join a game with an invalid auth token
        JoinGameRequest joinRequest = new JoinGameRequest(00, ChessGame.TeamColor.BLACK);

        Exception exception = assertThrows(Exception.class, () -> {
            facade.joinGame(joinRequest, "invalidAuthToken");
        });
        assertTrue(exception.getMessage().contains("Request failed"), "Expected failure for invalid auth token");
    }
}
