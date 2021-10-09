package inkball.scenes;

public interface GameScene {
    void setTitle(String title);

    boolean resize(int newWidth, int newHeight);

    void quit();

    void setScene(int scene);

    void setScene(Scene scene);
}
