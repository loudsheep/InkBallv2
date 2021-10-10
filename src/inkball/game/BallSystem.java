package inkball.game;

import inkball.util.Utility;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class BallSystem {
    private PApplet sketch;

    private List<Ball> balls;
    private List<BallQueue> waitingBalls;

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
//        for (Ball b : waitingBalls) {
//            b.setSketch(sketch);
//        }
    }

    public void update(GameGrid gameGrid, InkLinesSystem userLines, int gameFrame) {
        for (Ball b : balls) {
            b.update(gameGrid, userLines);
        }

        if (gameGrid.getSpawningSquares().size() > 0) {
            for (int i = waitingBalls.size() - 1; i >= 0; i--) {
                BallQueue ball = waitingBalls.get(i);
                if (ball.frame <= gameFrame) {
                    Tile spawner = Utility.getRandomElement(gameGrid.getSpawningSquares());
                    float ballRadius = (spawner.getWidth() / 2) - 2;

                    Ball newBall = new Ball(spawner.getCenter().x, spawner.getCenter().y, ball.speed, ballRadius, ball.color);
                    newBall.setSketch(sketch);
                    balls.add(newBall);
                    waitingBalls.remove(i);
                }
            }
        }
    }

    public void addWaitingBall(int speed, BallColor color, int frame) {
        BallQueue ball = new BallQueue(speed, color, frame);
        waitingBalls.add(ball);
        waitingBalls.sort(BallQueue::compareTo);
    }

    public void addBall(Ball b) {
        b.setSketch(sketch);
        this.balls.add(b);
    }

    public void clearAll() {
        this.balls.clear();
        this.waitingBalls.clear();
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public List<BallQueue> getWaitingBalls() {
        return waitingBalls;
    }
}
