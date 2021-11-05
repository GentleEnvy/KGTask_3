package task_3.graphics.drawers;

import task_3.figures.Polygon;
import task_3.graphics.canvas.Canvas;
import task_3.graphics.points.Pixel;
import task_3.graphics.points.Point;

import java.awt.*;


public class MarkDrawer
    extends BaseDrawer
{
    public final double side;
    public final Point center;
    
    {
        color = Color.BLUE;
    }
    
    public MarkDrawer(double side, Point center) {
        this.side = side;
        this.center = center;
    }
    
    @Override
    protected void _draw(Canvas canvas) {
        var topLeft = canvas.convert(new Point(
            center.x - side, center.y + side
        ));
        var downRight = canvas.convert(new Point(
            center.x + side, center.y - side
        ));
        for (var y = topLeft.y; y <= downRight.y; ++y) {
            for (var x = topLeft.x; x <= downRight.x; ++x) {
                canvas.setPixel(new Pixel(x, y, Color.BLUE));
            }
        }
    }
    
    public boolean isInside(Point point) {
        Polygon square = new Polygon(
            side * Math.sqrt(2), 4, Math.PI / 4, center
        );
        return square.isInside(point);
    }
}
