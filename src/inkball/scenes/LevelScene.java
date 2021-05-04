package inkball.scenes;

import inkball.game.GameGrid;
import inkball.game.InkLinesSystem;
import inkball.loader.LevelLoader;
import processing.core.PApplet;

import java.io.File;
import java.io.FileNotFoundException;

public class LevelScene extends Scene {
    private int panelHeight;
    private int level = 1;
    private boolean gamePaused = false;
    private boolean gameStarted = false;

    private GameGrid gameGrid;
    private InkLinesSystem userLines;

    public LevelScene(PApplet sketch, GameScene gameScene, int width, int height, int panelHeight) {
        super(sketch, gameScene, width, height);
        this.panelHeight = panelHeight;
        this.userLines = new InkLinesSystem(sketch);
    }

    private boolean loadLevel(int level) {
        File folder = new File("assets/levels");
        File f = null;

        if (folder.isDirectory() && folder.listFiles().length > 0) {
            File[] files = folder.listFiles();
            f = level < files.length ? files[level] : files[0];
        }

        if (f != null && f.isFile()) {
            try {
                this.gameGrid = LevelLoader.createGameGrid(f.getPath(), width, height - panelHeight);
                this.gameGrid.setSketch(sketch);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        loadLevel(0);
    }

    @Override
    public void update() {
        if (gamePaused) {
            sketch.background(220, 250);
        } else {
            sketch.background(220);
        }

        sketch.translate(0, panelHeight);
        gameGrid.update();

        userLines.draw(sketch.mouseX, sketch.mouseY - panelHeight, sketch.pmouseX, sketch.pmouseY - panelHeight);
        userLines.update();
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        gameGrid.mousePressed(mouseX, mouseY);
        userLines.startDraw(mouseX, mouseY - panelHeight);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        gameGrid.mouseReleased(mouseX, mouseY);
        userLines.stopDraw();
    }
}
