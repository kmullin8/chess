package client;

import dataaccess.DataAccessException;
import model.AuthTokenModel;
import model.GameModel;
import model.UserModel;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private static UserModel newUser;
    private static UserModel invalidUser;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);

        newUser = new UserModel("player1", "password", "p1@email.com");
        invalidUser = new UserModel(null, null, null);
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


    //join game test
}
