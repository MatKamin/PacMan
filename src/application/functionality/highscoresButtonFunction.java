package application.functionality;

//---------------------------------IMPORTS---------------------------------\\

import application.canvas.highscoreCanvas;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;


//---------------------------------CLASS---------------------------------\\

public class highscoresButtonFunction {

    /**
     * Settings Button Functionality
     *
     * @param highscoreText clickable Text
     */

    public static void play(Text highscoreText, Stage currentStage, Scene highscoreScene, GraphicsContext gc) {

        highscoreText.setOnMouseClicked(e -> {            // If clicked
            try {
                highscoreCanvas.play(gc);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

            // Primary Stage -> Settings Canvas
            currentStage.setScene(highscoreScene);
            currentStage.show();
        });

    }
}
