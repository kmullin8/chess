package httpService;

import requests.LoginRequest;
import results.LoginResult;

/**
 * Logs in an existing user (returns a new authToken)
 */
public class LoginService {

    /**
     *
     * @param request request from user
     * @return result of request, returns new authToken
     */
    public LoginResult Login(LoginRequest request) {
        LoginResult result = new LoginResult();
        return result;
    }
}
