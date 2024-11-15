package ui;

import model.AuthTokenModel;

public interface Client {
    String eval(String input);

    String help();

    void quit();

    void setAuthToken(AuthTokenModel authToken);

    AuthTokenModel getAuthToken();
}
