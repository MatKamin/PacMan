package application.functionality;

//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static application.variables.*;


//---------------------------------CLASS---------------------------------\\

public class pacmanControls {

    //::::::::::: Pac-Man RIGHT :::::::::::\\

    //Creating an image
    public static Image pacmanRight;

    static {
        try {
            pacmanRight = new Image(new FileInputStream("resources/characters/pacmanRight.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Setting the image view
    public static ImageView viewPacmanRight = new ImageView(pacmanRight);






    //::::::::::: Pac-Man UP :::::::::::\\

    //Creating an image
    public static Image pacmanUp;

    static {
        try {
            pacmanUp = new Image(new FileInputStream("resources/characters/pacmanUp.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Setting the image view
    public static ImageView viewPacmanUp = new ImageView(pacmanUp);







    //::::::::::: Pac-Man DOWN :::::::::::\\

    //Creating an image
    public static Image pacmanDown;

    static {
        try {
            pacmanDown = new Image(new FileInputStream("resources/characters/pacmanDown.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Setting the image view
    public static ImageView viewPacmanDown = new ImageView(pacmanDown);









    //::::::::::: Pac-Man LEFT :::::::::::\\

    //Creating an image
    public static Image pacmanLeft;

    static {
        try {
            pacmanLeft = new Image(new FileInputStream("resources/characters/pacmanLeft.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Setting the image view
    public static ImageView viewPacmanLeft = new ImageView(pacmanLeft);


    /**
     * Allows controlling Pac-Man
     * @param gameScene        Scene gameScene
     * @param gameLayout       Group gameLayout
     */
    public static void controls(Scene gameScene, Group gameLayout) {


        gameScene.setOnKeyPressed(e -> {

            //::::::::::: "UP" KEY & "W" KEY :::::::::::\\

            if ((e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP)) {
                waitingForTurn = 'u';

                if (allowNextMoveUp) {

                    //::::::::::: Pac-Man GIF :::::::::::\\

                    //Setting the position of the image
                    viewPacmanUp.setX((pacmanXPos));
                    viewPacmanUp.setY((pacmanYPos));

                    //setting the fit height and width of the image view
                    viewPacmanUp.setFitHeight(characterHeight);
                    viewPacmanUp.setFitWidth(characterWidth);

                    gameLayout.getChildren().remove(viewPacmanRight);
                    gameLayout.getChildren().remove(viewPacmanUp);
                    gameLayout.getChildren().remove(viewPacmanLeft);
                    gameLayout.getChildren().remove(viewPacmanDown);

                    gameLayout.getChildren().add(viewPacmanUp);

                    pacmanFacingUp = true;
                    pacmanFacingDown = false;
                    pacmanFacingLeft = false;
                    pacmanFacingRight = false;
                    velocityPacmanHorizontal = 0;
                    velocityPacmanVertical = -1;

                    hitUpWall = false;
                }
            }





            //::::::::::: "RIGHT" KEY & "D" KEY :::::::::::\\

            if ((e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT)) {
                waitingForTurn = 'r';

                if (allowNextMoveRight) {
                    //::::::::::: Pac-Man GIF :::::::::::\\

                    //Setting the position of the image
                    viewPacmanRight.setX((pacmanXPos));
                    viewPacmanRight.setY((pacmanYPos));

                    //setting the fit height and width of the image view
                    viewPacmanRight.setFitHeight(characterHeight);
                    viewPacmanRight.setFitWidth(characterWidth);

                    gameLayout.getChildren().remove(viewPacmanUp);
                    gameLayout.getChildren().remove(viewPacmanRight);
                    gameLayout.getChildren().remove(viewPacmanLeft);
                    gameLayout.getChildren().remove(viewPacmanDown);

                    gameLayout.getChildren().addAll(viewPacmanRight);

                    pacmanFacingRight = true;
                    pacmanFacingDown = false;
                    pacmanFacingLeft = false;
                    pacmanFacingUp = false;
                    velocityPacmanHorizontal = 1;
                    velocityPacmanVertical = 0;

                    hitRightWall = false;
                }
            }





            //::::::::::: "LEFT" KEY & "A" KEY :::::::::::\\

            if ((e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT)) {
                waitingForTurn = 'l';

                if (allowNextMoveLeft){


                    //::::::::::: Pac-Man GIF :::::::::::\\

                    //Setting the position of the image
                    viewPacmanLeft.setX((pacmanXPos));
                    viewPacmanLeft.setY((pacmanYPos));

                    //setting the fit height and width of the image view
                    viewPacmanLeft.setFitHeight(characterHeight);
                    viewPacmanLeft.setFitWidth(characterWidth);

                    gameLayout.getChildren().remove(viewPacmanUp);
                    gameLayout.getChildren().remove(viewPacmanRight);
                    gameLayout.getChildren().remove(viewPacmanLeft);
                    gameLayout.getChildren().remove(viewPacmanDown);

                    gameLayout.getChildren().addAll(viewPacmanLeft);


                    pacmanFacingLeft = true;
                    pacmanFacingDown = false;
                    pacmanFacingUp = false;
                    pacmanFacingRight = false;
                    velocityPacmanHorizontal = -1;
                    velocityPacmanVertical = 0;

                    hitLeftWall = false;
                }
            }




            //::::::::::: "DOWN" KEY & "S" KEY :::::::::::\\

            if ((e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN)) {
                waitingForTurn = 'd';

                if (allowNextMoveDown){

                    //::::::::::: Pac-Man GIF :::::::::::\\

                    //Setting the position of the image
                    viewPacmanDown.setX(pacmanXPos);
                    viewPacmanDown.setY(pacmanYPos);



                    //setting the fit height and width of the image view
                    viewPacmanDown.setFitHeight(characterHeight);
                    viewPacmanDown.setFitWidth(characterWidth);

                    gameLayout.getChildren().remove(viewPacmanUp);
                    gameLayout.getChildren().remove(viewPacmanRight);
                    gameLayout.getChildren().remove(viewPacmanLeft);
                    gameLayout.getChildren().remove(viewPacmanDown);

                    gameLayout.getChildren().addAll(viewPacmanDown);

                    pacmanFacingDown = true;
                    pacmanFacingUp = false;
                    pacmanFacingLeft = false;
                    pacmanFacingRight = false;
                    velocityPacmanHorizontal = 0;
                    velocityPacmanVertical = 1;

                    hitDownWall = false;
                }
            }



            //::::::::::: DEBUG :::::::::::\\

            if (e.getCode() == KeyCode.SPACE) {
                debug = true;
            }
        });
    }
}
