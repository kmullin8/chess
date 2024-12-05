package websocket.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandDispatcher {
    private final Map<String, CommandFactory> commandRegistry = new HashMap<>();

    public void registerCommand(String commandType, CommandFactory factory) {
        commandRegistry.put(commandType, factory);
    }

    public GameCommand getCommand(String commandType, Object... params) {
        CommandFactory factory = commandRegistry.get(commandType);
        if (factory == null) {
            throw new IllegalArgumentException("Unknown command: " + commandType);
        }
        return factory.create(params);
    }
}
