package task_3.graphics.drawers;

import task_3.graphics.canvas.Canvas;
import task_3.graphics.canvas.DrawParams;

import java.awt.event.InputEvent;


public abstract class BaseDrawer {
    protected final DrawParams drawParams = new DrawParams();
    
    public final void draw(Canvas canvas) {
        var oldDrawParams = canvas.modifyDrawParams(drawParams);
        _draw(canvas);
        canvas.comeBack(oldDrawParams);
    }
    
    public boolean edit(DrawParams drawParams, InputEvent event) {
        return false;
    }
    
    abstract protected void _draw(Canvas canvas);
}
