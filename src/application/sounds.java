package application;

//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;

import static application.main.clickURI;


//---------------------------------CLASS---------------------------------\\

public class sounds extends Thread{


    /**
     * Plays Background Music
     */

    static MediaPlayer backgroundMusic;

    public static void playBackgroundMusic() {
        String s = "resources/sounds/beginning.wav";          // Music file location
        String h = Paths.get(s).toUri().toString();     // Convert to URI

        backgroundMusic = new MediaPlayer(new Media(h));            // mediaPlayer -> Selected Music File
        backgroundMusic.setVolume(0.05);                            // change volume
        backgroundMusic.setCycleCount(2);      // play in loop

        backgroundMusic.play();     // Start Playing
    }


    /**
     * Plays Bounce Sound
     */

    public static boolean sfxSoundsOn = true;

    public static void playSiren() {
        if (sfxSoundsOn) {
            String s = "resources/sounds/siren.mp3";                             // Music file location
            String h = Paths.get(s).toUri().toString();                   // Convert to URI

            MediaPlayer sfxSiren = new MediaPlayer(new Media(h));        // mediaPlayer -> Selected Music File
            sfxSiren.setVolume(1);                                       // change volume

            sfxSiren.play();     // Start Playing
        }
    }


    /**
     * Plays Click Sound
     */

    static String s = "resources/sounds/chomp.mp3";                             // Music file location
    static String h = Paths.get(s).toUri().toString();                   // Convert to URI

    public static void playChomp() {
        if (sfxSoundsOn) {
            MediaPlayer sfxChomp = new MediaPlayer(new Media(h));        // mediaPlayer -> Selected Music File
            sfxChomp.setCycleCount(MediaPlayer.INDEFINITE);              // play in loop
            sfxChomp.play();     // Start Playing
        }
    }

    /**
     * Plays Click Sound
     */

    public static void playClick() {
        if (sfxSoundsOn) {
            MediaPlayer clickSounds = new MediaPlayer(new Media(clickURI));        // mediaPlayer -> Selected Music File
            clickSounds.setVolume(1);                                              // change volume
            clickSounds.setStartTime(new Duration(20));
            clickSounds.play();     // Start Playing
        }
    }

    /**
     * Plays Point Sound
     */

    public static void playPoint() {
        if (sfxSoundsOn) {
            String s = "resources/point.mp3";                             // Music file location
            String h = Paths.get(s).toUri().toString();                   // Convert to URI

            MediaPlayer pointSounds = new MediaPlayer(new Media(h));        // mediaPlayer -> Selected Music File
            pointSounds.setVolume(0.2);                                     // change volume

            pointSounds.play();     // Start Playing
        }
    }


    /**
     * Plays Win Sound
     */

    public static void playWin() {
        if (sfxSoundsOn) {
            String s = "resources/win.mp3";                               // Music file location
            String h = Paths.get(s).toUri().toString();                   // Convert to URI

            MediaPlayer winSounds = new MediaPlayer(new Media(h));        // mediaPlayer -> Selected Music File
            winSounds.setVolume(0.2);                                       // change volume

            winSounds.play();     // Start Playing
        }
    }


    /**
     * Plays Lose Sound
     */

    public static void playLose() {
        if (sfxSoundsOn) {
            String s = "resources/lose.mp3";                              // Music file location
            String h = Paths.get(s).toUri().toString();                   // Convert to URI

            MediaPlayer loseSounds = new MediaPlayer(new Media(h));        // mediaPlayer -> Selected Music File
            loseSounds.setVolume(1.5);                                       // change volume

            loseSounds.play();     // Start Playing
        }
    }


    /**
     * Plays Level up Sound
     */

    public static void playLevelUp() {
        if (sfxSoundsOn) {
            String s = "resources/levelup.mp3";                           // Music file location
            String h = Paths.get(s).toUri().toString();                   // Convert to URI

            MediaPlayer levelSounds = new MediaPlayer(new Media(h));        // mediaPlayer -> Selected Music File
            levelSounds.setVolume(1.5);                                       // change volume

            levelSounds.play();     // Start Playing
        }
    }
}
