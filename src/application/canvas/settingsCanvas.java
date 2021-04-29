package application.canvas;

//---------------------------------IMPORTS---------------------------------\\


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

import static application.main.*;


//---------------------------------CLASS---------------------------------\\

public class settingsCanvas {

    //--------------------------------------------MENU CANVAS--------------------------------------------\\

    /**
     * Draws the Settings Window
     * @param gc Graphics Context of the Settings window
     */
    public static void play(GraphicsContext gc) {

        //::::::::::: Background :::::::::::\\

        gc.setFill(backgroundColor);                // Set background color
        gc.fillRect(0, 0, width, height);    // Draw background


        //::::::::::: Text Settings  :::::::::::\\

        gc.setFill(fontColor);          // Set font color
        gc.setStroke(fontColor);        // Set font color for Stroke
        gc.setFont(pacmanFont);                               // Setting the pacman font
        gc.setTextAlign(TextAlignment.CENTER);                // Align text to center


        //::::::::::: Text :::::::::::\\

        gc.fillText("SETTINGS", width / 2, pacmanFontSize * 1.5);
    }
}
