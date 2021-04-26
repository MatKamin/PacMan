package application.functionality;

//---------------------------------IMPORTS---------------------------------\\

import application.canvas.gameCanvas;
import application.main;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static application.main.*;


//---------------------------------CLASS---------------------------------\\

public class playButtonFunction {

    /**
     * Play Button Functionality
     *
     * @param playText clickable Text
     */

    public static void play(Text playText, Stage currentStage, Scene gameScene, Timeline tl, GraphicsContext gc, Group gameLayout) {

        playText.setOnMouseClicked(e -> {            // If clicked

            gameCanvas.play(gc, gameLayout);
            gameStarted = true;
            main.setPacmanStartingPos(gameLayout);

            // Primary Stage -> Game Canvas
            currentStage.setScene(gameScene);
            currentStage.show();

            tl.playFromStart();                            // Start Animation

            lifesCounter--;
        });
    }
}
