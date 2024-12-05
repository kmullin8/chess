package websocket.commands;

public interface CommandFactory {
    GameCommand create(Object... params);
}
