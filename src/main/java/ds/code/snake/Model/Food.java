package ds.code.snake.Model;

import javafx.scene.paint.Color;

public class Food {
    public static final Color foodColor = Color.MEDIUMPURPLE;

    private Point point;

    public Food(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}