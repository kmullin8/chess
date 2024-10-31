package services;

import dataaccess.*;
import model.UserModel;
import org.mindrot.jbcrypt.BCrypt;
import spark.utils.StringUtils;

/**
 * Register a new user
 */
public class RegisterService {

    private DataAccess dataAccess;

    public RegisterService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public String registerUser(UserModel user) throws CodedException {
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new CodedException(400, "missing username");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new CodedException(400, "missing password");
        }

        try {
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            UserModel hashedUser = new UserModel(user.getUsername(), hashedPassword, user.getEmail());

            hashedUser = dataAccess.writeUser(hashedUser);
            return dataAccess.writeAuth(hashedUser.getUsername()).getAuthToken();
        } catch (dataaccess.DataAccessException ex) {
            throw new CodedException(403, "Unable to register user");
        }
    }
}
