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
import javafx.scene.input.KeyCode;
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

        // Add Vertical Rails to Map
        for(int i = 0; i < railVerticalCount; i++){
            gameLayout.getChildren().remove(viewRailVertical[i]);
            gameLayout.getChildren().add(viewRailVertical[i]);
        }

        // Add Horizontal Rails to Map
        for(int i = 0; i < railHorizontalCount; i++){
            gameLayout.getChildren().remove(viewRailHorizontal[i]);
            gameLayout.getChildren().add(viewRailHorizontal[i]);
        }

        // Add Up Right Rails to Map
        for(int i = 0; i < railUpRightCount; i++){
            gameLayout.getChildren().remove(viewRailUpRight[i]);
            gameLayout.getChildren().add(viewRailUpRight[i]);
        }

        // Add Up Left Rails to Map
        for(int i = 0; i < railUpLeftCount; i++){
            gameLayout.getChildren().remove(viewRailUpLeft[i]);
            gameLayout.getChildren().add(viewRailUpLeft[i]);
        }

        // Add Right Up Rails to Map
        for(int i = 0; i < railRightUpCount; i++){
            gameLayout.getChildren().remove(viewRailRightUp[i]);
            gameLayout.getChildren().add(viewRailRightUp[i]);
        }

        // Add Left Up Rails to Map
        for(int i = 0; i < railLeftUpCount; i++){
            gameLayout.getChildren().remove(viewRailLeftUp[i]);
            gameLayout.getChildren().add(viewRailLeftUp[i]);
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


        //----------------------------------------------------------------------------------------CONTROLS----------------------------------------------------------------------------------------\\


        //--------------------------------------------SETTINGS CONTROLS--------------------------------------------\\

        settingsScene.setOnKeyPressed(e -> {

            //::::::::::: ESCAPE :::::::::::\\

            if (e.getCode() == KeyCode.ESCAPE) {      // If "Escape" Pressed

                try {
                    start(currentStage);            // Restart with new settings
                    gameStarted = false;

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });


        //--------------------------------------------HIGHSCORE CONTROLS--------------------------------------------\\

        highscoreScene.setOnKeyPressed(e -> {

            //::::::::::: ESCAPE :::::::::::\\

            if (e.getCode() == KeyCode.ESCAPE) {      // If "Escape" Pressed

                currentStage.setScene(menuScene);   // Go to Menu
            }
        });



        //--------------------------------------------GAME CONTROLS--------------------------------------------\\

        controls(gameScene, gameLayout, tl, gcGame, currentStage, menuScene);     // Controls

    }






    public void controls(Scene gameScene, Group gameLayout, Timeline tl, GraphicsContext gc, Stage primaryStage, Scene menuScene) {

        if(startingStatus){
            //::::::::::: Pac-Man GIF :::::::::::\\

            //Setting the position of the image
            viewPacmanLeft.setX((pacmanXPos));
            viewPacmanLeft.setY((pacmanYPos));

            //setting the fit height and width of the image view
            viewPacmanLeft.setFitHeight(characterHeight);
            viewPacmanLeft.setFitWidth(characterWidth);

            gameLayout.getChildren().remove(viewPacmanUp);
            gameLayout.getChildren().remove(viewPacmanRight);
            gameLayout.getChildren().remove(viewPacmanLeft);
            gameLayout.getChildren().remove(viewPacmanDown);

            gameLayout.getChildren().addAll(viewPacmanLeft);

            startingStatus = false;
            allowNextMoveRight = true;
            allowNextMoveLeft = true;
        }

        gameScene.setOnKeyPressed(e -> {

            //::::::::::: "UP" KEY & "W" KEY :::::::::::\\

            if ((e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP)) {
                waitingForTurn = 'u';

                if (allowNextMoveUp) {

                    //::::::::::: Pac-Man GIF :::::::::::\\

                    //Setting the position of the image
                    viewPacmanUp.setX((pacmanXPos));
                    viewPacmanUp.setY((pacmanYPos));

                    //setting the fit height and width of the image view
                    viewPacmanUp.setFitHeight(characterHeight);
                    viewPacmanUp.setFitWidth(characterWidth);

                    gameLayout.getChildren().remove(viewPacmanRight);
                    gameLayout.getChildren().remove(viewPacmanUp);
                    gameLayout.getChildren().remove(viewPacmanLeft);
                    gameLayout.getChildren().remove(viewPacmanDown);

                    gameLayout.getChildren().add(viewPacmanUp);

                    pacmanFacingUp = true;
                    pacmanFacingDown = false;
                    pacmanFacingLeft = false;
                    pacmanFacingRight = false;
                    velocityPacmanHorizontal = 0;
                    velocityPacmanVertical = -1;

                    hitUpWall = false;
                }
            }





            //::::::::::: "RIGHT" KEY & "D" KEY :::::::::::\\

            if ((e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT)) {
                waitingForTurn = 'r';

                if (allowNextMoveRight) {
                    //::::::::::: Pac-Man GIF :::::::::::\\

                    //Setting the position of the image
                    viewPacmanRight.setX((pacmanXPos));
                    viewPacmanRight.setY((pacmanYPos));

                    //setting the fit height and width of the image view
                    viewPacmanRight.setFitHeight(characterHeight);
                    viewPacmanRight.setFitWidth(characterWidth);

                    gameLayout.getChildren().remove(viewPacmanUp);
                    gameLayout.getChildren().remove(viewPacmanRight);
                    gameLayout.getChildren().remove(viewPacmanLeft);
                    gameLayout.getChildren().remove(viewPacmanDown);

                    gameLayout.getChildren().addAll(viewPacmanRight);

                    pacmanFacingRight = true;
                    pacmanFacingDown = false;
                    pacmanFacingLeft = false;
                    pacmanFacingUp = false;
                    velocityPacmanHorizontal = 1;
                    velocityPacmanVertical = 0;

                    hitRightWall = false;
                }
            }





            //::::::::::: "LEFT" KEY & "A" KEY :::::::::::\\

            if ((e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT)) {
                waitingForTurn = 'l';

                if (allowNextMoveLeft){


                    //::::::::::: Pac-Man GIF :::::::::::\\

                    //Setting the position of the image
                    viewPacmanLeft.setX((pacmanXPos));
                    viewPacmanLeft.setY((pacmanYPos));

                    //setting the fit height and width of the image view
                    viewPacmanLeft.setFitHeight(characterHeight);
                    viewPacmanLeft.setFitWidth(characterWidth);

                    gameLayout.getChildren().remove(viewPacmanUp);
                    gameLayout.getChildren().remove(viewPacmanRight);
                    gameLayout.getChildren().remove(viewPacmanLeft);
                    gameLayout.getChildren().remove(viewPacmanDown);

                    gameLayout.getChildren().addAll(viewPacmanLeft);


                    pacmanFacingLeft = true;
                    pacmanFacingDown = false;
                    pacmanFacingUp = false;
                    pacmanFacingRight = false;
                    velocityPacmanHorizontal = -1;
                    velocityPacmanVertical = 0;

                    hitLeftWall = false;
                }
            }




            //::::::::::: "DOWN" KEY & "S" KEY :::::::::::\\

            if ((e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN)) {
                waitingForTurn = 'd';

                if (allowNextMoveDown){

                    //::::::::::: Pac-Man GIF :::::::::::\\

                    //Setting the position of the image
                    viewPacmanDown.setX(pacmanXPos);
                    viewPacmanDown.setY(pacmanYPos);



                    //setting the fit height and width of the image view
                    viewPacmanDown.setFitHeight(characterHeight);
                    viewPacmanDown.setFitWidth(characterWidth);

                    gameLayout.getChildren().remove(viewPacmanUp);
                    gameLayout.getChildren().remove(viewPacmanRight);
                    gameLayout.getChildren().remove(viewPacmanLeft);
                    gameLayout.getChildren().remove(viewPacmanDown);

                    gameLayout.getChildren().addAll(viewPacmanDown);

                    pacmanFacingDown = true;
                    pacmanFacingUp = false;
                    pacmanFacingLeft = false;
                    pacmanFacingRight = false;
                    velocityPacmanHorizontal = 0;
                    velocityPacmanVertical = 1;

                    hitDownWall = false;
                }
            }



            //::::::::::: DEBUG :::::::::::\\

            if (e.getCode() == KeyCode.SPACE) {
                debug = true;
            }








            // Pause Game when "P" Pressed
            if (e.getCode() == KeyCode.P) {   // If "P" Pressed

                startingStatus = false;
                tl.stop();    // Stop Timeline/Animation


                //::::::::::: Pause Menu :::::::::::\\

                gc.fillText("PAUSED",(blockCountHorizontally / 2) * widthOneBlock , blockCountVertically * heightOneBlock);
                gc.fillText("Press P to resume", (blockCountHorizontally / 2) * widthOneBlock, ( blockCountVertically + 1 ) * heightOneBlock);
                gc.fillText("Press Esc to leave", (blockCountHorizontally / 2) * widthOneBlock, ( blockCountVertically  + 2 ) * heightOneBlock);


                gameScene.setOnKeyPressed(el -> {

                    // Continue Game when P Pressed
                    if (el.getCode() == KeyCode.P) {      // If "P" pressed again

                        tl.play();      // Continue Timeline/Animation

                        controls(gameScene, gameLayout, tl, gc, primaryStage, menuScene);      // Recursion -> Check if pressed again
                    }

                    // Leave Game on Esc with Pause
                    if (el.getCode() == KeyCode.ESCAPE) {         // If "Escape" pressed

                        try {
                            startingStatus = true;
                            gameStarted = false;
                            start(primaryStage);            // Restart with new settings

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        tl.jumpTo(Duration.millis(0));          // Restart Animation
                        tl.stop();

                    }
                });
            }


            // Leave Game on Esc without Pause
            if (e.getCode() == KeyCode.ESCAPE) {          // If "Escape" pressed

                try {
                    startingStatus = true;
                    gameStarted = false;
                    start(primaryStage);                 // Restart with new settings



                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                tl.jumpTo(Duration.millis(0));          // Restart Animation
                tl.stop();

            }

        });
    }


    public static void main(String[] args){

        // TODO: Music
        // Sets Background Music
        // sounds.playBackgroundMusic();

        System.out.println(ANSI_GREEN + "--- Application Started ---");
        launch(args);
        System.out.println(ANSI_RESET);
    }
}


