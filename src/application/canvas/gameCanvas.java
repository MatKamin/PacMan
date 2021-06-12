package application.canvas;


//---------------------------------IMPORTS---------------------------------\\

import application.Client;
import application.Server;
import application.ai.chaseMode;
import application.ai.scaredMode;
import application.ai.scatterMode;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static application.Server.checkScore;
import static application.Server.clientsScoreMap;
import static application.gameMechanics.*;
import static application.main.*;
import static application.mapReader.blockCountHorizontally;


//---------------------------------CLASS---------------------------------\\

public class gameCanvas {




    //--------------------------------------------GAME CANVAS--------------------------------------------\\

    /**
     * draws game Canvas
     * @param gc Graphics Context of the Game window
     * @param gameLayout Group Layout of the Game Window
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
        if (score > highscore) {
            highscore = score;
        }
        gc.fillText("Highscore: " + highscore, (int) ((widthOneBlock * blockCountHorizontally) / 2) + (int) ((widthOneBlock * blockCountHorizontally) / 6), heightOneBlock * 2);


        //::::::::::: Nickname :::::::::::\\

        gc.fillText(validUsername, (int) (widthOneBlock * (blockCountHorizontally + 6)), heightOneBlock * 2);


        //::::::::::: Score :::::::::::\\

        gc.fillText("Score: " + score, (int) ((widthOneBlock * blockCountHorizontally) / 6), heightOneBlock * 2);


        //::::::::::: Speed UP/DOWN :::::::::::\\
        gc.setFill(Color.BLUE);
        if (inPinkMode) gc.setFill(Color.HOTPINK);
        gc.fillRoundRect((double) width-370, (double)height-130, 110, 40, 30, 30);
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.loadFont("file:resources/fonts/emulogic.ttf", 15));
        DecimalFormat df = new DecimalFormat("0.00");
        gc.fillText("Speed Controls (" + df.format(velocityAdder) + ")", width - 325, height - 150);


        final double[] heightStart = {height - 850};
        final int[] rank = {1};
        final int[] shownClients = {0};

        gc.setFont(Font.loadFont("file:resources/fonts/emulogic.ttf", 20));
        //Map<String, String> sortedMapDesc = sortByComparator(clientScores);


        sortedMap.forEach((key, value) -> {
            if (shownClients[0] < 10 && value != 0) {
                gc.fillText("#" + rank[0] + ".", width - 455, heightStart[0]);
                gc.fillText(key, width - 355, heightStart[0]);
                gc.fillText(" | ", width - 285, heightStart[0]);
                gc.fillText(value + "", width - 225, heightStart[0]);
                heightStart[0] += 40;
                rank[0]++;
                shownClients[0]++;
            }
        });


        if (gameStarted) {

            //::::::::::: Ghosts :::::::::::\\

            switch (levelCounter) {
                case 0, 1 -> {
                    switch (scatterCount) {
                        case 0, 1 -> scatterTime = 7000;
                        case 2, 3 -> scatterTime = 5000;
                    }
                }
                case 2, 3, 4 -> {
                    switch (scatterCount) {
                        case 0, 1 -> scatterTime = 7000;
                        case 2 -> scatterTime = 5000;
                        case 3 -> scatterTime = 17;
                    }
                }
                default -> {
                    switch (scatterCount) {
                        case 0, 1, 2 -> scatterTime = 5000;
                        case 3 -> scatterTime = 17;
                    }
                }
            }


            if (inScatterMode) {
                if (!inScaredModePinky) {
                    scatterMode.ghostAnimate(gameLayout, "pinky");
                }
                if (!inScaredModeBlinky) {
                    scatterMode.ghostAnimate(gameLayout, "blinky");
                }
                if (!inScaredModeClyde) {
                    scatterMode.ghostAnimate(gameLayout, "clyde");
                }
            }


            switch (levelCounter) {
                case 0, 1 -> {
                    switch (chaseCount) {
                        case 0, 1, 2, 3 -> chaseTime = 20000;
                        default -> chaseTime = 50;
                    }
                }
                case 2, 3, 4 -> {
                    switch (chaseCount) {
                        case 0, 1, 2 -> chaseTime = 20000;
                        case 3 -> chaseTime = 1033000;
                        default -> chaseTime = 50;
                    }
                }
                default -> {
                    switch (chaseCount) {
                        case 0, 1, 2 -> chaseTime = 20000;
                        case 3 -> chaseTime = 1037000;
                        default -> chaseTime = 50;
                    }
                }
            }



            if (inChaseMode) {
                chaseMode.ghostAnimate(gameLayout, "blinky");
                chaseMode.ghostAnimate(gameLayout, "pinky");
                chaseMode.ghostAnimate(gameLayout, "clyde");
            }


            if (inScaredModeBlinky) {
                scaredMode.ghostAnimate(gameLayout, "blinky");
            }
            if (inScaredModePinky) {
                scaredMode.ghostAnimate(gameLayout, "pinky");
            }
            if (inScaredModeClyde) {
                scaredMode.ghostAnimate(gameLayout, "clyde");
            }



            //::::::::::: Pac-Man :::::::::::\\


            pacmanMove(gameLayout);                   // Allows moving

            collectPoints(gameLayout);                // Allows collecting points
            collectPowerPill(gameLayout);             // Allows collecting Power Pills

            spawnFruit(gameLayout);                   // Spawns Fruit
            collectFruit(gameLayout);                 // Allows collecting Fruit & makes it disappear after some time
            levelUp(gameLayout);                      // Level Up
            drawLifesCounter(gameLayout);             // Draws Life Counter in UI
            drawLevelCounter(gameLayout);             // Draws Level Counter in UI

            pacmanDeath(gameLayout, gc);                  // Allows Losing


        } else {

            resetGame(gameLayout);

        }
    }
}
