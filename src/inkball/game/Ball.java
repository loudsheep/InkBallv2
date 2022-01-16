package inkball.game;

import inkball.util.Settings;
import processing.core.PApplet;
import util.Color;
import vector.Vector2;

public class Ball {
    private PApplet sketch;
    private Vector2 position;
    private Vector2 velocity;
    private float maxSpeed;
    private float radius;
    private BallColor color;

    public Ball(float positionX, float positionY, float maxSpeed, float radius, BallColor color) {
        this.position = new Vector2(positionX, positionY);
        this.velocity = Vector2.fromAngle((float) (Math.random() * Math.PI * 2)).setLength(maxSpeed);   // initial velocity not specified so pick a random one
        this.maxSpeed = maxSpeed;
        this.radius = radius;
        this.color = color;
    }

    public Ball(float positionX, float positionY, Vector2 initialVelocity, float maxSpeed, float radius,
                BallColor color) {
        this.position = new Vector2(positionX, positionY);
        this.velocity = initialVelocity.copy().setLength(maxSpeed);
        this.maxSpeed = maxSpeed;
        this.radius = radius;
        this.color = color;
    }

    private void updatePosition() {
        this.velocity.setLength(maxSpeed);
        this.position.add(this.velocity);
    }

    private void show() {
        if (color != null) {
            Color fillColor = color.color();
            sketch.fill(fillColor.r, fillColor.g, fillColor.b);
        }

        sketch.noStroke();
        sketch.circle(position.x, position.y, radius * 2);

        if (Settings.DEBUG) {
            sketch.stroke(255, 0, 0);
            sketch.strokeWeight(3);

            Vector2 vel = new Vector2(this.velocity).mult(radius);
            sketch.line(position.x, position.y, position.x + vel.x, position.y + vel.y);
        }
    }

    public void update(GameGrid gameGrid, InkLinesSystem userLines) {
        updatePosition();
        collideWithMap(gameGrid);
        collideWithLines(userLines);
        collideWithHoles(gameGrid);

        show();
    }

    public void collideWithHoles(GameGrid gameGrid) {
        Vector2 approxPosition = new Vector2();
        approxPosition.x = (int) (position.x / gameGrid.getWidth() * gameGrid.getSquaresX());
        approxPosition.y = (int) (position.y / gameGrid.getHeight() * gameGrid.getSquaresY());

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                Tile hole = gameGrid.getTile((int) (approxPosition.x + i), (int) (approxPosition.y + j));
                if (hole == null) continue;
                if (hole.getTileType() == Tile.TILE_TYPE.HOLE) {
                    sketch.strokeWeight(5);
//                    sketch.stroke(255,0,0);
//                    sketch.point(hole.getCenter().x, hole.getCenter().y);
                    if (hole.getCenter().distSq(approxPosition) < Math.pow(hole.getWidth() / 1.5, 3)) {
                        sketch.stroke(255, 0, 0);
                        sketch.point(hole.getCenter().x, hole.getCenter().y);
                    }
                }
            }
        }
    }

    // collision with tiles on map
    public void collideWithMap(GameGrid gameGrid) {
        Vector2 gridPosition = new Vector2();
        gridPosition.x = (int) (position.x / gameGrid.getWidth() * gameGrid.getSquaresX());
        gridPosition.y = (int) (position.y / gameGrid.getHeight() * gameGrid.getSquaresY());

        if (Settings.DEBUG) {
            sketch.stroke(255, 0, 0);
            sketch.strokeWeight(2);
            sketch.fill(255, 0, 0, 100);
            sketch.rect((gridPosition.x - 1) * gameGrid.getSquareWidth(),
                    (gridPosition.y - 1) * gameGrid.getSquareWidth(),
                    gameGrid.getSquareWidth() * 3,
                    gameGrid.getSquareWidth() * 3);
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Tile currentTile = gameGrid.getTile((int) (gridPosition.x + i), (int) (gridPosition.y + j));
                if (currentTile != null) {
                    if (currentTile.getTileType() == Tile.TILE_TYPE.FREE ||
                            currentTile.getTileType() == Tile.TILE_TYPE.SPAWN ||
                            currentTile.getTileType() == Tile.TILE_TYPE.NONE ||
                            currentTile.getTileType() == Tile.TILE_TYPE.HOLE) { // don't do physics on that types of tiles
                        continue;
                    }

                    if (sideCollision(currentTile)) continue;

                    if (edgeCollision(currentTile)) continue;
                }
            }
        }
    }

    private boolean sideCollision(Tile tile) {
        if (tile.getTileType() == Tile.TILE_TYPE.ONE_WAY_UP || tile.getTileType() == Tile.TILE_TYPE.ONE_WAY_DOWN || tile.getTileType() == Tile.TILE_TYPE.ONE_WAY_LEFT || tile.getTileType() == Tile.TILE_TYPE.ONE_WAY_RIGHT) {
            return false;
        }

        if (position.y + radius >= tile.getPosition().y && position.y < tile.getPosition().y + tile.getHeight() && position.x >= tile.getPosition().x && position.x <= tile.getPosition().x + tile.getWidth()) { // up side collision
            velocity.y = -velocity.y;
            position.y = tile.getPosition().y - radius;

            return true;
        }

        if (position.y - radius <= tile.getPosition().y + tile.getHeight() && position.y > tile.getPosition().y && position.x >= tile.getPosition().x && position.x <= tile.getPosition().x + tile.getWidth()) { // down side collision
            velocity.y = -velocity.y;
            position.y = tile.getPosition().y + tile.getHeight() + radius;

            return true;
        }

        if (position.x + radius >= tile.getPosition().x && position.x < tile.getPosition().x + tile.getWidth() && position.y >= tile.getPosition().y && position.y <= tile.getPosition().y + tile.getHeight()) { // left side collision
            velocity.x = -velocity.x;
            position.x = tile.getPosition().x - radius;

            return true;
        }

        if (position.x - radius <= tile.getPosition().x + tile.getWidth() && position.x > tile.getPosition().x && position.y >= tile.getPosition().y && position.y <= tile.getPosition().y + tile.getHeight()) { // right side collision
            velocity.x = -velocity.x;
            position.x = tile.getPosition().x + tile.getWidth() + radius;

            return true;
        }


        return false;
    }

    private boolean edgeCollision(Tile tile) {
        if (tile.collidableEdges == Tile.NONE) return false;
//        float nearestX = PApplet.max(s.getPosX(), PApplet.min(pos.x, s.getPosX() + s.getW()));
//        float nearestY = PApplet.max(s.getPosY(), PApplet.min(pos.y, s.getPosY() + s.getH()));

        float nearestX = Math.max(tile.getPosition().x, Math.min(position.x, tile.getPosition().x + tile.getWidth()));
        float nearestY = Math.max(tile.getPosition().y, Math.min(position.y, tile.getPosition().y + tile.getHeight()));

        boolean top_left = (tile.collidableEdges & Tile.TOP_LEFT) == Tile.TOP_LEFT && nearestX == tile.getPosition().x && nearestY == tile.getPosition().y;
        boolean top_right = (tile.collidableEdges & Tile.TOP_RIGHT) == Tile.TOP_RIGHT && nearestX == tile.getPosition().x + tile.getWidth() && nearestY == tile.getPosition().y;
        boolean bottom_left = (tile.collidableEdges & Tile.BOTTOM_LEFT) == Tile.BOTTOM_LEFT && nearestX == tile.getPosition().x && nearestY == tile.getPosition().y + tile.getHeight();
        boolean bottom_right = (tile.collidableEdges & Tile.BOTTOM_RIGHT) == Tile.BOTTOM_RIGHT && nearestX == tile.getPosition().x + tile.getWidth() && nearestY == tile.getPosition().y + tile.getHeight();

        if (top_left || top_right || bottom_left || bottom_right) {
            Vector2 distance = new Vector2(position.x - nearestX, position.y - nearestY);

            if (distance.lengthSq() >= radius * radius) return false;
            System.out.println("Collision");

            Vector2 normal = new Vector2(distance).normalize();
            float dot = Vector2.dot(velocity, normal);

            velocity.x -= 2 * dot * normal.x;
            velocity.y -= 2 * dot * normal.y;

            updatePosition();
            return true;

//            Vector2 normal = new Vector2(-distance.y, distance.x);
//            double normalAngle = Math.atan2(normal.y, normal.x);
//            double angle = Math.atan2(velocity.y, velocity.x);
//
//            double theta = normalAngle - angle;
//            velocity.x = (float) (Math.cos(theta) * velocity.length());
//            velocity.y = (float) (Math.sin(theta) * velocity.length());
//
//            while(distance.lengthSq() < radius * radius) {
//                updatePosition();
//                distance = new Vector2(position.x - nearestX, position.y - nearestY);
//            }
//
//            return true;
        }

        return false;
    }

    // collision with lines drawn by player
    private void collideWithLines(InkLinesSystem userLines) {
        if (userLines.getLinesLength() == 0) return;

        for (int i = userLines.getLinesLength() - 1; i >= 0; i--) {
            InkLine inkLine = userLines.getLine(i);
            for (InkLine.Line line : inkLine.line) {
                if (!intersectingWithLine(line)) continue;

                Vector2 normal = bounceLineNormal(line);
                float dot = Vector2.dot(velocity, normal);

                velocity.x -= 2 * dot * normal.x;
                velocity.y -= 2 * dot * normal.y;

                updatePosition();

                userLines.stopDraw(inkLine);
                return;
            }
        }
    }

    private boolean intersectingWithLine(InkLine.Line line) {
        Vector2 closest = pointOnLineClosestToCircle(line);

        float circleToLineDistSq = Vector2.distSq(position, closest);

        return circleToLineDistSq < (radius + line.lineThickness) * (radius + line.lineThickness);
    }

    // helper methods
    private Vector2 between(Vector2 start, Vector2 end) { // returns vector between two vectors (used for line collision)
        return new Vector2(end).sub(start);
    }

    private Vector2 pointOnLineClosestToCircle(InkLine.Line line) { // as name suggest returns pointOnLineClosestToCircle

        Vector2 endPoint1 = line.start;
        Vector2 endPoint2 = line.end;

        Vector2 lineUnitVector = between(endPoint1, endPoint2).normalize();

        Vector2 lineEnd = between(endPoint1, position);

        float proj = Vector2.dot(lineEnd, lineUnitVector);

        if (proj <= 0) {
            return endPoint1;
        }

        if (proj >= line.len) {
            return endPoint2;
        }

        return new Vector2(endPoint1.x + lineUnitVector.x * proj, endPoint1.y + lineUnitVector.y * proj);
    }

    private Vector2 bounceLineNormal(InkLine.Line line) {
        Vector2 closest = between(pointOnLineClosestToCircle(line), position);

        return closest.normalize();
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }
}
