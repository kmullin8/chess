package dataaccess;

import model.AuthTokenModel;
import model.GameModel;
import model.UserModel;
import org.junit.jupiter.api.*;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class DataAccessTest {
    private static MySqlDataAccess dataAccess;

    @BeforeAll
    public static void setupDatabase() throws Exception {
        dataAccess = new MySqlDataAccess();
    }

    @BeforeEach
    public void clearDatabase() throws Exception {
        dataAccess.clear();
    }

    @Test
    @DisplayName("Write and Read User - Positive Case")
    public void writeReadUserPositive() throws Exception {
        UserModel user = new UserModel("username", "password", "email@example.com");
        dataAccess.writeUser(user);
        UserModel foundUser = dataAccess.readUser("username");

        assertNotNull(foundUser, "User should exist");
        assertEquals("username", foundUser.getUsername(), "Username mismatch");
    }

    @Test
    @DisplayName("Write User - Negative Case (Duplicate)")
    public void writeUserNegativeDuplicate() throws Exception {
        UserModel user1 = new UserModel("username", "password1", "email1@example.com");
        dataAccess.writeUser(user1);

        UserModel duplicateUser = new UserModel("username", "password2", "email2@example.com");
        assertThrows(Exception.class, () -> dataAccess.writeUser(duplicateUser),
                "Writing a duplicate user should throw an exception");
    }

    @Test
    @DisplayName("Read User - Negative Case (Non-Existent)")
    public void readUserNegativeNonExistent() throws Exception {
        UserModel foundUser = dataAccess.readUser("nonexistent");
        assertNull(foundUser, "Non-existent user should return null");
    }

    @Test
    @DisplayName("Write and Read Auth Token - Positive Case")
    public void writeReadAuthTokenPositive() throws Exception {
        dataAccess.writeUser(new UserModel("username", "password", "email@example.com"));
        AuthTokenModel authToken = dataAccess.writeAuth("username");

        AuthTokenModel foundAuth = dataAccess.readAuth(authToken.getAuthToken());
        assertNotNull(foundAuth, "Auth token should exist");
        assertEquals("username", foundAuth.getUsername(), "Username mismatch in auth token");
    }

    @Test
    @DisplayName("Write Auth Token - Negative Case (Non-Existent User)")
    public void writeAuthTokenNegativeNonExistentUser() {
        assertThrows(Exception.class, () -> dataAccess.writeAuth("nonexistent"),
                "Writing auth token for non-existent user should throw an exception");
    }

    @Test
    @DisplayName("Delete Auth Token - Positive Case")
    public void deleteAuthTokenPositive() throws Exception {
        dataAccess.writeUser(new UserModel("username", "password", "email@example.com"));
        AuthTokenModel authToken = dataAccess.writeAuth("username");

        dataAccess.deleteAuth(authToken.getAuthToken());
        assertNull(dataAccess.readAuth(authToken.getAuthToken()), "Auth token should be deleted");
    }

    @Test
    @DisplayName("Delete Auth Token - Negative Case (Non-Existent)")
    public void deleteAuthTokenNegativeNonExistent() throws Exception {
        assertDoesNotThrow(() -> dataAccess.deleteAuth("nonexistent"),
                "Deleting non-existent auth token should not throw an exception");
    }

    @Test
    @DisplayName("New Game - Positive Case")
    public void newGamePositive() throws Exception {
        GameModel game = dataAccess.newGame("Test Game");
        assertNotNull(game, "Game should be created");
        assertEquals("Test Game", game.getGameName(), "Game name mismatch");
    }

    @Test
    @DisplayName("Update Game - Negative Case (Non-Existent)")
    public void updateGameNegativeNonExistent() throws Exception {
        GameModel game = new GameModel(999, "whiteUser", "blackUser", "NonExistentGame", null);
        assertThrows(Exception.class, () -> dataAccess.updateGame(game),
                "Updating non-existent game should throw an exception");
    }

    @Test
    @DisplayName("Read Game - Negative Case (Non-Existent)")
    public void readGameNegativeNonExistent() throws Exception {
        GameModel foundGame = dataAccess.readGame(999);
        assertNull(foundGame, "Non-existent game should return null");
    }

    @Test
    @DisplayName("List Games - Positive Case")
    public void listGamesPositive() throws Exception {
        dataAccess.newGame("Test Game 1");
        dataAccess.newGame("Test Game 2");

        Collection<GameModel> games = dataAccess.listGames();
        assertEquals(2, games.size(), "There should be two games in the list");
    }
}
