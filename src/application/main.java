package application;

//---------------------------------IMPORTS---------------------------------\\

import application.canvas.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static application.Server.clientsScoreMap;
import static application.Server.verbindung;
import static application.ai.Ghost.chaseTimer;
import static application.ai.Ghost.scatterTimer;
import static application.gameMechanics.*;
import static application.imageViewerVariables.*;
import static application.mapReader.blockCountHorizontally;
import static application.mapReader.blockCountVertically;
import static application.sounds.sfxSoundsOn;


@SuppressWarnings("ALL")
public class main extends Application implements Serializable {

    //---------------------------------VARIABLES---------------------------------\\


    public static double width = 1300;       // Window width
    public static double height = 1000;      // Window height

    public static ObjectOutputStream out;
    public static ObjectInputStream in;

    public static volatile boolean javaFxLaunched = false;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private static boolean isLoggedIn = false;

    public static boolean isPacmanStartingPosVisible = true;

    public static boolean gameStarted;    // has the game started? true/false

    public static Color backgroundColor = Color.BLACK;    // Background Color
    public static Color fontColor = Color.WHITE;          // Font Color

    public static String validUsername = "Unknown Player";
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

    public static boolean pacmanFacingUp = false;
    public static boolean pacmanFacingDown = false;
    public static boolean pacmanFacingLeft = false;
    public static boolean pacmanFacingRight = true;

    static char waitingForTurn;

    static double velocityPacmanHorizontal = 0;
    static double velocityPacmanVertical = 0;
    public static double velocityAdder = 0;

    static boolean hitRightWall = false;
    static boolean hitLeftWall = false;
    static boolean hitUpWall = false;
    static boolean hitDownWall = false;


    private static GraphicsContext gcSettings;
    private static Scene settingsScene;
    private static Text logoffButton;
    private static Text deleteAccountButton;
    private static Text exitButton;

    public static boolean inPinkMode = false;

    public static String clickPath = "resources/sounds/click.mp3";                                // Music file location
    public static String clickURI = Paths.get(clickPath).toUri().toString();                      // Convert to URI

    public static boolean isFullscreen = false;


    TextArea receivedMsgArea = new TextArea();
    TextField ipText = new TextField();
    TextField portText = new TextField();
    TextArea sendMsgArea = new TextArea();
    TextField statusText = new TextField();
    Button sendButton = new Button(" Send ");
    ObservableList<String> clients = FXCollections.observableArrayList();
    ListView<String> clientListView = new ListView<>(clients);


    private void createSettingsWindow() {
        // TODO: Settings

        // Canvas
        Canvas canvasSettings = new Canvas(width, height);
        gcSettings = canvasSettings.getGraphicsContext2D();

        // Layout
        Group settingsLayout = new Group();

        // Scene
        settingsScene = new Scene(settingsLayout, width, height);

        // Adds Canvas to Layout
        settingsLayout.getChildren().addAll(canvasSettings, logoffButton, deleteAccountButton, exitButton, getScheme, getScheme2, getScheme3, getSFX, getScheme5, getScheme6, statsButton);
    }

    private void createLogoffButton() {
        // Label
        logoffButton = new Text("Log out");
        logoffButton.setStroke(Color.YELLOW);
        logoffButton.setFont(pacmanFontUI);

        // Option Label Position
        logoffButton.setLayoutY(height - 50);
        logoffButton.setLayoutX(0.05 * width);
    }


    private static GraphicsContext gcStats;
    private static Scene statsScene;
    private static Group statsLayout;
    private static Text statsButton;

    private void createStatsWindow() {
        // Canvas
        Canvas canvasStats = new Canvas(width, height);
        gcStats = canvasStats.getGraphicsContext2D();

        // Layout
        statsLayout = new Group();

        // Scene
        statsScene = new Scene(statsLayout, width, height);
        statsLayout.getChildren().add(canvasStats);
    }

    private void createStatsButton(Stage currentStage) {
        // Label
        statsButton = new Text("stats");
        statsButton.setStroke(fontColor);
        pacmanFontSize = 60;
        statsButton.setFont(pacmanFontUI);

        // Option Label Position
        statsButton.setLayoutY(height - 50);
        statsButton.setLayoutX(width / 2 - statsButton.getBoundsInParent().getWidth() / 2);

        statsButton.setOnMouseClicked(e -> {

            sounds.playClick();
            statsCanvas.play(gcStats);

            // Primary Stage -> Settings Canvas
            currentStage.setScene(statsScene);
            if (isFullscreen) currentStage.setFullScreen(true);
            currentStage.show();
        });
    }


    private static ComboBox getScheme;

    private void createGetSchemeButton() {
        getScheme = new ComboBox();

        getScheme.getItems().add("Pac-Man");
        getScheme.getItems().add("Mrs. Pac-Man");

        getScheme.setLayoutX(width / 8);
        getScheme.setLayoutY(height / 6);

        getScheme.setPrefWidth(250);
        getScheme.setPrefHeight(40);

        getScheme.setPromptText("SELECT SCHEME");
        getScheme.setEffect(addShadowDropdown(0.1));
        addHoverDropdown(getScheme);
    }


    private static ComboBox getScheme2;

    private void createGetSchemeButton2() {
        getScheme2 = new ComboBox();

        getScheme2.getItems().add("1300x1000");
        getScheme2.getItems().add("Fullscreen");

        getScheme2.setLayoutX(width / 8);
        getScheme2.setLayoutY(height / 6 + (height / 6));

        getScheme2.setPrefWidth(250);
        getScheme2.setPrefHeight(40);

        getScheme2.setPromptText("SCREEN SIZE");
        getScheme2.setEffect(addShadowDropdown(0.1));
        addHoverDropdown(getScheme2);
    }


    private static ComboBox getScheme3;

    private void createGetSchemeButton3() {
        getScheme3 = new ComboBox();

        getScheme3.getItems().add("ON");
        getScheme3.getItems().add("OFF");

        getScheme3.setLayoutX(width / 8);
        getScheme3.setLayoutY(height / 6 + (height / 6) * 2);

        getScheme3.setPrefWidth(250);
        getScheme3.setPrefHeight(40);

        getScheme3.setPromptText("");
        getScheme3.setEffect(addShadowDropdown(0.1));
        addHoverDropdown(getScheme3);
    }


    private static ComboBox getSFX;

    private void createGetSFX() {
        getSFX = new ComboBox();

        getSFX.getItems().add("ON");
        getSFX.getItems().add("OFF");

        getSFX.setLayoutX(width / 8 + (width / 8) * 4);
        getSFX.setLayoutY(height / 6);

        getSFX.setPrefWidth(250);
        getSFX.setPrefHeight(40);

        getSFX.setPromptText("TOGGLE SFX");
        getSFX.setEffect(addShadowDropdown(0.1));
        addHoverDropdown(getSFX);
    }


    private static ComboBox getScheme5;

    private void createGetSchemeButton5() {
        getScheme5 = new ComboBox();

        getScheme5.getItems().add("ON");
        getScheme5.getItems().add("OFF");

        getScheme5.setLayoutX(width / 8 + (width / 8) * 4);
        getScheme5.setLayoutY(height / 6 + (height / 6));

        getScheme5.setPrefWidth(250);
        getScheme5.setPrefHeight(40);

        getScheme5.setPromptText("TOGGLE MUSIC");
        getScheme5.setEffect(addShadowDropdown(0.1));
        addHoverDropdown(getScheme5);
    }


    private static ComboBox getScheme6;

    private void createGetSchemeButton6() {
        getScheme6 = new ComboBox();

        getScheme6.getItems().add("Pac-Man");
        getScheme6.getItems().add("Mrs. Pac-Man");

        getScheme6.setLayoutX(width / 8 + (width / 8) * 4);
        getScheme6.setLayoutY(height / 6 + (height / 6) * 2);

        getScheme6.setPrefWidth(250);
        getScheme6.setPrefHeight(40);

        getScheme6.setPromptText("");
        getScheme6.setEffect(addShadowDropdown(0.1));
        addHoverDropdown(getScheme6);
    }


    private DropShadow addShadowDropdown(double spread) {
        DropShadow ds = new DropShadow();
        ds.setSpread(spread);
        ds.setOffsetY(1);
        ds.setOffsetX(1);
        ds.setColor(Color.YELLOW);
        return ds;
    }

    private void addHoverDropdown(ComboBox c) {

        c.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> {
                    c.setEffect(addShadowDropdown(0.5));
                });

        c.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> {
                    c.setEffect(addShadowDropdown(0.1));
                });
    }

    private void createDeleteAccountButton() {
        // Label
        deleteAccountButton = new Text("Delete account");
        deleteAccountButton.setStroke(Color.YELLOW);
        deleteAccountButton.setFont(pacmanFontUI);

        // Option Label Position
        deleteAccountButton.setLayoutY(height - 50);
        deleteAccountButton.setLayoutX(width - 300); // TODO: Improve "- 300"
    }


    private void createExitButton() {
        // Label
        exitButton = new Text("Exit");
        exitButton.setStroke(Color.YELLOW);
        exitButton.setFont(pacmanFontUI);

        // Option Label Position
        exitButton.setLayoutY(50);
        exitButton.setLayoutX(0.05 * width);
    }

    private static GraphicsContext gcGame;
    private static Group gameLayout;
    private static Scene gameScene;
    public static Timeline tl;

    private void createGameWindow() {
        // Canvas
        Canvas canvasGame = new Canvas(width, height);
        gcGame = canvasGame.getGraphicsContext2D();

        // Layout
        gameLayout = new Group();

        // Scene
        gameScene = new Scene(gameLayout, width, height);
        gameLayout.getChildren().addAll(canvasGame, speedUpButton, speedDownButton);
    }


    public static Text speedUpButton;

    private void createSpeedUpButton() {
        // Label
        speedUpButton = new Text("+");
        speedUpButton.setStroke(Color.YELLOW);
        speedUpButton.setFill(Color.YELLOW);
        speedUpButton.setFont(pacmanFontUI);

        // Option Label Position
        speedUpButton.setLayoutY(height - 100);
        speedUpButton.setLayoutX(width - 300); // TODO: Improve "- 300"
    }

    public static Text speedDownButton;

    private void createSpeedDownButton() {
        // Label
        speedDownButton = new Text("-");
        speedDownButton.setStroke(Color.YELLOW);
        speedDownButton.setFill(Color.YELLOW);
        speedDownButton.setFont(pacmanFontUI);

        // Option Label Position
        speedDownButton.setLayoutY(height - 100);
        speedDownButton.setLayoutX(width - 350); // TODO: Improve "- 350"
    }

    private void createTimeline() {
        // JavaFX Timeline = Free form animation defined by KeyFrames and their duration
        KeyFrame kf = new KeyFrame(Duration.millis(10), e -> gameCanvas.play(gcGame, gameLayout));
        tl = new Timeline(kf);

        // number of cycles in animation INDEFINITE = repeat indefinitely
        tl.setCycleCount(Timeline.INDEFINITE);
    }

    private static GraphicsContext gcHighscore;
    private static Scene highscoreScene;

    private void createHighscoreWindow() {
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

    Group menuLayout;

    private void createMenuWindow() {
        // Canvas
        Canvas canvasMenu = new Canvas(width, height);
        gcMenu = canvasMenu.getGraphicsContext2D();

        // Layout
        menuLayout = new Group();

        // Scene
        menuScene = new Scene(menuLayout, width, height);

        // Add to Layout
        menuLayout.getChildren().addAll(canvasMenu, playButton, viewMenuAnimation, settingsButton, highscoreButton, viewCoin, coinCount);
    }

    private void createMenuAnimation() {
        //Setting the position of the image
        viewMenuAnimation.setX(10);
        viewMenuAnimation.setY((height / 10) * 3);
        viewMenuAnimation.setFitWidth(width);
        viewMenuAnimation.setFitHeight(150);
    }

    static Text coinCount;

    private void createShowCoin() {
        viewCoin.setFitHeight(heightOneBlock * 1.5);
        viewCoin.setFitWidth(widthOneBlock * 1.5);

        viewCoin.setY(viewCoin.getFitHeight());
        viewCoin.setX(viewCoin.getFitWidth());

        String coins = "";
        try {
            sqlConnection();

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM User WHERE name = '" + validUsername.toUpperCase() + "'");

            while (results.next()) {
                coins = String.valueOf(Integer.parseInt(results.getString("alltimeScore")) / 10);
            }
        } catch (Exception e) {
            System.out.println("Error while reading Database");
            e.printStackTrace();
        }

        coinCount = new Text(coins);
        coinCount.setFill(Color.YELLOW);
        coinCount.setStroke(Color.DARKGRAY);
        coinCount.setFont(Font.loadFont("file:resources/fonts/emulogic.ttf", heightOneBlock));
        coinCount.setX(viewCoin.getFitWidth() + widthOneBlock * 2);
        coinCount.setY(viewCoin.getFitHeight() + coinCount.getBoundsInParent().getHeight());

    }

    private void createPlayButton(Stage currentStage) {
        // Label
        playButton = new Text("play");
        playButton.setStroke(fontColor);
        pacmanFontSize = 80;
        playButton.setFont(pacmanFont);

        // Option Label Position
        playButton.setLayoutY(height - (height / 10) * 3);
        playButton.setLayoutX((width / 2) - playButton.getBoundsInParent().getWidth() / 2);


        playButton.setOnMouseClicked(e -> {            // If clicked
            sounds.playClick();
            gameCanvas.play(gcGame, gameLayout);
            gameStarted = true;
            setPacmanStartingPos(gameLayout);

            // Primary Stage -> Game Canvas
            currentStage.setScene(gameScene);
            if (isFullscreen) currentStage.setFullScreen(true);
            currentStage.show();

            tl.playFromStart();                            // Start Animation

            lifesCounter--;
        });
    }

    private void createSettingsButton(Stage currentStage) {
        // Label
        settingsButton = new Text("settings");
        settingsButton.setStroke(fontColor);
        pacmanFontSize = 60;
        settingsButton.setFont(pacmanFont);

        // Option Label Position
        settingsButton.setLayoutY(height - (height / 10) * 2);
        settingsButton.setLayoutX((width / 2) - settingsButton.getBoundsInParent().getWidth() / 2);

        settingsButton.setOnMouseClicked(e -> {            // If clicked

            sounds.playClick();
            settingsCanvas.play(gcSettings);

            // Primary Stage -> Settings Canvas
            currentStage.setScene(settingsScene);
            if (isFullscreen) currentStage.setFullScreen(true);
            currentStage.show();
        });
    }

    private void createHighscoreButton(Stage currentStage) {
        // Label
        highscoreButton = new Text("highscores");
        highscoreButton.setStroke(fontColor);
        highscoreButton.setFont(pacmanFont);

        // Option Label Position
        highscoreButton.setLayoutY(height - (height / 10));
        highscoreButton.setLayoutX((width / 2) - highscoreButton.getBoundsInParent().getWidth() / 2);

        highscoreButton.setOnMouseClicked(e -> {            // If clicked
            try {
                sounds.playClick();
                highscoreCanvas.play(gcHighscore);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

            // Primary Stage -> Settings Canvas
            currentStage.setScene(highscoreScene);
            if (isFullscreen) currentStage.setFullScreen(true);
            currentStage.show();
        });
    }


    public static void myLaunch(Class<? extends Application> applicationClass) {
        if (!javaFxLaunched) { // First time
            Platform.setImplicitExit(false);
            new Thread(() -> Application.launch(applicationClass)).start();
            javaFxLaunched = true;
        } else { // Next times
            new JFXPanel();
            Platform.runLater(() -> {
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
        currentStage.setResizable(false);


        //----------------------------------------------------------------------------------------WINDOWS----------------------------------------------------------------------------------------\\


        //------------------------------------------------------ GAME WINDOW ------------------------------------------------------\\

        createSpeedUpButton();
        createSpeedDownButton();
        createGameWindow();
        createTimeline();


        //------------------------------------------------------ SETTINGS WINDOW ------------------------------------------------------\\

        createLogoffButton();
        createDeleteAccountButton();
        createExitButton();
        createGetSchemeButton();
        createGetSchemeButton2();
        createGetSchemeButton3();
        createGetSFX();
        createGetSchemeButton5();
        createGetSchemeButton6();
        createStatsButton(currentStage);
        createSettingsWindow();
        settingsScene.getStylesheets().add("file:resources/css/settings.css");


        //------------------------------------------------------ HIGHSCORE WINDOW ------------------------------------------------------\\

        createHighscoreWindow();

        //------------------------------------------------------ STATS WINDOW ------------------------------------------------------\\

        createStatsWindow();

        //------------------------------------------------------ MENU WINDOW ------------------------------------------------------\\

        createMenuAnimation();
        createShowCoin();
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
        if (isFullscreen) currentStage.setFullScreen(true);


        if (!isLoggedIn) {
            currentStage.setScene(loginScene);
            if (isFullscreen) currentStage.setFullScreen(true);
        }

        currentStage.show();

        logoff(logoffButton, currentStage);
        deleteAccount(deleteAccountButton, currentStage);
        exitToMain(exitButton, currentStage, menuScene);


        //----------------------------------------------------------------------------------------CONTROLS----------------------------------------------------------------------------------------\\

        //--------------------------------------------STATS CONTROLS--------------------------------------------\\

        statsScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {      // If "Escape" Pressed
                try {
                    sounds.playClick();
                    currentStage.setScene(settingsScene);
                    if (isFullscreen) currentStage.setFullScreen(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //--------------------------------------------SETTINGS CONTROLS--------------------------------------------\\

        settingsScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {      // If "Escape" Pressed
                try {
                    sounds.playClick();
                    start(currentStage);            // Restart with new settings
                    gameStarted = false;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        getScheme.setOnAction((event) -> {
            sounds.playClick();
            int selectedIndex = getScheme.getSelectionModel().getSelectedIndex();

            switch (selectedIndex) {
                case 0 -> {
                    viewPacmanLeft = new ImageView(pacmanLeft);
                    viewPacmanRight = new ImageView(pacmanRight);
                    viewPacmanUp = new ImageView(pacmanUp);
                    viewPacmanDown = new ImageView(pacmanDown);

                    inPinkMode = false;
                }
                case 1 -> {
                    viewPacmanLeft = new ImageView(MrspacmanLeft);
                    viewPacmanRight = new ImageView(MrspacmanRight);
                    viewPacmanUp = new ImageView(MrspacmanUp);
                    viewPacmanDown = new ImageView(MrspacmanDown);
                    inPinkMode = true;
                }
            }
        });

        getSFX.setOnAction((event) -> {
            sounds.playClick();
            int selectedIndex = getSFX.getSelectionModel().getSelectedIndex();

            switch (selectedIndex) {
                case 0 -> {
                    sfxSoundsOn = true;
                }
                case 1 -> {
                    sfxSoundsOn = false;
                }
            }
        });

        getScheme2.setOnAction((event) -> {
            sounds.playClick();
            int selectedIndex = getScheme2.getSelectionModel().getSelectedIndex();

            switch (selectedIndex) {
                case 0 -> {
                    width = 1300;
                    height = 1000;
                    isFullscreen = false;
                    currentStage.setFullScreen(false);
                    start(currentStage);
                }
                case 1 -> {
                    width = Screen.getPrimary().getBounds().getWidth();
                    height = Screen.getPrimary().getBounds().getHeight();
                    isFullscreen = true;
                    currentStage.setFullScreen(true);
                    currentStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    currentStage.setFullScreenExitHint("Vollbildmodus");
                    start(currentStage);
                }
            }
        });

        //--------------------------------------------HIGHSCORE CONTROLS--------------------------------------------\\

        highscoreScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {      // If "Escape" Pressed
                sounds.playClick();
                currentStage.setScene(menuScene);   // Go to Menu
                if (isFullscreen) currentStage.setFullScreen(true);
            }
        });

        //--------------------------------------------GAME CONTROLS--------------------------------------------\\

        controls(currentStage);     // Controls

        speedUpButton.setOnMouseClicked(e -> {
            if (velocityAdder >= 2.0) return;
            velocityAdder += 0.1;
        });

        speedDownButton.setOnMouseClicked(e -> {
            if (velocityAdder <= 0.0) return;
            velocityAdder -= 0.1;
        });

        sendScore();
        readAllScores(gcGame);
    }


    public void logoff(Text logoffButton, Stage currentStage) {
        logoffButton.setOnMouseClicked(e -> {
            sounds.playClick();
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
            sounds.playClick();
            isLoggedIn = false;
            application.UserDataStore.getInstance().deleteUser(validUsername);
            try {
                start(currentStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public void exitToMain(Text exitButton, Stage currentStage, Scene menuScene) {
        exitButton.setOnMouseClicked(e -> {
            sounds.playClick();
            currentStage.setScene(menuScene);
            if (isFullscreen) currentStage.setFullScreen(true);
            start(currentStage);
        });
    }


    private void controls(Stage primaryStage) {

        Thread t = new Thread(() -> {

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
                        velocityPacmanVertical = -1 - velocityAdder;

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
                        velocityPacmanHorizontal = 1 + velocityAdder;
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
                        velocityPacmanHorizontal = -1 - velocityAdder;
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
                        velocityPacmanVertical = 1 + velocityAdder;

                        hitDownWall = false;
                    }
                }


                // Pause Game when "P" Pressed
                if (e.getCode() == KeyCode.P) {   // If "P" Pressed

                    sounds.playClick();
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

                            sounds.playClick();
                            tl.play();      // Continue Timeline/Animation

                            controls(primaryStage);      // Recursion -> Check if pressed again
                        }

                        // Leave Game on Esc with Pause
                        if (el.getCode() == KeyCode.ESCAPE) {         // If "Escape" pressed

                            try {
                                sounds.playClick();
                                resetGame(gameLayout);
                                isPacmanStartingPosVisible = true;
                                gameStarted = false;
                                primaryStage.setScene(menuScene);
                                if (isFullscreen) primaryStage.setFullScreen(true);
                                chaseTimer.cancel();
                                scatterTimer.cancel();
                                chaseTimer = new Timer();
                                scatterTimer = new Timer();
                                chaseCount = 0;
                                scatterCount = 0;

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
                        sounds.playClick();
                        resetGame(gameLayout);
                        isPacmanStartingPosVisible = true;
                        gameStarted = false;
                        primaryStage.setScene(menuScene);
                        if (isFullscreen) primaryStage.setFullScreen(true);
                        chaseTimer.cancel();
                        scatterTimer.cancel();
                        chaseTimer = new Timer();
                        scatterTimer = new Timer();
                        chaseCount = 0;
                        scatterCount = 0;

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    tl.jumpTo(Duration.millis(0));          // Restart Animation
                    tl.stop();

                }
            });
        });
        t.start();
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


        loginButton.setOnAction(event -> {
            currentStage.setScene(loginScene);
            if (isFullscreen) currentStage.setFullScreen(true);
        });

        submitButton.setOnAction(event -> {

            // Empty Input
            if (nameField.getText().isEmpty()) {
                showAlert(gridPane.getScene().getWindow(),
                        "Please enter your name!");
                return;
            }
            // Not matching username regexp
            if (!isValidNickname(nameField.getText())) {
                showAlert(gridPane.getScene().getWindow(),
                        "Not allowed Username!");
                return;
            }
            if (application.UserDataStore.getInstance().isUsernameTaken(nameField.getText().toUpperCase())) {
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
                application.UserDataStore.getInstance().registerUser(nameField.getText().toUpperCase(), passwordField.getText());
            } catch (IOException | SQLException e) {
                System.out.println("Registration Error");
                e.printStackTrace();
            }
            isLoggedIn = true;
            validUsername = nameField.getText();
            createShowCoin();
            menuLayout.getChildren().add(coinCount);
            menuCanvas.play(gcMenu);
            currentStage.setScene(menuScene);
            if (isFullscreen) currentStage.setFullScreen(true);

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


        loginButton.setOnAction(event -> {
            currentStage.setScene(registrationScene);
            if (isFullscreen) currentStage.setFullScreen(true);
        });

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

            validUsername = nameField.getText();
            isLoggedIn = true;
            createShowCoin();
            menuLayout.getChildren().add(coinCount);
            menuCanvas.play(gcMenu);
            currentStage.setScene(menuScene);
            if (isFullscreen) currentStage.setFullScreen(true);
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

    public static String url = "jdbc:mysql://localhost:3306/javabase";
    public static String username = "root";
    public static String password = "";
    public static Connection connection = null;


    public static void sqlConnection() {

        /**
         try {
         Class.forName("com.mysql.jdbc.Driver");
         } catch (ClassNotFoundException e) {
         throw new IllegalStateException("Cannot find the driver in the classpath!", e);
         }
         **/

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }


    public static void sendScore() {
        Runnable helloRunnable = new Runnable() {
            public void run() {
                try {
                    out.writeUTF(validUsername + "," + score);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
    }

    public static HashMap<String, Integer> clientScores = new HashMap<>();

    public static void readAllScores(GraphicsContext gcGame) {
        Runnable helloRunnable = () -> {
            try {

                String x = in.readUTF().replace("{", "");
                x = x.replace("}", "");
                x = x.replace(", ", "=");

                for (int i = 0; i + 1 < x.split("=").length; i += 2) {
                    clientScores.put(x.split("=")[i], Integer.parseInt(x.split("=")[i + 1]));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);
        executor2.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
    }


    public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();

        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }




        public static long startingTime;

    public static void main(String[] args) {
        try {

            verbindung = new Socket("192.168.66.1", 10024);

            System.out.println("Verbunden");
            out = new ObjectOutputStream(verbindung.getOutputStream());
            in = new ObjectInputStream(verbindung.getInputStream());

            launch(args);
        } catch (Exception e) {
            System.out.println("Verbindung fehlgeschlagen");
        }

        //sounds.playBackgroundMusic();
        // TODO: Music
        // Sets Background Music
        // sounds.playBackgroundMusic();
        //Application.launch(args);
        //myLaunch(main.class);
    }
}


