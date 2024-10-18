package services;

import dataAccess.*;
import model.AuthTokenModel;
import model.UserModel;
import spark.utils.StringUtils;

/**
 *Register a new user
 */
public class RegisterService {

    private DataAccess dataAccess;

    public RegisterService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public String registerUser(UserModel user) throws CodedException {
        if (StringUtils.isEmpty(user.getUsername())) throw new CodedException(400, "missing username");
        if (StringUtils.isEmpty(user.getPassword())) throw new CodedException(400, "missing password");

        try {
            user = dataAccess.writeUser(user);
            return dataAccess.writeAuth(user.getUsername()).getAuthToken();
        } catch (dataAccess.DataAccessException ex) {
            throw new CodedException(403, "Unable to register user");
        }
    }
}
