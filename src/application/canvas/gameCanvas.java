package application.canvas;


//---------------------------------IMPORTS---------------------------------\\

import application.chaseMode;
import application.gameMechanics;
import application.scaredMode;
import application.scatterMode;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

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


        if (gameStarted) {

            //::::::::::: Ghosts Movement :::::::::::\\


            switch (scatterCount) {
                case 0, 1 -> scatterTime = 7000;
                case 2, 3 -> scatterTime = 5000;
            }

            if (inScatterMode) {
                scatterMode.ghostAnimate(gameLayout);
                gameMechanics.scatterModeTimer();
            }


            switch (chaseCount) {
                case 0, 1, 2, 3 -> chaseTime = 20000;
                default -> chaseTime = 500000;
            }

            if (inChaseMode) {
                chaseMode.ghostAnimate(gameLayout);
                gameMechanics.chaseModeTimer();
            }


            if (inScaredMode) {
                scaredMode.ghostAnimate(gameLayout);
                gameMechanics.eatGhost(gameLayout);
            }



            //::::::::::: Pac-Man Movement :::::::::::\\

            gameMechanics.pacmanMove(gameLayout);                   // Allows moving

            gameMechanics.collectPoints(gameLayout);                // Allows collecting points
            gameMechanics.collectPowerPill(gameLayout);             // Allows collecting Power Pills

            gameMechanics.spawnFruit(gameLayout);                   // Spawns Fruit
            gameMechanics.collectFruit(gameLayout);                 // Allows collecting Fruit & makes it disappear after some time

            gameMechanics.drawLifesCounter(gameLayout);                    // Draws Life Counter in UI
            gameMechanics.drawLevelCounter(gameLayout);             // Draws Level Counter in UI

            gameMechanics.levelUp(gameLayout);                      // Level Up


        } else {

            // TODO
            gameMechanics.resetGame(gameLayout);

        }
    }
}
