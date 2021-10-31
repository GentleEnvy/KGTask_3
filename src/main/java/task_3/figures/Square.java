package task_3.figures;

import task_3.graphics.points.Point;


public class Square extends Polygon {
    public final double side;
    
    public Square(double side, Point center) {
        super(side / Math.sqrt(2), 4, Math.PI / 4, center);
        this.side = side;
    }
    
    public boolean isInside(Point point) {
        return Math.abs(point.x - center.x) <= side / 2 && Math.abs(
            point.y - center.y
        ) <= side / 2;
    }
}
