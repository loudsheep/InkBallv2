package inkball.editor;

import gui.Button;
import inkball.game.BallSystem;
import inkball.game.GameGrid;
import inkball.loader.LevelLoader;
import inkball.scenes.GameScene;
import inkball.scenes.LevelScene;
import inkball.scenes.Scene;
import inkball.util.Settings;
import processing.core.PApplet;

import java.io.File;
import java.io.FileNotFoundException;

public class EditorScene extends Scene {
    private int sideMenuSize;
    private int panelHeight;

    private String fileName;

    // ui
    private Button saveButton;
    private Button cancelButton;

    // editor stuff
    private GameGrid gameGrid;
    private BallSystem ballSystem;

    public EditorScene(PApplet sketch, GameScene gameScene, int width, int height, int panelHeight, int sideMenuSize) {
        super(sketch, gameScene, width, height);
        this.panelHeight = panelHeight;
        this.sideMenuSize = sideMenuSize;
        gameScene.resize(width + sideMenuSize, height);

        this.ballSystem = new BallSystem(sketch);
    }

    public EditorScene(PApplet sketch, GameScene gameScene, int width, int height, int panelHeight, int sideMenuSize, String file) {
        this(sketch, gameScene, width, height, panelHeight, sideMenuSize);
        this.fileName = file;
    }

    private boolean loadLevel(int level) {
        File folder = new File("assets/levels");
        File f = null;
        System.out.println(level);

        if (folder.isDirectory() && folder.listFiles().length > 0) {
            File[] files = folder.listFiles();
            if (level < files.length) {
                f = files[level];
            } else {
                f = files[files.length - 1];
                level--;
            }
        }

        if (f != null && f.isFile()) {
            try {
                this.ballSystem.clearAll();
                this.gameGrid = LevelLoader.createGameGrid(f.getPath(), width, height - panelHeight, ballSystem);
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
        int buttonWidth = width / 6;
        int buttonHeight = panelHeight;

        saveButton = new Button(sketch, "Save", Settings.textSize / 2, width - buttonWidth * 2, 0, buttonWidth,
                buttonHeight);
        saveButton.setActive(true);

        cancelButton = new Button(sketch, "Cancel", Settings.textSize / 2, width - buttonWidth, 0, buttonWidth,
                buttonHeight);
        cancelButton.setAction(this::cancelEdit);
        cancelButton.setActive(true);
    }

    private void cancelEdit() {
        gameScene.setScene(0);
    }

    @Override
    public void update() {
        // draw boundaries
        sketch.stroke(0);
        sketch.strokeWeight(1);
        sketch.noFill();
        sketch.rect(0, 0, width, panelHeight);
        sketch.rect(0, panelHeight, width, height);
        sketch.rect(width, 0, sideMenuSize, height);

        // ui updates
        saveButton.show();
        cancelButton.show();
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        saveButton.clicked(mouseX, mouseY);
        cancelButton.clicked(mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        saveButton.released(mouseX, mouseY);
        cancelButton.released(mouseX, mouseY);
    }

    @Override
    public void keyPressed(int keyCode) {
    }
}
