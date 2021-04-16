package application.functionality;

//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;



//---------------------------------CLASS---------------------------------\\

public class highscoresButtonFunction {

    /**
     * Settings Button Functionality
     * @param highscoreText clickable Text
     */

    public static void play(Text highscoreText, Stage currentStage, Scene highscoreScene) {

        highscoreText.setOnMouseClicked(e -> {            // If clicked

            // Primary Stage -> Settings Canvas
            currentStage.setScene(highscoreScene);
            currentStage.show();
        });

    }
}
