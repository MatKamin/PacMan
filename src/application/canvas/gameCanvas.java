package application.canvas;


//---------------------------------IMPORTS---------------------------------\\

import application.gameMechanics;
import application.ghostAI;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.Scanner;

import static application.variables.*;


//---------------------------------CLASS---------------------------------\\

public class gameCanvas {

    //--------------------------------------------GAME CANVAS--------------------------------------------\\

    /**
     * plays the game scene
     * @param gc            GraphicsContext / gcGame of the scene
     * @param gameLayout    Game Layout Group
     */

    public static void play(GraphicsContext gc, Group gameLayout) {

        //::::::::::: Background :::::::::::\\

        gc.setFill(backgroundColor);                // Set background color
        gc.fillRect(0, 0, width, height);    // Draw background


        //::::::::::: Text Settings :::::::::::\\


        gc.setFill(fontColor);                       // Set font color
        gc.setFont(pacmanFontUI);                    // Set font
        gc.setStroke(fontColor);                     // Set font color for Stroke
        gc.setTextAlign(TextAlignment.CENTER);       // Align text to center



        //::::::::::: High score :::::::::::\\

        gc.setFill(Color.YELLOW);
        if (score > highscore){
            highscore = score;
        }
        gc.fillText("Highscore: " + highscore, (int)((widthOneBlock * blockCountHorizontally)/2) + (int)((widthOneBlock * blockCountHorizontally)/6), heightOneBlock * 2);


        //::::::::::: Nickname :::::::::::\\

        gc.fillText(validUsername, (int)(widthOneBlock * (blockCountHorizontally + 6)), heightOneBlock * 2);


        //::::::::::: Score :::::::::::\\

        gc.fillText("Score: " + score, (int)((widthOneBlock * blockCountHorizontally)/6), heightOneBlock * 2);






        if (gameStarted) {               // When the round starts

            //::::::::::: Ghosts Movement :::::::::::\\

            ghostAI.ghostMove();
            ghostAI.wallGhost();

            //::::::::::: Pac-Man Movement :::::::::::\\


            gameMechanics.pacmanMove(gameLayout);                   // Allows moving

            gameMechanics.collectPoints(gameLayout);                // Allows collecting points
            gameMechanics.collectPowerPill(gameLayout);             // Allows collecting Power Pills

            gameMechanics.spawnFruit(gameLayout);                   // Spawns Fruit
            gameMechanics.collectFruit(gameLayout);                 // Allows collecting Fruit & makes it disappear after some time

            gameMechanics.drawLifes(gameLayout);                    // Draws Life Counter in UI
            gameMechanics.drawLevelCounter(gameLayout);             // Draws Level Counter in UI

            gameMechanics.levelUp(gameLayout);                      // Level Up


        } else {    // If Round is Over

            // TODO


            // Save Score
            Scanner sc = null;
            try {
                sc = new Scanner(new File("resources/highscores.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            assert sc != null;
            sc.useDelimiter(",empty,0");
            String paste = sc.next();
            try {
                FileWriter writer2 = new FileWriter("resources/highscores.txt", false);
                BufferedWriter bufferedWriter = new BufferedWriter(writer2);
                bufferedWriter.write(paste);
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileWriter writer = new FileWriter("resources/highscores.txt", true);

                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write("," + validUsername + "," + score);
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            gameMechanics.resetGame(gameLayout);


        }
    }
}
