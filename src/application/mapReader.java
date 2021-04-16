package application;

//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static application.variables.*;


//---------------------------------CLASS---------------------------------\\

public class mapReader {

    /**
     * reads the Map
     */
    public static void readMap() {
        try {

            File map = new File(mapFile);
            Scanner myReader = new Scanner(map);
            int row = 0;
            int column = 0;
            int columnCounter = -1;

            while (myReader.hasNext()) {
                String value = myReader.next();
                columnCounter++;

                if (value.equals("?")) {
                    row++;
                    column = columnCounter - 1;
                    columnCounter = -1;
                } else {

                    if (firstRead && value.equals("S")) {
                        // POWER PILLS

                        viewPowerPill[powerPillCount] = new ImageView(powerPill);
                        viewPowerPill[powerPillCount].setX(widthOneBlock * columnCounter + (int)(widthOneBlock / 2) - powerPill.getWidth() / 2);
                        viewPowerPill[powerPillCount].setY(heightOneBlock * row + (int)(heightOneBlock / 2) - powerPill.getHeight() / 2);

                        powerPillCount++;

                        powerPills[columnCounter][row] = true;
                        notAllowedBox[columnCounter][row] = false;
                        dots[columnCounter][row] = false;



                    } else if (firstRead && value.equals("B")) {
                        // DOTS

                        viewDot[dotCount] = new ImageView(dot);
                        viewDot[dotCount].setX(widthOneBlock * columnCounter + (int)(widthOneBlock / 2) - dot.getWidth() / 2);
                        viewDot[dotCount].setY(heightOneBlock * row + (int)(heightOneBlock / 2) - dot.getHeight() / 2);

                        dotCount++;
                        dotCountAtStart++;

                        powerPills[columnCounter][row] = false;
                        dots[columnCounter][row] = true;
                        notAllowedBox[columnCounter][row] = false;




                    } else if ( firstRead && value.equals("1")) {
                        // BLINKY

                        blinkyXPos = widthOneBlock * columnCounter;
                        blinkyYPos = heightOneBlock * row;
                        blinkyRow = row;
                        blinkyColumn = columnCounter;

                        powerPills[columnCounter][row] = false;
                        dots[columnCounter][row] = false;
                        notAllowedBox[columnCounter][row] = false;





                    } else if (firstRead && value.equals("2")) {
                        // PINKY

                        pinkyXPos = widthOneBlock * columnCounter;
                        pinkyYPos = heightOneBlock * row;
                        pinkyRow = row;
                        pinkyColumn = columnCounter;

                        powerPills[columnCounter][row] = false;
                        dots[columnCounter][row] = false;
                        notAllowedBox[columnCounter][row] = false;





                    } else if (firstRead && value.equals("P")) {
                        // PAC-MAN

                        pacmanXPos = (widthOneBlock * columnCounter);
                        pacmanYPos = (heightOneBlock * row);
                        pacmanRow = row;
                        pacmanColumn = columnCounter;

                        powerPills[columnCounter][row] = false;
                        dots[columnCounter][row] = false;
                        notAllowedBox[columnCounter][row] = false;



                    } else if (firstRead && value.equals("E")) {
                        // NOTHING

                        //gc.setFill(Color.BLACK);
                        //gc.fillRect(widthOneBlock * columnCounter, heightOneBlock * row, widthOneBlock, heightOneBlock);

                        powerPills[columnCounter][row] = false;
                        dots[columnCounter][row] = false;
                        notAllowedBox[columnCounter][row] = false;

                        //gc.setStroke(Color.WHITE);
                        //gc.strokeRect(widthOneBlock * columnCounter, heightOneBlock * row, widthOneBlock, heightOneBlock);




                    } else if (firstRead && value.equals("W")) {
                        // WALLS

                        notAllowedBox[columnCounter][row] = true;
                        dots[columnCounter][row] = false;
                        powerPills[columnCounter][row] = false;

                        viewWall[wallCount] = new ImageView(wall);
                        viewWall[wallCount].setX(columnCounter * widthOneBlock);
                        viewWall[wallCount].setY(row * heightOneBlock);

                        viewWall[wallCount].setFitWidth(widthOneBlock);
                        viewWall[wallCount].setFitHeight(heightOneBlock);

                        wallCount++;
                    }
                }
            }

            myReader.close();
            firstRead = false;
            blockCountHorizontally = column;
            blockCountVertically = row;


        } catch (
                FileNotFoundException e) {
            System.out.println("An error while reading the map occurred.");
            e.printStackTrace();
        }
    }
}
