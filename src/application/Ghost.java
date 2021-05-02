package application;

import static application.gameMechanics.notAllowedBox;
import static application.main.widthOneBlock;
import static application.mapReader.*;
import static application.mapReader.blockCountHorizontally;

public class Ghost {

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



    public static void getReverseDirectionBlinky() {
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

    public static void getShortestDistanceBlinky() {
        double smallest = distance1;
        if (distance2 < smallest) smallest = distance2;
        if (distance3 < smallest) smallest = distance3;
        if (distance4 < smallest) smallest = distance4;
        if (smallest != distance1) blinkyGoRight = false;
        if (smallest != distance2) blinkyGoUp = false;
        if (smallest != distance3) blinkyGoDown = false;
        if (smallest != distance4) blinkyGoLeft = false;
    }


    public static void moveBlinky() {
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


    public static void blockImpossibleMovesBlinky() {
        if (ghostNotPossibleUp(blinkyColumn, blinkyRow)) blinkyGoUp = false;
        if (ghostNotPossibleDown(blinkyColumn, blinkyRow)) blinkyGoDown = false;
        if (ghostNotPossibleLeft(blinkyColumn, blinkyRow)) blinkyGoLeft = false;
        if (ghostNotPossibleRight(blinkyColumn, blinkyRow)) blinkyGoRight = false;
    }

    public static void blockTurnAroundBlinky() {
        if (blinkyGoingRight) blinkyGoLeft = false;
        if (blinkyGoingLeft) blinkyGoRight = false;
        if (blinkyGoingUp) blinkyGoDown = false;
        if (blinkyGoingDown) blinkyGoUp = false;
    }

    public static void getMovingDirectionBlinky() {
        if (velocityBlinkyHorizontal > 0) blinkyGoingRight = true;
        if (velocityBlinkyHorizontal < 0) blinkyGoingLeft = true;
        if (velocityBlinkyVertical < 0) blinkyGoingUp = true;
        if (velocityBlinkyVertical > 0) blinkyGoingDown = true;
    }


    public static void allowTeleportBlinky() {
        if (velocityBlinkyHorizontal > 0) {
            if (blinkyColumnNew + 1 > blockCountHorizontally) {
                // Teleport right to left
                blinkyXPos = widthOneBlock;
                blinkyColumnNew = 0;
                blinkyColumn = 0;
            }
        }
        if (velocityBlinkyVertical < 0) {
            if (blinkyColumnNew - 2 < 0) {
                // Teleport left to right
                blinkyXPos = blockCountHorizontally * widthOneBlock;
                blinkyColumnNew = blockCountHorizontally - 1;
                blinkyColumn = blockCountHorizontally - 1;
            }
        }
    }




    static int pinkyColumnNew;
    static int pinkyRowNew;

    static boolean pinkyGoingUp;
    static boolean pinkyGoingDown;
    static boolean pinkyGoingLeft;
    static boolean pinkyGoingRight;


    static double velocityPinkyVertical = 0;
    static double velocityPinkyHorizontal = 1;

    static boolean pinkyGoRight = true;
    static boolean pinkyGoLeft = true;
    static boolean pinkyGoUp = true;
    static boolean pinkyGoDown = true;

    static double distance1pinky = 10000;
    static double distance2pinky = 10000;
    static double distance3pinky = 10000;
    static double distance4pinky = 10000;

    public static void getReverseDirectionPinky() {
        double biggest = 0;
        if (distance1pinky > biggest && distance1pinky != 10000) biggest = distance1pinky;
        if (distance2pinky > biggest && distance2pinky != 10000) biggest = distance2pinky;
        if (distance3pinky > biggest && distance3pinky != 10000) biggest = distance3pinky;
        if (distance4pinky > biggest && distance4pinky != 10000) biggest = distance4pinky;
        if (biggest != distance1pinky) pinkyGoRight = false;
        if (biggest != distance2pinky) pinkyGoUp = false;
        if (biggest != distance3pinky) pinkyGoDown = false;
        if (biggest != distance4pinky) pinkyGoLeft = false;
    }

    public static void getShortestDistancePinky() {
        double smallest = distance1pinky;
        if (distance2pinky < smallest) smallest = distance2pinky;
        if (distance3pinky < smallest) smallest = distance3pinky;
        if (distance4pinky < smallest) smallest = distance4pinky;
        if (smallest != distance1pinky) pinkyGoRight = false;
        if (smallest != distance2pinky) pinkyGoUp = false;
        if (smallest != distance3pinky) pinkyGoDown = false;
        if (smallest != distance4pinky) pinkyGoLeft = false;
    }


    public static void movePinky() {
        if (pinkyGoLeft) {
            velocityPinkyVertical = 0;
            velocityPinkyHorizontal = -1;
        } else if (pinkyGoRight) {
            velocityPinkyVertical = 0;
            velocityPinkyHorizontal = 1;
        } else if (pinkyGoDown) {
            velocityPinkyVertical = 1;
            velocityPinkyHorizontal = 0;
        } else if (pinkyGoUp) {
            velocityPinkyVertical = -1;
            velocityPinkyHorizontal = 0;
        }
    }


    public static void blockImpossibleMovesPinky() {
        if (ghostNotPossibleUp(pinkyColumn, pinkyRow)) pinkyGoUp = false;
        if (ghostNotPossibleDown(pinkyColumn, pinkyRow)) pinkyGoDown = false;
        if (ghostNotPossibleLeft(pinkyColumn, pinkyRow)) pinkyGoLeft = false;
        if (ghostNotPossibleRight(pinkyColumn, pinkyRow)) pinkyGoRight = false;
    }

    public static void blockTurnAroundPinky() {
        if (pinkyGoingRight) pinkyGoLeft = false;
        if (pinkyGoingLeft) pinkyGoRight = false;
        if (pinkyGoingUp) pinkyGoDown = false;
        if (pinkyGoingDown) pinkyGoUp = false;
    }

    public static void getMovingDirectionPinky() {
        if (velocityPinkyHorizontal > 0) pinkyGoingRight = true;
        if (velocityPinkyHorizontal < 0) pinkyGoingLeft = true;
        if (velocityPinkyVertical < 0) pinkyGoingUp = true;
        if (velocityPinkyVertical > 0) pinkyGoingDown = true;
    }


    public static void allowTeleportPinky() {
        if (velocityPinkyHorizontal > 0) {
            if (pinkyColumnNew + 1 > blockCountHorizontally) {
                // Teleport right to left
                pinkyXPos = widthOneBlock;
                pinkyColumnNew = 0;
                pinkyColumn = 0;
            }
        }
        if (velocityPinkyVertical < 0) {
            if (pinkyColumnNew - 2 < 0) {
                // Teleport left to right
                pinkyXPos = blockCountHorizontally * widthOneBlock;
                pinkyColumnNew = blockCountHorizontally - 1;
                pinkyColumn = blockCountHorizontally - 1;
            }
        }
    }


    public static boolean ghostNotPossibleUp(double column, double row) { return notAllowedBox[(int) column][(int) row - 1]; }


    public static boolean ghostNotPossibleDown(double column, double row) { return notAllowedBox[(int) column][(int) row + 1]; }


    public static boolean ghostNotPossibleLeft(double column, double row) { return notAllowedBox[(int) column - 1][(int) row]; }


    public static boolean ghostNotPossibleRight(double column, double row) { return notAllowedBox[(int) column + 1][(int) row]; }


}
