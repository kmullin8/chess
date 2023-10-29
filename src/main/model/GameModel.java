package model;

import chess.ChessGame;
import chess.ChessGameImpl;

/**
 * creates a game
 */
public class GameModel {

    /**
     * unique game ID
     */
    private int gameID;
    /**
     * username of white player
     */
    private int whiteID;
    /**
     * username of black player
     */
    private int blackID;
    /**
     * name of current game
     */
    private String gameName;
    /**\
     * current game
     */
    private ChessGameImpl game;

    public GameModel(GameModel copy){
        this.gameID = copy.gameID;
        this.gameName = copy.gameName;
        this.blackID = copy.blackID;
        this.whiteID = copy.whiteID;
        this.game = new chess.ChessGameImpl(copy.getGame());
    }

    public GameModel(int gameID, String gameName) {
        this.gameID = gameID;
        this.blackID = 0;
        this.whiteID = 0;
        this.gameName = gameName;
        this.game = new chess.ChessGameImpl();
    }

    public ChessGame getGame() {
        return game;
    }

    public String getGameName(){
        return gameName;
    }

    public int getGameID(){
        return gameID;
    }
}
