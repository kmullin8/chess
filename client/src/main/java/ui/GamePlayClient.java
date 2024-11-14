package ui;

public class GamePlayClient implements Client {
    private ServerFacade server;
    private String serverUrl;

    public GamePlayClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    @Override
    public String eval(String input) {
        return null;
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void quit() {

    }
}
