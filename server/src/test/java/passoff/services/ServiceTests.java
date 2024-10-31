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

}
