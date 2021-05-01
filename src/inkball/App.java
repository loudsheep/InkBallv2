package inkball;

import inkball.scenes.*;
import processing.core.PApplet;

public class App extends PApplet implements GameScene {
    private Scene currentScene;
    private int WINDOW_WIDTH = 600;
    private int MENUBAR_HEIGHT = 30;

    @Override
    public void settings() {
        size(WINDOW_WIDTH, WINDOW_WIDTH + MENUBAR_HEIGHT);
    }

    @Override
    public void setup() {
        setTitle("InkBall");
        setScene(0);
    }

    @Override
    public void draw() {
        background(220);
        currentScene.update();
    }

    @Override
    public void mousePressed() {
        currentScene.mousePressed(mouseX, mouseY);
    }

    @Override
    public void mouseReleased() {
        currentScene.mouseReleased(mouseX, mouseY);
    }

    @Override
    public void setTitle(String title) {
        surface.setTitle(title);
    }

    @Override
    public void quit() {
        System.exit(0);
    }

    @Override
    public void setScene(int scene) {
        switch (scene) {
            case 0:
                currentScene = new MainMenuScene(this, this, width, height);
                break;
            case 1:
                currentScene = new LevelScene(this, this, width, height, MENUBAR_HEIGHT);
                break;
            case 2:
                currentScene = new SettingsScene(this, this, width, height);
                break;
            default:
                return;
        }
        currentScene.init();
    }

    @Override
    public void setScene(Scene scene) {
        currentScene = scene;
        currentScene.init();
    }

    public static void main(String[] args) {
        PApplet.main("inkball.App", args);
    }
}
