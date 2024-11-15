package requests;

/**
 * processes Logs in an existing user (returns a new authToken)
 */
public class LoginRequest {

    /**
     * username of current player
     */
    private String username;
    /**
     * password of current player
     */
    private String password;

    /**
     *
     * @param usernameInput name of current player
     * @param passwordInput password of current player
     */
    public LoginRequest(String usernameInput, String passwordInput){
        this.username = usernameInput;
        this. password = passwordInput;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
