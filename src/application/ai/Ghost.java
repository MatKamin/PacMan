package application.ai;

import java.util.Timer;

import static application.gameMechanics.*;
import static application.main.widthOneBlock;
import static application.mapReader.*;

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






    public static Timer scatterTimer = new Timer();

    public static void scatterModeTimer() {
        inScatterMode = true;

        try {
            scatterTimer.schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (inScatterMode && !inScaredModeBlinky && !inScaredModePinky) {
                                if (chaseCount < 5) {
                                    inScatterMode = false;
                                    scatterCount++;
                                    chaseModeTimer();
                                }
                            }
                            scatterTimer.cancel();
                            scatterTimer = new Timer();
                        }
                    },
                    scatterTime
            );
        } catch (IllegalStateException a){
            a.printStackTrace();
        }
    }

    public static Timer chaseTimer = new Timer();

    public static void chaseModeTimer() {
        inChaseMode = true;

        if (chaseCount != 3) {
            try {
                chaseTimer.schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (inChaseMode && !inScaredModeBlinky && !inScaredModePinky) {
                                    if (scatterCount < 4) {
                                        inChaseMode = false;
                                        chaseCount++;
                                        scatterModeTimer();
                                    }
                                }
                                chaseTimer.cancel();
                                chaseTimer = new Timer();
                            }
                        },
                        chaseTime
                );
            }catch (IllegalStateException a){
                a.printStackTrace();
            }

        }
    }




    public static void getRandomDirection(double d1, double d2, double d3, double d4, String ghost) {
        int random = 0;

        try {
            random = (int)(Math.random() * 4 + 1);
        } catch (StackOverflowError e) {
            e.printStackTrace();
        }

        double direction = 0;


        if (random == 1 && d1 != 10000) {
            direction = d1;
        }
        if (random == 2 && d2 != 10000) {
            direction = d2;
        }
        if (random == 3 && d3 != 10000) {
            direction = d3;
        }
        if (random == 4 && d4 != 10000) {
            direction = d4;
        }

        if (direction == 0) {
            getRandomDirection(d1, d2, d3, d4, ghost);
            return;
        }

        if (direction != d1) {
            switch (ghost) {
                case "blinky" -> blinkyGoRight = false;
                case "pinky" -> pinkyGoRight = false;
            }
        }
        if (direction != d2) {
            switch (ghost) {
                case "blinky" -> blinkyGoUp = false;
                case "pinky" -> pinkyGoUp = false;
            }
        }
        if (direction != d3) {
            switch (ghost) {
                case "blinky" -> blinkyGoDown = false;
                case "pinky" -> pinkyGoDown = false;
            }
        }
        if (direction != d4) {
            switch (ghost) {
                case "blinky" -> blinkyGoLeft = false;
                case "pinky" -> pinkyGoLeft = false;
            }
        }
    }


    public static void getShortestDistance(double d1, double d2, double d3, double d4, String ghost) {
        double smallest = d1;
        if (d2 < smallest) smallest = d2;
        if (d3 < smallest) smallest = d3;
        if (d4 < smallest) smallest = d4;
        if (smallest != d1) {
            switch (ghost) {
                case "blinky" -> blinkyGoRight = false;
                case "pinky" -> pinkyGoRight = false;
            }
        }
        if (smallest != d2) {
            switch (ghost) {
                case "blinky" -> blinkyGoUp = false;
                case "pinky" -> pinkyGoUp = false;
            }
        }
        if (smallest != d3) {
            switch (ghost) {
                case "blinky" -> blinkyGoDown = false;
                case "pinky" -> pinkyGoDown = false;
            }
        }
        if (smallest != d4) {
            switch (ghost) {
                case "blinky" -> blinkyGoLeft = false;
                case "pinky" -> pinkyGoLeft = false;
            }
        }
    }


    public static void move(String ghost) {
        if (ghost.equals("blinky")) {
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
            return;
        }



        if (ghost.equals("pinky")) {
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

    }


    public static void blockImpossibleMoves(String ghost) {
        if (ghost.equals("blinky")) {
            if (ghostNotPossibleUp(blinkyColumn, blinkyRow)) blinkyGoUp = false;
            if (ghostNotPossibleDown(blinkyColumn, blinkyRow)) blinkyGoDown = false;
            if (ghostNotPossibleLeft(blinkyColumn, blinkyRow)) blinkyGoLeft = false;
            if (ghostNotPossibleRight(blinkyColumn, blinkyRow)) blinkyGoRight = false;
            return;
        }

        if (ghost.equals("pinky")) {
            if (ghostNotPossibleUp(pinkyColumn, pinkyRow)) pinkyGoUp = false;
            if (ghostNotPossibleDown(pinkyColumn, pinkyRow)) pinkyGoDown = false;
            if (ghostNotPossibleLeft(pinkyColumn, pinkyRow)) pinkyGoLeft = false;
            if (ghostNotPossibleRight(pinkyColumn, pinkyRow)) pinkyGoRight = false;
        }

    }


    public static void blockTurnAround(String ghost) {
        if (ghost.equals("blinky")) {
            if (blinkyGoingRight) blinkyGoLeft = false;
            if (blinkyGoingLeft) blinkyGoRight = false;
            if (blinkyGoingUp) blinkyGoDown = false;
            if (blinkyGoingDown) blinkyGoUp = false;
            return;
        }

        if (ghost.equals("pinky")) {
            if (pinkyGoingRight) pinkyGoLeft = false;
            if (pinkyGoingLeft) pinkyGoRight = false;
            if (pinkyGoingUp) pinkyGoDown = false;
            if (pinkyGoingDown) pinkyGoUp = false;
        }
    }

    public static void getMovingDirection(String ghost) {
        if (ghost.equals("blinky")) {
            if (velocityBlinkyHorizontal > 0) blinkyGoingRight = true;
            if (velocityBlinkyHorizontal < 0) blinkyGoingLeft = true;
            if (velocityBlinkyVertical < 0) blinkyGoingUp = true;
            if (velocityBlinkyVertical > 0) blinkyGoingDown = true;
            return;
        }

        if (ghost.equals("pinky")) {
            if (velocityPinkyHorizontal > 0) pinkyGoingRight = true;
            if (velocityPinkyHorizontal < 0) pinkyGoingLeft = true;
            if (velocityPinkyVertical < 0) pinkyGoingUp = true;
            if (velocityPinkyVertical > 0) pinkyGoingDown = true;
        }
    }


    public static void allowTeleport(String ghost) {
        if (ghost.equals("blinky")) {
            if (velocityBlinkyHorizontal > 0) {
                if (blinkyColumnNew + 1 > blockCountHorizontally) {
                    // Teleport right to left
                    blinkyXPos = widthOneBlock;
                    blinkyColumnNew = 0;
                    blinkyColumn = 0;
                }
            }
            if (velocityBlinkyHorizontal < 0) {
                if (blinkyColumnNew - 2 < 0) {
                    // Teleport left to right
                    blinkyXPos = blockCountHorizontally * widthOneBlock;
                    blinkyColumnNew = blockCountHorizontally - 1;
                    blinkyColumn = blockCountHorizontally - 1;
                }
            }
            return;
        }

        if (ghost.equals("pinky")) {
            if (velocityPinkyHorizontal > 0) {
                if (pinkyColumnNew + 1 > blockCountHorizontally) {
                    // Teleport right to left
                    pinkyXPos = widthOneBlock;
                    pinkyColumnNew = 0;
                    pinkyColumn = 0;
                }
            }
            if (velocityPinkyHorizontal < 0) {
                if (pinkyColumnNew - 2 < 0) {
                    // Teleport left to right
                    pinkyXPos = blockCountHorizontally * widthOneBlock;
                    pinkyColumnNew = blockCountHorizontally - 1;
                    pinkyColumn = blockCountHorizontally - 1;
                }
            }
        }
    }




    public static boolean ghostNotPossibleUp(double column, double row) {
        return notAllowedBox[(int) column][(int) row - 1];
    }


    public static boolean ghostNotPossibleDown(double column, double row) {
        return notAllowedBox[(int) column][(int) row + 1];
    }


    public static boolean ghostNotPossibleLeft(double column, double row) {
        return notAllowedBox[(int) column - 1][(int) row];
    }


    public static boolean ghostNotPossibleRight(double column, double row) {
        return notAllowedBox[(int) column + 1][(int) row];
    }


}
