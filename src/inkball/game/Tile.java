package inkball.game;

import inkball.util.Settings;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Tile {
    public enum TILE_TYPE {
        WALL,
        FREE,
        SPAWN,
        HOLE,
        ONE_WAY_UP,
        ONE_WAY_RIGHT,
        ONE_WAY_DOWN,
        ONE_WAY_LEFT
    }

    private PApplet sketch;

    private int width;
    private int height;
    private PVector position;
    private TILE_TYPE tileType;

    private float attractionRadius = Settings.ATTRACTION_RADIUS;

    public Tile(int positionX, int positionY, int width, int height, TILE_TYPE tileType) {
        this.position = new PVector(positionX, positionY);
        this.width = width;
        this.height = height;
        this.tileType = tileType;
        this.attractionRadius *= width;
    }

    public void update() {
        sketch.stroke(200);
        sketch.strokeWeight(1);

        switch (tileType) {
            case WALL:
                sketch.fill(100);
                break;
            case SPAWN:
                sketch.fill(200);
                break;
            case ONE_WAY_UP:
            case ONE_WAY_LEFT:
            case ONE_WAY_DOWN:
            case ONE_WAY_RIGHT:
                sketch.fill(150);
                break;
            default:
                sketch.fill(220);
                break;
        }

        sketch.rectMode(PConstants.CORNER);
        sketch.rect(position.x, position.y, width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public PVector getPosition() {
        return position;
    }

    public TILE_TYPE getTileType() {
        return tileType;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }
}
