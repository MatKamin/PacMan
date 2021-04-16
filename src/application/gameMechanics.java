package application;


//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import static application.functionality.pacmanControls.*;
import static application.variables.*;


//---------------------------------CLASS---------------------------------\\

public class gameMechanics {

    /**
     * Animates Ghosts
     * TODO: Ghost AI
     */
    public static void ghostMove() {
        // Blinky
        viewBlinky.setX(blinkyXPos);
        blinkyXPos += velocityBlinky;

        // Pinky
        viewPinky.setX(pinkyXPos);
        pinkyXPos += velocityPinky;
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
            viewSpawningFruit.setY(20 * heightOneBlock + 2.5);

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
            viewSpawningFruit.setY(20 * heightOneBlock + 2.5);

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
        if(lifesCounter != lifesOriginally){

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
        // TODO

        if (levelCounter != startingLevel){
            viewCherry = new ImageView(cherry);

            viewCherry.setX((blockCountHorizontally - 2) * widthOneBlock);
            viewCherry.setY(blockCountVertically * heightOneBlock);

            viewCherry.setFitWidth(widthOneBlock);
            viewCherry.setFitHeight(heightOneBlock);

            gameLayout.getChildren().remove(viewCherry);
            gameLayout.getChildren().add(viewCherry);

            levelCounter++;
        }
    }

    /**
     * All Dots have been eaten -> Next Level
     */
    public static void gameOver() {

        // TODO Level UP

        if (dotCount == 0) {
            gameStarted = false;
        }
    }

    /**
     * makes collecting the spawned fruits possible
     * -> After 9 to 10 seconds the fruit disappears
     *
     * @param gameLayout Group with the gameLayout
     */
    public static void collectFruit(Group gameLayout) {
        // 14|20 is the Spawn point of the Fruits
        if ((pacmanColumn == 14) && (pacmanRow == 20) && collectableFruit) {
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
     * @param gameLayout Group with the gameLayout
     */
    public static void collectPoints(Group gameLayout) {
        if (dots[(int) pacmanColumn][(int) pacmanRow]) {
            dots[(int) pacmanColumn][(int) pacmanRow] = false;
            dotCount--;
            score += 10;    // A dot is worth 10 Points

            viewClearer = new ImageView(clearer);
            viewClearer.setFitWidth((int)(widthOneBlock / 2));
            viewClearer.setFitHeight((int)(heightOneBlock / 2));
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
        }
    }

    /**
     * Makes collecting Power Pills possible
     * TODO: Power Pill Effect
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
        }
    }


    /**
     * Makes Pac-Man movement possible
     * TODO: Fix Some Glitches
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
            if (!notAllowedBox[(int) pacmanColumn][(int) pacmanRow - 1]) {
                if (waitingForTurn == 'u' && !stop) {
                    stop = true;
                    pacmanXPosCenter = pacmanXPos + (int)(characterWidth / 2);
                    pacmanYPosCenter = pacmanYPos;
                    allowNextMoveUp = true;
                }
            }

            // Check if Down is possible
            if (!notAllowedBox[(int) pacmanColumn][(int) pacmanRow + 1]) {
                if (waitingForTurn == 'd' && !stop) {
                    stop = true;
                    pacmanXPosCenter = pacmanXPos + (int)(characterWidth / 2);
                    pacmanYPosCenter = pacmanYPos;
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

                        hitDownWall = false;
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
                        pacmanXPosCenter = pacmanXPos + (int)(characterWidth / 2);
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
                        }

                    } else {
                        allowNextMoveLeft = true;
                        allowNextMoveUp = false;
                        allowNextMoveDown = false;
                        allowNextMoveRight = false;

                        viewPacmanRight.setX(pacmanXPos);
                        viewPacmanRight.setY(pacmanYPos);
                        pacmanXPos += velocityPacmanHorizontal;
                    }
                }

            }


        } else if (pacmanFacingLeft) {

            // Check if Up is possible
            if (!notAllowedBox[(int) pacmanColumn][(int) pacmanRow - 1]) {
                if (waitingForTurn == 'u' && !stop) {
                    stop = true;
                    pacmanXPosCenter = pacmanXPos - (int)(characterWidth / 2);
                    pacmanYPosCenter = pacmanYPos;
                    allowNextMoveUp = true;
                }
            }

            // Check if Down is possible
            if (!notAllowedBox[(int) pacmanColumn][(int) pacmanRow + 1]) {
                if (waitingForTurn == 'd' && !stop) {
                    stop = true;
                    pacmanXPosCenter = pacmanXPos - (int)(characterWidth / 2);
                    pacmanYPosCenter = pacmanYPos;
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

                if ((int) pacmanColumn - 1 < 0) {
                    // Teleport left/right
                    pacmanXPos = blockCountHorizontally * widthOneBlock;

                } else {
                    if (notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow] && !hitLeftWall) {
                        hitLeftWall = true;
                        pacmanXPosCenter = pacmanXPos - (int)(characterWidth / 2);
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

                        }
                    } else {
                        allowNextMoveLeft = false;
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
            if (!notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow]) {
                if (waitingForTurn == 'l' && !stop) {
                    stop = true;
                    pacmanXPosCenter = pacmanXPos;
                    pacmanYPosCenter = pacmanYPos - (int)(characterWidth / 2);
                    allowNextMoveLeft = true;
                }
            }

            // Check if Right is possible
            if (!notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow]) {
                if (waitingForTurn == 'r' && !stop) {
                    stop = true;
                    pacmanXPosCenter = pacmanXPos;
                    pacmanYPosCenter = pacmanYPos - (int)(characterWidth / 2);
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
                    pacmanYPosCenter = pacmanYPos - (int)(characterWidth / 2);
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
            if (!notAllowedBox[(int) pacmanColumn - 1][(int) pacmanRow]) {
                if (waitingForTurn == 'l' && !stop) {
                    stop = true;
                    pacmanXPosCenter = pacmanXPos;
                    pacmanYPosCenter = pacmanYPos + (int)(characterWidth / 2);
                    allowNextMoveLeft = true;
                }
            }

            // Check if Right is possible
            if (!notAllowedBox[(int) pacmanColumn + 1][(int) pacmanRow]) {
                if (waitingForTurn == 'r' && !stop) {
                    stop = true;
                    pacmanXPosCenter = pacmanXPos;
                    pacmanYPosCenter = pacmanYPos + (int)(characterWidth / 2);
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
                    pacmanYPosCenter = pacmanYPos + (int)(characterWidth / 2);
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


    /**
     * Ghosts Bouncing left / right
     * TODO: Ghost AI
     */
    public static void wallGhost() {

        if (velocityBlinky < 0) {
            if (notAllowedBox[(int) blinkyXPos / (int) widthOneBlock][(int) blinkyRow]) {
                velocityBlinky *= -1;
            }
        } else {
            if (notAllowedBox[(int) blinkyXPos / (int) widthOneBlock + 1][(int) blinkyRow]) {
                velocityBlinky *= -1;
            }
        }

        if (velocityPinky < 0) {
            if (notAllowedBox[(int) pinkyXPos / (int) widthOneBlock][(int) pinkyRow]) {
                velocityPinky *= -1;
            }
        } else {
            if (notAllowedBox[(int) pinkyXPos / (int) widthOneBlock + 1][(int) pinkyRow]) {
                velocityPinky *= -1;
            }
        }

    }
}
