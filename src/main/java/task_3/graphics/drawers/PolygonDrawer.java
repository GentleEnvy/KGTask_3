package task_3.graphics.drawers;

import task_3.figures.Line;
import task_3.figures.Polygon;
import task_3.graphics.canvas.Canvas;
import task_3.graphics.canvas.DrawParams;
import task_3.graphics.points.Pixel;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;


public class PolygonDrawer
    extends BaseDrawer
{
    private final Polygon polygon;
    
    private boolean isSelected = false;
    
    public PolygonDrawer(Polygon polygon) {
        this.polygon = polygon;
    }
    
    @Override
    public boolean edit(DrawParams drawParams, InputEvent event) {
        if (event instanceof MouseEvent) {
            var mouseEvent = (MouseEvent) event;
            if (mouseEvent.getButton() != MouseEvent.BUTTON1) {
                return false;
            }
            var modifiedParams = drawParams.copy();
            modifiedParams.modify(this.drawParams);
            var pixel = new Pixel(mouseEvent.getX(), mouseEvent.getY());
            var point = modifiedParams.convert(pixel);
            isSelected = polygon.isInside(point);
        } else {
            isSelected = false;
        }
        return isSelected;
    }
    
    @Override
    protected void _draw(final Canvas canvas) {
        var pointsIter = polygon.getPoints().iterator();
        var firstPoint = pointsIter.next();
        var prevPoint = firstPoint;
        while (pointsIter.hasNext()) {
            var currentPoint = pointsIter.next();
            var lineDrawer = new LineDrawer(new Line(prevPoint, currentPoint));
            if (isSelected) {
                lineDrawer.color = Color.RED;
            }
            lineDrawer.draw(canvas);
            prevPoint = currentPoint;
        }
        var lineDrawer = new LineDrawer(new Line(prevPoint, firstPoint));
        if (isSelected) {
            lineDrawer.color = Color.RED;
        }
        lineDrawer.draw(canvas);
    }
}
