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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;

import static application.variables.*;


public class main extends Application {


    /**
     * Launching Game
     *
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

        gameCanvas.play(gcGame, gameLayout);


        gameLayout.getChildren().add(canvasGame);


        //::::::::::: Timeline :::::::::::\\

        // JavaFX Timeline = Free form animation defined by KeyFrames and their duration
        KeyFrame kf = new KeyFrame(Duration.millis(10), e -> gameCanvas.play(gcGame, gameLayout));
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


        //::::::::::: Logoff Button :::::::::::\\

        // Label
        Text logoffButton = new Text("Log out");
        logoffButton.setStroke(Color.YELLOW);
        logoffButton.setFont(pacmanFontUI);

        // Option Label Position
        logoffButton.setLayoutY(height - 50);
        logoffButton.setLayoutX(50);


        //::::::::::: Delete Account Button :::::::::::\\

        // Label
        Text deleteAccountButton = new Text("Delete account");
        deleteAccountButton.setStroke(Color.YELLOW);
        deleteAccountButton.setFont(pacmanFontUI);

        // Option Label Position
        deleteAccountButton.setLayoutY(height - 50);
        deleteAccountButton.setLayoutX(width - 300); // TODO: Improve "- 300"

        // Adds Canvas to Layout
        settingsLayout.getChildren().addAll(canvasSettings, logoffButton, deleteAccountButton);


        //------------------------------------------------------ HIGHSCORE WINDOW ------------------------------------------------------\\

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

        // Layout
        Group menuLayout = new Group();

        // Scene
        Scene menuScene = new Scene(menuLayout, width, height);


        //::::::::::: MENU GIF :::::::::::\\

        //Setting the position of the image
        viewMenuAnimation.setX(10);
        viewMenuAnimation.setY((height / 10) * 3);
        viewMenuAnimation.setFitWidth(width);
        viewMenuAnimation.setFitHeight(150);


        //::::::::::: PLAY Button :::::::::::\\

        // Label
        Text playButton = new Text("play");
        playButton.setStroke(fontColor);
        pacmanFontSize = 80;
        playButton.setFont(pacmanFont);

        // Option Label Position
        playButton.setLayoutY(height - (height / 10) * 3);
        playButton.setLayoutX((width / 2) - playButton.getBoundsInParent().getWidth() / 2);


        //::::::::::: SETTINGS Button :::::::::::\\

        // Label
        Text settingsButton = new Text("settings");
        settingsButton.setStroke(fontColor);
        pacmanFontSize = 60;
        settingsButton.setFont(pacmanFont);

        // Option Label Position
        settingsButton.setLayoutY(height - (height / 10) * 2);
        settingsButton.setLayoutX((width / 2) - settingsButton.getBoundsInParent().getWidth() / 2);


        //::::::::::: HIGHSCORES Button :::::::::::\\

        // Label
        Text highscoreButton = new Text("highscores");
        highscoreButton.setStroke(fontColor);
        highscoreButton.setFont(pacmanFont);

        // Option Label Position
        highscoreButton.setLayoutY(height - (height / 10));
        highscoreButton.setLayoutX((width / 2) - highscoreButton.getBoundsInParent().getWidth() / 2);


        // FUNCTIONALITY
        playButtonFunction.play(playButton, currentStage, gameScene, tl);
        settingsButtonFunction.play(settingsButton, currentStage, settingsScene);
        highscoresButtonFunction.play(highscoreButton, currentStage, highscoreScene, gcHighscore);


        // Add to Layout
        menuLayout.getChildren().addAll(canvasMenu, playButton, viewMenuAnimation, settingsButton, highscoreButton);


        //------------------------------------------------------ REGISTRATION FORM ------------------------------------------------------\\

        // Create the registration form grid pane
        GridPane registrationLayout = createRegistrationFormPane();

        // Create a scene with registration form grid pane as the root node
        Scene registrationScene = new Scene(registrationLayout, width, height);


        //------------------------------------------------------ LOGIN FORM ------------------------------------------------------\\

        // Create the registration form grid pane
        GridPane loginLayout = createLoginFormPane();

        // Create a scene with registration form grid pane as the root node
        Scene loginScene = new Scene(loginLayout, width, height);


        // Add UI controls to the registration form grid pane
        addUIControls(registrationLayout, currentStage, menuScene, loginScene, gcMenu);

        // Add UI controls to the registration form grid pane
        addUIControlsLogin(loginLayout, currentStage, menuScene, registrationScene, gcMenu);

        if (!isLoggedIn) {
            // Set the scene in primary stage
            currentStage.setScene(loginScene);

        } else {
            menuCanvas.play(gcMenu);
            currentStage.setScene(menuScene);
        }
        currentStage.show();

        logoff(logoffButton, currentStage);
        deleteAccount(deleteAccountButton, currentStage);



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

    public void deleteAccount(Text deleteButton, Stage currentStage){
        deleteButton.setOnMouseClicked(e -> {
            isLoggedIn = false;
            UserDataStore.getInstance().deleteUser(finalUsername);
            try {
                start(currentStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public void controls(Scene gameScene, Group gameLayout, Timeline tl, GraphicsContext gc, Stage primaryStage, Scene menuScene) {

        if (startingStatus) {
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

                if (allowNextMoveLeft) {


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

                if (allowNextMoveDown) {

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
                lifesCounter--;
                gameMechanics.drawLifes(gameLayout);
            }


            // Pause Game when "P" Pressed
            if (e.getCode() == KeyCode.P) {   // If "P" Pressed

                startingStatus = false;
                tl.stop();    // Stop Timeline/Animation


                //::::::::::: Pause Menu :::::::::::\\

                gc.setFill(Color.YELLOW);
                gc.fillText("PAUSED", (blockCountHorizontally / 2) * widthOneBlock, blockCountVertically * heightOneBlock);
                gc.fillText("Press P to resume", (blockCountHorizontally / 2) * widthOneBlock, (blockCountVertically + 1) * heightOneBlock);
                gc.fillText("Press Esc to leave", (blockCountHorizontally / 2) * widthOneBlock, (blockCountVertically + 2) * heightOneBlock);


                gameScene.setOnKeyPressed(el -> {

                    // Continue Game when P Pressed
                    if (el.getCode() == KeyCode.P) {      // If "P" pressed again

                        tl.play();      // Continue Timeline/Animation

                        controls(gameScene, gameLayout, tl, gc, primaryStage, menuScene);      // Recursion -> Check if pressed again
                    }

                    // Leave Game on Esc with Pause
                    if (el.getCode() == KeyCode.ESCAPE) {         // If "Escape" pressed

                        try {
                            gameMechanics.resetGame(gameLayout);
                            startingStatus = true;
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
                    startingStatus = true;
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
            if (nameField.getText().isEmpty()) {
                showAlert(gridPane.getScene().getWindow(),
                        "Please enter your name!");
                return;
            }
            if (gameMechanics.validNickname(nameField.getText())) {
                validUsername = nameField.getText();
            } else {
                showAlert(gridPane.getScene().getWindow(),
                        "Not allowed Username!");
                return;
            }
            if (UserDataStore.getInstance().isUsernameTaken(nameField.getText().toUpperCase())) {
                showAlert(gridPane.getScene().getWindow(),
                        "Username Taken!");
                return;
            }
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
                e.printStackTrace();
            }
            isLoggedIn = true;
            finalUsername = nameField.getText();
            menuCanvas.play(gcMenu);
            currentStage.setScene(menuScene);
        });
    }

    private void buttonHover(Button button) {
        Bloom bloom = new Bloom();
        //Adding the shadow when the mouse cursor is on
        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> {
                    button.setEffect(bloom);
                    button.setBackground(new Background(new BackgroundFill(Paint.valueOf("Darkblue"), new CornerRadii(10), Insets.EMPTY)));
                });
        //Removing the shadow when the mouse cursor is off
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
        //Removing the shadow when the mouse cursor is off
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
            finalUsername = validUsername;
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


    public static void main(String[] args) {

        // TODO: Music
        // Sets Background Music
        // sounds.playBackgroundMusic();

        System.out.println(ANSI_GREEN + "--- Application Started ---");
        launch(args);
        System.out.println(ANSI_RESET);
    }
}


