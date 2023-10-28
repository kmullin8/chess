package dataAccess;

import DataAccessException.DataAccessException;
import model.AuthTokenModel;
import model.GameModel;

import java.util.ArrayList;
import java.util.List;

/**
 * accesses database for authToken
 */
public class AuthTokenDAO {

    /**
     * authtoken database
     */
    private static List<AuthTokenModel> authTokenModelDatabase;

    public AuthTokenDAO(){
        authTokenModelDatabase = new ArrayList<>();
    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public void createAuthToken(String userName) throws DataAccessException{
        //creates new authToken with username and stores to database
        authTokenModelDatabase.add(new AuthTokenModel(userName));
    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public String findAuthToken(String username) throws DataAccessException{
        try {
            for (AuthTokenModel authTokenModel : authTokenModelDatabase) {
                if (authTokenModel.getUsername().equals(username)) {
                    return authTokenModel.getAuthToken();
                }
            }
        } catch (Exception ex) {
            // Handle the exception or wrap it in a DataAccessException if necessary
            throw new DataAccessException("Failed to create authToken");
        }
        return null;
    }

    public static AuthTokenModel getAuthTokenModel(String authToken){
        for (AuthTokenModel authTokenModel : authTokenModelDatabase) {
            if (authTokenModel.getAuthToken().equals(authToken)) {
                return authTokenModel;
            }
        }
        return null;
    }

    /**
     *
     * @throws DataAccessException throws if error
     */
    public void deleteAuthToken() throws DataAccessException{

    }

    /**
     * @throws DataAccessException throws if error
     */
    public void deleteAllAuthTokens() throws DataAccessException{

    }
}
