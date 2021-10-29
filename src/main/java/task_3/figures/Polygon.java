package task_3.figures;

import task_3.graphics.points.Point;

import java.util.ArrayList;
import java.util.List;


public class Polygon {
    private final List<Point> points = new ArrayList<>();
    
    public Polygon(double radius, int n, double startAngle, Point center)
    throws IllegalArgumentException {
        if (n < 3) {
            throw new IllegalArgumentException("n should be >= 3");
        }
        double angle = startAngle;
        double deltaAngle = 2 * Math.PI / n;
        for (int i = 0; i < n; ++i) {
            points.add(new Point(
                radius * Math.cos(angle) + center.x,
                radius * Math.sin(angle) + center.y
            ));
            angle += deltaAngle;
        }
    }
    
    public Polygon(int n, double side, double startAngle, Point center)
    throws IllegalArgumentException {
        this(
            side / Math.sqrt(2 * (1 - Math.cos(2 * Math.PI / n))),
            n, startAngle, center
        );
    }
    
    public List<Point> getPoints() {
        return points;
    }
}
