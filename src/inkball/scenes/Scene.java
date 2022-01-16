package inkball.scenes;

import processing.core.PApplet;

public abstract class Scene {
    protected PApplet sketch;
    protected GameScene gameScene;
    protected int width, height;

    public Scene(PApplet sketch, GameScene gameScene, int width, int height) {
        this.sketch = sketch;
        this.gameScene = gameScene;
        this.width = width;
        this.height = height;
    }

    protected void setTitle(String title) {
        if (gameScene != null) {
            gameScene.setTitle(title);
        }
    }

    public void init() {
    }

    public void update() {
    }

    public void mousePressed(int mouseX, int mouseY) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public void keyPressed(int keyCode) {
    }
}
