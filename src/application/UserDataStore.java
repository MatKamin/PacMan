package application;

import org.mindrot.jbcrypt.BCrypt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static application.main.*;
import static application.main.connection;
@SuppressWarnings("ALL")
public class UserDataStore {

    private static final UserDataStore instance = new UserDataStore();
    private final Map<String, String> userPasswordMap = new HashMap<>();

    /**
     * Loads and Reads Map from File
     * @throws IOException  Exception
     */
    private void loadMap() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("resources/data/data.properties"));
        for (String key : properties.stringPropertyNames()) {
            userPasswordMap.put(key, properties.get(key).toString());
        }
    }

    /**
     * Get Instance
     * @return instance
     */
    public static UserDataStore getInstance() { return instance; }

    /**
     * Constructor
     */
    private UserDataStore() { }


    /**
     * Check if Username is Taken
     * @param username  Username to check
     * @return          True / False
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
     * Register New User
     * @param username          String
     * @param password          String
     * @throws IOException      Exception
     * @throws SQLException     Exception
     */
    public void registerUser(String username, String password) throws IOException, SQLException {
        loadMap();

        /*
          encrypts Password using BCrypt for safe storing
         */
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        userPasswordMap.put(username, passwordHash);
        Properties properties = new Properties();
        properties.putAll(userPasswordMap);
        properties.store(new FileOutputStream("resources/data/data.properties"), null);

        sqlConnection();

        // the mysql insert statement
        String query = "INSERT INTO User(pk_user, name, highscore, eatenGhosts, creationDate, alltimeScore, gamesPlayed, finisehLevels)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Statement queryPKCount = connection.createStatement();
        ResultSet pkCount = queryPKCount.executeQuery("SELECT COUNT(*) AS c FROM User");
        int pk = 0;
        if(pkCount.next()){
            pk = Integer.parseInt(pkCount.getString("c"));
        }
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setInt (1, pk);
        preparedStmt.setString (2, username);
        preparedStmt.setInt   (3, 0);
        preparedStmt.setInt(4, 0);
        preparedStmt.setDate(5, date);
        preparedStmt.setInt(6, 0);
        preparedStmt.setInt(7, 0);
        preparedStmt.setInt(8, 0);

        // execute the preparedstatement
        preparedStmt.execute();
        connection.close();
    }


    /**
     * Check if Login is correct
     * @param username  String
     * @param password  String
     * @return          True / False
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
     * Deletes User
     * @param username String
     */
    public void deleteUser(String username) {
        userPasswordMap.remove(username.toUpperCase(), userPasswordMap.get(username.toUpperCase()));
        Properties properties = new Properties();
        properties.putAll(userPasswordMap);
        try {
            properties.store(new FileOutputStream("resources/data/data.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
