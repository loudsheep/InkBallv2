package inkball.game;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void update(GameGrid gameGrid, InkLinesSystem userLines) {
        for (Ball b : balls) {
            b.update(gameGrid, userLines);
        }
    }

    public void addWaitingBall(Ball b, int frame) {
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
}
