package application;


//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.Scanner;
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


    public static boolean isValidNickname(String USERNAME) {
        return Pattern.compile(regexp).matcher(USERNAME).matches();
    }


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
    }


    private static void drawFruit(Group gameLayout) {
        viewSpawningFruit = new ImageView(spawningFruit);
        viewSpawningFruit.setX(spawningFruitColumn * widthOneBlock + 2.5);
        viewSpawningFruit.setY(spawningFruitRow * heightOneBlock + 2.5);
        viewSpawningFruit.setFitWidth(widthOneBlock - 5);
        viewSpawningFruit.setFitHeight(heightOneBlock - 5);
        gameLayout.getChildren().remove(viewSpawningFruit);
        gameLayout.getChildren().add(viewSpawningFruit);
    }


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


    public static void drawLifes(Group gameLayout) {
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

        gameLayout.getChildren().addAll(viewBlinky, viewPinky);
    }


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

            mapReader.readMap();
            drawNextMap(gameLayout);
        }
    }


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
        gameLayout.getChildren().add(viewBlinky);
    }

    public static void collectPoints(Group gameLayout) {
        if (!dots[(int) pacmanColumn][(int) pacmanRow]) return;

        dots[(int) pacmanColumn][(int) pacmanRow] = false;
        dotCount--;
        score += 10;    // A dot is worth 10 Points

        clearer(gameLayout);
    }


    public static void collectPowerPill(Group gameLayout) {
        if (!powerPills[(int) pacmanColumn][(int) pacmanRow]) return;

        powerPills[(int) pacmanColumn][(int) pacmanRow] = false;
        powerPillCount--;
        score += 50;        // A Power Pill gives 50 points

        clearer(gameLayout);
    }


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

    private static void checkDownPossible(int direction) {
        // Check if Down is possible
        if (!notAllowedBox[(int) pacmanColumn + direction][(int) pacmanRow + 1]) {
            if (waitingForTurn == 'd' && !stop) {
                stop = true;
                pacmanXPosCenter = (pacmanColumn * widthOneBlock) + widthOneBlock * direction;
                pacmanYPosCenter = (pacmanRow * heightOneBlock);
                allowNextMoveUp = true;
            }
        }
    }

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

    private static void rightWallHit(){
        if (pacmanXPos >= pacmanXPosCenter) {
            velocityPacmanHorizontal = 0;
            velocityPacmanVertical = 0;
            waitingForTurn = '1';

            pacmanFacingLeft = false;
            pacmanFacingRight = false;
            pacmanFacingDown = false;
            pacmanFacingUp = false;

            if (!notAllowedBox[(int) pacmanColumn][(int) pacmanRow - 1]) allowNextMoveUp = true;
            if (!notAllowedBox[(int) pacmanColumn][(int) pacmanRow + 1]) allowNextMoveDown = true;

            allowNextMoveRight = false;
            return;
        }
        viewPacmanRight.setX(pacmanXPos);
        viewPacmanRight.setY(pacmanYPos);
        pacmanXPos += velocityPacmanHorizontal;
    }


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
                rightWallHit();
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

            if (stop) {
                if (pacmanXPos > pacmanXPosCenter) {
                    viewPacmanLeft.setX(pacmanXPos);
                    viewPacmanLeft.setY(pacmanYPos);
                    pacmanXPos += velocityPacmanHorizontal;
                } else {
                    if (waitingForTurn == 'u') {
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

                        gameLayout.getChildren().addAll(viewPacmanUp);

                        pacmanFacingUp = true;
                        pacmanFacingDown = false;
                        pacmanFacingLeft = false;
                        pacmanFacingRight = false;
                        velocityPacmanHorizontal = 0;
                        velocityPacmanVertical = -1;

                        hitDownWall = false;
                        stop = false;
                        waitingForTurn = '1';
                    }

                    if (waitingForTurn == 'd') {
                        //::::::::::: Pac-Man GIF :::::::::::\\

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
                }
            } else {

                if ((int) pacmanColumn - 2 < 0) {
                    // Teleport left/right
                    pacmanXPos = blockCountHorizontally * widthOneBlock;

                } else {
                    if (notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow] && !hitLeftWall) {
                        hitLeftWall = true;
                        pacmanXPosCenter = pacmanXPos - (int) (characterWidth / 2);
                        pacmanYPosCenter = pacmanYPos;
                    }

                    if (hitLeftWall) {
                        if (pacmanXPos > pacmanXPosCenter) {
                            viewPacmanLeft.setX(pacmanXPos);
                            viewPacmanLeft.setY(pacmanYPos);
                            pacmanXPos += velocityPacmanHorizontal;
                        } else {
                            velocityPacmanHorizontal = 0;
                            velocityPacmanVertical = 0;
                            waitingForTurn = '1';

                            pacmanFacingLeft = false;
                            pacmanFacingRight = false;
                            pacmanFacingDown = false;
                            pacmanFacingUp = false;


                            if (!notAllowedBox[(int) pacmanColumn][(int) pacmanRow - 1]) {
                                allowNextMoveUp = true;
                            }
                            if (!notAllowedBox[(int) pacmanColumn][(int) pacmanRow + 1]) {
                                allowNextMoveDown = true;
                            }

                            allowNextMoveLeft = false;
                        }
                    } else {
                        allowNextMoveLeft = true;
                        allowNextMoveUp = false;
                        allowNextMoveDown = false;
                        allowNextMoveRight = true;

                        viewPacmanLeft.setX(pacmanXPos);
                        viewPacmanLeft.setY(pacmanYPos);
                        pacmanXPos += velocityPacmanHorizontal;
                    }
                }
            }
            return;
        }

        if (pacmanFacingUp) {

            // Check if Left is possible
            if (!notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow - 1]) {
                if (waitingForTurn == 'l' && !stop) {
                    stop = true;
                    pacmanXPosCenter = (pacmanColumn * widthOneBlock);
                    pacmanYPosCenter = (pacmanRow * heightOneBlock) - heightOneBlock;
                    allowNextMoveLeft = true;
                }
            }

            // Check if Right is possible
            if (!notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow - 1]) {
                if (waitingForTurn == 'r' && !stop) {
                    stop = true;
                    pacmanXPosCenter = (pacmanColumn * widthOneBlock);
                    pacmanYPosCenter = (pacmanRow * heightOneBlock) - heightOneBlock;
                    allowNextMoveRight = true;
                }
            }


            if (stop) {
                if (pacmanYPos > pacmanYPosCenter) {
                    viewPacmanUp.setX(pacmanXPos);
                    viewPacmanUp.setY(pacmanYPos);
                    pacmanYPos += velocityPacmanVertical;
                } else {
                    if (waitingForTurn == 'l') {
                        //::::::::::: Pac-Man GIF :::::::::::\\

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

                    if (waitingForTurn == 'r') {
                        //::::::::::: Pac-Man GIF :::::::::::\\

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
                }
            } else {
                if (notAllowedBox[(int) pacmanColumn][(int) pacmanRow - 1] && !hitUpWall) {
                    hitUpWall = true;

                    pacmanXPosCenter = pacmanXPos;
                    pacmanYPosCenter = pacmanYPos - (int) (characterWidth / 2);
                }

                if (hitUpWall) {
                    if (pacmanYPos > pacmanYPosCenter) {
                        viewPacmanUp.setX(pacmanXPos);
                        viewPacmanUp.setY(pacmanYPos);
                        pacmanYPos += velocityPacmanVertical;
                    } else {
                        velocityPacmanHorizontal = 0;
                        velocityPacmanVertical = 0;
                        waitingForTurn = '1';

                        pacmanFacingLeft = false;
                        pacmanFacingRight = false;
                        pacmanFacingDown = false;
                        pacmanFacingUp = false;

                        if (!notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow]) {
                            allowNextMoveLeft = true;
                        }
                        if (!notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow]) {
                            allowNextMoveRight = true;
                        }
                    }
                } else {
                    allowNextMoveLeft = false;
                    allowNextMoveUp = false;
                    allowNextMoveDown = true;
                    allowNextMoveRight = false;

                    viewPacmanUp.setX(pacmanXPos);
                    viewPacmanUp.setY(pacmanYPos);
                    pacmanYPos += velocityPacmanVertical;
                }
            }
            return;
        }
        if (pacmanFacingDown) {

            // Check if Left is possible
            if (!notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow + 1]) {
                if (waitingForTurn == 'l' && !stop) {
                    stop = true;
                    pacmanXPosCenter = (pacmanColumn * widthOneBlock);
                    pacmanYPosCenter = (pacmanRow * heightOneBlock) + heightOneBlock;
                    allowNextMoveLeft = true;
                }
            }

            // Check if Right is possible
            if (!notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow + 1]) {
                if (waitingForTurn == 'r' && !stop) {
                    stop = true;
                    pacmanXPosCenter = (pacmanColumn * widthOneBlock);
                    pacmanYPosCenter = (pacmanRow * heightOneBlock) + heightOneBlock;
                    allowNextMoveRight = true;
                }
            }


            if (stop) {
                if (pacmanYPos < pacmanYPosCenter) {
                    viewPacmanDown.setX(pacmanXPos);
                    viewPacmanDown.setY(pacmanYPos);
                    pacmanYPos += velocityPacmanVertical;
                } else {
                    if (waitingForTurn == 'l') {
                        //::::::::::: Pac-Man GIF :::::::::::\\

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

                    if (waitingForTurn == 'r') {
                        //::::::::::: Pac-Man GIF :::::::::::\\

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
                }

            } else {

                if (notAllowedBox[(int) pacmanColumn][(int) pacmanRow + 1] && !hitDownWall) {
                    hitDownWall = true;

                    pacmanXPosCenter = pacmanXPos;
                    pacmanYPosCenter = pacmanYPos + (int) (characterWidth / 2);
                }

                if (hitDownWall) {
                    if (pacmanYPos < pacmanYPosCenter) {
                        viewPacmanDown.setX(pacmanXPos);
                        viewPacmanDown.setY(pacmanYPos);
                        pacmanYPos += velocityPacmanVertical;
                    } else {
                        velocityPacmanHorizontal = 0;
                        velocityPacmanVertical = 0;
                        waitingForTurn = '1';

                        pacmanFacingLeft = false;
                        pacmanFacingRight = false;
                        pacmanFacingDown = false;
                        pacmanFacingUp = false;

                        if (!notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow]) {
                            allowNextMoveLeft = true;
                        }
                        if (!notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow]) {
                            allowNextMoveRight = true;
                        }

                    }
                } else {
                    allowNextMoveLeft = false;
                    allowNextMoveUp = true;
                    allowNextMoveDown = false;
                    allowNextMoveRight = false;

                    viewPacmanDown.setX(pacmanXPos);
                    viewPacmanDown.setY(pacmanYPos);
                    pacmanYPos += velocityPacmanVertical;
                }
            }
        }
    }
}
