package results;

import chess.ChessGame;

import java.util.List;

/**
 * result of Gives a list of all games
 */
public class ListGameResult {
    /**
     * message to return upon failure
     */
    private String message;
    /**
     * list of games
     */
    private List<ChessGame> games;

    /**
     * default constructor
     */
    public ListGameResult() {
        // Default constructor
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChessGame> getGames() {
        return games;
    }

    public void setGames(List<ChessGame> games) {
        this.games = games;
    }
}
