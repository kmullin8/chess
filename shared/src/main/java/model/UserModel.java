package model;

import com.google.gson.Gson;

/**
 * creates a user
 */
public class UserModel {

    /**
     * username of player
     */
    private String username;
    /**
     * password for player
     */
    private String password;
    /**
     * email for player
     */
    private String email;

    /**
     * ID associated with the user.
     */
    private int userID;

    public UserModel(int userID) {
        this.userID = userID;
    }

    public UserModel(UserModel copy) {
        this.username = copy.username;
        this.password = copy.password;
        this.email = copy.email;
        this.userID = copy.userID;
    }

    public UserModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public void setUserID(int id) {
        this.userID = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
