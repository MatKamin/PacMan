package application.ai;

import application.ai.Ghost;
import javafx.scene.Group;

import static application.gameMechanics.*;
import static application.gameMechanics.switchedToScaredBlinky;
import static application.imageViewerVariables.*;
import static application.main.*;
import static application.mapReader.*;

public class chaseMode extends Ghost {


    public static void ghostAnimate(Group gameLayout, String ghost) {
        chaseModeTimer();
        if (ghost.equals("blinky")) {
            gameLayout.getChildren().remove(viewScaredBlinky);
            gameLayout.getChildren().remove(viewBlinky);

            gameLayout.getChildren().add(viewBlinky);

            // Blinky
            viewBlinky.setX(blinkyXPos);
            viewBlinky.setY(blinkyYPos);
            blinkyXPos += velocityBlinkyHorizontal;
            blinkyYPos += velocityBlinkyVertical;


            calculateNextMove("blinky");
            allowTeleport("blinky");
            return;
        }


        if (ghost.equals("pinky")) {
            gameLayout.getChildren().remove(viewScaredPinky);
            gameLayout.getChildren().remove(viewPinky);

            gameLayout.getChildren().add(viewPinky);

            // Pinky
            viewPinky.setX(pinkyXPos);
            viewPinky.setY(pinkyYPos);
            pinkyXPos += velocityPinkyHorizontal;
            pinkyYPos += velocityPinkyVertical;


            calculateNextMove("pinky");
            allowTeleport("pinky");
            return;
        }

        if (ghost.equals("clyde")) {
            gameLayout.getChildren().remove(viewScaredClyde);
            gameLayout.getChildren().remove(viewClyde);

            gameLayout.getChildren().add(viewClyde);

            // Clyde
            viewClyde.setX(clydeXPos);
            viewClyde.setY(clydeYPos);
            clydeXPos += velocityClydeHorizontal;
            clydeYPos += velocityClydeVertical;


            calculateNextMove("clyde");
            allowTeleport("clyde");
        }
    }

    public static void calculateDistances(String ghost) {
        if (ghost.equals("blinky")) {
            // TARGET SCATTER MODE
            // COLUMN: 26
            // ROW: 1
            if (blinkyGoRight)
                distance1 = Math.pow(Math.abs((blinkyColumn + 1) - pacmanColumn - 1), 2) + Math.pow(Math.abs(blinkyRow - pacmanRow), 2);
            if (blinkyGoUp)
                distance2 = Math.pow(Math.abs(blinkyColumn - pacmanColumn), 2) + Math.pow(Math.abs((blinkyRow - 1) - pacmanRow - 1), 2);
            if (blinkyGoDown)
                distance3 = Math.pow(Math.abs(blinkyColumn - pacmanColumn), 2) + Math.pow(Math.abs((blinkyRow + 1) - pacmanRow - 1), 2);
            if (blinkyGoLeft)
                distance4 = Math.pow(Math.abs((blinkyColumn - 1) - pacmanColumn - 1), 2) + Math.pow(Math.abs(blinkyRow - pacmanRow), 2);
            return;
        }


        if (ghost.equals("pinky")) {
            // TARGET SCATTER MODE
            // COLUMN: 2
            // ROW: 1
            int vertical = 0;
            int horizontal = 0;

            if (pacmanFacingUp) {
                vertical = -4;
                horizontal = -4;
            }
            if (pacmanFacingDown) vertical = 4;
            if (pacmanFacingLeft) horizontal = -4;
            if (pacmanFacingRight) horizontal = 4;

            if (pinkyGoRight)
                distance1pinky = Math.pow(Math.abs((pinkyColumn + 1) - (pacmanColumn + horizontal) - 1), 2) + Math.pow(Math.abs(pinkyRow - (pacmanRow + vertical)), 2);
            if (pinkyGoUp)
                distance2pinky = Math.pow(Math.abs(pinkyColumn - (pacmanColumn + horizontal)), 2) + Math.pow(Math.abs((pinkyRow - 1) - (pacmanRow + vertical) - 1), 2);
            if (pinkyGoDown)
                distance3pinky = Math.pow(Math.abs(pinkyColumn - (pacmanColumn + horizontal)), 2) + Math.pow(Math.abs((pinkyRow + 1) - (pacmanRow + vertical) - 1), 2);
            if (pinkyGoLeft)
                distance4pinky = Math.pow(Math.abs((pinkyColumn - 1) - (pacmanColumn + horizontal) - 1), 2) + Math.pow(Math.abs(pinkyRow - (pacmanRow + vertical)), 2);
        }




        if (ghost.equals("clyde")) {
            // TARGET SCATTER MODE
            // COLUMN: 0
            // ROW: 34
            double targetColumn = pacmanColumn;
            double targetRow = pacmanRow;


            if (Math.pow(Math.abs(clydeColumn - pacmanColumn), 2) + Math.pow(Math.abs(clydeRow - pacmanRow), 2) <= 64) {
                targetColumn = 0;
                targetRow = 34;
            }


            if (clydeGoRight)
                distance1clyde = Math.pow(Math.abs((clydeColumn + 1) - targetColumn - 1), 2) + Math.pow(Math.abs(clydeRow - targetRow), 2);
            if (clydeGoUp)
                distance2clyde = Math.pow(Math.abs(clydeColumn - targetColumn), 2) + Math.pow(Math.abs((clydeRow - 1) - targetRow - 1), 2);
            if (clydeGoDown)
                distance3clyde = Math.pow(Math.abs(clydeColumn - targetColumn), 2) + Math.pow(Math.abs((clydeRow + 1) - targetRow - 1), 2);
            if (clydeGoLeft)
                distance4clyde = Math.pow(Math.abs((clydeColumn - 1) - targetColumn - 1), 2) + Math.pow(Math.abs(clydeRow - targetRow), 2);


        }
    }



    /**
     * Calculates next move
     */
    public static void calculateNextMove(String ghost) {
        if (ghost.equals("blinky")) {
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

            if (switchedToChaseBlinky) {
                getMovingDirection("blinky");
                if (blinkyGoingRight || blinkyGoingLeft) {
                    velocityBlinkyHorizontal *= -1;
                }
                if (blinkyGoingUp || blinkyGoingDown) {
                    velocityBlinkyVertical *= -1;
                }
                switchedToChaseBlinky = false;
            }

            if (blinkyColumnNew == blinkyColumn + 1 || blinkyColumnNew == blinkyColumn - 1) {
                blinkyColumn = blinkyColumnNew;
                getMovingDirection("blinky");
                blockTurnAround("blinky");
                blockImpossibleMoves("blinky");
                calculateDistances("blinky");
                getShortestDistance(distance1, distance2, distance3, distance4, "blinky");
                move("blinky");
            }
            if (blinkyRowNew == blinkyRow + 1 || blinkyRowNew == blinkyRow - 1) {
                blinkyRow = blinkyRowNew;
                getMovingDirection("blinky");
                blockTurnAround("blinky");
                blockImpossibleMoves("blinky");
                calculateDistances("blinky");
                getShortestDistance(distance1, distance2, distance3, distance4, "blinky");
                move("blinky");
            }
            return;
        }


        if (ghost.equals("pinky")) {
            pinkyRowNew = (int) Math.round((pinkyYPos + characterHeight / 2) / widthOneBlock);
            pinkyColumnNew = (int) Math.round(((pinkyXPos - characterWidth / 2) / heightOneBlock));

            if (velocityPinkyHorizontal < 0 || velocityPinkyVertical > 0) {
                pinkyRowNew = (int) Math.round((pinkyYPos - characterHeight / 2) / widthOneBlock);
                pinkyColumnNew = (int) Math.round(((pinkyXPos + characterWidth / 2) / heightOneBlock));
            }

            pinkyGoingRight = false;
            pinkyGoingLeft = false;
            pinkyGoingUp = false;
            pinkyGoingDown = false;

            pinkyGoRight = true;
            pinkyGoLeft = true;
            pinkyGoUp = true;
            pinkyGoDown = true;

            distance1pinky = 10000;
            distance2pinky = 10000;
            distance3pinky = 10000;
            distance4pinky = 10000;

            if (switchedToChasePinky) {
                getMovingDirection("pinky");
                if (pinkyGoingRight || pinkyGoingLeft) {
                    velocityPinkyHorizontal *= -1;
                }
                if (pinkyGoingUp || pinkyGoingDown) {
                    velocityPinkyVertical *= -1;
                }
                switchedToChasePinky = false;
            }

            if (pinkyColumnNew == pinkyColumn + 1 || pinkyColumnNew == pinkyColumn - 1) {
                pinkyColumn = pinkyColumnNew;
                getMovingDirection("pinky");
                blockTurnAround("pinky");
                blockImpossibleMoves("pinky");
                calculateDistances("pinky");
                getShortestDistance(distance1pinky, distance2pinky, distance3pinky, distance4pinky, "pinky");
                move("pinky");
            }
            if (pinkyRowNew == pinkyRow + 1 || pinkyRowNew == pinkyRow - 1) {
                pinkyRow = pinkyRowNew;
                getMovingDirection("pinky");
                blockTurnAround("pinky");
                blockImpossibleMoves("pinky");
                calculateDistances("pinky");
                getShortestDistance(distance1pinky, distance2pinky, distance3pinky, distance4pinky, "pinky");
                move("pinky");
            }
            return;
        }


        if (ghost.equals("clyde")) {
            clydeRowNew = (int) Math.round((clydeYPos + characterHeight / 2) / widthOneBlock);
            clydeColumnNew = (int) Math.round(((clydeXPos - characterWidth / 2) / heightOneBlock));

            if (velocityClydeHorizontal < 0 || velocityClydeVertical > 0) {
                clydeRowNew = (int) Math.round((clydeYPos - characterHeight / 2) / widthOneBlock);
                clydeColumnNew = (int) Math.round(((clydeXPos + characterWidth / 2) / heightOneBlock));
            }

            clydeGoingRight = false;
            clydeGoingLeft = false;
            clydeGoingUp = false;
            clydeGoingDown = false;

            clydeGoRight = true;
            clydeGoLeft = true;
            clydeGoUp = true;
            clydeGoDown = true;

            distance1clyde = 10000;
            distance2clyde = 10000;
            distance3clyde = 10000;
            distance4clyde = 10000;

            if (switchedToChaseClyde) {
                getMovingDirection("clyde");
                if (clydeGoingRight || clydeGoingLeft) {
                    velocityClydeHorizontal *= -1;
                }
                if (clydeGoingUp || clydeGoingDown) {
                    velocityClydeVertical *= -1;
                }
                switchedToChaseClyde = false;
            }

            if (clydeColumnNew == clydeColumn + 1 || clydeColumnNew == clydeColumn - 1) {
                clydeColumn = clydeColumnNew;
                getMovingDirection("clyde");
                blockTurnAround("clyde");
                blockImpossibleMoves("clyde");
                calculateDistances("clyde");
                getShortestDistance(distance1clyde, distance2clyde, distance3clyde, distance4clyde, "clyde");
                move("clyde");
            }
            if (clydeRowNew == clydeRow + 1 || clydeRowNew == clydeRow - 1) {
                clydeRow = clydeRowNew;
                getMovingDirection("clyde");
                blockTurnAround("clyde");
                blockImpossibleMoves("clyde");
                calculateDistances("clyde");
                getShortestDistance(distance1clyde, distance2clyde, distance3clyde, distance4clyde, "clyde");
                move("clyde");
            }
        }
    }
}
