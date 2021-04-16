package application.canvas;


//---------------------------------IMPORTS---------------------------------\\

import application.gameMechanics;
import application.mapReader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import static application.variables.*;


//---------------------------------CLASS---------------------------------\\

public class gameCanvas {

    //--------------------------------------------GAME CANVAS--------------------------------------------\\

    /**
     * plays the game scene
     * @param gc            GraphicsContext / gcGame of the scene
     * @param gameScene     Game Scene
     * @param gameLayout    Game Layout Group
     */

    public static void play(GraphicsContext gc, Scene gameScene, Group gameLayout) {

        //::::::::::: Background :::::::::::\\

        gc.setFill(backgroundColor);                // Set background color
        gc.fillRect(0, 0, width, height);    // Draw background


        //::::::::::: Text Settings :::::::::::\\


        gc.setFill(fontColor);                       // Set font color
        gc.setFont(pacmanFontUI);                    // Set font
        gc.setStroke(fontColor);                     // Set font color for Stroke
        gc.setTextAlign(TextAlignment.CENTER);       // Align text to center


        //::::::::::: Read Map :::::::::::\\

        mapReader.readMap();



        //::::::::::: High score :::::::::::\\

        gc.setFill(Color.YELLOW);
        gc.fillText("Highscore: " + 500, (int)((widthOneBlock * blockCountHorizontally)/2) + (int)((widthOneBlock * blockCountHorizontally)/6), heightOneBlock * 2);



        //::::::::::: Score :::::::::::\\

        gc.fillText("Score: " + score, (int)((widthOneBlock * blockCountHorizontally)/6), heightOneBlock * 2);






        if (gameStarted) {               // When the round starts

            //::::::::::: Ghosts Movement :::::::::::\\

            gameMechanics.ghostMove();
            gameMechanics.wallGhost();

            //::::::::::: Pac-Man Movement :::::::::::\\


            gameMechanics.pacmanMove(gameLayout);                   // Allows moving

            gameMechanics.collectPoints(gameLayout);                // Allows collecting points
            gameMechanics.collectPowerPill(gameLayout);             // Allows collecting Power Pills

            gameMechanics.gameOver();                               // TODO: Level UP
            gameMechanics.spawnFruit(gameLayout);                   // Spawns Fruit
            gameMechanics.collectFruit(gameLayout);                 // Allows collecting Fruit & makes it disappear after some time

            gameMechanics.drawLifes(gameLayout);                    // Draws Life Counter in UI
            gameMechanics.drawLevelCounter(gameLayout);             // Draws Level Counter in UI
            

        } else {    // If Round is Over

            // TODO

            firstRead = true;

            score = 0;
            lifesCounter = 3;
            lifesOriginally = 3;

            levelCounter = 0;
            startingLevel = 1;

            wallCount = 0;
            dotCount = 0;
            dotCountAtStart = 0;
            powerPillCount = 0;
            railVerticalCount = 0;
            railHorizontalCount = 0;
            railUpRightCount = 0;
            railUpLeftCount = 0;
            railRightUpCount = 0;
            railLeftUpCount = 0;

            pacmanXPos = pacmanXPosStarting;
            pacmanYPos = pacmanYPosStarting;

            velocityPacmanHorizontal = 0;
            velocityPacmanVertical = 0;

            allowNextMoveDown = false;
            allowNextMoveUp = false;
            allowNextMoveRight = true;
            allowNextMoveLeft = true;

            mapReader.readMap();
        }
    }
}
