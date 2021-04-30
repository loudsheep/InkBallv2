package inkball.scenes;

import gui.Button;
import processing.core.PApplet;

public class MainMenuScene extends Scene {
    private Button start;
    private Button settings;
    private Button quit;
    
    private int textSize = 20;

    public MainMenuScene(PApplet sketch, GameScene gameScene, int width, int height) {
        super(sketch, gameScene, width, height);
    }

    @Override
    public void init() {
        setTitle("InkBall");

        start = new Button(sketch, "Start game", textSize, width / 4f, height / 5f, width / 2f, height / 10f);
        start.setAction(this::startGame);

        settings = new Button(sketch, "Settings", textSize, width / 4f, height / 5f * 2, width / 2f, height / 10f);
        settings.setAction(this::enterSettings);

        quit = new Button(sketch, "Quit", textSize, width / 4f, height / 5f * 3, width / 2f, height / 10f);
        quit.setAction(this.gameScene::quit);

        start.setActive(true);
        settings.setActive(true);
        quit.setActive(true);
    }

    private void startGame() {
        if (gameScene != null) {
            gameScene.setScene(1);
        }
    }

    private void enterSettings() {
        if (gameScene != null) {
            gameScene.setScene(2);
        }
    }

    @Override
    public void update() {
        start.show();
        settings.show();
        quit.show();
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        start.clicked(mouseX, mouseY);
        settings.clicked(mouseX, mouseY);
        quit.clicked(mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        start.released(mouseX, mouseY);
        settings.released(mouseX, mouseY);
        quit.released(mouseX, mouseY);
    }
}
