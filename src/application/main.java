package application;

//---------------------------------IMPORTS---------------------------------\\

import application.canvas.gameCanvas;
import application.canvas.highscoreCanvas;
import application.canvas.menuCanvas;
import application.canvas.settingsCanvas;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;

import static application.gameMechanics.*;
import static application.imageViewerVariables.*;
import static application.mapReader.blockCountHorizontally;
import static application.mapReader.blockCountVertically;


public class main extends Application {

    //---------------------------------VARIABLES---------------------------------\\


    public static final int width = 1300;       // Window width
    public static final int height = 1000;      // Window height

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private static boolean isLoggedIn = false;

    public static boolean isPacmanStartingPosVisible = true;

    public static boolean gameStarted;    // has the game started? true/false

    public static Color backgroundColor = Color.BLACK;    // Background Color
    public static Color fontColor = Color.WHITE;          // Font Color

    public static String validUsername = "Player 1";
    static final String regexp = "\\w{1,10}" + "\\s?+" + "\\d{0,3}";

    public static int lifesCounter = 3;
    static int lifesAtLevelStart = 3;

    public static final long widthOneBlock = 25;
    public static final long heightOneBlock = 25;

    public static final long characterWidth = widthOneBlock;
    public static final long characterHeight = heightOneBlock;

    public static int pacmanFontSize = 80;
    public static int pacmanFontSizeUI = 20;
    public static final Font pacmanFont = Font.loadFont("file:resources/fonts/pacman.ttf", pacmanFontSize);                  // Loading Pac-Man Font
    public static final Font pacmanFontUI = Font.loadFont("file:resources/fonts/emulogic.ttf", pacmanFontSizeUI);            // Loading Pac-Man UI Font

    static boolean pacmanFacingUp = false;
    static boolean pacmanFacingDown = false;
    static boolean pacmanFacingLeft = false;
    static boolean pacmanFacingRight = true;

    static char waitingForTurn;

    static double velocityPacmanHorizontal = 0;
    static double velocityPacmanVertical = 0;

    static boolean hitRightWall = false;
    static boolean hitLeftWall = false;
    static boolean hitUpWall = false;
    static boolean hitDownWall = false;


    private static GraphicsContext gcSettings;
    private static Scene settingsScene;
    private static Text logoffButton;
    private static Text deleteAccountButton;


    private void createSettingsWindow(){
        // TODO: Settings

        // Canvas
        Canvas canvasSettings = new Canvas(width, height);
        gcSettings = canvasSettings.getGraphicsContext2D();

        // Layout
        Group settingsLayout = new Group();

        // Scene
        settingsScene = new Scene(settingsLayout, width, height);

        // Adds Canvas to Layout
        settingsLayout.getChildren().addAll(canvasSettings, logoffButton, deleteAccountButton);
    }

    private void createLogoffButton(){
        // Label
        logoffButton = new Text("Log out");
        logoffButton.setStroke(Color.YELLOW);
        logoffButton.setFont(pacmanFontUI);

        // Option Label Position
        logoffButton.setLayoutY(height - 50);
        logoffButton.setLayoutX(50);
    }


    private void createDeleteAccountButton(){
        // Label
        deleteAccountButton = new Text("Delete account");
        deleteAccountButton.setStroke(Color.YELLOW);
        deleteAccountButton.setFont(pacmanFontUI);

        // Option Label Position
        deleteAccountButton.setLayoutY(height - 50);
        deleteAccountButton.setLayoutX(width - 300); // TODO: Improve "- 300"
    }


    private static GraphicsContext gcGame;
    private static Group gameLayout;
    private static Scene gameScene;
    public static Timeline tl;

    private void createGameWindow(){
        // Canvas
        Canvas canvasGame = new Canvas(width, height);
        gcGame = canvasGame.getGraphicsContext2D();

        // Layout
        gameLayout = new Group();

        // Scene
        gameScene = new Scene(gameLayout, width, height);
        gameLayout.getChildren().add(canvasGame);
    }

    private void createTimeline(){
        // JavaFX Timeline = Free form animation defined by KeyFrames and their duration
        KeyFrame kf = new KeyFrame(Duration.millis(10), e -> gameCanvas.play(gcGame, gameLayout));
        tl = new Timeline(kf);

        // number of cycles in animation INDEFINITE = repeat indefinitely
        tl.setCycleCount(Timeline.INDEFINITE);
    }

    private static GraphicsContext gcHighscore;
    private static Scene highscoreScene;

    private void createHighscoreWindow(){
        // Canvas
        Canvas canvasHighscore = new Canvas(width, height);
        gcHighscore = canvasHighscore.getGraphicsContext2D();

        // Layout
        Group highscoreLayout = new Group();

        // Scene
        highscoreScene = new Scene(highscoreLayout, width, height);

        // Adds Canvas to Layout
        highscoreLayout.getChildren().addAll(canvasHighscore);
    }

    private static GraphicsContext gcMenu;
    private static Scene menuScene;
    private static Text playButton;
    private static Text settingsButton;
    private static Text highscoreButton;

    private void createMenuWindow(){
        // Canvas
        Canvas canvasMenu = new Canvas(width, height);
        gcMenu = canvasMenu.getGraphicsContext2D();

        // Layout
        Group menuLayout = new Group();

        // Scene
        menuScene = new Scene(menuLayout, width, height);

        // Add to Layout
        menuLayout.getChildren().addAll(canvasMenu, playButton, viewMenuAnimation, settingsButton, highscoreButton);
    }

    private void createMenuAnimation(){
        //Setting the position of the image
        viewMenuAnimation.setX(10);
        viewMenuAnimation.setY((height / 10) * 3);
        viewMenuAnimation.setFitWidth(width);
        viewMenuAnimation.setFitHeight(150);
    }

    private void createPlayButton(Stage currentStage){
        // Label
        playButton = new Text("play");
        playButton.setStroke(fontColor);
        pacmanFontSize = 80;
        playButton.setFont(pacmanFont);

        // Option Label Position
        playButton.setLayoutY(height - (height / 10) * 3);
        playButton.setLayoutX((width / 2) - playButton.getBoundsInParent().getWidth() / 2);


        playButton.setOnMouseClicked(e -> {            // If clicked
            gameCanvas.play(gcGame, gameLayout);
            gameStarted = true;
            setPacmanStartingPos(gameLayout);

            // Primary Stage -> Game Canvas
            currentStage.setScene(gameScene);
            currentStage.show();

            tl.playFromStart();                            // Start Animation

            lifesCounter--;
        });
    }

    private void createSettingsButton(Stage currentStage){
        // Label
        settingsButton = new Text("settings");
        settingsButton.setStroke(fontColor);
        pacmanFontSize = 60;
        settingsButton.setFont(pacmanFont);

        // Option Label Position
        settingsButton.setLayoutY(height - (height / 10) * 2);
        settingsButton.setLayoutX((width / 2) - settingsButton.getBoundsInParent().getWidth() / 2);

        settingsButton.setOnMouseClicked(e -> {            // If clicked

            settingsCanvas.play(gcSettings);

            // Primary Stage -> Settings Canvas
            currentStage.setScene(settingsScene);
            currentStage.show();
        });
    }

    private void createHighscoreButton(Stage currentStage){
        // Label
        highscoreButton = new Text("highscores");
        highscoreButton.setStroke(fontColor);
        highscoreButton.setFont(pacmanFont);

        // Option Label Position
        highscoreButton.setLayoutY(height - (height / 10));
        highscoreButton.setLayoutX((width / 2) - highscoreButton.getBoundsInParent().getWidth() / 2);

        highscoreButton.setOnMouseClicked(e -> {            // If clicked
            try {
                highscoreCanvas.play(gcHighscore);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

            // Primary Stage -> Settings Canvas
            currentStage.setScene(highscoreScene);
            currentStage.show();
        });
    }


    private static volatile boolean javaFxLaunched = false;

    public static void myLaunch(Class<? extends Application> applicationClass) {
        if (!javaFxLaunched) { // First time
            Platform.setImplicitExit(false);
            new Thread(()->Application.launch(applicationClass)).start();
            javaFxLaunched = true;
        } else { // Next times
            new JFXPanel();
            Platform.runLater(()->{
                try {
                    Application application = applicationClass.newInstance();
                    Stage primaryStage = new Stage();
                    application.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


    /**
     * Launching Game
     *
     * @param currentStage -> current Stage
     */

    @Override
    public void start(Stage currentStage) {


        currentStage.setTitle("Pac-Man");      // Window title
        gameStarted = false;


        //----------------------------------------------------------------------------------------WINDOWS----------------------------------------------------------------------------------------\\


        //------------------------------------------------------ GAME WINDOW ------------------------------------------------------\\

        createGameWindow();
        createTimeline();


        //------------------------------------------------------ SETTINGS WINDOW ------------------------------------------------------\\

        createLogoffButton();
        createDeleteAccountButton();
        createSettingsWindow();

        //------------------------------------------------------ HIGHSCORE WINDOW ------------------------------------------------------\\

        createHighscoreWindow();


        //------------------------------------------------------ MENU WINDOW ------------------------------------------------------\\

        createMenuAnimation();
        createPlayButton(currentStage);
        createSettingsButton(currentStage);
        createHighscoreButton(currentStage);
        createMenuWindow();



        //------------------------------------------------------ REGISTRATION FORM ------------------------------------------------------\\

        GridPane registrationLayout = createRegistrationFormPane();
        Scene registrationScene = new Scene(registrationLayout, width, height);

        //------------------------------------------------------ LOGIN FORM ------------------------------------------------------\\

        GridPane loginLayout = createLoginFormPane();

        Scene loginScene = new Scene(loginLayout, width, height);

        addUIControls(registrationLayout, currentStage, menuScene, loginScene, gcMenu);

        addUIControlsLogin(loginLayout, currentStage, menuScene, registrationScene, gcMenu);

        menuCanvas.play(gcMenu);
        currentStage.setScene(menuScene);

        if (!isLoggedIn) currentStage.setScene(loginScene);

        currentStage.show();

        logoff(logoffButton, currentStage);
        deleteAccount(deleteAccountButton, currentStage);


        //----------------------------------------------------------------------------------------CONTROLS----------------------------------------------------------------------------------------\\

        //--------------------------------------------SETTINGS CONTROLS--------------------------------------------\\

        settingsScene.setOnKeyPressed(e -> {
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
            if (e.getCode() == KeyCode.ESCAPE) {      // If "Escape" Pressed
                currentStage.setScene(menuScene);   // Go to Menu
            }
        });

        //--------------------------------------------GAME CONTROLS--------------------------------------------\\

        controls(currentStage);     // Controls


        long endTime = System.currentTimeMillis();
        System.out.println("Exec Time: " + (endTime - startingTime) + "ms");
    }


    public void logoff(Text logoffButton, Stage currentStage) {
        logoffButton.setOnMouseClicked(e -> {
            isLoggedIn = false;
            try {
                start(currentStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public void deleteAccount(Text deleteButton, Stage currentStage) {
        deleteButton.setOnMouseClicked(e -> {
            isLoggedIn = false;
            UserDataStore.getInstance().deleteUser(validUsername);
            try {
                start(currentStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }


    private void controls(Stage primaryStage) {

        if (isPacmanStartingPosVisible) setPacmanStartingPos(gameLayout);

        gameScene.setOnKeyPressed(e -> {

            //::::::::::: "UP" KEY & "W" KEY :::::::::::\\

            if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
                waitingForTurn = 'u';

                if (allowNextMoveUp) {

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

                if (allowNextMoveLeft) {

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

                if (allowNextMoveDown) {

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


            // Pause Game when "P" Pressed
            if (e.getCode() == KeyCode.P) {   // If "P" Pressed

                isPacmanStartingPosVisible = false;
                tl.stop();    // Stop Timeline/Animation


                //::::::::::: Pause Menu :::::::::::\\

                gcGame.setFill(Color.YELLOW);
                gcGame.fillText("PAUSED", (blockCountHorizontally / 2) * widthOneBlock, blockCountVertically * heightOneBlock);
                gcGame.fillText("Press P to resume", (blockCountHorizontally / 2) * widthOneBlock, (blockCountVertically + 1) * heightOneBlock);
                gcGame.fillText("Press Esc to leave", (blockCountHorizontally / 2) * widthOneBlock, (blockCountVertically + 2) * heightOneBlock);


                gameScene.setOnKeyPressed(el -> {

                    // Continue Game when P Pressed
                    if (el.getCode() == KeyCode.P) {      // If "P" pressed again

                        tl.play();      // Continue Timeline/Animation

                        controls(primaryStage);      // Recursion -> Check if pressed again
                    }

                    // Leave Game on Esc with Pause
                    if (el.getCode() == KeyCode.ESCAPE) {         // If "Escape" pressed

                        try {
                            gameMechanics.resetGame(gameLayout);
                            isPacmanStartingPosVisible = true;
                            gameStarted = false;
                            primaryStage.setScene(menuScene);
                            //start(primaryStage);            // Restart with new settings

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
                    gameMechanics.resetGame(gameLayout);
                    isPacmanStartingPosVisible = true;
                    gameStarted = false;
                    primaryStage.setScene(menuScene);
                    //start(primaryStage);                 // Restart with new settings

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                tl.jumpTo(Duration.millis(0));          // Restart Animation
                tl.stop();

            }
        });
    }


    private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        gridPane.addRow(8, new Text(""));


        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(400, 400, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, 400);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }


    private GridPane createLoginFormPane() {
        // Instantiate a new Grid Pane
        GridPane loginLayout = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        loginLayout.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        loginLayout.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        loginLayout.setHgap(10);

        // Set the vertical gap between rows
        loginLayout.setVgap(10);


        loginLayout.addRow(6, new Text(""));

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(400, 400, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, 400);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        loginLayout.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        //loginLayout.gridLinesVisibleProperty().set(true);

        return loginLayout;
    }

    private void addUIControls(GridPane gridPane, Stage currentStage, Scene menuScene, Scene loginScene, GraphicsContext gcMenu) {

        // Add PacMan Header
        Label pacmanHeader = new Label("Pac-Man");
        pacmanHeader.setFont(pacmanFont);
        pacmanHeader.setTextFill(Color.WHITE);
        gridPane.add(pacmanHeader, 0, 0, 2, 1);
        GridPane.setHalignment(pacmanHeader, HPos.CENTER);

        // Add Header
        Label headerLabel = new Label("Registration");
        headerLabel.setFont(pacmanFontUI);
        headerLabel.setTextFill(Paint.valueOf("Yellow"));
        gridPane.add(headerLabel, 0, 1, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        gridPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("Black"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Add Name Label
        Label nameLabel = new Label("Username : ");
        nameLabel.setFont(pacmanFontUI);
        nameLabel.setTextFill(Paint.valueOf("Yellow"));
        gridPane.add(nameLabel, 0, 2);

        // Add Name Text Field
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        nameField.setFont(pacmanFontUI);
        textfieldCustom(nameField);
        gridPane.add(nameField, 1, 2);

        // Add Password Label
        Label passwordLabel = new Label("Password : ");
        passwordLabel.setFont(pacmanFontUI);
        passwordLabel.setTextFill(Paint.valueOf("Yellow"));
        gridPane.add(passwordLabel, 0, 4);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        textfieldCustom(passwordField);
        gridPane.add(passwordField, 1, 4);

        // Add Password Confirm Label
        Label passwordConfirmLabel = new Label("Confirm Password : ");
        passwordConfirmLabel.setFont(pacmanFontUI);
        passwordConfirmLabel.setTextFill(Paint.valueOf("Yellow"));
        gridPane.add(passwordConfirmLabel, 0, 6);

        // Add Password Confirm Field
        PasswordField passwordConfirmField = new PasswordField();
        passwordConfirmField.setPrefHeight(40);
        textfieldCustom(passwordConfirmField);
        gridPane.add(passwordConfirmField, 1, 6);

        // Add Submit Button
        Button submitButton = new Button("Register");
        buttonHover(submitButton);
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(350);
        submitButton.setFont(pacmanFontUI);
        submitButton.setTextFill(Paint.valueOf("Yellow"));
        submitButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("Blue"), new CornerRadii(10), Insets.EMPTY)));
        gridPane.add(submitButton, 0, 7, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        // Add Login Button
        Button loginButton = new Button("Login Instead");
        buttonHover(loginButton);
        loginButton.setPrefHeight(40);
        loginButton.setPrefWidth(350);
        loginButton.setFont(pacmanFontUI);
        loginButton.setTextFill(Paint.valueOf("Yellow"));
        loginButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("Blue"), new CornerRadii(10), Insets.EMPTY)));
        gridPane.add(loginButton, 0, 8, 2, 1);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setMargin(loginButton, new Insets(20, 0, 20, 0));


        loginButton.setOnAction(event -> currentStage.setScene(loginScene));

        submitButton.setOnAction(event -> {

            // Empty Input
            if (nameField.getText().isEmpty()) {
                showAlert(gridPane.getScene().getWindow(),
                        "Please enter your name!");
                return;
            }
            // Not matching username regexp
            if (!gameMechanics.isValidNickname(nameField.getText())) {
                showAlert(gridPane.getScene().getWindow(),
                        "Not allowed Username!");
                return;
            }
            if (UserDataStore.getInstance().isUsernameTaken(nameField.getText().toUpperCase())) {
                showAlert(gridPane.getScene().getWindow(),
                        "Username Taken!");
                return;
            }


            // Empty Input
            if (passwordField.getText().isEmpty()) {
                showAlert(gridPane.getScene().getWindow(),
                        "Please enter a password");
                return;
            }
            if (passwordConfirmField.getText().isEmpty()) {
                showAlert(gridPane.getScene().getWindow(),
                        "Please confirm your password!");
                return;
            }
            if (!passwordField.getText().equals(passwordConfirmField.getText())) {
                showAlert(gridPane.getScene().getWindow(),
                        "passwords do not match!");
                return;
            }

            try {
                UserDataStore.getInstance().registerUser(nameField.getText().toUpperCase(), passwordField.getText());
            } catch (IOException e) {
                System.out.println("Registration Error");
                e.printStackTrace();
            }
            isLoggedIn = true;
            validUsername = nameField.getText();
            menuCanvas.play(gcMenu);
            currentStage.setScene(menuScene);
        });
    }

    private void buttonHover(Button button) {
        Bloom bloom = new Bloom();

        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> {
                    button.setEffect(bloom);
                    button.setBackground(new Background(new BackgroundFill(Paint.valueOf("Darkblue"), new CornerRadii(10), Insets.EMPTY)));
                });

        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> {
                    button.setEffect(null);
                    button.setBackground(new Background(new BackgroundFill(Paint.valueOf("Blue"), new CornerRadii(10), Insets.EMPTY)));
                });
    }

    private void textfieldCustom(TextField field) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLUE);
        shadow.setRadius(30);

        field.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> field.setEffect(shadow));

        field.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> field.setEffect(null));
    }


    private void addUIControlsLogin(GridPane gridPane, Stage currentStage, Scene menuScene, Scene registrationScene, GraphicsContext gcMenu) {

        // Add PacMan Header
        Label pacmanHeader = new Label("Pac-Man");
        pacmanHeader.setFont(pacmanFont);
        pacmanHeader.setTextFill(Color.WHITE);
        gridPane.add(pacmanHeader, 0, 0, 2, 1);
        GridPane.setHalignment(pacmanHeader, HPos.CENTER);

        // Add Header
        Label headerLabel = new Label("Login");
        headerLabel.setFont(pacmanFontUI);
        headerLabel.setTextFill(Paint.valueOf("Yellow"));
        gridPane.add(headerLabel, 0, 1, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        gridPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("Black"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Add Name Label
        Label nameLabel = new Label("Username : ");
        nameLabel.setFont(pacmanFontUI);
        nameLabel.setTextFill(Paint.valueOf("Yellow"));
        gridPane.add(nameLabel, 0, 2);

        // Add Name Text Field
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        nameField.setFont(pacmanFontUI);
        textfieldCustom(nameField);
        gridPane.add(nameField, 1, 2);

        // Add Password Label
        Label passwordLabel = new Label("Password : ");
        passwordLabel.setFont(pacmanFontUI);
        passwordLabel.setTextFill(Paint.valueOf("Yellow"));
        gridPane.add(passwordLabel, 0, 4);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        textfieldCustom(passwordField);
        gridPane.add(passwordField, 1, 4);

        // Add Submit Button
        Button submitButton = new Button("Login");
        buttonHover(submitButton);
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(350);
        submitButton.setFont(pacmanFontUI);
        submitButton.setTextFill(Paint.valueOf("Yellow"));
        submitButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("Blue"), new CornerRadii(10), Insets.EMPTY)));
        gridPane.add(submitButton, 0, 5, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        // Add Login Button
        Button loginButton = new Button("Create account");
        buttonHover(loginButton);
        loginButton.setPrefHeight(40);
        loginButton.setPrefWidth(350);
        loginButton.setFont(pacmanFontUI);
        loginButton.setTextFill(Paint.valueOf("Yellow"));
        loginButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("Blue"), new CornerRadii(10), Insets.EMPTY)));

        gridPane.add(loginButton, 0, 6, 2, 1);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setMargin(loginButton, new Insets(20, 0, 20, 0));


        loginButton.setOnAction(event -> currentStage.setScene(registrationScene));

        submitButton.setOnAction(event -> {
            if (nameField.getText().isEmpty()) {
                showAlert(gridPane.getScene().getWindow(),
                        "Please enter your name!");
                return;
            }
            if (passwordField.getText().isEmpty()) {
                showAlert(gridPane.getScene().getWindow(),
                        "Please enter a password");
                return;
            }
            if (!UserDataStore.getInstance().isLoginCorrect(nameField.getText().toUpperCase(), passwordField.getText())) {
                showAlert(gridPane.getScene().getWindow(),
                        "Wrong Username or Password!");
                return;
            }

            currentStage.setScene(menuScene);
            validUsername = nameField.getText();
            isLoggedIn = true;
            menuCanvas.play(gcMenu);
        });

    }


    private void showAlert(Window owner, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Form Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }


    public static long startingTime;

    public static void main(String[] args) {

        // TODO: Music
        // Sets Background Music
        // sounds.playBackgroundMusic();

        startingTime = System.currentTimeMillis();

        System.out.println(ANSI_GREEN);
        //Application.launch(args);
        myLaunch(main.class);
        System.out.println(ANSI_RESET);
    }
}


