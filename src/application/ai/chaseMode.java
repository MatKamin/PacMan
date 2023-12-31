package application.ai;

import javafx.scene.Group;

import static application.gameMechanics.*;
import static application.imageViewerVariables.*;
import static application.main.*;


public class chaseMode extends Ghost {

    /**
     * makes the ghost move
     * @param gameLayout Game Layout Group
     * @param ghost lower case name of the ghost
     */
    public static void ghostAnimate(Group gameLayout, String ghost) {
        chaseModeTimer();

        if (ghost.equals("blinky")) {
            gameLayout.getChildren().remove(viewScaredBlinky);
            gameLayout.getChildren().remove(viewBlinky);
            gameLayout.getChildren().add(viewBlinky);

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

            viewInky.setX(inkyXPos);
            viewInky.setY(inkyYPos);
            inkyXPos += velocityInkyHorizontal;
            inkyYPos += velocityInkyVertical;

            calculateNextMove("inky");
            allowTeleport("inky");
        }
    }

    /**
     * Calculates Distance to the target block
     * @param ghost lowercase name of the ghost
     */
    public static void calculateDistances(String ghost) {

        /*
          BLINKY
          Target block => PacMan
         */
        if (ghost.equals("blinky")) {
            // If in Ghosthouse -> go up
            if ((blinkyRow == 18 || blinkyRow == 19) && (blinkyColumn == 14 || blinkyColumn == 15)) {
                distance2 = 0;
                return;
            }

            // calculated with the pythagorean theorem
            if (blinkyGoRight) distance1 = Math.pow(Math.abs((blinkyColumn + 1) - pacmanColumn - 1), 2) + Math.pow(Math.abs(blinkyRow - pacmanRow), 2);
            if (blinkyGoUp) distance2 = Math.pow(Math.abs(blinkyColumn - pacmanColumn), 2) + Math.pow(Math.abs((blinkyRow - 1) - pacmanRow - 1), 2);
            if (blinkyGoDown) distance3 = Math.pow(Math.abs(blinkyColumn - pacmanColumn), 2) + Math.pow(Math.abs((blinkyRow + 1) - pacmanRow - 1), 2);
            if (blinkyGoLeft) distance4 = Math.pow(Math.abs((blinkyColumn - 1) - pacmanColumn - 1), 2) + Math.pow(Math.abs(blinkyRow - pacmanRow), 2);
            return;
        }


        /*
          PINKY
          Target block => 4 blocks in front of Pacman
             if pacman facing up => 4 blocks up and 4 blocks left
         */
        if (ghost.equals("pinky")) {
            int vertical = 0;       // vertical shift
            int horizontal = 0;     // horizontal shift

            // If in Ghosthouse -> go up
            if ((pinkyRow == 18 || pinkyRow == 19) && (pinkyColumn == 14 || pinkyColumn == 15)) {
                distance2pinky = 0;
                return;
            }


            /*
              4 Block shifts
             */
            if (pacmanFacingUp) {
                vertical = -4;
                horizontal = -4;
            }
            if (pacmanFacingDown) vertical = 4;
            if (pacmanFacingLeft) horizontal = -4;
            if (pacmanFacingRight) horizontal = 4;

            // calculated with the pythagorean theorem
            if (pinkyGoRight) distance1pinky = Math.pow(Math.abs((pinkyColumn + 1) - (pacmanColumn + horizontal) - 1), 2) + Math.pow(Math.abs(pinkyRow - (pacmanRow + vertical)), 2);
            if (pinkyGoUp) distance2pinky = Math.pow(Math.abs(pinkyColumn - (pacmanColumn + horizontal)), 2) + Math.pow(Math.abs((pinkyRow - 1) - (pacmanRow + vertical) - 1), 2);
            if (pinkyGoDown) distance3pinky = Math.pow(Math.abs(pinkyColumn - (pacmanColumn + horizontal)), 2) + Math.pow(Math.abs((pinkyRow + 1) - (pacmanRow + vertical) - 1), 2);
            if (pinkyGoLeft) distance4pinky = Math.pow(Math.abs((pinkyColumn - 1) - (pacmanColumn + horizontal) - 1), 2) + Math.pow(Math.abs(pinkyRow - (pacmanRow + vertical)), 2);
        }




        /*
          CLYDE
          Target block => PacMan
           if clyde is in the 8 block radius of pacman then target => Scatter Mode Target
         */
        if (ghost.equals("clyde")) {

            // If in Ghosthouse -> go up
            if ((clydeRow == 18 || clydeRow == 19) && (clydeColumn == 14 || clydeColumn == 15)) {
                distance2clyde = 0;
                return;
            }

            double targetColumn = pacmanColumn;
            double targetRow = pacmanRow;

            // If distance <= 64 then radius in blocks = 8
            // and target changes
            if (Math.pow(Math.abs(clydeColumn - pacmanColumn), 2) + Math.pow(Math.abs(clydeRow - pacmanRow), 2) <= 64) {
                targetColumn = 0;
                targetRow = 34;
            }

            // calculated with the pythagorean theorem
            if (clydeGoRight) distance1clyde = Math.pow(Math.abs((clydeColumn + 1) - targetColumn - 1), 2) + Math.pow(Math.abs(clydeRow - targetRow), 2);
            if (clydeGoUp) distance2clyde = Math.pow(Math.abs(clydeColumn - targetColumn), 2) + Math.pow(Math.abs((clydeRow - 1) - targetRow - 1), 2);
            if (clydeGoDown) distance3clyde = Math.pow(Math.abs(clydeColumn - targetColumn), 2) + Math.pow(Math.abs((clydeRow + 1) - targetRow - 1), 2);
            if (clydeGoLeft) distance4clyde = Math.pow(Math.abs((clydeColumn - 1) - targetColumn - 1), 2) + Math.pow(Math.abs(clydeRow - targetRow), 2);


        }



        /*
          INKY
          Target block => Line from Pacman to Blinky rotated 180 degrees around Pacman
         */
        if (ghost.equals("inky")) {

            // If in Ghosthouse -> go up
            if ((inkyRow == 18 || inkyRow == 19) && (inkyColumn == 14 || inkyColumn == 15)) {
                distance2inky = 0;
                return;
            }

            double targetColumn;
            double targetRow;

            // "Rotates" the target
            if (blinkyColumn > pacmanColumn) {
                targetColumn = blinkyColumn - (2 * (blinkyColumn-pacmanColumn));
            } else {
                targetColumn = blinkyColumn + (2 * (pacmanColumn-blinkyColumn));
            }

            if (blinkyRow < pacmanRow) {
                targetRow = blinkyRow + (2 * (pacmanRow - blinkyRow));
            } else {
                targetRow = blinkyRow - (2 * (blinkyRow - pacmanRow));
            }

            // calculated with the pythagorean theorem
            if (inkyGoRight) distance1inky = Math.pow(Math.abs((inkyColumn + 1) - targetColumn - 1), 2) + Math.pow(Math.abs(inkyRow - targetRow), 2);
            if (inkyGoUp) distance2inky = Math.pow(Math.abs(inkyColumn - targetColumn), 2) + Math.pow(Math.abs((inkyRow - 1) - targetRow - 1), 2);
            if (inkyGoDown) distance3inky = Math.pow(Math.abs(inkyColumn - targetColumn), 2) + Math.pow(Math.abs((inkyRow + 1) - targetRow - 1), 2);
            if (inkyGoLeft) distance4inky = Math.pow(Math.abs((inkyColumn - 1) - targetColumn - 1), 2) + Math.pow(Math.abs(inkyRow - targetRow), 2);


        }
    }


    /**
     * Calculate next move by eliminating worst / not possible moves
     * @param ghost lowercase name of the Ghost
     */
    public static void calculateNextMove(String ghost) {
        if (ghost.equals("blinky")) {
            blinkyRowNew = (int) Math.round((blinkyYPos + characterHeight / 2) / widthOneBlock);
            blinkyColumnNew = (int) Math.round(((blinkyXPos - characterWidth / 2) / heightOneBlock));

            if (velocityBlinkyHorizontal < 0 || velocityBlinkyVertical > 0) {
                blinkyRowNew = (int) Math.round((blinkyYPos - characterHeight / 2) / widthOneBlock);
                blinkyColumnNew = (int) Math.round(((blinkyXPos + characterWidth / 2) / heightOneBlock));
            }

            // Direction the Ghost is heading into
            // (unknown at the moment)
            blinkyGoingRight = false;
            blinkyGoingLeft = false;
            blinkyGoingUp = false;
            blinkyGoingDown = false;

            // Possible Moves
            // (All allowed at the moment)
            blinkyGoRight = true;
            blinkyGoLeft = true;
            blinkyGoUp = true;
            blinkyGoDown = true;

            distance1 = 10000;
            distance2 = 10000;
            distance3 = 10000;
            distance4 = 10000;

            // Every time when Ghost moves into new column
            if (blinkyColumnNew == blinkyColumn + 1 || blinkyColumnNew == blinkyColumn - 1) {
                blinkyColumn = blinkyColumnNew;
                getMovingDirection("blinky");
                blockTurnAround("blinky");
                blockImpossibleMoves("blinky");
                calculateDistances("blinky");
                getShortestDistance(distance1, distance2, distance3, distance4, "blinky");
                move("blinky");
            }
            // Every time when Ghost moves into new Row
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
