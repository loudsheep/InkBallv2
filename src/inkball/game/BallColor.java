package inkball.game;

import inkball.util.Settings;
import util.Color;

public enum BallColor {
    BLUE {
        @Override
        public Color color() {
            return Settings.ballColors.get(BLUE);
        }
    },
    GREEN {
        @Override
        public Color color() {
            return Settings.ballColors.get(GREEN);
        }
    },
    ORANGE {
        @Override
        public Color color() {
            return Settings.ballColors.get(ORANGE);
        }
    },
    YELLOW {
        @Override
        public Color color() {
            return Settings.ballColors.get(YELLOW);
        }
    };

    public abstract Color color();
}
