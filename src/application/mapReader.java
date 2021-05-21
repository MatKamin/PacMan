package application;

//---------------------------------IMPORTS---------------------------------\\

import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static application.gameMechanics.*;
import static application.gameMechanics.mapFile;
import static application.imageViewerVariables.*;
import static application.main.*;


//---------------------------------CLASS---------------------------------\\

public class mapReader {

    //---------------------------------VARIABLES---------------------------------\\
    public static int blockCountHorizontally = 29;
    public static int blockCountVertically = 37;

    static double pacmanXPosStarting;
    static double pacmanYPosStarting;

    public static double blinkyRow;
    public static double blinkyColumn;
    public static double blinkyColumnStart;
    public static double blinkyRowStart;
    public static double blinkyXPos;
    public static double blinkyYPos;
    public static double blinkyXPosStarting;
    public static double blinkyYPosStarting;

    public static double pinkyRow;
    public static double pinkyColumn;
    public static double pinkyColumnStart;
    public static double pinkyRowStart;
    public static double pinkyXPos;
    public static double pinkyYPos;
    public static double pinkyXPosStarting;
    public static double pinkyYPosStarting;

    static int spawningFruitColumn = 14;
    static int spawningFruitRow = 21;

    public static boolean reset = true;

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

                                if (reset) {
                                    powerPillCount++;
                                    powerPills[columnCounter][row] = true;
                                    notAllowedBox[columnCounter][row] = false;
                                    dots[columnCounter][row] = false;
                                }
                            }
                            case "B" -> {
                                // DOTS

                                viewDot[dotCount] = new ImageView(dot);
                                viewDot[dotCount].setX(widthOneBlock * columnCounter + (int) (widthOneBlock / 2) - dot.getWidth() / 2);
                                viewDot[dotCount].setY(heightOneBlock * row + (int) (heightOneBlock / 2) - dot.getHeight() / 2);

                                if (reset) {
                                    dotCount++;
                                    dotCountAtStart++;
                                    powerPills[columnCounter][row] = false;
                                    dots[columnCounter][row] = true;
                                    notAllowedBox[columnCounter][row] = false;
                                }
                            }
                            case "1" -> {
                                // BLINKY

                                blinkyXPos = (widthOneBlock * columnCounter);
                                blinkyYPos = (heightOneBlock * row);
                                blinkyXPosStarting = blinkyXPos;
                                blinkyYPosStarting = blinkyYPos;

                                blinkyRow = row;
                                blinkyColumn = columnCounter;
                                blinkyColumnStart = columnCounter;
                                blinkyRowStart = row;
                                if (reset) {
                                    powerPills[columnCounter][row] = false;
                                    dots[columnCounter][row] = false;
                                    notAllowedBox[columnCounter][row] = false;
                                }
                            }
                            case "2" -> {
                                // PINKY

                                pinkyXPos = widthOneBlock * columnCounter;
                                pinkyYPos = heightOneBlock * row;
                                pinkyXPosStarting = pinkyXPos;
                                pinkyYPosStarting = pinkyYPos;

                                pinkyRow = row;
                                pinkyColumn = columnCounter;
                                pinkyColumnStart = columnCounter;
                                pinkyRowStart = row;
                                if (reset) {
                                    powerPills[columnCounter][row] = false;
                                    dots[columnCounter][row] = false;
                                    notAllowedBox[columnCounter][row] = false;
                                }
                            }
                            case "P" -> {
                                // PAC-MAN

                                pacmanXPos = (widthOneBlock * columnCounter);
                                pacmanYPos = (heightOneBlock * row);
                                pacmanXPosStarting = pacmanXPos;
                                pacmanYPosStarting = pacmanYPos;
                                pacmanRow = row;
                                pacmanColumn = columnCounter;
                                if (reset) {
                                    powerPills[columnCounter][row] = false;
                                    dots[columnCounter][row] = false;
                                    notAllowedBox[columnCounter][row] = false;
                                }
                            }
                            case "E" -> {
                                // NOTHING

                                powerPills[columnCounter][row] = false;
                                dots[columnCounter][row] = false;
                                notAllowedBox[columnCounter][row] = false;
                            }
                            case "V" -> {
                                // RAIL VERTICAL

                                if (reset) {
                                    notAllowedBox[columnCounter][row] = true;
                                    dots[columnCounter][row] = false;
                                    powerPills[columnCounter][row] = false;
                                }
                                if (inPinkMode) {
                                    viewRailVertical[railVerticalCount] = new ImageView(railVerticalPink);
                                } else {
                                    viewRailVertical[railVerticalCount] = new ImageView(railVertical);
                                }

                                viewRailVertical[railVerticalCount].setX(columnCounter * widthOneBlock);
                                viewRailVertical[railVerticalCount].setY(row * heightOneBlock);
                                viewRailVertical[railVerticalCount].setFitWidth(widthOneBlock);
                                viewRailVertical[railVerticalCount].setFitHeight(heightOneBlock);
                                railVerticalCount++;
                            }
                            case "H" -> {
                                // RAIL HORIZONTAL

                                if (reset) {
                                    notAllowedBox[columnCounter][row] = true;
                                    dots[columnCounter][row] = false;
                                    powerPills[columnCounter][row] = false;
                                }
                                if (inPinkMode) {
                                    viewRailHorizontal[railHorizontalCount] = new ImageView(railHorizontalPink);
                                } else {
                                    viewRailHorizontal[railHorizontalCount] = new ImageView(railHorizontal);
                                }

                                viewRailHorizontal[railHorizontalCount].setX(columnCounter * widthOneBlock);
                                viewRailHorizontal[railHorizontalCount].setY(row * heightOneBlock);
                                viewRailHorizontal[railHorizontalCount].setFitWidth(widthOneBlock);
                                viewRailHorizontal[railHorizontalCount].setFitHeight(heightOneBlock);
                                railHorizontalCount++;
                            }
                            case "R" -> {
                                // UP RIGHT

                                if (reset) {
                                    notAllowedBox[columnCounter][row] = true;
                                    dots[columnCounter][row] = false;
                                    powerPills[columnCounter][row] = false;
                                }
                                if (inPinkMode) {
                                    viewRailUpRight[railUpRightCount] = new ImageView(railUpRightPink);
                                } else {
                                    viewRailUpRight[railUpRightCount] = new ImageView(railUpRight);
                                }

                                viewRailUpRight[railUpRightCount].setX(columnCounter * widthOneBlock);
                                viewRailUpRight[railUpRightCount].setY(row * heightOneBlock);
                                viewRailUpRight[railUpRightCount].setFitWidth(widthOneBlock);
                                viewRailUpRight[railUpRightCount].setFitHeight(heightOneBlock);
                                railUpRightCount++;
                            }
                            case "L" -> {
                                // UP LEFT
                                if (reset) {
                                    notAllowedBox[columnCounter][row] = true;
                                    dots[columnCounter][row] = false;
                                    powerPills[columnCounter][row] = false;
                                }
                                if (inPinkMode) {
                                    viewRailUpLeft[railUpLeftCount] = new ImageView(railUpLeftPink);
                                } else {
                                    viewRailUpLeft[railUpLeftCount] = new ImageView(railUpLeft);
                                }

                                viewRailUpLeft[railUpLeftCount].setX(columnCounter * widthOneBlock);
                                viewRailUpLeft[railUpLeftCount].setY(row * heightOneBlock);
                                viewRailUpLeft[railUpLeftCount].setFitWidth(widthOneBlock);
                                viewRailUpLeft[railUpLeftCount].setFitHeight(heightOneBlock);
                                railUpLeftCount++;
                            }
                            case "U" -> {
                                // RIGHT UP

                                if (reset) {
                                    notAllowedBox[columnCounter][row] = true;
                                    dots[columnCounter][row] = false;
                                    powerPills[columnCounter][row] = false;
                                }
                                if (inPinkMode) {
                                    viewRailRightUp[railRightUpCount] = new ImageView(railRightUpPink);
                                } else {
                                    viewRailRightUp[railRightUpCount] = new ImageView(railRightUp);
                                }

                                viewRailRightUp[railRightUpCount].setX(columnCounter * widthOneBlock);
                                viewRailRightUp[railRightUpCount].setY(row * heightOneBlock);
                                viewRailRightUp[railRightUpCount].setFitWidth(widthOneBlock);
                                viewRailRightUp[railRightUpCount].setFitHeight(heightOneBlock);
                                railRightUpCount++;
                            }
                            case "D" -> {
                                // LEFT UP

                                if (reset) {
                                    notAllowedBox[columnCounter][row] = true;
                                    dots[columnCounter][row] = false;
                                    powerPills[columnCounter][row] = false;
                                }
                                if (inPinkMode) {
                                    viewRailLeftUp[railLeftUpCount] = new ImageView(railLeftUpPink);
                                } else {
                                    viewRailLeftUp[railLeftUpCount] = new ImageView(railLeftUp);
                                }

                                viewRailLeftUp[railLeftUpCount].setX(columnCounter * widthOneBlock);
                                viewRailLeftUp[railLeftUpCount].setY(row * heightOneBlock);
                                viewRailLeftUp[railLeftUpCount].setFitWidth(widthOneBlock);
                                viewRailLeftUp[railLeftUpCount].setFitHeight(heightOneBlock);
                                railLeftUpCount++;
                            }
                            case "F" -> {
                                // Spawning Fruit
                                if (reset) {
                                    powerPills[columnCounter][row] = false;
                                    dots[columnCounter][row] = false;
                                    notAllowedBox[columnCounter][row] = false;
                                }

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
