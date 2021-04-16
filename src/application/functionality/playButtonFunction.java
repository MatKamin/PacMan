package application.functionality;

//---------------------------------IMPORTS---------------------------------\\

import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static application.variables.ANSI_GREEN;
import static application.variables.*;


//---------------------------------CLASS---------------------------------\\

public class playButtonFunction {

    /**
     * Play Button Functionality
     * @param playText clickable Text
     */

    public static void play(Text playText, Stage currentStage, Scene gameScene, Timeline tl) {

        playText.setOnMouseClicked(e -> {            // If clicked

            gameStarted = true;

            // Primary Stage -> Game Canvas
            currentStage.setScene(gameScene);
            currentStage.show();

            tl.playFromStart();                            // Start Animation

            System.out.println("--- GAME STARTED ---");
            lifesCounter--;
        });
    }
}
