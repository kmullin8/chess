package dataAccess;
import DataAccessException.DataAccessException;
import model.AuthTokenModel;
import model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *accesses database for user
 */
public class UserDOA {

    /**
     * user database
     */
    private static List<UserModel> userDatabase;

    public UserDOA(){
        userDatabase = new ArrayList<>();
    }

    /**
     *
     * @throws DataAccessException  throws if error
     */
    public void createUser(UserModel newUser) throws DataAccessException {
        if (userExists(newUser.getUsername())) {
            throw new DataAccessException("Username already exists");
        }
        newUser.setUserID(userDatabase.size()); // set userId
        userDatabase.add(newUser);
    }

    private boolean userExists(String username) {
        for (UserModel userModel : userDatabase) {
            if (Objects.equals(userModel.getUsername(), username)) {
                return true;
            }
        }
        return false;
    }

    public static UserModel getUser(String username) throws DataAccessException {
        for (UserModel userModel : userDatabase) {
            if (Objects.equals(userModel.getUsername(), username)) {
                return userModel;
            }
        }
        return null;
    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public void clear() throws DataAccessException {
        userDatabase.clear();
    }
}
