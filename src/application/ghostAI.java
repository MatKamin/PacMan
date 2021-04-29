package application;

import static application.gameMechanics.*;
import static application.imageViewerVariables.viewBlinky;
import static application.main.*;
import static application.mapReader.*;

public class ghostAI {

    //---------------------------------VARIABLES---------------------------------\\
    static int blinkyColumnNew;
    static int blinkyRowNew;

    static boolean blinkyGoingUp;
    static boolean blinkyGoingDown;
    static boolean blinkyGoingLeft;
    static boolean blinkyGoingRight;

    static double velocityBlinkyVertical = 0;
    static double velocityBlinkyHorizontal = 1;

    static boolean blinkyGoRight = true;
    static boolean blinkyGoLeft = true;
    static boolean blinkyGoUp = true;
    static boolean blinkyGoDown = true;

    static double distance1 = 10000;
    static double distance2 = 10000;
    static double distance3 = 10000;
    static double distance4 = 10000;

    /**
     * Animates Ghosts
     * TODO: Ghost AI remaining
     */
    public static void ghostAnimate() {
        // Blinky
        viewBlinky.setX(blinkyXPos);
        viewBlinky.setY(blinkyYPos);
        blinkyXPos += velocityBlinkyHorizontal;
        blinkyYPos += velocityBlinkyVertical;

        calculateNextMove();
        allowTeleport();
    }

    /**
     * Allows Teleporting Left / Right by Ghost
     */
    private static void allowTeleport(){
        if (velocityBlinkyHorizontal > 0) {
            if (blinkyColumnNew + 1 > blockCountHorizontally) {
                // Teleport right to left
                blinkyXPos = widthOneBlock;
                blinkyColumnNew = 0;
                blinkyColumn = 0;
                calculateNextMove();
            }
        }
        if (velocityBlinkyHorizontal < 0) {
            if (blinkyColumnNew - 2 < 0) {
                // Teleport left to right
                blinkyXPos = blockCountHorizontally * widthOneBlock;
                blinkyColumnNew = blockCountHorizontally - 1;
                blinkyColumn = blockCountHorizontally - 1;
                calculateNextMove();
            }
        }
    }

    /**
     * Gets Ghosts moving direction
     */
    private static void getMovingDirection() {
        if (velocityBlinkyHorizontal > 0) blinkyGoingRight = true;
        if (velocityBlinkyHorizontal < 0) blinkyGoingLeft = true;
        if (velocityBlinkyVertical < 0) blinkyGoingUp = true;
        if (velocityBlinkyVertical > 0) blinkyGoingDown = true;
    }

    /**
     * Dont allow Turning around
     */
    private static void blockTurnAround() {
        if (blinkyGoingRight) blinkyGoLeft = false;
        if (blinkyGoingLeft) blinkyGoRight = false;
        if (blinkyGoingUp) blinkyGoDown = false;
        if (blinkyGoingDown) blinkyGoUp = false;
    }

    /**
     * Dont allow any moves against walls
     */
    private static void blockImpossibleMoves() {
        if (ghostNotPossibleUp()) blinkyGoUp = false;
        if (ghostNotPossibleDown()) blinkyGoDown = false;
        if (ghostNotPossibleLeft()) blinkyGoLeft = false;
        if (ghostNozPossibleRight()) blinkyGoRight = false;
    }

    /**
     * Calculates Distances
     */
    private static void calculateDistances() {
        // TARGET SCATTER MODE
        // COLUMN: 26 (pacmanColumn)
        // ROW: 1 (pacmanRow)
        if (blinkyColumnNew > 10 && blinkyColumnNew < 17 && blinkyRowNew > 15 && blinkyRowNew < 20) {
            if (blinkyGoRight) distance1 = Math.pow(Math.abs((blinkyColumn + 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
            if (blinkyGoUp) distance2 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow - 1) - 1 - 1), 2);
            if (blinkyGoDown) distance3 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow + 1) - 1 - 1), 2);
            if (blinkyGoLeft) distance4 = Math.pow(Math.abs((blinkyColumn - 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
        } else {
            if (blinkyGoRight) distance1 = Math.pow(Math.abs((blinkyColumn + 1) - pacmanColumn - 1), 2) + Math.pow(Math.abs(blinkyRow - pacmanRow), 2);
            if (blinkyGoUp) distance2 = Math.pow(Math.abs(blinkyColumn - pacmanColumn), 2) + Math.pow(Math.abs((blinkyRow - 1) - pacmanRow - 1), 2);
            if (blinkyGoDown) distance3 = Math.pow(Math.abs(blinkyColumn - pacmanColumn), 2) + Math.pow(Math.abs((blinkyRow + 1) - pacmanRow - 1), 2);
            if (blinkyGoLeft) distance4 = Math.pow(Math.abs((blinkyColumn - 1) - pacmanColumn - 1), 2) + Math.pow(Math.abs(blinkyRow - pacmanRow), 2);
        }
    }

    /**
     * Sets Ghost Velocity
     */
    private static void moveGhost(){
        if (blinkyGoLeft) {
            velocityBlinkyVertical = 0;
            velocityBlinkyHorizontal = -1;
        } else if (blinkyGoRight) {
            velocityBlinkyVertical = 0;
            velocityBlinkyHorizontal = 1;
        } else if (blinkyGoDown) {
            velocityBlinkyVertical = 1;
            velocityBlinkyHorizontal = 0;
        } else if (blinkyGoUp) {
            velocityBlinkyVertical = -1;
            velocityBlinkyHorizontal = 0;
        }
    }

    /**
     * Gets the shortest distance
     */
    private static void getShortestDistance() {
        double smallest = distance1;
        if (distance2 < smallest) smallest = distance2;
        if (distance3 < smallest) smallest = distance3;
        if (distance4 < smallest) smallest = distance4;
        if (smallest != distance1) blinkyGoRight = false;
        if (smallest != distance2) blinkyGoUp = false;
        if (smallest != distance3) blinkyGoDown = false;
        if (smallest != distance4) blinkyGoLeft = false;
    }


    /**
     * Calculates next move
     */
    private static void calculateNextMove() {
        blinkyRowNew = (int) Math.round((blinkyYPos + characterHeight / 2) / widthOneBlock);
        blinkyColumnNew = (int) Math.round(((blinkyXPos - characterWidth / 2) / heightOneBlock));

        if (velocityBlinkyHorizontal < 0 || velocityBlinkyVertical > 0) {
            blinkyRowNew = (int) Math.round((blinkyYPos - characterHeight / 2) / widthOneBlock);
            blinkyColumnNew = (int) Math.round(((blinkyXPos + characterWidth / 2) / heightOneBlock));
        }

        blinkyGoingRight = false;
        blinkyGoingLeft = false;
        blinkyGoingUp = false;
        blinkyGoingDown = false;

        blinkyGoRight = true;
        blinkyGoLeft = true;
        blinkyGoUp = true;
        blinkyGoDown = true;

        distance1 = 10000;
        distance2 = 10000;
        distance3 = 10000;
        distance4 = 10000;

        if (blinkyColumnNew == blinkyColumn + 1 || blinkyColumnNew == blinkyColumn - 1) {
            blinkyColumn = blinkyColumnNew;
            getMovingDirection();
            blockTurnAround();
            blockImpossibleMoves();
            calculateDistances();
            getShortestDistance();
            moveGhost();
        }
        if (blinkyRowNew == blinkyRow + 1 || blinkyRowNew == blinkyRow - 1) {
            blinkyRow = blinkyRowNew;
            getMovingDirection();
            blockTurnAround();
            blockImpossibleMoves();
            calculateDistances();
            getShortestDistance();
            moveGhost();
        }
    }


    /**
     * Check if UP is possible
     * @return true or false
     */
    public static boolean ghostNotPossibleUp() { return notAllowedBox[(int) blinkyColumn][(int) blinkyRow - 1]; }

    /**
     * Check if DOWN is possible
     * @return true or false
     */
    public static boolean ghostNotPossibleDown() { return notAllowedBox[(int) blinkyColumn][(int) blinkyRow + 1]; }

    /**
     * Check if Left is possible
     * @return true or false
     */
    public static boolean ghostNotPossibleLeft() { return notAllowedBox[(int) blinkyColumn - 1][(int) blinkyRow]; }

    /**
     * Check if Right is possible
     * @return true or false
     */
    public static boolean ghostNozPossibleRight() { return notAllowedBox[(int) blinkyColumn + 1][(int) blinkyRow]; }
}
