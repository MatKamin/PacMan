package application;

//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.nio.file.Paths;

import static application.main.clickURI;


//---------------------------------CLASS---------------------------------\\

public class sounds {

    public static boolean sfxSoundsOn = true;
    static MediaPlayer backgroundMusic;


    /**
     * Plays Background Music
     */
    public static void playBackgroundMusic() {
        String s = "resources/sounds/background.mp3";          // Music file location
        String h = Paths.get(s).toUri().toString();            // Convert to URI

        backgroundMusic = new MediaPlayer(new Media(h));            // mediaPlayer -> Selected Music File
        backgroundMusic.setVolume(0.4);                             // change volume
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);      // play in loop

        backgroundMusic.play();     // Start Playing
    }



    /**
     * Plays Click Sound
     */
    public static void playClick() {
        if (sfxSoundsOn) {
            MediaPlayer clickSounds = new MediaPlayer(new Media(clickURI));        // mediaPlayer -> Selected Music File
            clickSounds.setVolume(1);                                              // change volume
            clickSounds.setStartTime(new Duration(20));
            clickSounds.play();
        }
    }
}
