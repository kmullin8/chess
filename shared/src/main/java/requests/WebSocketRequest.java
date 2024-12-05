package requests;

public class WebSocketRequest {
    private String type;
    private String username;
    private String gameId; // Add this field
    private String move; // Assuming this is for moves, adjust as needed

    // Getter and Setter for `type`
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getter and Setter for `username`
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for `gameId`
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    // Getter and Setter for `move`
    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
