package inkball.loader;

import inkball.game.BallSystem;
import inkball.game.GameGrid;
import inkball.game.Tile;
import vector.Vector2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LevelLoader {

    public static GameGrid createGameGrid(String filePath, int gameWidth, int gameHeight) throws FileNotFoundException {
        File levelFile = new File(filePath);
        Scanner scanner = new Scanner(levelFile);

        List<String> lines = new ArrayList<>();
        while (scanner.hasNext()) {
            lines.add(scanner.next());
        }

        int currentLine = 0;

        if (lines.size() <= 1) throw new IncorrectFileFormat("");

        String[] dimesions = lines.get(0).split(",");
        if (dimesions.length != 2) throw new IncorrectFileFormat("First line does not specify map dimensions");
        currentLine++;
        Vector2 mapDimensions = new Vector2(Integer.parseInt(dimesions[0]), Integer.parseInt(dimesions[1]));

        if (lines.size() <= mapDimensions.x) throw new IncorrectFileFormat("File is missing map description");

        GameGrid resultGrid = new GameGrid((int) mapDimensions.x, (int) mapDimensions.y, gameWidth, gameHeight);
        Tile[][] gridTiles = new Tile[(int) mapDimensions.x][(int) mapDimensions.y];

        Vector2 squareSize = new Vector2(gameWidth / mapDimensions.x, gameHeight / mapDimensions.y);
        System.out.println(squareSize);

        // set ball radius to be 2 pixels thinner than tile width
        float ballRadius = (squareSize.x / 2) - 2;

        int positionY = 0;
        for (int i = 1; i < mapDimensions.y + 1; i++) {
            String[] line = lines.get(i).split(",");

            int positionX = 0;
            for (int j = 0; j < line.length; j++) {
                String[] expr = line[j].split("=");
                if (expr.length < 2) throw new IncorrectFileFormat("Error while splitting by '='");

                int numberOfSquares = Integer.parseInt(expr[0]);
                Tile.TILE_TYPE tileType = Tile.TILE_TYPE.valueOf(expr[1]);
                Tile.TILE_COLOR tileColor = null;

                if (expr.length == 3) {
                    tileColor = Tile.TILE_COLOR.valueOf(expr[2]);
                    resultGrid.setTileAt(positionX, positionY,
                            new Tile(positionX * squareSize.x, positionY * squareSize.y, squareSize.x * 3,
                                    squareSize.y * 3,
                                    tileType, tileColor));
                    positionX++;
                    continue;
                }

                for (int k = 0; k < numberOfSquares; k++) {
                    resultGrid.setTileAt(positionX, positionY,
                            new Tile(positionX * squareSize.x, positionY * squareSize.y, squareSize.x, squareSize.y, tileType));
                    positionX++;
                }
            }

            positionY++;
            currentLine++;
        }

        return resultGrid;
    }

    public static BallSystem loadBallConfig(String filePath) {
        return null;
    }
}
