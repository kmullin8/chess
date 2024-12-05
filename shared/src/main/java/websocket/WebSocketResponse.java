package websocket;

import java.time.Instant;

public class WebSocketResponse {
    private String type;
    private String message;
    private long timestamp;

    public WebSocketResponse(String type, String message) {
        this.type = type;
        this.message = message;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
