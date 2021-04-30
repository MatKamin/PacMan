package application;

import static application.chaseMode.calculateNextMove;
import static application.gameMechanics.notAllowedBox;
import static application.main.widthOneBlock;
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
     * Allows Teleporting Left / Right by Ghost
     */
    public static void allowTeleport(){
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
    public static void getMovingDirection() {
        if (velocityBlinkyHorizontal > 0) blinkyGoingRight = true;
        if (velocityBlinkyHorizontal < 0) blinkyGoingLeft = true;
        if (velocityBlinkyVertical < 0) blinkyGoingUp = true;
        if (velocityBlinkyVertical > 0) blinkyGoingDown = true;
    }

    /**
     * Dont allow Turning around
     */
    public static void blockTurnAround() {
        if (blinkyGoingRight) blinkyGoLeft = false;
        if (blinkyGoingLeft) blinkyGoRight = false;
        if (blinkyGoingUp) blinkyGoDown = false;
        if (blinkyGoingDown) blinkyGoUp = false;
    }

    /**
     * Dont allow any moves against walls
     */
    public static void blockImpossibleMoves() {
        if (ghostNotPossibleUp()) blinkyGoUp = false;
        if (ghostNotPossibleDown()) blinkyGoDown = false;
        if (ghostNotPossibleLeft()) blinkyGoLeft = false;
        if (ghostNozPossibleRight()) blinkyGoRight = false;
    }



    /**
     * Sets Ghost Velocity
     */
    public static void moveGhost(){
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
    public static void getShortestDistance() {
        double smallest = distance1;
        if (distance2 < smallest) smallest = distance2;
        if (distance3 < smallest) smallest = distance3;
        if (distance4 < smallest) smallest = distance4;
        if (smallest != distance1) blinkyGoRight = false;
        if (smallest != distance2) blinkyGoUp = false;
        if (smallest != distance3) blinkyGoDown = false;
        if (smallest != distance4) blinkyGoLeft = false;
    }

    public static void getReverseDirection() {

        double biggest = 0;
        if (distance1 > biggest && distance1 != 10000) biggest = distance1;
        if (distance2 > biggest && distance2 != 10000) biggest = distance2;
        if (distance3 > biggest && distance3 != 10000) biggest = distance3;
        if (distance4 > biggest && distance4 != 10000) biggest = distance4;
        if (biggest != distance1) blinkyGoRight = false;
        if (biggest != distance2) blinkyGoUp = false;
        if (biggest != distance3) blinkyGoDown = false;
        if (biggest != distance4) blinkyGoLeft = false;
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
