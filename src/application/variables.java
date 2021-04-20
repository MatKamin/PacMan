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

    public static boolean isLoggedIn = false;

    public static boolean startingStatus = true;
    public static boolean nextLevel = true;

    public static final int width = 1300;       // Window width
    public static final int height = 1000;      // Window height

    public static int score = 0;    // player score
    public static int highscore;    // Highest score



    public static boolean gameStarted;    // has the game started? true/false

    public static Color backgroundColor = Color.BLACK;    // Background Color
    public static Color fontColor = Color.WHITE;          // Font Color


    public static String validUsername = "Player 1";
    public static String finalUsername = "";
    static final String regexp = "\\w{1,10}" + "\\s?+" + "\\d{0,3}";


    public static String mapFile = "resources/levels/level1.txt";
    public static int maxLevel = 2;

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
    public static int railVerticalCount = 0;
    public static int railHorizontalCount = 0;
    public static int railUpRightCount = 0;
    public static int railUpLeftCount = 0;
    public static int railRightUpCount = 0;
    public static int railLeftUpCount = 0;

    //::::::::::: Pac-Man :::::::::::\\

    public static double pacmanRow;
    public static double pacmanColumn;
    public static double pacmanXPos;
    public static double pacmanYPos;

    public static double pacmanXPosStarting;
    public static double pacmanYPosStarting;


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


    //::::::::::: RAIL VERTICAL :::::::::::\\

    public static ImageView[] viewRailVertical = new ImageView[blockCountHorizontally * blockCountVertically];

    // Creating an image
    static public Image railVertical;

    static {
        try {
            railVertical = new Image(new FileInputStream("resources/mapAssets/railVertical.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //::::::::::: RAIL HORIZONTAL :::::::::::\\

    public static ImageView[] viewRailHorizontal = new ImageView[blockCountHorizontally * blockCountVertically];

    // Creating an image
    static public Image railHorizontal;

    static {
        try {
            railHorizontal = new Image(new FileInputStream("resources/mapAssets/railHorizontal.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //::::::::::: RAIL UP RIGHT :::::::::::\\

    public static ImageView[] viewRailUpRight = new ImageView[blockCountHorizontally * blockCountVertically];

    // Creating an image
    static public Image railUpRight;

    static {
        try {
            railUpRight = new Image(new FileInputStream("resources/mapAssets/railUpRight.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //::::::::::: RAIL UP LEFT :::::::::::\\

    public static ImageView[] viewRailUpLeft = new ImageView[blockCountHorizontally * blockCountVertically];

    // Creating an image
    static public Image railUpLeft;

    static {
        try {
            railUpLeft = new Image(new FileInputStream("resources/mapAssets/railUpLeft.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //::::::::::: RAIL RIGHT UP :::::::::::\\

    public static ImageView[] viewRailRightUp = new ImageView[blockCountHorizontally * blockCountVertically];

    // Creating an image
    static public Image railRightUp;

    static {
        try {
            railRightUp = new Image(new FileInputStream("resources/mapAssets/railRightUp.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //::::::::::: RAIL LEFT UP :::::::::::\\

    public static ImageView[] viewRailLeftUp = new ImageView[blockCountHorizontally * blockCountVertically];

    // Creating an image
    static public Image railLeftUp;

    static {
        try {
            railLeftUp = new Image(new FileInputStream("resources/mapAssets/railLeftUp.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }








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

}


