package inkball.scenes;

import inkball.game.GameGrid;
import processing.core.PApplet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LevelScene extends Scene {
    private int panelHeight;
    private int level = 1;
    private boolean gamePaused = false;
    private boolean gameStarted = false;

    private GameGrid gameGrid;

    public LevelScene(PApplet sketch, GameScene gameScene, int width, int height, int panelHeight) {
        super(sketch, gameScene, width, height);
        this.panelHeight = panelHeight;
    }

    private boolean loadLevel(int level) {
        File folder = new File("assets/levels");
        File f = null;

        if (folder.isDirectory() && folder.listFiles().length > 0) {
            File[] files = folder.listFiles();
            f = level < files.length ? files[level] : files[0];
        }

        if (f != null && f.isFile()) {
            this.gameGrid = new GameGrid(sketch, 10, 10, width, height);
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        loadLevel(1);
    }

    @Override
    public void update() {
        if (gamePaused) {
            sketch.background(220, 250);
        } else {
            sketch.background(220);
        }

        sketch.translate(0, panelHeight);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
    }
}
