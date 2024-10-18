package model;

import java.util.UUID;

/**
 * creates key for a user
 */
public class AuthTokenModel {

    /**
     * unique token for authentication
     */
    private String authToken;

    /**
     * username for player
     */
    private String username;

    public AuthTokenModel(String username){
        this.username = username;
        this.authToken = UUID.randomUUID().toString();
    }

    public void setNewAuthToken(){
        this.authToken = UUID.randomUUID().toString();
    }

    public String getUsername(){
        return username;
    }

    public String getAuthToken(){
        return authToken;
    }

    public void setAuthToken(String newAuthToken){
        this.authToken = newAuthToken;
    }
}
