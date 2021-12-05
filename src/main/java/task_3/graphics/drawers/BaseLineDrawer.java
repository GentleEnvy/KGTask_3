package task_3.graphics.drawers;

import task_3.figures.Line;
import task_3.graphics.canvas.Canvas;


abstract public class BaseLineDrawer
    extends BaseDrawer
{
    protected final Line line;
    
    public BaseLineDrawer(Line line) {
        this.line = line;
    }
    
    @Override
    protected final void _draw(Canvas canvas) {
        var startPixel = canvas.convert(line.start);
        var endPixel = canvas.convert(line.end);
        if (startPixel.equals(endPixel)) {
            canvas.setPixel(startPixel);
            return;
        }
        _drawLine(canvas, startPixel.x, endPixel.x, startPixel.y, endPixel.y);
    }
    
    abstract protected void _drawLine(
        Canvas canvas,
        int startX,
        int endX,
        int startY,
        int endY
    );
}
