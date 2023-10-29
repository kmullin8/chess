package services;

import dataAccess.*;
import model.AuthTokenModel;
import model.UserModel;
import DataAccessException.DataAccessException;
import spark.utils.StringUtils;

/**
 *Register a new user
 */
public class RegisterUserService {

    private AuthTokenDAO authTokenDAO;
    private GameDOA gameDOA;
    private UserDOA userDOA;

    public RegisterUserService(){
        this.authTokenDAO = new AuthTokenDAO();
        this.gameDOA = new GameDOA();
        this.userDOA = new UserDOA();
    }

    public Object registerUser(UserModel user) throws CodedException{
        if (StringUtils.isEmpty(user.getUsername())) throw new CodedException(400, "missing username");
        if (StringUtils.isEmpty(user.getPassword())) throw new CodedException(400, "missing password");

        try {
            userDOA.createUser(user);
            AuthTokenModel authTokenModel = authTokenDAO.createAuthToken(user.getUsername());

            return authTokenDAO.findAuthToken(user.getUsername());
        } catch (DataAccessException ex) {
            throw new CodedException(403, "Unable to register user");
        }
    }
}
