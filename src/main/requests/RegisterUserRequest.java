package requests;

/**
 * processes Register a new user
 */
public class RegisterUserRequest {
    private String username;
    private String password;
    private String email;

    /**
     *
     * @param username unique identifier for user
     * @param password key for username
     * @param email email of current player
     */
    public RegisterUserRequest(String username, String password, String email) {
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
