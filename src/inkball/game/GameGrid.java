package inkball.game;

import inkball.util.Settings;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class GameGrid {
    private int squaresX, squaresY;
    private int width, height;
    private int gameFrameCount;

    private PApplet sketch;
    private Tile[][] map;
    private List<Tile> spawningSquares = new ArrayList<>();
    private boolean recalculateCollidableEdges = true;

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

        if(Settings.DEBUG) {
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j < map[i].length; j++) {
                    if(map[i][j].collidableEdges != Tile.NONE) {
                        map[i][j].drawCollidableEdges();
                    }
                }
            }
        }
    }

    public void setTileAt(int x, int y, Tile tile) {
        if (x >= map.length || y >= map[x].length) {
            return;
        }

        map[x][y] = tile;
        if (tile.getTileType() == Tile.TILE_TYPE.SPAWN) {
            spawningSquares.add(tile);
        }
        recalculateCollidableEdges = true;
    }

    public void calculateCollidableEdges() {
        if (!recalculateCollidableEdges) return;

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                Tile tile = map[x][y];
                if (tile.getTileType() != Tile.TILE_TYPE.WALL) continue;

                tile.collidableEdges = Tile.TOP_LEFT | Tile.TOP_RIGHT | Tile.BOTTOM_LEFT | Tile.BOTTOM_RIGHT;

                if (inMap(x - 1, y)) {
                    if (map[x - 1][y].getTileType() == Tile.TILE_TYPE.WALL) {
                        tile.collidableEdges &= ~Tile.TOP_LEFT;
                        tile.collidableEdges &= ~Tile.BOTTOM_LEFT;
                    }
                }

                if (inMap(x + 1, y)) {
                    if (map[x + 1][y].getTileType() == Tile.TILE_TYPE.WALL) {
                        tile.collidableEdges &= ~Tile.TOP_RIGHT;
                        tile.collidableEdges &= ~Tile.BOTTOM_RIGHT;
                    }
                }

                if (inMap(x, y - 1)) {
                    if (map[x][y - 1].getTileType() == Tile.TILE_TYPE.WALL) {
                        tile.collidableEdges &= ~Tile.TOP_LEFT;
                        tile.collidableEdges &= ~Tile.TOP_RIGHT;
                    }
                }

                if (inMap(x, y + 1)) {
                    if (map[x][y + 1].getTileType() == Tile.TILE_TYPE.WALL) {
                        tile.collidableEdges &= ~Tile.BOTTOM_LEFT;
                        tile.collidableEdges &= ~Tile.BOTTOM_RIGHT;
                    }
                }
            }
        }
    }

    private boolean inMap(int x, int y) {
        return x >= 0 && x < map.length && y >= 0 && y < map[x].length;
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

    public List<Tile> getSpawningSquares() {
        return spawningSquares;
    }
}
