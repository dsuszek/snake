package ds.code.snake.Model;

import ds.code.snake.Controller.Mechanics;
import javafx.scene.paint.Color;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    public static final Color colorAlive = Color.DARKGREEN;
    public static final Color colorDead = Color.RED;
    public static final Color colorHead = Color.YELLOWGREEN;
    public boolean safe;
    public static Point head;
    public static List<Point> points;
    private Mechanics mechanics;
    private int length;
    private int speedX;
    private int speedY;

    /**
     * Konstruktor obiektu Snake.
     * Wymaga dwóch argumentów - punktu początkowego, oraz siatki.
     * Początkowy punkt stanie się głową węża, natomiast wewnątrz siatki wąż będzie się poruszał.<br>
     * Jeśli wyjdzie poza siatkę, gra się kończy.
     */
    public Snake(Mechanics mechanics, Point startingPoint) {
        length = 1;
        points = new LinkedList<>();
        points.add(startingPoint);
        head = startingPoint;

        safe = true;
        this.mechanics = mechanics;
        speedX = 0;
        speedY = 0;
    }

    /**
     * Ta metoda jest wywoływana w momencie, kiedy wąż zjada tradycyjne pożywienie.<br>
     * Zwiększa jego długość o 1.
     *
     * @param point Punkt, gdzie znajdowało się jedzenie, a tym samym nowe umiejscowienie głowy węża.
     */
    private void growTo(Point point) {
        length++;
        checkAndAdd(point);
    }


    private void shiftTo(Point point) {
        // głowa zaczyna przemieszczać się w wybranym kierunku
        checkAndAdd(point);
        // ostatni (najstarszy) punkt jest usuwany
        points.remove(0);
    }

    /**
     * Metoda checkAndAdd sprawdza, czy nie nastąpiło przecięcie węża.
     *
     * Jeśli nie, wartość zmiennej <b>safe</b> to nadal "true".
     *
     * @param point Każdy kolejny punkt na drodze węża.

     */
    private void checkAndAdd(Point point) {
        point = mechanics.collide(point);
        safe = safe & !points.contains(point);
        points.add(point);
        head = point;
    }

    /**
     * @return Punkty, z których składa się wąż.
     */
    public static List<Point> getPoints() {
        return points;
    }

    /**
     * @return {@code true} jeśli wąż jeszcze się "nie ugryzł" i nie uderzył w ścianę.
     */
    public boolean isSafe() {
        return safe;
    }

    /**
     * @return Dane o punkcie, który jest głową węża. Między innymi możemy określić współrzędne X i Y.
     */
    public Point getHead() {
        return head;
    }

    private boolean isStill() {
        return speedX == 0 & speedY == 0;
    }

    /**
     * Przesuwa węża o jedną kratkę w wybranym kierunku.
     */
    public void move() {
        if (!isStill()) {
            shiftTo(head.translate(speedX, speedY));
        }
    }

    public void extend() {
        if (!isStill()) {
            growTo(head.translate(speedX, speedY));
        }
    }

    public void goUp() {
        if (speedY == 1 && length > 1) return;
        speedX = 0;
        speedY = -1;
    }

    public void goDown() {
        if (speedY == -1 && length > 1) return;
        speedX = 0;
        speedY = 1;
    }

    public void goLeft() {
        if (speedX == 1 && length > 1) return;
        speedX = -1;
        speedY = 0;
    }

    public void goRight() {
        if (speedX == -1 && length > 1) return;
        speedX = 1;
        speedY = 0;
    }

}
