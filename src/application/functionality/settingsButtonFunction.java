package application.functionality;

//---------------------------------IMPORTS---------------------------------\\

import application.canvas.settingsCanvas;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.stage.Stage;


//---------------------------------CLASS---------------------------------\\

public class settingsButtonFunction {

    /**
     * Settings Button Functionality
     *
     * @param settingsText clickable Text
     */

    public static void play(Text settingsText, Stage currentStage, Scene settingsScene, GraphicsContext gc) {

        settingsText.setOnMouseClicked(e -> {            // If clicked

            settingsCanvas.play(gc);

            // Primary Stage -> Settings Canvas
            currentStage.setScene(settingsScene);
            currentStage.show();
        });

    }
}
