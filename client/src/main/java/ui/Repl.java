package ui;

import server.Server;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private Client client;
    private String serverUrl;
    private State state;
    private Server server;

    public Repl(String serverUrl) {
        this.serverUrl = serverUrl;
        this.state = State.SIGNEDOUT;


        server = new Server();
        server.run(8080);
    }

    public void run() {
        System.out.println("\uD83D\uDC36 Welcome to 240 chess. Type help to get started.");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            client = getClient(); // determine what client to use
            // Display prompt inline
            System.out.print("[" + state + "] >>> ");

            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result + RESET_TEXT_COLOR);

                //change status
                if (result.startsWith("logged in")) {
                    state = State.SIGNEDIN;
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private Client getClient() {
        if (state == State.SIGNEDOUT) {
            return new PreLoginClient(serverUrl);
        } else if (state == State.SIGNEDIN) {
            return new PostLoginClient(serverUrl);
        } else if (state == State.PLAYINGGAME) {
            return new GamePlayClient(serverUrl);
        }
        return null;
    }

    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
    }
}
