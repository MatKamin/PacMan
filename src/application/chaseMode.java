package application;

import javafx.scene.Group;

import static application.gameMechanics.pacmanColumn;
import static application.gameMechanics.pacmanRow;
import static application.imageViewerVariables.*;
import static application.main.*;
import static application.mapReader.*;

public class chaseMode extends Ghost {


    public static void blinkyAnimate(Group gameLayout) {
        gameLayout.getChildren().remove(viewScaredBlinky);
        gameLayout.getChildren().remove(viewBlinky);

        gameLayout.getChildren().add(viewBlinky);

        // Blinky
        viewBlinky.setX(blinkyXPos);
        viewBlinky.setY(blinkyYPos);
        blinkyXPos += velocityBlinkyHorizontal;
        blinkyYPos += velocityBlinkyVertical;


        calculateNextMoveBlinky();
        allowTeleportBlinky();
    }

    public static void calculateDistancesBlinky() {
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
            getShortestDistanceBlinky();
            moveBlinky();
        }
        if (blinkyRowNew == blinkyRow + 1 || blinkyRowNew == blinkyRow - 1) {
            blinkyRow = blinkyRowNew;
            getMovingDirectionBlinky();
            blockTurnAroundBlinky();
            blockImpossibleMovesBlinky();
            calculateDistancesBlinky();
            getShortestDistanceBlinky();
            moveBlinky();
        }
    }









    public static void pinkyAnimate(Group gameLayout) {
        gameLayout.getChildren().remove(viewScaredPinky);
        gameLayout.getChildren().remove(viewPinky);

        gameLayout.getChildren().add(viewPinky);

        // Pinky
        viewPinky.setX(pinkyXPos);
        viewPinky.setY(pinkyYPos);
        pinkyXPos += velocityPinkyHorizontal;
        pinkyYPos += velocityPinkyVertical;


        calculateNextMovePinky();
        allowTeleportPinky();
    }

    public static void calculateDistancesPinky() {
        // TARGET SCATTER MODE
        // COLUMN: 2
        // ROW: 1
        if (pinkyGoRight)
            distance1pinky = Math.pow(Math.abs((pinkyColumn + 1) - pacmanColumn - 1), 2) + Math.pow(Math.abs(pinkyRow - pacmanRow), 2);
        if (pinkyGoUp)
            distance2pinky = Math.pow(Math.abs(pinkyColumn - pacmanColumn), 2) + Math.pow(Math.abs((pinkyRow - 1) - pacmanRow - 1), 2);
        if (pinkyGoDown)
            distance3pinky = Math.pow(Math.abs(pinkyColumn - pacmanColumn), 2) + Math.pow(Math.abs((pinkyRow + 1) - pacmanRow - 1), 2);
        if (pinkyGoLeft)
            distance4pinky = Math.pow(Math.abs((pinkyColumn - 1) - pacmanColumn - 1), 2) + Math.pow(Math.abs(pinkyRow - pacmanRow), 2);
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
            getShortestDistancePinky();
            movePinky();
        }
        if (pinkyRowNew == pinkyRow + 1 || pinkyRowNew == pinkyRow - 1) {
            pinkyRow = pinkyRowNew;
            getMovingDirectionPinky();
            blockTurnAroundPinky();
            blockImpossibleMovesPinky();
            calculateDistancesPinky();
            getShortestDistancePinky();
            movePinky();
        }
    }
}
