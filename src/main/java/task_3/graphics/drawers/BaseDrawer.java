package task_3.graphics.drawers;

import task_3.graphics.canvas.Canvas;
import task_3.graphics.canvas.DrawParams;
import task_3.window.TypeEvent;

import java.awt.*;
import java.awt.event.InputEvent;


public abstract class BaseDrawer {
    public final DrawParams drawParams = new DrawParams();
    public Color color = Color.BLACK;
    
    public final void draw(Canvas canvas) {
        var oldDrawParams = canvas.modifyDrawParams(drawParams);
        _draw(canvas);
        canvas.comeBack(oldDrawParams);
    }
    
    abstract protected void _draw(Canvas canvas);
    
    public boolean handleEvent(
        TypeEvent typeEvent, InputEvent event, DrawParams drawParams
    ) {
        return false;
    }
}
