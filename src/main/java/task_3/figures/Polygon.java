package task_3.figures;

import task_3.graphics.points.Point;

import java.util.ArrayList;
import java.util.List;


public class Polygon {
    private final List<Point> points = new ArrayList<>();
    public final double radius;
    public final Point center;
    
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
        this.radius = radius;
        this.center = center;
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
    
    public boolean isInside(Point point) {
        boolean result = false;
        var prev = points.get(points.size() - 1);
        for (var current : points) {
            if (
                ((current.y > point.y) != (prev.y > point.y)) && (
                    point.x < current.x + (
                        prev.x - current.x
                    ) * (point.y - current.y) / (prev.y - current.y)
                )
            )
            {
                result = !result;
            }
            prev = current;
        }
        return result;
    }
}
