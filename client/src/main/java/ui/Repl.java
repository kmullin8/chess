package ui;

import model.AuthTokenModel;
import model.GameModel;
import server.Server;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private Client client;
    private String serverUrl;
    private State state;
    private Server server;

    private PreLoginClient preLoginClient;
    private PostLoginClient postLoginClient;
    private GamePlayClient gamePlayClient;
    private AuthTokenModel authToken;

    public Repl(String serverUrl) {
        this.serverUrl = serverUrl;
        this.state = State.SIGNEDOUT;


        server = new Server();
        server.run(8080);

        preLoginClient = new PreLoginClient(serverUrl);
        postLoginClient = new PostLoginClient(serverUrl, null);
        gamePlayClient = new GamePlayClient(serverUrl);
    }

    public void run() {
        client = preLoginClient; // start in prelogin

        System.out.println("\uD83D\uDC36 Welcome to 240 chess. Type help to get started.");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            // Display prompt inline
            System.out.print("[" + state + "] >>> ");

            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result + RESET_TEXT_COLOR);

                //change status
                if (result.startsWith("Logged in") && state == State.SIGNEDOUT) { // enter if signed out on just logged in
                    state = State.SIGNEDIN;
                    postLoginClient.setAuthToken(preLoginClient.getAuthToken());//set authToken once logged in
                    gamePlayClient.setAuthToken(preLoginClient.getAuthToken());
                } else if (result.startsWith("Joined Game") && state == State.SIGNEDIN) {
                    state = State.PLAYINGGAME;
                } else if (result.startsWith("Loggout out") && state == State.SIGNEDIN) {
                    state = State.SIGNEDOUT;
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
            client = getClient(); // determine what client to use
        }
        System.out.println();
    }

    private Client getClient() {
        if (state == State.SIGNEDOUT) {
            return preLoginClient;
        } else if (state == State.SIGNEDIN) {
            return postLoginClient;
        } else if (state == State.PLAYINGGAME) {
            return gamePlayClient;
        }
        return null;
    }

    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
    }
}
