package application.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.Scanner;

import static application.gameMechanics.highscore;
import static application.main.*;


public class highscoreCanvas {

    public static String[][] scoreArrayString = new String[6][2];

    public static void rankScores() throws FileNotFoundException {
        //::::::::::: List :::::::::::\\

        scoreArrayString[0][0] = "Tom";
        scoreArrayString[0][1] = "1";
        scoreArrayString[1][0] = "Tom";
        scoreArrayString[1][1] = "1";
        scoreArrayString[2][0] = "Tom";
        scoreArrayString[2][1] = "1";
        scoreArrayString[3][0] = "Tom";
        scoreArrayString[3][1] = "1";
        scoreArrayString[4][0] = "Tom";
        scoreArrayString[4][1] = "1";
        scoreArrayString[5][0] = "Tom";
        scoreArrayString[5][1] = "1";

        // pass the path to the file as a parameter
        File file = new File("resources/highscores.txt");
        Scanner sc = new Scanner(file);

        sc.useDelimiter(",");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                scoreArrayString[i][j] = sc.next();
            }
        }


        Scanner sc2 = new Scanner(file);
        sc2.useDelimiter("," + scoreArrayString[5][0]);

        // Delete Last entry if not in top 5
        if (Integer.parseInt(scoreArrayString[5][1]) < Integer.parseInt(scoreArrayString[4][1])) {
            String paste = sc2.next();
            try {
                FileWriter writer = new FileWriter("resources/highscores.txt", false);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(paste);
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        for (int z = 4; z > -1; z--) {
            if (Integer.parseInt(scoreArrayString[z + 1][1]) > Integer.parseInt(scoreArrayString[z][1])) {
                String[][] between = new String[1][2];
                between[0][0] = scoreArrayString[z][0];
                between[0][1] = scoreArrayString[z][1];

                String[][] between2 = new String[1][2];
                between2[0][0] = scoreArrayString[z + 1][0];
                between2[0][1] = scoreArrayString[z + 1][1];

                scoreArrayString[z][0] = between2[0][0];
                scoreArrayString[z][1] = between2[0][1];

                scoreArrayString[z + 1][0] = between[0][0];
                scoreArrayString[z + 1][1] = between[0][1];
            }
        }

        try {
            FileWriter writer = new FileWriter("resources/highscores.txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(scoreArrayString[0][0] + "," + scoreArrayString[0][1] + "," + scoreArrayString[1][0] + "," + scoreArrayString[1][1] + "," + scoreArrayString[2][0] + "," + scoreArrayString[2][1] + "," + scoreArrayString[3][0] + "," + scoreArrayString[3][1] + "," + scoreArrayString[4][0] + "," + scoreArrayString[4][1] + ",empty,0");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void play(GraphicsContext gc) throws FileNotFoundException {

        rankScores();

        //::::::::::: Background :::::::::::\\

        gc.setFill(Color.BLACK);                // Set background color
        gc.fillRect(0, 0, width, height);    // Draw background


        //::::::::::: Text :::::::::::\\

        gc.setFill(fontColor);          // Set font color
        gc.setStroke(fontColor);        // Set font color for Stroke
        Font highscoreFont = Font.loadFont("file:resources/fonts/emulogic.ttf", 50);
        gc.setFont(highscoreFont);
        gc.setTextAlign(TextAlignment.CENTER);      // Align text to center


        gc.strokeText("HIGHSCORES", width / 2, height / 7);


        gc.setFont(Font.loadFont("file:resources/fonts/emulogic.ttf", 30));

        gc.strokeText("PLAYER", width / 4, height / 6 + 70);

        gc.strokeText("SCORE", width / 4 + width / 4, height / 6 + 70);
        gc.strokeText("RANG", width / 4 + width / 4 + width / 4, height / 6 + 70);
        gc.strokeText("----------------------------------------------", width / 2, height / 6 + 100);

        highscore = Integer.parseInt(scoreArrayString[0][1]);
        gc.strokeText(scoreArrayString[0][0], width / 4, height / 6 + 150);
        gc.strokeText(scoreArrayString[0][1], width / 4 + width / 4, height / 6 + 150);
        gc.strokeText("#1", width / 4 + width / 4 + width / 4, height / 6 + 150);


        gc.strokeText(scoreArrayString[1][0], width / 4, height / 6 + 200);
        gc.strokeText(scoreArrayString[1][1], width / 4 + width / 4, height / 6 + 200);
        gc.strokeText("#2", width / 4 + width / 4 + width / 4, height / 6 + 200);


        gc.strokeText(scoreArrayString[2][0], width / 4, height / 6 + 250);
        gc.strokeText(scoreArrayString[2][1], width / 4 + width / 4, height / 6 + 250);
        gc.strokeText("#3", width / 4 + width / 4 + width / 4, height / 6 + 250);


        gc.strokeText(scoreArrayString[3][0], width / 4, height / 6 + 300);
        gc.strokeText(scoreArrayString[3][1], width / 4 + width / 4, height / 6 + 300);
        gc.strokeText("#4", width / 4 + width / 4 + width / 4, height / 6 + 300);


        gc.strokeText(scoreArrayString[4][0], width / 4, height / 6 + 350);
        gc.strokeText(scoreArrayString[4][1], width / 4 + width / 4, height / 6 + 350);
        gc.strokeText("#5", width / 4 + width / 4 + width / 4, height / 6 + 350);

    }
}
