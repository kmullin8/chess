package requests;

/**
 * processes Register a new user
 */
public class RegisterRequest {

    /**
     * new username
     */
    private String username;
    /**
     * new password for user
     */
    private String password;
    /**
     * new email for user
     */
    private String email;

    /**
     *
     * @param username unique identifier for user
     * @param password key for username
     * @param email email of current player
     */
    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }
}
