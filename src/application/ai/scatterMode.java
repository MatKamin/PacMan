package application.ai;

import application.ai.Ghost;
import javafx.scene.Group;

import static application.imageViewerVariables.*;
import static application.main.*;
import static application.mapReader.*;

public class scatterMode extends Ghost {

    /**
     * Animates Ghosts
     * TODO: Ghost AI remaining
     */
    public static void ghostAnimate(Group gameLayout, String ghost) {
        scatterModeTimer();
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

        if (ghost.equals("inky")) {
            gameLayout.getChildren().remove(viewScaredInky);
            gameLayout.getChildren().remove(viewInky);
            gameLayout.getChildren().add(viewInky);

            // Inky
            viewInky.setX(inkyXPos);
            viewInky.setY(inkyYPos);
            inkyXPos += velocityInkyHorizontal;
            inkyYPos += velocityInkyVertical;

            calculateNextMove("inky");
            allowTeleport("inky");
        }
    }



    public static void calculateDistances(String ghost) {
        if (ghost.equals("blinky")) {
            // TARGET SCATTER MODE
            // COLUMN: 26
            // ROW: 1

            if ((blinkyRow == 18 || blinkyRow == 19) && (blinkyColumn == 14 || blinkyColumn == 15)) {
                distance2 = 0;
                return;
            }

            if (blinkyGoRight) distance1 = Math.pow(Math.abs((blinkyColumn + 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
            if (blinkyGoUp) distance2 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow - 1) - 1 - 1), 2);
            if (blinkyGoDown) distance3 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow + 1) - 1 - 1), 2);
            if (blinkyGoLeft) distance4 = Math.pow(Math.abs((blinkyColumn - 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
            return;
        }


        if (ghost.equals("pinky")) {
            // TARGET SCATTER MODE
            // COLUMN: 2
            // ROW: 1

            if ((pinkyRow == 18 || pinkyRow == 19) && (pinkyColumn == 14 || pinkyColumn == 15)) {
                distance2pinky = 0;
                return;
            }

            if (pinkyGoRight) distance1pinky = Math.pow(Math.abs((pinkyColumn + 1) - 2 - 1), 2) + Math.pow(Math.abs(pinkyRow - 1), 2);
            if (pinkyGoUp) distance2pinky = Math.pow(Math.abs(pinkyColumn - 2), 2) + Math.pow(Math.abs((pinkyRow - 1) - 1 - 1), 2);
            if (pinkyGoDown) distance3pinky = Math.pow(Math.abs(pinkyColumn - 2), 2) + Math.pow(Math.abs((pinkyRow + 1) - 1 - 1), 2);
            if (pinkyGoLeft) distance4pinky = Math.pow(Math.abs((pinkyColumn - 1) - 2 - 1), 2) + Math.pow(Math.abs(pinkyRow - 1), 2);
            return;
        }

        if (ghost.equals("clyde")) {
            // TARGET SCATTER MODE
            // COLUMN: 0
            // ROW: 37

            if ((clydeRow == 18 || clydeRow == 19) && (clydeColumn == 14 || clydeColumn == 15)) {
                distance2clyde = 0;
                return;
            }

            if (clydeColumn > 10 && clydeColumn < 17 && clydeRow > 14 && clydeRow < 20) {
                if (clydeGoRight) distance1clyde = Math.pow(Math.abs((clydeColumn + 1) - 26 - 1), 2) + Math.pow(Math.abs(clydeRow - 1), 2);
                if (clydeGoUp) distance2clyde = Math.pow(Math.abs(clydeColumn - 26), 2) + Math.pow(Math.abs((clydeRow - 1) - 1 - 1), 2);
                if (clydeGoDown) distance3clyde = Math.pow(Math.abs(clydeColumn - 26), 2) + Math.pow(Math.abs((clydeRow + 1) - 1 - 1), 2);
                if (clydeGoLeft) distance4clyde = Math.pow(Math.abs((clydeColumn - 1) - 26 - 1), 2) + Math.pow(Math.abs(clydeRow - 1), 2);
            } else {
                if (clydeGoRight) distance1clyde = Math.pow(Math.abs((clydeColumn + 1) - 0 - 1), 2) + Math.pow(Math.abs(clydeRow - 37), 2);
                if (clydeGoUp) distance2clyde = Math.pow(Math.abs(clydeColumn - 0), 2) + Math.pow(Math.abs((clydeRow - 1) - 37 - 1), 2);
                if (clydeGoDown) distance3clyde = Math.pow(Math.abs(clydeColumn - 0), 2) + Math.pow(Math.abs((clydeRow + 1) - 37 - 1), 2);
                if (clydeGoLeft) distance4clyde = Math.pow(Math.abs((clydeColumn - 1) - 0 - 1), 2) + Math.pow(Math.abs(clydeRow - 37), 2);
            }
        }

        if (ghost.equals("inky")) {
            // TARGET SCATTER MODE
            // COLUMN: 27
            // ROW: 37

            if ((inkyRow == 18 || inkyRow == 19) && (inkyColumn == 14 || inkyColumn == 15)) {
                distance2inky = 0;
                return;
            }

            if (inkyColumn > 10 && inkyColumn < 17 && inkyRow > 14 && inkyRow < 20) {
                if (inkyGoRight) distance1inky = Math.pow(Math.abs((inkyColumn + 1) - 26 - 1), 2) + Math.pow(Math.abs(inkyRow - 1), 2);
                if (inkyGoUp) distance2inky = Math.pow(Math.abs(inkyColumn - 26), 2) + Math.pow(Math.abs((inkyRow - 1) - 1 - 1), 2);
                if (inkyGoDown) distance3inky = Math.pow(Math.abs(inkyColumn - 26), 2) + Math.pow(Math.abs((inkyRow + 1) - 1 - 1), 2);
                if (inkyGoLeft) distance4inky = Math.pow(Math.abs((inkyColumn - 1) - 26 - 1), 2) + Math.pow(Math.abs(inkyRow - 1), 2);
            } else {
                if (inkyGoRight) distance1inky = Math.pow(Math.abs((inkyColumn + 1) - 27 - 1), 2) + Math.pow(Math.abs(inkyRow - 37), 2);
                if (inkyGoUp) distance2inky = Math.pow(Math.abs(inkyColumn - 27), 2) + Math.pow(Math.abs((inkyRow - 1) - 37 - 1), 2);
                if (inkyGoDown) distance3inky = Math.pow(Math.abs(inkyColumn - 27), 2) + Math.pow(Math.abs((inkyRow + 1) - 37 - 1), 2);
                if (inkyGoLeft) distance4inky = Math.pow(Math.abs((inkyColumn - 1) - 27 - 1), 2) + Math.pow(Math.abs(inkyRow - 37), 2);
            }
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



        if (ghost.equals("inky")) {
            inkyRowNew = (int) Math.round((inkyYPos + characterHeight / 2) / widthOneBlock);
            inkyColumnNew = (int) Math.round(((inkyXPos - characterWidth / 2) / heightOneBlock));

            if (velocityInkyHorizontal < 0 || velocityInkyVertical > 0) {
                inkyRowNew = (int) Math.round((inkyYPos - characterHeight / 2) / widthOneBlock);
                inkyColumnNew = (int) Math.round(((inkyXPos + characterWidth / 2) / heightOneBlock));
            }

            inkyGoingRight = false;
            inkyGoingLeft = false;
            inkyGoingUp = false;
            inkyGoingDown = false;

            inkyGoRight = true;
            inkyGoLeft = true;
            inkyGoUp = true;
            inkyGoDown = true;

            distance1inky = 10000;
            distance2inky = 10000;
            distance3inky = 10000;
            distance4inky = 10000;


            if (inkyColumnNew == inkyColumn + 1 || inkyColumnNew == inkyColumn - 1) {
                inkyColumn = inkyColumnNew;
                getMovingDirection("inky");
                blockTurnAround("inky");
                blockImpossibleMoves("inky");
                calculateDistances("inky");
                getShortestDistance(distance1inky, distance2inky, distance3inky, distance4inky, "inky");
                move("inky");
            }
            if (inkyRowNew == inkyRow + 1 || inkyRowNew == inkyRow - 1) {
                inkyRow = inkyRowNew;
                getMovingDirection("inky");
                blockTurnAround("inky");
                blockImpossibleMoves("inky");
                calculateDistances("inky");
                getShortestDistance(distance1inky, distance2inky, distance3inky, distance4inky, "inky");
                move("inky");
            }
        }
    }
}
