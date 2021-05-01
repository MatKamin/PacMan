package application;


//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.regex.Pattern;

import static application.imageViewerVariables.*;
import static application.main.*;
import static application.mapReader.*;


//---------------------------------CLASS---------------------------------\\

public class gameMechanics {

    //---------------------------------VARIABLES---------------------------------\\

    private static boolean nextLevel = true;
    public static int score = 0;    // player score
    public static int highscore;    // Highest score

    private static final int maxLevel = 2;

    static String mapFile = "resources/levels/level1.txt";

    static int levelCounter = 0;
    static final int startingLevel = 1;

    static boolean firstRead = true;

    static boolean[][] dots = new boolean[blockCountHorizontally + 1][blockCountVertically];
    static boolean[][] powerPills = new boolean[blockCountHorizontally + 1][blockCountVertically];
    static boolean[][] notAllowedBox = new boolean[blockCountHorizontally + 1][blockCountVertically];

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
    public static boolean inScaredMode = false;
    public static boolean inScatterMode = true;

    public static int scatterTime = 7000;
    public static int chaseTime = 20000;
    public static int scaredTime = 7000;
    public static int scatterCount = 0;
    public static int chaseCount = 0;


    /**
     * checks if given Username is valid
     *
     * @param USERNAME username to validate
     * @return true or false
     */
    public static boolean isValidNickname(String USERNAME) {
        return Pattern.compile(regexp).matcher(USERNAME).matches();
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

        // Save Score
        Scanner sc = null;
        try {
            sc = new Scanner(new File("resources/highscores.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert sc != null;
        sc.useDelimiter(",empty,0");
        String old = sc.next();
        String newScore = "," + validUsername + "," + score;
        String paste = old + newScore;

        paste = paste.replaceAll("," + validUsername + ",0", "");

        try {
            FileWriter writer = new FileWriter("resources/highscores.txt", false);

            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(paste);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        removeMap(gameLayout);
        gameLayout.getChildren().removeAll(viewCherry);
        firstRead = true;
        score = 0;
        lifesCounter = 3;
        lifesAtLevelStart = 3;
        levelCounter = 0;
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
        mapReader.readMap();
        nextLevel = true;
        isPacmanStartingPosVisible = true;

        inScatterMode = true;
        inChaseMode = false;
        inScaredMode = false;
    }


    /**
     * Draws The Spawning Fruit
     *
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
    public static void drawLifesCounter(Group gameLayout) {
        if (lifesCounter != lifesAtLevelStart) {
            for (int i = 1; i <= lifesCounter; i++) {
                viewLifes = new ImageView(lifes);
                viewLifes.setX(i * (widthOneBlock + 10));
                viewLifes.setY(blockCountVertically * heightOneBlock);
                viewLifes.setFitWidth(widthOneBlock);
                viewLifes.setFitHeight(heightOneBlock);
                gameLayout.getChildren().addAll(viewLifes);
            }
            lifesAtLevelStart--;
        }
    }


    /**
     * draws Level counter
     *
     * @param gameLayout Group Layout of the Game window
     */
    public static void drawLevelCounter(Group gameLayout) {
        // TODO: More Level Icons

        if (levelCounter != startingLevel) {
            viewCherry = new ImageView(cherry);
            viewCherry.setX((blockCountHorizontally - 2) * widthOneBlock);
            viewCherry.setY(blockCountVertically * heightOneBlock);
            viewCherry.setFitWidth(widthOneBlock);
            viewCherry.setFitHeight(heightOneBlock);
            gameLayout.getChildren().remove(viewCherry);
            gameLayout.getChildren().add(viewCherry);
        }
    }


    /**
     * Removes Complete Map
     *
     * @param gameLayout Group Layout of the Game window
     */
    private static void removeMap(Group gameLayout) {
        // Remove Dots to Map
        for (int i = 0; i < dotCount; i++) {
            gameLayout.getChildren().remove(viewDot[i]);
        }

        // Remove Power Pills to Map
        for (int i = 0; i < powerPillCount; i++) {
            gameLayout.getChildren().remove(viewPowerPill[i]);
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
        viewBlinky.setX((blinkyColumn * widthOneBlock) + (int) ((widthOneBlock - characterWidth) / 2));
        viewBlinky.setY((blinkyRow * heightOneBlock) + (int) ((heightOneBlock - characterHeight) / 2));

        //setting the fit height and width of the image view
        viewBlinky.setFitHeight(characterHeight);
        viewBlinky.setFitWidth(characterWidth);

        //Setting the position of the image
        viewPinky.setX((pinkyColumn * widthOneBlock) + (int) ((widthOneBlock - characterWidth) / 2));
        viewPinky.setY((pinkyRow * heightOneBlock) + (int) ((heightOneBlock - characterHeight) / 2));

        //setting the fit height and width of the image view
        viewPinky.setFitHeight(characterHeight);
        viewPinky.setFitWidth(characterWidth);

        if (!inScaredMode) {
            gameLayout.getChildren().addAll(viewBlinky, viewPinky);
        }
    }


    /**
     * Sets the next level
     *
     * @param gameLayout Group Layout of the Game window
     */
    public static void levelUp(Group gameLayout) {
        if (dotCount == 0) nextLevel = true;
        if (nextLevel) {
            levelCounter++;
            if (levelCounter > maxLevel) return;
            mapFile = "resources/levels/level" + levelCounter + ".txt";

            removeMap(gameLayout);
            gameLayout.getChildren().removeAll(viewBlinky, viewPinky);
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
            inScaredMode = false;

            mapReader.readMap();
            drawNextMap(gameLayout);
        }
    }


    /**
     * Makes Spawning Fruit collectable
     *
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
     *
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
        if (!inScaredMode) {
            gameLayout.getChildren().add(viewBlinky);
        }
    }

    /**
     * Makes Dots collectable
     *
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
     *
     * @param gameLayout Group Layout of the Game window
     */
    public static void collectPowerPill(Group gameLayout) {
        if (!powerPills[(int) pacmanColumn][(int) pacmanRow]) return;
        powerPills[(int) pacmanColumn][(int) pacmanRow] = false;
        powerPillCount--;
        score += 50;        // A Power Pill gives 50 points
        inChaseMode = false;
        pacmanPowerMode(gameLayout);
        clearer(gameLayout);
    }


    private static void pacmanPowerMode(Group gameLayout) {
        gameLayout.getChildren().remove(viewBlinky);
        inScaredMode = true;
        // Timer
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (inScaredMode) {
                            inScaredMode = false;
                            inChaseMode = true;
                        }
                    }
                },
                scaredTime
        );
    }


    public static void scatterModeTimer() {
        Timer t = new Timer();

        inScatterMode = true;

        t.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (inScatterMode) {
                            if (chaseCount < 5) {
                                inScatterMode = false;
                                System.out.println("SWITCHED TO CHASE MODE AFTER: " + scatterTime + "ms                " + scatterCount);
                                scatterCount++;
                                chaseModeTimer();
                            }
                        }
                        t.cancel();
                    }
                },
                scatterTime
        );
    }


    public static void chaseModeTimer() {
        Timer t = new Timer();
        inChaseMode = true;

        t.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (inChaseMode) {
                            if (scatterCount < 4) {
                                inChaseMode = false;
                                System.out.println("SWITCHED TO SCATTER MODE AFTER " + chaseTime + "ms          " + chaseCount);
                                chaseCount++;
                                scatterModeTimer();
                            }
                        }
                        t.cancel();
                    }
                },
                chaseTime
        );
    }


    public static void eatGhost(Group gameLayout) {
        if (!inScaredMode) {
            return;
        }

        if (pacmanColumn == blinkyColumn && pacmanRow == blinkyRow) {
            inScaredMode = false;
            inChaseMode = true;

            blinkyXPos = (widthOneBlock * blinkyColumnStart);
            blinkyYPos = (heightOneBlock * blinkyRowStart);
            blinkyColumn = blinkyColumnStart;
            blinkyRow = blinkyRowStart;

            viewBlinky.setX(blinkyXPos);
            viewBlinky.setY(blinkyYPos);

            gameLayout.getChildren().remove(viewScared);

            score += 200;
        }
    }


    /**
     * Checks if UP move is possible
     *
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
     *
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
     *
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
     *
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
     *
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
        velocityPacmanVertical = -1;

        hitUpWall = false;
        stop = false;
        waitingForTurn = '1';
    }

    /**
     * Turns Pacman DOWN
     *
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
        velocityPacmanVertical = 1;

        hitDownWall = false;
        stop = false;
        waitingForTurn = '1';
    }

    /**
     * Turns Pacman LEFT
     *
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
        velocityPacmanHorizontal = -1;
        velocityPacmanVertical = 0;

        hitLeftWall = false;
        stop = false;
        waitingForTurn = '1';
    }

    /**
     * Turns Pacman RIGHT
     *
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
        velocityPacmanHorizontal = 1;
        velocityPacmanVertical = 0;

        hitRightWall = false;
        stop = false;
        waitingForTurn = '1';
    }


    /**
     * Checks if horizontal Wall got Hit
     *
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
     *
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
     *
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

