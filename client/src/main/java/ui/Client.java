package ui;

import model.AuthTokenModel;

public interface Client {
    String eval(String input);

    String help();

    void setAuthToken(AuthTokenModel authToken);

    AuthTokenModel getAuthToken();
}
