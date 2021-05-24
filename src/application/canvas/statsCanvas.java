package application.canvas;

//---------------------------------IMPORTS---------------------------------\\


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.sql.*;
import java.util.Locale;

import static application.main.*;


//---------------------------------CLASS---------------------------------\\
@SuppressWarnings("ALL")
public class statsCanvas {

    //--------------------------------------------MENU CANVAS--------------------------------------------\\

    /**
     * Draws the Stats Window
     * @param gc Graphics Context of the Stats window
     */
    public static void play(GraphicsContext gc) {

        sqlConnection();

        //::::::::::: Background :::::::::::\\

        gc.setFill(backgroundColor);                // Set background color
        gc.fillRect(0, 0, width, height);    // Draw background


        //::::::::::: Text Settings  :::::::::::\\

        gc.setFill(Color.YELLOW);          // Set font color
        gc.setStroke(Color.YELLOW);        // Set font color for Stroke
        gc.setFont(pacmanFont);                               // Setting the pacman font
        gc.setTextAlign(TextAlignment.CENTER);                // Align text to center


        //::::::::::: Text :::::::::::\\

        gc.fillText("STATS", width / 2, pacmanFontSize * 1.5);

        gc.setFont(Font.loadFont("file:resources/fonts/emulogic.ttf", 30));
        gc.setTextAlign(TextAlignment.CENTER);      // Align text to center
        gc.setStroke(Color.YELLOW);


        try {

            Statement statementHighscore = connection.createStatement();
            ResultSet resultsHighscore = statementHighscore.executeQuery("SELECT * FROM highscores WHERE username = '" + validUsername.toUpperCase() + "' ORDER BY score DESC LIMIT 1");
            int highestScore = 0;
            while (resultsHighscore.next()) {
                highestScore = Integer.parseInt(resultsHighscore.getString("score"));
            }


            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM User WHERE name = '" + validUsername.toUpperCase() + "'");
            int pk = 0;
            int currentHighest = 1000000;
            while (results.next()) {
                pk = Integer.parseInt(results.getString("pk_user"));
                currentHighest = Integer.parseInt(results.getString("highscore"));
            }

            results = statement.executeQuery("SELECT * FROM User WHERE name = '" + validUsername.toUpperCase() + "'");

            if (highestScore > currentHighest) {
                String query = "UPDATE User SET highscore = "+highestScore+" WHERE pk_user = "+pk;
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.execute();
                connection.close();
            }
            sqlConnection();

            statement = connection.createStatement();
            results = statement.executeQuery("SELECT * FROM User WHERE name = '" + validUsername.toUpperCase() + "'");


            gc.strokeText("USERNAME", width / 4, heightOneBlock * 10);
            gc.strokeText("HIGHSCORE", width / 4, heightOneBlock * 13);
            gc.strokeText("GHOSTS EATEN", width / 4, heightOneBlock * 16);
            gc.strokeText("DATE OF CREATION", width / 4, heightOneBlock * 19);
            gc.strokeText("COINS", width / 4, heightOneBlock * 22);
            gc.strokeText("GAMES PLAYED", width / 4, heightOneBlock * 25);
            gc.strokeText("CLEARED LEVELS", width / 4, heightOneBlock * 28);




            while (results.next()) {
                gc.strokeText(results.getString("name"), (width / 4) * 3, heightOneBlock * 10);
                gc.strokeText(results.getString("highscore"), (width / 4) * 3, heightOneBlock * 13);
                gc.strokeText(results.getString("eatenGhosts"), (width / 4) * 3, heightOneBlock * 16);
                gc.strokeText(results.getString("creationDate"), (width / 4) * 3, heightOneBlock * 19);
                gc.strokeText(String.valueOf(Integer.parseInt(results.getString("alltimeScore")) / 10), (width / 4) * 3, heightOneBlock * 22);
                gc.strokeText(results.getString("gamesPlayed"), (width / 4) * 3, heightOneBlock * 25);
                gc.strokeText(results.getString("finisehLevels"), (width / 4) * 3, heightOneBlock * 28);
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Could not retrieve data from the database " + e.getMessage());
        }
    }
}
