package ds.code.snake.Controller;

import ds.code.snake.Model.Food;
import ds.code.snake.Model.Point;
import ds.code.snake.Model.Snake;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Random;

public class Mechanics {

    public static final int size = 10;
    public static final Color color = new Color(0.1, 0.1, 0.1, 1);

    private final int columns;
    private final int rows;

    private Snake snake;
    private Food food;

    public Mechanics(final double width, final double height) {
        rows = (int) width / size;
        columns = (int) height / size;
        snake = new Snake(this, new Point(rows / 2, columns / 2));

        // Jedzenie generuje się w losowych miejscach
        food = new Food(getRandomPoint());
    }

    public Point collide(Point point) {
        int x = point.getX();
        int y = point.getY();

        if (x >= rows) {
            snake.safe = false;
        };
        if (y >= columns) {
            snake.safe = false;
        };
        if (x < 0) {
            snake.safe = false;
        };
        if (y < 0) {
            snake.safe = false;
        };
        return point;
    }


    public static void paint(Mechanics mechanics, GraphicsContext graphicsContext) {
        graphicsContext.setFill(Mechanics.color);
        graphicsContext.fillRect(0, 0, mechanics.getWidth(), mechanics.getHeight());

        // Kolor jedzenia
        graphicsContext.setFill(Food.foodColor);
        paintPoint(mechanics.getFood().getPoint(), graphicsContext);

        // Kolor węża - który zależy od tego, czy gra nadal się toczy, czy gracz przegrał
        Snake snake = mechanics.getSnake();

        if (snake.points.size() == 1) {

            // głowa węża ma być w innym kolorze - łatwo rozróżnialna
            graphicsContext.setFill(Snake.colorHead);
            paintPoint(Snake.getPoints().get(0), graphicsContext);
        } else {
            Point currentPoint = Snake.getPoints().get(Snake.points.size() - 1);

            graphicsContext.setFill(Snake.colorAlive);
            Snake.getPoints().forEach(point -> paintPoint(point, graphicsContext));

            graphicsContext.setFill(Snake.colorHead);
            paintPoint(currentPoint, graphicsContext);
        }

        if (!snake.isSafe()) {
            graphicsContext.setFill(Snake.colorDead);
            paintPoint(snake.getHead(), graphicsContext);

            // Zapisywanie punktacji do pliku tekstowego

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("HIGH SCORES\n\n\n");
            stringBuilder.append("Date: " + LocalDate.now() + "\t\t" + (100 * ((snake.getPoints().size()) * snake.getPoints().size()) / 10 - 10) + " points !");

            FileOutputStream fileOutputStream = null;

            try {
                fileOutputStream = new FileOutputStream("Snake_HighScore.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            try {
                fileOutputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {

            graphicsContext.setFill(Color.GHOSTWHITE);
            graphicsContext.setFont(new Font(15));

            // Punktacja
            graphicsContext.fillText("Score : " + ((100 * (snake.getPoints().size()) * snake.getPoints().size()) / 10 - 10), 10, 20);

            // Informacja o moźliwości wyjścia z gry
            graphicsContext.fillText("Press ESCAPE to reset the game.", 10, 780);

        }
    }


    private static void paintPoint(Point point, GraphicsContext graphicsContext) {
        graphicsContext.fillRect(point.getX() * size, point.getY() * size, size, size);
    }

    public static void paintResetMessage(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.RED);
        graphicsContext.setFont(new Font(20));
        graphicsContext.fillText("GAME OVER ! Press ENTER to reset.\n" +
                "\n\tYour result has been saved.", 450, 350);
        graphicsContext.setFont(new Font(10));

    }


    private Point getRandomPoint() {
        Random random = new Random();
        Point point;
        do {
            point = new Point(random.nextInt(rows), random.nextInt(columns));
        } while (point.equals(snake.getHead()));
        return point;
    }


    public void update() {
        if (food.getPoint().equals(snake.getHead())) {
            snake.extend();
            food.setPoint(getRandomPoint());
        } else {
            snake.move();
        }
    }

    public double getWidth() {
        return rows * size;
    }

    public double getHeight() {
        return columns * size;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }


}
