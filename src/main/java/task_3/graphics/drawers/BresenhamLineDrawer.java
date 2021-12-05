package task_3.graphics.drawers;

import task_3.figures.Line;
import task_3.graphics.canvas.Canvas;
import task_3.graphics.points.Pixel;


public class BresenhamLineDrawer
    extends BaseLineDrawer
{
    public BresenhamLineDrawer(Line line) {
        super(line);
    }
    
    @Override
    protected void _drawLine(
        Canvas canvas,
        int startX,
        int endX,
        int startY,
        int endY
    ) {
        int dx = Math.abs(startX - endX);
        int dy = Math.abs(startY - endY);
        
        boolean isSteep = dx < dy;
        if (isSteep) {
            int startX_ = startX;
            //noinspection SuspiciousNameCombination
            startX = startY;
            //noinspection SuspiciousNameCombination
            startY = startX_;
            
            int endX_ = endX;
            //noinspection SuspiciousNameCombination
            endX = endY;
            //noinspection SuspiciousNameCombination
            endY = endX_;
            
            int dx_ = dx;
            dx = dy;
            dy = dx_;
        }
        
        if (startX > endX) {
            int startX_ = startX;
            startX = endX;
            endX = startX_;
            int startY_ = startY;
            startY = endY;
            endY = startY_;
        }
        
        int stepY = startY > endY ? -1 : 1;
        int y = startY;
        int d = dx / 2;
        for (int x = startX; x <= endX; ++x) {
            canvas.setPixel(new Pixel(isSteep ? y : x, isSteep ? x : y, color));
            d -= dy;
            if (d < 0) {
                y += stepY;
                d += dx;
            }
        }
    }
}
