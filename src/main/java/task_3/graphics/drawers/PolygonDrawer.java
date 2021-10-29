package task_3.graphics.drawers;

import task_3.figures.Line;
import task_3.figures.Polygon;
import task_3.graphics.canvas.Canvas;


public class PolygonDrawer
    extends BaseDrawer
{
    private final Polygon polygon;
    
    public PolygonDrawer(final Polygon polygon) {
        this.polygon = polygon;
    }
    
    @Override
    protected void _draw(final Canvas canvas) {
        var pointsIter = polygon.getPoints().iterator();
        var firstPoint = pointsIter.next();
        var prevPoint = firstPoint;
        while (pointsIter.hasNext()) {
            var currentPoint = pointsIter.next();
            new LineDrawer(new Line(prevPoint, currentPoint)).draw(canvas);
            prevPoint = currentPoint;
        }
        new LineDrawer(new Line(prevPoint, firstPoint)).draw(canvas);
    }
}
