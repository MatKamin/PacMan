package application;

import javafx.scene.Group;

import static application.gameMechanics.*;
import static application.gameMechanics.score;
import static application.imageViewerVariables.*;
import static application.main.*;
import static application.mapReader.*;

public class scaredMode extends Ghost {

    /**
     * Animates Ghosts
     * TODO: Ghost AI remaining
     */
    public static void blinkyAnimate(Group gameLayout) {

        gameLayout.getChildren().remove(viewBlinky);
        gameLayout.getChildren().remove(viewScaredBlinky);
        gameLayout.getChildren().add(viewScaredBlinky);

        viewScaredBlinky.setX(blinkyXPos);
        viewScaredBlinky.setY(blinkyYPos);
        blinkyXPos += velocityBlinkyHorizontal;
        blinkyYPos += velocityBlinkyVertical;

        calculateNextMoveBlinky();
        allowTeleportBlinky();
        eatBlinky(gameLayout);
    }


    public static void eatBlinky(Group gameLayout) {
        if (!inScaredModeBlinky) {
            return;
        }
        if (pacmanColumn == blinkyColumn && pacmanRow == blinkyRow) {
            inScaredModeBlinky = false;
            inChaseMode = true;

            blinkyXPos = (widthOneBlock * blinkyColumnStart);
            blinkyYPos = (heightOneBlock * blinkyRowStart);
            blinkyColumn = blinkyColumnStart;
            blinkyRow = blinkyRowStart;

            viewBlinky.setX(blinkyXPos);
            viewBlinky.setY(blinkyYPos);

            gameLayout.getChildren().remove(viewScaredBlinky);

            score += 200;
        }
    }


    /**
     * Calculates Distances
     */

    public static void calculateDistancesBlinky() {
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
     * Calculates next move
     */
    public static void calculateNextMoveBlinky() {
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
            getMovingDirectionBlinky();
            blockTurnAroundBlinky();
            blockImpossibleMovesBlinky();
            calculateDistancesBlinky();
            getReverseDirectionBlinky();
            moveBlinky();
        }
        if (blinkyRowNew == blinkyRow + 1 || blinkyRowNew == blinkyRow - 1) {
            blinkyRow = blinkyRowNew;
            getMovingDirectionBlinky();
            blockTurnAroundBlinky();
            blockImpossibleMovesBlinky();
            calculateDistancesBlinky();
            getReverseDirectionBlinky();
            moveBlinky();
        }
    }








    public static void pinkyAnimate(Group gameLayout) {

        gameLayout.getChildren().remove(viewPinky);
        gameLayout.getChildren().remove(viewScaredPinky);
        gameLayout.getChildren().add(viewScaredPinky);

        viewScaredPinky.setX(pinkyXPos);
        viewScaredPinky.setY(pinkyYPos);
        pinkyXPos += velocityPinkyHorizontal;
        pinkyYPos += velocityPinkyVertical;

        calculateNextMovePinky();
        allowTeleportPinky();
        eatPinky(gameLayout);
    }


    public static void eatPinky(Group gameLayout) {
        if (!inScaredModePinky) {
            return;
        }
        if (pacmanColumn == pinkyColumn && pacmanRow == pinkyRow) {
            inScaredModePinky = false;
            inChaseMode = true;

            pinkyXPos = (widthOneBlock * pinkyColumnStart);
            pinkyYPos = (heightOneBlock * pinkyRowStart);
            pinkyColumn = pinkyColumnStart;
            pinkyRow = pinkyRowStart;

            viewPinky.setX(pinkyXPos);
            viewPinky.setY(pinkyYPos);

            gameLayout.getChildren().remove(viewScaredPinky);

            score += 200;
        }
    }


    /**
     * Calculates Distances
     */

    public static void calculateDistancesPinky() {
        // TARGET SCATTER MODE
        // COLUMN: 2 (pacmanColumn)
        // ROW: 1 (pacmanRow)
        if (pinkyColumnNew > 10 && pinkyColumnNew < 17 && pinkyRowNew > 15 && pinkyRowNew < 20) {
            if (pinkyGoRight) distance1pinky = Math.pow(Math.abs((pinkyColumn + 1) - 2 - 1), 2) + Math.pow(Math.abs(pinkyRow - 1), 2);
            if (pinkyGoUp) distance2pinky = Math.pow(Math.abs(pinkyColumn - 2), 2) + Math.pow(Math.abs((pinkyRow - 1) - 1 - 1), 2);
            if (pinkyGoDown) distance3pinky = Math.pow(Math.abs(pinkyColumn - 2), 2) + Math.pow(Math.abs((pinkyRow + 1) - 1 - 1), 2);
            if (pinkyGoLeft) distance4pinky = Math.pow(Math.abs((pinkyColumn - 1) - 2 - 1), 2) + Math.pow(Math.abs(pinkyRow - 1), 2);
        } else {
            if (pinkyGoRight) distance1pinky = Math.pow(Math.abs((pinkyColumn + 1) - pacmanColumn - 1), 2) + Math.pow(Math.abs(pinkyRow - pacmanRow), 2);
            if (pinkyGoUp) distance2pinky = Math.pow(Math.abs(pinkyColumn - pacmanColumn), 2) + Math.pow(Math.abs((pinkyRow - 1) - pacmanRow - 1), 2);
            if (pinkyGoDown) distance3pinky = Math.pow(Math.abs(pinkyColumn - pacmanColumn), 2) + Math.pow(Math.abs((pinkyRow + 1) - pacmanRow - 1), 2);
            if (pinkyGoLeft) distance4pinky = Math.pow(Math.abs((pinkyColumn - 1) - pacmanColumn - 1), 2) + Math.pow(Math.abs(pinkyRow - pacmanRow), 2);
        }
    }

    /**
     * Calculates next move
     */
    public static void calculateNextMovePinky() {
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
            getMovingDirectionPinky();
            blockTurnAroundPinky();
            blockImpossibleMovesPinky();
            calculateDistancesPinky();
            getReverseDirectionPinky();
            movePinky();
        }
        if (pinkyRowNew == pinkyRow + 1 || pinkyRowNew == pinkyRow - 1) {
            pinkyRow = pinkyRowNew;
            getMovingDirectionPinky();
            blockTurnAroundPinky();
            blockImpossibleMovesPinky();
            calculateDistancesPinky();
            getReverseDirectionPinky();
            movePinky();
        }
    }
}
