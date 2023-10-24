package results;

import chess.ChessGameImpl;

import java.util.List;

/**
 * result of Gives a list of all games
 */
public class ListGamesResult {
    /**
     * message to return upon failure
     */
    private String message;
    /**
     * list of games
     */
    private List<ChessGameImpl> games;

    /**
     * default constructor
     */
    public ListGamesResult() {
        // Default constructor
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChessGameImpl> getGames() {
        return games;
    }

    public void setGames(List<ChessGameImpl> games) {
        this.games = games;
    }
}
