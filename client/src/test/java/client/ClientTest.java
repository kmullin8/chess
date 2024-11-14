package client;

import org.junit.jupiter.api.*;
import ui.Repl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void testHelpCommand() {
        // Simulate user input "help" followed by "quit" to exit the REPL
        String simulatedInput = "help\nregister username password email\nlogin username password\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Run the Repl
        new Repl("http://localhost:8080").run();


        // Capture the output and assert it contains help text
    }
}
