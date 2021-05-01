package inkball.game;

import processing.core.PApplet;
import processing.core.PVector;

public class Ball {
    public enum COLOR {
        BLUE,
        GREEN,
        ORANGE,
        RED,
        YELLOW
    }

    private PApplet sketch;
    private PVector position;
    private PVector velocity;
    private float maxSpeed;
    private float radius;
    private COLOR color;

    public Ball(int positionX, int positionY, float maxSpeed, float radius, COLOR color) {
        this.position = new PVector(positionX, positionY);
        this.velocity = PVector.random2D().mult(maxSpeed);  // initial velocity not specified so pick a random one
        this.maxSpeed = maxSpeed;
        this.radius = radius;
        this.color = color;
    }

    public Ball(int positionX, int positionY, PVector initialVelocity, float maxSpeed, float radius, COLOR color) {
        this.position = new PVector(positionX, positionY);
        this.velocity = initialVelocity.copy().setMag(maxSpeed);
        this.maxSpeed = maxSpeed;
        this.radius = radius;
        this.color = color;
    }

    public void update() {
        sketch.noStroke();
        sketch.circle(position.x, position.y, radius * 2);
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }
}
