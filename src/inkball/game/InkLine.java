package inkball.game;

import processing.core.PApplet;
import processing.core.PVector;
import vector.Vector2;

import java.util.ArrayList;

public class InkLine {
    static class Line {
        public Vector2 start, end;
        public float angle;
        public float len;
        public final float lineThickness;

        public Line(Vector2 start, Vector2 end, float lineThickness) {
            this.start = start;
            this.end = end;
            this.angle = PApplet.atan2(start.y - end.y, start.x - end.x);
            this.len = start.dist(end);
            this.lineThickness = lineThickness;
        }
    }

    public ArrayList<Line> line;
    private PApplet sketch;
    public final float lineThickness = 5;

    public InkLine(PApplet sketch) {
        this.sketch = sketch;
        line = new ArrayList<>();
    }

    public void append(int mouseX, int mouseY, int pmouseX, int pmouseY) {
        line.add(new Line(new Vector2(mouseX, mouseY), new Vector2(pmouseX, pmouseY), lineThickness));
    }

    public void update() {
        sketch.stroke(0);
        sketch.strokeWeight(lineThickness);
        sketch.fill(0);
        for (Line lineSegment : line) {
            sketch.line(lineSegment.start.x, lineSegment.start.y, lineSegment.end.x, lineSegment.end.y);
        }
    }
}
