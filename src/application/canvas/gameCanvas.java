package application.canvas;


//---------------------------------IMPORTS---------------------------------\\

import application.ai.chaseMode;
import application.ai.scaredMode;
import application.ai.scatterMode;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.text.DecimalFormat;

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



        if (gameStarted) {

            //::::::::::: Ghosts :::::::::::\\


            switch (scatterCount) {
                case 0, 1 -> scatterTime = 7000;
                case 2, 3 -> scatterTime = 5000;
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


            switch (chaseCount) {
                case 0, 1, 2, 3 -> chaseTime = 20000;
                default -> chaseTime = 50;
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
