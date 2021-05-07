package inkball.game;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class BallSystem {
    private PApplet sketch;

    private List<Ball> balls;
    private List<Ball> waitingBalls;

    private int ballsCount = 0;
    private int ballWaitingCount = 0;

    public BallSystem(PApplet sketch) {
        this();
        this.sketch = sketch;
    }

    public BallSystem() {
        this.balls = new ArrayList<>();
        this.waitingBalls = new ArrayList<>();
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
        for (Ball ball : balls) {
            ball.setSketch(sketch);
        }
        for (Ball b : waitingBalls) {
            b.setSketch(sketch);
        }
    }

    public void update() {
        for (Ball b : balls) {
            b.update();
        }
    }

    public void addWaitingBall(Ball b) {
        b.setSketch(sketch);
        this.waitingBalls.add(b);
        ballWaitingCount++;
    }

    public void addBall(Ball b) {
        b.setSketch(sketch);
        this.balls.add(b);
        ballsCount++;
    }

    public void clearAll() {
        this.balls.clear();
        this.waitingBalls.clear();
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public List<Ball> getWaitingBalls() {
        return waitingBalls;
    }
}
