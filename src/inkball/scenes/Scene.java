package inkball.scenes;

import processing.core.PApplet;

public abstract class Scene {
    protected PApplet sketch;
    protected int width, height;

    public Scene(PApplet sketch, int width, int height) {
        this.sketch = sketch;
        this.width = width;
        this.height = height;
    }

    public void init() {
    }

    public void update() {
    }

    public void mousePressed(int mouseX, int mouseY){
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }
}
