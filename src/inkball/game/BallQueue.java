package inkball.game;

public class BallQueue implements Comparable {
    public final int speed;
    public final BallColor color;
    public final int frame;

    public BallQueue(int speed, BallColor color, int frame) {
        this.speed = speed;
        this.color = color;
        this.frame = frame;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof BallQueue) {
            BallQueue ball = (BallQueue) o;
            return Integer.compare(this.frame, ball.frame);
        }
        return 0;
    }
}
