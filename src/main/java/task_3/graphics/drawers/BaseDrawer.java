package task_3.graphics.drawers;

import task_3.graphics.canvas.Canvas;
import task_3.graphics.canvas.DrawParams;


public abstract class BaseDrawer {
    private final DrawParams drawParams = new DrawParams();
    
    public final void draw(Canvas canvas) {
        var oldDrawParams = canvas.modifyDrawParams(drawParams);
        _draw(canvas);
        canvas.comeBack(oldDrawParams);
    }
    
    abstract protected void _draw(Canvas canvas);
}
