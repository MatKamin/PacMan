package application;

import static application.variables.*;

public class ghostAI {



    /**
     * Animates Ghosts
     * TODO: Ghost AI
     */
    public static void ghostMove() {
        // Blinky
        viewBlinky.setX(blinkyXPos);
        blinkyXPos += velocityBlinky;

        // Pinky
        viewPinky.setX(pinkyXPos);
        pinkyXPos += velocityPinky;
    }




    /**
     * Ghosts Bouncing left / right
     * TODO: Ghost AI
     */
    public static void wallGhost() {

        if (velocityBlinky < 0) {
            if (notAllowedBox[(int) blinkyXPos / (int) widthOneBlock][(int) blinkyRow]) {
                velocityBlinky *= -1;
            }
        } else {
            if (notAllowedBox[(int) blinkyXPos / (int) widthOneBlock + 1][(int) blinkyRow]) {
                velocityBlinky *= -1;
            }
        }

        if (velocityPinky < 0) {
            if (notAllowedBox[(int) pinkyXPos / (int) widthOneBlock][(int) pinkyRow]) {
                velocityPinky *= -1;
            }
        } else {
            if (notAllowedBox[(int) pinkyXPos / (int) widthOneBlock + 1][(int) pinkyRow]) {
                velocityPinky *= -1;
            }
        }

    }

}
