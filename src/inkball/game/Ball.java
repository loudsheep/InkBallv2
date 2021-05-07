package inkball.game;

import processing.core.PApplet;
import processing.core.PVector;
import vector.Vector2;

public class Ball {
    public enum COLOR {
        BLUE,
        GREEN,
        ORANGE,
        YELLOW
    }

    private PApplet sketch;
    private Vector2 position;
    private Vector2 velocity;
    private float maxSpeed;
    private float radius;
    private COLOR color;

    public Ball(int positionX, int positionY, float maxSpeed, float radius, COLOR color) {
        this.position = new Vector2(positionX, positionY);
        this.velocity = Vector2.fromAngle((float) (Math.random() * Math.PI * 2));   // initial velocity not specified so pick a random one
        this.maxSpeed = maxSpeed;
        this.radius = radius;
        this.color = color;
    }

    public Ball(int positionX, int positionY, Vector2 initialVelocity, float maxSpeed, float radius, COLOR color) {
        this.position = new Vector2(positionX, positionY);
        this.velocity = initialVelocity.copy().setLength(maxSpeed);
        this.maxSpeed = maxSpeed;
        this.radius = radius;
        this.color = color;
    }

    private void updatePosition() {
        this.velocity.limit(maxSpeed);
        this.position.add(this.velocity);
    }

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

    private void show() {
        switch (color) {
            case BLUE:
                sketch.fill(53, 62, 194);
                break;
            case GREEN:
                sketch.fill(24, 167, 66);
                break;
            case ORANGE:
                sketch.fill(245, 117, 29);
                break;
            case YELLOW:
                sketch.fill(245, 232, 0);
                break;
        }

        sketch.noStroke();
        sketch.circle(position.x, position.y, radius * 2);
    }

    public void update(GameGrid gameGrid, InkLinesSystem userLines) {
        updatePosition();
        collideWithLines(userLines);

        show();
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }

    private boolean intersectingWithLine(InkLine.Line line) {
        Vector2 closest = pointOnLineClosestToCircle(line);
        float circleToLineDistSq = Vector2.distSq(position, closest);

        return circleToLineDistSq < (radius + 5) * (radius + 5); // ... + 5 is line thickness
    }

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
}
