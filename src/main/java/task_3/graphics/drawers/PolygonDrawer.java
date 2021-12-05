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
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static task_3.Utils.distance;


public class PolygonDrawer
    extends BaseDrawer
{
    public static final Color SELECT_COLOR = Color.RED;
    public static final double MARK_SIDE = 10;
    
    public final Polygon polygon;
    
    private boolean isSelected = false;
    private Integer selectedMarkIndex = null;
    private Pixel prevDrag = null;
    
    public PolygonDrawer(Polygon polygon) {
        super();
        this.polygon = polygon;
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
            var selectedMark = getSelectedMark();
            if (isSelected) {
                if (selectedMark == null) {
                    if (typeEvent == TypeEvent.MOUSE_PRESSED) {
                        var index = 0;
                        for (MarkDrawer mark : getMarks()) {
                            if (mark.isInside(point)) {
                                selectedMarkIndex = index;
                                return true;
                            }
                            ++index;
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
                            var zeroReal = drawParams.convert(
                                new Pixel(0, 0)
                            );
                            var vector = new Point(
                                deltaReal.x - zeroReal.x,
                                deltaReal.y - zeroReal.y
                            );
                            polygon.center.x += vector.x;
                            polygon.center.y += vector.y;
                            prevDrag = pixel;
                            return true;
                        }
                    } else if (typeEvent == TypeEvent.MOUSE_RELEASED) {
                        prevDrag = null;
                        return true;
                    }
                } else if (typeEvent == TypeEvent.MOUSE_DRAGGED) {
                    var a = selectedMark.center;
                    Point b;
                    b = point;
                    var o = polygon.center;
                    
                    var k1 = (a.y - o.y) / (a.x - o.x);
                    var k2 = -1 / k1;
                    var cx = (k1 * o.x - k2 * b.x + b.y - o.y) / (k1 - k2);
                    var cy = k1 * cx - k1 * o.x + o.y;
                    var c = new Point(cx, cy);
                    
                    var dr = distance(a, c);
                    var isIncrease = distance(o, a) < distance(o, c);
                    
                    polygon.radius += (isIncrease ? 1 : -1) * dr;
                    if (polygon.radius <= 0) {
                        polygon.radius = MARK_SIDE;
                    }
                    
                    var ao = distance(a, o);
                    var bo = distance(b, o);
                    var ab = distance(a, b);
                    var cosDeltaAngle = (ao * ao + bo * bo - ab * ab) / (2 * ao * bo);
                    if (Math.abs(cosDeltaAngle) <= 1) {
                        var oay = k1 * (b.x - o.x) + o.y;
                        var isClockwise = b.y < oay;
                        if (a.x < o.x) {
                            isClockwise = !isClockwise;
                        }
                        var deltaAngle = Math.acos(cosDeltaAngle);
                        polygon.startAngle += (isClockwise ? -1 : 1) * deltaAngle;
                    }
                    
                    return true;
                } else if (typeEvent == TypeEvent.MOUSE_RELEASED) {
                    selectedMarkIndex = null;
                    return true;
                }
            } else if (typeEvent == TypeEvent.MOUSE_PRESSED && polygon.isInside(point)) {
                isSelected = true;
                return true;
            }
        }
        else if (
            isSelected && event instanceof KeyEvent && typeEvent == TypeEvent.KEY_PRESSED
        ) {
            var keyEvent = (KeyEvent) event;
            if (keyEvent.getKeyChar() == '+') {
                polygon.n++;
            } else if (keyEvent.getKeyChar() == '-') {
                polygon.n--;
            }
            return true;
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
            for (var mark : getMarks()) {
                mark.draw(canvas);
            }
        }
    }
    
    private void drawSide(Canvas canvas, Point start, Point end) {
        var lineDrawer = new VuLineDrawer(new Line(start, end));
        lineDrawer.color = isSelected ? SELECT_COLOR : color;
        lineDrawer.draw(canvas);
    }
    
    private List<MarkDrawer> getMarks() {
        var marks = new ArrayList<MarkDrawer>();
        for (var vertex : polygon.getPoints()) {
            marks.add(new MarkDrawer(MARK_SIDE, vertex));
        }
        return marks;
    }
    
    private MarkDrawer getSelectedMark() {
        if (selectedMarkIndex == null) {
            return null;
        }
        return getMarks().get(selectedMarkIndex);
    }
}
