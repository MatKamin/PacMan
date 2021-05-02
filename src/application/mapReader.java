package application;

//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static application.gameMechanics.*;
import static application.imageViewerVariables.*;
import static application.main.heightOneBlock;
import static application.main.widthOneBlock;


//---------------------------------CLASS---------------------------------\\

public class mapReader {

    //---------------------------------VARIABLES---------------------------------\\
    public static int blockCountHorizontally = 29;
    public static int blockCountVertically = 37;

    static double pacmanXPosStarting;
    static double pacmanYPosStarting;

    static double blinkyRow;
    static double blinkyColumn;
    static double blinkyColumnStart;
    static double blinkyRowStart;
    static double blinkyXPos;
    static double blinkyYPos;

    static double pinkyRow;
    static double pinkyColumn;
    static double pinkyColumnStart;
    static double pinkyRowStart;
    static double pinkyXPos;
    static double pinkyYPos;

    static int spawningFruitColumn = 14;
    static int spawningFruitRow = 21;

    /**
     * reads the Map
     */
    public static void readMap() {
        if (firstRead) {
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
                        switch (value) {
                            case "S" -> {
                                // POWER PILLS

                                viewPowerPill[powerPillCount] = new ImageView(powerPill);
                                viewPowerPill[powerPillCount].setX(widthOneBlock * columnCounter + (int) (widthOneBlock / 2) - powerPill.getWidth() / 2);
                                viewPowerPill[powerPillCount].setY(heightOneBlock * row + (int) (heightOneBlock / 2) - powerPill.getHeight() / 2);
                                powerPillCount++;
                                powerPills[columnCounter][row] = true;
                                notAllowedBox[columnCounter][row] = false;
                                dots[columnCounter][row] = false;
                            }
                            case "B" -> {
                                // DOTS

                                viewDot[dotCount] = new ImageView(dot);
                                viewDot[dotCount].setX(widthOneBlock * columnCounter + (int) (widthOneBlock / 2) - dot.getWidth() / 2);
                                viewDot[dotCount].setY(heightOneBlock * row + (int) (heightOneBlock / 2) - dot.getHeight() / 2);
                                dotCount++;
                                dotCountAtStart++;
                                powerPills[columnCounter][row] = false;
                                dots[columnCounter][row] = true;
                                notAllowedBox[columnCounter][row] = false;
                            }
                            case "1" -> {
                                // BLINKY

                                blinkyXPos = (widthOneBlock * columnCounter);
                                blinkyYPos = (heightOneBlock * row);
                                blinkyRow = row;
                                blinkyColumn = columnCounter;
                                blinkyColumnStart = columnCounter;
                                blinkyRowStart = row;
                                powerPills[columnCounter][row] = false;
                                dots[columnCounter][row] = false;
                                notAllowedBox[columnCounter][row] = false;
                            }
                            case "2" -> {
                                // PINKY

                                pinkyXPos = widthOneBlock * columnCounter;
                                pinkyYPos = heightOneBlock * row;
                                pinkyRow = row;
                                pinkyColumn = columnCounter;
                                powerPills[columnCounter][row] = false;
                                dots[columnCounter][row] = false;
                                notAllowedBox[columnCounter][row] = false;
                            }
                            case "P" -> {
                                // PAC-MAN

                                pacmanXPos = (widthOneBlock * columnCounter);
                                pacmanYPos = (heightOneBlock * row);
                                pacmanXPosStarting = pacmanXPos;
                                pacmanYPosStarting = pacmanYPos;
                                pacmanRow = row;
                                pacmanColumn = columnCounter;
                                powerPills[columnCounter][row] = false;
                                dots[columnCounter][row] = false;
                                notAllowedBox[columnCounter][row] = false;
                            }
                            case "E" -> {
                                // NOTHING

                                powerPills[columnCounter][row] = false;
                                dots[columnCounter][row] = false;
                                notAllowedBox[columnCounter][row] = false;
                            }
                            case "V" -> {
                                // RAIL VERTICAL

                                notAllowedBox[columnCounter][row] = true;
                                dots[columnCounter][row] = false;
                                powerPills[columnCounter][row] = false;
                                viewRailVertical[railVerticalCount] = new ImageView(railVertical);
                                viewRailVertical[railVerticalCount].setX(columnCounter * widthOneBlock);
                                viewRailVertical[railVerticalCount].setY(row * heightOneBlock);
                                viewRailVertical[railVerticalCount].setFitWidth(widthOneBlock);
                                viewRailVertical[railVerticalCount].setFitHeight(heightOneBlock);
                                railVerticalCount++;
                            }
                            case "H" -> {
                                // RAIL HORIZONTAL

                                notAllowedBox[columnCounter][row] = true;
                                dots[columnCounter][row] = false;
                                powerPills[columnCounter][row] = false;
                                viewRailHorizontal[railHorizontalCount] = new ImageView(railHorizontal);
                                viewRailHorizontal[railHorizontalCount].setX(columnCounter * widthOneBlock);
                                viewRailHorizontal[railHorizontalCount].setY(row * heightOneBlock);
                                viewRailHorizontal[railHorizontalCount].setFitWidth(widthOneBlock);
                                viewRailHorizontal[railHorizontalCount].setFitHeight(heightOneBlock);
                                railHorizontalCount++;
                            }
                            case "R" -> {
                                // UP RIGHT

                                notAllowedBox[columnCounter][row] = true;
                                dots[columnCounter][row] = false;
                                powerPills[columnCounter][row] = false;
                                viewRailUpRight[railUpRightCount] = new ImageView(railUpRight);
                                viewRailUpRight[railUpRightCount].setX(columnCounter * widthOneBlock);
                                viewRailUpRight[railUpRightCount].setY(row * heightOneBlock);
                                viewRailUpRight[railUpRightCount].setFitWidth(widthOneBlock);
                                viewRailUpRight[railUpRightCount].setFitHeight(heightOneBlock);
                                railUpRightCount++;
                            }
                            case "L" -> {
                                // UP LEFT

                                notAllowedBox[columnCounter][row] = true;
                                dots[columnCounter][row] = false;
                                powerPills[columnCounter][row] = false;
                                viewRailUpLeft[railUpLeftCount] = new ImageView(railUpLeft);
                                viewRailUpLeft[railUpLeftCount].setX(columnCounter * widthOneBlock);
                                viewRailUpLeft[railUpLeftCount].setY(row * heightOneBlock);
                                viewRailUpLeft[railUpLeftCount].setFitWidth(widthOneBlock);
                                viewRailUpLeft[railUpLeftCount].setFitHeight(heightOneBlock);
                                railUpLeftCount++;
                            }
                            case "U" -> {
                                // RIGHT UP

                                notAllowedBox[columnCounter][row] = true;
                                dots[columnCounter][row] = false;
                                powerPills[columnCounter][row] = false;
                                viewRailRightUp[railRightUpCount] = new ImageView(railRightUp);
                                viewRailRightUp[railRightUpCount].setX(columnCounter * widthOneBlock);
                                viewRailRightUp[railRightUpCount].setY(row * heightOneBlock);
                                viewRailRightUp[railRightUpCount].setFitWidth(widthOneBlock);
                                viewRailRightUp[railRightUpCount].setFitHeight(heightOneBlock);
                                railRightUpCount++;
                            }
                            case "D" -> {
                                // LEFT UP

                                notAllowedBox[columnCounter][row] = true;
                                dots[columnCounter][row] = false;
                                powerPills[columnCounter][row] = false;
                                viewRailLeftUp[railLeftUpCount] = new ImageView(railLeftUp);
                                viewRailLeftUp[railLeftUpCount].setX(columnCounter * widthOneBlock);
                                viewRailLeftUp[railLeftUpCount].setY(row * heightOneBlock);
                                viewRailLeftUp[railLeftUpCount].setFitWidth(widthOneBlock);
                                viewRailLeftUp[railLeftUpCount].setFitHeight(heightOneBlock);
                                railLeftUpCount++;
                            }
                            case "F" -> {
                                // Spawning Fruit
                                powerPills[columnCounter][row] = false;
                                dots[columnCounter][row] = false;
                                notAllowedBox[columnCounter][row] = false;

                                spawningFruitColumn = columnCounter;
                                spawningFruitRow = row;
                            }
                        }
                    }
                }
                myReader.close();
                firstRead = false;
                blockCountHorizontally = column;
                blockCountVertically = row;

            } catch (FileNotFoundException e) {
                System.out.println("An error while reading the map occurred.");
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
