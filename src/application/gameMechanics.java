package application;


//---------------------------------IMPORTS---------------------------------\\

import application.ai.Ghost;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Timer;
import java.util.regex.Pattern;
import static application.imageViewerVariables.*;
import static application.main.*;
import static application.mapReader.*;


//---------------------------------CLASS---------------------------------\\
@SuppressWarnings("ALL")
public class gameMechanics {

    //---------------------------------VARIABLES---------------------------------\\

    private static boolean nextLevel = true;
    public static int score = 0;    // player score
    public static int highscore;    // Highest score

    private static final int maxMaps = 2;

    static String mapFile = "resources/levels/level1.txt";

    public static int levelCounter = 0;
    static final int startingLevel = 1;

    static boolean firstRead = true;

    static boolean[][] dots = new boolean[blockCountHorizontally + 2][blockCountVertically + 1];
    static boolean[][] powerPills = new boolean[blockCountHorizontally + 2][blockCountVertically + 1];
    public static boolean[][] notAllowedBox = new boolean[blockCountHorizontally + 2][blockCountVertically + 1];

    static int dotCount = 0;
    static int dotCountAtStart = 0;
    static int powerPillCount = 0;
    static int wallCount = 0;
    static int railVerticalCount = 0;
    static int railHorizontalCount = 0;
    static int railUpRightCount = 0;
    static int railUpLeftCount = 0;
    static int railRightUpCount = 0;
    static int railLeftUpCount = 0;

    public static double pacmanRow;
    public static double pacmanColumn;
    public static double pacmanXPos;
    public static double pacmanYPos;

    static double pacmanXPosCenter = pacmanXPos + (int) (characterWidth / 2);
    static double pacmanYPosCenter = pacmanYPos + (int) (characterWidth / 2);

    static boolean allowNextMoveUp = true;
    static boolean allowNextMoveDown = true;
    public static boolean allowNextMoveLeft = true;
    public static boolean allowNextMoveRight = true;

    static boolean stop = false;

    static boolean collectableFruit = false;
    static boolean fruitSpawned1 = false;
    static boolean fruitSpawned2 = false;

    static boolean getRandomLifespan = true;
    static boolean collectFruitOnce = true;
    static int delayFruit = 1;

    public static boolean inChaseMode = false;
    public static boolean inScaredModeBlinky = false;
    public static boolean inScaredModePinky = false;
    public static boolean inScaredModeClyde = false;
    public static boolean inScaredModeInky = false;
    public static boolean inScatterMode = true;

    public static int scatterTime = 7000;
    public static int chaseTime = 20000;
    public static int scaredTime = 7000;
    public static int scatterCount = 0;
    public static int chaseCount = 0;

    public static int ghostsEaten = 0;

    /**
     * min. 3 word characters
     * max. 10 word characters + optional 3 digits at end
     */
    static final String regexpName = "\\w{3,10}" + "\\d{0,3}";

    /**
     * min. 8 characters
     * min. 1 digit
     * min. 1 lower & upper letter
     * min. one special character (@, #, $, %, ^, &, +, =, .)
     */
    static final String regexpPass = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.])(?=\\S+$).{8,}$";



    /**
     * checks if given Username is valid
     *
     * @param USERNAME username to validate
     * @return true or false
     */
    public static boolean isValidNickname(String USERNAME) {
        return Pattern.compile(regexpName).matcher(USERNAME).matches();
    }

    public static boolean isValidPassword(String PASSWORD) {
        return Pattern.compile(regexpPass).matcher(PASSWORD).matches();
    }


    public static void saveScore() {

        try {
            sqlConnection();


            Statement statement2 = connection.createStatement();
            ResultSet results2 = statement2.executeQuery("SELECT * FROM highscores ORDER BY score DESC LIMIT 1");

            while (results2.next()) {
                highscore = Integer.parseInt(results2.getString("score"));
            }

            // the mysql insert statement
            String query = "INSERT INTO highscores(pk_highscores, username, score)"
                    + " VALUES (?, ?, ?)";


            Statement queryPKCount = connection.createStatement();
            ResultSet pkCount = queryPKCount.executeQuery("SELECT COUNT(*) AS c FROM highscores");
            int pk = 0;
            if (pkCount.next()) {
                pk = Integer.parseInt(pkCount.getString("c"));
            }

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, pk);
            preparedStmt.setString(2, validUsername);
            preparedStmt.setInt(3, score);

            // execute the preparedstatement
            preparedStmt.execute();
            connection.close();

            // EATEN GHOSTS
            sqlConnection();

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM User WHERE name = '" + validUsername.toUpperCase() + "'");
            int eaten = 0;
            int pk2 = 0;
            while (results.next()) {
                pk2 = Integer.parseInt(results.getString("pk_user"));
                eaten = Integer.parseInt(results.getString("eatenGhosts"));
            }
            int eatenNew = eaten + ghostsEaten;


            String query2 = "UPDATE User SET eatenGhosts = " + eatenNew + " WHERE pk_user = " + pk2;
            PreparedStatement preparedStmt2 = connection.prepareStatement(query2);
            preparedStmt2.execute();
            connection.close();


            //ALLTIME SCORE
            sqlConnection();

            Statement statement3 = connection.createStatement();
            ResultSet results3 = statement3.executeQuery("SELECT * FROM User WHERE name = '" + validUsername.toUpperCase() + "'");
            int scoreOld = 0;
            int pk3 = 0;
            while (results3.next()) {
                pk3 = Integer.parseInt(results3.getString("pk_user"));
                scoreOld = Integer.parseInt(results3.getString("alltimeScore"));
            }
            int scoreNew = scoreOld + score;


            String query3 = "UPDATE User SET alltimeScore = " + scoreNew + " WHERE pk_user = " + pk3;
            PreparedStatement preparedStmt3 = connection.prepareStatement(query3);
            preparedStmt3.execute();
            connection.close();


            // GAMES PLAYED

            sqlConnection();

            Statement statement4 = connection.createStatement();
            ResultSet results4 = statement4.executeQuery("SELECT * FROM User WHERE name = '" + validUsername.toUpperCase() + "'");
            int gamesOld = 0;
            int pk4 = 0;
            while (results4.next()) {
                pk4 = Integer.parseInt(results4.getString("pk_user"));
                gamesOld = Integer.parseInt(results4.getString("gamesPlayed"));
            }

            int gamesNew = gamesOld;
            if (!gameStarted) {
                gamesNew++;
            }

            String query4 = "UPDATE User SET gamesPlayed = " + gamesNew + " WHERE pk_user = " + pk4;
            PreparedStatement preparedStmt4 = connection.prepareStatement(query4);
            preparedStmt4.execute();
            connection.close();


            // LEVELS CLEARED

            sqlConnection();

            Statement statement5 = connection.createStatement();
            ResultSet results5 = statement5.executeQuery("SELECT * FROM User WHERE name = '" + validUsername.toUpperCase() + "'");
            int levelsOld = 0;
            int pk5 = 0;
            while (results5.next()) {
                pk5 = Integer.parseInt(results5.getString("pk_user"));
                levelsOld = Integer.parseInt(results5.getString("finisehLevels"));
            }

            if (levelCounter == 0) {
                connection.close();
                return;
            }
            int levelsNew = levelsOld + levelCounter - 1;

            String query5 = "UPDATE User SET finisehLevels = " + levelsNew + " WHERE pk_user = " + pk5;
            PreparedStatement preparedStmt5 = connection.prepareStatement(query5);
            preparedStmt5.execute();
            connection.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets and draws the Starting Position of PacMan
     *
     * @param gameLayout Group Layout of the Game window
     */
    public static void setPacmanStartingPos(Group gameLayout) {
        viewPacmanLeft.setX((pacmanXPosStarting));
        viewPacmanLeft.setY((pacmanYPosStarting));
        viewPacmanLeft.setFitHeight(characterHeight);
        viewPacmanLeft.setFitWidth(characterWidth);

        gameLayout.getChildren().remove(viewPacmanUp);
        gameLayout.getChildren().remove(viewPacmanRight);
        gameLayout.getChildren().remove(viewPacmanLeft);
        gameLayout.getChildren().remove(viewPacmanDown);
        gameLayout.getChildren().add(viewPacmanLeft);

        isPacmanStartingPosVisible = false;
        allowNextMoveRight = true;
        allowNextMoveLeft = true;
    }


    /**
     * Resets the Game
     *
     * @param gameLayout Group Layout of the Game window
     */
    public static void resetGame(Group gameLayout) {

        saveScore();

        removeMap(gameLayout);
        gameLayout.getChildren().removeAll(viewCherry);
        firstRead = true;
        reset = true;
        score = 0;
        ghostsEaten = 0;
        lifesCounter = 3;
        lifesAtLevelStart = 3;
        levelCounter = 0;
        mapNumber = 0;
        wallCount = 0;
        dotCount = 0;
        dotCountAtStart = 0;
        powerPillCount = 0;
        railVerticalCount = 0;
        railHorizontalCount = 0;
        railUpRightCount = 0;
        railUpLeftCount = 0;
        railRightUpCount = 0;
        railLeftUpCount = 0;
        pacmanXPos = pacmanXPosStarting;
        pacmanYPos = pacmanYPosStarting;
        velocityPacmanHorizontal = 0;
        velocityPacmanVertical = 0;
        allowNextMoveDown = false;
        allowNextMoveUp = false;
        allowNextMoveRight = true;
        allowNextMoveLeft = true;
        fruitSpawned1 = false;
        fruitSpawned2 = false;
        getRandomLifespan = true;
        collectFruitOnce = true;
        application.mapReader.readMap();
        nextLevel = true;
        isPacmanStartingPosVisible = true;

        inScatterMode = true;
        inChaseMode = false;
        inScaredModeBlinky = false;
        inScaredModeClyde = false;
        inScaredModePinky = false;
        inScaredModeInky = false;

        scatterCount = 0;
        chaseCount = 0;
        drawLevelCounterOnce = true;
        drawLifesCounterOnce = true;
    }


    /**
     * Draws The Spawning Fruit
     * @param gameLayout Group Layout of the Game window
     */
    private static void drawFruit(Group gameLayout) {
        viewSpawningFruit = new ImageView(spawningFruit);
        viewSpawningFruit.setX(spawningFruitColumn * widthOneBlock + 2.5);
        viewSpawningFruit.setY(spawningFruitRow * heightOneBlock + 2.5);
        viewSpawningFruit.setFitWidth(widthOneBlock - 5);
        viewSpawningFruit.setFitHeight(heightOneBlock - 5);
        gameLayout.getChildren().remove(viewSpawningFruit);
        gameLayout.getChildren().add(viewSpawningFruit);
    }


    /**
     * Spawns Fruit after eating 70 and / or 170 dots
     *
     * @param gameLayout Group Layout of the Game window
     */
    public static void spawnFruit(Group gameLayout) {
        if (dotCount == dotCountAtStart - 70 && !fruitSpawned1) {
            drawFruit(gameLayout);
            collectableFruit = true;
            fruitSpawned1 = true;
        }
        if (dotCount == dotCountAtStart - 170 && !fruitSpawned2) {
            drawFruit(gameLayout);
            collectableFruit = true;
            fruitSpawned2 = true;
            collectFruitOnce = true;
        }
    }


    /**
     * Draws Life counter
     *
     * @param gameLayout Group Layout of the Game window
     */
    static boolean drawLifesCounterOnce = true;

    public static void drawLifesCounter(Group gameLayout) {
        if (drawLifesCounterOnce) {
            for (int i = 1; i <= lifesCounter; i++) {
                viewLifes = new ImageView(lifes);
                viewLifes.setX(i * (widthOneBlock + 10));
                viewLifes.setY((blockCountVertically - 1) * heightOneBlock);
                viewLifes.setFitWidth(widthOneBlock);
                viewLifes.setFitHeight(heightOneBlock);
                gameLayout.getChildren().removeAll(viewLifes);
                gameLayout.getChildren().add(viewLifes);
            }
            drawLifesCounterOnce = false;
        }
    }


    /**
     * draws Level counter
     *
     * @param gameLayout Group Layout of the Game window
     */

    static boolean drawLevelCounterOnce = true;

    public static void drawLevelCounter(Group gameLayout) {
        // TODO: More Level Icons

        if (drawLevelCounterOnce) {
            for (int i = 0; i < levelCounter; i++) {
                viewCherry = new ImageView(cherry);
                viewCherry.setX((blockCountHorizontally - i) * widthOneBlock);
                viewCherry.setY((blockCountVertically - 1) * heightOneBlock);
                viewCherry.setFitWidth(widthOneBlock);
                viewCherry.setFitHeight(heightOneBlock);
                gameLayout.getChildren().remove(viewCherry);
                gameLayout.getChildren().add(viewCherry);
            }
            drawLevelCounterOnce = false;
        }

    }


    /**
     * Removes Complete Map
     *
     * @param gameLayout Group Layout of the Game window
     */
    private static void removeMap(Group gameLayout) {
        if (reset) {
            // Remove Dots to Map
            for (int i = 0; i < dotCount; i++) {
                gameLayout.getChildren().remove(viewDot[i]);
            }

            // Remove Power Pills to Map
            for (int i = 0; i < powerPillCount; i++) {
                gameLayout.getChildren().remove(viewPowerPill[i]);
            }
        }
        // Remove Vertical Rails to Map
        for (int i = 0; i < railVerticalCount; i++) {
            gameLayout.getChildren().remove(viewRailVertical[i]);
        }

        // Remove Horizontal Rails to Map
        for (int i = 0; i < railHorizontalCount; i++) {
            gameLayout.getChildren().remove(viewRailHorizontal[i]);
        }

        // Remove Up Right Rails to Map
        for (int i = 0; i < railUpRightCount; i++) {
            gameLayout.getChildren().remove(viewRailUpRight[i]);
        }

        // Remove Up Left Rails to Map
        for (int i = 0; i < railUpLeftCount; i++) {
            gameLayout.getChildren().remove(viewRailUpLeft[i]);
        }

        // Remove Right Up Rails to Map
        for (int i = 0; i < railRightUpCount; i++) {
            gameLayout.getChildren().remove(viewRailRightUp[i]);
        }

        // Remove Left Up Rails to Map
        for (int i = 0; i < railLeftUpCount; i++) {
            gameLayout.getChildren().remove(viewRailLeftUp[i]);
        }
    }

    /**
     * Draws Next Map
     *
     * @param gameLayout Group Layout of the Game window
     */
    private static void drawNextMap(Group gameLayout) {

        if (reset) {
            // Add Dots to Map
            for (int i = 0; i < dotCount; i++) {
                gameLayout.getChildren().remove(viewDot[i]);
                gameLayout.getChildren().add(viewDot[i]);
            }

            // Add Power Pills to Map
            for (int i = 0; i < powerPillCount; i++) {
                gameLayout.getChildren().remove(viewPowerPill[i]);
                gameLayout.getChildren().add(viewPowerPill[i]);
            }
        }

        // Add Vertical Rails to Map
        for (int i = 0; i < railVerticalCount; i++) {
            gameLayout.getChildren().remove(viewRailVertical[i]);
            gameLayout.getChildren().add(viewRailVertical[i]);
        }

        // Add Horizontal Rails to Map
        for (int i = 0; i < railHorizontalCount; i++) {
            gameLayout.getChildren().remove(viewRailHorizontal[i]);
            gameLayout.getChildren().add(viewRailHorizontal[i]);
        }

        // Add Up Right Rails to Map
        for (int i = 0; i < railUpRightCount; i++) {
            gameLayout.getChildren().remove(viewRailUpRight[i]);
            gameLayout.getChildren().add(viewRailUpRight[i]);
        }

        // Add Up Left Rails to Map
        for (int i = 0; i < railUpLeftCount; i++) {
            gameLayout.getChildren().remove(viewRailUpLeft[i]);
            gameLayout.getChildren().add(viewRailUpLeft[i]);
        }

        // Add Right Up Rails to Map
        for (int i = 0; i < railRightUpCount; i++) {
            gameLayout.getChildren().remove(viewRailRightUp[i]);
            gameLayout.getChildren().add(viewRailRightUp[i]);
        }

        // Add Left Up Rails to Map
        for (int i = 0; i < railLeftUpCount; i++) {
            gameLayout.getChildren().remove(viewRailLeftUp[i]);
            gameLayout.getChildren().add(viewRailLeftUp[i]);
        }

        //Setting the position of the image
        viewBlinky.setX((blinkyColumnStart * widthOneBlock) + (int) ((widthOneBlock - characterWidth) / 2));
        viewBlinky.setY((blinkyRowStart * heightOneBlock) + (int) ((heightOneBlock - characterHeight) / 2));

        //setting the fit height and width of the image view
        viewBlinky.setFitHeight(characterHeight);
        viewBlinky.setFitWidth(characterWidth);

        //Setting the position of the image
        viewPinky.setX((pinkyColumnStart * widthOneBlock) + (int) ((widthOneBlock - characterWidth) / 2));
        viewPinky.setY((pinkyRowStart * heightOneBlock) + (int) ((heightOneBlock - characterHeight) / 2));

        //setting the fit height and width of the image view
        viewPinky.setFitHeight(characterHeight);
        viewPinky.setFitWidth(characterWidth);

        //Setting the position of the image
        viewClyde.setX((clydeColumnStart * widthOneBlock) + (int) ((widthOneBlock - characterWidth) / 2));
        viewClyde.setY((clydeRowStart * heightOneBlock) + (int) ((heightOneBlock - characterHeight) / 2));

        //setting the fit height and width of the image view
        viewClyde.setFitHeight(characterHeight);
        viewClyde.setFitWidth(characterWidth);

        //Setting the position of the image
        viewInky.setX((inkyColumnStart * widthOneBlock) + (int) ((widthOneBlock - characterWidth) / 2));
        viewInky.setY((inkyRowStart * heightOneBlock) + (int) ((heightOneBlock - characterHeight) / 2));

        //setting the fit height and width of the image view
        viewInky.setFitHeight(characterHeight);
        viewInky.setFitWidth(characterWidth);

        if (!inScaredModeBlinky) {
            gameLayout.getChildren().addAll(viewBlinky, viewPinky, viewClyde, viewInky);
        }
    }


    /**
     * Sets the next level
     * @param gameLayout Group Layout of the Game window
     */

    static int mapNumber = 0;
    static int maxLevel = 255;

    public static void levelUp(Group gameLayout) {
        if (dotCount == 0 && powerPillCount == 0) nextLevel = true;
        if (nextLevel) {
            levelCounter++;
            mapNumber++;
            if (levelCounter == maxLevel) return;
            if (mapNumber > maxMaps) mapNumber = 1;
            mapFile = "resources/levels/level" + mapNumber + ".txt";
            removeMap(gameLayout);
            gameLayout.getChildren().removeAll(viewBlinky, viewPinky, viewClyde, viewInky);
            reset = true;
            nextLevel = false;
            firstRead = true;
            lifesAtLevelStart = 3;
            wallCount = 0;
            dotCount = 0;
            dotCountAtStart = 0;
            powerPillCount = 0;
            railVerticalCount = 0;
            railHorizontalCount = 0;
            railUpRightCount = 0;
            railUpLeftCount = 0;
            railRightUpCount = 0;
            railLeftUpCount = 0;
            pacmanXPos = pacmanXPosStarting;
            pacmanYPos = pacmanYPosStarting;
            velocityPacmanHorizontal = 0;
            velocityPacmanVertical = 0;
            allowNextMoveDown = false;
            allowNextMoveUp = false;
            allowNextMoveRight = true;
            allowNextMoveLeft = true;
            fruitSpawned1 = false;
            fruitSpawned2 = false;
            getRandomLifespan = true;
            collectFruitOnce = true;
            inScatterMode = true;
            inChaseMode = false;
            inScaredModeBlinky = false;
            inScaredModePinky = false;
            inScaredModeClyde = false;
            inScaredModeInky = false;

            scatterCount = 0;
            chaseCount = 0;
            Ghost.chaseTimer.cancel();
            Ghost.chaseTimer = new Timer();
            Ghost.scatterTimer.cancel();
            Ghost.scatterTimer = new Timer();
            Ghost.velocityBlinkyVertical = -1;
            Ghost.velocityBlinkyHorizontal = 0;
            Ghost.velocityPinkyVertical = -1;
            Ghost.velocityPinkyHorizontal = 0;
            Ghost.velocityInkyVertical = -1;
            Ghost.velocityInkyHorizontal = 0;
            Ghost.velocityClydeVertical = -1;
            Ghost.velocityClydeHorizontal = 0;

            drawLevelCounterOnce = true;
            drawLifesCounterOnce = true;

            mapReader.readMap();
            drawNextMap(gameLayout);
        }
    }


    /**
     * Makes Spawning Fruit collectable
     * @param gameLayout Group Layout of the Game window
     */
    public static void collectFruit(Group gameLayout) {
        if (!collectableFruit) {
            gameLayout.getChildren().remove(viewSpawningFruit);
            getRandomLifespan = true;
            return;
        }
        if ((pacmanColumn == spawningFruitColumn) && (pacmanRow == spawningFruitRow)) {
            score += 100;       // Fruit gives 100 Points
            // TODO: Fruit gives points depending on current Level

            collectableFruit = false;
            gameLayout.getChildren().remove(viewSpawningFruit);
            return;
        }

        // Get Random Lifespan of the Fruit
        if (getRandomLifespan) {
            delayFruit = (int) (Math.random() * (10000 - 9000 + 1)) + 9000;
            getRandomLifespan = false;
        }

        // Timer
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (collectFruitOnce) {
                            collectableFruit = false;
                            collectFruitOnce = false;
                        }
                    }
                },
                delayFruit
        );
    }

    /**
     * Hides Dots and Power Pills
     * @param gameLayout Group Layout of the Game window
     */
    private static void clearer(Group gameLayout) {
        viewClearer = new ImageView(clearer);
        viewClearer.setFitWidth(widthOneBlock - 5);
        viewClearer.setFitHeight(heightOneBlock - 5);
        viewClearer.setX(pacmanColumn * widthOneBlock + 2.5);
        viewClearer.setY(pacmanRow * heightOneBlock + 2.5);

        gameLayout.getChildren().add(viewClearer);

        // Override with Pac-Man
        if (pacmanFacingRight) {
            gameLayout.getChildren().remove(viewPacmanRight);
            gameLayout.getChildren().add(viewPacmanRight);
        }
        if (pacmanFacingLeft) {
            gameLayout.getChildren().remove(viewPacmanLeft);
            gameLayout.getChildren().add(viewPacmanLeft);
        }
        if (pacmanFacingUp) {
            gameLayout.getChildren().remove(viewPacmanUp);
            gameLayout.getChildren().add(viewPacmanUp);
        }
        if (pacmanFacingDown) {
            gameLayout.getChildren().remove(viewPacmanDown);
            gameLayout.getChildren().add(viewPacmanDown);
        }
        gameLayout.getChildren().remove(viewBlinky);
        if (!inScaredModeBlinky) {
            gameLayout.getChildren().add(viewBlinky);
        }
    }

    /**
     * Makes Dots collectable
     * @param gameLayout Group Layout of the Game window
     */
    public static void collectPoints(Group gameLayout) {
        if (!dots[(int) pacmanColumn][(int) pacmanRow]) return;

        dots[(int) pacmanColumn][(int) pacmanRow] = false;
        dotCount--;
        score += 10;    // A dot is worth 10 Points
        clearer(gameLayout);

    }


    /**
     * Makes Power Pills collectable
     * @param gameLayout Group Layout of the Game window
     */
    public static boolean pacmanInPowerMode = false;

    public static void collectPowerPill(Group gameLayout) {
        if (!powerPills[(int) pacmanColumn][(int) pacmanRow]) return;
        powerPills[(int) pacmanColumn][(int) pacmanRow] = false;
        powerPillCount--;
        score += 50;        // A Power Pill gives 50 points
        inChaseMode = false;
        pacmanInPowerMode = true;
        pacmanPowerModeBlinky(gameLayout);
        pacmanPowerModePinky(gameLayout);
        pacmanPowerModeClyde(gameLayout);
        pacmanPowerModeInky(gameLayout);
        clearer(gameLayout);
    }


    private static void pacmanPowerModeBlinky(Group gameLayout) {
        gameLayout.getChildren().remove(viewBlinky);
        inScaredModeBlinky = true;
        // Timer
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (inScaredModeBlinky) {
                            inScaredModeBlinky = false;
                            inScatterMode = true;
                        }
                    }
                },
                scaredTime
        );
    }




    private static void pacmanPowerModePinky(Group gameLayout) {
        gameLayout.getChildren().remove(viewPinky);
        inScaredModePinky = true;
        // Timer
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (inScaredModePinky) {
                            inScaredModePinky = false;
                            inScatterMode = true;
                        }
                    }
                },
                scaredTime
        );
    }

    private static void pacmanPowerModeClyde(Group gameLayout) {
        gameLayout.getChildren().remove(viewClyde);
        inScaredModeClyde = true;
        // Timer
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (inScaredModeClyde) {
                            inScaredModeClyde = false;
                            inScatterMode = true;
                        }
                    }
                },
                scaredTime
        );
    }

    private static void pacmanPowerModeInky(Group gameLayout) {
        gameLayout.getChildren().remove(viewInky);
        inScaredModeInky = true;
        // Timer
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (inScaredModeInky) {
                            inScaredModeInky = false;
                            inScatterMode = true;
                        }
                    }
                },
                scaredTime
        );
    }


    private static boolean pacmanIsAlive = true;

    public static void pacmanDeath(Group gameLayout, GraphicsContext gcGame) {

        if (lifesCounter == -1) {
            gcGame.setFill(Color.YELLOW);
            gcGame.fillText("Game Over", (blockCountHorizontally / 2) * widthOneBlock, blockCountVertically * heightOneBlock);
            gcGame.fillText("Press Esc to leave", (blockCountHorizontally / 2) * widthOneBlock, (blockCountVertically + 2) * heightOneBlock);
            tl.pause();
        }

        if ((!inScaredModeBlinky) && (!inScaredModePinky) && (!inScaredModeClyde)) {
            pacmanInPowerMode = false;
        }

        if ((!pacmanInPowerMode) && pacmanIsAlive && ((pacmanColumn == blinkyColumn && pacmanRow == blinkyRow) || (pacmanColumn == pinkyColumn && pacmanRow == pinkyRow) || (pacmanColumn == clydeColumn && pacmanRow == clydeRow) || (pacmanColumn == inkyColumn && pacmanRow == inkyRow))) {

            reset = false;
            pacmanXPos = pacmanXPosStarting;
            pacmanYPos = pacmanYPosStarting;

            removeMap(gameLayout);
            gameLayout.getChildren().removeAll(viewCherry, viewBlinky, viewPinky, viewClyde, viewInky);

            firstRead = true;
            lifesCounter -= 1;
            pacmanIsAlive = false;

            wallCount = 0;
            railVerticalCount = 0;
            railHorizontalCount = 0;
            railUpRightCount = 0;
            railUpLeftCount = 0;
            railRightUpCount = 0;
            railLeftUpCount = 0;

            velocityPacmanHorizontal = 0;
            velocityPacmanVertical = 0;

            allowNextMoveDown = false;
            allowNextMoveUp = false;
            allowNextMoveRight = true;
            allowNextMoveLeft = true;

            fruitSpawned1 = false;
            fruitSpawned2 = false;

            getRandomLifespan = true;
            collectFruitOnce = true;

            application.mapReader.readMap();

            drawNextMap(gameLayout);
            drawLifesCounter(gameLayout);
            deleteLifeCounter(gameLayout);

            isPacmanStartingPosVisible = true;

            inScatterMode = true;
            inChaseMode = false;
            inScaredModeBlinky = false;
            inScaredModePinky = false;
            inScaredModeClyde = false;
            inScaredModeInky = false;

            Ghost.velocityBlinkyVertical = -1;
            Ghost.velocityBlinkyHorizontal = 0;
            Ghost.velocityPinkyVertical = -1;
            Ghost.velocityPinkyHorizontal = 0;
            Ghost.velocityInkyVertical = -1;
            Ghost.velocityInkyHorizontal = 0;
            Ghost.velocityClydeVertical = -1;
            Ghost.velocityClydeHorizontal = 0;

            scatterCount = 0;
            chaseCount = 0;
            drawLevelCounterOnce = true;
            pacmanIsAlive = true;
        }
    }


    private static void deleteLifeCounter(Group gameLayout) {
        viewClearer = new ImageView(clearer);
        viewClearer.setX((lifesCounter + 1) * (widthOneBlock + 10));
        viewClearer.setY((blockCountVertically - 1) * heightOneBlock);
        viewClearer.setFitWidth(widthOneBlock);
        viewClearer.setFitHeight(heightOneBlock);
        gameLayout.getChildren().removeAll(viewClearer);
        gameLayout.getChildren().add(viewClearer);
    }

    /**
     * Checks if UP move is possible
     * @param direction direction Pacman is heading (1 = going Right, -1 = going Left)
     */
    private static void checkUpPossible(int direction) {
        if (!notAllowedBox[(int) pacmanColumn + direction][(int) pacmanRow - 1]) {
            if (waitingForTurn == 'u' && !stop) {
                stop = true;
                pacmanXPosCenter = (pacmanColumn * widthOneBlock) + widthOneBlock * direction;
                pacmanYPosCenter = (pacmanRow * heightOneBlock);
                allowNextMoveUp = true;
            }
        }
    }

    /**
     * Checks if DOWN move is possible
     * @param direction direction Pacman is heading (1 = going Right, -1 = going Left)
     */
    private static void checkDownPossible(int direction) {
        if (!notAllowedBox[(int) pacmanColumn + direction][(int) pacmanRow + 1]) {
            if (waitingForTurn == 'd' && !stop) {
                stop = true;
                pacmanXPosCenter = (pacmanColumn * widthOneBlock) + widthOneBlock * direction;
                pacmanYPosCenter = (pacmanRow * heightOneBlock);
                allowNextMoveUp = true;
            }
        }
    }

    /**
     * Checks if LEFT move is possible
     * @param direction direction Pacman is heading (1 = going Down, -1 = going Up)
     */
    private static void checkLeftPossible(int direction) {
        if (!notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow + direction]) {
            if (waitingForTurn == 'l' && !stop) {
                stop = true;
                pacmanXPosCenter = (pacmanColumn * widthOneBlock);
                pacmanYPosCenter = (pacmanRow * heightOneBlock) + heightOneBlock * direction;
                allowNextMoveLeft = true;
            }
        }
    }

    /**
     * Checks if RIGHT move is possible
     * @param direction direction Pacman is heading (1 = going Down, -1 = going Up)
     */
    private static void checkRightPossible(int direction) {
        if (!notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow + direction]) {
            if (waitingForTurn == 'r' && !stop) {
                stop = true;
                pacmanXPosCenter = (pacmanColumn * widthOneBlock);
                pacmanYPosCenter = (pacmanRow * heightOneBlock) + heightOneBlock * direction;
                allowNextMoveRight = true;
            }
        }
    }

    /**
     * Turns Pacman UP
     * @param gameLayout Group Layout of the Game window
     */
    private static void turnPacmanUp(Group gameLayout) {
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

        gameLayout.getChildren().addAll(viewPacmanUp);

        pacmanFacingUp = true;
        pacmanFacingDown = false;
        pacmanFacingLeft = false;
        pacmanFacingRight = false;
        velocityPacmanHorizontal = 0;
        velocityPacmanVertical = -1 - velocityAdder;

        hitUpWall = false;
        stop = false;
        waitingForTurn = '1';
    }

    /**
     * Turns Pacman DOWN
     * @param gameLayout Group Layout of the Game window
     */
    private static void turnPacmanDown(Group gameLayout) {
        //Setting the position of the image
        viewPacmanDown.setX((pacmanXPos));
        viewPacmanDown.setY((pacmanYPos));

        //setting the fit height and width of the image view
        viewPacmanDown.setFitHeight(characterHeight);
        viewPacmanDown.setFitWidth(characterWidth);

        gameLayout.getChildren().remove(viewPacmanRight);
        gameLayout.getChildren().remove(viewPacmanUp);
        gameLayout.getChildren().remove(viewPacmanLeft);
        gameLayout.getChildren().remove(viewPacmanDown);

        gameLayout.getChildren().addAll(viewPacmanDown);

        pacmanFacingUp = false;
        pacmanFacingDown = true;
        pacmanFacingLeft = false;
        pacmanFacingRight = false;
        velocityPacmanHorizontal = 0;
        velocityPacmanVertical = 1 + velocityAdder;

        hitDownWall = false;
        stop = false;
        waitingForTurn = '1';
    }

    /**
     * Turns Pacman LEFT
     * @param gameLayout Group Layout of the Game window
     */
    private static void turnPacmanLeft(Group gameLayout) {
        //Setting the position of the image
        viewPacmanLeft.setX((pacmanXPos));
        viewPacmanLeft.setY((pacmanYPos));

        //setting the fit height and width of the image view
        viewPacmanLeft.setFitHeight(characterHeight);
        viewPacmanLeft.setFitWidth(characterWidth);

        gameLayout.getChildren().remove(viewPacmanRight);
        gameLayout.getChildren().remove(viewPacmanUp);
        gameLayout.getChildren().remove(viewPacmanLeft);
        gameLayout.getChildren().remove(viewPacmanDown);

        gameLayout.getChildren().addAll(viewPacmanLeft);

        pacmanFacingUp = false;
        pacmanFacingDown = false;
        pacmanFacingLeft = true;
        pacmanFacingRight = false;
        velocityPacmanHorizontal = -1 - velocityAdder;
        velocityPacmanVertical = 0;

        hitLeftWall = false;
        stop = false;
        waitingForTurn = '1';
    }

    /**
     * Turns Pacman RIGHT
     * @param gameLayout Group Layout of the Game window
     */
    private static void turnPacmanRight(Group gameLayout) {
        //Setting the position of the image
        viewPacmanRight.setX((pacmanXPos));
        viewPacmanRight.setY((pacmanYPos));

        //setting the fit height and width of the image view
        viewPacmanRight.setFitHeight(characterHeight);
        viewPacmanRight.setFitWidth(characterWidth);

        gameLayout.getChildren().remove(viewPacmanRight);
        gameLayout.getChildren().remove(viewPacmanUp);
        gameLayout.getChildren().remove(viewPacmanLeft);
        gameLayout.getChildren().remove(viewPacmanDown);

        gameLayout.getChildren().addAll(viewPacmanRight);

        pacmanFacingUp = false;
        pacmanFacingDown = false;
        pacmanFacingLeft = false;
        pacmanFacingRight = true;
        velocityPacmanHorizontal = 1 + velocityAdder;
        velocityPacmanVertical = 0;

        hitRightWall = false;
        stop = false;
        waitingForTurn = '1';
    }


    /**
     * Checks if horizontal Wall got Hit
     * @param MovingDirection Pacmans moving direction
     */
    private static void horizontalWallHit(String MovingDirection) {
        velocityPacmanHorizontal = 0;
        velocityPacmanVertical = 0;
        waitingForTurn = '1';

        pacmanFacingLeft = false;
        pacmanFacingRight = false;
        pacmanFacingDown = false;
        pacmanFacingUp = false;

        if (!notAllowedBox[(int) pacmanColumn][(int) pacmanRow - 1]) allowNextMoveUp = true;
        if (!notAllowedBox[(int) pacmanColumn][(int) pacmanRow + 1]) allowNextMoveDown = true;

        switch (MovingDirection) {
            case "LEFT" -> allowNextMoveLeft = false;
            case "RIGHT" -> allowNextMoveRight = false;
        }
    }

    /**
     * Checks if vertical Wall got Hit
     * @param MovingDirection Pacmans moving direction
     */
    private static void verticalWallHit(String MovingDirection) {
        velocityPacmanHorizontal = 0;
        velocityPacmanVertical = 0;
        waitingForTurn = '1';

        pacmanFacingLeft = false;
        pacmanFacingRight = false;
        pacmanFacingDown = false;
        pacmanFacingUp = false;

        if (!notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow]) allowNextMoveLeft = true;
        if (!notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow]) allowNextMoveRight = true;

        switch (MovingDirection) {
            case "UP" -> allowNextMoveUp = false;
            case "DOWN" -> allowNextMoveDown = false;
        }
    }


    /**
     * Allows Moving Pacman
     * @param gameLayout Group Layout of the Game window
     */
    public static void pacmanMove(Group gameLayout) {
        pacmanRow = (int) Math.round(pacmanYPos / widthOneBlock);
        pacmanColumn = (int) Math.round((pacmanXPos / heightOneBlock));

        if (pacmanFacingRight) {
            checkUpPossible(1);
            checkDownPossible(1);
            if (!stop) {
                // Teleport Right to Left
                if (pacmanColumn + 1 > blockCountHorizontally) pacmanXPos = widthOneBlock;
                // Check if Wall got Hit
                if (notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow] && !hitRightWall) {
                    hitRightWall = true;
                    pacmanXPosCenter = pacmanXPos + (int) (characterWidth / 2);
                    pacmanYPosCenter = pacmanYPos;
                }
                if (!hitRightWall) {
                    allowNextMoveLeft = true;
                    allowNextMoveUp = false;
                    allowNextMoveDown = false;
                    allowNextMoveRight = true;
                    viewPacmanRight.setX(pacmanXPos);
                    viewPacmanRight.setY(pacmanYPos);
                    pacmanXPos += velocityPacmanHorizontal;
                    return;
                }
                if (pacmanXPos >= pacmanXPosCenter) horizontalWallHit("RIGHT");
            }
            if (pacmanXPos >= pacmanXPosCenter) {        // Move a little bit further
                switch (waitingForTurn) {
                    case 'u' -> turnPacmanUp(gameLayout);
                    case 'd' -> turnPacmanDown(gameLayout);
                }
                return;
            }
            viewPacmanRight.setX(pacmanXPos);
            viewPacmanRight.setY(pacmanYPos);
            pacmanXPos += velocityPacmanHorizontal;
            return;
        }


        if (pacmanFacingLeft) {
            checkUpPossible(-1);
            checkDownPossible(-1);
            if (!stop) {
                // Teleport Left to Right
                if ((int) pacmanColumn - 2 < 0) pacmanXPos = blockCountHorizontally * widthOneBlock;
                // Check if left Wall hit
                if (notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow] && !hitLeftWall) {
                    hitLeftWall = true;
                    pacmanXPosCenter = pacmanXPos - (int) (characterWidth / 2);
                    pacmanYPosCenter = pacmanYPos;
                }
                if (!hitLeftWall) {
                    allowNextMoveLeft = true;
                    allowNextMoveUp = false;
                    allowNextMoveDown = false;
                    allowNextMoveRight = true;

                    viewPacmanLeft.setX(pacmanXPos);
                    viewPacmanLeft.setY(pacmanYPos);
                    pacmanXPos += velocityPacmanHorizontal;
                    return;
                }
                if (pacmanXPos <= pacmanXPosCenter) horizontalWallHit("LEFT");
            }
            if (pacmanXPos <= pacmanXPosCenter) {
                switch (waitingForTurn) {
                    case 'u' -> turnPacmanUp(gameLayout);
                    case 'd' -> turnPacmanDown(gameLayout);
                }
                return;
            }
            viewPacmanLeft.setX(pacmanXPos);
            viewPacmanLeft.setY(pacmanYPos);
            pacmanXPos += velocityPacmanHorizontal;
            return;
        }


        if (pacmanFacingUp) {
            checkLeftPossible(-1);
            checkRightPossible(-1);
            if (!stop) {
                // Check if Up Wall hit
                if (notAllowedBox[(int) pacmanColumn][(int) pacmanRow - 1] && !hitUpWall) {
                    hitUpWall = true;
                    pacmanXPosCenter = pacmanXPos;
                    pacmanYPosCenter = pacmanYPos - (int) (characterWidth / 2);
                }
                if (!hitUpWall) {
                    allowNextMoveLeft = false;
                    allowNextMoveUp = false;
                    allowNextMoveDown = true;
                    allowNextMoveRight = false;
                    viewPacmanUp.setX(pacmanXPos);
                    viewPacmanUp.setY(pacmanYPos);
                    pacmanYPos += velocityPacmanVertical;
                    return;
                }
                if (pacmanYPos <= pacmanYPosCenter) verticalWallHit("UP");
            }
            if (pacmanYPos <= pacmanYPosCenter) {
                switch (waitingForTurn) {
                    case 'l' -> turnPacmanLeft(gameLayout);
                    case 'r' -> turnPacmanRight(gameLayout);
                }
                return;
            }
            viewPacmanUp.setX(pacmanXPos);
            viewPacmanUp.setY(pacmanYPos);
            pacmanYPos += velocityPacmanVertical;
            return;
        }


        if (pacmanFacingDown) {
            checkLeftPossible(1);
            checkRightPossible(1);
            if (!stop) {
                // Check down Wall hit
                if (notAllowedBox[(int) pacmanColumn][(int) pacmanRow + 1] && !hitDownWall) {
                    hitDownWall = true;
                    pacmanXPosCenter = pacmanXPos;
                    pacmanYPosCenter = pacmanYPos + (int) (characterWidth / 2);
                }
                if (!hitDownWall) {
                    allowNextMoveLeft = false;
                    allowNextMoveUp = true;
                    allowNextMoveDown = false;
                    allowNextMoveRight = false;

                    viewPacmanDown.setX(pacmanXPos);
                    viewPacmanDown.setY(pacmanYPos);
                    pacmanYPos += velocityPacmanVertical;
                    return;
                }
                if (pacmanYPos >= pacmanYPosCenter) verticalWallHit("DOWN");
            }
            if (pacmanYPos >= pacmanYPosCenter) {
                switch (waitingForTurn) {
                    case 'l' -> turnPacmanLeft(gameLayout);
                    case 'r' -> turnPacmanRight(gameLayout);
                }
                return;
            }
            viewPacmanDown.setX(pacmanXPos);
            viewPacmanDown.setY(pacmanYPos);
            pacmanYPos += velocityPacmanVertical;
        }
    }
}

