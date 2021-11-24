package task_3.graphics.drawers;

import task_3.figures.Line;
import task_3.figures.Polygon;
import task_3.graphics.canvas.Canvas;
import task_3.graphics.canvas.DrawParams;
import task_3.graphics.points.Pixel;
import task_3.graphics.points.Point;
import task_3.window.PixelPane;
import task_3.window.TypeEvent;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;


public class PolygonDrawer
    extends BaseDrawer
{
    public static final Color SELECT_COLOR = Color.RED;
    public static final double MARK_SIDE = 10;
    
    public final Polygon polygon;
    public final Map<MarkDrawer, Point> marks;
    
    private boolean isSelected = false;
    private MarkDrawer selectedMark = null;
    private Pixel prevDrag = null;
    
    public PolygonDrawer(Polygon polygon) {
        super();
        this.polygon = polygon;
        marks = new HashMap<>();
        for (var point : polygon.getPoints()) {
            marks.put(new MarkDrawer(MARK_SIDE, point), point);
        }
    }
    
    @Override
    public boolean handleEvent(
        TypeEvent typeEvent, InputEvent event, DrawParams drawParams
    ) {
        var oldDrawParams = drawParams;
        drawParams = this.drawParams.copy();
        drawParams.modify(oldDrawParams);
        if (event instanceof MouseEvent) {
            var mouseEvent = (MouseEvent) event;
            var pixel = new Pixel(
                mouseEvent.getX(), mouseEvent.getY()
            );
            var point = drawParams.convert(pixel);
            if (isSelected) {
                if (selectedMark == null) {
                    if (typeEvent == TypeEvent.MOUSE_PRESSED) {
                        for (MarkDrawer mark : marks.keySet()) {
                            if (mark.isInside(point)) {
                                selectedMark = mark;
                                return true;
                            }
                        }
                        if (prevDrag == null && polygon.isInside(point)) {
                            prevDrag = drawParams.convert(point);
                        } else {
                            isSelected = false;
                        }
                        return isSelected;
                    } else if (typeEvent == TypeEvent.MOUSE_DRAGGED) {
                        if (prevDrag != null) {
                            var delta = new Pixel(
                                (int) ((pixel.x - prevDrag.x) * PixelPane.SPEED_DRAG),
                                (int) ((pixel.y - prevDrag.y) * PixelPane.SPEED_DRAG)
                            );
                            var deltaReal = drawParams.convert(delta);
                            var zeroReal = drawParams.convert(new Pixel(0, 0));
                            var vector = new Point(
                                deltaReal.x - zeroReal.x,
                                deltaReal.y - zeroReal.y
                            );
//                            this.drawParams.offsetX += vector.x;
//                            this.drawParams.offsetY += vector.y;
                            for (Point vertex : polygon.getPoints()) {
                                vertex.x += vector.x;
                                vertex.y += vector.y;
                            }
                            prevDrag = pixel;
                            return true;
                        }
                    } else if (typeEvent == TypeEvent.MOUSE_RELEASED) {
                        prevDrag = null;
                        return true;
                    }
                } else if (typeEvent == TypeEvent.MOUSE_DRAGGED) {
                    selectedMark.center.x = point.x;
                    selectedMark.center.y = point.y;
                    var vertex = marks.get(selectedMark);
                    vertex.x = point.x;
                    vertex.y = point.y;
                    return true;
                } else if (typeEvent == TypeEvent.MOUSE_RELEASED) {
                    selectedMark = null;
                    return true;
                }
            } else if (typeEvent == TypeEvent.MOUSE_PRESSED && polygon.isInside(point)) {
                isSelected = true;
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected void _draw(Canvas canvas) {
        var pointsIter = polygon.getPoints().iterator();
        var firstPoint = pointsIter.next();
        var prevPoint = firstPoint;
        while (pointsIter.hasNext()) {
            var currentPoint = pointsIter.next();
            drawSide(canvas, prevPoint, currentPoint);
            prevPoint = currentPoint;
        }
        drawSide(canvas, prevPoint, firstPoint);
        if (isSelected) {
            for (var mark : marks.keySet()) {
                mark.draw(canvas);
            }
        }
    }
    
    private void drawSide(Canvas canvas, Point start, Point end) {
        var lineDrawer = new LineDrawer(new Line(start, end));
        lineDrawer.color = isSelected ? SELECT_COLOR : color;
        lineDrawer.draw(canvas);
    }
}
