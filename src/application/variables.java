package application;

//---------------------------------IMPORTS---------------------------------\\


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


//---------------------------------CLASS---------------------------------\\

public class variables {


    //---------------------------------VARIABLES---------------------------------\\

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";



    public static final int width = 1000;       // Window width
    public static final int height = 1000;      // Window height

    public static int score = 0;    // player score

    // TODO: Level
    //static int level = 1;      // level counter

    public static boolean gameStarted;    // has the game started? true/false

    public static Color backgroundColor = Color.BLACK;    // Background Color
    public static Color fontColor = Color.WHITE;          // Font Color


    // TODO: Threads
    //static String USERNAME = "Player 1";
    //static String validUsername = "Player 1";
    //static final String regexp = "\\w{1,10}" + "\\s?+" + "\\d{0,3}";


    static final String mapFile = "resources/levels/level.txt";

    public static int lifesCounter = 3;
    public static int lifesOriginally = 3;

    public static int levelCounter = 0;
    public static int startingLevel = 1;

    public static int blockCountHorizontally = 29;
    public static int blockCountVertically = 36;

    public static long widthOneBlock = 25;
    public static long heightOneBlock = 25;

    public static long characterWidth = widthOneBlock;
    public static long characterHeight = heightOneBlock;

    public static boolean firstRead = true;     // First time reading Map

    public static boolean[][] dots = new boolean[blockCountHorizontally + 1][blockCountVertically];
    public static boolean[][] powerPills = new boolean[blockCountHorizontally + 1][blockCountVertically];
    static boolean[][] notAllowedBox = new boolean[blockCountHorizontally + 1][blockCountVertically];

    public static int pacmanFontSize = 80;
    public static int pacmanFontSizeUI = 20;
    public static Font pacmanFont = Font.loadFont("file:resources/fonts/pacman.ttf", pacmanFontSize);       // Loading Pac-Man Font
    public static Font pacmanFontUI = Font.loadFont("file:resources/fonts/emulogic.ttf", pacmanFontSizeUI);            // Loading Pac-Man UI Font

    public static int dotCount = 0;
    public static int dotCountAtStart = 0;
    public static int powerPillCount = 0;
    public static int wallCount = 0;


    //::::::::::: Pac-Man :::::::::::\\

    public static double pacmanRow;
    public static double pacmanColumn;
    public static double pacmanXPos;
    public static double pacmanYPos;

    public static double pacmanXPosCenter = pacmanXPos + (int)(characterWidth/2);
    public static double pacmanYPosCenter = pacmanYPos + (int)(characterWidth/2);

    public static boolean pacmanFacingUp = false;
    public static boolean pacmanFacingDown = false;
    public static boolean pacmanFacingLeft = false;
    public static boolean pacmanFacingRight = true;

    public static char waitingForTurn;

    public static boolean debug = false;    // Debug for Pac-Man movement

    public static double velocityPacmanHorizontal = 0;
    public static double velocityPacmanVertical = 0;

    public static boolean allowNextMoveUp = true;
    public static boolean allowNextMoveDown = true;
    public static boolean allowNextMoveLeft = true;
    public static boolean allowNextMoveRight = true;

    public static boolean hitRightWall = false;
    public static boolean hitLeftWall = false;
    public static boolean hitUpWall = false;
    public static boolean hitDownWall = false;

    public static boolean stop = false;



    //::::::::::: Blinky :::::::::::\\

    static double blinkyRow;
    static double blinkyColumn;
    static double blinkyXPos;
    static double blinkyYPos;

    static double velocityBlinky = 1;
    static double velocityPinky = 1;




    //::::::::::: Pinky :::::::::::\\

    static double pinkyRow;
    static double pinkyColumn;
    static double pinkyXPos;
    static double pinkyYPos;



    //::::::::::: Spawning Fruits :::::::::::\\

    public static boolean collectableFruit = false;
    public static boolean fruitSpawned1 = false;
    public static boolean fruitSpawned2 = false;

    public static boolean doOnce = true;
    public static boolean doOnce2 = true;
    public static int delayFruit = 1;





    //:::::::::::::::::::::: Image Viewers ::::::::::::::::::::::\\


    //::::::::::: MENU GIF :::::::::::\\

    //Creating an image
    static Image menuAnimation;

    static {
        try {
            menuAnimation = new Image(new FileInputStream("resources/animations/menuAnimation.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Setting the image view
    static ImageView viewMenuAnimation = new ImageView(menuAnimation);






    //::::::::::: Red Ghost (Blinky) GIF :::::::::::\\

    //Creating an image
    static Image blinky;

    static {
        try {
            blinky = new Image(new FileInputStream("resources/characters/blinky.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Setting the image view
    public static ImageView viewBlinky = new ImageView(blinky);








    //::::::::::: Pink Ghost (Pinky) GIF :::::::::::\\

    // Creating an image
    static Image pinky;

    static {
        try {
            pinky = new Image(new FileInputStream("resources/characters/pinky.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Setting the image view
    static ImageView viewPinky = new ImageView(pinky);








    //::::::::::: DOTS :::::::::::\\

    public static ImageView[] viewDot = new ImageView[blockCountHorizontally * blockCountVertically];

    // Creating an image
    static public Image dot;

    static {
        try {
            dot = new Image(new FileInputStream("resources/mapAssets/dot.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }






    //::::::::::: POWER PILL :::::::::::\\

    public static ImageView[] viewPowerPill = new ImageView[blockCountHorizontally * blockCountVertically];

    // Creating an image
    static public Image powerPill;


    static {
        try {
            powerPill = new Image(new FileInputStream("resources/mapAssets/powerpill.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }








    //::::::::::: DOTS CLEARER :::::::::::\\

    public static ImageView viewClearer = new ImageView();

    // Creating an image
    static public Image clearer;

    static {
        try {
            clearer = new Image(new FileInputStream("resources/mapAssets/clearer.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }






    //::::::::::: SPAWNING FRUIT :::::::::::\\

    public static ImageView viewSpawningFruit = new ImageView();


    // Creating an image
    static public Image spawningFruit;

    static {
        try {
            spawningFruit = new Image(new FileInputStream("resources/mapAssets/cherries.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }





    //::::::::::: LIFES :::::::::::\\

    public static ImageView viewLifes = new ImageView();

    // Creating an image
    static public Image lifes;

    static {
        try {
            lifes = new Image(new FileInputStream("resources/mapAssets/lifeCounter.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }






    //::::::::::: CHERRIES :::::::::::\\

    public static ImageView viewCherry = new ImageView();


    // Creating an image
    static public Image cherry;

    static {
        try {
            cherry = new Image(new FileInputStream("resources/mapAssets/cherries.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }






    //::::::::::: WALL :::::::::::\\

    public static ImageView[] viewWall = new ImageView[blockCountHorizontally * blockCountVertically];

    // Creating an image
    static public Image wall;

    static {
        try {
            wall = new Image(new FileInputStream("resources/mapAssets/wall.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}


