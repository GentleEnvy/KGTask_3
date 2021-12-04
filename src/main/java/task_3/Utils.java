package task_3;

import task_3.graphics.points.Point;


public class Utils {
    public static double distance(Point point1, Point point2) {
        var dx = point1.x - point2.x;
        var dy = point1.y - point2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
