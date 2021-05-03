package inkball.loader;

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

        // set ball radius to be 2 pixels thinner than tile width
        float ballRadius = (squareSize.x / 2) - 2;

        int postionY = 0;
        for (int i = 1; i < mapDimensions.y + 1; i++) {
            String[] line = lines.get(i).split(",");
            for (int i1 = 0; i1 < line.length; i1++) {
                System.out.println(line[i1]);
            }
            System.out.println();
        }

        return null;
    }
}
