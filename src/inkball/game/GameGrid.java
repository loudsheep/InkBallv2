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

    public GameGrid(PApplet sketch, int squaresX, int squaresY, int width, int height) {
        this.sketch = sketch;
        this.squaresX = squaresX;
        this.squaresY = squaresY;
        this.width = width;
        this.height = height;
        this.map = new Tile[squaresX][squaresY];
    }

    public GameGrid(int squaresX, int squaresY, int width, int height) {
        this.squaresX = squaresX;
        this.squaresY = squaresY;
        this.width = width;
        this.height = height;
        this.map = new Tile[squaresX][squaresY];
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j].setSketch(sketch);
            }
        }
    }

    public void update() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j].update();
            }
        }
    }

    public void setTileAt(int x, int y, Tile tile) {
        if (x >= map.length || y >= map[x].length) {
            return;
        }

        map[x][y] = tile;
    }

    public void mousePressed(int mouseX, int mouseY) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= this.map.length) return null;
        if (y < 0 || y >= this.map[x].length) return null;

        return this.map[x][y];
    }

    public int getSquaresX() {
        return squaresX;
    }

    public int getSquaresY() {
        return squaresY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getSquareWidth() {
        return (float) width / squaresX;
    }
}
