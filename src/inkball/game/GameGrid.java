package inkball.game;

import processing.core.PApplet;

import java.util.List;

public class GameGrid {
    private int squaresX, squaresY;
    private int width, height;
    private int gameFrameCount;

    private PApplet sketch;
    private Tile[][] map;
    private List<Ball> balls;
    private List<Ball> waitingBalls;

    private InkLinesSystem userLines;

    public GameGrid(PApplet sketch, int squaresX, int squaresY, int width, int height) {
        this.sketch = sketch;
        this.squaresX = squaresX;
        this.squaresY = squaresY;
        this.width = width;
        this.height = height;
        this.userLines = new InkLinesSystem(sketch);
    }

    public GameGrid(int squaresX, int squaresY, int width, int height) {
        this.squaresX = squaresX;
        this.squaresY = squaresY;
        this.width = width;
        this.height = height;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
        this.userLines = new InkLinesSystem(sketch);
    }

    public void update() {
        userLines.draw(sketch.mouseX, sketch.mouseY, sketch.pmouseX, sketch.pmouseY);
        userLines.update();
    }

    public void mousePressed(int mouseX, int mouseY) {
        userLines.startDraw(mouseX, mouseY, sketch.pmouseX, sketch.pmouseY);
    }

    public void mouseReleased(int mouseX, int mouseY) {
        userLines.stopDraw();
    }
}
