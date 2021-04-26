package application;

import org.mindrot.jbcrypt.BCrypt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UserDataStore {

    private static final UserDataStore instance = new UserDataStore();

    /**
     * Map of usernames to their password hashes.
     */
    private final Map<String, String> userPasswordMap = new HashMap<>();

    /**
     * Method to read the input data
     * @throws IOException Exception
     */
    private void loadMap() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("data.properties"));

        for (String key : properties.stringPropertyNames()) {
            userPasswordMap.put(key, properties.get(key).toString());
        }
    }

    public static UserDataStore getInstance() {
        return instance;
    }

    private UserDataStore() {
    }

    /**
     * Checks if the username is already taken
     *
     * @param username Username to check
     * @return true or false
     */
    public boolean isUsernameTaken(String username) {
        try {
            loadMap();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userPasswordMap.containsKey(username);
    }

    /**
     * Registration of the user
     *
     * @param username username to register
     * @param password password to register
     * @throws IOException Exception
     */
    public void registerUser(String username, String password) throws IOException {

        loadMap();

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        userPasswordMap.put(username, passwordHash);

        Properties properties = new Properties();

        properties.putAll(userPasswordMap);

        properties.store(new FileOutputStream("data.properties"), null);

    }

    /**
     * Checks if the Login information is correct
     *
     * @param username username to check
     * @param password password to check
     * @return true or false
     */
    public boolean isLoginCorrect(String username, String password) {

        try {
            loadMap();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // username isn't registered
        if (!userPasswordMap.containsKey(username)) {
            return false;
        }

        String storedPasswordHash = userPasswordMap.get(username);

        return BCrypt.checkpw(password, storedPasswordHash);
    }

    /**
     * Deletes the user, if wanted
     *
     * @param username username to delete
     */
    public void deleteUser(String username) {
        userPasswordMap.remove(username.toUpperCase(), userPasswordMap.get(username.toUpperCase()));

        Properties properties = new Properties();
        properties.putAll(userPasswordMap);

        try {
            properties.store(new FileOutputStream("data.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
