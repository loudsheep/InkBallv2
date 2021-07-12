package inkball.util;

import inkball.game.Ball;
import inkball.game.BallColor;
import util.Color;

import java.util.HashMap;
import java.util.Map;

public class Settings {
    public static boolean DEBUG = true;

    public static final Map<BallColor, Color> ballColors;

    static {
        ballColors = new HashMap<>(4);
        ballColors.put(BallColor.BLUE, new Color(53, 62, 194));
        ballColors.put(BallColor.GREEN, new Color(24, 167, 66));
        ballColors.put(BallColor.ORANGE, new Color(245, 117, 29));
        ballColors.put(BallColor.YELLOW, new Color(245, 232, 0));
    }

    public static final float ATTRACTION_RADIUS = 0.33f; // set attraction radius of a hole to 1/3 of tiles width

    public static final int textSize = 20;
}
