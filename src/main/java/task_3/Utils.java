package task_3;

import task_3.graphics.points.Point;


public class Utils {
    public static double distance(Point point_1, Point point_2) {
        var dx = point_2.x - point_1.x;
        var dy = point_2.y - point_1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
