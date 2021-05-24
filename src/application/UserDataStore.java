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

import static application.gameMechanics.score;
import static application.main.*;
import static application.main.connection;
@SuppressWarnings("ALL")
public class UserDataStore {

    private static final UserDataStore instance = new UserDataStore();
    private final Map<String, String> userPasswordMap = new HashMap<>();

    private void loadMap() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("resources/data/data.properties"));
        for (String key : properties.stringPropertyNames()) {
            userPasswordMap.put(key, properties.get(key).toString());
        }
    }

    public static UserDataStore getInstance() {
        return instance;
    }

    private UserDataStore() { }

    public boolean isUsernameTaken(String username) {
        try {
            loadMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userPasswordMap.containsKey(username);
    }

    public void registerUser(String username, String password) throws IOException, SQLException {
        loadMap();

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        userPasswordMap.put(username, passwordHash);
        Properties properties = new Properties();
        properties.putAll(userPasswordMap);
        properties.store(new FileOutputStream("resources/data/data.properties"), null);



        sqlConnection();

        // the mysql insert statement
        String query = "INSERT INTO User(pk_user, name, highscore, eatenGhosts, creationDate)"
                + " VALUES (?, ?, ?, ?, ?)";


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

        // execute the preparedstatement
        preparedStmt.execute();
        connection.close();
    }


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
