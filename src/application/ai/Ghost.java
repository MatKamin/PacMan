package application.ai;

import java.util.Timer;

import static application.gameMechanics.*;
import static application.main.*;
import static application.mapReader.*;

public class Ghost {

    //---------------------------------VARIABLES---------------------------------\\

    //-----------------------BLINKY-----------------------\\
    static int blinkyColumnNew;
    static int blinkyRowNew;

    static boolean blinkyGoingUp;
    static boolean blinkyGoingDown;
    static boolean blinkyGoingLeft;
    static boolean blinkyGoingRight;


    public static double velocityBlinkyVertical = 0;
    public static double velocityBlinkyHorizontal = 1;

    static boolean blinkyGoRight = true;
    static boolean blinkyGoLeft = true;
    static boolean blinkyGoUp = true;
    static boolean blinkyGoDown = true;

    static double distance1 = 10000;
    static double distance2 = 10000;
    static double distance3 = 10000;
    static double distance4 = 10000;

    public static double blinkyRow;
    public static double blinkyColumn;
    public static double blinkyColumnStart;
    public static double blinkyRowStart;
    public static double blinkyXPos;
    public static double blinkyYPos;
    public static double blinkyXPosStarting;
    public static double blinkyYPosStarting;




    //-----------------------PINKY-----------------------\\

    static int pinkyColumnNew;
    static int pinkyRowNew;

    static boolean pinkyGoingUp;
    static boolean pinkyGoingDown;
    static boolean pinkyGoingLeft;
    static boolean pinkyGoingRight;


    public static double velocityPinkyVertical = 0;
    public static double velocityPinkyHorizontal = 1;

    static boolean pinkyGoRight = true;
    static boolean pinkyGoLeft = true;
    static boolean pinkyGoUp = true;
    static boolean pinkyGoDown = true;

    static double distance1pinky = 10000;
    static double distance2pinky = 10000;
    static double distance3pinky = 10000;
    static double distance4pinky = 10000;

    public static double pinkyRow;
    public static double pinkyColumn;
    public static double pinkyColumnStart;
    public static double pinkyRowStart;
    public static double pinkyXPos;
    public static double pinkyYPos;
    public static double pinkyXPosStarting;
    public static double pinkyYPosStarting;




    //-----------------------CLYDE-----------------------\\

    static int clydeColumnNew;
    static int clydeRowNew;

    static boolean clydeGoingUp;
    static boolean clydeGoingDown;
    static boolean clydeGoingLeft;
    static boolean clydeGoingRight;


    public static double velocityClydeVertical = 0;
    public static double velocityClydeHorizontal = 1;

    static boolean clydeGoRight = true;
    static boolean clydeGoLeft = true;
    static boolean clydeGoUp = true;
    static boolean clydeGoDown = true;

    static double distance1clyde = 10000;
    static double distance2clyde = 10000;
    static double distance3clyde = 10000;
    static double distance4clyde = 10000;

    public static double clydeRow;
    public static double clydeColumn;
    public static double clydeColumnStart;
    public static double clydeRowStart;
    public static double clydeXPos;
    public static double clydeYPos;
    public static double clydeXPosStarting;
    public static double clydeYPosStarting;




    //-----------------------INKY-----------------------\\

    static int inkyColumnNew;
    static int inkyRowNew;

    static boolean inkyGoingUp;
    static boolean inkyGoingDown;
    static boolean inkyGoingLeft;
    static boolean inkyGoingRight;


    public static double velocityInkyVertical = 0;
    public static double velocityInkyHorizontal = 1;

    static boolean inkyGoRight = true;
    static boolean inkyGoLeft = true;
    static boolean inkyGoUp = true;
    static boolean inkyGoDown = true;

    static double distance1inky = 10000;
    static double distance2inky = 10000;
    static double distance3inky = 10000;
    static double distance4inky = 10000;

    public static double inkyRow;
    public static double inkyColumn;
    public static double inkyColumnStart;
    public static double inkyRowStart;
    public static double inkyXPos;
    public static double inkyYPos;
    public static double inkyXPosStarting;
    public static double inkyYPosStarting;



    public static Timer scatterTimer = new Timer();

    /**
     * Timer controlling the Scatter Times
     */
    public static void scatterModeTimer() {
        inScatterMode = true;

        try {
            scatterTimer.schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (gameStarted && !inChaseMode && inScatterMode && !inScaredModeBlinky && !inScaredModePinky && !inScaredModeClyde && !inScaredModeInky) {
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
    /**
     * Timer controlling the Chase Times
     */
    public static void chaseModeTimer() {
        inChaseMode = true;

        if (chaseCount != 3) {
            try {
                chaseTimer.schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (gameStarted && inChaseMode && !inScatterMode && !inScaredModeBlinky && !inScaredModePinky && !inScaredModeClyde & !inScaredModeInky) {
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
            } catch (IllegalStateException a){
                a.printStackTrace();
            }

        }
    }


    /**
     * Used For Scared Mode
     * Chooses a random direction
     * @param d1 distance 1
     * @param d2 distance 2
     * @param d3 distance 3
     * @param d4 distance 4
     * @param ghost lowercase name of the ghost
     */
    public static void getRandomDirection(double d1, double d2, double d3, double d4, String ghost) {
        int random = 0;

        try {
            random = (int)(Math.random() * 4 + 1);
        } catch (Exception ignore) { }

        double direction = 0;


        if (random == 1 && d1 != 10000) direction = d1;
        if (random == 2 && d2 != 10000) direction = d2;
        if (random == 3 && d3 != 10000) direction = d3;
        if (random == 4 && d4 != 10000) direction = d4;

        if (direction == 0) {
            try {
                try {
                    random = (int)(Math.random() * 4 + 1);
                } catch (Exception ignore) { }
                direction = 0;
                if (random == 1 && d1 != 10000) direction = d1;
                if (random == 2 && d2 != 10000) direction = d2;
                if (random == 3 && d3 != 10000) direction = d3;
                if (random == 4 && d4 != 10000) direction = d4;
            } catch (Exception ignore) { }
            return;
        }

        if (direction != d1) {
            switch (ghost) {
                case "blinky" -> blinkyGoRight = false;
                case "pinky" -> pinkyGoRight = false;
                case "clyde" -> clydeGoRight = false;
                case "inky" -> inkyGoRight = false;
            }
        }
        if (direction != d2) {
            switch (ghost) {
                case "blinky" -> blinkyGoUp = false;
                case "pinky" -> pinkyGoUp = false;
                case "clyde" -> clydeGoUp = false;
                case "inky" -> inkyGoUp = false;
            }
        }
        if (direction != d3) {
            switch (ghost) {
                case "blinky" -> blinkyGoDown = false;
                case "pinky" -> pinkyGoDown = false;
                case "clyde" -> clydeGoDown = false;
                case "inky" -> inkyGoDown = false;
            }
        }
        if (direction != d4) {
            switch (ghost) {
                case "blinky" -> blinkyGoLeft = false;
                case "pinky" -> pinkyGoLeft = false;
                case "clyde" -> clydeGoLeft = false;
                case "inky" -> inkyGoLeft = false;
            }
        }
    }


    /**
     * Chooses the shortest Distance
     * @param d1 distance 1
     * @param d2 distance 2
     * @param d3 distance 3
     * @param d4 distance 4
     * @param ghost lowercase name of the ghost
     */
    public static void getShortestDistance(double d1, double d2, double d3, double d4, String ghost) {
        double smallest = d1;
        if (d3 < smallest) smallest = d3;
        if (d4 < smallest) smallest = d4;
        if (d2 <= smallest) smallest = d2;

        if (smallest != d1) {
            switch (ghost) {
                case "blinky" -> blinkyGoRight = false;
                case "pinky" -> pinkyGoRight = false;
                case "clyde" -> clydeGoRight = false;
                case "inky" -> inkyGoRight = false;
            }
        }
        if (smallest != d2) {
            switch (ghost) {
                case "blinky" -> blinkyGoUp = false;
                case "pinky" -> pinkyGoUp = false;
                case "clyde" -> clydeGoUp = false;
                case "inky" -> inkyGoUp = false;
            }
        }
        if (smallest != d3) {
            switch (ghost) {
                case "blinky" -> blinkyGoDown = false;
                case "pinky" -> pinkyGoDown = false;
                case "clyde" -> clydeGoDown = false;
                case "inky" -> inkyGoDown = false;
            }
        }
        if (smallest != d4) {
            switch (ghost) {
                case "blinky" -> blinkyGoLeft = false;
                case "pinky" -> pinkyGoLeft = false;
                case "clyde" -> clydeGoLeft = false;
                case "inky" -> inkyGoLeft = false;
            }
        }
    }


    /**
     * Gives Ghost the correct velocity
     * @param ghost lowercase name of the Ghost
     */
    public static void move(String ghost) {
        if (ghost.equals("blinky")) {
            if (blinkyGoLeft) {
                velocityBlinkyVertical = 0;
                velocityBlinkyHorizontal = -1 - velocityAdder;
            } else if (blinkyGoRight) {
                velocityBlinkyVertical = 0;
                velocityBlinkyHorizontal = 1 + velocityAdder;
            } else if (blinkyGoDown) {
                velocityBlinkyVertical = 1 + velocityAdder;
                velocityBlinkyHorizontal = 0;
            } else if (blinkyGoUp) {
                velocityBlinkyVertical = -1 - velocityAdder;
                velocityBlinkyHorizontal = 0;
            }
            return;
        }

        if (ghost.equals("pinky")) {
            if (pinkyGoLeft) {
                velocityPinkyVertical = 0;
                velocityPinkyHorizontal = -1 - velocityAdder;
            } else if (pinkyGoRight) {
                velocityPinkyVertical = 0;
                velocityPinkyHorizontal = 1 + velocityAdder;
            } else if (pinkyGoDown) {
                velocityPinkyVertical = 1 + velocityAdder;
                velocityPinkyHorizontal = 0;
            } else if (pinkyGoUp) {
                velocityPinkyVertical = -1 - velocityAdder;
                velocityPinkyHorizontal = 0;
            }
        }

        if (ghost.equals("clyde")) {
            if (clydeGoLeft) {
                velocityClydeVertical = 0;
                velocityClydeHorizontal = -1 - velocityAdder;
            } else if (clydeGoRight) {
                velocityClydeVertical = 0;
                velocityClydeHorizontal = 1 + velocityAdder;
            } else if (clydeGoDown) {
                velocityClydeVertical = 1 + velocityAdder;
                velocityClydeHorizontal = 0;
            } else if (clydeGoUp) {
                velocityClydeVertical = -1 - velocityAdder;
                velocityClydeHorizontal = 0;
            }
        }

        if (ghost.equals("inky")) {
            if (inkyGoLeft) {
                velocityInkyVertical = 0;
                velocityInkyHorizontal = -1 - velocityAdder;
            } else if (inkyGoRight) {
                velocityInkyVertical = 0;
                velocityInkyHorizontal = 1 + velocityAdder;
            } else if (inkyGoDown) {
                velocityInkyVertical = 1 + velocityAdder;
                velocityInkyHorizontal = 0;
            } else if (inkyGoUp) {
                velocityInkyVertical = -1 - velocityAdder;
                velocityInkyHorizontal = 0;
            }
        }
    }


    /**
     * Blocks Moves which result in hitting the wall
     * @param ghost lowercase name of the Ghost
     */
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
        if (ghost.equals("clyde")) {
            if (ghostNotPossibleUp(clydeColumn, clydeRow)) clydeGoUp = false;
            if (ghostNotPossibleDown(clydeColumn, clydeRow)) clydeGoDown = false;
            if (ghostNotPossibleLeft(clydeColumn, clydeRow)) clydeGoLeft = false;
            if (ghostNotPossibleRight(clydeColumn, clydeRow)) clydeGoRight = false;
        }
        if (ghost.equals("inky")) {
            if (ghostNotPossibleUp(inkyColumn, inkyRow)) inkyGoUp = false;
            if (ghostNotPossibleDown(inkyColumn, inkyRow)) inkyGoDown = false;
            if (ghostNotPossibleLeft(inkyColumn, inkyRow)) inkyGoLeft = false;
            if (ghostNotPossibleRight(inkyColumn, inkyRow)) inkyGoRight = false;
        }
    }


    /**
     * Blocks moves which result in turning around
     * (Illegal Ghost move in PacMan)
     * @param ghost lowercase name of the ghost
     */
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
        if (ghost.equals("clyde")) {
            if (clydeGoingRight) clydeGoLeft = false;
            if (clydeGoingLeft) clydeGoRight = false;
            if (clydeGoingUp) clydeGoDown = false;
            if (clydeGoingDown) clydeGoUp = false;
        }
        if (ghost.equals("inky")) {
            if (inkyGoingRight) inkyGoLeft = false;
            if (inkyGoingLeft) inkyGoRight = false;
            if (inkyGoingUp) inkyGoDown = false;
            if (inkyGoingDown) inkyGoUp = false;
        }
    }


    /**
     * Gives moving direction by setting the right boolean to true
     * @param ghost lowercase name of the Ghost
     */
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
        if (ghost.equals("clyde")) {
            if (velocityClydeHorizontal > 0) clydeGoingRight = true;
            if (velocityClydeHorizontal < 0) clydeGoingLeft = true;
            if (velocityClydeVertical < 0) clydeGoingUp = true;
            if (velocityClydeVertical > 0) clydeGoingDown = true;
        }
        if (ghost.equals("inky")) {
            if (velocityInkyHorizontal > 0) inkyGoingRight = true;
            if (velocityInkyHorizontal < 0) inkyGoingLeft = true;
            if (velocityInkyVertical < 0) inkyGoingUp = true;
            if (velocityInkyVertical > 0) inkyGoingDown = true;
        }
    }


    /**
     * Allows Teleporting Ghosts on Exits
     * @param ghost lowercase name of the Ghost
     */
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
        if (ghost.equals("clyde")) {
            if (velocityClydeHorizontal > 0) {
                if (clydeColumnNew + 1 > blockCountHorizontally) {
                    // Teleport right to left
                    clydeXPos = widthOneBlock;
                    clydeColumnNew = 0;
                    clydeColumn = 0;
                }
            }
            if (velocityClydeHorizontal < 0) {
                if (clydeColumnNew - 2 < 0) {
                    // Teleport left to right
                    clydeXPos = blockCountHorizontally * widthOneBlock;
                    clydeColumnNew = blockCountHorizontally - 1;
                    clydeColumn = blockCountHorizontally - 1;
                }
            }
        }
        if (ghost.equals("inky")) {
            if (velocityInkyHorizontal > 0) {
                if (inkyColumnNew + 1 > blockCountHorizontally) {
                    // Teleport right to left
                    inkyXPos = widthOneBlock;
                    inkyColumnNew = 0;
                    inkyColumn = 0;
                }
            }
            if (velocityInkyHorizontal < 0) {
                if (inkyColumnNew - 2 < 0) {
                    // Teleport left to right
                    inkyXPos = blockCountHorizontally * widthOneBlock;
                    inkyColumnNew = blockCountHorizontally - 1;
                    inkyColumn = blockCountHorizontally - 1;
                }
            }
        }
    }


    /**
     * Checks if moving Up results in hitting wall
     * @param column Column to check
     * @param row    Row to check
     * @return       True / False
     */
    public static boolean ghostNotPossibleUp(double column, double row) {
        return notAllowedBox[(int) column][(int) row - 1];
    }

    /**
     * Checks if moving Down results in hitting wall
     * @param column Column to check
     * @param row    Row to check
     * @return       True / False
     */
    public static boolean ghostNotPossibleDown(double column, double row) {
        return notAllowedBox[(int) column][(int) row + 1];
    }

    /**
     * Checks if moving Left results in hitting wall
     * @param column Column to check
     * @param row    Row to check
     * @return       True / False
     */
    public static boolean ghostNotPossibleLeft(double column, double row) {
        return notAllowedBox[(int) column - 1][(int) row];
    }

    /**
     * Checks if moving Right results in hitting wall
     * @param column Column to check
     * @param row    Row to check
     * @return       True / False
     */
    public static boolean ghostNotPossibleRight(double column, double row) {
        return notAllowedBox[(int) column + 1][(int) row];
    }


}
