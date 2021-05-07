package inkball.scenes;

import gui.Button;
import inkball.game.GameGrid;
import inkball.game.InkLinesSystem;
import inkball.loader.LevelLoader;
import inkball.util.Settings;
import processing.core.PApplet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

public class LevelScene extends Scene {
    private int panelHeight;
    private int level = 0;
    private int maxLevel = 0;
    private boolean gamePaused = false;
    private boolean gameStarted = false;

    // ui
    private Button nextLvl;
    private Button prevLvl;
    private Button resetLvl;
    private Button togglePause;
    private Button quitToMenu;
    private Button resumeGame;

    // game related stuff
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
        System.out.println(level);

        if (folder.isDirectory() && folder.listFiles().length > 0) {
            File[] files = folder.listFiles();
            this.maxLevel = files.length - 1;
            if (level < files.length) {
                f = files[level];
            } else {
                f = files[files.length - 1];
                level--;
            }
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
        nextLvl = new Button(sketch, "Next level", Settings.textSize / 2, width - (width / 8f * 3), -panelHeight,
                width / 8f, panelHeight);
        nextLvl.setAction(this::nextLevel);
        nextLvl.setActive(true);

        prevLvl = new Button(sketch, "Previous level", Settings.textSize / 2, width - (width / 8f * 4), -panelHeight,
                width / 8f, panelHeight);
        prevLvl.setAction(this::prevLevel);
        prevLvl.setActive(true);

        resetLvl = new Button(sketch, "Restart level", Settings.textSize / 2, width - (width / 8f * 2), -panelHeight,
                width / 8f, panelHeight);
        resetLvl.setAction(this::restartLevel);
        resetLvl.setActive(true);

        togglePause = new Button(sketch, "Pause", Settings.textSize / 2, width - width / 8f, -panelHeight,
                width / 8f, panelHeight);
        togglePause.setAction(this::togglePause);
        togglePause.setActive(true);

        quitToMenu = new Button(sketch, "Quit to main menu", Settings.textSize, width / 4f, (int) (height / 5 * 2.5),
                width / 2f,
                height / 10f);
        quitToMenu.setAction(this::endGame);
        quitToMenu.setActive(false);

        resumeGame = new Button(sketch, "Resume", Settings.textSize, width / 4f, (int) (height / 5 * 1.5), width / 2f,
                height / 10f);
        resumeGame.setAction(this::togglePause);
        resumeGame.setActive(false);

        loadLevel(level);
    }

    @Override
    public void update() {


        if (gamePaused) {
            sketch.background(220, 250);
        } else {
            sketch.background(220);
        }

        sketch.translate(0, panelHeight);

        if (!gamePaused) {
            gameGrid.update();
            userLines.draw(sketch.mouseX, sketch.mouseY - panelHeight, sketch.pmouseX, sketch.pmouseY - panelHeight);
            userLines.update();
        }
        nextLvl.show();
        prevLvl.show();
        resetLvl.show();
        togglePause.show();
        resumeGame.show();
        quitToMenu.show();
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        gameGrid.mousePressed(mouseX, mouseY);
        userLines.startDraw(mouseX, mouseY - panelHeight);
        nextLvl.clicked(mouseX, mouseY - panelHeight);
        prevLvl.clicked(mouseX, mouseY - panelHeight);
        resetLvl.clicked(mouseX, mouseY - panelHeight);
        togglePause.clicked(mouseX, mouseY - panelHeight);
        resumeGame.clicked(mouseX, mouseY - panelHeight);
        quitToMenu.clicked(mouseX, mouseY - panelHeight);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        gameGrid.mouseReleased(mouseX, mouseY);
        userLines.stopDraw();
        nextLvl.released(mouseX, mouseY - panelHeight);
        prevLvl.released(mouseX, mouseY - panelHeight);
        resetLvl.released(mouseX, mouseY - panelHeight);
        togglePause.released(mouseX, mouseY - panelHeight);
        resumeGame.released(mouseX, mouseY - panelHeight);
        quitToMenu.released(mouseX, mouseY - panelHeight);
    }

    private void endGame() {
        gameScene.setScene(0);
    }

    private void restartLevel() {
        userLines.clear();
        loadLevel(level);
    }

    private void nextLevel() {
        if (level + 1 > maxLevel) return;
        userLines.clear();
        loadLevel(++level);
    }

    private void prevLevel() {
        if (level - 1 < 0) return;
        userLines.clear();
        loadLevel(--level);
    }

    private void togglePause() {
        togglePause(!this.gamePaused);
    }

    private void togglePause(boolean pause) {
        this.gamePaused = pause;

        nextLvl.setActive(!pause);
        prevLvl.setActive(!pause);
        resetLvl.setActive(!pause);
        togglePause.setActive(!pause);

        resumeGame.setActive(pause);
        quitToMenu.setActive(pause);
    }
}
