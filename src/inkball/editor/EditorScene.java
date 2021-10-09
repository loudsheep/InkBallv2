package inkball.editor;

import inkball.scenes.GameScene;
import inkball.scenes.Scene;
import processing.core.PApplet;

public class EditorScene extends Scene {
    private int sideMenuSize;

    public EditorScene(PApplet sketch, GameScene gameScene, int width, int height, int sideMenuSize) {
        super(sketch, gameScene, width, height);
        this.sideMenuSize = sideMenuSize;
    }
}
