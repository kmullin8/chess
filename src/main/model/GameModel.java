package model;

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
    private String whiteUsername;
    /**
     * username of black player
     */
    private String blackUsername;
    /**
     * name of current game
     */
    private String gameName;
    /**\
     * current game
     */
    private ChessGameImpl game;
}
