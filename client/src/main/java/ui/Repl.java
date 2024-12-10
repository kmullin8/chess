package ui;

import management.State;

import java.util.Scanner;

import static management.EscapeSequences.*;

public class Repl {
    private Client client;
    private String serverUrl;
    private State state;

    private PreLoginClient preLoginClient;
    private PostLoginClient postLoginClient;
    private GamePlayClient gamePlayClient;

    public Repl(String serverUrl) {
        this.serverUrl = serverUrl;
        this.state = State.SIGNEDOUT;

        preLoginClient = new PreLoginClient(serverUrl);
        postLoginClient = new PostLoginClient(serverUrl);
    }

    public void run() throws InterruptedException {
        client = preLoginClient; // start in prelogin

        System.out.println("\uD83D\uDC36 Welcome to 240 chess. Type help to get started.");
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            Thread.sleep(1000);
            System.out.print("[" + state + "] >>> ");
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result + RESET_TEXT_COLOR);

                if (result.startsWith("Logged in") && state == State.SIGNEDOUT || result.startsWith("left game") && state == State.PLAYINGGAME) {
                    state = State.SIGNEDIN;
                } else if (result.startsWith("Joined Game") && state == State.SIGNEDIN) {
                    state = State.PLAYINGGAME;
                    //GameStateManager.getInstance().setCurrentGame(postLoginClient.getCurrentGame());
                    client = getClient();
                } else if (result.startsWith("Logged out") && state == State.SIGNEDIN) {
                    state = State.SIGNEDOUT;
                }
            } catch (Throwable e) {
                System.out.print(e.toString());
            }

            if (state != State.PLAYINGGAME) {
                client = getClient();
            }
        }
        System.out.println();
    }

    private Client getClient() {
        return switch (state) {
            case SIGNEDOUT -> preLoginClient;
            case SIGNEDIN -> postLoginClient;
            case PLAYINGGAME -> new GamePlayClient(serverUrl);
        };
    }
}
