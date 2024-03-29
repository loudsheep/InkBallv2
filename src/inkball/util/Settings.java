package inkball.util;

import inkball.game.Ball;
import inkball.game.BallColor;
import util.Color;

import java.util.HashMap;
import java.util.Map;

public class Settings {
    public static boolean DEBUG = false;

    public static final int MIN_WIDTH = 10;
    public static final int MIN_HEIGHT = 10;
    public static final int SIDE_MENU_SIZE = 200;

    public static final Map<BallColor, Color> ballColors;

    static {
        ballColors = new HashMap<>(4);
        ballColors.put(BallColor.BLUE, new Color(53, 62, 194));
        ballColors.put(BallColor.GREEN, new Color(24, 167, 66));
        ballColors.put(BallColor.ORANGE, new Color(245, 117, 29));
        ballColors.put(BallColor.YELLOW, new Color(201, 191, 4));
    }

    public static final float ATTRACTION_RADIUS = 0.3f; // set attraction radius of a hole to 1/3 of tiles width

    public static final int textSize = 20;

    public static final int maxDynamicBallsInFile = 10;
    public static final int holeCalculationBorder = 3; // if a ball is closer to a hole that this value, then
    // attraction force are to be calculated
    public static final int newWaitingBallTimeOffset = 90;
}
