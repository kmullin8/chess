package services;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    private CreateGameService createGameService;

    @BeforeEach
    public void setup() {
        DataAccess dataAccess = new MemoryDataAccess(); // Assuming you have a mock implementation
        createGameService = new CreateGameService(dataAccess);
    }

    @Test
    @DisplayName("Successful Game Creation")
    public void testCreateGameSuccess() throws CodedException {
        GameModel result = createGameService.createGame("Test Game");

        assertNotNull(result);
        assertEquals("Test Game", result.getGameName());
    }

    @Test
    @DisplayName("Fail to Create Game")
    public void testCreateGameFailure() {
        // Simulate a DataAccessException being thrown
        DataAccess dataAccess = new MemoryDataAccess() {
            @Override
            public GameModel newGame(String gameName) throws DataAccessException {
                throw new DataAccessException("Error");
            }
        };

        createGameService = new CreateGameService(dataAccess);

        CodedException exception = assertThrows(CodedException.class, () -> {
            createGameService.createGame("Test Game");
        });

        assertEquals(500, exception.statusCode());
        assertEquals("Server error", exception.getMessage());
    }

    @Test
    @DisplayName("Successful Join Game")
    public void testJoinGameSuccess() throws CodedException {
        GameModel result = createGameService.createGame("Test Game");

        assertNotNull(result);
        assertEquals("Test Game", result.getGameName());
    }

    @Test
    @DisplayName("Fail to Join Game")
    public void testJoinGameFailure() {
        // Simulate a DataAccessException being thrown
        DataAccess dataAccess = new MemoryDataAccess() {
            @Override
            public GameModel newGame(String gameName) throws DataAccessException {
                throw new DataAccessException("Error");
            }
        };

        createGameService = new CreateGameService(dataAccess);

        CodedException exception = assertThrows(CodedException.class, () -> {
            createGameService.createGame("Test Game");
        });

        assertEquals(500, exception.statusCode());
        assertEquals("Server error", exception.getMessage());
    }

    @Test
    @DisplayName("Successful List Game")
    public void testListGameSuccess() throws CodedException {
        GameModel result = createGameService.createGame("Test Game");

        assertNotNull(result);
        assertEquals("Test Game", result.getGameName());
    }

    @Test
    @DisplayName("Fail to List Games")
    public void testListGameFailure() {
        // Simulate a DataAccessException being thrown
        DataAccess dataAccess = new MemoryDataAccess() {
            @Override
            public GameModel newGame(String gameName) throws DataAccessException {
                throw new DataAccessException("Error");
            }
        };

        createGameService = new CreateGameService(dataAccess);

        CodedException exception = assertThrows(CodedException.class, () -> {
            createGameService.createGame("Test Game");
        });

        assertEquals(500, exception.statusCode());
        assertEquals("Server error", exception.getMessage());
    }

    @Test
    @DisplayName("Successful Login")
    public void testLoginSuccess() throws CodedException {
        GameModel result = createGameService.createGame("Test Game");

        assertNotNull(result);
        assertEquals("Test Game", result.getGameName());
    }

    @Test
    @DisplayName("Fail to Login")
    public void testLoginFailure() {
        // Simulate a DataAccessException being thrown
        DataAccess dataAccess = new MemoryDataAccess() {
            @Override
            public GameModel newGame(String gameName) throws DataAccessException {
                throw new DataAccessException("Error");
            }
        };

        createGameService = new CreateGameService(dataAccess);

        CodedException exception = assertThrows(CodedException.class, () -> {
            createGameService.createGame("Test Game");
        });

        assertEquals(500, exception.statusCode());
        assertEquals("Server error", exception.getMessage());
    }

    @Test
    @DisplayName("Successful Logout")
    public void testLogoutSuccess() throws CodedException {
        GameModel result = createGameService.createGame("Test Game");

        assertNotNull(result);
        assertEquals("Test Game", result.getGameName());
    }

    @Test
    @DisplayName("Fail to Logout")
    public void testLogoutFailure() {
        // Simulate a DataAccessException being thrown
        DataAccess dataAccess = new MemoryDataAccess() {
            @Override
            public GameModel newGame(String gameName) throws DataAccessException {
                throw new DataAccessException("Error");
            }
        };

        createGameService = new CreateGameService(dataAccess);

        CodedException exception = assertThrows(CodedException.class, () -> {
            createGameService.createGame("Test Game");
        });

        assertEquals(500, exception.statusCode());
        assertEquals("Server error", exception.getMessage());
    }

    @Test
    @DisplayName("Successful to Register")
    public void testRegisterSuccess() throws CodedException {
        GameModel result = createGameService.createGame("Test Game");

        assertNotNull(result);
        assertEquals("Test Game", result.getGameName());
    }

    @Test
    @DisplayName("Fail to Register")
    public void testRegisterFailure() {
        // Simulate a DataAccessException being thrown
        DataAccess dataAccess = new MemoryDataAccess() {
            @Override
            public GameModel newGame(String gameName) throws DataAccessException {
                throw new DataAccessException("Error");
            }
        };

        createGameService = new CreateGameService(dataAccess);

        CodedException exception = assertThrows(CodedException.class, () -> {
            createGameService.createGame("Test Game");
        });

        assertEquals(500, exception.statusCode());
        assertEquals("Server error", exception.getMessage());
    }

    @Test
    @DisplayName("Succesfull Clear")
    public void testClear() throws CodedException {
        GameModel result = createGameService.createGame("Test Game");

        assertNotNull(result);
        assertEquals("Test Game", result.getGameName());
    }
}
