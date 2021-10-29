package task_3.graphics.canvas;

import task_3.graphics.points.Pixel;
import task_3.graphics.points.Point;


public class DrawParams {
    public double scaleX = 1;
    public double scaleY = 1;
    public double offsetX = 0;
    public double offsetY = 0;
    
    public Point convert(Pixel pixel) {
        double x = pixel.x * scaleX - offsetX;
        double y = -(pixel.y * scaleY - offsetY);
        return new Point(x, y);
    }
    
    public Pixel convert(Point point) {
        double x = (point.x + offsetX) / scaleX;
        double y = -(point.y + offsetY) / scaleY;
        return new Pixel((int) x, (int) y);
    }
    
    public void modify(DrawParams drawParams) {
        scaleX *= drawParams.scaleX;
        scaleY *= drawParams.scaleY;
        offsetX += drawParams.offsetX;
        offsetY += drawParams.offsetY;
    }
    
    public DrawParams copy() {
        var copyDrawParams = new DrawParams();
        copyDrawParams.offsetX = offsetX;
        copyDrawParams.offsetY = offsetY;
        copyDrawParams.scaleX = scaleX;
        copyDrawParams.scaleY = scaleY;
        return copyDrawParams;
    }
    
    public void copyOf(DrawParams drawParams) {
        if (drawParams == null) {
            return;
        }
        
        this.offsetX = drawParams.offsetX;
        this.offsetY = drawParams.offsetY;
        this.scaleX = drawParams.scaleX;
        this.scaleY = drawParams.scaleY;
    }
}
