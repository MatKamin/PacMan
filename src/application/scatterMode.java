package application;

import javafx.scene.Group;

import static application.imageViewerVariables.viewBlinky;
import static application.imageViewerVariables.viewScared;
import static application.main.*;
import static application.mapReader.*;

public class scatterMode extends ghostAI {

    /**
     * Animates Ghosts
     * TODO: Ghost AI remaining
     */
    public static void ghostAnimate(Group gameLayout) {
            gameLayout.getChildren().remove(viewScared);
            gameLayout.getChildren().remove(viewBlinky);
            gameLayout.getChildren().add(viewBlinky);

            // Blinky
            viewBlinky.setX(blinkyXPos);
            viewBlinky.setY(blinkyYPos);
            blinkyXPos += velocityBlinkyHorizontal;
            blinkyYPos += velocityBlinkyVertical;

            calculateNextMove();
            allowTeleport();
    }


    /**
     * Calculates Distances
     */
    public static void calculateDistances() {
        // TARGET SCATTER MODE
        // COLUMN: 26
        // ROW: 1
        if (blinkyGoRight)
            distance1 = Math.pow(Math.abs((blinkyColumn + 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
        if (blinkyGoUp)
            distance2 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow - 1) - 1 - 1), 2);
        if (blinkyGoDown)
            distance3 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow + 1) - 1 - 1), 2);
        if (blinkyGoLeft)
            distance4 = Math.pow(Math.abs((blinkyColumn - 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
    }


    /**
     * Calculates next move
     */
    public static void calculateNextMove() {
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
}
