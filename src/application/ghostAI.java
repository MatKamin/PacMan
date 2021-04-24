package application;

import static application.variables.*;

public class ghostAI {


    /**
     * Animates Ghosts
     * TODO: Ghost AI
     */
    public static void ghostAnimate() {

        // TODO!!!

        // Blinky

        viewBlinky.setX(blinkyXPos);
        viewBlinky.setY(blinkyYPos);


        blinkyXPos += velocityBlinkyHorizontal;
        blinkyYPos += velocityBlinkyVertical;

        calculateNextMove();
    }


    public static void calculateNextMove() {

        blinkyRowNew = (int) Math.round((blinkyYPos + characterHeight/2) / widthOneBlock);
        blinkyColumnNew = (int) Math.round(((blinkyXPos - characterWidth/2) / heightOneBlock));

        // TODO:
        if (velocityBlinkyHorizontal > 0){
            if (blinkyColumnNew + 1 > blockCountHorizontally) {
                // Teleport left/right
                blinkyXPos = widthOneBlock;
                blinkyColumnNew = 0;
            }
        }
        if (velocityBlinkyHorizontal < 0){
            if (blinkyColumnNew - 2 < 0) {
                // Teleport left/right
                blinkyXPos = blockCountHorizontally * widthOneBlock;
                blinkyColumnNew = blockCountHorizontally - 1;
            }
        }
        // TODO

        if (velocityBlinkyHorizontal < 0 || velocityBlinkyVertical > 0) {
            blinkyRowNew = (int) Math.round((blinkyYPos - characterHeight/2) / widthOneBlock);
            blinkyColumnNew = (int) Math.round(((blinkyXPos + characterWidth/2) / heightOneBlock));
        }


        if (blinkyColumnNew == blinkyColumn + 1 || blinkyColumnNew == blinkyColumn - 1) {

            blinkyColumn = blinkyColumnNew;


            blinkyGoingRight = false;
            blinkyGoingLeft = false;
            blinkyGoingUp = false;
            blinkyGoingDown = false;

            boolean blinkyGoRight = true;
            boolean blinkyGoLeft = true;
            boolean blinkyGoUp = true;
            boolean blinkyGoDown = true;

            // Determine which direction Blinky is going in
            // and dont allow to turn around
            if (velocityBlinkyHorizontal > 0) {
                blinkyGoingRight = true;
                blinkyGoLeft = false;
            } else if (velocityBlinkyHorizontal < 0) {
                blinkyGoingLeft = true;
                blinkyGoRight = false;
            } else if (velocityBlinkyVertical < 0) {
                blinkyGoingUp = true;
                blinkyGoDown = false;
            } else if (velocityBlinkyVertical > 0) {
                blinkyGoingDown = true;
                blinkyGoUp = false;
            }



            if (!ghostPossibleUp()) {
                blinkyGoUp = false;
            }
            if (!ghostPossibleDown()) {
                blinkyGoDown = false;
            }
            if (!ghostPossibleLeft()) {
                blinkyGoLeft = false;
            }
            if (!ghostPossibleRight()) {
                blinkyGoRight = false;
            }


            // TARGET
            // COLUMN: 26 (pacmanColumn)
            // ROW: 1 (pacmanRow)

            double distance1 = 10000;
            double distance2 = 10000;
            double distance3 = 10000;
            double distance4 = 10000;

            /**
            if (blinkyColumnNew > 10 && blinkyColumnNew < 19 && blinkyRowNew > 15 && blinkyRowNew < 21){
                if (blinkyGoRight) {
                    distance1 = Math.pow(Math.abs((blinkyColumn + 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
                }
                if (blinkyGoUp) {
                    distance2 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow - 1) - 1 - 1), 2);
                }
                if (blinkyGoDown) {
                    distance3 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow + 1) - 1 - 1), 2);
                }
                if (blinkyGoLeft) {
                    distance4 = Math.pow(Math.abs((blinkyColumn - 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
                }

            } else {
**/
                if (blinkyGoRight) {
                    distance1 = Math.pow(Math.abs((blinkyColumn + 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
                }
                if (blinkyGoUp) {
                    distance2 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow - 1) - 1 - 1), 2);
                }
                if (blinkyGoDown) {
                    distance3 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow + 1) - 1 - 1), 2);
                }
                if (blinkyGoLeft) {
                    distance4 = Math.pow(Math.abs((blinkyColumn - 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
                }





            // Find shortest distance
            double smallest = distance1;
            if (distance2 < smallest){
                smallest = distance2;
            }
            if (distance3 < smallest){
                smallest = distance3;
            }
            if (distance4 < smallest){
                smallest = distance4;
            }



            if (smallest != distance1){
                blinkyGoRight = false;
            }
            if (smallest != distance2){
                blinkyGoUp = false;
            }
            if (smallest != distance3) {
              blinkyGoDown = false;
            }
            if (smallest != distance4) {
                blinkyGoLeft = false;
            }


            if (blinkyGoLeft){
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


            //System.out.println(blinkyColumnNew + " | " + blinkyRowNew);
        }






        if (blinkyRowNew == blinkyRow + 1 || blinkyRowNew == blinkyRow - 1) {

            blinkyRow = blinkyRowNew;


            blinkyGoingRight = false;
            blinkyGoingLeft = false;
            blinkyGoingUp = false;
            blinkyGoingDown = false;

            boolean blinkyGoRight = true;
            boolean blinkyGoLeft = true;
            boolean blinkyGoUp = true;
            boolean blinkyGoDown = true;

            // Determine which direction Blinky is going in
            // and dont allow to turn around
            if (velocityBlinkyHorizontal > 0) {
                blinkyGoingRight = true;
                blinkyGoLeft = false;
            } else if (velocityBlinkyHorizontal < 0) {
                blinkyGoingLeft = true;
                blinkyGoRight = false;
            } else if (velocityBlinkyVertical < 0) {
                blinkyGoingUp = true;
                blinkyGoDown = false;
            } else if (velocityBlinkyVertical > 0) {
                blinkyGoingDown = true;
                blinkyGoUp = false;
            }



            if (!ghostPossibleUp()) {
                blinkyGoUp = false;
            }
            if (!ghostPossibleDown()) {
                blinkyGoDown = false;
            }
            if (!ghostPossibleLeft()) {
                blinkyGoLeft = false;
            }
            if (!ghostPossibleRight()) {
                blinkyGoRight = false;
            }


            // TARGET
            // COLUMN: 26
            // ROW: 1


            double distance1 = 10000;
            double distance2 = 10000;
            double distance3 = 10000;
            double distance4 = 10000;


            /**
            if (blinkyColumnNew > 10 && blinkyColumnNew < 19 && blinkyRowNew > 15 && blinkyRowNew < 21){
                if (blinkyGoRight) {
                    distance1 = Math.pow(Math.abs((blinkyColumn + 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
                }
                if (blinkyGoUp) {
                    distance2 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow - 1) - 1 - 1), 2);
                }
                if (blinkyGoDown) {
                    distance3 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow + 1) - 1 - 1), 2);
                }
                if (blinkyGoLeft) {
                    distance4 = Math.pow(Math.abs((blinkyColumn - 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
                }

            } else {
**/
                if (blinkyGoRight) {
                    distance1 = Math.pow(Math.abs((blinkyColumn + 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
                }
                if (blinkyGoUp) {
                    distance2 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow - 1) - 1 - 1), 2);
                }
                if (blinkyGoDown) {
                    distance3 = Math.pow(Math.abs(blinkyColumn - 26), 2) + Math.pow(Math.abs((blinkyRow + 1) - 1 - 1), 2);
                }
                if (blinkyGoLeft) {
                    distance4 = Math.pow(Math.abs((blinkyColumn - 1) - 26 - 1), 2) + Math.pow(Math.abs(blinkyRow - 1), 2);
                }



            // Find shortest distance
            double smallest = distance1;
            if (distance2 < smallest){
                smallest = distance2;
            }
            if (distance3 < smallest){
                smallest = distance3;
            }
            if (distance4 < smallest){
                smallest = distance4;
            }

            if (smallest != distance1){
                blinkyGoRight = false;
            }
            if (smallest != distance2){
                blinkyGoUp = false;
            }
            if (smallest != distance3) {
                blinkyGoDown = false;
            }
            if (smallest != distance4) {
                blinkyGoLeft = false;
            }


            if (blinkyGoLeft){
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



            //System.out.println(blinkyColumnNew + " | " + blinkyRowNew);
        }
    }


    public static boolean ghostPossibleUp() {
        // Check if Up is possible
        if (!notAllowedBox[(int) blinkyColumn][(int) blinkyRow - 1]) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean ghostPossibleDown() {
        // Check if Down is possible
        if (!notAllowedBox[(int) blinkyColumn][(int) blinkyRow + 1]) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean ghostPossibleLeft() {
        // Check if Left is possible
        if (!notAllowedBox[(int) blinkyColumn - 1][(int) blinkyRow]) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean ghostPossibleRight() {
        // Check if Right is possible
        if (!notAllowedBox[(int) blinkyColumn + 1][(int) blinkyRow]) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Ghosts Bouncing left / right
     * TODO: Ghost AI
     */
    public static void wallGhost() {

        if (velocityBlinkyHorizontal < 0) {
            if (notAllowedBox[(int) blinkyXPos / (int) widthOneBlock][(int) blinkyRow]) {
                velocityBlinkyHorizontal *= -1;
            }
        } else {
            if (notAllowedBox[(int) blinkyXPos / (int) widthOneBlock + 1][(int) blinkyRow]) {
                velocityBlinkyHorizontal *= -1;
            }
        }

    }
}
