package inkball.scenes;

import inkball.game.GameGrid;
import processing.core.PApplet;

import java.io.File;

public class LevelScene extends Scene {
    private int panelHeight;
    private int level = 1;
    private boolean pause = false;
    private boolean gameStarted = false;

    private GameGrid gameGrid;

    public LevelScene(PApplet sketch, GameScene gameScene, int width, int height, int panelHeight) {
        super(sketch, gameScene, width, height);
        this.panelHeight = panelHeight;
    }

    private boolean loadLevel(int level) {
        File f = new File("assets/levels/level" + level + ".lvl");
        if (f.exists() && f.isFile()) {
            this.gameGrid = new GameGrid();
            return true;
        }

        return false;
    }
}
