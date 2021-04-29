package inkball;

import inkball.scenes.GameScene;
import inkball.scenes.Scene;
import processing.core.PApplet;

public class App extends PApplet implements GameScene {
    Scene currentScene;

    @Override
    public void settings() {
    }

    @Override
    public void setup() {
    }

    @Override
    public void draw() {
    }

    @Override
    public void mousePressed() {
    }

    @Override
    public void mouseReleased() {
    }

    @Override
    public void setTitle(String title) {
    }

    @Override
    public void quit() {
        System.exit(0);
    }

    @Override
    public void setScene(int scene) {
    }

    public static void main(String[] args) {
        PApplet.main("inkball.App", args);
    }
}
