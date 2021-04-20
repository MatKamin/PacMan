package application.canvas;

//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import static application.variables.*;


//---------------------------------CLASS---------------------------------\\

public class menuCanvas {

    //--------------------------------------------MENU CANVAS--------------------------------------------\\

    /**
     * Canvas Menu
     * @param gc graphics context for Menu
     */

    public static void play(GraphicsContext gc) {

        //::::::::::: Background :::::::::::\\

        gc.setFill(backgroundColor);                // Set background color
        gc.fillRect(0, 0, width, height);    // Draw background


        //::::::::::: Text Settings :::::::::::\\

        gc.setFill(fontColor);          // Set font color
        gc.setStroke(fontColor);        // Set font color for Stroke
        gc.setFont(pacmanFont);                               // Setting the pacman font
        gc.setTextAlign(TextAlignment.CENTER);                // Align text to center


        //::::::::::: Text :::::::::::\\

        gc.fillText("Pac-Man", width/2 , pacmanFontSize * 1.5);


        gc.setFill(Color.YELLOW);
        gc.setFont(pacmanFontUI);
        gc.fillText("Welcome " + validUsername + "!!! ", width/2, pacmanFontSize * 3);

    }
}
