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

    public void update() {
        updatePosition();

        sketch.noStroke();
        sketch.circle(position.x, position.y, radius * 2);
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }
}
