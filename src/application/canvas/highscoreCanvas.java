package application.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static application.gameMechanics.allowNextMoveLeft;
import static application.gameMechanics.highscore;
import static application.main.*;


public class highscoreCanvas {


    public static void play(GraphicsContext gc) throws FileNotFoundException {

        sqlConnection();

        //::::::::::: Background :::::::::::\\

        gc.setFill(Color.BLACK);                // Set background color
        gc.fillRect(0, 0, width, height);    // Draw background


        //::::::::::: Text :::::::::::\\

        Font highscoreFont = Font.loadFont("file:resources/fonts/emulogic.ttf", 35);
        gc.setFont(highscoreFont);
        gc.setTextAlign(TextAlignment.CENTER);      // Align text to center
        gc.setStroke(Color.YELLOW);

        try {

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM highscores ORDER BY score DESC LIMIT 10");

            gc.strokeText("HIGHSCORES", width / 2, heightOneBlock * 3);
            gc.strokeText("PLAYER", width / 4, heightOneBlock * 6);
            gc.strokeText("SCORE", (width / 4) * 2, heightOneBlock * 6);
            gc.strokeText("RANG", (width / 4) * 3, heightOneBlock * 6);
            gc.setLineWidth(5);
            gc.setLineDashes(10);
            gc.strokeLine(0, heightOneBlock*8, width, heightOneBlock*8);
            gc.setLineWidth(1);
            gc.setLineDashes(0);

            int i = 1;

            while (results.next()) {

                gc.strokeText(results.getString("username"), width / 4, heightOneBlock * (8 + (i * 3) ));
                gc.strokeText(results.getString("score"), (width / 4) * 2, heightOneBlock * (8 + (i * 3) ));
                gc.strokeText(String.valueOf(i), (width / 4) * 3, heightOneBlock * (8 + (i * 3) ));

                i++;
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Could not retrieve data from the database " + e.getMessage());
        }

    }
}
