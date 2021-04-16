package application.canvas;


//---------------------------------IMPORTS---------------------------------\\

import application.functionality.pacmanControls;
import application.gameMechanics;
import application.mapReader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

            pacmanControls.controls(gameScene, gameLayout);     // Controls
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
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(90));
            gc.fillText("GAME OVER", (int)(width/2), (int)(height/2));

        }
    }
}
