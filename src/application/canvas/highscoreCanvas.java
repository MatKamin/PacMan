package application.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.Scanner;

import static application.variables.*;

public class highscoreCanvas {


    public static void play(GraphicsContext gc) throws FileNotFoundException {

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


        //::::::::::: List :::::::::::\\

        String[][] score = new String[6][3];
        score[0][0] = "Tom";
        score[0][1] = "2";
        score[0][2] = "6";
        score[1][0] = "Tom";
        score[1][1] = "2";
        score[1][2] = "6";
        score[2][0] = "Tom";
        score[2][1] = "2";
        score[2][2] = "6";
        score[3][0] = "Tom";
        score[3][1] = "2";
        score[3][2] = "6";
        score[4][0] = "Tom";
        score[4][1] = "2";
        score[4][2] = "6";


        // pass the path to the file as a parameter
        File file = new File("resources/highscores.txt");
        Scanner sc = new Scanner(file);

        sc.useDelimiter(",");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                score[i][j] = sc.next();
            }
        }


        Scanner sc2 = new Scanner(file);
        sc2.useDelimiter("," + score[5][0]);

        // Delete Last entry if not in top 5
        if (Integer.parseInt(score[5][1]) < Integer.parseInt(score[4][1])) {
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

        if (Integer.parseInt(score[5][1]) == Integer.parseInt(score[4][1])) {
            if (Integer.parseInt(score[5][2]) < Integer.parseInt(score[4][2])) {
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
        }

        for (int z = 4; z > -1; z--) {
            if (Integer.parseInt(score[z + 1][1]) > Integer.parseInt(score[z][1])) {
                String[][] zwischen = new String[1][3];
                zwischen[0][0] = score[z][0];
                zwischen[0][1] = score[z][1];
                zwischen[0][2] = score[z][2];

                String[][] zwischen2 = new String[1][3];
                zwischen2[0][0] = score[z + 1][0];
                zwischen2[0][1] = score[z + 1][1];
                zwischen2[0][2] = score[z + 1][2];

                score[z][0] = zwischen2[0][0];
                score[z][1] = zwischen2[0][1];
                score[z][2] = zwischen2[0][2];

                score[z + 1][0] = zwischen[0][0];
                score[z + 1][1] = zwischen[0][1];
                score[z + 1][2] = zwischen[0][2];
            }
            if (Integer.parseInt(score[z + 1][1]) == Integer.parseInt(score[z][1])) {
                if (Integer.parseInt(score[z + 1][2]) > Integer.parseInt(score[z][2])) {
                    String[][] zwischen = new String[1][3];
                    zwischen[0][0] = score[z][0];
                    zwischen[0][1] = score[z][1];
                    zwischen[0][2] = score[z][2];

                    String[][] zwischen2 = new String[1][3];
                    zwischen2[0][0] = score[z + 1][0];
                    zwischen2[0][1] = score[z + 1][1];
                    zwischen2[0][2] = score[z + 1][2];

                    score[z][0] = zwischen2[0][0];
                    score[z][1] = zwischen2[0][1];
                    score[z][2] = zwischen2[0][2];

                    score[z + 1][0] = zwischen[0][0];
                    score[z + 1][1] = zwischen[0][1];
                    score[z + 1][2] = zwischen[0][2];
                }
            }
        }

        try {
            FileWriter writer = new FileWriter("resources/highscores.txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(score[0][0] + "," + score[0][1] + "," + score[0][2] + "," + score[1][0] + "," + score[1][1] + "," + score[1][2] + "," + score[2][0] + "," + score[2][1] + "," + score[2][2] + "," + score[3][0] + "," + score[3][1] + "," + score[3][2] + "," + score[4][0] + "," + score[4][1] + "," + score[4][2] + ",empty,0,0");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        gc.setFont(Font.loadFont("file:resources/fonts/emulogic.ttf", 30));

        gc.strokeText("PLAYER", width / 5, height / 6 + 70);
        gc.strokeText("LEVEL", width / 5 + width / 5, height / 6 + 70);
        gc.strokeText("BOUNCE", width / 5 + width / 5 + width / 5, height / 6 + 70);
        gc.strokeText("RANG", width / 5 + width / 5 + width / 5 + width / 5, height / 6 + 70);
        gc.strokeText("----------------------------------------------", width / 2, height / 6 + 100);


        gc.strokeText(score[0][0], width / 5, height / 6 + 150);
        gc.strokeText(score[0][1], width / 5 + width / 5, height / 6 + 150);
        gc.strokeText(score[0][2], width / 5 + width / 5 + width / 5, height / 6 + 150);
        gc.strokeText("#1", width / 5 + width / 5 + width / 5 + width / 5, height / 6 + 150);

        gc.strokeText(score[1][0], width / 5, height / 6 + 200);
        gc.strokeText(score[1][1], width / 5 + width / 5, height / 6 + 200);
        gc.strokeText(score[1][2], width / 5 + width / 5 + width / 5, height / 6 + 200);
        gc.strokeText("#2", width / 5 + width / 5 + width / 5 + width / 5, height / 6 + 200);

        gc.strokeText(score[2][0], width / 5, height / 6 + 250);
        gc.strokeText(score[2][1], width / 5 + width / 5, height / 6 + 250);
        gc.strokeText(score[2][2], width / 5 + width / 5 + width / 5, height / 6 + 250);
        gc.strokeText("#3", width / 5 + width / 5 + width / 5 + width / 5, height / 6 + 250);

        gc.strokeText(score[3][0], width / 5, height / 6 + 300);
        gc.strokeText(score[3][1], width / 5 + width / 5, height / 6 + 300);
        gc.strokeText(score[3][2], width / 5 + width / 5 + width / 5, height / 6 + 300);
        gc.strokeText("#4", width / 5 + width / 5 + width / 5 + width / 5, height / 6 + 300);

        gc.strokeText(score[4][0], width / 5, height / 6 + 350);
        gc.strokeText(score[4][1], width / 5 + width / 5, height / 6 + 350);
        gc.strokeText(score[4][2], width / 5 + width / 5 + width / 5, height / 6 + 350);
        gc.strokeText("#5", width / 5 + width / 5 + width / 5 + width / 5, height / 6 + 350);


    }
}
