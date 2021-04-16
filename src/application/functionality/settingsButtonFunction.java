package application.functionality;

//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static application.variables.gameStarted;


//---------------------------------CLASS---------------------------------\\

public class settingsButtonFunction {

    /**
     * Settings Button Functionality
     * @param settingsText clickable Text
     */

    public static void play(Text settingsText, Stage currentStage, Scene settingsScene) {

        settingsText.setOnMouseClicked(e -> {            // If clicked

            // Primary Stage -> Settings Canvas
            currentStage.setScene(settingsScene);
            currentStage.show();
        });

    }
}
