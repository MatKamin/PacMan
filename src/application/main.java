package application;

//---------------------------------IMPORTS---------------------------------\\

import application.canvas.gameCanvas;
import application.canvas.highscoreCanvas;
import application.canvas.menuCanvas;
import application.canvas.settingsCanvas;
import application.functionality.highscoresButtonFunction;
import application.functionality.playButtonFunction;
import application.functionality.settingsButtonFunction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import static application.variables.*;


public class main extends Application {


    /**
     * Launching Game
     * @param currentStage -> current Stage
     */

    @Override
    public void start(Stage currentStage) throws Exception {



        currentStage.setTitle("Pac-Man");      // Window title
        gameStarted = false;

        //----------------------------------------------------------------------------------------WINDOWS----------------------------------------------------------------------------------------\\


        //------------------------------------------------------ GAME WINDOW ------------------------------------------------------\\

        // Canvas
        Canvas canvasGame = new Canvas(width, height);
        GraphicsContext gcGame = canvasGame.getGraphicsContext2D();

        // Layout
        Group gameLayout = new Group();

        // Scene
        Scene gameScene = new Scene(gameLayout, width, height);

        gameCanvas.play(gcGame, gameScene, gameLayout);





        //::::::::::: Red Ghost (Blinky) GIF :::::::::::\\

        //Setting the position of the image
        viewBlinky.setX((blinkyColumn * widthOneBlock) + (int)((widthOneBlock - characterWidth) / 2));
        viewBlinky.setY((blinkyRow * heightOneBlock) + (int)((heightOneBlock - characterHeight) / 2));


        //setting the fit height and width of the image view
        viewBlinky.setFitHeight(characterHeight);
        viewBlinky.setFitWidth(characterWidth);


        //::::::::::: Pink Ghost (Pinky) GIF :::::::::::\\

        //Setting the position of the image
        viewPinky.setX((pinkyColumn * widthOneBlock) + (int)((widthOneBlock - characterWidth) / 2));
        viewPinky.setY((pinkyRow * heightOneBlock) + (int)((heightOneBlock - characterHeight) / 2));


        //setting the fit height and width of the image view
        viewPinky.setFitHeight(characterHeight);
        viewPinky.setFitWidth(characterWidth);


        gameLayout.getChildren().addAll(canvasGame, viewBlinky, viewPinky);


        // Add Dots to Map
        for (int i = 0; i < dotCount; i++) {
            gameLayout.getChildren().remove(viewDot[i]);
            gameLayout.getChildren().add(viewDot[i]);
        }


        // Add Power Pills to Map
        for(int i = 0; i < powerPillCount; i++){
            gameLayout.getChildren().remove(viewPowerPill[i]);
            gameLayout.getChildren().add(viewPowerPill[i]);
        }

        // Add Walls to Map
        for(int i = 0; i < wallCount; i++){
            gameLayout.getChildren().remove(viewWall[i]);
            gameLayout.getChildren().add(viewWall[i]);
        }



        //::::::::::: Timeline :::::::::::\\

        // JavaFX Timeline = Free form animation defined by KeyFrames and their duration
        KeyFrame kf = new KeyFrame(Duration.millis(10), e -> gameCanvas.play(gcGame, gameScene, gameLayout));
        Timeline tl = new Timeline(kf);

        // number of cycles in animation INDEFINITE = repeat indefinitely
        tl.setCycleCount(Timeline.INDEFINITE);



        //------------------------------------------------------ SETTINGS WINDOW ------------------------------------------------------\\

        // TODO: Settings

        // Layout
        Group settingsLayout = new Group();

        // Canvas
        Canvas canvasSettings = new Canvas(width, height);
        GraphicsContext gcSettings = canvasSettings.getGraphicsContext2D();
        settingsCanvas.play(gcSettings);

        // Scene
        Scene settingsScene = new Scene(settingsLayout, width, height);

        // Adds Canvas to Layout
        settingsLayout.getChildren().addAll(canvasSettings);




        //------------------------------------------------------ HIGHSCORE WINDOW ------------------------------------------------------\\

        // TODO: Multiple Players

        // Canvas
        Canvas canvasHighscore = new Canvas(width, height);
        GraphicsContext gcHighscore = canvasHighscore.getGraphicsContext2D();
        highscoreCanvas.play(gcHighscore);

        // Layout
        Group highscoreLayout = new Group();

        // Scene
        Scene highscoreScene = new Scene(highscoreLayout, width, height);

        // Adds Canvas to Layout
        highscoreLayout.getChildren().addAll(canvasHighscore);



        //------------------------------------------------------ MENU WINDOW ------------------------------------------------------\\

        // Canvas
        Canvas canvasMenu = new Canvas(width, height);
        GraphicsContext gcMenu = canvasMenu.getGraphicsContext2D();
        menuCanvas.play(gcMenu);

        // Layout
        Group menuLayout = new Group();

        // Scene
        Scene menuScene = new Scene(menuLayout, width, height);


        //::::::::::: MENU GIF :::::::::::\\

        //Setting the position of the image
        viewMenuAnimation.setX(10);
        viewMenuAnimation.setY((int)(height / 10) * 2);
        viewMenuAnimation.setFitWidth(width);


        //::::::::::: PLAY Button :::::::::::\\

        // Label
        Text playButton = new Text("play");
        playButton.setStroke(fontColor);
        pacmanFontSize = 80;
        playButton.setFont(pacmanFont);

        // Option Label Position
        playButton.setLayoutY(height - (int)(height / 10) * 3);
        playButton.setLayoutX((int)(width / 2) - playButton.getBoundsInParent().getWidth() / 2);



        //::::::::::: SETTINGS Button :::::::::::\\

        // Label
        Text settingsButton = new Text("settings");
        settingsButton.setStroke(fontColor);
        pacmanFontSize = 60;
        settingsButton.setFont(pacmanFont);


        // Option Label Position
        settingsButton.setLayoutY(height - (int)(height / 10) * 2);
        settingsButton.setLayoutX((int)(width / 2) - settingsButton.getBoundsInParent().getWidth() / 2);


        //::::::::::: HIGHSCORES Button :::::::::::\\

        // Label
        Text highscoreButton = new Text("highscores");
        highscoreButton.setStroke(fontColor);
        highscoreButton.setFont(pacmanFont);


        // Option Label Position
        highscoreButton.setLayoutY(height - (int)(height / 10));
        highscoreButton.setLayoutX((int)(width / 2) - highscoreButton.getBoundsInParent().getWidth() / 2 );



        // FUNCTIONALITY
        playButtonFunction.play(playButton, currentStage, gameScene, tl);
        settingsButtonFunction.play(settingsButton, currentStage, settingsScene);
        highscoresButtonFunction.play(highscoreButton, currentStage, highscoreScene);


        // Add to Layout
        menuLayout.getChildren().addAll(canvasMenu, playButton, viewMenuAnimation, settingsButton, highscoreButton);


        // Primary Stage -> Menu
        currentStage.setScene(menuScene);
        currentStage.show();

    }




    public static void main(String[] args){

        // TODO: Music
        // Sets Background Music
        // sounds.playBackgroundMusic();

        launch(args);
    }
}


