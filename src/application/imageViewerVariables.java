package application;

//---------------------------------IMPORTS---------------------------------\\


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static application.mapReader.blockCountHorizontally;
import static application.mapReader.blockCountVertically;

//---------------------------------CLASS---------------------------------\\

public class imageViewerVariables {


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


    //::::::::::: Scared Ghost GIF :::::::::::\\

    //Creating an image
    static Image scared;

    static {
        try {
            scared = new Image(new FileInputStream("resources/characters/scared.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Setting the image views
    public static ImageView viewScaredBlinky = new ImageView(scared);
    public static ImageView viewScaredPinky = new ImageView(scared);
    public static ImageView viewScaredInky = new ImageView(scared);
    public static ImageView viewScaredClyde = new ImageView(scared);


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
    public static ImageView viewPinky = new ImageView(pinky);



    //::::::::::: Orange Ghost (Clyde) GIF :::::::::::\\

    // Creating an image
    static Image clyde;

    static {
        try {
            clyde = new Image(new FileInputStream("resources/characters/clyde.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Setting the image view
    public static ImageView viewClyde = new ImageView(clyde);


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




    //::::::::::: RAIL VERTICAL PINK :::::::::::\\

    // Creating an image
    static public Image railVerticalPink;

    static {
        try {
            railVerticalPink = new Image(new FileInputStream("resources/mapAssets/railVerticalPink.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //::::::::::: RAIL HORIZONTAL PINK :::::::::::\\

    // Creating an image
    static public Image railHorizontalPink;

    static {
        try {
            railHorizontalPink = new Image(new FileInputStream("resources/mapAssets/railHorizontalPink.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //::::::::::: RAIL UP RIGHT PINK :::::::::::\\

    // Creating an image
    static public Image railUpRightPink;

    static {
        try {
            railUpRightPink = new Image(new FileInputStream("resources/mapAssets/railUpRightPink.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //::::::::::: RAIL UP LEFT PINK :::::::::::\\

    // Creating an image
    static public Image railUpLeftPink;

    static {
        try {
            railUpLeftPink = new Image(new FileInputStream("resources/mapAssets/railUpLeftPink.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //::::::::::: RAIL RIGHT UP PINK :::::::::::\\

    // Creating an image
    static public Image railRightUpPink;

    static {
        try {
            railRightUpPink = new Image(new FileInputStream("resources/mapAssets/railRightUpPink.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //::::::::::: RAIL LEFT UP PINK :::::::::::\\

    // Creating an image
    static public Image railLeftUpPink;

    static {
        try {
            railLeftUpPink = new Image(new FileInputStream("resources/mapAssets/railLeftUpPink.png"));
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










    //::::::::::: Mrs. Pac-Man RIGHT :::::::::::\\

    //Creating an image
    public static Image MrspacmanRight;

    static {
        try {
            MrspacmanRight = new Image(new FileInputStream("resources/characters/mrspacmanRight.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    //::::::::::: Pac-Man UP :::::::::::\\

    //Creating an image
    public static Image MrspacmanUp;

    static {
        try {
            MrspacmanUp = new Image(new FileInputStream("resources/characters/mrspacmanUp.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    //::::::::::: Pac-Man DOWN :::::::::::\\

    //Creating an image
    public static Image MrspacmanDown;

    static {
        try {
            MrspacmanDown = new Image(new FileInputStream("resources/characters/mrspacmanDown.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    //::::::::::: Pac-Man LEFT :::::::::::\\

    //Creating an image
    public static Image MrspacmanLeft;

    static {
        try {
            MrspacmanLeft = new Image(new FileInputStream("resources/characters/mrspacmanLeft.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


