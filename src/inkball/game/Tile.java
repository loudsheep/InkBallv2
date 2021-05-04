package inkball.game;

import inkball.util.Settings;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import util.Color;
import vector.Vector2;

import java.util.HashMap;
import java.util.Map;

public class Tile {
    public enum TILE_TYPE {
        WALL,
        FREE,
        SPAWN,
        HOLE,
        ONE_WAY_UP,
        ONE_WAY_RIGHT,
        ONE_WAY_DOWN,
        ONE_WAY_LEFT,
        NONE
    }

    public enum TILE_COLOR { // used only when tile is a hole for ball
        BLUE,
        GREEN,
        ORANGE,
        YELLOW,
        GREY
    }

    public static Map<TILE_COLOR, Color> colorMap;

    static {
        colorMap = new HashMap<>(5);
        colorMap.put(TILE_COLOR.BLUE, new Color(63, 72, 204));
        colorMap.put(TILE_COLOR.GREEN, new Color(34, 177, 76));
        colorMap.put(TILE_COLOR.ORANGE, new Color(255, 127, 39));
        colorMap.put(TILE_COLOR.YELLOW, new Color(255, 242, 0));
        colorMap.put(TILE_COLOR.GREY, new Color(200, 200, 200));
    }

    private PApplet sketch;

    private float width;
    private float height;
    private Vector2 position;
    private Vector2 center;
    private TILE_TYPE tileType;
    private TILE_COLOR tileColor;

    private float attractionRadius = Settings.ATTRACTION_RADIUS;

    public Tile(float positionX, float positionY, float width, float height, TILE_TYPE tileType) {
        this.position = new Vector2(positionX, positionY);
        this.width = width;
        this.height = height;

        this.center = new Vector2(positionX + width / 2f, positionY + height / 2f);

        this.tileType = tileType;
    }

    public Tile(float positionX, float positionY, float width, float height, TILE_TYPE tileType, TILE_COLOR tileColor) {
        this.position = new Vector2(positionX, positionY);
        this.width = width;
        this.height = height;

        this.center = new Vector2(positionX + width / 2f, positionY + height / 2f);

        this.tileType = tileType;
        this.tileColor = tileColor;
        this.attractionRadius *= width;
    }

    public void update() {
        if(tileType == TILE_TYPE.NONE) return;

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
        } else if (tileType == TILE_TYPE.ONE_WAY_DOWN || tileType == TILE_TYPE.ONE_WAY_LEFT || tileType == TILE_TYPE.ONE_WAY_RIGHT || tileType == TILE_TYPE.ONE_WAY_UP) {
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
        } else if (tileType == TILE_TYPE.HOLE) {
            Color color = colorMap.get(this.tileColor);
            sketch.fill(color.r, color.g, color.b);
            sketch.stroke(200);

            sketch.rect(position.x, position.y, width, height);

            sketch.stroke(50);
            sketch.strokeWeight(width / 20f);
            sketch.fill(0);
            sketch.circle(center.x, center.y, width / 1.5f);
        }
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getCenter() {
        return center;
    }

    public TILE_TYPE getTileType() {
        return tileType;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }
}
