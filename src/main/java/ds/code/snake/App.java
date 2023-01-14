package ds.code.snake;

import ds.code.snake.Controller.Mechanics;
import ds.code.snake.Controller.Time;
import ds.code.snake.Model.Snake;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class App extends Application {

    private static final int width = 1200;
    private static final int height = 800;
    private Time time;
    private Mechanics mechanics;
    private GraphicsContext graphicsContext;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        StackPane stack = new StackPane();
        Scene scene = new Scene(stack, width, height);
        Canvas canvas = new Canvas(width, height);
        graphicsContext = canvas.getGraphicsContext2D();

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> {
            Snake snake = mechanics.getSnake();
            if (time.isKeyPressed()) {
                return;
            }
            switch (e.getCode()) {
                case UP:
                    snake.goUp();
                    break;
                case DOWN:
                    snake.goDown();
                    break;
                case LEFT:
                    snake.goLeft();
                    break;
                case RIGHT:
                    snake.goRight();
                    break;
                case ESCAPE:
                    time.pause();
                    reset();
                    (new Thread(time)).start();
                    break;
                case ENTER:
                    if (time.isPaused()) {
                        reset();
                        (new Thread(time)).start();
                    }

            }
        });

        reset();

        stack.getChildren().add(canvas);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Snake");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.getIcons().add(new Image(getClass().getResource("snake.png").toExternalForm()));
        primaryStage.setScene(scene);
        primaryStage.show();

        (new Thread(time)).start();
    }

    private void reset() {
        mechanics = new Mechanics(width, height);
        time = new Time(mechanics, graphicsContext);
        Mechanics.paint(mechanics, graphicsContext);
    }
}