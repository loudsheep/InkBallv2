package inkball.game;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class InkLine {
    static class Line {
        PVector start, end;
        float angle;
        float len;

        public Line(PVector start, PVector end) {
            this.start = start;
            this.end = end;
            angle = PApplet.atan2(start.y - end.y, start.x - end.x);
            len = start.dist(end);
        }
    }

    private ArrayList<Line> line;
    private PApplet sketch;
    private final float lineThickness = 5;

    public InkLine(PApplet sketch) {
        this.sketch = sketch;
        line = new ArrayList<>();
    }

    public void append(int mouseX, int mouseY, int pmouseX, int pmouseY) {
        line.add(new Line(new PVector(mouseX, mouseY), new PVector(pmouseX, pmouseY)));
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
