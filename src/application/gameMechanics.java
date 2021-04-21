package application;


//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static application.variables.*;


//---------------------------------CLASS---------------------------------\\

public class gameMechanics {


    /**
     * Checks if nickname is valid
     *
     * @param USERNAME input username
     */
    public static boolean validNickname(String USERNAME) {
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(USERNAME);
        return m.matches();
    }


    public static void resetGame(Group gameLayout){
        removeMap(gameLayout);
        gameLayout.getChildren().removeAll(viewCherry);
        firstRead = true;
        score = 0;
        lifesCounter = 3;
        lifesOriginally = 3;
        levelCounter = 0;
        startingLevel = 1;
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
        doOnce = true;
        doOnce2 = true;
        mapReader.readMap();
        nextLevel = true;
    }




    /**
     * Spawns a fruit after eating 70 and 170 dots
     *
     * @param gameLayout Group with the gameLayout
     */
    public static void spawnFruit(Group gameLayout) {
        if (dotCount == dotCountAtStart - 70 && !fruitSpawned1) {
            viewSpawningFruit = new ImageView(spawningFruit);
            viewSpawningFruit.setX(14 * widthOneBlock + 2.5);
            viewSpawningFruit.setY(21 * heightOneBlock + 2.5);
            viewSpawningFruit.setFitWidth(widthOneBlock - 5);
            viewSpawningFruit.setFitHeight(heightOneBlock - 5);
            gameLayout.getChildren().remove(viewSpawningFruit);
            gameLayout.getChildren().add(viewSpawningFruit);
            collectableFruit = true;
            fruitSpawned1 = true;
        }

        if (dotCount == dotCountAtStart - 170 && !fruitSpawned2) {
            viewSpawningFruit = new ImageView(spawningFruit);
            viewSpawningFruit.setX(14 * widthOneBlock + 2.5);
            viewSpawningFruit.setY(21 * heightOneBlock + 2.5);
            viewSpawningFruit.setFitWidth(widthOneBlock - 5);
            viewSpawningFruit.setFitHeight(heightOneBlock - 5);
            gameLayout.getChildren().remove(viewSpawningFruit);
            gameLayout.getChildren().add(viewSpawningFruit);
            collectableFruit = true;
            fruitSpawned2 = true;
            doOnce2 = true;
        }
    }



    /**
     * draws the Life Counter in the UI
     *
     * @param gameLayout Group with the gameLayout
     */
    public static void drawLifes(Group gameLayout) {
        if (lifesCounter != lifesOriginally) {
            for (int i = 1; i <= lifesCounter; i++) {
                viewLifes = new ImageView(lifes);
                viewLifes.setX(i * (widthOneBlock + 10));
                viewLifes.setY(blockCountVertically * heightOneBlock);
                viewLifes.setFitWidth(widthOneBlock);
                viewLifes.setFitHeight(heightOneBlock);
                gameLayout.getChildren().addAll(viewLifes);
            }
            lifesOriginally--;
        }
    }

    /**
     * Draws the LevelCounter as Fruit Symbols
     *
     * @param gameLayout Group with the gameLayout
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


    private static void removeMap(Group gameLayout){
        // Remove Dots to Map
        for (int i = 0; i < dotCount; i++) {
            gameLayout.getChildren().remove(viewDot[i]);
        }


        // Remove Power Pills to Map
        for(int i = 0; i < powerPillCount; i++){
            gameLayout.getChildren().remove(viewPowerPill[i]);
        }


        // Remove Walls to Map
        for(int i = 0; i < wallCount; i++){
            gameLayout.getChildren().remove(viewWall[i]);
        }

        // Remove Vertical Rails to Map
        for(int i = 0; i < railVerticalCount; i++){
            gameLayout.getChildren().remove(viewRailVertical[i]);
        }

        // Remove Horizontal Rails to Map
        for(int i = 0; i < railHorizontalCount; i++){
            gameLayout.getChildren().remove(viewRailHorizontal[i]);
        }

        // Remove Up Right Rails to Map
        for(int i = 0; i < railUpRightCount; i++){
            gameLayout.getChildren().remove(viewRailUpRight[i]);
        }

        // Remove Up Left Rails to Map
        for(int i = 0; i < railUpLeftCount; i++){
            gameLayout.getChildren().remove(viewRailUpLeft[i]);
        }

        // Remove Right Up Rails to Map
        for(int i = 0; i < railRightUpCount; i++){
            gameLayout.getChildren().remove(viewRailRightUp[i]);
        }

        // Remove Left Up Rails to Map
        for(int i = 0; i < railLeftUpCount; i++){
            gameLayout.getChildren().remove(viewRailLeftUp[i]);
        }
    }

    /**
     * All Dots have been eaten -> Next Level
     */
    public static void levelUp(Group gameLayout) {

        if (dotCount == 0) {
            nextLevel = true;
        }
        if (nextLevel) {
            levelCounter++;
            if (levelCounter > maxLevel){
                return;
            }
            mapFile = "resources/levels/level" + levelCounter + ".txt";
            System.out.println(mapFile);

            removeMap(gameLayout);
            gameLayout.getChildren().removeAll(viewBlinky, viewPinky);

            nextLevel = false;
            firstRead = true;
            lifesOriginally = 3;
            startingLevel = 1;
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
            doOnce = true;
            doOnce2 = true;

            mapReader.readMap();

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


            gameLayout.getChildren().addAll(viewBlinky, viewPinky);

            System.out.println(levelCounter);
            System.out.println(startingLevel);
            System.out.println();
        }
    }



    /**
     * makes collecting the spawned fruits possible
     * -> After 9 to 10 seconds the fruit disappears
     *
     * @param gameLayout Group with the gameLayout
     */
    public static void collectFruit(Group gameLayout) {
        // 14|21 is the Spawn point of the Fruits
        if ((pacmanColumn == 14) && (pacmanRow == 21) && collectableFruit) {
            score += 100;       // Fruit gives 100 Points
            // TODO: Fruit gives points depending on current Level

            collectableFruit = false;
            gameLayout.getChildren().remove(viewSpawningFruit);

        } else if (collectableFruit) {

            // Get Random Lifespan of the Fruit
            if (doOnce) {
                delayFruit = (int) (Math.random() * (10000 - 9000 + 1)) + 9000;
                doOnce = false;
            }

            // Timer
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (doOnce2) {
                                collectableFruit = false;
                                doOnce2 = false;
                            }
                        }
                    },
                    delayFruit
            );

        } else {
            gameLayout.getChildren().remove(viewSpawningFruit);
            doOnce = true;
        }
    }



    /**
     * Makes Collecting Dots possible
     *
     * @param gameLayout Group with the gameLayout
     */
    public static void collectPoints(Group gameLayout) {
        if (dots[(int) pacmanColumn][(int) pacmanRow]) {
            dots[(int) pacmanColumn][(int) pacmanRow] = false;
            dotCount--;
            score += 10;    // A dot is worth 10 Points

            viewClearer = new ImageView(clearer);
            viewClearer.setFitWidth((int) (widthOneBlock / 2));
            viewClearer.setFitHeight((int) (heightOneBlock / 2));
            viewClearer.setX(pacmanColumn * widthOneBlock + viewClearer.getFitWidth() / 2);
            viewClearer.setY(pacmanRow * heightOneBlock + viewClearer.getFitHeight() / 2);

            gameLayout.getChildren().remove(viewClearer);
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
    }

    /**
     * Makes collecting Power Pills possible
     * TODO: Power Pill Effect
     *
     * @param gameLayout Group with the gameLayout
     */
    public static void collectPowerPill(Group gameLayout) {
        if (powerPills[(int) pacmanColumn][(int) pacmanRow]) {
            powerPills[(int) pacmanColumn][(int) pacmanRow] = false;
            powerPillCount--;
            score += 50;        // A Power Pill gives 50 points

            viewClearer = new ImageView(clearer);
            viewClearer.setFitWidth(widthOneBlock - 5);
            viewClearer.setFitHeight(heightOneBlock - 5);
            viewClearer.setX(pacmanColumn * widthOneBlock + 2.5);
            viewClearer.setY(pacmanRow * heightOneBlock + 2.5);

            //gameLayout.getChildren().remove(viewClearer);
            gameLayout.getChildren().addAll(viewClearer);

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
    }


    /**
     * Makes Pac-Man movement possible
     *
     * @param gameLayout Group with the gameLayout
     */
    public static void pacmanMove(Group gameLayout) {

        pacmanRow = (int) Math.round(pacmanYPos / widthOneBlock);
        pacmanColumn = (int) Math.round((pacmanXPos / heightOneBlock));

        // Info Output for debugging
        // Press SPACE to Output
        if (debug) {
            System.out.println(notAllowedBox[(int) ((pacmanXPos / widthOneBlock))][(int) (pacmanYPos / heightOneBlock)]);
            System.out.println("COLUMN: ");
            System.out.println("ROW: ");
            System.out.println();
            System.out.println("COLUMN front LEFT: " + (pacmanColumn - 1));
            System.out.println("ROW front LEFT: " + pacmanRow);
            System.out.println();
            System.out.println("COLUMN front RIGHT: " + pacmanColumn);
            System.out.println("ROW front RIGHT: " + pacmanRow);
            System.out.println();
            System.out.println("COLUMN front DOWN: " + pacmanColumn);
            System.out.println("ROW front DOWN: " + (pacmanRow + 1));
            System.out.println();
            System.out.println("COLUMN front UP: " + pacmanColumn);
            System.out.println("ROW front UP: " + (pacmanRow - 1));
            System.out.println();
            debug = false;
        }


        if (pacmanFacingRight) {

            // Check if Up is possible
            if (!notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow - 1]) {
                if (waitingForTurn == 'u' && !stop) {
                    stop = true;
                    pacmanXPosCenter = (pacmanColumn * widthOneBlock) + widthOneBlock;
                    pacmanYPosCenter = (pacmanRow * heightOneBlock);
                    allowNextMoveUp = true;
                }
            }

            // Check if Down is possible
            if (!notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow + 1]) {
                if (waitingForTurn == 'd' && !stop) {
                    stop = true;
                    pacmanXPosCenter = (pacmanColumn * widthOneBlock) + widthOneBlock;
                    pacmanYPosCenter = (pacmanRow * heightOneBlock);
                    allowNextMoveUp = true;
                }
            }


            if (stop) {                                     // If Up or Down turn is wanted
                if (pacmanXPos < pacmanXPosCenter) {
                    viewPacmanRight.setX(pacmanXPos);
                    viewPacmanRight.setY(pacmanYPos);
                    pacmanXPos += velocityPacmanHorizontal;
                } else {
                    if (waitingForTurn == 'u') {

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

                    if (waitingForTurn == 'd') {

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

                if (pacmanColumn + 1 > blockCountHorizontally) {
                    // Teleport left/right
                    pacmanXPos = widthOneBlock;

                } else {
                    // Check if Wall got Hit
                    if (notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow] && !hitRightWall) {
                        hitRightWall = true;
                        pacmanXPosCenter = pacmanXPos + (int) (characterWidth / 2);
                        pacmanYPosCenter = pacmanYPos;
                    }


                    // If Wall hit
                    if (hitRightWall) {
                        if (pacmanXPos < pacmanXPosCenter) {    // Go a little bit further
                            viewPacmanRight.setX(pacmanXPos);
                            viewPacmanRight.setY(pacmanYPos);
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

                            allowNextMoveRight = false;
                        }

                    } else {
                        allowNextMoveLeft = true;
                        allowNextMoveUp = false;
                        allowNextMoveDown = false;
                        allowNextMoveRight = true;

                        viewPacmanRight.setX(pacmanXPos);
                        viewPacmanRight.setY(pacmanYPos);
                        pacmanXPos += velocityPacmanHorizontal;
                    }
                }

            }


        } else if (pacmanFacingLeft) {

            // Check if Up is possible
            if (!notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow - 1]) {
                if (waitingForTurn == 'u' && !stop) {
                    stop = true;
                    pacmanXPosCenter = (pacmanColumn * widthOneBlock) - widthOneBlock;
                    pacmanYPosCenter = (pacmanRow * heightOneBlock);
                    allowNextMoveUp = true;
                }
            }

            // Check if Down is possible
            if (!notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow + 1]) {
                if (waitingForTurn == 'd' && !stop) {
                    stop = true;
                    pacmanXPosCenter = (pacmanColumn * widthOneBlock) - widthOneBlock;
                    pacmanYPosCenter = (pacmanRow * heightOneBlock);
                    allowNextMoveDown = true;
                }
            }

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


        } else if (pacmanFacingUp) {

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


        } else if (pacmanFacingDown) {

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
