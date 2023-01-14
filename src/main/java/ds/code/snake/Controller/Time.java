package ds.code.snake.Controller;

import javafx.scene.canvas.GraphicsContext;

public class Time implements Runnable {
    private final Mechanics mechanics;
    private final GraphicsContext graphicsContext;
    private int frameRate;
    private float interval;
    private boolean running;
    private boolean paused;
    private boolean isKeyPressed;

    public Time(final Mechanics mechanics, final GraphicsContext graphicsContext) {
        this.mechanics = mechanics;
        this.graphicsContext = graphicsContext;
        frameRate = 30;
        interval = 1200.0f / frameRate;
        running = true;
        paused = false;
        isKeyPressed = false;
    }

    @Override
    public void run() {
        while (running && !paused) {
            float time = System.currentTimeMillis();

            isKeyPressed = false;
            mechanics.update();
            Mechanics.paint(mechanics, graphicsContext);

            if (!mechanics.getSnake().isSafe()) {
                pause();
                Mechanics.paintResetMessage(graphicsContext);
                break;
            }

            time = System.currentTimeMillis() - time;

            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException e) {
                }
            }
        }
    }


    public boolean isKeyPressed() {
        return isKeyPressed;
    }

    public void pause() {
        paused = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isRunning() {
        return running;
    }

}