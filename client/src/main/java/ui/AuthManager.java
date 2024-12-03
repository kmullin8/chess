package ui;

import model.AuthTokenModel;

public class AuthManager {
    private static AuthManager instance;
    private AuthTokenModel authToken;

    private AuthManager() {
    }

    public static synchronized AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    public synchronized AuthTokenModel getAuthToken() {
        return authToken;
    }

    public synchronized void setAuthToken(AuthTokenModel token) {
        this.authToken = token;
    }
}

