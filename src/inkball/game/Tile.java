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

        if (tileType == TILE_TYPE.SPAWN) {
            sketch.fill(150);
            sketch.circle(position.x + width / 2f, position.y + height / 2f, width / 3f);
        }
        else if (tileType == TILE_TYPE.ONE_WAY_DOWN || tileType == TILE_TYPE.ONE_WAY_LEFT || tileType == TILE_TYPE.ONE_WAY_RIGHT || tileType == TILE_TYPE.ONE_WAY_UP) {
            sketch.push();

            sketch.fill(3, 84, 0);

            sketch.noStroke();
            sketch.translate(position.x + width / 2f, position.y + height / 2f);
            switch (tileType) {
                case ONE_WAY_DOWN:
                    sketch.rotate(PConstants.PI);
                    sketch.fill(3, 84, 0);
                    break;
                case ONE_WAY_LEFT:
                    sketch.rotate(-PConstants.PI / 2);
                    sketch.fill(0, 6, 84);
                    break;
                case ONE_WAY_RIGHT:
                    sketch.rotate(PConstants.PI / 2);
                    sketch.fill(0, 6, 84);
                    break;
            }

            sketch.triangle(-width / 4f, height / 5f,
                    0, -height / 4f,
                    width / 4f, height / 5f);

            sketch.pop();
        }
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
