package websocket.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandDispatcher {
    private final Map<String, CommandFactory> commandRegistry = new HashMap<>();

    public void registerCommand(String commandType, CommandFactory factory) {
        commandRegistry.put(commandType, factory);
    }
}
