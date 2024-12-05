package websocket.messages;

import com.google.gson.Gson;

import java.util.Objects;

/**
 * Represents a Message the server can send through a WebSocket
 * <p>
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class ServerMessage {
    ServerMessageType serverMessageType;
    private String payload;

    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION,
        GAME_OVER,        // New: Indicates the game has ended
        TURN_TRANSITION,  // New: Indicates it's another player's turn
        INVALID_MOVE      // New: Indicates an invalid move
    }

    public ServerMessage(ServerMessageType type) {
        this.serverMessageType = type;
    }

    public ServerMessageType getServerMessageType() {
        return this.serverMessageType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public <T> T getPayloadAs(Class<T> clazz) {
        return new Gson().fromJson(payload, clazz);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServerMessage)) {
            return false;
        }
        ServerMessage that = (ServerMessage) o;
        return getServerMessageType() == that.getServerMessageType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServerMessageType());
    }
}
