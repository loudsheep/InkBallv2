package inkball.game;

import processing.core.PApplet;

import java.util.ArrayList;

public class InkLinesSystem {
    private PApplet sketch;
    private ArrayList<InkLine> lines;

    private boolean drawing = false;
    private InkLine newLine = null;

    public InkLinesSystem(PApplet sketch) {
        lines = new ArrayList<>();
        this.sketch = sketch;
    }

    public void draw(int mouseX, int mouseY, int pmouseX, int pmouseY) {
        if (!drawing) return;
        if (mouseX < 0 || mouseY < 0) return;
        if (!(mouseX == pmouseX && mouseY == pmouseY)) newLine.append(mouseX, mouseY, pmouseX, pmouseY);


        newLine.update();
    }

    public void startDraw(int mouseX, int mouseY, int pmouseX, int pmouseY) {
        drawing = true;
        InkLine newLine = new InkLine(sketch);
        lines.add(newLine);

        if (mouseX >= 0 && mouseY >= 0) {
            newLine.append(mouseX, mouseY, pmouseX, pmouseY);
        }
    }

    public void stopDraw() {
        drawing = false;
        newLine = null;
    }

    public void stopDraw(boolean ifHit) {
        if (!ifHit) return;
        drawing = false;
        newLine = null;
    }

    public void stopDraw(InkLine hit) {
        if (newLine == hit) {
            for (InkLine i : lines) {
                if (i == hit) {
                    lines.remove(hit);
                    break;
                }
            }
            stopDraw();
        } else {
            for (InkLine i : lines) {
                if (i == hit) {
                    lines.remove(hit);
                    return;
                }
            }
        }
    }

    public void update() {
        lines.forEach(InkLine::update);
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }
}
